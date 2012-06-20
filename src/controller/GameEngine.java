/**
 * 
 */
package controller;

import gui.GameRenderer;
import gui.Renderer;

import java.awt.Event;
import java.awt.Graphics;

import model.GameModel;

/**
 * @author Nicolas
 * 
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public class GameEngine {
	private GameModel model;
	private Renderer renderer;
	
	public GameEngine() {
		model = new GameModel();
		model.init();
		renderer = new GameRenderer(model);
	}
	
	public boolean handleEvent(Event e) {
		switch (e.id) {
		case Event.KEY_PRESS:
		case Event.KEY_ACTION:
			// key pressed
			break;
		case Event.KEY_RELEASE:
			// key released
			break;
		case Event.MOUSE_DOWN:
			// mouse button pressed
			break;
		case Event.MOUSE_UP:
			// mouse button released
			break;
		case Event.MOUSE_MOVE:
			// mouse is being moved
			break;
		case Event.MOUSE_DRAG:
			// mouse is being dragged (button pressed)
			break;
		}
		return false;
	}

	/** the update method with the deltaTime in seconds **/
	public void update(float deltaTime) {
		// empty
	}

	/** this will render the whole world **/
	public void render(Graphics g) {
		renderer.render(g);
	}
}
