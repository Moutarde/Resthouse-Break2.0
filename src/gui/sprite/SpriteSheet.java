/**
 * 
 */
package gui.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Coord;
import model.Resource;

/**
 * @author Nicolas Kniebihler
 *
 */
public class SpriteSheet {
	private BufferedImage image;
	
	private int spriteWidth;
	private int spriteHeight;
	
	public SpriteSheet(Resource sheet, int spriteWidth, int spriteHeight) {
		try {
			image = ImageIO.read(new File(sheet.path()));
		} catch (IOException e) {
			System.out.println("URL does'nt exist...");
			e.printStackTrace();
		}
		this.spriteHeight = spriteHeight;
		this.spriteWidth = spriteWidth;
	}
	
	public Sprite getSprite(int x, int y) {
		return new Sprite(image.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight), spriteWidth/3, spriteHeight/4, 3, 5);
	}

	public Sprite getSprite(Coord spriteCoord) {
		return getSprite(spriteCoord.getX(), spriteCoord.getY());
	}
}
