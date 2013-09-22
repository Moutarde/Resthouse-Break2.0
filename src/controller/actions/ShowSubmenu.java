package controller.actions;

import gui.contextMenu.ContextMenu;
import gui.contextMenu.Menu;
import controller.MenuHandler;

/**
 * @author Nicolas
 *
 */
public class ShowSubmenu implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert menu instanceof ContextMenu : "menu is not a ContextMenu";
        ContextMenu.MenuCategory pointedCategory = ContextMenu.MenuCategory.values()[menu.getPointedElementId()];
        if (pointedCategory == ContextMenu.MenuCategory.BAG) {
            handler.showBag();
        }
        else {
            assert false : "Trying to show a subMenu that doesn't exist : " + pointedCategory;
        }
    }

}
