package tracking;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.Statics;
import dataTypes.SensorData;
import dataTypes.SensorDataPacket;
import brainwave.ConnectionStatus;

public abstract class Tracker{
	
	private List<TrackerCallback> callbacks = new ArrayList<TrackerCallback>();
	
	/**
	 * Coordinates on the whole screen, should be taken care of in other class!
	 * 
	 * @return
	 */
	public abstract Point getCurrentFocusPoint(); //gaze or mouse

	public Tracker(List<TrackerCallback> callbacks) {
		super();
		this.callbacks = callbacks;
	}
	
	public void sendSensorAndScrollData(SensorData sensorData, int horizontalValue, int verticalValue) {
		if(sensorData.getStatus() == ConnectionStatus.CONNECTED){
			Point position = this.getCurrentFocusPoint();
			int positionX = position.x - Statics.frameOffsetX + horizontalValue;
			int positionY = position.y - Statics.frameOffsetY + verticalValue;
			SensorDataPacket packet = new SensorDataPacket(sensorData, new Point(positionX,positionY));
			for(TrackerCallback callback: callbacks){
				callback.sendSensorDataPacket(packet);
			}
		}
		//Don't inform rest of program if bad connection
	}
	
	public void addCallback(TrackerCallback callback){
		this.callbacks.add(callback);
	}
	
	public void removeCallback(TrackerCallback removeCallback){
		List<TrackerCallback> copyOfCallbacks = callbacks;
		for(TrackerCallback callback: copyOfCallbacks){
			if(removeCallback == callback){
				callbacks.remove(callback);
			}
		}
	}

}
