package Business;

import Agents.Agent;
import Data.*;
import HandleStoreFiles.HandleFiles;

import java.io.IOException;
import java.util.Random;

import java.util.Scanner;

public class InquiryHandling extends Thread {

    private Inquiry currentInquiry;
    private Agent currentAgent;

    public Inquiry getCurrentInquiry() {
        return currentInquiry;
    }
    public void setCurrentInquiry(Inquiry currentInquiry) {
        this.currentInquiry = currentInquiry;
    }


    public InquiryHandling(InquiryInTreat currentInquiry){
        this.currentInquiry=currentInquiry.getInquiry();
        this.currentAgent=currentInquiry.getAgent();
    }

    public InquiryHandling() {
    }

    /**
     * יש פונקציה אחרת לשימוש
     *
     * @throws Exception
     */
    @Deprecated
    public void createInquiry() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("to create request press 1\nto create Question press2\nto create complaint press 3 ");
        int status = scanner.nextInt();
        switch (status) {
            case 1:
                currentInquiry = new Request();
                break;
            case 2:
                currentInquiry = new Question();
                break;
            case 3:
                currentInquiry = new Complaint();
                break;
            default:
                throw new Exception("no such option");
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        int min = 0, max = 0, r = 0;
        String type = "";
        if (currentInquiry instanceof Request) {
            min = 10;
            max = 15;
            type = "Request";
        }
        if (currentInquiry instanceof Question) {
            min = 1;
            max = 5;
            type = "Question";
        }
        if (currentInquiry instanceof Complaint) {
            min = 20;
            max = 40;
            type = "Complaint";
        }
        if (currentInquiry instanceof Question)
            setPriority(MAX_PRIORITY);
        //-------------------סעיף ב----------------
//        if(currentInquiry instanceof Request) {
//            try {
//                sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else {
//            try {
//                sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        int randomNumber = random.nextInt(max - min + 1) + min;
        if (randomNumber > 5 && Thread.activeCount() > 10)
            Thread.yield();

        currentInquiry.handling();
        try {
            sleep(1000 * randomNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("number "+currentInquiry.getCode()+" " + type + " will take " + randomNumber + " seconds----");

        //insert agent in queueAgent back
            try {
                InquiryManager.getInstance().queueAgent.add(currentAgent);
                System.out.println("agent added to queueAgent");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        //move inquiry form 'inquiry_in_treat' folder to 'history' folder
        HandleFiles handleFiles=new HandleFiles();
        try {
            handleFiles.deleteFile(currentInquiry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentInquiry.setFolderName(Inquiry.HISTORY_FOLDER);
        try {
            handleFiles.saveFile(currentInquiry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
