package Game;
import java.util.Random;


public class Player {
	private Deck deck;
	private Card card;
	private Random rand=new Random();
	private boolean penalized;
	
	public Player(int size, CardSets images) {
		deck=new Deck(size, images);
		card=deck.getCard(rand.nextInt(deck.getSize()));
		penalized=true;
	}
	boolean isCorrectCard(Card card) {
		return this.card==card;
	}
	boolean isPenalized() {
		return penalized;
	}
	void setPenalized(boolean b) {
		penalized=b;
	}
}
