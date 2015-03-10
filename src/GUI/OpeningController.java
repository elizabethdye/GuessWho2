package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class OpeningController {
	@FXML
	TextField ipAddress;
	
	@FXML
	ChoiceBox cardSet;
	
	@FXML
	ChoiceBox numCards;
	
	@FXML
	Button startGame;
	
	@FXML
	void initialize(){
		
	}
	
	@FXML
	void setIPAddress(){
		//TODO: ipAddress.getText();
	}
	
	@FXML
	void cardSetChoice(){
		//TODO: Game.CardSet = cardSet.getChoice()
	}
	
	@FXML
	void numCardChoice(){
		//TODO: Game.CardSet = numCards.getChoice()
	}
	
	@FXML
	void startGame(){
		setIPAddress();
		cardSetChoice();
		numCardChoice();
	}
}
