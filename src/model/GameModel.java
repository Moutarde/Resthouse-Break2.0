package model;

import gui.contextMenu.BagMenu;
import gui.contextMenu.ContextMenu;
import gui.contextMenu.Menu;
import gui.sprite.Posture;
import gui.sprite.SpriteSheet;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import model.chests.Item;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Room;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameModel extends Observable {

    private Player player;
    private SpriteSheet charactersSpriteSheet = new SpriteSheet(Resource.SPRITE_SHEET, 32*3, 32*4);
    private Message currentMessage = new Message();
    private ContextMenu menu;
    private Menu subMenu;
    private boolean gameIsPaused = false;

    public GameModel() {
        menu = new ContextMenu(this);
    }

    public void init() {
        try {
            Item.createItems();
            Room startRoom = Room.createRooms();
            startRoom.loadImg();
            player = new Player(Player.controllablePlayerId, startRoom, new Coord(3,3), Posture.LOOK_DOWN);
            NPC.createNPC();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SpriteSheet getCharactersSpriteSheet() {
        return charactersSpriteSheet;
    }

    public Player getPlayer() {
        return player;
    }

    // MESSAGE

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void setNewMessage(String str) {
        assert str != "" : "Trying to set an empty message";
        currentMessage.setString(str);
        gameIsPaused = true;
    }

    public void hideMessage() {
        currentMessage.setString("");
        gameIsPaused = false;
    }

    public boolean isMessageDisplayed() {
        return currentMessage != null && !currentMessage.isEmpty();
    }

    // MENU

    public ContextMenu getMenu() {
        return menu;
    }

    public boolean isMenuDisplayed() {
        return menu != null && menu.isDisplayed();
    }

    public void showMenu() {
        menu.display(true);
        gameIsPaused = true;
    }

    public void hideMenu() {
        menu.display(false);
        gameIsPaused = false;
    }

    // SUB MENU

    public Menu getSubMenu() {
        return subMenu;
    }

    public boolean isSubMenuDisplayed() {
        return subMenu != null && subMenu.isDisplayed();
    }

    public void showBag() {
        subMenu = new BagMenu("bag", player.getBag(), this);
        subMenu.display(true);
    }

    public void hideSubMenu() {
        subMenu = null;
    }

    public boolean isGamePaused() {
        return gameIsPaused;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        // TODO Auto-generated method stub
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObservers() {
        // TODO Auto-generated method stub
        super.deleteObservers();
    }

    @Override
    public void notifyObservers() {
        // TODO Auto-generated method stub
        super.notifyObservers();
    }

    public void load(String saveFile) {
        // TODO Auto-generated method stub

    }
}
