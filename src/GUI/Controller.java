package GUI;

import Game.*;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
	@FXML
	TextField ipAddress;
	
	@FXML
	ChoiceBox<String> card_Set;
	
	@FXML
	ChoiceBox<String> num_Cards;
	
	@FXML
	Button startGame;
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
    
    private CardSet cardSet;
    private Deck deck;
	private Card[][] cardGrid;
	private Player player;
	private final int ipNum=8888;
    public boolean gameStarted = false;
    
	NetworkManager manager = NetworkManager.getInstance();

    void presentData(){
        //called to display the most recently received event.
        try {
            NetworkCommunication data = manager.getLatest(); // I've modified this to take from the Network API -- John
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        String message = inputText.getText();
        NetworkCommunication comm = new NetworkCommunication(Message.TEXT, message);

        if (gameStarted){
            manager.sendMessage(comm);
            conversation.appendText("ME: " + message + "\n");
            inputText.setText("");
        }
        else {
            conversation.appendText("The game isn't started!  Please start the game to send a message.");
        }
    }
    @FXML
    void initialize(){
        String ip = manager.getLocalIP();
        conversation.appendText("Your IP: " + manager.getLocalIP() + '\n');
        manager.setDisplay(conversation);
        addCardChoices();
        addNumCardChoices();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (manager.numItems() > 0){
                    Platform.runLater(() -> {
                        try {
                            NetworkCommunication comm = manager.getLatest();
                            handleCommand(comm);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                }
            }
        }, 1000, 800);
    }

    public void handleCommand(NetworkCommunication communication) {
        if (shouldPrint(communication)) {
            conversation.appendText("Other Player: " + communication.data);
        }
        else if (communication.type == Message.GUESS){
            //todo: run code to provide a guess to the game manager
        }
        else if (communication.type == Message.AUTODISCOVER){
            manager.openConnection(communication.data, 8888);
            NetworkCommunication comm = new NetworkCommunication(Message.AUTODISCOVER, manager.getLocalIP());
        }

        //todo: Finish the rest of the commands.
    }
    private boolean shouldPrint(NetworkCommunication comm){
        return comm.type == Message.TEXT || comm.type == Message.RESPONSE || comm.type == Message.QUESTION;
    }

    @FXML
	void addCardChoices(){
		card_Set.getItems().addAll("Emojis","Superheros");
		card_Set.setValue("Emojis");
	}
	
	@FXML
	void addNumCardChoices(){
		num_Cards.getItems().addAll("9","16","25");
		num_Cards.setValue("16");
	}
	@FXML
	void setIPAddress(){
		manager.IPAddress = ipAddress.getText();
	}
	
	private void cardSetChoice(){
		manager.cardSetName = card_Set.getValue().toString().toUpperCase();
		cardSet = CardSets.valueOf(manager.cardSetName).toCardSet();
	}
	private void numCardChoice(){
		manager.numCards = Integer.valueOf(num_Cards.getValue().toString());
	}
	@FXML
	void startNewGame(){
        if (imageGrid.getChildren().size() > 0){
            imageGrid.getChildren().remove(0, imageGrid.getChildren().size());
        }
        if (ipAddress.getText().length() <= 0){
            manager.reportError("You need to specify an IP to connect to!");
            System.out.println("You need to specify an IP to connect to!");
            return;
        }
		setIPAddress();
		cardSetChoice();
		numCardChoice();
		startGame();

        manager.openConnection(ipAddress.getText(), ipNum);
        gameStarted = true;
	}
    @FXML
    private void yes() {
    	inputText.appendText("Yes");

        NetworkCommunication comm = new NetworkCommunication(Message.RESPONSE, "Yes!");
        manager.sendMessage(comm);

    	game.turn();
    }
    @FXML
    private void no() {
    	inputText.appendText("No");

        NetworkCommunication comm = new NetworkCommunication(Message.RESPONSE, "No!");
        manager.sendMessage(comm);

    	game.turn();
    }
    @FXML
    public void favorite() {
    	//TODO
    	//Draw heart over selected node
    	if (isEditable()) {
    		Node selected = findNodeSelected();
    		int row = findRowSelected(selected);
    		int col = findColumnSelected(selected);
    	}
    }
    @FXML
    public void crossOut() {
    	//TODO Draw "X" over selected node
    	if (isEditable()) {
    		Node selected = findNodeSelected();
    		int row = findRowSelected(selected);
    		int col = findColumnSelected(selected);
    	}
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
    
    private void startGame() {
    	deck=new Deck(manager.numCards, cardSet);
    	game=new Game(deck,manager.numCards);
        setUpGrid();
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
    @SuppressWarnings("static-access")
	private void setUpGrid() {
    	Card tempCard;
    	BufferedImage bufImage;
    	Image image;
		int width=(int) Math.sqrt(manager.numCards);
		cardGrid=new Card[width][width];
		
		//imageGrid = new GridPane();
		imageGrid.setGridLinesVisible(true);
		for (int idx=0; idx<width; idx++) {
			for (int idy=0; idy<width; idy++) {
				tempCard=deck.getCard(idx*width+idy);
				cardGrid[idx][idy]=tempCard;
				bufImage=tempCard.getImage();
				image=convertBufferedToImage(bufImage);
				ImageView imgView = new ImageView(image);
				imgView.setFitWidth(imageGrid.getWidth() / width);
				imgView.setFitHeight(imageGrid.getWidth() / width);
				imageGrid.setColumnIndex(imgView, idx);
				imageGrid.setRowIndex(imgView, idy);
				imageGrid.setRowSpan(imgView, 1);
				imageGrid.setColumnSpan(imgView, 1);
				imageGrid.addRow(idy, imgView);
				//imageGrid.add(imgView, idy, idx);
				//set the image to a row, column within the grid, so a specific grid spot
				//I believe there is a method to set an image within a grid like setColumn, setRow
			}
		}
    }
    private void insertProfilePic() {
    	BufferedImage playerImage = player.getCard().getImage();
    	Image image = convertBufferedToImage(playerImage);
    	profile.setImage(image);
    }
    private Image convertBufferedToImage(BufferedImage image) {
    	return SwingFXUtils.toFXImage(image, null);
    }
}
