package gui;

import java.awt.Dimension;

import javax.swing.JButton;

public class UnscalableButton extends JButton{

	/**
	 * 
	 */
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

	public void setSizeTo(Dimension dimension) {
		this.setMaximumSize(dimension);
		this.setMinimumSize(dimension);
	}
}
