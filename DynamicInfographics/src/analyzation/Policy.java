package analyzation;

import main.Statics;
import dataTypes.ESensePacket;

public abstract class Policy {
		
	private int outOfPartHeightCounter = 0;
	private int counterPartId = -1;
	
	public abstract boolean showExtraPart(ESensePacket packet);

	/**
	 * Hide an extra part if we're no longer on the same height as the part in the infographic
	 * for a sufficient amount of time.
	 * 
	 * @param packet
	 * @param indexOfPart
	 * @return
	 */
	public boolean hideExtraPart(ESensePacket packet, int indexOfPart){
		boolean result = false;
		
		if(Statics.extraPartId != -1 && Statics.extraPartId != indexOfPart){ //only act if something is shown and if we're not on the same height as the shown part
			if(counterPartId == indexOfPart){ //if the current counter fits the currently shown part
				outOfPartHeightCounter++;
				if(outOfPartHeightCounter >= Statics.nbTimesOutExtraParts){
					result = true;
					outOfPartHeightCounter = 0;
				}
			}
			else{ //reset counter
				outOfPartHeightCounter = 1;
				counterPartId = indexOfPart;
			}
		}
		return result;
	}
}
