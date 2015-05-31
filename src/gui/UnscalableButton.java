package gui;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Represents buttons that can't be scaled automatically.
 * 
 * @author Dieter
 *
 */
public class UnscalableButton extends JButton{

	private static final long serialVersionUID = 1L;

	public UnscalableButton(String string) {
		super(string);
	}

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
	 * Set size of this button to given dimension. 
	 * The button will have the given size and won't be scaled.
	 * 
	 * @param dimension: dimension that indicates the new size of this button
	 */
	public void setSizeTo(Dimension dimension) {
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
	}
}
