package brainwave;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.neurosky.developer.connector.ThinkGearListener;
import com.neurosky.developer.connector.ThinkGearSocketChanged;

import dataTypes.ESenseData;
import dataTypes.SensorData;

/**
 * Gets called when the brainwave sensor made a measurement.s
 * 
 * @author Dieter
 *
 */
public class BrainwaveListener implements ThinkGearListener {
	
	private List<BrainwaveListenerCallback> callbacks;
	private ConcurrentLinkedQueue<ESenseData> queue = new ConcurrentLinkedQueue<ESenseData>();
	
	public BrainwaveListener(List<BrainwaveListenerCallback> callbacks) {
		this.callbacks = callbacks;
	}

	public void startSensor() {
		ThinkGearSocketChanged socket = new ThinkGearSocketChanged(this);
		try {
			socket.start();
		} catch (ConnectException e) {
			socket.stop();
			e.printStackTrace();
		}
	}
	
	private void informCallbacks(SensorData data){
		for(BrainwaveListenerCallback callback: callbacks){
			callback.sendSensorData(data);
		}
	}
	
	@Override
	public void eSenseEvent(int attentionValue, int meditationValue) {
		ESenseData data = null;
		if(attentionValue != 0 && meditationValue != 0){
			data = new ESenseData(ConnectionStatus.CONNECTED, attentionValue, meditationValue);
		}
		else{
			data = new ESenseData(ConnectionStatus.BAD_CONNECTION, attentionValue, meditationValue);
		}
		queue.add(data);
	}

	@Override
	public void attentionEvent(int valor) {
//		System.out.println("attentionEvent: "+value);
	}

	@Override
	public void meditationEvent(int valor) {
//		System.out.println("meditationEvent: "+value);
	}

	@Override
	public void poorSignalEvent(int valor) {
//		informCallbacks(new ESenseData(ConnectionStatus.POOR_SIGNAL, 0, 0));
//		System.out.println("poorsignalEvent: "+value);
	}

	@Override
	public void blinkEvent(int valor) {
//		System.out.println("blinkEvent: "+value);
	}

	@Override
	public void eegEvent(int delta, int theta, int lowAlpha, int highAlpha, int lowBeta, int highBeta, int lowGamma, int highGamma) {
		ESenseData eSenseData = queue.poll();
		SensorData data = new SensorData(eSenseData.getStatus(), eSenseData.getAttentionValue(), eSenseData.getMeditationValue(), 
										 delta, theta, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma);
		informCallbacks(data);
	}

	@Override
	public void rawEvent(int[] valores) {
//		System.out.println("rawEvent: "+values);
	}
}
