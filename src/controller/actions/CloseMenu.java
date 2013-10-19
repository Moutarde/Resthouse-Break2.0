package controller.actions;

import gui.contextMenu.Menu;
import model.GameModel.MenuID;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class CloseMenu implements IAction {

    private Menu menuToClose;
    private MenuID menuToCloseID;

    public CloseMenu() {
    }

    public CloseMenu(Menu menuToClose) {
        this.menuToClose = menuToClose;
    }

    public CloseMenu(MenuID menuToCloseID) {
        this.menuToCloseID = menuToCloseID;
    }

    @Override
    public void execute(Object origin, Handler handler) {
        if (menuToClose != null) {
            menuToClose.close();
        }
        else if (menuToCloseID != null) {
            handler.getModel().getMenu(menuToCloseID).close();
        }
        else {
            assert origin instanceof Menu : "origin is not a Menu";
            ((Menu)origin).close();
        }
    }

}
