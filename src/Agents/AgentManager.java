package Agents;

import BackgroundProcesses.Global;
import Business.InquiryManager;
import Data.Inquiry;
import HandleStoreFiles.HandleFiles;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AgentManager {
    Scanner scanner=new Scanner(System.in);
    HandleFiles handleFiles = new HandleFiles();
    InquiryManager inquiryManager=InquiryManager.getInstance();

    public AgentManager() throws Exception {  }

    public Queue<Agent> readAllAgent() throws IOException {
        Queue<Agent> agentQueue=new LinkedList<Agent>();
        Path directoryPath = Paths.get("Agent"); // הנחה שהתיקיות נמצאות באותו נתיב
        if (Files.exists(directoryPath) && Files.isDirectory(directoryPath))
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        Agent agent = (Agent) handleFiles.readObj(entry.toString());
                        System.out.println(agent);
                        agentQueue.add(agent);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return agentQueue;
    }
    public void inaertAllAgentToQueue() throws IOException {
        Queue<Agent> agentQueue=readAllAgent();
        for(Agent a:agentQueue){
            addAgentToQueue(a);
        }
    }

    public void writAgent(Agent agent) throws IOException {
        handleFiles.saveFile(agent);
    }

    public void deleteAgent(Agent agent) throws IOException {
        handleFiles.deleteFile(agent);
        System.out.println("Agent delete succsesfully");
    }

    public void addAgentToQueue(Agent agent){
        inquiryManager.queueAgent.add(agent);
        System.out.println("Agent add to queue succsesfully");
    }

    public Agent insertAgentDatials(){
      System.out.println("insert Name of agent");
      String name = scanner.next();

       System.out.println("insert Id of agent");
       int Id = Integer.parseInt(scanner.next());

        Agent agent = new Agent(Id,name);
        return agent;
    }

    public void PrintMenu() throws IOException {
        System.out.println("the Agents exist in our system");
        readAllAgent();
        int choose=-1;
        Agent agent;
        while(choose!=4){
        System.out.println("to Add agent press 1\n to delete press 2\n to look at all agent press 3\n to load system prees 4 ");
        choose =scanner.nextInt();
        switch (choose){

            case 1:
                agent=insertAgentDatials();
                writAgent(agent);
                break;
            case 2:
                agent=insertAgentDatials();
                deleteAgent(agent);
                break;
            case 3:
                readAllAgent();
                break;
            case 4:
                Global.SYSTEM_CONDITION_FLAG=true;
                inaertAllAgentToQueue();
                break;
        }
        }



    }



}