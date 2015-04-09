package infographic;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

public abstract class MainPart extends GraphicPart{

	protected int topLeftCornerX; //absolute coordinates -> kader breedte/hoogte moeten al afgetrokken zijn
	protected int topLeftCornerY;
	protected UUID id;
	
	public MainPart(BufferedImage bimg, int topLeftCornerX, int topLeftCornerY) {
		super(bimg);
		this.topLeftCornerX = topLeftCornerX;
		this.topLeftCornerY = topLeftCornerY;
		this.id = UUID.randomUUID();
	}
	
	public int getTopLeftCornerX() {
		return topLeftCornerX;
	}

	public void setTopLeftCornerX(int topLeftCornerX) {
		this.topLeftCornerX = topLeftCornerX;
	}

	public int getTopLeftCornerY() {
		return topLeftCornerY;
	}

	public void setTopLeftCornerY(int topLeftCornerY) {
		this.topLeftCornerY = topLeftCornerY;
	}

	public UUID getId() {
		return id;
	}

	public abstract ExtraPart getChildOfPartAt(int width);
	
	public abstract ExtraPart getHighestChild();
	
	public abstract ExtraPart getWidestChild();

	public abstract UUID getIdOfMainPartAt(double width);

	public abstract Map<UUID, MainPart> getMapWithSelfAndChildren();
	
}
