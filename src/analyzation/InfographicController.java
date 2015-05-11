package analyzation;

import infographic.Infographic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import main.Statics;
import gui.GUIController;
import dataTypes.SensorDataPacket;
import tracking.TrackerCallback;

/**
 * Responsible for controlling the infographic (main and extra parts).
 * Uses policies to decide whether an extra part should be shown/hidden.
 * 
 * @author Dieter
 *
 */
public class InfographicController implements TrackerCallback {

	private GUIController gui; //gui that shows the infographic
	private Infographic infographic; //infographic that this class controls
	private static Policy policy; //policy used to determine whether or not to show/hide extra part
	private Map<UUID,Integer> highMeasureMap = new HashMap<UUID,Integer>(); //map that contains nb of high measurements made for a specific extra part

	/**
	 * Constructor for an infographic Controller.
	 * 
	 * @param gui: gui that shows the infographic
	 * @param infographic: infographic that this class controls
	 */
	public InfographicController(GUIController gui, Infographic infographic) {
		this.gui = gui;
		this.infographic = infographic;
		setPolicyToStaticsPolicy();
	}
	
	/**
	 * Set the policy for deciding whether to show/hide an extra part to Statics.policy
	 */
	public static void setPolicyToStaticsPolicy() {
		switch(Statics.policy){
		case ATTENTION:
			policy = new AttentionPolicy();
			break;
		case MEDITATION:
			policy = new MeditationPolicy();
			break;
		case COMBINED:
			policy = new CombinedPolicy();
			break;
		case UNDEFINED:
			policy = null;
			break;
		}
	}
	
	/**
	 * Set the policy for deciding whether to show/hide an extra part to the given policy
	 */
	public static void setPolicy(PolicyType newPolicy) {
		switch(newPolicy){
		case ATTENTION:
			policy = new AttentionPolicy();
			break;
		case MEDITATION:
			policy = new MeditationPolicy();
			break;
		case COMBINED:
			policy = new CombinedPolicy();
			break;
		case UNDEFINED:
			policy = null;
			break;
		}
	}
	
	@Override
	public void sendSensorDataPacket(SensorDataPacket packet) {	
		if(policy == null){
			throw new IllegalStateException("Policy not yet defined!");
		}
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
}
