package gui;

import infographic.Infographic;
import infographic.InfographicParser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Statics;
import mongodb.DatabaseManager;
import analyzation.InfographicController;
import analyzation.PolicyType;
import brainwave.ConnectionStatus;

/**
 * Class that represents the controlpanel of the GUI.
 * This class contains buttons/sliders/textfields that allow 
 * to change the infographic, change the policy, to start/stop a session ...
 * It also contains the fields that show the connection status of the sensor.
 * 
 * @author Dieter
 */
public class ControlPanel extends UnscalablePanel{

	private static final long serialVersionUID = 1L;	
	
	private JTextPane connectionStatusText;
	private JTextPane session_info;
	private JLabel chooseInfographic;
	private JLabel choosePolicy;
	private JLabel currentPolicy;
	private JLabel setPolicyThreshold;
	private ConnectionStatus status;
	private GUIController gui;
	
	private JLabel average;
	private int total = 0;
	private int nbOfMeasurements = 0;
	
	/**
	 * Constructor for a control panel. It takes the GUI wherein it's drawn
	 * as a parameter.
	 * 
	 * @param gui: gui wherin this controlpanel is drawn
	 */
	public ControlPanel(GUIController gui){
		this.gui = gui;
		this.connectionStatusText = new JTextPane();
		this.session_info = new JTextPane();
		this.chooseInfographic = new JLabel();
		this.choosePolicy = new JLabel();
		this.currentPolicy = new JLabel();
		this.setPolicyThreshold = new JLabel();
		this.average = new JLabel();		
		this.status = ConnectionStatus.NOT_CONNECTED;
		initializeControlPanel();
	}

	private void initializeControlPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField name = new JTextField(Statics.default_text_name_field);
        
		JButton start = makeStartButton(name);
        JButton stop  = makeStopButton();
        List<JButton> infographicList  = makeInfographicButtons();
        List<JButton> policyList  = makePolicyButtons();
        JSlider policyThresholdSlider = makePolicyThresholdSlider();
        
        setConnectionStatusText(ConnectionStatus.NOT_CONNECTED,0,0);
        connectionStatusText.setEditable(false);
        
        setSessionInfoText("Not started");
        session_info.setEditable(false);
        
        chooseInfographic.setText("Choose infographic:");
        choosePolicy.setText("Choose policy:");
        updateCurrentPolicyText();
        setPolicyThreshold.setText("Set Policy Threshold:");
        
        average.setText("Previous session avg.: 0 ");
        
        JCheckBox highlightingCheckbox = createHighlightingCheckBox();
        
