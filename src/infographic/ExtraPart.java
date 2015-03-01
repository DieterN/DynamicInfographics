package infographic;

import java.awt.image.BufferedImage;

public class ExtraPart extends GraphicPart{

	private MainPart parent;
	
	public ExtraPart(BufferedImage bimg, MainPart parent){
		super(bimg);
		this.parent = parent;
	}
	
	public ExtraPart(BufferedImage bimg){
		super(bimg);
	}

	public MainPart getParent() {
		return parent;
	}

	public void setParent(MainPart parent) {
		this.parent = parent;
	}
	
	
}
