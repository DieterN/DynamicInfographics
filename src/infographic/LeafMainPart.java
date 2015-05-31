package infographic;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeafMainPart extends MainPart{

	private ExtraPart child;
	
	public LeafMainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY) {
		super(bimg, topLeftCornerX, topLeftCornerY);
	}

	public ExtraPart getChild() {
		return child;
	}

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
	public ExtraPart getChildOfPartAt(int width) {
		ExtraPart part = null;
		if(width <= super.getImageWidth())
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
