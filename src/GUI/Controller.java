package GUI;

import Game.*;
import Network.Message;
import Network.NetworkCommunication;
import Network.NetworkManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
	
    @FXML
    GridPane imageGrid;
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
    private Point2D selected;
	private final int port=8888;
    public boolean gameStarted = false;
	private NetworkManager manager = NetworkManager.getInstance();

    @FXML
    private void initialize(){
        String ip = manager.getLocalIP();
        changeDefaultSettings();
        conversation.appendText("Your IP: " + ip + '\n');
        manager.setDisplay(conversation);
        Timer t = new Timer();
        t.schedule(makeTimerTask(), 1000, 800);
        startAutoDiscover();
        startNewGame();
    }

	void startNewGame(){
		if (checkIP()) {
			clearImageGrid();
//			setIPAddress();
//			chooseCardSet();
//			chooseNumCards();
			startGame();
<<<<<<< HEAD
	        manager.openConnection(ipAddress.getText(), port);
	        gameStarted = true;  
            if (game.userTurn()){
                userTurn();
            }
            else {
                otherTurn();
            }
=======
	        manager.openConnection(manager.IPAddress, port);
	        gameStarted = true;
>>>>>>> SorryTemp
		}
	}
	
    @FXML
    private void yes() {
    	response("Yes!");
    }
    
    @FXML
    private void no() {
    	response("No!");
    }

    
    @FXML
    private void guess() {//TODO this probably is not right
        Node guessed = findNodeSelected();
        int row = findRowSelected(guessed);
    	int col = findColumnSelected(guessed);
    	if(guessed.equals(null)) {
    		return;
    	}
    	if(game.p1Turn()) {
    		game.p1Guess(cardGrid[row][col]);
    	} else {
    		//game.p2Guess((Card)guessed);//TODO
    	}
    }
    
    public void handleCommand(NetworkCommunication communication) {
        if (shouldPrint(communication)) {
            conversation.appendText("Other Player: " + communication.data);
        }
        else if (communication.type == Message.GUESS){
            //TODO: run code to provide a guess to the game manager
        }
        else if (communication.type == Message.AUTODISCOVER){
            manager.openConnection(communication.data, 8888);
            NetworkCommunication comm = new NetworkCommunication(Message.AUTODISCOVER, manager.getLocalIP());
        }
        else if (communication.type == Message.ERROR){
            conversation.appendText("ERROR: " + communication.data);
        }
<<<<<<< HEAD
        else if (communication.type == Message.RESPONSE){
            String response = communication.data.replace("\n", "");
            if (response.equals(Game.GUESS_RIGHT)){
            	victory();
            }
            else if (response.equals(Game.GUESS_WRONG)){
            	wrongGuess();
            }
            else {
                manager.reportError("Couldn't interpret the response from server: " + communication.data);
            }
        }
        else if (communication.type == Message.INITINFO){
            String[] info = communication.data.split(":");
            if (info[0].equals("TURN")){
                setTurnFromString(info[1]);
            }
        }
    }

    void setTurnFromString(String s){
        if (s.equals("true")){
            game.setTurn(true);
        }
        else {
            game.setTurn(false);
        }
    }

    boolean decideTurn(){
        Random rand = new Random();
        return rand.nextInt() % 2 == 1;
    }
    private void wrongGuess() {
        game.wrongGuess();
        conversation.appendText("WRONG... How Could you?\n");
    }
    private void victory() {
    	conversation.appendText("VICTORY!\n");
    }
    void handleGuess(NetworkCommunication communication){
        boolean isCorrect = game.checkGuess(communication.data);
        NetworkCommunication comm;
        if (isCorrect){
            comm = new NetworkCommunication(Message.RESPONSE, Game.GUESS_RIGHT);
        }
        else {
            comm = new NetworkCommunication(Message.RESPONSE, Game.GUESS_WRONG);
        }
        manager.sendMessage(comm);
=======
>>>>>>> SorryTemp
    }
    
    private void changeDefaultSettings() {
        inputText.setWrapText(true);
        conversation.setWrapText(true);
        isQuestion.setVisible(false);
//        setUpCardSet();
//        setUpNumCards();
    }
    
    private void startGame() {
    	deck=new Deck(CardSets.valueOf(manager.cardSetName).toCardSet(), manager.numCards);
    	game=new Game(deck);
        setUpGrid();
        insertProfilePic();

    }
    
    private void insertProfilePic() {
    	Card playerCard = game.getPlayer1().getCard();//TODO fix for each gui
    	Image image = getImageFromCard(playerCard);
    	profile.setImage(image);
    }
    
	private boolean checkIP() {
        if (manager.IPAddress.length() <= 0){
            manager.reportError("You need to specify an IP to connect to!");
            System.out.println("You need to specify an IP to connect to!");
            return false;
        }
        return true;
	}
	
