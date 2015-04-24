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

public class GUIController extends JFrame implements BrainwaveListenerCallback{
	
	private static final long serialVersionUID = 1L;
	private Infographic infographic;
	private JPanel basic;
	private JScrollPane scroll;
	private GraphPanel graphPanel;
	private ControlPanel control;
	private Tracker tracker; //tracker that monitors the position in this GUI
	
	public GUIController(Infographic infographic, Tracker tracker){
		this.infographic = infographic;
		this.basic = new JPanel();
		this.control = new ControlPanel(this);
		this.graphPanel = new GraphPanel(infographic);
		this.scroll = new JScrollPane(graphPanel);
		
		InfographicController controller = new InfographicController(this, infographic);
		
		this.tracker = tracker;
		tracker.addCallback(controller);
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
		
        //Set frame size to size of screen and make it visible
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(rectangle.getSize());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	public void sendSensorData(SensorData data){
		System.out.println("Entered");
		JScrollBar vertical = scroll.getVerticalScrollBar();
		JScrollBar horizontal = scroll.getHorizontalScrollBar();
		if(Statics.reading){
			tracker.sendSensorAndScrollData(data, horizontal.getValue(), vertical.getValue());
		}		
		
		control.setConnectionStatusText(data.getStatus(), data.getAttentionValue(), data.getMeditationValue());
	}

	public UUID getPartID(double width, double height){
		return infographic.getIDofPartAt(width, height);
	}
	
	public void showExtraPart(UUID id){
		graphPanel.showExtraPart(id);
	}

	public void fadeOutExtraPart() {
		graphPanel.fadeOutExtraPart();
	}
	
	public void clearExtraParts(){
		graphPanel.clearExtraParts();
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