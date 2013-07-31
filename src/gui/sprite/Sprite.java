/**
 *
 */
package gui.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Coord;

/**
 * A sprite to be displayed on the screen. Note that a sprite
 * contains no state information, i.e. its just the image and
 * not the location. This allows us to use a single sprite in
 * lots of different places without having to store multiple
 * copies of the image.
 *
 * @author Kevin Glass, modified by Nicolas Kniebihler
 */
public class Sprite {
    /** The image to be drawn for this sprite */
    private BufferedImage image;

    private int objectWidth;
    private int objectHeight;
    private int offsetX = 0;
    private int offsetY = 0;

    /**
     * Create a new sprite based on an image
     *
     * @param image The image that is this sprite
     */
    public Sprite(BufferedImage image, int objectWidth, int objectHeight) {
        this.image = image;
        this.objectHeight = objectHeight;
        this.objectWidth = objectWidth;
    }

    /**
     * Create a new sprite with an offset
     *
     * @param image The image that is this sprite
     */
    public Sprite(BufferedImage image, int objectWidth, int objectHeight, int offsetX, int offsetY) {
        this(image, objectWidth, objectHeight);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * Get the width of the drawn sprite
     *
     * @return The width in pixels of this sprite
     */
    public int getWidth() {
        return image.getWidth(null);
    }

    /**
     * Get the height of the drawn sprite
     *
     * @return The height in pixels of this sprite
     */
    public int getHeight() {
        return image.getHeight(null);
    }

    public BufferedImage getObject(Posture p) {
        return image.getSubimage(p.getCoord().getX() * objectWidth, p.getCoord().getY() * objectHeight, objectWidth, objectHeight);
    }

    public Coord getOffset() {
        return new Coord(offsetX, offsetY);
    }

    /**
     * Draw the sprite onto the graphics context provided
     *
     * @param g The graphics context on which to draw the sprite
     * @param x The x location at which to draw the sprite
     * @param y The y location at which to draw the sprite
     * @param posture The posture of the object
     */
    public void draw(Graphics g, int x, int y, Posture p) {
        g.drawImage(getObject(p), x, y, null);
    }
}
