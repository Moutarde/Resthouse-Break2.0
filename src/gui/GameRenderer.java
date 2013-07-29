package gui;

import gui.sprite.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import model.Coord;
import model.GameModel;
import model.Move;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Matrix;
import model.rooms.Room;
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
        Room currentRoom = player.getRoom();
        Move move = player.getMove();

        int px = GamePanel.SIZE.width/2 - Matrix.CASE_SIZE/2 - playerSprite.getOffset().getX();
        int py = GamePanel.SIZE.height/2 - Matrix.CASE_SIZE/2 - playerSprite.getOffset().getY();

        int bgx = px - (player.getCoord().getX() * Matrix.CASE_SIZE + move.getDistMove().getX() - 3);
        int bgy = py - (player.getCoord().getY() * Matrix.CASE_SIZE + move.getDistMove().getY() - 5);


        // Render the background
        if (debugMode) {
            Matrix mat = currentRoom.getMat();
            int positionX = bgx;
            int positionY = bgy;
            for (int lineId = 0; lineId < mat.getHeight(); ++lineId) {
                for (int colId = 0; colId < mat.getWidth(); ++colId) {
                    Coord coord = new Coord(colId, lineId);
                    SquareType typeFromEvolutiveMat = currentRoom.getSquareTypeFromEvolutiveMat(coord);
                    if (typeFromEvolutiveMat == SquareType.CHARACTER) {
                        g.setColor(Color.yellow);
                    }
                    else {
                        SquareType type = currentRoom.getSquareType(coord);
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
            g.drawImage(currentRoom.getImg(), bgx, bgy, null);
        }

        // Render the player
        playerSprite.draw(g, px, py, player.getPosture());

        Sprite npcSprite;
        for (NPC npc : NPC.getNPCsInRoom(currentRoom)) {
            npcSprite = model.getCharactersSpriteSheet().getSprite(npc.getSpriteCoord());
            int npcx = bgx + npc.getCoord().getX() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getX() - npcSprite.getOffset().getX();
            int npcy = bgy + npc.getCoord().getY() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getY() - npcSprite.getOffset().getY();
            npcSprite.draw(g, npcx, npcy, npc.getPosture());
        }

        // Render the bottom message
        if(model.isMessageDisplayed()) {
            Coord messagePosition = new Coord(0, GamePanel.SIZE.height - GamePanel.TEXT_ZONE_HEIGHT);
            drawFramedRect(g, messagePosition, new Dimension(GamePanel.SIZE.width, GamePanel.TEXT_ZONE_HEIGHT), 2);

            Coord offset = new Coord(10, 10);
            drawText(g, messagePosition, model.getCurrentMessage().getString(), offset);

            String pressEnter = UserInterface.getLang().getString("pressEnter");
            g.drawString(pressEnter, GamePanel.SIZE.width - (offset.getX() + g.getFontMetrics().stringWidth(pressEnter)), GamePanel.SIZE.height - offset.getY());
        }

        // Render the menu
        if(model.isMenuDisplayed()) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.MENU_SIZE.width, 0);
            drawFramedRect(g, menuPosition, GamePanel.MENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getMenu().getContent(), offset);
        }

        // Render the sub menu
        if(model.isSubMenuDisplayed()) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.SUBMENU_SIZE.width, 0);
            drawFramedRect(g, menuPosition, GamePanel.SUBMENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getSubMenu().getContent(), offset);
        }
    }

    private static void drawFramedRect(Graphics g, Coord position, Dimension size, int borderWidth) {
        // Background
        g.setColor(Color.white);
        g.fillRect(position.getX(), position.getY(), size.width, size.height);
        // Border
        g.setColor(Color.black);
        for (int i = 0 ; i < borderWidth ; ++i) {
            g.drawRect(position.getX() + i, position.getY() + i, size.width - 2 * i, size.height - 2 * i);
        }
    }

    private static void drawText(Graphics g, Coord position, String content, Coord offset) {
        g.setColor(Color.black);
        int textHeight = g.getFontMetrics().getHeight();
        int offsetX = offset.getX();
        int offsetY = offset.getY() + textHeight/2;
        String[] lines = content.split("\n");
        for (String string : lines) {
            g.drawString(string, position.getX() + offsetX, position.getY() + offsetY);
            offsetY += textHeight;
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
