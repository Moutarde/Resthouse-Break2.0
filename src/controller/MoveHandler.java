package controller;

import gui.sprite.Posture;

import java.util.HashMap;

import model.GameModel;
import model.Move;
import model.npc.NPC;
import model.player.Player;

public class MoveHandler {
    private GameModel model;

    private boolean moving = false;
    private final HashMap<Direction, Boolean> stopMovingAsked = new HashMap<Direction, Boolean>();

    public MoveHandler(GameModel model) {
        this.model = model;
    }

    /**
     * @return true if the movement is finished
     */
    public void evolveMove(Player player) {
        if (moving) {
            Move move = player.getMove();

            boolean timerEnded = move.updateTimer();
            if(timerEnded) {
                Direction dir = move.getDir();
                player.setOnSquare(dir);

                move.nextStep();
                player.setPosture(Posture.getPosture(dir, move.getStep()));

                if(move.isMoveFinished()) {
                    player.moveSquare(dir);
                    if(stopMovingAsked.get(dir) || !player.canWalkOnSquare(dir)) {
                        moving = false;
                    }
                }
            }
        }
    }

    public void evolvePlayers() {
        for (NPC npc : NPC.getNPCList()) {
            Direction nextDir = npc.getCurrentDirectionInScript();
            Move move = npc.getMove();

            if (nextDir != Direction.NONE) {
                if (!npc.canWalkOnSquare(nextDir)) {
                    npc.setPosture(Posture.getPosture(nextDir, 0));
                    move.setDir(Direction.NONE);
                    move.reset();
                    npc.setMovementIFP(nextDir);
                }
                else {
                    npc.setOnSquare(nextDir);
                    move.setDir(nextDir);
                    npc.setPosture(Posture.getPosture(nextDir, move.getStep()));

                    boolean timerEnded = move.updateTimer();
                    if(timerEnded) {
                        move.nextStep();

                        if(move.isMoveFinished()) {
                            npc.moveSquare(nextDir);
                            npc.goToNextStepOfScript();
                        }
                    }
                }
            }
        }
    }

    public void startMoving(Direction d) {
        if(!moving) {
            boolean movementPossible = model.getPlayer().setMovementIFP(d);
            if (movementPossible) {
                stopMovingAsked.put(d, false);
                moving = true;
            }
        }
    }

    public void stopMoving(Direction d) {
        this.stopMovingAsked.put(d, true);
    }

    public boolean isMoving() {
        return moving;
    }
}
