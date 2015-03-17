package Game;

import GUI.Controller;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;

public class Game {//handles basic game rules
<<<<<<< HEAD
	private Player userPlayer, otherPlayer;
	private boolean userPlayerTurn, gameOver;
=======
	private Player user, otherPlayer;
	public boolean userTurn, gameOver;
>>>>>>> origin/master
    private NetworkManager manager = NetworkManager.getInstance();
    public Controller parent;

    public static final String GUESS_RIGHT = "CORRECT", GUESS_WRONG = "WRONG";

	
	public Game(Deck deck) {	
		userPlayer =new Player(deck);
		otherPlayer =new Player(deck);
<<<<<<< HEAD
		userPlayerTurn=true;
		gameOver=false;
	}
	
	public void userPlayerGuess(Card card) {
		if (userPlayerTurn) {
=======
		userTurn =false;
		gameOver=false;
	}
	
	public void p1Guess(Card card) {
		if (userTurn) {
>>>>>>> origin/master
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
<<<<<<< HEAD
        if (userPlayerTurn){
=======
        if (userTurn){
>>>>>>> origin/master
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
	
<<<<<<< HEAD
	public void otherPlayerGuess(Card card) {
		if (!userPlayerTurn) {
			if (userPlayer.isCorrectCard(card.getName())) {
=======
	public void p2Guess(Card card) {
		if (!userTurn) {
			if (user.isCorrectCard(card.getName())) {
>>>>>>> origin/master
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
	
<<<<<<< HEAD
	public boolean userPlayerTurn() {
		return userPlayerTurn;
=======
	public boolean p1Turn() {
		return userTurn;
>>>>>>> origin/master
	}
	
	private void gameOver() {
		gameOver=true;
	}
	
	public void changeTurn() {
<<<<<<< HEAD
		if (userPlayerTurn) {
=======
		if (userTurn) {
>>>>>>> origin/master
			if (otherPlayer.isPenalized()) {
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
<<<<<<< HEAD
					userPlayerTurn=false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {userPlayerTurn=false;}
=======
					userTurn =false;
				}
				else {removePenalty(otherPlayer);}
			}
			else {
                userTurn =false;}
>>>>>>> origin/master
		}
		else {
			if (userPlayer.isPenalized()) {
				if (otherPlayer.isPenalized()) {
					removePenalty(userPlayer);
					removePenalty(otherPlayer);
<<<<<<< HEAD
					userPlayerTurn=true;
=======
					userTurn =true;
>>>>>>> origin/master
				}
				else {removePenalty(userPlayer);}
			}
<<<<<<< HEAD
			else {userPlayerTurn=true;}
=======
			else {
                userTurn =true;}
>>>>>>> origin/master
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
