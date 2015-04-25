package analyzation;

import main.Statics;
import dataTypes.SensorDataPacket;

public class MeditationPolicy extends Policy{

	private double currentAverage = -1;
	private int maxValue = 100;

	@Override
	public boolean showExtraPart(SensorDataPacket packet) {
		int meditation = packet.getMeditationValue();
		if(currentAverage == -1){
			currentAverage = meditation;
		}
		else{
			currentAverage = Statics.alpha*meditation + (1-Statics.alpha)*currentAverage;
		}
		return currentAverage >= Statics.minPolicyValue*maxValue/100;
	}
}
