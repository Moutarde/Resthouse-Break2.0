package model;

import gui.contextMenu.ContextMenu;
import gui.contextMenu.Menu;
import gui.sprite.Posture;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import model.items.Item;
import model.messages.Message;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Room;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameModel extends Observable {

    private Player player;
    private Message currentMessage = new Message("");
    private ContextMenu menu;
    private Menu subMenu;
    private Menu inspectItemBox;
    private Menu selectAnswerBox;
    private Menu storeMenu;
    private Menu transactionMenu;
    private boolean gameIsPaused = false;

    public GameModel() {
        this.menu = new ContextMenu(this);
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

    public Player getPlayer() {
        return player;
    }

    public Menu getPrioritaryDisplayedMenu() {
        if (isStoreMenuDisplayed()) {
            if (isTransactionMenuDisplayed()) {
                return transactionMenu;
            }
            else {
                return storeMenu;
            }
        }
        else if (isMenuDisplayed()) {
            if (isInspectItemBoxDisplayed()) {
                return inspectItemBox;
            }
            else if (isSubMenuDisplayed()) {
                return subMenu;
            }
            else {
                return menu;
            }
        }
        else {
            return null;
        }
    }

    // MESSAGE

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void setNewMessage(Message message) {
        assert message != null && !message.getString().equals("") : "Trying to set an empty message";
        currentMessage = message;
    }

    public void setNewMessage(String str) {
        assert str != null && !str.equals("") : "Trying to set an empty message";
        setNewMessage(new Message(str));
    }

    public void hideMessage() {
        currentMessage = null;
    }

    public boolean isMessageDisplayed() {
        return currentMessage != null && !currentMessage.isEmpty();
    }

    // MENU

    public ContextMenu getMenu() {
        return menu;
    }

    public void setMenu(ContextMenu menu) {
        this.menu = menu;
    }

    public boolean isMenuDisplayed() {
        return menu != null && menu.isDisplayed();
    }

    // SUB MENU

    public Menu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(Menu menu) {
        subMenu = menu;
    }

    public boolean isSubMenuDisplayed() {
        return subMenu != null && subMenu.isDisplayed();
    }

    // INSPECT ITEM BOX

    public Menu getInspectItemBox() {
        return inspectItemBox;
    }

    public void setInspectItemBox(Menu menu) {
        inspectItemBox = menu;
    }

    public boolean isInspectItemBoxDisplayed() {
        return inspectItemBox != null && inspectItemBox.isDisplayed();
    }

    // SELECT ANSWER BOX

    public Menu getSelectAnswerBox() {
        return selectAnswerBox;
    }

    public void setSelectAnswerBox(Menu menu) {
        selectAnswerBox = menu;
    }

    public boolean isSelectAnswerBoxDisplayed() {
        return selectAnswerBox != null && selectAnswerBox.isDisplayed();
    }

    // STORE MENU

    public Menu getStoreMenu() {
        return storeMenu;
    }

    public void setStoreMenu(Menu menu) {
        storeMenu = menu;
    }

    public boolean isStoreMenuDisplayed() {
        return storeMenu != null && storeMenu.isDisplayed();
    }

    // TRANSACTION MENU

    public Menu getTransactionMenu() {
        return transactionMenu;
    }

    public void setTransactionMenu(Menu menu) {
        transactionMenu = menu;
    }

    public boolean isTransactionMenuDisplayed() {
        return transactionMenu != null && transactionMenu.isDisplayed();
    }

    // PAUSE

    public void setGamePaused(boolean isPaused) {
        gameIsPaused = isPaused;
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
