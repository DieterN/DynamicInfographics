package infographic;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

/**
 * Abstract superclass for Composite- and LeafMainParts
 * 
 * @author Dieter
 *
 */
public abstract class MainPart extends GraphicPart implements Comparable<MainPart>{

	protected int topLeftCornerX; //absolute coordinates -> kader breedte/hoogte moeten al afgetrokken zijn
	protected int topLeftCornerY;
	protected UUID id;
	
	/**
	 * Constructor for a general MainPart
	 * 
	 * @param bimg: image of this MainPart
	 * @param topLeftCornerX: x-coordinate of top left corner of this MainPart
	 * @param topLeftCornerY: y-coordinate of top left corner of this MainPart
	 */
	public MainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY) {
		super(bimg);
		this.topLeftCornerX = topLeftCornerX;
		this.topLeftCornerY = topLeftCornerY;
		this.id = UUID.randomUUID();
	}
	
	/**
	 * Gets the top left corner x.
	 *
	 * @return the top left corner x
	 */
	public int getTopLeftCornerX() {
		return topLeftCornerX;
	}

	/**
	 * Sets the top left corner x.
	 *
	 * @param topLeftCornerX: the new top left corner x
	 */
	public void setTopLeftCornerX(int topLeftCornerX) {
		this.topLeftCornerX = topLeftCornerX;
	}

	/**
	 * Gets the top left corner y.
	 *
	 * @return the top left corner y
	 */
	public int getTopLeftCornerY() {
		return topLeftCornerY;
	}

	/**
	 * Sets the top left corner y.
	 *
	 * @param topLeftCornerY: the new top left corner y
	 */
	public void setTopLeftCornerY(int topLeftCornerY) {
		this.topLeftCornerY = topLeftCornerY;
	}

	/**
	 * Get the ID of this MainPart.
	 *
	 * @return the ID of this MainPart
	 */
	public UUID getId() {
		return id;
	}
	
	@Override
	public int compareTo(MainPart part){
		int answer = 0;
		if(this.topLeftCornerY < part.getTopLeftCornerY())
			answer = -1;
		else if(this.topLeftCornerY > part.getTopLeftCornerY())
			answer = 1;
		else if(this.topLeftCornerX < part.getTopLeftCornerX())
			answer = -1;
		else if(this.topLeftCornerX > part.getTopLeftCornerX())
			answer = 1;
		return answer;
	}

	/**
	 * Return the child ExtraPart of the MainPart at the given x-coordinate.
	 * 
	 * @param x: x-coordinate of the searched MainPart
	 * @return child ExtraPart of the MainPart at the given x-coordinate
	 */
	public abstract ExtraPart getChildOfPartAt(int x);
	
	/**
	 * Return the highest child (= ExtraPart) of this MainPart.
	 *
	 * @return the highest child (= ExtraPart)
	 */
	public abstract ExtraPart getHighestChild();
	
	/**
	 * Return the widest child (= ExtraPart) of this MainPart.
	 *
	 * @return the widest child (= ExtraPart)
	 */
	public abstract ExtraPart getWidestChild();

	/**
	 * Return the ID of the MainPart at the given x-coordinate.
	 * 
	 * @param x: x-coordinate of the searched MainPart ID
	 * @return ID of the MainPart at the given x-coordinate
	 */
	public abstract UUID getIdOfMainPartAt(double x);

	/**
	 * Make and return a map with the current MainPart and all of it's sub MainParts.
	 *
	 * @return map with the current MainPart and all of it's sub MainParts
	 */
	public abstract Map<UUID, MainPart> getMapWithSelfAndChildren();
	
	/**
	 * Method needed for implementing the visitor pattern.
	 * 
	 * @param visitor: visitor that should be called with visitor.visit(this) to complete the Visitor pattern
	 */
	public abstract void accept(MainPartVisitor visitor);
}
