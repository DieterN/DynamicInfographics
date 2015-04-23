package brainwave;

import dataTypes.SensorData;

public interface BrainwaveListenerCallback {

	public void sendSensorData(SensorData sensorData);
	
}
