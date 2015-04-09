package gui;

import infographic.ExtraPart;
import infographic.Infographic;
import infographic.LeafMainPart;
import infographic.MainPart;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import tracking.Tracker;
import main.Statics;
import mongodb.DatabaseManager;
import dataTypes.ESenseData;
import analyzation.InfographicController;
import brainwave.BrainwaveListenerCallback;
import brainwave.ConnectionStatus;

public class GUIController extends JFrame implements BrainwaveListenerCallback, FadeOutCallback{
	
	private static final long serialVersionUID = 1L;
	private Infographic infographic;
	private JPanel basic;
	private JPanel graphPanel;
	private JScrollPane scroll;
	private UnscalablePanel control;
	private JPanel mainParts;
	private UnscalablePanel extraParts;
//	private List<Integer> partHeights = new ArrayList<Integer>();
	private JTextPane connectionStatusText;
	private JTextPane session_info;
	private Tracker tracker; //tracker that monitors the position in this GUI
	private ConnectionStatus status;
	private boolean waitForFadeOut;
	private UUID fadeOutWaitId;
	private int index = 1;
	
	private Timer highlightTimer = new Timer();
	private float stroke = 0.5f;
	private Rectangle highlightRectangle;
	
	public GUIController(Infographic infographic, Tracker tracker){
		this.infographic = infographic;
		this.basic = new JPanel();
		this.graphPanel = new JPanel();
		this.scroll = new JScrollPane(graphPanel);
		this.control = new UnscalablePanel();
		this.mainParts = new JPanel();
		this.extraParts = new UnscalablePanel();
		this.connectionStatusText = new JTextPane();
		this.session_info = new JTextPane();
		
		InfographicController controller = new InfographicController(this);
		
		this.tracker = tracker;
		tracker.addCallback(controller);
		
		status = ConnectionStatus.NOT_CONNECTED;
	}	
		
