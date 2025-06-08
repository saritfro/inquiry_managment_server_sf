package Business;

import Agents.Agent;
import Data.*;
import HandleStoreFiles.HandleFiles;
import HandleStoreFiles.IForSaving;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InquiryManager{
    public Queue<Inquiry>q;
    public Queue<Agent>queueAgent;
    public Queue<InquiryInTreat> queueInTreat;
    private static InquiryManager instance;
    static Integer nextCodeVal ;
    public static Integer getNextCodeVal() {
        return nextCodeVal;
    }

    public static void setNextCodeVal(Integer nextCodeVal) {
        InquiryManager.nextCodeVal = nextCodeVal;
    }
    private InquiryManager() throws Exception {
        nextCodeVal=0;
        q=new LinkedList<Inquiry>() ;
        queueAgent=new LinkedList<Agent>();
        queueInTreat=new LinkedList<InquiryInTreat>();
        LoadFiles();
    }

    public static InquiryManager getInstance() throws Exception {
        if(instance==null)
            instance=new InquiryManager();
        return instance;
    }

    public void UpdateNextCodeVal(int code){
        if(nextCodeVal <= code)
            nextCodeVal = code + 1;
    }

    public void LoadFiles() throws Exception {
        String []str={"Question","Request","Complaint"};
        HandleFiles handleFiles = new HandleFiles();
        for(int i=0;i<3;i++){
            Path directoryPath = Paths.get(Inquiry.INQUIRIES_IN_TREAT_FOLDER+ File.separator+str[i]); // הנחה שהתיקיות נמצאות באותו נתיב
            if (Files.exists(directoryPath) && Files.isDirectory(directoryPath))
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                       Inquiry inq=(Inquiry) handleFiles.readObj(entry.toString());// לבדוק אם זה קובץ רגיל
                        UpdateNextCodeVal(inq.getCode());
                        System.out.println(inq);
                        q.add(inq); // החלף עם הפונקציה שלך
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading directory: " + e.getMessage());
            }
        }
    }

    public void addInquiry(Inquiry inquiry) throws IOException {
        HandleFiles handleFiles = new HandleFiles();
        inquiry.setCode(nextCodeVal++);
        q.add(inquiry);
        handleFiles.saveFile(inquiry);
    }

    public void inquiryCreation() throws IOException {
        HandleFiles handleFiles = new HandleFiles();
        boolean flag=true;
        Scanner scanner = new Scanner(System.in);
        while(flag) {
            System.out.println("to create request press 1\nto create Question press2\nto create complaint press 3 \n press other key to exit");
            int status = scanner.nextInt();
            Inquiry i=null;
            switch (status) {
                case 1:
                    i=new Request();
                    i.fillDataByUser();
                    break;
                case 2:
                    i= new Question();
                    i.fillDataByUser();
                    break;
                case 3:
                    i=new Complaint();
                    i.fillDataByUser();
                    break;
                default:
                    flag=false;
                    break;
            }
            if(!flag)
                break;
            i.setCode(nextCodeVal++);
            q.add(i);
            handleFiles.saveFile((IForSaving) i);
        }
    }

//    public void processInquiryManager() throws IOException {
//        HandleFiles handleFiles = new HandleFiles();
//        while(!q.isEmpty()){
//            Inquiry inqu=q.remove();
//            InquiryHandling inquiry=new InquiryHandling(inqu);
//            inquiry.start();
//            //handleFiles.deleteFile(inquiry.getCurrentInquiry());
//        }
//    }
}
