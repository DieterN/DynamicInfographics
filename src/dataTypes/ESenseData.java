package dataTypes;

import brainwave.ConnectionStatus;

public class ESenseData {
	
	private ConnectionStatus status;
	private int attentionValue;
	private int meditationValue;
	
	public ESenseData(ConnectionStatus status, int attentionValue, int meditationValue) {
		this.status = status;
		this.attentionValue = attentionValue;
		this.meditationValue = meditationValue;
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
}
