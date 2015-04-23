package tracking;

import dataTypes.ESensePacket;
import dataTypes.EegPacket;
import dataTypes.SensorDataPacket;

public interface TrackerCallback {

	public void sendSensorDataPacket(SensorDataPacket packet);
	
	public void sendESensePacket(ESensePacket packet);
	
	public void sendEegPacket(EegPacket packet);
}
