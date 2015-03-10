package Game;

public enum CardSets {
	SUPERHEROES,EMOJIS;
	
	public CardSet toCardSet() {
		switch(this) {
			case SUPERHEROES: 
				return new CardSet("Superheroes");
			case EMOJIS:
				return new CardSet("Emojis");
			default:
				return new CardSet("null");//null?
		}
	}
}
