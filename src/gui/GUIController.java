package gui;

import infographic.Infographic;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import tracking.Tracker;
import main.Statics;
import dataTypes.SensorData;
import analyzation.InfographicController;
import brainwave.BrainwaveListenerCallback;

/**
 * Topclass for the complete GUI. Contains the Graph- and ControlPanel
 * and all of their children.
 * 
 * @author Dieter
 *
 */
public class GUIController extends JFrame implements BrainwaveListenerCallback{
	
	private static final long serialVersionUID = 1L;
	private Infographic infographic;
	private JPanel basic;
	private JScrollPane scroll;
	private GraphPanel graphPanel;
	private ControlPanel control;
	private InfographicController controller;
	private Tracker tracker; //tracker that monitors the position in this GUI
	
	/**
	 * Constructor for the GUIController class.
	 * 
	 * @param infographic: infographic to be shown 
	 * @param tracker: tracker that tracks the mouse in this gui
	 */
	public GUIController(Infographic infographic, Tracker tracker){
		this.infographic = infographic;
		this.basic = new JPanel();
		this.control = new ControlPanel(this);
		this.graphPanel = new GraphPanel(infographic);
		this.scroll = new JScrollPane(graphPanel);
		
		controller = new InfographicController(this, infographic);
		
		this.tracker = tracker;
		tracker.addCallback(controller);
	}	
		
	/**
	 * Start the gui, only to be called at the beginning of the program
	 */
	public void startGUI(){
//		if(this.isVisible()){
//			throw new IllegalStateException("This method should only be called at the start of the program");
//		}		
		//Add right panes to basic layout and add basic to frame
		basic.setLayout(new BoxLayout(basic, BoxLayout.X_AXIS));
		basic.add(scroll);
		basic.add(control);
		
		//Adjust scrollbar settings and add it to the frame
		JScrollBar scrollbar = scroll.getVerticalScrollBar();
		scrollbar.setUnitIncrement(10);
		add(basic);
		
        //Set frame size to size of screen and make it visible
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(rectangle.getSize());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	/**
	 * Call when new sensor data has been received from the brainwave sensor.
	 * The received that data will be sent to the tracker together with the
	 * scroll bar values.
	 */
	public void sendSensorData(SensorData data){
		JScrollBar vertical = scroll.getVerticalScrollBar();
		JScrollBar horizontal = scroll.getHorizontalScrollBar();
		if(Statics.reading){
			tracker.sendSensorAndScrollData(data, horizontal.getValue(), vertical.getValue());
		}		
		
		control.setConnectionStatusText(data.getStatus(), data.getAttentionValue(), data.getMeditationValue());
		control.setTotal(data.getAttentionValue(), data.getMeditationValue());
	}

	/**
	 * Get ID of extra part at the given x and y coordinates.
	 * 
	 * @param x: x-coordinate of extra part
	 * @param y: y-coordinate of extra part
	 * @return Extra part at given x and y coordinates.
	 */
	public UUID getPartID(double x, double y){
		return infographic.getIDofPartAt(x, y);
	}
	
	/**
	 * Show extra part with given id.
	 * If the part with the given ID is already shown, do nothing.
	 * 
	 * @param id: id of extra part that should be shown
	 */
	public void showExtraPart(UUID id){
		graphPanel.showExtraPart(id);
	}
	
	/**
	 * Fade out currently shown extra part 
	 */
	public void fadeOutExtraPart() {
		graphPanel.fadeOutExtraPart();
	}
	
	/**
	 * Call this method when a new infographic has to be shown
	 * 
	 * @param infographic: new infographic to be shown
	 */
	public void drawNewInfographic(Infographic infographic){
		graphPanel.drawNewInfographic(infographic);
		tracker.removeCallback(controller);
		controller = new InfographicController(this, infographic);
		tracker.addCallback(controller);
	}
	
	/**
	 * Call when the current session has ended.
	 */
	public void endSession(){
		Statics.partId = null;
		graphPanel.endSession();
	}
	
	/***********************
	 *** GETTERS/SETTERS ***
	 ***********************/

	public Infographic getInfographic() {
		return infographic;
	}
	
	public void setInfographic(Infographic infographic){
		this.infographic = infographic;
	}

	public Component getMainParts() {
		return graphPanel.getMainParts();
	}
	
}