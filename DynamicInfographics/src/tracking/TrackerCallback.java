package tracking;

import dataTypes.ESensePacket;

public interface TrackerCallback {

	public void sendESensePacket(ESensePacket packet);
}
