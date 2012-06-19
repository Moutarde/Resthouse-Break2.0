import gui.UserInterface;

import javax.swing.JApplet;

import controller.GameController;

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
		GameController controller = new GameController(model);
		UserInterface gui = new UserInterface(controller);
		model.addObserver(gui);
	}

}
