package Network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
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
        recieved = new ArrayBlockingQueue<NetworkCommunication>(2, true);
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
        clientThread.addMessage(comm);
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

    public String getLocalIP() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }

    public void test() throws UnknownHostException {
        System.out.println("Starting Server");
        int port = 8888;

        startServer();
        openConnection(getLocalIP(), port);

        NetworkCommunication comm = new NetworkCommunication(Message.GUESS, "Hello!");
        System.out.println("Sent message: Hello!");
        sendMessage(comm);

    }
}
