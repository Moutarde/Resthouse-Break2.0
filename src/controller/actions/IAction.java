package controller.actions;

import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public interface IAction {
    public void execute(Object origin, Handler handler);
}
