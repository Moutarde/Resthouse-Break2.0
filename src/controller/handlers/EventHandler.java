package controller.handlers;

import java.util.Observable;

import model.GameModel;
import model.events.Event;

public class EventHandler extends Handler {

    public EventHandler(GameModel model) {
        super(model);
    }

    @Override
    public void update(Observable obs, Object action) {
        assert obs instanceof Event : "Trying to update EventHandler with an obs which is not an Event";

        super.update(obs, action);
    }

}
