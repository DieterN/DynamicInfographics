package gui;

import infographic.ExtraPart;
import infographic.Infographic;
import infographic.LeafMainPart;
import infographic.MainPart;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.TimerTask;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import main.Statics;

public class GraphPanel extends UnscalablePanel implements FadeOutCallback{
	
	private static final long serialVersionUID = 1L;
	
	private float stroke = 0.5f;
	
	private Infographic infographic;
	private JPanel mainParts;
	private ButtonPanel buttonPanel;
	private UnscalablePanel extraParts;

	private boolean waitForFadeOut;
	private UUID fadeOutWaitId;

	private Rectangle highlightRectangle;

	private Timer highlightTimer;
	
	public GraphPanel(Infographic infographic) {
		this.infographic = infographic; 
		
		this.mainParts = new JPanel();
		this.buttonPanel = new ButtonPanel(infographic, this);
		this.extraParts = new UnscalablePanel();
		
		mainParts.setAlignmentY(TOP_ALIGNMENT);
		mainParts.setLayout(new BoxLayout(mainParts, BoxLayout.Y_AXIS));
		
		extraParts.setAlignmentY(TOP_ALIGNMENT);
		extraParts.setLayout(new BoxLayout(extraParts, BoxLayout.Y_AXIS));

		add(mainParts);
		add(buttonPanel);
		add(extraParts);
		
		initializeInfographic(infographic);
		
	}

	private void initializeInfographic(Infographic infographic) {
		int extraWidth = 0;
		//Add main, button and extra part panels to the graphPanel
		for(MainPart part : infographic.getMainParts()){
			BufferedImage bimg = part.getBimg();
			GraphicPartComponent component = new GraphicPartComponent(bimg, 1.0f, false, false, null);
			component.setSizeTo(new Dimension(bimg.getWidth(),bimg.getHeight()));
			mainParts.add(component);
			
			ExtraPart widestChild = part.getWidestChild();
			
			if(widestChild != null){
				BufferedImage bimg2 = widestChild.getBimg();
				extraWidth = Math.max(extraWidth, bimg2.getWidth());
			}
		}
		
		//Set extrapart size according to mainpart size and width of extra parts
		extraParts.setSizeTo(new Dimension(extraWidth,infographic.getInfographicHeight()));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		drawRectangle();
	}
	
	/**
	 * Call when the extra frame at a given height has to be shown
	 * 
	 * @param pointerHeight
	 */
	public void showExtraPart(UUID id) {
		if(Statics.partId != id){
			if(Statics.partId != null){
				if(id != null){
					waitForFadeOut = true;
					fadeOutWaitId = id;
				}
				fadeOutExtraPart();
			}
			
			else if(id != null){
				continueShowExtraPart(id);
			}
		}
	}
	
	private void continueShowExtraPart(UUID id) {
		boolean entered = drawExtraPartWithIDAndWithGivenFading(id, 0.0f, true, false, null);
		
		//Update shown extraPartId
		if(entered){
			Statics.partId = id;
		}
		else{
			Statics.partId = null;
		}	
	}

	public void fadeOutExtraPart() {
		UUID id = Statics.partId;
		if(id == null){
			return;
		}
		Statics.partId = null;
		buttonPanel.setButtonVisible(id);
		drawExtraPartWithIDAndWithGivenFading(id, 1.0f, false, true, this);	
	}

	@Override
	public void fadeOutFinished() {
		if(waitForFadeOut){
			continueShowExtraPart(fadeOutWaitId);
		}
		
		waitForFadeOut = false;
	}	

	private boolean drawExtraPartWithIDAndWithGivenFading(UUID id, float alpha, boolean fadeIn, boolean fadeOut, FadeOutCallback callback) {
		clearExtraParts();
		Dimension d = new Dimension();
		boolean childExists = false;
		LeafMainPart mainPart = (LeafMainPart) infographic.getMainPartWithId(id);
		int y = mainPart.getTopLeftCornerY();
		ExtraPart child = mainPart.getChild(); //MainPart should be leaf
		
		if(child != null){
			d.setSize(d.getWidth(), y);
			childExists = true;
			BufferedImage bimg = child.getBimg();
			GraphicPartComponent component = new GraphicPartComponent(bimg, alpha, fadeIn, fadeOut, callback);
			component.setMaximumSize(new Dimension(bimg.getWidth(),bimg.getHeight()));
			component.setMinimumSize(new Dimension(bimg.getWidth(),bimg.getHeight()));
			extraParts.add(Box.createRigidArea(d));
			extraParts.add(component);
			d = new Dimension();
			d.setSize(d.getWidth(),infographic.getInfographicHeight() - (y+bimg.getHeight()));
			highlight(mainPart, fadeIn, fadeOut);
		}
		else{
			d.setSize(d.getWidth(),infographic.getInfographicHeight());
		}
		
		extraParts.add(Box.createRigidArea(d));
		extraParts.revalidate();
		extraParts.repaint();
		
		return childExists;
	}
	
	
	private void highlight(LeafMainPart mainPart, boolean fadeIn, boolean fadeOut) {
		if(fadeIn){
			highLightMainPart(mainPart.getTopLeftCornerX(), mainPart.getTopLeftCornerY(), mainPart.getImageWidth(), mainPart.getImageHeight());
		}
		else if(fadeOut){
			withdrawHighlight();
		}
		
	}

	public void highLightMainPart(int leftTopCornerX, int leftTopCornerY, int width, int height){
		withdrawHighlight();
		highlightRectangle = new Rectangle(leftTopCornerX, leftTopCornerY, width, height);
		
		highlightTimer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stroke < 3){
					drawRectangle();
					stroke += 0.2f;
				}
				else{
					stroke = 3.0f;
					highlightTimer.stop();
				}
			}
		});
		highlightTimer.start();
	}
	
	private void drawRectangle() {
		if(highlightRectangle != null){
			Graphics2D g2d = (Graphics2D) getGraphics();
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(highlightRectangle);
		}
	}
	
	public void withdrawHighlight(){
		mainParts.repaint();
		stroke = 0.5f;
	}
	
	public void clearExtraParts(){
		extraParts.removeAll();
		extraParts.revalidate();
		extraParts.repaint();
	}

	public Component getMainParts() {
		return this.mainParts;
	}
	
	public void resetHighlightRectangle(){
		this.highlightRectangle = null;
	}
	
}
