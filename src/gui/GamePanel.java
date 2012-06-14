/**
 * 
 */
package gui;

import gui.characters.CharacLabel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author Nicolas
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = -8860204251354754377L;

	private ImgPanel screen;
	private BufferedImage bgImage;
	private ArrayList<CharacLabel> characters;
	private CharacLabel player;
	
	public GamePanel() {
        screen = new ImgPanel(bgImage);
        
        this.add(screen);
	}
	
}
