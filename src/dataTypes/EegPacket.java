package dataTypes;

import java.awt.Point;

public class EegPacket {

	private EegData data;
	private Point position;
	
	public EegPacket(EegData data, Point position) {
		this.data = data;
		this.position = position;
	}

	public EegData getData() {
		return data;
	}

	public void setData(EegData data) {
		this.data = data;
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
