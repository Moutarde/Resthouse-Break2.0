/**
 * 
 */
package gui;

import java.awt.Graphics;

import model.GameModel;
import model.rooms.Matrix;

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
	public void render(Graphics g) {
		int x = GamePanel.SIZE.width/2 - (model.getCurrentRoom().getMat().getWidth()*Matrix.CASE_SIZE)/2;
		int y = GamePanel.SIZE.height/2 - (model.getCurrentRoom().getMat().getHeight()*Matrix.CASE_SIZE)/2;
		g.drawImage(model.getCurrentRoom().getRes().getBufferedImage(), x, y, null);
	}

}
