/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.GameEngine;

/**
 * @author Nicolas
 * 
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = -8860204251354754377L;

	public static final Dimension SIZE = new Dimension(300, 300);

	private GameEngine engine;
	private JPanel screen;

	public GamePanel() {
		super();

		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				requestFocus();
			}
		});
		
		engine = new GameEngine();
		addKeyListener(engine.getKeyboard());

		setPreferredSize(SIZE);

		setLayout(new BorderLayout());

		initComponents();

		add(screen, BorderLayout.CENTER);
	}

	private void initComponents() {
		screen = new JPanel();

		screen.setBackground(Color.black);
		screen.setPreferredSize(SIZE);
	}

	public JPanel getScreen() {
		return screen;
	}

	@Override
	public void run() {
		// Set up the graphics stuff, double-buffering.
		BufferedImage image = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Graphics appletGraphics = screen.getGraphics();
		
		requestFocus();

		long delta = 0l;

		// Game loop.
		while (true) {
			long lastTime = System.nanoTime();

			g.setColor(Color.black);
			g.fillRect(0, 0, SIZE.width, SIZE.height);
			
			// Update the state (convert to seconds)
			engine.update((float)(delta / 1000000000.0));
			// Render the world
			engine.render(g);

			// Draw the entire results on the screen.
			appletGraphics.drawImage(image, 0, 0, null);

			// Lock the frame rate
			delta = System.nanoTime() - lastTime;
			if (delta < 20000000L) {
				try {
					Thread.sleep((20000000L - delta) / 1000000L);
				} catch (Exception e) {
					// It's an interrupted exception, and nobody cares
				}
			}
		}
	}
}
