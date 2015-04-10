package infographic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class CompositeMainPart extends MainPart{

	private TreeSet<MainPart> subParts = new TreeSet<MainPart>();
	
	public CompositeMainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY) {
		super(bimg, topLeftCornerX, topLeftCornerY);
	}

	public TreeSet<MainPart> getSubParts() {
		return subParts;
	}

	public void setSubParts(TreeSet<MainPart> subParts) {
		this.subParts = subParts;
	}

	@Override
	public ExtraPart getHighestChild() {
		ExtraPart heighestChild = null;
		int height = 0;
		
		for(MainPart subPart: subParts){
			ExtraPart child = subPart.getHighestChild();
			int childHeight = child.getImageHeight();
			if(childHeight > height){
				heighestChild = child;
				height = childHeight;
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
			int childWidth = child.getImageWidth();
			if(childWidth > width){
				widestChild = child;
				width = childWidth;
			}
		}
		return widestChild;
	}

	@Override
	public ExtraPart getChildOfPartAt(int width) {
		ExtraPart part = null;
		for(MainPart subPart: subParts){
			int subPartTopLeftCornerX = subPart.getImageWidth();
			if((subPartTopLeftCornerX <= width) && ((subPart.getImageWidth() + subPartTopLeftCornerX) > width)){
				part = subPart.getChildOfPartAt(width);
				break;
			}
		}
		return part;
	}

	@Override
	public UUID getIdOfMainPartAt(double width) {
		UUID partId = null;
		for(MainPart subPart: subParts){
			int subPartTopLeftCornerX = subPart.getImageWidth();
			if((subPartTopLeftCornerX <= width) && ((subPart.getImageWidth() + subPartTopLeftCornerX) > width)){
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


}
