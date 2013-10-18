package controller.actions;

import gui.contextMenu.Menu;

/**
 * @author Nicolas Kniebihler
 *
 */
public class CloseMenu implements IAction {

    private Menu menuToClose;

    public CloseMenu() {
    }

    public CloseMenu(Menu menuToClose) {
        this.menuToClose = menuToClose;
    }

    @Override
    public void execute(Object origin, Object handler) {
        if (menuToClose != null) {
            menuToClose.close();
        }
        else {
            assert origin instanceof Menu : "origin is not a Menu";
            ((Menu)origin).close();
        }
    }

}
