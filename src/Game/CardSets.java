package Game;

public enum CardSets {
	SUPERHEROES;
	
	public CardSet toCardSet() {
		switch(this) {
			case SUPERHEROES: 
				return new CardSet("Superheroes");
			default:
				return new CardSet("null");//null?
		}
	}
}
