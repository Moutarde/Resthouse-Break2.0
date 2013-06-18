package gui;

import gui.sprite.Sprite;

import java.awt.Color;
import java.awt.Graphics;

import model.GameModel;
import model.Message;
import model.player.Player;
import model.rooms.Matrix;

/**
 * @author Nicolas Kniebihler
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
		Player player = model.getPlayer();
		int px = x + player.getCoord().getX() * Matrix.CASE_SIZE + player.getMove().getDistMove().getX() - 3;
		int py = y + player.getCoord().getY() * Matrix.CASE_SIZE + player.getMove().getDistMove().getY() - 5;
		playerSprite.draw(g, px, py, player.getPosture());
		
		// Render the bottom message
		Message message = model.getCurrentMessage();
		if(message != null && !message.isEmpty()) {
			int offsetX = 10;
			int offsetY = 10;
			int position = GamePanel.SIZE.height - GamePanel.TEXT_ZONE_HEIGHT;
			
			g.setColor(Color.white);
			g.fillRect(0, position, GamePanel.SIZE.width, GamePanel.SIZE.height);
			g.setColor(Color.black);
			g.drawString(message.getString(), offsetX, position + offsetY + g.getFontMetrics().getHeight()/2);
			String pressEnter = UserInterface.getLang().getString("pressEnter");
			offsetX += g.getFontMetrics().stringWidth(pressEnter);
			g.drawString(pressEnter, GamePanel.SIZE.width - offsetX, GamePanel.SIZE.height - offsetY);
		}
		
		// Render the menu
		ContextMenu menu = model.getMenu();
		if(menu != null && !menu.isEmpty()) {
			int textHeight = g.getFontMetrics().getHeight();
			int offsetX = 10;
			int offsetY = 10 + textHeight/2;
			int positionX = GamePanel.SIZE.width - GamePanel.MENU_SIZE.width;
			
			String[] lines = menu.getContent().split("\n");
			
			g.setColor(Color.white);
			g.fillRect(positionX, 0, GamePanel.SIZE.width, GamePanel.MENU_SIZE.height);
			g.setColor(Color.black);
			
			for (String string : lines) {
				g.drawString(string, positionX + offsetX, offsetY);
				offsetY += textHeight;
			}
		}
	}

}
