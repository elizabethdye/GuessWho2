package Game;

import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;

public class Game {//handles basic game rules
	private Player userPlayer, otherPlayer;
	private boolean userPlayerTurn, gameOver;
    private NetworkManager manager = NetworkManager.getInstance();

    public static final String GUESS_RIGHT = "CORRECT", GUESS_WRONG = "WRONG";

	
	public Game(Deck deck) {	
		userPlayer =new Player(deck);
		otherPlayer =new Player(deck);
		userPlayerTurn=true;
		gameOver=false;
	}
	
	public void userPlayerGuess(Card card) {
		if (userPlayerTurn) {
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
        if (userPlayerTurn){
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
		if (!userPlayerTurn) {
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
	
	public boolean userPlayerTurn() {
		return userPlayerTurn;
	}
	
	private void gameOver() {
		gameOver=true;
	}
	
	public void changeTurn() {
		if (userPlayerTurn) {
			if (otherPlayer.isPenalized()) {
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
					userPlayerTurn=false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {userPlayerTurn=false;}
		}
		else {
			if (userPlayer.isPenalized()) {
				if (otherPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
					userPlayerTurn=true;
				}
				else {removePenalty(userPlayer);}
			}
			else {userPlayerTurn=true;}
		}
	}
}
