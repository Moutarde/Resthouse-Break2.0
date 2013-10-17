package controller.actions.menu;

import gui.contextMenu.Menu;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class CloseMenu extends MenuAction {

    private Menu menuToClose;

    public CloseMenu() {
    }

    public CloseMenu(Menu menuToClose) {
        this.menuToClose = menuToClose;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        if (menuToClose != null) {
            menuToClose.close();
        }
        else {
            menu.close();
        }
    }

}
