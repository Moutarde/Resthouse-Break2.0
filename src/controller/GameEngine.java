/**
 * 
 */
package controller;

import gui.GameRenderer;
import gui.Renderer;
import gui.sprite.Posture;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Coord;
import model.GameModel;
import model.Player;

/**
 * @author Nicolas
 * 
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public class GameEngine implements KeyListener {
	private Player player;
	private GameModel model;
	private Renderer renderer;
	private GameController controller;

	public GameEngine() {
		player = new Player(new Coord(3,3), Posture.LOOK_DOWN);

		model = new GameModel(player);
		model.init();

		renderer = new GameRenderer(model);
		controller = new GameController(model);
	}

	/** the update method with the deltaTime in seconds **/
	public void update(float deltaTime) {
		controller.update(deltaTime);
	}

	/** this will render the whole world **/
	public void render(Graphics g) {
		renderer.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP :
			controller.onKeyPressed(Direction.UP);
			break;
		case KeyEvent.VK_DOWN :
			controller.onKeyPressed(Direction.DOWN);
			break;
		case KeyEvent.VK_LEFT :
			controller.onKeyPressed(Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT :
			controller.onKeyPressed(Direction.RIGHT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