		add(Box.createRigidArea(new Dimension(0,10)));
		add(start);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(stop);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(name);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(session_info);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(connectionStatusText);
		add(Box.createRigidArea(new Dimension(0,100)));
		add(chooseInfographic);
		add(Box.createRigidArea(new Dimension(0,20)));
		for(JButton infographicButton: infographicList){
			add(infographicButton);
			add(Box.createRigidArea(new Dimension(0,10)));
		}
		add(Box.createRigidArea(new Dimension(0,20)));
		add(highlightingCheckbox);
		add(Box.createRigidArea(new Dimension(0,40)));
		add(currentPolicy);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(choosePolicy);
		add(Box.createRigidArea(new Dimension(0,20)));
		for(JButton policyButton: policyList){
			add(policyButton);
			add(Box.createRigidArea(new Dimension(0,10)));
		}
		add(Box.createRigidArea(new Dimension(0,10)));
		add(setPolicyThreshold);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(policyThresholdSlider);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(average);
		add(Box.createRigidArea(new Dimension(0,350)));
        
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSizeTo(new Dimension(200,(int) rectangle.getHeight()));
	}
	
	private JButton makeStartButton(final JTextField name) {
		UnscalableButton start = new UnscalableButton("Start session");
		start.setSizeTo(new Dimension(150,25));
		
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	if(Statics.reading){
        			JOptionPane.showMessageDialog(gui, "You already started a session", "Error", JOptionPane.ERROR_MESSAGE);
            	}
            	else if(name.getText().equals(Statics.default_text_name_field) || name.getText().equals("")){
        			JOptionPane.showMessageDialog(gui, "Please fill in your name first!", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		else if(Statics.policy == PolicyType.UNDEFINED){
        			JOptionPane.showMessageDialog(gui, "Please choose a policy first!", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		else if(status != ConnectionStatus.CONNECTED){
        			JOptionPane.showMessageDialog(gui, "Please wait for the brainwave sensor status to be CONNECTED", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		else{
        			Statics.reader_name = name.getText();
        			Statics.SID = UUID.randomUUID().toString();
        			Statics.reading = true;
        			setSessionInfoText("Session started");
        		}
//            	UUID extraId = testMethodGetMainPart();
//            	gui.showExtraPart(extraId);
//            	index ++;
            }

//			private UUID testMethodGetMainPart() {
//				int localIndex = 0;
//				for(LeafMainPart part : gui.getInfographic().getAllLeafs()){
//					if(localIndex == index){
//						return part.getId();
//					}
//					else{
//						localIndex++;
//					}
//				}
//				return null;
//			}
        });
        return start;
	}
	
	private JButton makeStopButton() {
		UnscalableButton stop = new UnscalableButton("Stop session");
		stop.setSizeTo(new Dimension(150,25));

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	if(Statics.reading){
	                Statics.reading = false;
	                DatabaseManager.storeDataInDB();
	                gui.endSession();
	    			setSessionInfoText("Session stopped");
	    			setAverageText();
            	}
            	else{
            		JOptionPane.showMessageDialog(gui, "No session started yet!", "Error", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        return stop;
	}
	
	private List<JButton> makeInfographicButtons() {
		List<JButton> infographicButtons = new ArrayList<JButton>();
		
		for(final String infographicName : Statics.infographicList){
			UnscalableButton infographicButton = new UnscalableButton(infographicName);
			infographicButton.setSizeTo(new Dimension(150,25));
	
			infographicButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	if(Statics.reading){
	        			JOptionPane.showMessageDialog(gui, "Can't change infographic while reading!", "Error", JOptionPane.ERROR_MESSAGE);
	            	}
	            	else{
//	            		Statics.inputFolder = "infographic_" + infographicName.toLowerCase();
	            		String inputFolder = "infographic_" + infographicName.toLowerCase();
	            		Statics.currentInfographicFolder = inputFolder;
	        			InfographicParser parser = new InfographicParser(inputFolder);
	        			Infographic infographic = parser.parse();
	        			gui.drawNewInfographic(infographic);
	            	}
	            }
	        });
			infographicButtons.add(infographicButton);
		}
        return infographicButtons;
	}
	
	private List<JButton> makePolicyButtons() {
		List<JButton> policyButtons = new ArrayList<JButton>();
		
		for(final String policyName : Statics.allowedPoliciesList){
			UnscalableButton policyButton = new UnscalableButton(policyName);
			policyButton.setSizeTo(new Dimension(150,25));
	
			policyButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	if(Statics.reading){
	        			JOptionPane.showMessageDialog(gui, "Can't change policy while reading!", "Error", JOptionPane.ERROR_MESSAGE);
	            	}
	            	else{
	            		Statics.policy = PolicyType.stringToPolicyType(policyName);
	            		InfographicController.setPolicyToStaticsPolicy();
	            		updateCurrentPolicyText();
	            	}
	            }
	        });
			policyButtons.add(policyButton);
		}
        return policyButtons;
	}
	
	private JSlider makePolicyThresholdSlider() {
		final JSlider policyThresholdSlider = new JSlider(0, 100, Statics.minPolicyValue);
		policyThresholdSlider.setMinorTickSpacing(5);
		policyThresholdSlider.setMajorTickSpacing(10);
		policyThresholdSlider.setPaintTicks(true);
		policyThresholdSlider.setPaintLabels(true);
		policyThresholdSlider.setSnapToTicks(true);

		policyThresholdSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
            	if(Statics.reading){
        			JOptionPane.showMessageDialog(gui, "Can't change policythreshold while reading!", "Error", JOptionPane.ERROR_MESSAGE);
            	}
            	else{
            		Statics.minPolicyValue = policyThresholdSlider.getValue();
            	}
			}
		});
		return policyThresholdSlider;
	}
	
	private JCheckBox createHighlightingCheckBox() {
		final JCheckBox highlightingCheckBox = new JCheckBox("Highlighting");
		highlightingCheckBox.setSelected(Statics.highlighting);
		highlightingCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Statics.reading){
					Statics.highlighting = highlightingCheckBox.isSelected();					
				}
				else{
        			highlightingCheckBox.setSelected(Statics.highlighting);
        			JOptionPane.showMessageDialog(gui, "Can't turn highlighting on/off while reading!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return highlightingCheckBox;
	}

	/**
	 * Call this message to change the session status text.
	 * Normally this text will be: "Session started" or
	 * "Session stopped"
	 * 
	 * @param info: text to be put as session info
	 */
	public void setSessionInfoText(String info) {
        session_info.setText(info);
        session_info.revalidate();
	}

	/**
	 * Change the current used policy text to Statics.policy
	 */
	public void updateCurrentPolicyText() {
        currentPolicy.setText("Current Policy: " + PolicyType.toString(Statics.policy));
        currentPolicy.revalidate();
	}

	/**
	 * Change the current used policy text to Statics.policy
	 */
	public void setAverageText() {
		if(nbOfMeasurements == 0){
			average.setText("Previous session avg.: 0");
	        average.revalidate();
		}
		else{
	        average.setText("Previous session avg.: " + total/nbOfMeasurements);
	        average.revalidate();
	        total = 0;
	        nbOfMeasurements = 0;
		}
	}

	/**
	 * Change the text that indicates the connection status of the brainwave sensor
	 * 
	 * @param status: Connectionstatus of the brainwave sensor
	 * @param attention: last measured attention value
	 * @param meditation: last measured meditation value
	 */
	public void setConnectionStatusText(ConnectionStatus status, int attention, int meditation) {
		this.status = status;
		
		String text = ConnectionStatus.toString(status);
		text += "\n \n Attention: ";
		text += attention;
		text += "\n \n Meditation: ";
		text += meditation;

        connectionStatusText.setText(text);
		connectionStatusText.revalidate();
	}

	public void setTotal(int attention, int meditation) {
		nbOfMeasurements++;
		switch(Statics.policy){
		case ATTENTION:
			total += attention;
			break;
		case MEDITATION:
			total += meditation;
			break;
		case COMBINED:
			total += (attention + meditation)/2;
			break;
		default:
			break;
		}
	}
}
