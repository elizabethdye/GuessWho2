package Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();
    ArrayBlockingQueue<NetworkCommunication> recieved;
    Thread serverThread;
    ClientThread clientThread;

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
    }

    public void startServer() {
        ServerThread thread = null;
        try {
            thread = new ServerThread(8888);
            serverThread = new Thread(thread);
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openConnection(String ip, int port) {
        if (!channelSetup()){
            return;
        }
        clientThread = new ClientThread(ip, port);
        clientThread.start();
    }

    public void sendMessage(NetworkCommunication comm){
        clientThread.toSend.add(comm);
    }

    ArrayList<String> getAvailableServers(){
        //todo: implement
        return new ArrayList<String>();
    }

    public void endConnections() {
        serverThread.interrupt();
        clientThread.stopThread(); //custom clean stopper
    }

    boolean channelSetup(){
        return recieved != null;
    }

    public NetworkCommunication getLatest() throws InterruptedException {
        return recieved.take();
    }
}
