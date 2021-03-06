package gui;

import gui.contextMenu.StoreMenu;
import gui.contextMenu.TransactionMenu;
import gui.sprite.Sprite;
import gui.sprite.SpriteSheet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import model.Coord;
import model.GameModel;
import model.GameModel.MenuID;
import model.Move;
import model.Resource;
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
    private SpriteSheet charactersSpriteSheet = new SpriteSheet(Resource.SPRITE_SHEET, 32*3, 32*4);

    private boolean debugMode = false;

    private GameModel model;

    private Sprite playerSprite;
    private Map<NPC, Sprite> npcSprites = new HashMap<NPC, Sprite>();

    public GameRenderer(GameModel model) {
        this.model = model;

        playerSprite = charactersSpriteSheet.getSprite(2, 5);
        for (NPC npc : NPC.getNPCList()) {
            npcSprites.put(npc, charactersSpriteSheet.getSprite(npc.getSpriteCoord()));
        }
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
            npcSprite = npcSprites.get(npc);
            int npcx = bgx + npc.getCoord().getX() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getX() - npcSprite.getOffset().getX();
            int npcy = bgy + npc.getCoord().getY() * Matrix.CASE_SIZE + npc.getMove().getDistMove().getY() - npcSprite.getOffset().getY();
            npcSprite.draw(g, npcx, npcy, npc.getPosture());
        }

        // Render the bottom message
        if (model.isMenuDisplayed(MenuID.messageBox)) {
            Coord messagePosition = new Coord(0, GamePanel.SIZE.height - GamePanel.TEXT_ZONE_HEIGHT);
            Dimension messageSize = new Dimension(GamePanel.SIZE.width, GamePanel.TEXT_ZONE_HEIGHT);
            drawFramedRect(g, messagePosition, messageSize, 2);

            Coord offset = new Coord(10, 10);
            drawText(g, messagePosition, model.getMenu(MenuID.messageBox).getContent(), offset, messageSize);

            String pressEnter = UserInterface.getLang().getString("pressEnter");
            g.drawString(pressEnter, GamePanel.SIZE.width - (offset.getX() + g.getFontMetrics().stringWidth(pressEnter)), GamePanel.SIZE.height - offset.getY());
        }

        // Render the menu
        if (model.isMenuDisplayed(MenuID.menu)) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.MENU_SIZE.width, 0);
            drawFramedRect(g, menuPosition, GamePanel.MENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getMenu(MenuID.menu).getContent(), offset, GamePanel.MENU_SIZE);
        }

        // Render the sub menu
        if (model.isMenuDisplayed(MenuID.subMenu)) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.SUBMENU_SIZE.width, 0);
            drawFramedRect(g, menuPosition, GamePanel.SUBMENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getMenu(MenuID.subMenu).getContent(), offset, GamePanel.SUBMENU_SIZE);
        }

        // Render the inspect item box
        if (model.isMenuDisplayed(MenuID.inspectItemBox)) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width / 3, GamePanel.SIZE.height / 3);
            drawFramedRect(g, menuPosition, GamePanel.INSPECT_ITEM_BOX_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getMenu(MenuID.inspectItemBox).getContent(), offset, GamePanel.INSPECT_ITEM_BOX_SIZE);
        }

        // Render the store menu
        if (model.isMenuDisplayed(MenuID.storeMenu)) {
            assert model.getMenu(MenuID.storeMenu) instanceof StoreMenu : "Store menu is not an instance of StoreMenu";
            StoreMenu storeMenu = (StoreMenu) model.getMenu(MenuID.storeMenu);

            // STORE
            Coord menuPosition = new Coord(0, GamePanel.SIZE.height - (GamePanel.TEXT_ZONE_HEIGHT + GamePanel.STORE_MENU_SIZE.height));
            drawFramedRect(g, menuPosition, GamePanel.STORE_MENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, storeMenu.getContent(), offset, GamePanel.STORE_MENU_SIZE);

            // DETAILS
            Coord detailsPosition = new Coord(menuPosition.getX() + GamePanel.STORE_MENU_SIZE.width, menuPosition.getY());
            drawFramedRect(g, detailsPosition, GamePanel.STORE_MENU_DETAILS_SIZE, 2);
            drawText(g, detailsPosition, storeMenu.getPointedElementDescr(), offset, GamePanel.STORE_MENU_DETAILS_SIZE);

            // PRICE
            Coord pricePosition = new Coord(detailsPosition.getX(), detailsPosition.getY() + GamePanel.STORE_MENU_DETAILS_SIZE.height);
            drawFramedRect(g, pricePosition, GamePanel.PRICE_MENU_SIZE, 2);
            drawText(g, pricePosition, storeMenu.getPointedElementPrice(), offset, GamePanel.PRICE_MENU_SIZE);
        }

        // Render the transaction menu
        if (model.isMenuDisplayed(MenuID.transactionMenu)) {
            assert model.getMenu(MenuID.transactionMenu) instanceof TransactionMenu : "Transaction menu is not an instance of TransactionMenu";
            TransactionMenu transactionMenu = (TransactionMenu) model.getMenu(MenuID.transactionMenu);

            // NB ITEMS TO BUY
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.TRANSACTION_MENU_SIZE.width, 0);
            drawFramedRect(g, menuPosition, GamePanel.TRANSACTION_MENU_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, transactionMenu.getContent(), offset, GamePanel.TRANSACTION_MENU_SIZE);

            // NB ITEMS OWNED
            Coord itemsOwnedPosition = new Coord(0, 0);
            drawFramedRect(g, itemsOwnedPosition, GamePanel.ITEMS_OWNED_MENU_SIZE, 2);
            String itemsOwnedText = transactionMenu.getNbAlreadyOwned() + " " + UserInterface.getLang().getString("owned");
            drawText(g, itemsOwnedPosition, itemsOwnedText, offset, GamePanel.ITEMS_OWNED_MENU_SIZE);
        }

        // Render the select answer box
        if (model.isMenuDisplayed(MenuID.selectAnswerBox)) {
            Coord menuPosition = new Coord(GamePanel.SIZE.width - GamePanel.SELECT_ANSWER_BOX_SIZE.width, GamePanel.SIZE.height - (GamePanel.TEXT_ZONE_HEIGHT + GamePanel.SELECT_ANSWER_BOX_SIZE.height));
            drawFramedRect(g, menuPosition, GamePanel.SELECT_ANSWER_BOX_SIZE, 2);
            Coord offset = new Coord(10, 10);
            drawText(g, menuPosition, model.getMenu(MenuID.selectAnswerBox).getContent(), offset, GamePanel.SELECT_ANSWER_BOX_SIZE);
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

    private static void drawText(Graphics g, Coord position, String content, Coord offset, Dimension maxSpace) {
        g.setColor(Color.black);
        int textHeight = g.getFontMetrics().getHeight();
        int offsetX = offset.getX();
        int offsetY = offset.getY() + textHeight/2;
        String[] lines = content.split("\n");
        for (String string : lines) {
            if (g.getFontMetrics().stringWidth(string) + 2 * offsetX > maxSpace.width) {
                int wordPosition = offsetX;
                String[] words = string.split(" ");

                for (String word : words) {
                    int wordSize = g.getFontMetrics().stringWidth(word);
                    int wordSizeWithSpace = g.getFontMetrics().stringWidth(word + " ");

                    if (wordPosition + wordSize > maxSpace.width + offsetX) {
                        offsetY += textHeight;
                        wordPosition = offsetX;
                    }

                    g.drawString(word + " ", position.getX() + wordPosition, position.getY() + offsetY);
                    wordPosition += wordSizeWithSpace;
                }
            }
            else {
                g.drawString(string, position.getX() + offsetX, position.getY() + offsetY);
            }

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
