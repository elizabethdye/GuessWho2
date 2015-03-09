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
        startServer();
    }

    public void startServer() {
        ServerThread thread = null;
        try {
            thread = new ServerThread(8888);
            serverThread = new Thread(thread);
            serverThread.start();

        } catch (IOException e) {
            this.reportError(e.toString());
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

    public void addComm(NetworkCommunication comm){
        recieved.add(comm);
    }

    public String getLocalIP() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "Couldn't Aquire your IP";
        }
    }

    public void test(String IP) {
        System.out.println("Starting Server");
        int port = 8888;

        openConnection(IP, port);

        NetworkCommunication comm = new NetworkCommunication(Message.GUESS, "Hello!");
        System.out.println("Sent message: Hello!");
        sendMessage(comm);

    }

    public void reportError(String message){
        NetworkCommunication comm = new NetworkCommunication(Message.ERROR, message);
        this.addComm(comm);
    }
}
