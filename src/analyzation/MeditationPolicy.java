package analyzation;

import main.Statics;
import dataTypes.ESensePacket;

public class MeditationPolicy extends Policy{

	private double currentAverage = -1;
	
	@Override
	public boolean showExtraPart(ESensePacket packet) {
		int meditation = packet.getMeditationValue();
		if(currentAverage == -1){
			currentAverage = meditation;
		}
		else{
			currentAverage = Statics.alpha*meditation + (1-Statics.alpha)*currentAverage;
		}
		return currentAverage >= Statics.minMeditationValue;
	}
}
