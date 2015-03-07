package Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();
    public ArrayBlockingQueue<NetworkCommunication> recieved;
    Thread serverThread;
    ClientThread clientThread;

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
    }

    void startServer() {
        ServerThread thread = null;
        try {
            thread = new ServerThread(8888);
            serverThread = new Thread(thread);
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void openConnection(String ip, int port) {
        if (!channelSetup()){
            return;
        }
        clientThread = new ClientThread(ip, port);
        clientThread.start();
    }

    void sendMessage(NetworkCommunication comm){
        clientThread.toSend.add(comm);
    }

    ArrayList<String> getAvailableServers(){
        //todo: implement
        return new ArrayList<String>();
    }

    void endConnections() {
        serverThread.interrupt();
        clientThread.stopThread(); //custom clean stopper
    }

    boolean channelSetup(){
        return recieved != null;
    }

    NetworkCommunication getLatest() throws InterruptedException {
        return recieved.take();
    }
}
