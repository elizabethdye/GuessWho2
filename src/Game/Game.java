package Game;

public class Game {//handles basic game rules
	private Player p1, p2;
	private boolean p1Turn, gameOver;

	
	public Game(Deck deck, int size) {	
		p1=new Player(deck); 
		p2=new Player(deck);
		p1Turn=true;
		gameOver=false;
	}
	
	public void p1Guess(Card card) {
		if (p1Turn) {
			if (p2.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(p1);
				p1Turn=false;
			}
		}
	}
	
	public void p2Guess(Card card) {
		if (!p1Turn) {
			if (p1.isCorrectCard(card.getName())) {
				gameOver();	
			}
			else {
				penalize(p2);
				p1Turn=true;
			}
		}
	}
	
	public Player getPlayer() {
		return p1; //TODO fix this
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
		//print game over
		//TODO
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
