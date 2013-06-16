/**
 * 
 */
package controller;

import java.util.HashMap;

import model.GameModel;
import model.Move;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameController {
	private GameModel model;

	private boolean moving = false;
	private boolean isMessageDisplayed = false;
	private int timer = 0;
	private final HashMap<Direction, Boolean> stopMovingAsked;
	
	private static final int TIMER_SPEED = 2;

	public GameController(GameModel model) {
		this.model = model;
		this.stopMovingAsked = new HashMap<Direction, Boolean>();
		
		model.setNewMessage("Good morning Granny Ginette !");
		isMessageDisplayed = true;
	}

	public void update(float delta) {
		if (moving && !isMessageDisplayed) {
			timer++;
			if(timer > TIMER_SPEED) {
				boolean moveIsFinished = model.evolveMove();

				if(moveIsFinished) {
					Move move = model.getPlayer().getMove();
					Direction dir = move.getDir();
					if(stopMovingAsked.get(dir) || !model.isMovementPossible(dir)) {
						moving = false;
					}
				}

				timer = 0;
			}
		}
	}

	public void onStartMovingAsked(Direction d) {
		if(!moving && !isMessageDisplayed) {
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
		model.hideMessage();
		isMessageDisplayed = false;
	}

}
