package Game;

public class Game {//handles basic game rules
	private Player p1, p2;
	private boolean p1Turn, gameOver;

	
	public Game(Deck deck) {	
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
				changeTurn();
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
				changeTurn();
			}
		}
	}
	
	public Player getPlayer1() {
		return p1;
	}
	public Player getPlayer2() {
		return p2;
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
			if (p2.isPenalized()) {
				if (p1.isPenalized()) {
					removePenalty(p1);
					removePenalty(p2);
					p1Turn=false;
				}
				else {removePenalty(p2);}
			}
			else {p1Turn=false;}
		}
		else {
			if (p1.isPenalized()) {
				if (p2.isPenalized()) {
					removePenalty(p1);
					removePenalty(p2);
					p1Turn=true;
				}
				else {removePenalty(p1);}
			}
			else {p1Turn=true;}
		}
	}
}
