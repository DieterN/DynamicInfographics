package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * To draw on the screen, it is first necessary to subclass a Component 
 * and override its paint() method. The paint() method is automatically called 
 * by the windowing system whenever component's area needs to be repainted.
 */
public class GraphicPartComponent extends UnscalableComponent{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage bimg;
	
	public GraphicPartComponent(BufferedImage bimg){
		this.bimg = bimg;
	}
	  
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bimg, 0, 0, getWidth(), getHeight(), null);
	}
}