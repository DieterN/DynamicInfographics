package analyzation;

public enum PolicyType {
	
	ATTENTION, //attention policy
	MEDITATION, //meditation policy
	COMBINED, //combined policy
	UNDEFINED; //undefined policy = no policy

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
