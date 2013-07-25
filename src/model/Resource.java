/**
 *
 */
package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Resource {
    public static final Resource HOME = new Resource("/other/RHB.jpg");
    public static final Resource SPRITE_SHEET = new Resource("/other/spriteSheet.png");

    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(getClass().getResource(this.path));
    }

    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            System.out.println("URL does'nt exist : " + path);
            e.printStackTrace();
        }
        return null;
    }
}
