package controller.actions;

import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Pause implements IAction {

    @Override
    public void execute(Object origin, Handler handler) {
        handler.getModel().setGamePaused(true);
    }

}
