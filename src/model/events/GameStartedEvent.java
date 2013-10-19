package model.events;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 * This Event has to observe a GameModel.
 */
public class GameStartedEvent extends Event implements Observer {

    public GameStartedEvent(GameModel model) {
        model.addObserver(this);
    }

    public GameStartedEvent(List<IAction> actionList, GameModel model) {
        super(actionList);
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameModel : "GameStartedEvent observes something that is not a GameModel";

        if (!hasBeenTriggered() && ((GameModel) o).isGameStarted()) {
            trigger();
        }
    }

}
