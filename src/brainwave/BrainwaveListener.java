package brainwave;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.neurosky.developer.connector.ThinkGearListener;
import com.neurosky.developer.connector.ThinkGearSocketChanged;

import dataTypes.ESenseData;
import dataTypes.EegData;
import dataTypes.SensorData;

/**
 * Gets called when the brainwave sensor made a measurement.s
 * 
 * @author Dieter
 *
 */
public class BrainwaveListener implements ThinkGearListener {
	
	private List<BrainwaveListenerCallback> callbacks;
	private ConnectionStatus status;
	private ConcurrentLinkedQueue<ESenseData> queue = new ConcurrentLinkedQueue<ESenseData>();
	
	public BrainwaveListener(List<BrainwaveListenerCallback> callbacks) {
		this.callbacks = callbacks;
		status = ConnectionStatus.NOT_CONNECTED;
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
	
	private void informCallbacksESenseData(ESenseData data){
		for(BrainwaveListenerCallback callback: callbacks){
			callback.sendESenseData(data);
		}
	}
	
	private void informCallbacksEegData(EegData data){
		for(BrainwaveListenerCallback callback: callbacks){
			callback.sendEegData(data);
		}
	}
	
//	@Override
//	public void eSenseEvent(int attentionValue, int meditationValue) {
//		System.out.println("ESense");
//		if(attentionValue != 0 && meditationValue != 0){
//			this.status = ConnectionStatus.CONNECTED;
//			informCallbacksESenseData(new ESenseData(ConnectionStatus.CONNECTED, attentionValue, meditationValue));
//		}
//		else{
//			this.status = ConnectionStatus.BAD_CONNECTION;
//			informCallbacksESenseData(new ESenseData(ConnectionStatus.BAD_CONNECTION, attentionValue, meditationValue));
//		}
//	}
	
	@Override
	public void eSenseEvent(int attentionValue, int meditationValue) {
		System.out.println("ESense");
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
		System.out.println("EEG");
		ESenseData eSenseData = queue.poll();
		SensorData data = new SensorData(eSenseData.getStatus(), eSenseData.getAttentionValue(), eSenseData.getMeditationValue(), 
										 delta, theta, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma);
		informCallbacks(data);
//		informCallbacksEegData(new EegData(status, delta, theta, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma));
	}

	@Override
	public void rawEvent(int[] valores) {
//		System.out.println("rawEvent: "+values);
	}
}
