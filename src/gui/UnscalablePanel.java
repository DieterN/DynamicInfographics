package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Represents panels that can't be scaled automatically.
 * 
 * @author Dieter
 *
 */
public class UnscalablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	@Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();
        Dimension minimum = getMinimumSize();
        Dimension maximum = getMaximumSize();
        preferred.width = Math.min(Math.max(preferred.width, minimum.width), 
            maximum.width);
        preferred.height = Math.min(Math.max(preferred.height, minimum.height), 
            maximum.height);
        return preferred;
    }
	
	/**
	 * Set size of this panel to given dimension. 
	 * The panel will have the given size and won't be scaled.
	 * 
	 * @param dimension: dimension that indicates the new size of this panel
	 */

	public void setSizeTo(Dimension dimension) {
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
	}
	
}
