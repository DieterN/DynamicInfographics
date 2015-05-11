package brainwave;

/**
 * Enum representing possible connectionstatus of the sensor
 * 
 * @author Dieter
 *
 */
public enum ConnectionStatus {

	CONNECTED, //connected to sensor
	BAD_CONNECTION, //connected to sensor, but getting 0 values
	POOR_SIGNAL, //poor signal events
	NOT_CONNECTED; //not connected
	
	/**
	 * Return a string based on the given ConnectionStatus
	 * 
	 * @param status: based on this ConnectionStatus, a string is returned
	 * @return string based on the given ConnectionStatus 
	 */
	public static String toString(ConnectionStatus status){
		switch(status){
		case CONNECTED:
			return "Connected";
		case BAD_CONNECTION:
			return "Bad Connection";
		case POOR_SIGNAL:
			return "Poor Signal";
		default:
			return "Not Connected";
		}
	}
}
