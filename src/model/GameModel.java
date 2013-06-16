/**
 * 
 */
package model;

import gui.sprite.Posture;
import gui.sprite.SpriteSheet;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import controller.Direction;

import model.rooms.Room;
import model.rooms.SquareType;

/**
 * @author Nicolas
 *
 */
public class GameModel extends Observable {
	
	private Room currentRoom;
    private SpriteSheet charactersSpriteSheet;
    private Player player;
    private Message currentMessage;

	public GameModel(Player player) {
		super();
		
		this.player = player;
	}

	public void init() {
		try {
			currentRoom = Room.createRooms();
		} catch (IOException e) {
			e.printStackTrace();
		}
		charactersSpriteSheet = new SpriteSheet(Resource.SPRITE_SHEET, 32*3, 32*4);
		currentMessage = new Message();
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
		if(isMovementPossible(d)) {
			player.getMove().setDir(d);
			return true;
		}
		else {
			boolean playerIsOnDoor = currentRoom.getSquareType(player.getCoord()) == SquareType.DOOR;
			boolean nextSquareIsOutside = currentRoom.getSquareType(player.getNextSquare(d)) == SquareType.OUTSIDE;
			if(playerIsOnDoor && nextSquareIsOutside) {
				player.getMove().setDir(d);
				player.getMove().setIsLeavingRoom(true);
				return true;
			}
			else {
				player.setPosture(Posture.getPosture(d, 0));
				return false;
			}
		}
			
	}
	
	public boolean isMovementPossible(Direction d) {
		return currentRoom.canWalkOnSquare(player.getNextSquare(d));
	}

	/**
	 * @return true if the movement is finished
	 */
	public boolean evolveMove() {
		Move move = player.getMove();
		Direction dir = move.getDir();

		move.nextStep();
		player.setPosture(Posture.getPosture(dir, move.getStep()));

		if(move.isMoveFinished()) {
			if(move.isLeavingRoom()) {
				changeRoom();
				move.setIsLeavingRoom(false);
			}
			else {
				player.moveSquare(dir);
			}
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
		
		currentRoom = nextRoom;
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
