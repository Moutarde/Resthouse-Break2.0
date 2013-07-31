package controller.actions;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import model.GameModel;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowChoiceBox implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        assert !model.isChoiceBoxDisplayed() : "Trying to show item inspection box while it is already displayed";
        if (model.isSubMenuDisplayed() && model.getSubMenu() instanceof BagMenu) {
            handler.showInspectItemBox();
        }
        else {
            assert false : "Trying to show a choice box that doesn't exist";
        }
    }

}
