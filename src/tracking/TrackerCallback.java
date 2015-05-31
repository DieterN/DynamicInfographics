package tracking;

import dataTypes.SensorDataPacket;

/**
 * Interface that should be implemented by all classes that should be notified when a
 * SensorDataPacket has been generated.
 * 
 * @author Dieter
 *
 */
public interface TrackerCallback {

	public void sendSensorDataPacket(SensorDataPacket packet);
	
}
