package BackgroundProcesses;

import Agents.Agent;
import Business.InquiryHandling;
import Business.InquiryManager;
import Data.Inquiry;
import Data.InquiryInTreat;

public class Treat extends Thread{


    public Treat(){
    }

    @Override
    public void run(){

        while(Global.SYSTEM_CONDITION_FLAG){
            try {
                if(!InquiryManager.getInstance().queueInTreat.isEmpty()){
                    InquiryInTreat inquiryInTreat=InquiryManager.getInstance().queueInTreat.remove();
                    InquiryHandling inquiryHandling=new InquiryHandling(inquiryInTreat);
                    inquiryHandling.start();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
