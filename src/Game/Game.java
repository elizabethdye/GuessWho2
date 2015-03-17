package Game;

import GUI.Controller;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;

public class Game {//handles basic game rules
	private Player user, otherPlayer;
	public boolean userTurn, gameOver;
    private NetworkManager manager = NetworkManager.getInstance();
    public Controller parent;

    public static final String GUESS_RIGHT = "CORRECT", GUESS_WRONG = "WRONG";

	
	public Game(Deck deck) {	
		user =new Player(deck);
		otherPlayer =new Player(deck);
		userTurn =false;
		gameOver=false;
	}
	
	public void p1Guess(Card card) {
		if (userTurn) {
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
        if (userTurn){
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
		if (!userTurn) {
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
		return userTurn;
	}
	
	private void gameOver() {
		gameOver=true;
	}
	
	public void changeTurn() {
		if (userTurn) {
			if (otherPlayer.isPenalized()) {
				if (user.isPenalized()) {
					removePenalty(user);
					removePenalty(otherPlayer);
					userTurn =false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {
                userTurn =false;}
		}
		else {
			if (user.isPenalized()) {
				if (otherPlayer.isPenalized()) {
					removePenalty(user);
					removePenalty(otherPlayer);
					userTurn =true;
				}
				else {removePenalty(user);}
			}
			else {
                userTurn =true;}
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
