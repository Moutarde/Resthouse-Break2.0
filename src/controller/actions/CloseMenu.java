package controller.actions;

import gui.contextMenu.Menu;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class CloseMenu implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert menu.isDisplayed() : "Trying to hide menu while it is not displayed";
        menu.display(false);
    }

}
