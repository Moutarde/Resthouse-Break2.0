package gui;

import gui.sprite.Sprite;

import java.awt.Color;
import java.awt.Graphics;

import model.Coord;
import model.GameModel;
import model.Message;
import model.Move;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Matrix;
import model.rooms.SquareType;

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
	private boolean debugMode = false;
	
	private GameModel model;
	
	private Sprite playerSprite;

	public GameRenderer(GameModel model) {
		this.model = model;
		
		playerSprite = model.getCharactersSpriteSheet().getSprite(2, 5);
	}

	@Override
	public void render(Graphics g) {
		Player player = model.getPlayer();
		Move move = player.getMove();
		
		int px = GamePanel.SIZE.width/2 - Matrix.CASE_SIZE/2 - playerSprite.getOffset().getX();
		int py = GamePanel.SIZE.height/2 - Matrix.CASE_SIZE/2 - playerSprite.getOffset().getY();
		
		int bgx = px - (player.getCoord().getX() * Matrix.CASE_SIZE + move.getDistMove().getX() - 3);
		int bgy = py - (player.getCoord().getY() * Matrix.CASE_SIZE + move.getDistMove().getY() - 5);
		
		
		// Render the background
		if (debugMode) {
			Matrix mat = model.getCurrentRoom().getMat(); 
			int positionX = bgx;
			int positionY = bgy;
			for (int lineId = 0; lineId < mat.getHeight(); ++lineId) {
				for (int colId = 0; colId < mat.getWidth(); ++colId) {
					Coord coord = new Coord(colId, lineId);
					SquareType typeFromEvolutiveMat = model.getCurrentRoom().getSquareTypeFromEvolutiveMat(coord);
					if (typeFromEvolutiveMat == SquareType.CHARACTER) {
						g.setColor(Color.yellow);
					}
					else {
						SquareType type = model.getCurrentRoom().getSquareType(coord);
						switch (type) {
						case FREESQUARE:
							g.setColor(Color.white);
							break;
						case DOOR:
							g.setColor(Color.red);
							break;
						case CHEST:
							g.setColor(Color.blue);
							break;
						case OBSTACLE:
							g.setColor(Color.gray);
							break;
						case OUTSIDE:
							g.setColor(Color.darkGray);
							break;
						default:
							assert false : "Square type unknown : " + type;
							break;
						}
					}
					
					g.fillRect(positionX, positionY, Matrix.CASE_SIZE, Matrix.CASE_SIZE);
					positionX += Matrix.CASE_SIZE;
				}
				positionX = bgx;
				positionY += Matrix.CASE_SIZE;
			}
		}
		else {
			g.drawImage(model.getCurrentRoom().getImg(), bgx, bgy, null);
		}
		
		// Render the player
		playerSprite.draw(g, px, py, player.getPosture());
		
		Sprite npcSprite;
		for (NPC npc : NPC.getNPCsInRoom(model.getCurrentRoom())) {
			npcSprite = model.getCharactersSpriteSheet().getSprite(npc.getSpriteCoord());
			int npcx = bgx + npc.getCoord().getX() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getX() - npcSprite.getOffset().getX();
			int npcy = bgy + npc.getCoord().getY() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getY() - npcSprite.getOffset().getY();
			npcSprite.draw(g, npcx, npcy, npc.getPosture());
		}
		
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
			int menuPositionX = GamePanel.SIZE.width - GamePanel.MENU_SIZE.width;
			
			String[] lines = menu.getContent().split("\n");
			
			g.setColor(Color.white);
			g.fillRect(menuPositionX, 0, GamePanel.SIZE.width, GamePanel.MENU_SIZE.height);
			g.setColor(Color.black);
			
			for (String string : lines) {
				g.drawString(string, menuPositionX + offsetX, offsetY);
				offsetY += textHeight;
			}
		}
	}

	@Override
	public void changeDebugMode() {
		if (debugMode)
			debugMode = false;
		else
			debugMode = true;
	}
}
