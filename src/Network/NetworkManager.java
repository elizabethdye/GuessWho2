package Network;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class NetworkManager {
    private static NetworkManager ourInstance = new NetworkManager();
    ArrayBlockingQueue<NetworkCommunication> received;
    ArrayBlockingQueue<NetworkCommunication> errors;
    Thread serverThread;
    ClientThread clientThread;
    TextArea display;
	public String cardSetName;
	public int numCards;
	public String IPAddress;
	private int port=8888;
    private ObservableList<String> knownServers;

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
        received = new ArrayBlockingQueue<NetworkCommunication>(100, true);
        errors = new ArrayBlockingQueue<NetworkCommunication>(100000);
        startServer();
        display = null;
        knownServers = FXCollections.observableArrayList();
    }

    public void setDisplay(TextArea text){
        display = text;
    }

    public void startServer() {
        ServerThread thread = null;
        try {
            thread = new ServerThread(port);
            serverThread = new Thread(thread);
            serverThread.start();

        } catch (IOException e) {
            this.reportError(e.toString());
        }

    }

    public void openConnection(String ip, int port) {
        this.IPAddress = ip;
    }

    public void sendMessage(NetworkCommunication comm){
        clientThread = new ClientThread(IPAddress, port);
        clientThread.addMessage(comm);
        clientThread.start();
    }

    public void sendAutoDiscover(String ip){
        ClientThread thread = new ClientThread(ip, port);
        NetworkCommunication comm = new NetworkCommunication(Message.AUTODISCOVER, getLocalHostname());
        thread.addMessage(comm);
        thread.isAutoDiscover = true;
        thread.start();
    }

    public ArrayList<String> potentialNetworkPartners(){
        ArrayList<String> neighbors = new ArrayList<>();
        String ip = getLocalIP();
        String ipStart = ip.substring(0, ip.lastIndexOf('.'));
        for(int i = 0; i < 255; ++i){
            String next = ipStart + "." + i;
            if (!next.equals(ip)){
                neighbors.add(next);
            }
        }
        return neighbors;
    }

    public void endConnections() {
        serverThread.interrupt();
        clientThread.stopThread(); //custom clean stopper
    }

    boolean channelSetup(){
        return received != null;
    }

    public NetworkCommunication getLatest() throws InterruptedException {
        return received.take();
    }

    public void addComm(NetworkCommunication comm){
        received.add(comm);
    }

    public String getLocalIP() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "Couldn't Aquire your IP";
        }
    }

    public String getLocalHostname() {
        try{
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e){
            return "Couldn't get Hostname";
        }
    }

    public void test(String IP) {
        System.out.println("Starting Server");

        openConnection(IP, port);

        NetworkCommunication comm = new NetworkCommunication(Message.GUESS, "Hello!");
        System.out.println("Sent message: Hello!");
        sendMessage(comm);

    }

    public void reportError(String message){
        NetworkCommunication comm = new NetworkCommunication(Message.ERROR, message);
        received.add(comm);
    }

    public int numItems() {
        return received.size();
    }

    void addServer(String ip){
        System.out.println("Added Server(1): " + ip);
        if (!knownServers.contains(ip)){
            System.out.println("Added Server(2): " + ip);
            knownServers.add(ip);
            sendAutoDiscover(ip);
        }
    }

    public ObservableList<String> getKnownServers(){
        return knownServers;
    }
}
