/**
 * 
 */
package controller;

import model.GameModel;
import model.Resource;

/**
 * @author Nicolas
 *
 */
public class GameController {
	private GameModel model;

	public GameController(GameModel model) {
		this.model = model;
	}

	public Resource newGame() {
		model.init();
		
		return model.getCurrentRoom().getRes();
	}

	public Resource loadGame(String saveFile) {
		model.load(saveFile);
		
		return model.getCurrentRoom().getRes();
	}
	
}
