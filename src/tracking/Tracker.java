package tracking;

import java.awt.Point;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import main.Statics;
import dataTypes.ESenseData;
import dataTypes.ESensePacket;
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
	
	public void sendESenseAndScrollData(ESenseData eSenseData, int horizontalValue, int verticalValue) {
		if(eSenseData.getStatus() == ConnectionStatus.CONNECTED){
			Point position = this.getCurrentFocusPoint();
			int positionX = position.x - Statics.frameOffsetX + horizontalValue;
			int positionY = position.y - Statics.frameOffsetY + verticalValue;
			ESensePacket packet = new ESensePacket(eSenseData, new Point(positionX,positionY));
			for(TrackerCallback callback: callbacks){
				callback.sendESensePacket(packet);
			}
		}
		//Don't inform rest of program if bad connection
	}
	
	public void addCallback(TrackerCallback callback){
		this.callbacks.add(callback);
	}

}
