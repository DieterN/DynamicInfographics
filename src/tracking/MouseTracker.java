package tracking;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;

/**
 * Class that is responsible for tracking the mouse on the screen.
 * 
 * @author Dieter
 */
public class MouseTracker extends Tracker{

	/**
	 * Create a new MouseTracker
	 * 
	 * @param callbacks: all classes that should be notified of the position on the screen
	 */
	public MouseTracker(List<TrackerCallback> callbacks) {
		super(callbacks);
	}
	
	@Override
	public Point getCurrentFocusPoint() {
		return MouseInfo.getPointerInfo().getLocation();
	}

}
