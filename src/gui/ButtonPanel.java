package gui;

import infographic.CompositeMainPart;
import infographic.Infographic;
import infographic.LeafMainPart;
import infographic.MainPart;
import infographic.MainPartVisitor;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import main.Statics;

public class ButtonPanel extends UnscalablePanel implements MainPartVisitor{

	private static final long serialVersionUID = 1L;
	
	private List<UUID> visibleButtonList = new ArrayList<UUID>();
	private final int buttonHeight = 25;
	private final int buttonWidth = 200;
	private Infographic infographic;
	private GraphPanel graphPanel;

	public ButtonPanel(Infographic infographic, GraphPanel graphPanel){
		this.infographic = infographic;
		this.graphPanel = graphPanel;
		drawButtonPanel();
	}
	
	private void setAllButtonsVisible() {
		for(LeafMainPart part: infographic.getAllLeafs()){
			visibleButtonList.add(part.getId());
		}
	}

	public void drawButtonPanel(){
		if(Statics.interactive){
			setAllButtonsVisible();
		}
		
		removeAll();
		setAlignmentY(TOP_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setSizeTo(new Dimension(buttonWidth, infographic.getInfographicHeight()));
		//Loop over LeafMainParts to draw invisible buttons (they're in a treeset, so they should be ordened)
		for(MainPart part : infographic.getMainParts()){
			part.accept(this);
		}
		revalidate();
	}

	private void createCompositeButtonPanel(CompositeMainPart part) {
		TreeSet<MainPart> subParts = part.getSubParts();
		int height = part.getImageHeight();
		int nbOfParts = subParts.size();
		int heightWithoutButtons = height - nbOfParts*buttonHeight;
		Dimension d = new Dimension(0,heightWithoutButtons/(nbOfParts+1));
		
		for(MainPart leaf: subParts){
			add(Box.createRigidArea(d));
			createExtraButton((LeafMainPart) leaf);
		}
		add(Box.createRigidArea(d));
	}

	private void createLeafButtonPanel(LeafMainPart part) {		
		int height = part.getImageHeight();
		Dimension d = new Dimension(0,(height - buttonHeight)/2);
		
		add(Box.createRigidArea(d));
		createExtraButton(part);
		add(Box.createRigidArea(d));
	}

	private void createExtraButton(final LeafMainPart leaf) {
		final UUID id = leaf.getId();
		if(leaf.getChild() != null && visibleButtonList.contains(id)){
			final UnscalableButton extraButton = new UnscalableButton("Review extra information");
			if(Statics.interactive){
				extraButton.setText("View extra information");
			}
			extraButton.setSizeTo(new Dimension(buttonWidth,buttonHeight));
			extraButton.setVisible(true);
			add(extraButton);
			extraButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Statics.reading){
						graphPanel.showExtraPart(id);						
					}
					else{
	        			JOptionPane.showMessageDialog(SwingUtilities.getAncestorOfClass(JFrame.class, graphPanel), "Can't show extra information! Start a session first!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			extraButton.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	Rectangle rectangle = new Rectangle(leaf.getTopLeftCornerX(), leaf.getTopLeftCornerY(), leaf.getImageWidth(), leaf.getImageHeight());
			    	graphPanel.setMouseOverRectangle(rectangle);
			    	graphPanel.drawMouseOverRectangle();
			    }

			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	graphPanel.removeMouseOverRectangle();
			    }
			});
		}
		else{
			add(Box.createRigidArea(new Dimension(0, buttonHeight)));
		}
	}
	
	public void setButtonVisible(UUID id){
		visibleButtonList.add(id);
		removeAll();
		drawButtonPanel();
		repaint();
		revalidate();
	}

	@Override
	public void visit(LeafMainPart part) {
		createLeafButtonPanel(part);
	}

	@Override
	public void visit(CompositeMainPart part) {
		createCompositeButtonPanel(part);
	}
	
	public void drawNewInfographic(Infographic infographic) {
		this.infographic = infographic;
		visibleButtonList.clear();
		if(Statics.interactive)
			setAllButtonsVisible();
		drawButtonPanel();
	}

	public void endSession() {
		this.removeAll();
		if(!Statics.interactive)
			visibleButtonList.clear();
		drawButtonPanel();
		repaint();
		revalidate();
	}
}
