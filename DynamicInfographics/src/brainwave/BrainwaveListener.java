package brainwave;

import java.net.ConnectException;
import java.util.List;

import com.neurosky.developer.connector.ThinkGearListener;
import com.neurosky.developer.connector.ThinkGearSocketChanged;

import dataTypes.ESenseData;

/**
 * Gets called when the brainwave sensor made a measurement.s
 * 
 * @author Dieter
 *
 */
public class BrainwaveListener implements ThinkGearListener {
	
	private List<BrainwaveListenerCallback> callbacks;
	
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
	
	private void informCallbacks(ESenseData data){
		for(BrainwaveListenerCallback callback: callbacks){
			callback.sendESenseMeasurement(data);
		}
	}
	
	@Override
	public void eSenseEvent(int attentionValue, int meditationValue) {
		if(attentionValue != 0 && meditationValue != 0)
			informCallbacks(new ESenseData(ConnectionStatus.CONNECTED, attentionValue, meditationValue));
		else
			informCallbacks(new ESenseData(ConnectionStatus.BAD_CONNECTION, attentionValue, meditationValue));
	}

	@Override
	public void attentionEvent(int valor) {
//		System.out.println("attentionEvent: "+valor);
	}

	@Override
	public void meditationEvent(int valor) {
//		System.out.println("meditationEvent: "+valor);
	}

	@Override
	public void poorSignalEvent(int valor) {
//		informCallbacks(new ESenseData(ConnectionStatus.POOR_SIGNAL, 0, 0));
//		System.out.println("poorsignalEvent: "+valor);
	}

	@Override
	public void blinkEvent(int valor) {
//		System.out.println("blinkEvent: "+valor);
	}

	@Override
	public void eegEvent(int v1, int v2, int v3, int v4, int v5, int v6,
			int v7, int v8) {
//		System.out.println("eegEvent: "+v1+","+v2+","+v3+","+v4+","+v5+","+v6+","+v7+","+v8);
	}

	@Override
	public void rawEvent(int[] valores) {
//		System.out.println("rawEvent: "+valores);
	}
}
