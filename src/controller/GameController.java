/**
 * 
 */
package controller;

import java.util.TreeMap;

import gui.sprite.Posture;
import model.Coord;
import model.GameModel;
import model.Move;
import model.Player;

/**
 * @author Nicolas
 *
 */
public class GameController {
	private GameModel model;

	private boolean moving = false;
	private int timer = 0;
	private final TreeMap<Direction, Boolean> stopMovingAsked;

	public GameController(GameModel model) {
		this.model = model;
		this.stopMovingAsked = new TreeMap<Direction, Boolean>();
	}

	public void update(float delta) {
		if (moving) {
			timer++;
			if(timer > 2) {
				Player player = model.getPlayer();
				Move move = player.getMove();
				Direction dir = move.getDir();

				move.nextStep();
				player.setPosture(Posture.getPosture(dir, move.getStep()));

				if(move.isMoveFinished()) {
					player.moveSquare(dir);
					move.setStep(0);
					move.setDistMove(new Coord(0,0));
					if(stopMovingAsked.get(dir) || !model.isMovementPossible(dir)) {
						moving = false;
					}
				}

				timer = 0;
			}
		}
	}

	public void onStartMovingAsked(Direction d) {
		if(!moving) {
			if (model.isMovementPossible(d)) {
				model.getPlayer().getMove().setDir(d);
				this.stopMovingAsked.put(d, false);
				moving = true;
			}
			else {
				model.getPlayer().setPosture(Posture.getPosture(d, 0));
			}
		}
	}

	public void onStopMovingAsked(Direction d) {
		this.stopMovingAsked.put(d, true);
	}

}
