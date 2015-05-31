package analyzation;

import java.util.UUID;

import main.Statics;
import dataTypes.SensorDataPacket;

/**
 * Superclass for policies.
 * Policies are responsible for determining whether an extra part should be shown/hidden based on attention and/or meditation output of the sensor.
 * 
 * @author Dieter
 *
 */
public abstract class Policy {
		
	private int outOfPartCounter = 0;
	private UUID counterPartId = null;
	
	/**
	 * Method that returns a boolean indicating whether or not an extra part should be shown.
	 * 
	 * @param packet: contains information about the output of the brainwave sensor
	 * @return true if extra part should be shown, false otherwise
	 */
	public abstract boolean showExtraPart(SensorDataPacket packet);

	/**
	 * Method that decides whether or not to hide the currently shown extra part.
	 * Hide an extra part if we're no longer looking at that part in the infographic
	 * for a sufficient amount of time. 
	 * 
	 * @param packet: contains information about the output of the brainwave sensor
	 * @param id: id of the part that the given packet is about
	 * @return true if the currently shown extra part should be hidden, false otherwise
	 */
	public boolean hideExtraPart(SensorDataPacket packet, UUID id){
		boolean result = false;
		 
		if(Statics.partId != null && Statics.partId != id){ //only act if something is shown and if we're not looking at the shown part anymore
			if(counterPartId == Statics.partId){ //if the current counter fits the currently shown part
				outOfPartCounter++;
				if(outOfPartCounter >= Statics.nbTimesOutExtraParts){
					result = true;
					outOfPartCounter = 0;
				}
			}
			else{ //reset counter
				outOfPartCounter = 1;
				counterPartId = Statics.partId;
			}
		}
		return result;
	}
}
