package controller.actions.menu;

import gui.contextMenu.Menu;
import controller.MenuHandler;
import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class MenuAction implements IAction {

    @Override
    public void execute(Object origin, Object handler) {
        assert origin instanceof Menu : "";
        assert handler instanceof MenuHandler : "";

        execute((Menu)origin, (MenuHandler)handler);
    }

    public abstract void execute(Menu menu, MenuHandler handler);
}
