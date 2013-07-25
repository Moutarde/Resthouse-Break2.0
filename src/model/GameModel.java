package model;

import gui.ContextMenu;
import gui.UserInterface;
import gui.sprite.Posture;
import gui.sprite.SpriteSheet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.chests.Chest;
import model.chests.Item;
import model.npc.NPC;
import model.player.Bag;
import model.player.Player;
import model.rooms.Room;
import model.rooms.SquareType;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class GameModel extends Observable {

    private Room currentRoom;
    private SpriteSheet charactersSpriteSheet;
    private Player player;
    private Message currentMessage;
    private ContextMenu menu;
    private Bag bag;

    public GameModel() {
        super();
    }

    public void init() {
        try {
            Item.createItems();
            currentRoom = Room.createRooms();
            currentRoom.loadImg();
            NPC.createNPC();
        } catch (IOException e) {
            e.printStackTrace();
        }
        charactersSpriteSheet = new SpriteSheet(Resource.SPRITE_SHEET, 32*3, 32*4);

        currentMessage = new Message();
        menu = new ContextMenu();
        bag = new Bag();

        player = new Player(100, currentRoom, new Coord(3,3), Posture.LOOK_DOWN);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public SpriteSheet getCharactersSpriteSheet() {
        return charactersSpriteSheet;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return true if the movement is possible
     */
    public boolean setMovementIFP(Direction d) {
        if (isMovementPossible(d)) {
            player.startMove(d);
            return true;
        }
        else {
            boolean playerIsOnDoor = currentRoom.getSquareType(player.getCoord()) == SquareType.DOOR;
            boolean nextSquareIsOutside = currentRoom.getSquareType(player.getNextSquare(d)) == SquareType.OUTSIDE;

            if(playerIsOnDoor && nextSquareIsOutside) {
                changeRoom();
            }

            player.setPosture(Posture.getPosture(d, 0));
            player.getMove().setDir(d);
            return false;
        }
    }

    public boolean isMovementPossible(Direction d) {
        return currentRoom.canWalkOnSquare(player.getNextSquare(d), player.getId());
    }

    /**
     * @return true if the movement is finished
     */
    public boolean evolveMove() {
        Move move = player.getMove();
        Direction dir = move.getDir();
        player.getRoom().setPlayerOnSquare(player.getId(), player.getNextSquare(dir));

        move.nextStep();
        player.setPosture(Posture.getPosture(dir, move.getStep()));

        if(move.isMoveFinished()) {
            player.moveSquare(dir);
            move.setStep(0);
            move.setDistMove(new Coord(0,0));
            return true;
        }

        return false;
    }

    private void changeRoom() {
        int doorId = currentRoom.getMat().getSquareValue(player.getCoord());
        if(doorId == -1) {
            System.out.println("Door id not found at coord " + player.getCoord());
            return;
        }

        Room nextRoom = currentRoom.getNeighbor(doorId);
        if(nextRoom == null) {
            System.out.println("No neighbor room found from door " + doorId);
            return;
        }

        Coord newCoord = currentRoom.getStartingCoordFromDoor(doorId);
        if(newCoord == null) {
            System.out.println("The starting coords were not found from door " + doorId);
            return;
        }

        player.getRoom().freeSquare(player.getCoord());
        currentRoom.unloadImg();
        currentRoom = nextRoom;
        currentRoom.loadImg();
        player.setRoom(currentRoom);
        player.setCoord(newCoord);
    }

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void setNewMessage(String str) {
        currentMessage.setString(str);
    }

    public void hideMessage() {
        currentMessage.setString("");
    }

    public ContextMenu getMenu() {
        return menu;
    }

    public void showBag() {
        String content = "Bag :\n";
        HashMap<Item, Integer> bagContent = bag.getContent();
        for (Item item : bagContent.keySet()) {
            content += "  " + bagContent.get(item) + "x " + item.getName() + "\n";
        }
        menu.setContent(content);
    }

    public void hideMenu() {
        menu.setContent("");
    }

    public boolean isInFrontOfAChest() {
        return currentRoom.isChest(player.getFrontSquare());
    }

    /**
     * @return true if an item was picked
     */
    public boolean pickChestContentIFP() {
        int chestId = currentRoom.getMat().getSquareValue(player.getFrontSquare());
        Chest chest = currentRoom.getChest(chestId);
        Item item = chest.getItem();

        if (item == null) {
            setNewMessage(UserInterface.getLang().getString("nothingHere"));
            return false;
        }
        else {
            bag.addItem(item);
            chest.empty();
            setNewMessage(UserInterface.getLang().getString("itemFound") + item.getName());
            return true;
        }
    }

    public void evolveNPCs() {
        for (NPC npc : NPC.getNPCsInRoom(currentRoom)) {
            Direction nextDir = npc.getCurrentDirectionInScript();
            Move move = npc.getMove();

            if (nextDir == Direction.NONE) {
                return;
            }
            else if (!currentRoom.canWalkOnSquare(npc.getNextSquare(nextDir), npc.getId())) {
                npc.setPosture(Posture.getPosture(nextDir, 0));
                move.setDir(Direction.NONE);
                move.setStep(0);
                move.setDistMove(new Coord(0,0));
                return;
            }
            else {
                npc.getRoom().setPlayerOnSquare(npc.getId(), npc.getNextSquare(nextDir));
                move.setDir(nextDir);
                npc.setPosture(Posture.getPosture(nextDir, move.getStep()));

                if (move.updateTimer()) {
                    move.nextStep();

                    if(move.isMoveFinished()) {
                        npc.moveSquare(nextDir);
                        move.setStep(0);
                        move.setDistMove(new Coord(0,0));
                        npc.goToNextStepOfScript();
                    }
                }
            }
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
