package dataTypes;

import java.awt.Point;

/**
 * Packet containing data of the sensor (SensorData object)
 * and the place on the screen where the mousePointer was when this data was collected (Point object).
 * 
 * @author Dieter
 *
 */
public class SensorDataPacket {
	
	private SensorData data;
	private Point position;
	
	/**
	 * Constructor for a SensorDataPacket.
	 * 
	 * @param data: data of the sensor
	 * @param position: place on the screen where the mousePointer was when this data was collected
	 */
	public SensorDataPacket(SensorData data, Point position) {
		this.data = data;
		this.position = position;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public SensorData getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(SensorData data) {
		this.data = data;
	}
	
	/**
	 * Gets the attention value.
	 *
	 * @return the attention value
	 */
	public int getAttentionValue(){
		return data.getAttentionValue();
	}

	/**
	 * Gets the meditation value.
	 *
	 * @return the meditation value
	 */
	public int getMeditationValue(){
		return data.getMeditationValue();
	}
	
	/**
	 * Gets the delta.
	 *
	 * @return the delta
	 */
	public int getDelta(){
		return data.getDelta();
	}

	/**
	 * Gets the theta.
	 *
	 * @return the theta
	 */
	public int getTheta(){
		return data.getTheta();
	}
	
	/**
	 * Gets the low alpha.
	 *
	 * @return the low alpha
	 */
	public int getLowAlpha(){
		return data.getLowAlpha();
	}
	
	/**
	 * Gets the high alpha.
	 *
	 * @return the high alpha
	 */
	public int getHighAlpha(){
		return data.getHighAlpha();
	}
	
	/**
	 * Gets the low beta.
	 *
	 * @return the low beta
	 */
	public int getLowBeta(){
		return data.getLowBeta();
	}
	
	/**
	 * Gets the high beta.
	 *
	 * @return the high beta
	 */
	public int getHighBeta(){
		return data.getHighBeta();
	}
	
	/**
	 * Gets the low gamma.
	 *
	 * @return the low gamma
	 */
	public int getLowGamma(){
		return data.getLowGamma();
	}
	
	/**
	 * Gets the high gamma.
	 *
	 * @return the high gamma
	 */
	public int getHighGamma(){
		return data.getHighGamma();
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
}