//	private void setIPAddress(){
//		manager.IPAddress = ipAddress.getText();
//	}
	
//	private void chooseCardSet(){
//		manager.cardSetName = getString(cardSetBox).toUpperCase();
//		cardSet = CardSets.valueOf(manager.cardSetName).toCardSet();
//	}
	
//	private void chooseNumCards(){
//		manager.numCards = Integer.valueOf(getString(numCardsBox));
//	}
	
	private void clearImageGrid() {
		 if (imageGrid.getChildren().size() > 0){
	            imageGrid.getChildren().remove(0, imageGrid.getChildren().size());
	     }
	}
	
    private void response(String message) {
    	inputText.appendText(message);
        NetworkCommunication comm = new NetworkCommunication(Message.RESPONSE, message);
        manager.sendMessage(comm);
    }

    @FXML
    public void sendMessage(){
        String text = inputText.getText();
        NetworkCommunication comm = new NetworkCommunication(Message.TEXT, text);
        manager.sendMessage(comm);
    }
    
    private void presentData(){
        //called to display the most recently received event.
        try {
			NetworkCommunication data = manager.getLatest(); // I've modified this to take from the Network API -- John
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    private TimerTask makeTimerTask() {
    	return new TimerTask() {
            @Override
            public void run() {
                if (manager.numItems() > 0){
                    Platform.runLater(() -> {
                        try {
                            NetworkCommunication comm = manager.getLatest();
                            handleCommand(comm);
                        } catch (InterruptedException e) {
                            conversation.appendText("ERROR: " + e.toString());
                        }
                    });
                }
            }
        };
    }


    public void startAutoDiscover(){
        ArrayList<String> strings = manager.potentialNetworkPartners();
        for (String ip : strings){
            manager.sendAutoDiscover(ip);
        }
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
    
    private void setUpGrid() {
    	int width=(int) Math.sqrt(manager.numCards);
		cardGrid=new Card[width][width];
		for (int row=0; row<width; row++) {
			for (int column=0; column<width; column++) {
				addToGrids(row,column, width);
			}
		}
    }
    
    private void addToGrids(int row, int column, int width) {
    	Card tempCard=deck.getCard(row*width+column);
		cardGrid[row][column]=tempCard;
		Image image=getImageFromCard(tempCard);
		ImageView imgView = new ImageView(image);
        double wide = imageGrid.getWidth() / width;
        double height = imageGrid.getHeight() / width;
        imgView.setFitHeight(height);
        imgView.setFitWidth(wide);


        final Button item = new Button();
        item.setGraphic(imgView);
        item.setMinSize(0, 0);
        item.setMaxSize(wide, height);
        item.setOnAction((e) -> {
            Main.Log("I'm Here! the selected item was (row, column): " + row + ", " + column);
            //todo: handle selection logic
        });

		imageGrid.add(item, row, column);
    }
    
    private Image getImageFromCard(Card card) {
		BufferedImage bufImage=card.getImage();
		return convertBufferedToImage(bufImage);
    }
    
    private Image convertBufferedToImage(BufferedImage image) {
    	return SwingFXUtils.toFXImage(image, null);
    }
    
	private String getString(ChoiceBox<String> box) {
		return box.getValue().toString();
	}
	
    private int findRowSelected(Node node) {
    	return GridPane.getRowIndex(node);
    }
    
    private int findColumnSelected(Node node) {
    	return GridPane.getColumnIndex(node);
    }
    
    private boolean shouldPrint(NetworkCommunication comm){
        return comm.type == Message.TEXT || comm.type == Message.RESPONSE || comm.type == Message.QUESTION;
    }
    
    private boolean isEditable() {
    	return game.isEditable();
    }
}