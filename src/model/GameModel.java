package model;

import gui.contextMenu.ContextMenu;
import gui.contextMenu.Menu;
import gui.sprite.Posture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.events.EnterRoomEvent;
import model.events.Event;
import model.items.Item;
import model.messages.Message;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Room;
import controller.actions.ShowMessage;
import controller.handlers.EventHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameModel extends Observable {

    private Player player;

    private Menu messageBox;
    private Menu menu;
    private Menu subMenu;
    private Menu inspectItemBox;
    private Menu selectAnswerBox;
    private Menu storeMenu;
    private Menu transactionMenu;

    private List<Event> events = new ArrayList<Event>();

    private boolean gameIsPaused = false;

    public GameModel() {
        this.menu = new ContextMenu();
    }

    public void init() {
        try {
            Item.createItems();

            Room startRoom = Room.createRooms();
            startRoom.loadImg();

            NPC.createNPC();

            this.player = new Player(Player.controllablePlayerId, startRoom, new Coord(3,3), Posture.LOOK_DOWN);

            Event e = new EnterRoomEvent(Room.getRoom("R_PARK"), player);
            e.addAction(new ShowMessage(new Message("enterRoom")));
            events.add(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getPrioritaryDisplayedMenu() {
        if (isSelectAnswerBoxDisplayed()) {
            return selectAnswerBox;
        }
        else if (isStoreMenuDisplayed()) {
            if (isTransactionMenuDisplayed()) {
                return transactionMenu;
            }
            else {
                return storeMenu;
            }
        }
        else if (isMessageBoxDisplayed()) {
            return messageBox;
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

    // MESSAGE BOX

    public Menu getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(Menu menu) {
        this.messageBox = menu;
    }

    public boolean isMessageBoxDisplayed() {
        return messageBox != null && messageBox.isDisplayed();
    }

    // MENU

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
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

    // EVENTS

    public void setEventHandler(EventHandler handler) {
        for (Event e : events) {
            e.addObserver(handler);
        }
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
