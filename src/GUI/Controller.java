package GUI;

import Game.CardSet;
import Game.Deck;
import Game.Game;
import Game.CardSets;
import Network.NetworkCommunication;

import java.util.concurrent.ArrayBlockingQueue;

public class Controller {
	Game game;
	int numCards;
	CardSet cardSet;
    ArrayBlockingQueue<NetworkCommunication> networkData;

    void presentData(){
        //called to display the most recently received event.
        try {
            NetworkCommunication data = networkData.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void startGame() {
    	Deck deck=new Deck(numCards, cardSet);
    	game=new Game(deck);
    }
    private void chooseNumCards() {
    	numCards=25; //temp
    	//TODO not sure best way to do this. perhaps small window with choice/combobox upon starting game that requests num
    }
    private void chooseCardSet() {//choose cards first
    	CardSets set=CardSets.SUPERHEROES;
    	cardSet=set.toCardSet(); //temp
    	//TODO see chooseNumCards
    }
}
