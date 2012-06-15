/**
 * 
 */
package controler;

import model.GameModel;
import model.Resource;

/**
 * @author Nicolas
 *
 */
public class GameControler {
	private GameModel model;

	public GameControler(GameModel model) {
		this.model = model;
	}

	public Resource newGame() {
		this.model.init();
		
		return this.model.getCurrentRoom();
	}

	public Resource loadGame(String saveFile) {
		this.model.load(saveFile);
		
		return this.model.getCurrentRoom();
	}
	
}
