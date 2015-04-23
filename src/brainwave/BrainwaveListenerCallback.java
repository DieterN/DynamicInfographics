package brainwave;

import dataTypes.ESenseData;
import dataTypes.EegData;
import dataTypes.SensorData;

public interface BrainwaveListenerCallback {

	public void sendSensorData(SensorData sensorData);

	public void sendESenseData(ESenseData eSenseData);
	
	public void sendEegData(EegData eegData);
}
