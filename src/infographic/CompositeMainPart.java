package infographic;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Class that represents a composite main part.
 * 
 * A composite main parts consists of other main parts (composites and/or extras).
 * 
 * @author Dieter
 *
 */
public class CompositeMainPart extends MainPart{

	private TreeSet<MainPart> subParts = new TreeSet<MainPart>();
	
	/**
	 * Constructor for a composite main part
	 * 
	 * @param bimg: image of the complete composite part
	 * @param topLeftCornerX: topLeft 
	 * @param topLeftCornerY
	 */
	public CompositeMainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY, TreeSet<MainPart> subParts) {
		super(bimg, topLeftCornerX, topLeftCornerY);
		this.subParts = subParts;
	}

	/**
	 * Gets the sub parts.
	 *
	 * @return the sub parts
	 */
	public TreeSet<MainPart> getSubParts() {
		return subParts;
	}

	/**
	 * Sets the sub parts.
	 *
	 * @param subParts: the new sub parts
	 */
	public void setSubParts(TreeSet<MainPart> subParts) {
		this.subParts = subParts;
	}

	@Override
	public ExtraPart getHighestChild() {
		ExtraPart heighestChild = null;
		int height = 0;
		
		for(MainPart subPart: subParts){
			ExtraPart child = subPart.getHighestChild();
			if(child != null){
				int childHeight = child.getImageHeight();
				if(childHeight > height){
					heighestChild = child;
					height = childHeight;
				}
			}
		}
		
		return heighestChild;
	}

	@Override
	public ExtraPart getWidestChild() {
		ExtraPart widestChild = null;
		int width = 0;
		
		for(MainPart subPart: subParts){
			ExtraPart child = subPart.getWidestChild();
			if(child != null){
				int childWidth = child.getImageWidth();
				if(childWidth > width){
					widestChild = child;
					width = childWidth;
				}
			}
		}
		return widestChild;
	}

	@Override
	public ExtraPart getChildOfPartAt(int x) {
		ExtraPart part = null;
		for(MainPart subPart: subParts){
			int subPartTopLeftCornerX = subPart.getImageWidth();
			if((subPartTopLeftCornerX <= x) && ((subPart.getImageWidth() + subPartTopLeftCornerX) > x)){
				part = subPart.getChildOfPartAt(x);
				break;
			}
		}
		return part;
	}

	@Override
	public UUID getIdOfMainPartAt(double x) {
		UUID partId = null;
		for(MainPart subPart: subParts){
			int subPartTopLeftCornerX = subPart.getTopLeftCornerX();
			if((subPartTopLeftCornerX <= x) && ((subPart.getImageWidth() + subPartTopLeftCornerX) > x)){
				partId = subPart.getId();
				break;
			}
		}
		return partId;
	}

	@Override
	public Map<UUID, MainPart> getMapWithSelfAndChildren() {
		Map<UUID, MainPart> map = new HashMap<UUID, MainPart>();
		map.put(id, this);
		for(MainPart child: subParts){
			map.putAll(child.getMapWithSelfAndChildren());
		}
		return map;
	}

	@Override
	public void accept(MainPartVisitor visitor) {
		visitor.visit(this);
	}


}
