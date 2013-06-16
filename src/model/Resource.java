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
 * @author Nicolas Kniebihler
 *
 */
public class Resource {
	public static final Resource HOME = new Resource("src/resources/other/RHB.jpg");
	public static final Resource SPRITE_SHEET = new Resource("src/resources/other/spriteSheet.png");
	
	private final String path;

	public Resource(String path) {
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
