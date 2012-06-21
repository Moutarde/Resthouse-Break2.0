/**
 * 
 */
package gui;

import gui.sprite.Sprite;

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
	
	private Sprite playerSprite;

	public GameRenderer(GameModel model) {
		this.model = model;
		
		playerSprite = model.getCharactersSpriteSheet().getSprite(2, 5);
	}

	@Override
	public void render(Graphics g) {
		// Render the background
		int x = GamePanel.SIZE.width/2 - (model.getCurrentRoom().getMat().getWidth() * Matrix.CASE_SIZE)/2;
		int y = GamePanel.SIZE.height/2 - (model.getCurrentRoom().getMat().getHeight() * Matrix.CASE_SIZE)/2;
		g.drawImage(model.getCurrentRoom().getRes().getBufferedImage(), x, y, null);
		
		// Render the player
		int px = x + model.getPlayer().getCoord().getX() * Matrix.CASE_SIZE - 3;
		int py = y + model.getPlayer().getCoord().getY() * Matrix.CASE_SIZE - 5;
		g.drawImage(playerSprite.getObject(model.getPlayer().getPosture()), px, py, null);
	}

}
