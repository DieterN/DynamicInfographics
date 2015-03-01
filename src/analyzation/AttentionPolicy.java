package analyzation;

import main.Statics;
import dataTypes.ESensePacket;

public class AttentionPolicy extends Policy{

	private double currentAverage = -1;
	
	@Override
	public boolean showExtraPart(ESensePacket packet) {
		int attention = packet.getAttentionValue();
		if(currentAverage == -1){
			currentAverage = attention;
		}
		else{
			currentAverage = Statics.alpha*attention + (1-Statics.alpha)*currentAverage;
		}
		return currentAverage >= Statics.minAttentionValue;
	}
}
