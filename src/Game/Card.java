package Game;
import java.awt.image.BufferedImage;


public class Card {
	private String name;
	private BufferedImage image;
	
	public Card(String name, BufferedImage image) {
		this.name=name;
		this.image=image;
	}
	public String getName() {
		return name;
	}
	public BufferedImage getImage() {
		return image;
	}
}
