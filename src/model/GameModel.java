/**
 * 
 */
package model;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Nicolas
 *
 */
public class GameModel extends Observable {

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
	
}
