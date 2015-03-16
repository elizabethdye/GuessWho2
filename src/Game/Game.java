package Game;

import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;

public class Game {//handles basic game rules
	private Player user, otherPlayer;
	private boolean p1Turn, gameOver;
    private NetworkManager manager = NetworkManager.getInstance();

    public static final String GUESS_RIGHT = "CORRECT", GUESS_WRONG = "WRONG";

	
	public Game(Deck deck) {	
		user =new Player(deck);
		otherPlayer =new Player(deck);
		p1Turn=true;
		gameOver=false;
	}
	
	public void p1Guess(Card card) {
		if (p1Turn) {
			if (otherPlayer.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(user);
				changeTurn();
			}
		}
	}

    public void guess(String name){
        if (p1Turn){
            NetworkCommunication guess = new NetworkCommunication(Message.GUESS, name);
            manager.sendMessage(guess);

            changeTurn();
        }
    }

    public void wrongGuess(){
        penalize(user);
    }

    public boolean checkGuess(String name){
        boolean isCorrect =  user.isCorrectCard(name);
        if (!isCorrect){
            penalize(otherPlayer);
        }
        return isCorrect;
    }
	
	public void p2Guess(Card card) {
		if (!p1Turn) {
			if (user.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(otherPlayer);
				changeTurn();
			}
		}
	}
	
	public Player getPlayer1() {
		return user;
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
	
	public boolean p1Turn() {
		return p1Turn;
	}
	
	private void gameOver() {
		gameOver=true;
	}
	
	public void changeTurn() {
		if (p1Turn) {
			if (otherPlayer.isPenalized()) {
				if (user.isPenalized()) {
					removePenalty(user);
					removePenalty(otherPlayer);
					p1Turn=false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {p1Turn=false;}
		}
		else {
			if (user.isPenalized()) {
				if (otherPlayer.isPenalized()) {
					removePenalty(user);
					removePenalty(otherPlayer);
					p1Turn=true;
				}
				else {removePenalty(user);}
			}
			else {p1Turn=true;}
		}
	}
}