	/**
	 * Start the gui, only to be called at the beginning of the program
	 */
	public void startGUI(){
		if(this.isVisible()){
			throw new IllegalStateException("This method should only be called at the start of the program");
		}
		
		//Add right panes to basic layout and add basic to frame
		basic.setLayout(new BoxLayout(basic, BoxLayout.X_AXIS));
		basic.add(scroll);
		basic.add(control);
		
		//Adjust scrollbar settings and add it to the frame
		JScrollBar scrollbar = scroll.getVerticalScrollBar();
		scrollbar.setUnitIncrement(10);
		add(basic);
		
		//Set Layout of graphPanel component
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.X_AXIS));
		
        //Make column for the main parts
		mainParts.setAlignmentY(TOP_ALIGNMENT);
		mainParts.setLayout(new BoxLayout(mainParts, BoxLayout.Y_AXIS));
		
		//Make column for the extra parts
		extraParts.setAlignmentY(TOP_ALIGNMENT);
		extraParts.setLayout(new BoxLayout(extraParts, BoxLayout.Y_AXIS));

		//Add main and extra part panels to the graphPanel
		graphPanel.add(mainParts);
		graphPanel.add(extraParts);
		
		paintGraphPanel();
		paintControlPanel();
		
        //Set frame size to size of screen and make it visible
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(rectangle.getSize());
		setVisible(true);		
	}

	/********************
	 *** CONTROL PANE ***
	 ********************/
	
	/**
	 * Initialize the panel with all information needed for the program to work
	 */
	private void paintControlPanel() {
		control.setLayout(new BoxLayout(control,BoxLayout.Y_AXIS));
		control.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField name = new JTextField(Statics.default_text_name_field);
        
		JButton start = makeStartButton(name);
        JButton stop  = makeStopButton();
        
        setConnectionStatusText(ConnectionStatus.NOT_CONNECTED,0,0);
        connectionStatusText.setEditable(false);
        
        setSessionInfoText("Not started");
        session_info.setEditable(false);
 
		control.add(Box.createRigidArea(new Dimension(0,10)));
		control.add(start);
		control.add(Box.createRigidArea(new Dimension(0,10)));
		control.add(stop);
		control.add(Box.createRigidArea(new Dimension(0,20)));
		control.add(name);
		control.add(Box.createRigidArea(new Dimension(0,20)));
		control.add(session_info);
		control.add(Box.createRigidArea(new Dimension(0,20)));
		control.add(connectionStatusText);
		control.add(Box.createRigidArea(new Dimension(0,770)));
        Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		control.setSizeTo(new Dimension(200,(int) rectangle.getHeight()));
		
		control.revalidate();
	}

	private JButton makeStartButton(final JTextField name) {
		JButton start = new JButton("Start session");
		
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
//        		if(name.getText().equals(Statics.default_text_name_field) || name.getText().equals("")){
//        			JOptionPane.showMessageDialog(basic, "Please fill in your name first!", "Error", JOptionPane.ERROR_MESSAGE);
//        		}
//        		else if(status != ConnectionStatus.CONNECTED){
//        			JOptionPane.showMessageDialog(basic, "Please wait for the brainwave sensor status to be CONNECTED", "Error", JOptionPane.ERROR_MESSAGE);
//        		}
//        		else{
//        			Statics.reader_name = name.getText();
//        			Statics.SID = UUID.randomUUID().toString();
//        			Statics.reading = true;
//        			setSessionInfoText("Session started");
//        		}
            	UUID extraId = testMethodGetMainPart();
            	showExtraPart(extraId);
            	index ++;
            }

			private UUID testMethodGetMainPart() {
				int localIndex = 0;
				for(MainPart part : infographic.getMappedMainParts().values()){
					if(part instanceof LeafMainPart){
						if(localIndex == index){
							return part.getId();
						}
						else{
							localIndex++;
						}
					}
				}
				return null;
			}
        });
        return start;
	}
	
	private JButton makeStopButton() {
		JButton stop = new JButton("Stop session ");

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            	fadeOutExtraPart();
//            	highLightMainPart(100, 100, 50, 50);
//                Statics.reading = false;
//                DatabaseManager.storeESenseDataInDB();
//    			setSessionInfoText("Session stopped");
//                clearExtraParts();
            }
        });
        return stop;
	}

	private void setSessionInfoText(String info) {
        session_info.setText(info);
        session_info.revalidate();
	}

	private void setConnectionStatusText(ConnectionStatus status, int attention, int meditation) {
		this.status = status;
		
		String text = ConnectionStatus.toString(status);
		text += "\n \n Attention: ";
		text += attention;
		text += "\n \n Meditation: ";
		text += meditation;

        connectionStatusText.setText(text);
		connectionStatusText.revalidate();
	}
	
	@Override
	public void sendESenseMeasurement(ESenseData eSenseData) {
		JScrollBar vertical = scroll.getVerticalScrollBar();
		JScrollBar horizontal = scroll.getHorizontalScrollBar();
//		System.out.println("Received packet: Att " + eSenseData.getAttentionValue() + " Med " + eSenseData.getMeditationValue());
		if(Statics.reading){
//			System.out.println("Sent packet to tracker: vertical " + vertical.getValue() + " horizontal " + horizontal.getValue());
			tracker.sendESenseAndScrollData(eSenseData, horizontal.getValue(), vertical.getValue());
		}
		
		setConnectionStatusText(eSenseData.getStatus(), eSenseData.getAttentionValue(), eSenseData.getMeditationValue());
	}
	

	/*******************
	 *** GRAPH PANEL ***
	 *******************/

	/**
	 * Call this method to draw the infographic that is currently saved in the infographic field of the GUIController class.
	 * No extra parts are shown
	 */
	public void paintGraphPanel() {
		//Parameters used to make the program function well
		int extraWidth = 0;
//		int partHeight = 0;
		
		//show MainParts of infographic
		for(MainPart part : infographic.getMainParts()){
			BufferedImage bimg = part.getBimg();
			GraphicPartComponent component = new GraphicPartComponent(bimg, 1.0f, false, false, null);
			component.setSizeTo(new Dimension(bimg.getWidth(),bimg.getHeight()));
			mainParts.add(component);
			
//			partHeight += bimg.getHeight();
//			partHeights.add(partHeight);
			
			ExtraPart widestChild = part.getWidestChild();
			
			if(widestChild != null){
				BufferedImage bimg2 = widestChild.getBimg();
				extraWidth = Math.max(extraWidth, bimg2.getWidth());
			}
		}
		//Set extrapart size according to mainpart size and width of extra parts
//		extraParts.setSizeTo(new Dimension(extraWidth,partHeights.get(partHeights.size()-1)));
		extraParts.setSizeTo(new Dimension(extraWidth,infographic.getInfographicHeight()));
	}

	/**
	 * Call when the extra frame at a given height has to be shown
	 * 
	 * @param pointerHeight
	 */
	public void showExtraPart(UUID id) {
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
//	public void showExtraPart(int index) {
//		if(Statics.extraPartId != -1){
//			waitForFadeOut = true;
//			fadeOutWaitIndex = index;
//			fadeOutExtraPart();
//		}
//		
//		else{
//			continueShowExtraPart(index);
//		}
//	}
		
//	public void showExtraPart(int index) {
//		if(Statics.extraPartId != -1){
//			waitForFadeOut = true;
//			fadeOutWaitIndex = index;
//			fadeOutExtraPart();
//		}
//		
//		else{
//			continueShowExtraPart(index);
//		}
//	}
	
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
			if(fadeIn){
				highLightMainPart(mainPart.getTopLeftCornerX(), mainPart.getTopLeftCornerY(), mainPart.getImageWidth(), mainPart.getImageHeight());
			}
			else if(fadeOut){
				withdrawHighlight(mainPart.getTopLeftCornerX(), mainPart.getTopLeftCornerY(), mainPart.getImageWidth(), mainPart.getImageHeight());
			}
		}
		else{
			d.setSize(d.getWidth(),infographic.getInfographicHeight());
		}
		
		extraParts.add(Box.createRigidArea(d));
		extraParts.revalidate();
		extraParts.repaint();
		
		return childExists;
