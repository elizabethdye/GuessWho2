package Network;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by John McAvey on 3/3/2015.
 */
public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();
    public ArrayBlockingQueue<NetworkCommunication> recieved;

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
    }

    void startServer() {
        //todo: implement
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
