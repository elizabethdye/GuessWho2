package Game;

public class Deck {
	private Card[] cards;
	private CardSet set;
	
	public Deck(int size, CardSet set) {
		this.cards=new Card[size];
		this.set=set;
		setUpCards();
	}
	public int getSize() {
		return cards.length;
	}
	Card getCard(int index) {
		return cards[index];
	}
	private void setUpCards() {
		set.randomize();
		for (int i=0; i<cards.length; i++) {
			cards[i]=set.getCard(i);
		}
	}
}