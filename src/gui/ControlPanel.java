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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import main.Statics;
import mongodb.DatabaseManager;
import brainwave.ConnectionStatus;

public class ControlPanel extends UnscalablePanel{

	private static final long serialVersionUID = 1L;	
	
	private JTextPane connectionStatusText;
	private JTextPane chooseInfographic;
	private JTextPane session_info;
	private ConnectionStatus status;
	private GUIController gui;
	
	public ControlPanel(GUIController gui){
		this.gui = gui;
		this.connectionStatusText = new JTextPane();
		this.session_info = new JTextPane();
		this.chooseInfographic = new JTextPane();
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
        
        setConnectionStatusText(ConnectionStatus.NOT_CONNECTED,0,0);
        connectionStatusText.setEditable(false);
        
        setSessionInfoText("Not started");
        session_info.setEditable(false);
        
        chooseInfographic.setText("Choose infographic:");
        chooseInfographic.setEditable(false);
        
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
			add(Box.createRigidArea(new Dimension(0,20)));
		}
		add(Box.createRigidArea(new Dimension(0,600)));
        
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSizeTo(new Dimension(200,(int) rectangle.getHeight()));
	}
	
//	private int index = 1;
	
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

                Statics.reading = false;
//                DatabaseManager.storeDataInDB();
    			setSessionInfoText("Session stopped");
                gui.clearExtraParts();
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
	            		Statics.inputFolder = "infographic_" + infographicName.toLowerCase();
	        			InfographicParser parser = new InfographicParser(Statics.inputFolder);
	        			Infographic infographic = parser.parse();
	        			gui.drawNewInfographic(infographic);
	            	}
	            }
	        });
			infographicButtons.add(infographicButton);
		}
        return infographicButtons;
	}

	public void setSessionInfoText(String info) {
        session_info.setText(info);
        session_info.revalidate();
	}

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
}
