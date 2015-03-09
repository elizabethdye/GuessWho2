package Network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;


public class ClientThread extends Thread {
    String data, destinationIP;
    int port;
    Message type;
    Socket sender;
    BufferedWriter writer;
    public ArrayBlockingQueue toSend;
    boolean finished;
    public static final String seperator = "|";

    public ClientThread(String destinationIP, int port){
        this.destinationIP = destinationIP;
        this.port = port;
        this.toSend = new ArrayBlockingQueue<NetworkCommunication>(2, true);
        this.finished = false;
    }

    public void run(){
        try {
            sender = openConnection();
            writer = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));

            if (sender != null){
                NetworkCommunication comm = (NetworkCommunication)toSend.take();

                String toSend = comm.header + seperator + comm.data;

                writeToSocket(writer, ServerThread.START + "\n");
                writeToSocket(writer, toSend + "\n");
                writeToSocket(writer, ServerThread.END);
            }

            writer.close();
            sender.close();

        } catch (IOException e) {
            NetworkManager.getInstance().reportError(e.toString());
        } catch (InterruptedException e) {
            NetworkManager.getInstance().reportError(e.toString());
        }
    }

    public void writeHeader(BufferedWriter writer, String header) throws IOException {
        if (isValidHeader(header)){
            writer.write(header);
        }
    }

    void writeToSocket(BufferedWriter writer, String message) throws IOException {
        if (message.length() > 0){
            writer.write(message);
        }
    }

    public Socket openConnection() throws IOException {
        Socket s = new Socket(destinationIP, port);
        return s;
    }

    public void addMessage(NetworkCommunication comm){
        this.toSend.add(comm);
    }
    boolean isValidHeader(String header) {
        return header != null && !header.equals("NONE");
    }

    public void stopThread() {
        this.finished = true;
    }
}
