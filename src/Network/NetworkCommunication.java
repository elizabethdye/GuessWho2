package Network;

/**
 * Created by John McAvey on 3/3/2015.
 */
public class NetworkCommunication {
    public Message type;
    public String header, data;

    public NetworkCommunication(Message t, String header, String data){
        this.type = t;
        this.header = header;
        this.data = data;
    }
}
