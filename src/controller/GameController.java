/**
 * 
 */
package controller;

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

	public GameController(GameModel model) {
		this.model = model;
	}

	public void update(float delta) {
		Player player = model.getPlayer();
		if (moving) {
			timer++;
			if(timer > 6) {
				Move move = player.getMove();
				move.nextStep();
				player.setPosture(Posture.getPosture(move.getDir(), move.getStep()));

				if(move.getStep() == 4) {
					player.moveSquare(move.getDir());
					move.setStep(0);
					move.setDistMove(new Coord(0,0));
					moving = false;
				}

				timer = 0;
			}
		}
	}

	public void onKeyPressed(Direction d) {
		if(!moving) {
			int xMove = 0;
			int yMove = 0;

			switch(d) {
			case UP:
				yMove = -1;
				break;
			case DOWN:
				yMove = 1;
				break;
			case LEFT:
				xMove = -1;
				break;
			case RIGHT:
				xMove = 1;
				break;
			}

			int xp = model.getPlayer().getCoord().getX();
			int yp = model.getPlayer().getCoord().getY();

			Coord c = new Coord(xp + xMove, yp + yMove);

			if (model.getCurrentRoom().getMat().getSquareValue(c) == 1) {
				model.getPlayer().getMove().setDir(d);
				moving = true;
			}
			else {
				model.getPlayer().setPosture(Posture.getPosture(d, 0));
			}
		}
	}

}
