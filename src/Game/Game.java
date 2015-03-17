package Game;

public class Game {//handles basic game rules
	private Player p1, p2;
	private boolean p1Turn, gameOver;

	
<<<<<<< HEAD
	public Game(Controller controller, Deck deck) {	
		parent=controller;
		userPlayer =new Player(deck);
		otherPlayer =new Player(deck);
=======
	public Game(Deck deck) {	
		p1=new Player(deck); 
		p2=new Player(deck);
		p1Turn=true;
>>>>>>> SorryTemp
		gameOver=false;
	}
	
	public void p1Guess(Card card) {
		if (p1Turn) {
			if (p2.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(p1);
				changeTurn();
			}
		}
	}
<<<<<<< HEAD

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
=======
	
	public void p2Guess(Card card) {
		if (!p1Turn) {
			if (p1.isCorrectCard(card.getName())) {
>>>>>>> SorryTemp
				gameOver();	
			}
			else {
				penalize(p2);
				changeTurn();
			}
		}
	}
	
<<<<<<< HEAD
	public void setTurn(boolean b) {
		userTurn=b;
	}
	
	public Player getUserPlayer() {
		return userPlayer;
	}
	public Player getOtherPlayer() {
		return otherPlayer;
=======
	public Player getPlayer1() {
		return p1;
	}
	public Player getPlayer2() {
		return p2;
>>>>>>> SorryTemp
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
<<<<<<< HEAD
		if (userTurn) {
			if (otherPlayer.isPenalized()) {
				removePenalty(otherPlayer);
				if (userPlayer.isPenalized()) {
					removePenalty(userPlayer);	
					userTurn=false;
				}
=======
		if (p1Turn) {
			if (p2.isPenalized()) {
				if (p1.isPenalized()) {
					removePenalty(p1);
					removePenalty(p2);
					p1Turn=false;
				}
				else {removePenalty(p2);}
>>>>>>> SorryTemp
			}
			else {p1Turn=false;}
		}
		else {
<<<<<<< HEAD
			if (userPlayer.isPenalized()) {
				removePenalty(userPlayer);
				if (otherPlayer.isPenalized()) {
					removePenalty(otherPlayer);
					userTurn=true;
				}
=======
			if (p1.isPenalized()) {
				if (p2.isPenalized()) {
					removePenalty(p1);
					removePenalty(p2);
					p1Turn=true;
				}
				else {removePenalty(p1);}
>>>>>>> SorryTemp
			}
			else {p1Turn=true;}
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
