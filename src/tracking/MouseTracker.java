package tracking;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.List;

/**
 * 
 * Class that is responsible for tracking the mouse on the screen.
 * 
 * @author Dieter
 *
 */
public class MouseTracker extends Tracker{

	
	public MouseTracker(List<TrackerCallback> callbacks) {
		super(callbacks);
	}
	
	@Override
	public Point getCurrentFocusPoint() {
		return MouseInfo.getPointerInfo().getLocation();
	}

}
