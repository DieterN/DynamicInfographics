package infographic;

import java.awt.image.BufferedImage;

public class MainPart extends GraphicPart{

	private ExtraPart child;
	
	public MainPart(BufferedImage bimg, ExtraPart child){
		super(bimg);
		this.child = child;
	}
	
	public MainPart(BufferedImage bimg){
		super(bimg);
	}

	public ExtraPart getChild() {
		return child;
	}

	public void setChild(ExtraPart child) {
		this.child = child;
	}	
}
