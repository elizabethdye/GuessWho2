package GUI;

import Network.NetworkCommunication;

import java.util.concurrent.ArrayBlockingQueue;

public class Controller {

    ArrayBlockingQueue<NetworkCommunication> networkData;

    void presentData(){
        //called to display the most recently recieved event.
        try {
            NetworkCommunication data = networkData.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
