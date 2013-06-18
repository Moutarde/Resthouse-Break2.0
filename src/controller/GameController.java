/**
 * 
 */
package controller;

import java.util.HashMap;

import model.GameModel;
import model.player.Move;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameController {
	private GameModel model;

	private boolean moving = false;
	private boolean isMessageDisplayed = false;
	private boolean isMenuDisplayed = false;
	private final HashMap<Direction, Boolean> stopMovingAsked;

	public GameController(GameModel model) {
		this.model = model;
		this.stopMovingAsked = new HashMap<Direction, Boolean>();
		
		model.setNewMessage("Good morning Granny Ginette !");
		isMessageDisplayed = true;
	}

	public void update(float delta) {
		if (moving && !isMessageDisplayed && !isMenuDisplayed) {
			Move move = model.getPlayer().getMove();
			boolean timerEnded = move.updateTimer();
			if(timerEnded) {
				boolean moveIsFinished = model.evolveMove();

				if(moveIsFinished) {
					Direction dir = move.getDir();
					if(stopMovingAsked.get(dir) || !model.isMovementPossible(dir)) {
						moving = false;
					}
				}
			}
		}
	}

	public void onStartMovingAsked(Direction d) {
		if(!moving && !isMessageDisplayed && !isMenuDisplayed) {
			boolean movementPossible = model.setMovementIFP(d);
			if (movementPossible) {
				this.stopMovingAsked.put(d, false);
				moving = true;
			}
		}
	}

	public void onStopMovingAsked(Direction d) {
		this.stopMovingAsked.put(d, true);
	}

	public void onValidate() {
		if (isMessageDisplayed) {
			model.hideMessage();
			isMessageDisplayed = false;
		}
		else if (!moving && !isMenuDisplayed && model.isInFrontOfAChest()) {
			model.pickChestContentIFP();
			isMessageDisplayed = true;
		}
	}

	public void onOpenBag() {
		if (isMenuDisplayed) {
			model.hideMenu();
			isMenuDisplayed = false;
		}
		else if (!isMessageDisplayed) {
			model.showBag();
			isMenuDisplayed = true;
		}
	}

}
