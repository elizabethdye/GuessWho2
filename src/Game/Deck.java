package Game;

public class Deck {//deck used in game, made from size elements from preset CardSets
	private Card[] cards;
	private CardSets images;
	
	public Deck(int size, CardSets images) {
		this.cards=new Card[size];
		this.images=images;
		setUpCards();
	}
	public int getSize() {
		return cards.length;
	}
	Card getCard(int index) {
		return cards[index];
	}
	private void setUpCards() {
		String name;
		images.randomize();
		for (int i=0; i<cards.length; i++) {
			name=images.getName(i);
			cards[i]=new Card(name,images.getImage(name));
		}
	}
}