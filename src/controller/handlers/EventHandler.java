package controller.handlers;

import java.util.Observable;

import model.GameModel;
import model.events.Event;

public class EventHandler extends Handler {

    private GameModel model;

    public EventHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable obs, Object action) {
        assert obs instanceof Event : "Trying to update EventHandler with an obs which is not an Event";

        super.update(obs, action);
    }

}
