package Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();
    public ArrayBlockingQueue<NetworkCommunication> recieved;
    Thread serverThread, clientThread;

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

    void openConnection() {
        if (!channelSetup()){
            return;
        }
        //todo: implement
    }

    ArrayList<String> getAvailableServers(){
        //todo: implement
        return new ArrayList<String>();
    }

    void stopServer(){
        //todo: implement
    }

    boolean channelSetup(){
        return recieved != null;
    }
}
