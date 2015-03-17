package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GUI.Controller;
import Game.Card;
import Game.CardSet;
import Game.CardSets;
import Game.Deck;
import Game.Game;
import Game.Player;

public class GameTests {
	private Game game;
	private Deck deck;
	private int size;
	private Card wrongCard;
	
	@Before
	public void setup() {
		size=16;
		CardSet cardSet=CardSets.EMOJIS.toCardSet();
		deck=new Deck(cardSet,size);
		newGame();
		CardSet wrongCardSet=CardSets.SUPERHEROES.toCardSet();
		wrongCard=wrongCardSet.getCard(0);
	}

	@Test
	public void testGuessing() {
		Card p1Card;
		Card p2Card;
		
		checkEditable();
		p2Card=getP2Card();
		p1Guess(p2Card);
		checkNotEditable();
		
		newGame();
		checkEditable();
		p1Guess(wrongCard);
		checkEditable();
		p1Card=getP1Card();
		p2Guess(p1Card);
		checkNotEditable();
		
		newGame();
		checkEditable();
		p1Guess(wrongCard);
		checkEditable();
		p2Guess(wrongCard);
		checkEditable();
	}
	
	@Test
	public void testTurns() {
		isP1Turn();
		p2Guess(getP1Card());
		checkEditable();
		changeTurn();
		isP2Turn();
		p1Guess(getP2Card());
		checkEditable();
		changeTurn();
		isP1Turn();
		changeTurn();
		isP2Turn();
		changeTurn();
		isP1Turn();
		changeTurn();
		isP2Turn();
		changeTurn();
		isP1Turn();
		changeTurn();
		isP2Turn();
	}
	
	@Test
	public void testPenalty() {
		p1Guess(wrongCard);
		checkP1Penalized();
		isP2Turn();
		p2Guess(wrongCard);
		isP1Turn();
		changeTurn();
		isP2Turn();
		
		newGame();
		changeTurn();
		p2Guess(wrongCard);
		checkP2Penalized();
		p1Guess(wrongCard);
		isP2Turn();
		
		newGame();
		p1Guess(wrongCard);
		checkP1Penalized();
		changeTurn();
		
		newGame();
		changeTurn();
		p2Guess(wrongCard);
		checkP2Penalized();
		changeTurn();
	}
	
	@Test
	public void testImage() {
		Card card=getP1Card();
		assertFalse(card.getImage().equals(null));
	}
	
	private void newGame() {
		game=new Game(new Controller(),deck);
		game.setTurn(true);
	}
	private void changeTurn() {
		game.changeTurnForTesting();
	}
	private void p1Guess(Card card) {
<<<<<<< HEAD
		game.userPlayerGuessForTesting(card);
	}
	private void p2Guess(Card card) {
		game.userPlayerGuessForTesting(card);
=======
		game.p1Guess(card);
	}
	private void p2Guess(Card card) {
		game.p2Guess(card);
>>>>>>> SorryTemp
	}
	private Player getP1() {
		return game.getUserPlayer();
	}
	private Player getP2() {
		return game.getOtherPlayer();
	}
	private Card getP1Card() {
		return getP1().getCard();
	}
	private Card getP2Card() {
		return getP2().getCard();
	}
	
	private void checkEditable() {
		assertTrue(game.isEditable());
	}
	private void checkNotEditable() {
		assertFalse(game.isEditable());
	}
	private void isP1Turn() {
		assertTrue(game.p1Turn());
	}
	private void isP2Turn() {
		assertFalse(game.p1Turn());
	}
	private void checkP1Penalized() {
		assertTrue(getP1().isPenalized());
	}
	private void checkP2Penalized() {
		assertTrue(getP2().isPenalized());
	}
}