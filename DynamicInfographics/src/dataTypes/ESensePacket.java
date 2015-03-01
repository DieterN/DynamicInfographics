package dataTypes;

import java.awt.Point;

public class ESensePacket {
	
	private ESenseData data;
	private Point position;
	
	public ESensePacket(ESenseData data, Point position) {
		this.data = data;
		this.position = position;
	}

	public ESenseData getData() {
		return data;
	}

	public void setData(ESenseData data) {
		this.data = data;
	}
	
	public int getAttentionValue(){
		return data.getAttentionValue();
	}

	public int getMeditationValue(){
		return data.getMeditationValue();
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
