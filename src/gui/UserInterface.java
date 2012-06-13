/**
 * 
 */
package gui;

import java.util.Observable;
import java.util.Observer;

import controler.GameControler;

/**
 * @author Nicolas
 *
 */
public class UserInterface implements Observer {

	private GameControler controler;
	
	public UserInterface(GameControler controler) {
		this.controler = controler;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
