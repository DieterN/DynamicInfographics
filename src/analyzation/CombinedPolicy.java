package analyzation;

import main.Statics;
import dataTypes.SensorDataPacket;

/**
 * Class responsible for calculating whether an extra part should be shown based on attention and meditation output of the sensor.
 * 
 * @author Dieter
 *
 */
public class CombinedPolicy extends Policy{

	private double currentAttentionAverage = -1;
	private double currentMeditationAverage = -1;
	private int maxValue = 200;

	@Override
	public boolean showExtraPart(SensorDataPacket packet) {
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
		return (currentAttentionAverage + currentMeditationAverage >= Statics.minPolicyValue*maxValue/100);
	}
}
