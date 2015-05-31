package tracking;

import java.awt.Point;
import java.util.List;

/**
 * Dummy class, can be used in the future to use eyetracking instead of mousetracking
 * 
 * @author Dieter
 */
public class EyeTracker extends Tracker{

	/**
	 * Dummy constructor to create a new EyeTracker
	 * 
	 * @param callbacks: all classes that should be notified of the position on the screen 
	 */
	public EyeTracker(List<TrackerCallback> callbacks) {
		super(callbacks);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point getCurrentFocusPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
