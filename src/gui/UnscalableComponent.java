package gui;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * Represents components that can't be scaled automatically.
 * 
 * @author Dieter
 *
 */
public class UnscalableComponent extends JComponent{

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
	 * Set size of this component to given dimension. 
	 * The component will have the given size and won't be scaled.
	 * 
	 * @param dimension: dimension that indicates the new size of this component
	 */
	public void setSizeTo(Dimension dimension) {
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
	}
}
