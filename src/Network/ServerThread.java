package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by John McAvey on 3/3/2015.
 */
public class ServerThread {
    ServerSocket acceptor;
    Socket connection;
    NetworkManager manager = NetworkManager.getInstance();

    public static final String START = "START", END = "END";

    public ServerThread(int port) throws IOException {
        acceptor = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while(true){
            acceptConnection();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (isStartCommand(socketReader.readLine())){
                StringBuilder sb = new StringBuilder();
                String line = socketReader.readLine();
                String header = line;
                while(!isEndCommand(line)){
                    sb.append(line);
                }
                NetworkCommunication comm = new NetworkCommunication(null, header, sb.toString());
                manager.recieved.add(comm);
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
