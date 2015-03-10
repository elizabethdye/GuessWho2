package GUI;

import Game.CardSet;
import Game.CardSets;
import Game.Deck;
import Game.Game;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    GridPane imageGrid;
    @FXML
    Button heart;
    @FXML
    Button crossOut;
    @FXML
    Button guess;
    @FXML
    TextArea conversation;
    @FXML
    ImageView profile;
    @FXML
    Label whoseTurn;
    @FXML
    TextArea inputText;
    @FXML
    CheckBox isQuestion;
    @FXML
    Button yes;
    @FXML
    Button no;
    
    private Game game;
    private int numCards;
    private CardSet cardSet;

    NetworkManager manager = NetworkManager.getInstance();

    void presentData(){
        //called to display the most recently received event.
        try {
            NetworkCommunication data = manager.getLatest(); // I've modified this to take from the Network API -- John
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void sendTestPackets() {
        String ip = inputText.getText();
        if (ip.length() > 0) {
            NetworkManager.getInstance().test(ip);
        }
    }

    @FXML
    void loadFromQueue() {
        try {
            conversation.appendText(manager.getLatest().toString() + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendMessage(){
        String message = inputText.getText();
        NetworkCommunication comm = new NetworkCommunication(Message.TEXT, message);

        manager.sendMessage(comm);
    }

    @FXML
    void initialize(){
        String ip = manager.getLocalIP();
        conversation.appendText("Your IP: " + manager.getLocalIP() + '\n');
        manager.setDisplay(conversation);
    }
    
    private void startGame() {
    	Deck deck=new Deck(numCards, cardSet);
    	game=new Game(deck);
    }
    private void chooseNumCards() {
    	numCards=25; //temp
    	//TODO not sure best way to do this. perhaps small window with choice/combobox upon starting game that requests num
    }
    private void chooseCardSet() {//choose cards first
    	CardSets set=CardSets.SUPERHEROES;
    	cardSet=set.toCardSet(); //temp
    	//TODO see chooseNumCards
    }
    private boolean isEditable() {
    	return game.isEditable();
    }
}
