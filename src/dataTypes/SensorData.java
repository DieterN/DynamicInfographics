package dataTypes;

import brainwave.ConnectionStatus;

/**
 * Represents eSense and eeg data from the brainwave sensor:
 * 
 * - status of the connection
 * - measured attentionValue
 * - measured meditationValue
 * - measured value for delta wave
 * - measured value for theta wave
 * - measured value for lowAlpha wave
 * - measured value for highAlpha wave
 * - measured value for lowBeta wave
 * - measured value for highBeta wave
 * - measured value for lowGamma wave
 * - measured value for highGamma wave
 * 
 * @author Dieter
 *
 */

public class SensorData {
	
	private ConnectionStatus status;
	private int attentionValue;
	private int meditationValue;
	
	private int delta;
	private int theta;
	private int lowAlpha;
	private int highAlpha; 
	private int lowBeta; 
	private int highBeta; 
	private int lowGamma;
	private int highGamma;
	
	/**
	 * Constructor for SensorData object
	 * 
	 * @param status: status of the connection
	 * @param attentionValue: measured attentionValue
	 * @param meditationValue: measured meditationValue
	 * @param delta: measured value for delta wave
	 * @param theta: measured value for theta wave
	 * @param lowAlpha: measured value for lowAlpha wave
	 * @param highAlpha: measured value for highAlpha wave
	 * @param lowBeta: measured value for lowBeta wave
	 * @param highBeta: measured value for highBeta wave
	 * @param lowGamma: measured value for lowGamma wave
	 * @param highGamma: measured value for highGamma wave
	 */
	public SensorData(ConnectionStatus status, int attentionValue, int meditationValue, int delta, int theta, 
			int lowAlpha,	int highAlpha, int lowBeta, int highBeta, int lowGamma,	int highGamma) {
		this.status = status;
		this.attentionValue = attentionValue;
		this.meditationValue = meditationValue;
		this.delta = delta;
		this.theta = theta;
		this.lowAlpha = lowAlpha;
		this.highAlpha = highAlpha;
		this.lowBeta = lowBeta;
		this.highBeta = highBeta;
		this.lowGamma = lowGamma;
		this.highGamma = highGamma;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public ConnectionStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(ConnectionStatus status) {
		this.status = status;
	}

	/**
	 * Gets the attention value.
	 *
	 * @return the attention value
	 */
	public int getAttentionValue() {
		return attentionValue;
	}

	/**
	 * Sets the attention value.
	 *
	 * @param attentionValue the new attention value
	 */
	public void setAttentionValue(int attentionValue) {
		this.attentionValue = attentionValue;
	}

	/**
	 * Gets the meditation value.
	 *
	 * @return the meditation value
	 */
	public int getMeditationValue() {
		return meditationValue;
	}

	/**
	 * Sets the meditation value.
	 *
	 * @param meditationValue the new meditation value
	 */
	public void setMeditationValue(int meditationValue) {
		this.meditationValue = meditationValue;
	}

	/**
	 * Gets the delta.
	 *
	 * @return the delta
	 */
	public int getDelta() {
		return delta;
	}

	/**
	 * Sets the delta.
	 *
	 * @param delta the new delta
	 */
	public void setDelta(int delta) {
		this.delta = delta;
	}

	/**
	 * Gets the theta.
	 *
	 * @return the theta
	 */
	public int getTheta() {
		return theta;
	}

	/**
	 * Sets the theta.
	 *
	 * @param theta the new theta
	 */
	public void setTheta(int theta) {
		this.theta = theta;
	}

	/**
	 * Gets the low alpha.
	 *
	 * @return the low alpha
	 */
	public int getLowAlpha() {
		return lowAlpha;
	}

	/**
	 * Sets the low alpha.
	 *
	 * @param lowAlpha the new low alpha
	 */
	public void setLowAlpha(int lowAlpha) {
		this.lowAlpha = lowAlpha;
	}

	/**
	 * Gets the high alpha.
	 *
	 * @return the high alpha
	 */
	public int getHighAlpha() {
		return highAlpha;
	}

	/**
	 * Sets the high alpha.
	 *
	 * @param highAlpha the new high alpha
	 */
	public void setHighAlpha(int highAlpha) {
		this.highAlpha = highAlpha;
	}

	/**
	 * Gets the low beta.
	 *
	 * @return the low beta
	 */
	public int getLowBeta() {
		return lowBeta;
	}

	/**
	 * Sets the low beta.
	 *
	 * @param lowBeta the new low beta
	 */
	public void setLowBeta(int lowBeta) {
		this.lowBeta = lowBeta;
	}

	/**
	 * Gets the high beta.
	 *
	 * @return the high beta
	 */
	public int getHighBeta() {
		return highBeta;
	}

	/**
	 * Sets the high beta.
	 *
	 * @param highBeta the new high beta
	 */
	public void setHighBeta(int highBeta) {
		this.highBeta = highBeta;
	}

	/**
	 * Gets the low gamma.
	 *
	 * @return the low gamma
	 */
	public int getLowGamma() {
		return lowGamma;
	}

	/**
	 * Sets the low gamma.
	 *
	 * @param lowGamma the new low gamma
	 */
	public void setLowGamma(int lowGamma) {
		this.lowGamma = lowGamma;
	}

	/**
	 * Gets the high gamma.
	 *
	 * @return the high gamma
	 */
	public int getHighGamma() {
		return highGamma;
	}

	/**
	 * Sets the high gamma.
	 *
	 * @param highGamma the new high gamma
	 */
	public void setHighGamma(int highGamma) {
		this.highGamma = highGamma;
	}

}
