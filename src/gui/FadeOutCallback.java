package gui;

/**
 * Interface to be implemented by all classes that want to be notified
 * when the shown extra part has faded out.
 * 
 * @author Dieter
 */
public interface FadeOutCallback {

	/**
	 * Called when the shown extra part has faded out.
	 */
	public void fadeOutFinished();
}
