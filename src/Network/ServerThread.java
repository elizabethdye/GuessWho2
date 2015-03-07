package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket acceptor;
    Socket connection;
    NetworkManager manager = NetworkManager.getInstance();

    public static final String START = "START", END = "END";

    public ServerThread(int port) throws IOException {
        acceptor = new ServerSocket(port);
    }

    public void run() {
        while(true){
            try {
                acceptConnection();
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                if (isStartCommand(socketReader.readLine())){
                    StringBuilder sb = new StringBuilder();
                    String line = socketReader.readLine();
                    String header = line;
                    while(!isEndCommand(line)){
                        sb.append(line);
                    }
                    //todo: parse out the data better than this... 

                    NetworkCommunication comm = new NetworkCommunication(Message.TEXT, sb.toString());
                    manager.recieved.add(comm);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //todo: Get back to the user with the error
            }
        }
    }

    void acceptConnection() throws IOException {
        if (connection.isClosed()) {
            connection = acceptor.accept();
        }
    }
    boolean isStartCommand(String s) {
        return s.equals(START);
    }

    boolean isEndCommand(String s){
        return s.equals(END);
    }

}
