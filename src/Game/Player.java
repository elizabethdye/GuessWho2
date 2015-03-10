package Game;
import java.util.Random;


public class Player {
	private Deck deck;
	private Card card;
	private Random rand=new Random();
	private boolean penalized;

	public Player(Deck deck) {
		this.deck=deck;
		card=getRandomCard();
		penalized=false;
	}
	boolean isCorrectCard(String card) {
		return this.card.getName().equals(card);
	}
	boolean isPenalized() {
		return penalized;
	}
	void setPenalized(boolean b) {
		penalized=b;
	}
	
	private Card getRandomCard() {
		return deck.getCard(rand.nextInt(deck.getSize()));
	}
}
