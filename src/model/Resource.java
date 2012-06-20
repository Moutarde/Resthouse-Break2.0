/**
 * 
 */
package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Nicolas
 *
 */
public enum Resource {
	HOME		("src/resources/other/RHB.jpg"),
	R_GINETTE	("src/resources/room/ginetteRoom.jpg");
	
	private final String path;

	private Resource(String path) {
		this.path = path;
	}

	public String path() {
		return path;
	}
	
	public ImageIcon getImageIcon() {
		return new ImageIcon(this.path);
	}
	
	public BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
