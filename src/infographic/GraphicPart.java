package infographic;

import java.awt.image.BufferedImage;

public abstract class GraphicPart{

	private BufferedImage bimg; //link to image of this part
	
	public GraphicPart(BufferedImage bimg){
		this.bimg = bimg;
	}

	public BufferedImage getBimg() {
		return bimg;
	}

	public void setBimg(BufferedImage bimg) {
		this.bimg = bimg;
	}

	public int getType() {
		return this.bimg.getType();
	}
	
	public int getImageWidth(){
		return this.bimg.getWidth();
	}
	
	public int getImageHeight(){
		return this.bimg.getHeight();
	}
	
}
