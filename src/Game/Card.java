package Game;
import java.awt.image.BufferedImage;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.Node;


public class Card extends Node {
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
	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0,
			MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
