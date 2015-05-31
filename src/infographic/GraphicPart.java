package infographic;

import java.awt.image.BufferedImage;

/**
 * Abstract class representing graphic parts (= main- or extra parts).
 * 
 * @author Dieter
 *
 */
public abstract class GraphicPart{

	private BufferedImage bimg; //link to image of this part
	
	/**
	 * Constructor for a graphic part
	 * 
	 * @param bimg: image to be shown in this graphic part
	 */
	public GraphicPart(BufferedImage bimg){
		this.bimg = bimg;
	}
	
	/**
	 * Gets the bimg.
	 *
	 * @return the bimg
	 */
	public BufferedImage getBimg() {
		return bimg;
	}

	/**
	 * Sets the BufferedImage.
	 *
	 * @param bimg: the new BufferedImage
	 */
	public void setBimg(BufferedImage bimg) {
		this.bimg = bimg;
	}

	/**
	 * Gets the type of the BufferedImage.
	 *
	 * @return the type of the BufferedImage
	 */
	public int getType() {
		return this.bimg.getType();
	}
	
	/**
	 * Gets the image width.
	 *
	 * @return the image width
	 */
	public int getImageWidth(){
		return this.bimg.getWidth();
	}
	
	/**
	 * Gets the image height.
	 *
	 * @return the image height
	 */
	public int getImageHeight(){
		return this.bimg.getHeight();
	}
	
}
