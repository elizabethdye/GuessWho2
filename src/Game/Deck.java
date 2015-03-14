package Game;

public class Deck {
	private Card[] cards;
	private CardSet set;
	
	public Deck(CardSet set, int size) {
		this.cards=new Card[size];
		this.set=set;
		setUpCards();
	}
	public int getSize() {
		return cards.length;
	}
	public Card getCard(int index) {
		return cards[index];
	}
	private void setUpCards() {
		set.randomize();
		for (int i=0; i<cards.length; i++) {
			cards[i]=set.getCard(i);
			//TODO error handling for size>num cards in set
		}
	}
}