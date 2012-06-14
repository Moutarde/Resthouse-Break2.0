/**
 * 
 */
package model;

import javax.swing.ImageIcon;

/**
 * @author Nicolas
 *
 */
public enum Resources {
	HOME	("src/resources/other/RHB.jpg");
	
	private final String path;

	private Resources(String path) {
		this.path = path;
	}

	public String path() {
		return path;
	}
	
	public ImageIcon getImageIcon() {
		return new ImageIcon(this.path);
	}
}
