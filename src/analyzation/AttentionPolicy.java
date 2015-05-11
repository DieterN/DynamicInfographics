package analyzation;

import main.Statics;
import dataTypes.SensorDataPacket;

/**
 * Class responsible for calculating whether an extra part should be shown based on attention output of the sensor.
 * 
 * @author Dieter
 *
 */
public class AttentionPolicy extends Policy{

	private double currentAverage = -1;
	private int maxValue = 100;

	@Override
	public boolean showExtraPart(SensorDataPacket packet) {
		int attention = packet.getAttentionValue();
		if(currentAverage == -1){
			currentAverage = attention;
		}
		else{
			currentAverage = Statics.alpha*attention + (1-Statics.alpha)*currentAverage;
		}
		return currentAverage >= Statics.minPolicyValue*maxValue/100;
	}
}
