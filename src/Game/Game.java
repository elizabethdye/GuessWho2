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

	
	public Game(Deck deck) {	
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
	
	public Player getPlayer1() {
		return userPlayer;
	}
	public Player getPlayer2() {
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
			if (!userPlayer.isPenalized()) {
				userTurn=false;
			}
			if (otherPlayer.isPenalized()) {
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
					userTurn=false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {userTurn=false;}
		}
		else {
			if (!otherPlayer.isPenalized()) {
				userTurn=true;
			}
			if (userPlayer.isPenalized()) {
				if (otherPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
					userTurn=true;
				}
				else {removePenalty(userPlayer);}
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
}
