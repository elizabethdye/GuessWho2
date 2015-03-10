package GUI;

import java.awt.image.BufferedImage;

import Game.Card;
import Game.CardSet;
import Game.CardSets;
import Game.Deck;
import Game.Game;
import Game.Player;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    GridPane imageGrid;
    @FXML
    Button heart;
    @FXML
    Button crossOut;
    @FXML
    Button guess;
    @FXML
    TextArea conversation;
    @FXML
    ImageView profile;
    @FXML
    Label whoseTurn;
    @FXML
    TextArea inputText;
    @FXML
    CheckBox isQuestion;
    @FXML
    Button yes;
    @FXML
    Button no;
    
    private Game game;
    private int numCards;
    private CardSet cardSet;
    private Deck deck;
	private Card[][] cardGrid;
	private Player player;
    
	NetworkManager manager = NetworkManager.getInstance();
    
	@FXML
    void initialize(){
        String ip = manager.getLocalIP();
        conversation.appendText("Your IP: " + manager.getLocalIP() + '\n');
        manager.setDisplay(conversation);
        updateUI runner = new updateUI(conversation);
        Platform.runLater(runner);
        startGame();
        setUpGrid();
    }
    void presentData(){
        //called to display the most recently received event.
        try {
            NetworkCommunication data = manager.getLatest(); // I've modified this to take from the Network API -- John
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void isCorrectGuess(){
    	//TODO: if myGuess.equals() other person's card: return yes ~or~ if mycard.equals() other person's guess
    }
    @FXML
    void sendTestPackets() {
        String ip = inputText.getText();
        if (ip.length() > 0) {
            NetworkManager.getInstance().test(ip);
        }
    }

    @FXML
    void loadFromQueue() {
        try {
            conversation.appendText(manager.getLatest().toString() + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendMessage(){
        String input = inputText.getText();
        String[] items = input.split("\n");
        String ip = items[0];
        String message = "";
        for (int i = 1; i < items.length; ++i){
            message += items[i] + "\n";
        }
        NetworkCommunication comm = new NetworkCommunication(Message.TEXT, message);

        manager.openConnection(ip, 8888);
        
        manager.sendMessage(comm);
    }
    @FXML
    void yes() {
    	inputText.appendText("Yes");
    }
    @FXML
    void no() {
    	inputText.appendText("No");
    }
    private void startGame() {
    	deck=new Deck(numCards, cardSet);
    	game=new Game(deck,numCards);
    }
    private void chooseNumCards() {
    	numCards=25; //temp
    }
    private void chooseCardSet() {//choose cards first
    	CardSets set=CardSets.EMOJIS;//temp
    	cardSet=set.toCardSet();
    	//TODO see chooseNumCards
    }
    private boolean isEditable() {
    	return game.isEditable();
    }
    
    private Node findNodeSelected() {
    	ObservableList<Node> cards = imageGrid.getChildren();
    	for(Node card: cards) {
    		if(card.isFocused()) {
    			return card;
    		}
    	}
    	return null;
    }
    
    private int findRowSelected(Node node) {
    	return imageGrid.getRowIndex(node);
    }
    
    private int findColumnSelected(Node node) {
    	return imageGrid.getColumnIndex(node);
    }
    
    @FXML
    private void guess() {
    	Node guessed = findNodeSelected();
    	int row = findRowSelected(guessed);
    	int col = findColumnSelected(guessed);
    	if(guessed.equals(null)) {
    		return;
    	}
    	if(game.p1Turn()) {
    		game.p1Guess(cardGrid[row][col]);
    	} else {
    		game.p2Guess((Card)guessed);
    	}
    }

    private void setUpGrid() {
    	Card tempCard;
		int width=(int) Math.sqrt(numCards);
		cardGrid=new Card[width][width];
		for (int idx=0; idx<width; idx++) {
			for (int idy=0; idy<width; idy++) {
				tempCard=deck.getCard(idx*width+idy);
				cardGrid[idx][idy]=tempCard;
				imageGrid.add(tempCard, idx, idy);
			}
		}
    }

    public class updateUI implements Runnable{

        TextArea conv;

        public updateUI(TextArea conv){
            this.conv = conv;
        }
        public void run(){
            try {
                while(true){
                    NetworkCommunication comm = manager.getLatest();
                    if (comm.type == Message.TEXT){
                        this.conv.appendText(comm.data + "\n");
                    }
                    //todo: fill out the rest of these
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void favorite() {
    	//TODO
    	//Draw heart over selected node
    	Node selected = findNodeSelected();
    	int row = findRowSelected(selected);
    	int col = findColumnSelected(selected);
    }
    
    @FXML
    public void crossOut() {
    	//TODO Draw "X" over selected node
    	Node selected = findNodeSelected();
    	int row = findRowSelected(selected);
    	int col = findColumnSelected(selected);
    }
    
    private void insertProfilePic() {
    	BufferedImage playerImage = player.getCard().getImage();
    	Image image = SwingFXUtils.toFXImage(playerImage, null);
    	profile.setImage(image);
    }

}
