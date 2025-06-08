package Server;

import Business.InquiryManager;
import ClientServer.InquiryManagerActions;
import ClientServer.RequestData;
import ClientServer.ResponseData;
import ClientServer.ResponseStatus;
import Data.*;
import HandleStoreFiles.HandleFiles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HandleClient extends Thread {
    private Socket clientSocket;

    public Socket getClientSocket() {
        return clientSocket;
    }
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public HandleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private void handleClientRequest() throws Exception {
        RequestData obj;
        ObjectInputStream ois=new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos=new ObjectOutputStream(clientSocket.getOutputStream());
        do {
            obj = (RequestData) ois.readObject();
            ResponseData ResponseData = treatRequest(obj);
            oos.writeObject(ResponseData);
        } while (obj.getAction() != InquiryManagerActions.EXIT);

        oos.close();
        ois.close();
    }

    public ResponseData treatRequest(RequestData requestData) throws Exception {
        ResponseData res = new ResponseData();
        switch (requestData.getAction()) {
            case ADD_INQUIRY:
                addInquiry(requestData);
                res.setStatus(ResponseStatus.SUCCESS);
                res.setMessage("inquiry added successfully");
                //also send in result inquiry-code
                break;
            case ALL_INQUIRY:
                String s = allInquiries();
                res.setStatus(ResponseStatus.SUCCESS);
                res.setMessage("send all inquiry successfully");
                res.setResult(s);
                break;
            case MONTHLY_INQUIRIES:
                try{
                List<Inquiry> monthInquiries=monthlyInquiries((int)requestData.getParameters()[0]
                        ,(int)requestData.getParameters()[1]);
                res.setStatus(ResponseStatus.SUCCESS);
                res.setResult(monthInquiries);
                res.setMessage("send all inquiry successfully");}
                catch (Exception e){
                    System.out.println("The parameters are not match");
                    e.printStackTrace();
                }
                break;
            case CANCEL:
                boolean isFound = cancelInquiry(requestData);
                if(isFound){
                    res.setStatus(ResponseStatus.SUCCESS);
                    res.setMessage("inquiry canceled successfully");
                    break;
                }
                else{
                    res.setStatus(ResponseStatus.FAIL);
                    res.setMessage("inquiry already in treat");
                    break;
                }
            default:
                res.setStatus(ResponseStatus.FAIL);
                res.setMessage("there is no such function");
                break;
        }
        return res;
    }

    private static boolean cancelInquiry(RequestData requestData) throws Exception {
        int code=(int) requestData.getParameters()[0];
        InquiryManager inquiryManager=InquiryManager.getInstance();

        boolean isFound = inquiryManager.queueInTreat.removeIf(inTreat ->
                inTreat.getInquiry().getCode() == code
        );
        if(!isFound)
            isFound=inquiryManager.q.removeIf(inquiry ->
                    inquiry.getCode()==code
            );
        return isFound;
    }

    private void addInquiry(RequestData requestData) throws Exception {
        InquiryManager inquiryManager = InquiryManager.getInstance();
        Inquiry i = null;

        switch (requestData.getParameters()[0].getClass().getSimpleName()) {
            case "Question":
                i = new Question();
                Question q = (Question) requestData.getParameters()[0];
                i.setDescription(q.getDescription());
                i.setDocumentsList(q.getDocumentsList());
                break;
            case "Request":
                i = new Request();
                Request r = (Request) requestData.getParameters()[0];
                i.setDescription(r.getDescription());
                i.setDocumentsList(r.getDocumentsList());
                break;
            case "Complaint":
                i = new Complaint();
                Complaint c = (Complaint) requestData.getParameters()[0];
                i.setDescription(c.getDescription());
                i.setDocumentsList(c.getDocumentsList());
                Complaint temp = (Complaint) i;
                temp.setAssignedBranch(c.getAssignedBranch());
                break;
        }
        inquiryManager.addInquiry(i);
    }

    private String allInquiries() throws Exception {
        InquiryManager inquiryManager = InquiryManager.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Inquiry item : inquiryManager.q) {
            sb.append(item.toString());
        }
        return sb.toString();
    }

    private List<Inquiry> monthlyInquiries(int year, int month) throws Exception {
        List<Inquiry> inquiryList = new ArrayList<Inquiry>();
        HandleFiles handleFiles = new HandleFiles();
        Path directoryPath = Paths.get("history");
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        Inquiry inq = (Inquiry) handleFiles.readObj(entry.toString());
                        if (inq.getCreationDate().getYear() == year &&
                                inq.getCreationDate().getMonthValue() == month)
                            inquiryList.add(inq);


                    }
                }
            }
        }
        return inquiryList;
    }

    @Override
    public synchronized void run() {
        try {
            handleClientRequest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

