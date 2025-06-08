package BackgroundProcesses;

import Business.InquiryManager;
import Data.InquiryInTreat;

public class Matcher extends Thread{

    InquiryManager inquiryManager;

    public Matcher() {
        try {
            this.inquiryManager = InquiryManager.getInstance();//מקבל ב Singleton
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Matcher התחיל...");

        while(Global.SYSTEM_CONDITION_FLAG) {

            if (!inquiryManager.q.isEmpty() && !inquiryManager.queueAgent.isEmpty()) {
                System.out.println("inquiry " + inquiryManager.q.element().getCode() + " sent to treatment");
                InquiryInTreat inTreat = new InquiryInTreat(inquiryManager.queueAgent.remove(), inquiryManager.q.remove());
                inquiryManager.queueInTreat.add(inTreat);
            }

        }
    }
}
