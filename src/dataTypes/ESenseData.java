package dataTypes;

import brainwave.ConnectionStatus;

/**
 * Represents eSense data from the brainwave sensor:
 * 
 * - status of the connection
 * - measured attentionValue
 * - measured meditationValue
 * 
 * @author Dieter
 *
 */
public class ESenseData{
	
	private ConnectionStatus status;
	private int attentionValue;
	private int meditationValue;
	
	/**
	 * Create a new ESenseData object
	 * 
	 * @param status: status of the connection with the brainwave sensor
	 * @param attentionValue: measured attentionValue
	 * @param meditationValue: measured meditationValue
	 */
	public ESenseData(ConnectionStatus status, int attentionValue, int meditationValue){
		this.status = status;
		this.attentionValue = attentionValue;
		this.meditationValue = meditationValue;
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
}