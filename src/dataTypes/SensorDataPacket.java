package dataTypes;

import java.awt.Point;

public class SensorDataPacket {
	
	private SensorData data;
	private Point position;
	
	public SensorDataPacket(SensorData data, Point position) {
		this.data = data;
		this.position = position;
	}

	public SensorData getData() {
		return data;
	}

	public void setData(SensorData data) {
		this.data = data;
	}
	
	public int getAttentionValue(){
		return data.getAttentionValue();
	}

	public int getMeditationValue(){
		return data.getMeditationValue();
	}
	
	public int getDelta(){
		return data.getDelta();
	}

	public int getTheta(){
		return data.getTheta();
	}
	
	public int getLowAlpha(){
		return data.getLowAlpha();
	}
	
	public int getHighAlpha(){
		return data.getHighAlpha();
	}
	
	public int getLowBeta(){
		return data.getLowBeta();
	}
	
	public int getHighBeta(){
		return data.getHighBeta();
	}
	
	public int getLowGamma(){
		return data.getLowGamma();
	}
	
	public int getHighGamma(){
		return data.getHighGamma();
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}