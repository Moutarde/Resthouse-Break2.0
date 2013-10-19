package model;

import gui.UserInterface;
import gui.contextMenu.BagMenu;
import gui.contextMenu.ContextMenu;
import gui.contextMenu.InspectItemBox;
import gui.contextMenu.Menu;
import gui.contextMenu.MessageBox;
import gui.contextMenu.SelectAnswerBox;
import gui.contextMenu.StoreMenu;
import gui.contextMenu.TransactionMenu;
import gui.sprite.Posture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.events.EnterRoomEvent;
import model.events.Event;
import model.items.Item;
import model.messages.Conversation;
import model.messages.Message;
import model.npc.NPC;
import model.player.Player;
import model.rooms.Room;
import controller.actions.Pause;
import controller.actions.ShowMessage;
import controller.handlers.EventHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameModel extends Observable {

    public static enum MenuID {
        messageBox,
        menu,
        subMenu,
        inspectItemBox,
        selectAnswerBox,
        storeMenu,
        transactionMenu;
    }

    private Player player;

    private Map<MenuID, Menu> menus = new HashMap<MenuID, Menu>(MenuID.values().length);

    private Conversation conversation = new Conversation();

    private List<Event> events = new ArrayList<Event>();

    private boolean gameIsPaused = false;

    public GameModel() {
        setMenu(MenuID.messageBox, new MessageBox());
        setMenu(MenuID.menu, new ContextMenu());
        setMenu(MenuID.subMenu, new BagMenu());
        setMenu(MenuID.inspectItemBox, new InspectItemBox());
        setMenu(MenuID.selectAnswerBox, new SelectAnswerBox());
        setMenu(MenuID.storeMenu, new StoreMenu());
        setMenu(MenuID.transactionMenu, new TransactionMenu());
    }

    public void init() {
        try {
            Item.createItems();

            Room startRoom = Room.createRooms();
            startRoom.loadImg();

            NPC.createNPC();

            this.player = new Player(Player.controllablePlayerId, startRoom, new Coord(3,3), Posture.LOOK_DOWN);

            Event e = new EnterRoomEvent(Room.getRoom("R_PARK"), player);
            e.addAction(new Pause());
            e.addAction(new ShowMessage(new Message(UserInterface.getLang().getString("enterRoom"))));
            events.add(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getPrioritaryDisplayedMenu() {
        if (isMenuDisplayed(MenuID.selectAnswerBox)) {
            return getMenu(MenuID.selectAnswerBox);
        }
        else if (isMenuDisplayed(MenuID.storeMenu)) {
            if (isMenuDisplayed(MenuID.transactionMenu)) {
                return getMenu(MenuID.transactionMenu);
            }
            else {
                return getMenu(MenuID.storeMenu);
            }
        }
        else if (isMenuDisplayed(MenuID.messageBox)) {
            return getMenu(MenuID.messageBox);
        }
        else if (isMenuDisplayed(MenuID.menu)) {
            if (isMenuDisplayed(MenuID.inspectItemBox)) {
                return getMenu(MenuID.inspectItemBox);
            }
            else if (isMenuDisplayed(MenuID.subMenu)) {
                return getMenu(MenuID.subMenu);
            }
            else {
                return getMenu(MenuID.menu);
            }
        }
        else {
            return null;
        }
    }

    // MENUS

    public void setMenu(MenuID id, Menu menu) {
        menus.put(id, menu);
    }

    public Menu getMenu(MenuID id) {
        return menus.get(id);
    }

    public boolean isMenuDisplayed(MenuID id) {
        return getMenu(id) != null && getMenu(id).isDisplayed();
    }

    public void showMenu(MenuID id) {
        getMenu(id).display(true);
    }

    public void hideMenu(MenuID id) {
        getMenu(id).close();
    }

    // CONVERSATION

    public Conversation getConversation() {
        return conversation;
    }

    public void startConversation() {
        conversation.start(player.getFrontNPC());
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

    // OBSERVABLE

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
