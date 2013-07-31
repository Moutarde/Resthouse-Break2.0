package controller.actions;

import gui.contextMenu.Menu;
import gui.contextMenu.MenuCategory;
import model.GameModel;
import controller.MenuHandler;

/**
 * @author Nicolas
 *
 */
public class ShowSubmenu implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        assert !model.isSubMenuDisplayed() : "Trying to show subMenu while it is already displayed";
        MenuCategory pointedCategory = MenuCategory.values()[model.getMenu().getPointedElementId()];
        if (pointedCategory == MenuCategory.BAG) {
            handler.showBag();
        }
        else {
            assert false : "Trying to show a subMenu that doesn't exist : " + pointedCategory;
        }
    }

}
