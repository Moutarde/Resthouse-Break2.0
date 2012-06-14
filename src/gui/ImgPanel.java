/**
 * 
 */
package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author Nicolas
 *
 */
public class ImgPanel extends JPanel {

	private static final long serialVersionUID = -2930806243663014819L;
	
	private BufferedImage bgImage;
	
	public ImgPanel(BufferedImage bgImage) {
		super();
		
		this.bgImage = bgImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(bgImage, 0, 0, this);
	}

	public BufferedImage getBgImage() {
		return bgImage;
	}

	public void setBgImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}

}
