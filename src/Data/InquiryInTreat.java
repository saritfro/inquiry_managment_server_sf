package Data;

import Agents.Agent;
import Business.InquiryManager;
import HandleStoreFiles.HandleFiles;

import java.io.IOException;

public class InquiryInTreat extends Thread{

    private Agent agent;
    private Inquiry inquiry;

    public InquiryInTreat(Agent agent, Inquiry inquiry) {
        this.agent = agent;
        this.inquiry = inquiry;
    }

    public Agent getAgent() {return agent;}
    public Inquiry getInquiry() {return inquiry;}

    @Override
    public void run(){

        inquiry.handling();

        try {
            InquiryManager.getInstance().queueAgent.add(agent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
