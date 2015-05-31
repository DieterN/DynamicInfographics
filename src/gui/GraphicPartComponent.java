package gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

/**
 * Component class for GraphicParts (Main- or Extraparts)
 */
public class GraphicPartComponent extends UnscalableComponent{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage bimg;
	private float alpha;
	private Timer fadeInTimer;
	private Timer fadeOutTimer;
	private boolean fadeIn;
	private boolean fadeOut;

	/**
	 * Component that contains a main- or extra graphic part.
	 * 
	 * @param bimg: image to be shown in the component
	 * @param alpha: alpha value for the image
	 * @param fadeIn: should the image be faded in?
	 * @param fadeOut: should the image be faded out?
	 * @param callback: callback that has to be called when fading out is finished
	 */
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

	@Override
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

	/**
	 * Call this method to fade the extra part in by 0.05f
	 */
	public void fadeIn() {
		alpha += 0.05f;
        if (alpha >1) {
            alpha = 1.0f;
            fadeInTimer.stop();
        }
        repaint();		
	}


	/**
	 * Call this method to fade the extra part out by 0.05f
	 * If fading out is done (alpha = 0), the provided callback is called.
	 * 
	 * @param callback: called when fading out is done
	 */
	public void fadeOut(FadeOutCallback callback) {
		alpha -= 0.05f;
        if (alpha < 0) {
            alpha = 0.0f;
            fadeOutTimer.stop();
            callback.fadeOutFinished();
        }
        repaint();		
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
	 * Sets the bimg.
	 *
	 * @param bimg the new bimg
	 */
	public void setBimg(BufferedImage bimg) {
		this.bimg = bimg;
	}

	/**
	 * Gets the alpha.
	 *
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha.
	 *
	 * @param alpha the new alpha
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}