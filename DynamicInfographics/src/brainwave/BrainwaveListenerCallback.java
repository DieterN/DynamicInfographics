package brainwave;

import dataTypes.ESenseData;

public interface BrainwaveListenerCallback {

	public void sendESenseMeasurement(ESenseData eSenseData);
}
