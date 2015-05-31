package tracking;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.Statics;
import dataTypes.SensorData;
import dataTypes.SensorDataPacket;
import brainwave.ConnectionStatus;

/**
 * Abstract superclass for all class that track where a user looks at on the screen
 * 
 * @author Dieter
 *
 */
public abstract class Tracker{
	
	private List<TrackerCallback> callbacks = new ArrayList<TrackerCallback>();

	/**
	 * Constructor for a Tracker.
	 * 
	 * @param callbacks: all classes that should be notified of the position on the screen
	 */
	public Tracker(List<TrackerCallback> callbacks) {
		super();
		this.callbacks = callbacks;
	}
	
	/**
	 * Call this method to send provided sensordata together
	 * with the current focuspoint within the infographic to all callbacks.
	 * The position is not the absolute position on the screen, but the position on the infographic! 
	 * 
	 * The scrollbar values are used to adjust to determine the right position within the infographic.
	 * 
	 * All information is send to the callbacks in a SensorDataPacket.
	 * 
	 * @param sensorData: data from the brainwave sensor
	 * @param horizontalValue: value of the horizontal scroll bar
	 * @param verticalValue: value of the vertical scroll bar
	 */
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
	
	/**
	 * Add a callback
	 * 
	 * @param callback: callback to be added
	 */
	public void addCallback(TrackerCallback callback){
		this.callbacks.add(callback);
	}
	
	/**
	 * Remove a callback
	 * 
	 * @param removeCallback: callback to be removed
	 */
	public void removeCallback(TrackerCallback removeCallback){
		int removeIndex = -1;
		for(int i = 0; i < callbacks.size(); i++){
			if(removeCallback == callbacks.get(i)){
				removeIndex = i;
			}
		}
		if(removeIndex != -1){
			callbacks.remove(removeIndex);
		}
	}

	/**
	 * Return the coordinates of the point where the user is currently looking at on the screen.
	 * 
	 * @return point where the user is currently looking at
	 */
	public abstract Point getCurrentFocusPoint(); //gaze or mouse
}
