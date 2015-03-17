package Game;

import GUI.Controller;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;

public class Game {//handles basic game rules
	private Player userPlayer, otherPlayer;
	private boolean userTurn, gameOver;
    private NetworkManager manager = NetworkManager.getInstance();
    public Controller parent;

    public static final String GUESS_RIGHT = "CORRECT", GUESS_WRONG = "WRONG";

	
	public Game(Controller controller, Deck deck) {	
		parent=controller;
		userPlayer =new Player(deck);
		otherPlayer =new Player(deck);
		gameOver=false;
	}
	
	public void userPlayerGuess(Card card) {
		if (userTurn) {
			if (otherPlayer.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(userPlayer);
				changeTurn();
			}
		}
	}

    public void guess(String name){
        if (userTurn){
            NetworkCommunication guess = new NetworkCommunication(Message.GUESS, name);
            manager.sendMessage(guess);

            changeTurn();
        }
    }

    public void wrongGuess(){
        penalize(userPlayer);
    }

    public boolean checkGuess(String name){
        boolean isCorrect =  userPlayer.isCorrectCard(name);
        if (!isCorrect){
            penalize(otherPlayer);
        }
        return isCorrect;
    }
    
	public void otherPlayerGuess(Card card) {
		if (!userTurn) {
			if (userPlayer.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(otherPlayer);
				changeTurn();
			}
		}
	}
	
	public void setTurn(boolean b) {
		userTurn=b;
	}
	
	public Player getUserPlayer() {
		return userPlayer;
	}
	public Player getOtherPlayer() {
		return otherPlayer;
	}
	
	public void penalize(Player p) {
		p.setPenalized(true);
	}
	
	public void removePenalty(Player p) {
		p.setPenalized(false);
	}
	
	public boolean isEditable() {
		return !gameOver;
	}
	public boolean userTurn() {
		return userTurn;
	}
	
	private void gameOver() {
		gameOver=true;
	}
	
	public void changeTurn() {
		if (userTurn) {
			if (otherPlayer.isPenalized()) {
				removePenalty(otherPlayer);
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);	
					userTurn=false;
				}
			}
			else {userTurn=false;}
		}
		else {
			if (userPlayer.isPenalized()) {
				removePenalty(userPlayer);
				if (otherPlayer.isPenalized()) {
					removePenalty(otherPlayer);
					userTurn=true;
				}
			}
			else {userTurn=true;}
		}

        if (parent != null){
            if (userTurn) {
                parent.userTurn();
            }
            else {
                parent.otherTurn();
            }
        }
	}
	public void changeTurnForTesting() {
		if (userTurn) {
			if (otherPlayer.isPenalized()) {
				removePenalty(otherPlayer);
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);
					userTurn=false;
				}
			}
			else {userTurn=false;}  
		}
		else {
			if (userPlayer.isPenalized()) {
				removePenalty(userPlayer);
				if (otherPlayer.isPenalized()) {
					removePenalty(otherPlayer);
					userTurn=true;  
				}
			}
			else {userTurn=true;}
		}
	}
	public void userPlayerGuessForTesting(Card card) {
		if (userTurn) {
			if (otherPlayer.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(userPlayer);
				changeTurnForTesting();
			}
		}
	}
	public void otherPlayerGuessForTesting(Card card) {
		if (!userTurn) {
			if (userPlayer.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(otherPlayer);
				changeTurnForTesting();
			}
		}
	}
}
