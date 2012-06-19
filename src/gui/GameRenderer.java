/**
 * 
 */
package gui;

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
public class GameRenderer implements Renderer {
	private GameModel model;
	

	public GameRenderer(GameModel model) {
		this.model = model;
	}

	@Override
	public void render(GamePanel gp) {
		gp.getBackgroundLabel().setIcon(model.getCurrentRoom().getRes().getImageIcon());
	}

}
