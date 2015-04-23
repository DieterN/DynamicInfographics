package analyzation;

import infographic.Infographic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import main.Statics;
import gui.GUIController;
import dataTypes.ESensePacket;
import dataTypes.EegPacket;
import dataTypes.SensorDataPacket;
import tracking.TrackerCallback;

/**
 * Receives all ESensePackets and analyses them using a certain policy.
 * 
 * @author Dieter
 *
 */
public class InfographicController implements TrackerCallback {

	private GUIController gui;
	private Infographic infographic;
	private Policy policy;
	private Map<UUID,Integer> highMeasureMap = new HashMap<UUID,Integer>();

	public InfographicController(GUIController gui, Infographic infographic) {
		this.gui = gui;
		this.infographic = infographic;
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
	public void sendSensorDataPacket(SensorDataPacket packet) {		
		Point point = packet.getPosition();
		UUID id = infographic.getIDofPartAt(point.x, point.y);
		
		if(id != null){ //only do something if you're on the main infographic
			if(policy.showExtraPart(packet)){
				if(id != Statics.partId){ //only show part if it's not already shown
					if(showPartWithUUID(id)){ //if enough show messages, show part
						gui.showExtraPart(id);
					}
				}
			}
			else{ //if we don't want to show something, then see if we want to hide the shown part
				if(policy.hideExtraPart(packet, id)){ 
					gui.fadeOutExtraPart();
				}
			}
		}
	}

	@Override
	public void sendESensePacket(ESensePacket packet) {		
		Point point = packet.getPosition();
		UUID id = infographic.getIDofPartAt(point.x, point.y);
		
		if(id != null){ //only do something if you're on the main infographic
			if(policy.showExtraPart(packet)){
				if(id != Statics.partId){ //only show part if it's not already shown
					if(showPartWithUUID(id)){ //if enough show messages, show part
						gui.showExtraPart(id);
					}
				}
			}
			else{ //if we don't want to show something, then see if we want to hide the shown part
				if(policy.hideExtraPart(packet, id)){ 
					gui.fadeOutExtraPart();
				}
			}
		}
	}

	@Override
	public void sendEegPacket(EegPacket packet) {
		// doe niets hier voorlopig, als ooit de infografiek gecontroleerd wordt door een van de waarden in dit pakket
		// kan deze methode gebruikt worden om de juiste policy aan te roepen
	}

	private boolean showPartWithUUID(UUID id) {
		boolean result = false;
		
		int count = getCountForId(id);
		int newCount = count + 1;
		if(newCount >= Statics.nbOfTimesMeasuredInMainPart){
			result = true;
			highMeasureMap.put(id, 0); //extra part will be shown, reset counter
		}
		else{
			highMeasureMap.put(id, newCount);
		}
		return result;
	}

	private int getCountForId(UUID id) {
		if(!highMeasureMap.keySet().contains(id)){
			highMeasureMap.put(id, 0);
		}
		return highMeasureMap.get(id);
	}

	public GUIController getGui() {
		return gui;
	}

	public void setGui(GUIController gui) {
		this.gui = gui;
	}
}
