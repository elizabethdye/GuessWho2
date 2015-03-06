package Game;

public class Game {//handles basic game rules
	private Player p1, p2;
	private boolean p1Turn, gameOver;
	
	public Game(int numCards, CardSets images) {
		p1=new Player(numCards, images);
		p2=new Player(numCards, images);
		p1Turn=true;
		gameOver=false; //TODO if gameOver, boxes are noneditable
	}
	
	private void turn() {
		if (p1Turn) {
			//TODO display yes or no p1 gui
		}
		else {
			//TODO display yes or no p2 gui
		}
		changeTurn();
	}
	void yes() {
		//TODO print Yes
		turn();
	}
	void no() {
		//TODO print No
		turn();
	}
	void p1Guess(Card card) {
		if (p2.isCorrectCard(card)) {
			gameOver();	
		}
		else {
			penalize(p1);
		}
	}
	void p2Guess(Card card) {
		if (p1.isCorrectCard(card)) {
			gameOver();	
		}
		else {
			penalize(p2);
		}
	}
	void gameOver() {
		gameOver=true;
		//print game over
		//TODO
	}
	void penalize(Player p) {
		p.setPenalized(true);
	}
	void removePenalty(Player p) {
		p.setPenalized(false);
	}
	private void changeTurn() {
		if (p1Turn) {
			if (!p1.isPenalized()) {
				p1Turn=false;
			}
			removePenalty(p1);
		}
		else {
			if (!p2.isPenalized()) {
				p1Turn=true;
			}
			removePenalty(p2);
		}
	}
}
