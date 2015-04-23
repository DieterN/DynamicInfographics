package dataTypes;

import brainwave.ConnectionStatus;

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

	public ConnectionStatus getStatus() {
		return status;
	}

	public void setStatus(ConnectionStatus status) {
		this.status = status;
	}

	public int getAttentionValue() {
		return attentionValue;
	}

	public void setAttentionValue(int attentionValue) {
		this.attentionValue = attentionValue;
	}

	public int getMeditationValue() {
		return meditationValue;
	}

	public void setMeditationValue(int meditationValue) {
		this.meditationValue = meditationValue;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getTheta() {
		return theta;
	}

	public void setTheta(int theta) {
		this.theta = theta;
	}

	public int getLowAlpha() {
		return lowAlpha;
	}

	public void setLowAlpha(int lowAlpha) {
		this.lowAlpha = lowAlpha;
	}

	public int getHighAlpha() {
		return highAlpha;
	}

	public void setHighAlpha(int highAlpha) {
		this.highAlpha = highAlpha;
	}

	public int getLowBeta() {
		return lowBeta;
	}

	public void setLowBeta(int lowBeta) {
		this.lowBeta = lowBeta;
	}

	public int getHighBeta() {
		return highBeta;
	}

	public void setHighBeta(int highBeta) {
		this.highBeta = highBeta;
	}

	public int getLowGamma() {
		return lowGamma;
	}

	public void setLowGamma(int lowGamma) {
		this.lowGamma = lowGamma;
	}

	public int getHighGamma() {
		return highGamma;
	}

	public void setHighGamma(int highGamma) {
		this.highGamma = highGamma;
	}

}
