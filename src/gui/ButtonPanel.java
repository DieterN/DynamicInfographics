package gui;

import infographic.CompositeMainPart;
import infographic.Infographic;
import infographic.LeafMainPart;
import infographic.MainPart;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class ButtonPanel extends UnscalablePanel{

	private static final long serialVersionUID = 1L;
	
	private List<UUID> visibleButtonList = new ArrayList<UUID>();
	private final int buttonHeight = 25;
	private final int buttonWidth = 200;
	private Infographic infographic;
	private GraphPanel graphPanel;
	private int nbOfLeafs = 3;

	public ButtonPanel(Infographic infographic, GraphPanel graphPanel){
		this.infographic = infographic;
		this.graphPanel = graphPanel;
		for(LeafMainPart part: infographic.getAllLeafs()){
			visibleButtonList.add(part.getId());
			nbOfLeafs--;
			if(nbOfLeafs == 0){
				break;
			}
		}
		nbOfLeafs = 3;
		drawButtonPanel();
	}
	
	public void drawButtonPanel(){
		removeAll();
		setAlignmentY(TOP_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setSizeTo(new Dimension(buttonWidth, infographic.getInfographicHeight()));
		//Loop over LeafMainParts to draw invisible buttons (they're in a treeset, so they should be ordened)
		for(MainPart part : infographic.getMainParts()){
			if(part instanceof CompositeMainPart){
				createCompositeButtonPanel((CompositeMainPart) part);
			}
			else{
				createLeafButtonPanel((LeafMainPart) part);
			}
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

	private void createExtraButton(LeafMainPart leaf) {
		final UUID id = leaf.getId();
		if(leaf.getChild() != null && visibleButtonList.contains(id)){
			UnscalableButton extraButton = new UnscalableButton("Review extra part");
			extraButton.setSizeTo(new Dimension(buttonWidth,buttonHeight));
			extraButton.setVisible(true);
//			buttonmap.put(id, extraButton);
			add(extraButton);
			extraButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					graphPanel.resetHighlightRectangle();
					graphPanel.showExtraPart(id);
				}
			});
		}
		else{
			add(Box.createRigidArea(new Dimension(0, buttonHeight)));
		}
	}
	
	public void setButtonVisible(UUID id){
		visibleButtonList.add(id);
		drawButtonPanel();
		repaint();
	}
	
}
