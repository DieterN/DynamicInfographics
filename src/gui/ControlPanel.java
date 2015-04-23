package gui;

import infographic.LeafMainPart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JTextPane session_info;
	private ConnectionStatus status;
	private GUIController gui;
	
	public ControlPanel(GUIController gui){
		this.gui = gui;
		this.connectionStatusText = new JTextPane();
		this.session_info = new JTextPane();
		this.status = ConnectionStatus.NOT_CONNECTED;
		initializeControlPanel();
	}

	private void initializeControlPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField name = new JTextField(Statics.default_text_name_field);
        
		JButton start = makeStartButton(name);
        JButton stop  = makeStopButton();
        
        setConnectionStatusText(ConnectionStatus.NOT_CONNECTED,0,0);
        connectionStatusText.setEditable(false);
        
        setSessionInfoText("Not started");
        session_info.setEditable(false);
 
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
		add(Box.createRigidArea(new Dimension(0,770)));
        
		Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSizeTo(new Dimension(200,(int) rectangle.getHeight()));
	}
	
	private int index = 1;
	
	private JButton makeStartButton(final JTextField name) {
		JButton start = new JButton("Start session");
		
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
        		if(name.getText().equals(Statics.default_text_name_field) || name.getText().equals("")){
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
		JButton stop = new JButton("Stop session ");

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                Statics.reading = false;
//                DatabaseManager.storeESenseDataInDB();
    			setSessionInfoText("Session stopped");
                gui.clearExtraParts();
            }
        });
        return stop;
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
