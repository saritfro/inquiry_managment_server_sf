package Server;

import BackgroundProcesses.Global;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InquiryManagerServer extends Thread {
   private ServerSocket myServer;

    public ServerSocket getMyServer() {
        return myServer;
    }

    public void setMyServer(ServerSocket myServer) {
        this.myServer = myServer;
    }

    public InquiryManagerServer() throws IOException {
        this.myServer=new ServerSocket(5000);
    }

    @Override
    public void run()  {
        while(Global.SYSTEM_CONDITION_FLAG) {
            Socket clientSocket = null;
            try {
                clientSocket = myServer.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("request accepted. port:"+clientSocket.getPort());
            HandleClient client = new HandleClient(clientSocket);
            client.start();
        }
        stopRun();
    }
    public void stopRun() {
        try {
            myServer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