//		List<MainPart> graphParts = infographic.getMainParts();
		
//		for(int i = 0; i < graphParts.size(); i++){
//			MainPart part = graphParts.get(i);
//			ExtraPart child = part.getChild();
//			if(child != null && i == index){
//				entered = true;
//				BufferedImage bimg = child.getBimg();
//				GraphicPartComponent component = new GraphicPartComponent(bimg, alpha, fadeIn, fadeOut, callback);
//				component.setMaximumSize(new Dimension(bimg.getWidth(),bimg.getHeight()));
//				component.setMinimumSize(new Dimension(bimg.getWidth(),bimg.getHeight()));
//				extraParts.add(Box.createRigidArea(d));
//				extraParts.add(component);
//				d = new Dimension();
//			}
//			else{
//				d.setSize(d.getWidth(),d.getHeight()+part.getBimg().getHeight());
//			}
//		}
	}
	
	public void highLightMainPart(final int leftTopCornerX, final int leftTopCornerY, final int width, final int height){
		highlightTimer = new Timer();
		highlightTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				drawRectangle(leftTopCornerX, leftTopCornerY, width, height);
			}
		}, 0, 100);
	}
	
	private void drawRectangle(int leftTopCornerX, int leftTopCornerY, int width, int height){
		JScrollBar vertical = scroll.getVerticalScrollBar();
		JScrollBar horizontal = scroll.getHorizontalScrollBar();

		int positionX = leftTopCornerX + Statics.frameOffsetX - horizontal.getValue();
		int positionY = leftTopCornerY + Statics.frameOffsetY - vertical.getValue();
		
		highlightRectangle = new Rectangle(positionX, positionY, width, height);
		
		if(stroke < 3){
			drawHighlightRectangle();
			stroke += 0.2f;
		}
		else{
			stroke = 3.0f;
			highlightTimer.cancel();
		}
	}
	
	private void drawHighlightRectangle() {
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setStroke(new BasicStroke(stroke));
		g2d.draw(highlightRectangle);
	}

	public void withdrawHighlight(int leftTopCornerX, int leftTopCornerY, int width, int height){
		mainParts.removeAll();
		paintGraphPanel();
		revalidate();
	}
	
	public void clearExtraParts(){
		extraParts.removeAll();
		extraParts.revalidate();
		extraParts.repaint();
	}

	public UUID getPartID(double width, double height){
		return infographic.getIDofPartAt(width, height);
	}
	
	/***********************
	 *** GETTERS/SETTERS ***
	 ***********************/

	public JScrollPane getScroll() {
		return scroll;
	}

	public Infographic getInfographic() {
		return infographic;
	}
	
	public void setInfographic(Infographic infographic){
		this.infographic = infographic;
	}

	public JPanel getBasic() {
		return basic;
	}

	public JPanel getGraphPanel() {
		return graphPanel;
	}

	public UnscalablePanel getControl() {
		return control;
	}

	public JPanel getMainParts() {
		return mainParts;
	}

	public UnscalablePanel getExtraParts() {
		return extraParts;
	}
}