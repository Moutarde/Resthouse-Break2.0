/**
 *
 */
package gui.sprite;

import java.awt.image.BufferedImage;

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
        image = sheet.getBufferedImage();
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
