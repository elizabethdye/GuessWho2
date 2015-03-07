package Network;


public class NetworkCommunication {
    public Message type;
    public String header, data;

    public NetworkCommunication(Message t, String data){
        this.type = t;
        this.header = t.toString();
        this.data = data;
    }
}
