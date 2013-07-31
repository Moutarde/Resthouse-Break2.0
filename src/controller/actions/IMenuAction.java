package controller.actions;

import gui.contextMenu.Menu;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public interface IMenuAction {
    public void execute(Menu menu, MenuHandler handler);
}
