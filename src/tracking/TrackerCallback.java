package tracking;

import dataTypes.SensorDataPacket;

public interface TrackerCallback {

	public void sendSensorDataPacket(SensorDataPacket packet);
	
}
