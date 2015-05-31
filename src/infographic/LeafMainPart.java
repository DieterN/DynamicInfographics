package infographic;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Subclass of MainPart that represents a MainPart without sub MainParts. 
 */
public class LeafMainPart extends MainPart{

	private ExtraPart child;
	
	/**
	 * Constructor for a LeafMainPart.
	 * 
	 * @param bimg: image to be shown in this part
	 * @param topLeftCornerX: x-coordinate of the top left corner of this part
	 * @param topLeftCornerY: y-coordinate of the top left corner of this part
	 */
	public LeafMainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY) {
		super(bimg, topLeftCornerX, topLeftCornerY);
	}

	/**
	 * Get the child (= ExtraPart) of this MainPart, this is the part that should be shown when
	 * more information about this LeafMainPart is requested.
	 * 
	 * @return the child (= ExtraPart) of this MainPart
	 */
	public ExtraPart getChild() {
		return child;
	}

	/**
	 * Set the child to the specified child
	 * 
	 * @param child: the new child of this LeafMainPart
	 */
	public void setChild(ExtraPart child) {
		this.child = child;
	}

	@Override
	public ExtraPart getHighestChild() {
		return child;
	}

	@Override
	public ExtraPart getWidestChild() {
		return child;
	}

	@Override
	public ExtraPart getChildOfPartAt(int x) {
		ExtraPart part = null;
		if(x <= super.getImageWidth())
			part = this.child;		
			
		return part;
	}
	
	@Override
	public UUID getIdOfMainPartAt(double x) {
		UUID partID = null;
		if(x <= super.getImageWidth())
			partID = this.getId();		
			
		return partID;
	}

	@Override
	public Map<UUID, MainPart> getMapWithSelfAndChildren() {
		Map<UUID, MainPart> map = new HashMap<UUID, MainPart>();
		map.put(id, this);
		return map;
	}

	@Override
	public void accept(MainPartVisitor visitor) {
		visitor.visit(this);
	}
}
