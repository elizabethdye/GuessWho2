package GUI;

import java.io.IOException;

import Game.Card;
import Game.CardSet;
import Game.CardSets;
import Game.Deck;
import Game.Game;
import Network.NetworkManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpeningController {
	@FXML
	TextField ipAddress;
	
	@FXML
	ChoiceBox cardSet;
	
	@FXML
	ChoiceBox numCards;
	
	@FXML
	Button startGame;
	
	
    private CardSet cards;
    private Deck deck;
	private final int port=8888;
    public boolean gameStarted = false;
	private NetworkManager manager = NetworkManager.getInstance();
	
	@FXML
	void initialize(){
		cardSetChoice();
		numCardChoice();
	}
	
	
	@FXML
	void cardSetChoice(){
		cardSet.getItems().addAll("Emojis","Superheroes");
		cardSet.setValue("Emojis");
		//TODO: Game.CardSet = cardSet.getChoice()
	}
	
	@FXML
	void numCardChoice(){
		numCards.getItems().addAll("9","16","25");
		numCards.setValue("16");
		//TODO: Game.CardSet = numCards.getChoice()
	}
	
	@FXML
	void startGame() throws IOException{
		chooseCardSet();
		chooseNumCards();
		setIPAddress();
		Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
		Scene home_page_scene = new Scene(home_page_parent, 900, 650);
		Stage app_stage = (Stage) startGame.getScene().getWindow();
		app_stage.setScene(home_page_scene);
		app_stage.show();
	}
	
	private void setIPAddress(){
		manager.IPAddress = ipAddress.getText();
	}
	
	private void chooseCardSet(){
		manager.cardSetName = getString(cardSet).toUpperCase();
		cards = CardSets.valueOf(manager.cardSetName).toCardSet();
	}
	
	private void chooseNumCards(){
		manager.numCards = Integer.valueOf(getString(numCards));
	}
	
	private String getString(ChoiceBox<String> box) {
		return box.getValue().toString();
	}
}
