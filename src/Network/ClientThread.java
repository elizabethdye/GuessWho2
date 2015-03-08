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

                writeToSocket(writer, ServerThread.START);

                writeHeader(writer, comm.header);

                writeToSocket(writer, comm.data);

                writeToSocket(writer, ServerThread.END);
            }

            writer.close();
            sender.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public Socket openConnection(){
        try {
            Socket s = new Socket(destinationIP, port);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
