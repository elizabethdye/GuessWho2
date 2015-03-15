package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket acceptor;
    Socket connection;
    NetworkManager manager;

    public static final String START = "START", END = "END";

    public ServerThread(int port) throws IOException {
        acceptor = new ServerSocket(port);
        connection = new Socket();
        manager = NetworkManager.getInstance();
    }

    public void run() {
        System.out.println("Server Started");
        while (true) {
            try {
                acceptConnection();
                if (connectionClosed()){
                    continue;
                }

                System.out.println("connection accepted from: " + connection.getInetAddress().getHostName() + " (" + connection.getInetAddress().getHostAddress() + ")");
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = socketReader.readLine();

                if (isStartCommand(line)) {
                    String message = readFromSocket(socketReader);
                    NetworkCommunication comm = getFromString(message);

                    checkManager();

                    handleComm(comm);
                }
                connection.close();

            } catch (IOException e) {
                manager.reportError(e.toString());
            }
        }
    }

    String readFromSocket(BufferedReader socketReader) throws IOException {
        String line = socketReader.readLine();
        StringBuilder sb = new StringBuilder();
        while(!isEndCommand(line)){
            sb.append(line + "\n");
            line = socketReader.readLine();
        }

        return sb.toString();
    }

    void handleComm(NetworkCommunication comm){
        if (comm.type == Message.AUTODISCOVER){
            manager.addServer(comm.data);
        }
        else {
            manager.addComm(comm);
        }
    }

    void checkManager(){
        if (manager == null) {
            manager = NetworkManager.getInstance();
        }
    }

    void acceptConnection() throws IOException {
        if (connectionClosed()) {
            connection = acceptor.accept();
        }
    }

    boolean connectionClosed() {
        return !connection.isConnected() || connection.isClosed();
    }

    boolean isStartCommand(String s) {
        return s.equals(START);
    }

    boolean isEndCommand(String s){
        return s.equals(END);
    }

    NetworkCommunication getFromString(String s){

        String[] lines = s.split("\\|"); // Can't use the final variable in ClientThread because it will evaluate '|' to and empty String
        Message type = Message.fromString(lines[0]);
        String data = "";
        for (int i = 1; i < lines.length; ++i){
            data += lines[i] + "\n";
        }

        return new NetworkCommunication(type, data);
    }
}
