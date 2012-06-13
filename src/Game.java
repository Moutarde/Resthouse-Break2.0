import gui.UserInterface;

import javax.swing.JApplet;

import controler.GameControler;

import model.GameModel;

/**
 * 
 */

/**
 * @author Nicolas Kniebihler
 *
 */
public class Game extends JApplet {

	private static final long serialVersionUID = -7803629994015778818L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameModel model = new GameModel();
		GameControler controler = new GameControler(model);
		UserInterface gui = new UserInterface(controler);
		model.addObserver(gui);
	}

}
