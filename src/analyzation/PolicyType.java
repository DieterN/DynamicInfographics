package analyzation;

/**
 * Enum representing the different types of policies that can be used to decide whether an extra part should be shown/hidden.
 * 
 * @author Dieter
 *
 */
public enum PolicyType {
	
	ATTENTION, //attention policy
	MEDITATION, //meditation policy
	COMBINED, //combined policy
	UNDEFINED; //undefined policy = no policy

	/**
	 * Returns a PolicyType based on the given string
	 * 
	 * @param policy: based on this string, a PolicyType is returned
	 * @return PolicyType based on given string
	 */
	public static PolicyType stringToPolicyType(String policy){
		switch(policy){
		case "Attention":
			return PolicyType.ATTENTION;
		case "Meditation":
			return PolicyType.MEDITATION;
		case "Combined":
			return PolicyType.COMBINED;
		default:
			return PolicyType.UNDEFINED;
		}
	}
	
	/**
	 * Returns a string based on the given PolicyType.
	 * 
	 * @param policy: based on this PolicyType, a string is returned
	 * @return string based on given PolicyType
	 */
	public static String toString(PolicyType policy){
		switch(policy){
		case ATTENTION:
			return "Attention";
		case MEDITATION:
			return "Meditation";
		case COMBINED:
			return "Combined";
		default:
			return "Undefined";
		}
	}
}
