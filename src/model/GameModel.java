/**
 * 
 */
package model;

import gui.sprite.SpriteSheet;

import java.util.Observable;
import java.util.Observer;

import controller.Direction;

import model.rooms.Room;

/**
 * @author Nicolas
 *
 */
public class GameModel extends Observable {
	
	private Room currentRoom;
    private SpriteSheet charactersSpriteSheet;
    private Player player;

	public GameModel(Player player) {
		super();
		
		this.player = player;
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

	public void init() {
		currentRoom = Room.createRooms();
		charactersSpriteSheet = new SpriteSheet(Resource.SPRITE_SHEET, 32*3, 32*4);
	}

	public void load(String saveFile) {
		// TODO Auto-generated method stub
		
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public SpriteSheet getCharactersSpriteSheet() {
		return charactersSpriteSheet;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	public boolean isMovementPossible(Direction d) {
		int xMove = 0;
		int yMove = 0;

		switch(d) {
		case UP:
			yMove = -1;
			break;
		case DOWN:
			yMove = 1;
			break;
		case LEFT:
			xMove = -1;
			break;
		case RIGHT:
			xMove = 1;
			break;
		default:
			assert false;
			break;
		}

		int xp = player.getCoord().getX();
		int yp = player.getCoord().getY();

		Coord c = new Coord(xp + xMove, yp + yMove);
		
		return currentRoom.canWalkOnSquare(c);
	}
	
}
