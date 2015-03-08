package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import Game.CardSet;
import Game.Deck;
import Game.Game;
import Game.CardSets;
import Network.NetworkCommunication;

import java.util.concurrent.ArrayBlockingQueue;

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
    Game game;
    int numCards;
    CardSet cardSet;
    
    ArrayBlockingQueue<NetworkCommunication> networkData;

    void presentData(){
        //called to display the most recently received event.
        try {
            NetworkCommunication data = networkData.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}
