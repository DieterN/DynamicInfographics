package analyzation;

import java.util.HashMap;
import java.util.Map;

import main.Statics;
import gui.GUIController;
import dataTypes.ESensePacket;
import tracking.TrackerCallback;

/**
 * Receives all ESensePackets and analyses them using a certain policy.
 * 
 * @author Dieter
 *
 */
public class InfographicController implements TrackerCallback {

	private GUIController gui;
	private Policy policy;
	private Map<Integer,Integer> highMeasureMap = new HashMap<Integer,Integer>();

	public InfographicController(GUIController gui) {
		this.gui = gui;
		setRightPolicy();
	}
	
	private void setRightPolicy() {
		switch(Statics.policy){
		case ATTENTION:
			this.policy = new AttentionPolicy();
			break;
		case MEDITATION:
			this.policy = new MeditationPolicy();
			break;
		case COMBINED:
			this.policy = new CombinedPolicy();
			break;
		}
	}

	@Override
	public void sendESensePacket(ESensePacket packet) {		
		int indexOfPart = gui.getIndexOfPart(packet.getPosition().y);
		int mainPartsWidth = gui.getMainParts().getSize().width;
		
		boolean showExtraPart = policy.showExtraPart(packet);
		
		if(packet.getPosition().x <= mainPartsWidth + 5 && indexOfPart != -1){ //only do something if you're on the main infographic
			if(showExtraPart){
				if(indexOfPart != Statics.extraPartId){ //only show part if it's not already shown
					if(showPartAtIndex(indexOfPart)){ //if enough show messages, show part
						gui.showExtraPart(indexOfPart);
					}
				}
			}
			else{ //if we don't want to show something, then see if we want to hide the shown part
				if(policy.hideExtraPart(packet, indexOfPart)){ 
					gui.hideExtraPart();
				}
			}
		}
	}

	private boolean showPartAtIndex(int index) {
		boolean result = false;
		
		int count = getCountForIndex(index);
		int newCount = count + 1;
		if(newCount >= Statics.nbOfTimesMeasuredInMainPart){
			result = true;
			highMeasureMap.put(index, 0); //extra part will be shown, reset counter
		}
		else{
			highMeasureMap.put(index, newCount);
		}
		return result;
	}

	private int getCountForIndex(int index) {
		if(!highMeasureMap.keySet().contains(index)){
			highMeasureMap.put(index, 0);
		}
		return highMeasureMap.get(index);
	}

	public GUIController getGui() {
		return gui;
	}

	public void setGui(GUIController gui) {
		this.gui = gui;
	}
}
