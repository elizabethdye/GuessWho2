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
	public Card getCard() {
		return card;
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
		int index=rand.nextInt(deck.getSize());
		return deck.getCard(index);
	}
}
