/**
 * 
 */
package gui;

import gui.characters.CharacLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
	private JLabel background;
	private ImageIcon currentRoom;
	private ArrayList<CharacLabel> characters;
	private CharacLabel player;

	public GamePanel() {
		engine = new GameEngine();

		setSize(SIZE);

		setLayout(new BorderLayout());

		initComponents();

		add(screen, BorderLayout.CENTER);

		new Thread(this).start();
	}

	private void initComponents() {
		this.screen = new JPanel();

		this.screen.setBackground(Color.black);
		this.screen.setSize(SIZE);
		this.screen.setLayout(new BorderLayout());

		this.background = new JLabel(this.currentRoom);

		this.screen.add(this.background, BorderLayout.CENTER);
	}

	public JPanel getScreen() {
		return screen;
	}

	public JLabel getBackgroundLabel() {
		return background;
	}

	@Override
	public void run() {
		setSize(SIZE); // For AppletViewer, remove later.

		// Set up the graphics stuff, double-buffering.
		BufferedImage screen = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
		Graphics g = screen.getGraphics();
		Graphics appletGraphics = getGraphics();

		long delta = 0l;

		// Game loop.
		while (true) {
			long lastTime = System.nanoTime();

			g.setColor(Color.black);
			g.fillRect(0, 0, SIZE.width, SIZE.height);
			
			// Update the state (convert to seconds)
			engine.update((float)(delta / 1000000000.0));
			// Render the world
			engine.render(this);

			// Draw the entire results on the screen.
			appletGraphics.drawImage(screen, 0, 0, null);

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
	
	public boolean handleEvent(Event e) {
		return engine.handleEvent(e);
	}
}
