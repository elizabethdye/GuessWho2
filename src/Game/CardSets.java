package Game;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class CardSets {
	private ArrayList<String> names;
	private HashMap<String, BufferedImage> images=new HashMap<String, BufferedImage>();
	
	public CardSets(int size) {
		names=new ArrayList<String>();
	}
	public String getName(int index) {
		return names.get(index);
	}
	public BufferedImage getImage(String name) {
		return images.get(name);
	}
	public void randomize() {
		Collections.shuffle(names);
	}
	//TODO add preset cardsets
	
	//TODO add ability to add custom sets
}
