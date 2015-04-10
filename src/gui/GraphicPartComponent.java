package gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

/**
 * To draw on the screen, it is first necessary to subclass a Component 
 * and override its paint() method. The paint() method is automatically called 
 * by the windowing system whenever component's area needs to be repainted.
 */
public class GraphicPartComponent extends UnscalableComponent{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage bimg;
	private float alpha;
	private Timer fadeInTimer;
	private Timer fadeOutTimer;
	private boolean fadeIn;
	private boolean fadeOut;

	public GraphicPartComponent(BufferedImage bimg, float alpha, boolean fadeIn, boolean fadeOut, final FadeOutCallback callback){
		if(fadeOut && callback == null)
			throw new IllegalArgumentException("FadeOutCallback needed when creating a fade out component!");
		
		this.bimg = bimg;
		this.alpha = alpha;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.fadeInTimer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fadeIn();
			}
		});
		this.fadeOutTimer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fadeOut(callback);
			}
		});
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(bimg, 0, 0, getWidth(), getHeight(), null);
		if(fadeIn & alpha < 1.0f){
			fadeInTimer.start();
		}
		else if(fadeOut & alpha > 0.0f){
			fadeOutTimer.start();
		}
	}

	public void fadeIn() {
		alpha += 0.05f;
        if (alpha >1) {
            alpha = 1.0f;
            fadeInTimer.stop();
        }
        repaint();		
	}

	public void fadeOut(FadeOutCallback callback) {
		alpha -= 0.05f;
        if (alpha < 0) {
            alpha = 0.0f;
            fadeOutTimer.stop();
            callback.fadeOutFinished();
        }
        repaint();		
	}
	  
	public BufferedImage getBimg() {
		return bimg;
	}

	public void setBimg(BufferedImage bimg) {
		this.bimg = bimg;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}