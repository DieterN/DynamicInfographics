package brainwave;

public enum ConnectionStatus {

	CONNECTED, //connected to sensor
	BAD_CONNECTION, //connected to sensor, but getting 0 values
	POOR_SIGNAL, //poor signal events
	NOT_CONNECTED; //not connected
	
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
