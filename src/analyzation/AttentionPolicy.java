package analyzation;

import main.Statics;
import dataTypes.SensorDataPacket;

public class AttentionPolicy extends Policy{

	private double currentAverage = -1;

	@Override
	public boolean showExtraPart(SensorDataPacket packet) {
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
