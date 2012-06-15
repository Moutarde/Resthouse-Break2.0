/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.rooms.Room;

/**
 * @author Nicolas
 *
 */
public class GameModel extends Observable {
	
	private Room currentRoom;

	public GameModel() {
		super();
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
		this.currentRoom = Room.createRooms();
	}

	public void load(String saveFile) {
		// TODO Auto-generated method stub
		
	}

	public Resource getCurrentRoom() {
		return this.currentRoom.getRes();
	}
	
}
