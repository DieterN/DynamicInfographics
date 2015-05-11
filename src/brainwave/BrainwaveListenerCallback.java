package brainwave;

import dataTypes.SensorData;

/**
 * Interface that should be implemented by all classes that want to be notified when the brainwave sensor has made a measurement.
 * 
 * @author Dieter
 *
 */
public interface BrainwaveListenerCallback {

	public void sendSensorData(SensorData sensorData);
	
}
