package gui;

import infographic.ExtraPart;
import infographic.Infographic;
import infographic.LeafMainPart;
import infographic.MainPart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
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
	private Rectangle mouseOverRectangle;

	private Timer highlightTimer;
	
	public GraphPanel(Infographic infographic) {
		this.infographic = infographic; 

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
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
		
		drawInfographic(infographic);
	}

	public void endSession(){
		extraParts.removeAll();
		buttonPanel.endSession();

		resetHighlightRectangle();
		removeMouseOverRectangle();
		
		restartGraphPanel();
	}
	
	public void drawNewInfographic(Infographic infographic){
		mainParts.removeAll();
		extraParts.removeAll();
		
		drawInfographic(infographic);
		this.infographic = infographic;
		
		buttonPanel.drawNewInfographic(infographic);
		
		resetHighlightRectangle();
		removeMouseOverRectangle();
		
		mainParts.revalidate();
		extraParts.revalidate();
		buttonPanel.revalidate();
	}
	
	private void drawInfographic(Infographic infographic) {
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

		drawHighlightRectangle(g);
		drawMouseOverRectangle();
	}
	
	/**
	 * Call when the extra frame at a given height has to be shown
	 * 
	 * @param pointerHeight
	 */
	public void showExtraPart(UUID id) {
		if(Statics.partId != id){
			resetHighlightRectangle();
			if(Statics.partId != null){
				if(id != null){
					waitForFadeOut = true;
					fadeOutWaitId = id;
				}
				withdrawHighlight();
				fadeOutExtraPart();
			}
			
			else if(id != null){
				continueShowExtraPart(id);
			}
		}
	}
	
	private void restartGraphPanel(){
		revalidate();
		repaint();
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
		buttonPanel.setButtonVisible(id);
		drawExtraPartWithIDAndWithGivenFading(id, 1.0f, false, true, this);	
	}

	@Override
	public void fadeOutFinished() {
		Statics.partId = null;
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
		if(Statics.highlighting){
			if(fadeIn){
				highLightMainPart(mainPart.getTopLeftCornerX(), mainPart.getTopLeftCornerY(), mainPart.getImageWidth(), mainPart.getImageHeight());
			}
			else if(fadeOut){
				withdrawHighlight();
			}
		}
		
	}

	public void highLightMainPart(int leftTopCornerX, int leftTopCornerY, int width, int height){
		withdrawHighlight();
		highlightRectangle = new Rectangle(leftTopCornerX, leftTopCornerY, width, height);
		
		highlightTimer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stroke < 3){
					drawHighlightRectangle(getGraphics());
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
	
	private void drawHighlightRectangle(Graphics g) {
		if(highlightRectangle != null){
			Graphics2D g2d = (Graphics2D) g;
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

	public void drawMouseOverRectangle(){
		if(mouseOverRectangle != null){
			Graphics2D g2d = (Graphics2D) getGraphics();
			g2d.setColor(Color.red);
			float[] dash = { 5F, 5F };
			g2d.setStroke(new BasicStroke( 2F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 3F, dash, 0F ));
			g2d.draw(mouseOverRectangle);
		}
	}

	public void setMouseOverRectangle(Rectangle rectangle){
		this.mouseOverRectangle = rectangle;
	}
	
	public void removeMouseOverRectangle(){
		this.mouseOverRectangle = null;
		repaint();
	}
	
}
