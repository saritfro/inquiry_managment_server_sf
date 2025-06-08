import Agents.AgentManager;
import BackgroundProcesses.Matcher;
import BackgroundProcesses.Treat;
import BackgroundProcesses.Global;
import Business.InquiryManager;
import Server.InquiryManagerServer;

import java.util.Scanner;

public  class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello, World!");
        //-------------סעיף ב-----------------------
        //InquiryHandling handling1 = new InquiryHandling();
        //Thread handling2 = new Thread();
        //InquiryHandling handling3 = new InquiryHandling();
        //InquiryHandling handling4 = new InquiryHandling();
        //handling1. createInquiry();
       //handling2.createInquiry();
        //handling3.createInquiry();
       //handling4.createInquiry();
        // first running
		//handling1.start();
		//handling2.start();
	//	handling3.start();
		//handling4.start();
        // second running
      // handling1.run();
        //handling2.run();
        //handling3.run();
        //handling4.run();

        //----------------------------------------סעיף ד----------------
//        InquiryHandling[] inquiries = new InquiryHandling[5];
//        for (int i = 0; i < inquiries.length; i++) {
//            inquiries[i] = new InquiryHandling();
//            inquiries[i].createInquiry();
//        }
//        for (int i = 0; i < inquiries.length; i++) {
//            inquiries[i].start();
//        }
        //------------------
       // InquiryManager inquiryManager =  InquiryManager.getInstance();
//        inquiryManager.inquiryCreation();
//        inquiryManager.processInquiryManager();
        //-------------bonus
     //   Thread t=new Thread(()->inquiryManager.inquiryCreation());
     //   Thread t1=new Thread(()->{while(true){inquiryManager.processInquiryManager();}});
     //   t.start();
     //   t1.start();
       // RenameFiles renameFilesThread = new RenameFiles("C:\\Users\\This User\\Desktop\\try", "prefix_"); // ניתן לשנות את הנתיב ואת הטקסט
       // renameFilesThread.start();
        //RenameFilesScheduler scheduler = new RenameFilesScheduler();

        // Schedule renaming task to run at 2:00 AM
        //scheduler.scheduleRenamingTask("C:\\Users\\This User\\Desktop\\try", "new_", 2, 0);
        //FileCleaner.nightProcess();
       /* InquiryManager inquiryManager = InquiryManager.getInstance();

        Thread inquiryCreationThread = new Thread(() -> {
            try {
                inquiryManager.inquiryCreation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Thread processInquiryManagerThread = new Thread(() -> {
            while (true) {
                try {
                    inquiryManager.processInquiryManager();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
              //  try {
              //  sleep(100);
              //  } catch (InterruptedException e) {
              //      e.printStackTrace();
               // }
            }
        });
*/
        // הפעלת התהליכים
      // inquiryCreationThread.start();
      // processInquiryManagerThread.start();
      // FileCleaner.nightProcess();
        //RenameFilesScheduler scheduler = new RenameFilesScheduler();

        // Schedule renaming task to r00 AM
        //scheduler.scheduleRenamingTask("C:\\Users\\This User\\Desktop\\try", "new_", 2, 0);


        /// //////////////////////////
        //Inquiry i=new Question("8iuyjhgf",null);
        //HandleFiles h=new HandleFiles();
        //System.out.println(h.getCSVDataRecursive(i));
//         InquiryManagerServer inquiryManagerServer=new InquiryManagerServer();
//         inquiryManagerServer.start();
        // inquiryManagerServer.stop();

//        while(true){
//
//            AgentManager a=new AgentManager();
//            a.PrintMenu();
//            InquiryManagerServer inquiryManagerServer=new InquiryManagerServer();
//            Matcher matcher=new Matcher();
//            Treat treat=new Treat();
//
//            Global.SYSTEM_CONDITION_FLAG = true;
//
//            inquiryManagerServer.start();
//            Global.SYSTEM_CONDITION_FLAG = true;
//
//            matcher.start();
//            treat.start();
//            Scanner scanner=new Scanner(System.in);
//            System.out.println("insert 'false' at any time to end the run");
//            Global.SYSTEM_CONDITION_FLAG=scanner.nextBoolean();
//
//        }

        try {
            AgentManager a = new AgentManager();
            a.PrintMenu();
            Global.SYSTEM_CONDITION_FLAG = true;
            InquiryManager.getInstance().LoadFiles();
            InquiryManagerServer inquiryManagerServer = new InquiryManagerServer();
            Matcher matcher = new Matcher();
            Treat treat = new Treat();

            inquiryManagerServer.start();
            matcher.start();
            treat.start();


            Scanner scanner = new Scanner(System.in);
            System.out.println("insert 'false' at any time to end the run");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}