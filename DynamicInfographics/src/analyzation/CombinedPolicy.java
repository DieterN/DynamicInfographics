package analyzation;

import main.Statics;
import dataTypes.ESensePacket;

public class CombinedPolicy extends Policy{

	private double currentAttentionAverage = -1;
	private double currentMeditationAverage = -1;
	
	@Override
	public boolean showExtraPart(ESensePacket packet) {
		int attention = packet.getAttentionValue();
		int meditation = packet.getMeditationValue();
		if(currentAttentionAverage == -1){
			currentAttentionAverage = attention;
			currentMeditationAverage = meditation;
		}
		else{
			currentAttentionAverage = Statics.alpha*attention + (1-Statics.alpha)*currentAttentionAverage;
			currentMeditationAverage = Statics.alpha*meditation + (1-Statics.alpha)*currentMeditationAverage;
		}
		return (currentAttentionAverage + currentMeditationAverage >= Statics.minCombinedValue);
	}
}
