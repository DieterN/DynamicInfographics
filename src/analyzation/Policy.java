package analyzation;

import java.util.UUID;

import main.Statics;
import dataTypes.ESensePacket;
import dataTypes.SensorDataPacket;

public abstract class Policy {
		
	private int outOfPartHeightCounter = 0;
	private UUID counterPartId = null;
	
	public abstract boolean showExtraPart(ESensePacket packet);
	public abstract boolean showExtraPart(SensorDataPacket packet);

	/**
	 * Hide an extra part if we're no longer looking at that part in the infographic
	 * for a sufficient amount of time.
	 * 
	 * @param packet
	 * @param indexOfPart
	 * @return
	 */
	public boolean hideExtraPart(ESensePacket packet, UUID id){
		boolean result = false;
		 
		if(Statics.partId != null && Statics.partId != id){ //only act if something is shown and if we're not looking at the shown part anymore
			if(counterPartId == Statics.partId){ //if the current counter fits the currently shown part
				outOfPartHeightCounter++;
				if(outOfPartHeightCounter >= Statics.nbTimesOutExtraParts){
					result = true;
					outOfPartHeightCounter = 0;
				}
			}
			else{ //reset counter
				outOfPartHeightCounter = 1;
				counterPartId = Statics.partId;
			}
		}
		return result;
	}
	
	public boolean hideExtraPart(SensorDataPacket packet, UUID id){
		boolean result = false;
		 
		if(Statics.partId != null && Statics.partId != id){ //only act if something is shown and if we're not looking at the shown part anymore
			if(counterPartId == Statics.partId){ //if the current counter fits the currently shown part
				outOfPartHeightCounter++;
				if(outOfPartHeightCounter >= Statics.nbTimesOutExtraParts){
					result = true;
					outOfPartHeightCounter = 0;
				}
			}
			else{ //reset counter
				outOfPartHeightCounter = 1;
				counterPartId = Statics.partId;
			}
		}
		return result;
	}
}
