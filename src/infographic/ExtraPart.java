package infographic;

import java.awt.image.BufferedImage;

/**
 * Class that represents an extra part that belongs to a certain mainpart.
 * 
 * @author Dieter
 */
public class ExtraPart extends GraphicPart{

	private MainPart parent;
	
	/**
	 * Constructor for an extra part.
	 * 
	 * @param bimg: 
	 * @param parent: mainpart whereto this extra part belongs
	 */
	public ExtraPart(BufferedImage bimg, MainPart parent){
		super(bimg);
		this.parent = parent;
	}
	
	/**
	 * Constructor for an extra part, that takes no parent.
	 * The parent should be set later on.
	 * 
	 * @param bimg:image to be shown on this extra part
	 */
	public ExtraPart(BufferedImage bimg){
		super(bimg);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public MainPart getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(MainPart parent) {
		this.parent = parent;
	}
	
	
}
