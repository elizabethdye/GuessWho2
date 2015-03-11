package Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;


public class CardSet {
	private ArrayList<Card> cards=new ArrayList<Card>();
	private String picFolder="Pictures";
	private String folderName;
	
	public CardSet(String setName) {
		this.folderName=setName;
		try {initiate();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public Card getCard(int index) {
		return cards.get(index);
	}
	public void randomize() {
		Collections.shuffle(cards);
	}
	
	private BufferedImage readImage(File file) throws IOException {
		return ImageIO.read(file);
	}
	private void initiate() throws IOException {
		File[] files=listFiles();
		for (File file: files) {
			addFile(file);
		}
	}
	private void addFile(File file) throws IOException {	
		cards.add(new Card(file.getName(), readImage(file)));
	}
	private File[] listFiles() {
		return new File(picFolder+"/"+folderName).listFiles();
	}
}