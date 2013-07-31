package controller.actions;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import model.GameModel;
import model.items.Item;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowMessage implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        Menu subMenu = model.getSubMenu();
        if (subMenu != null && subMenu instanceof BagMenu) {
            assert model.isSubMenuDisplayed() : "Trying to show item description while subMenu isn't displayed";

            int itemId = subMenu.getPointedElementId();
            Item item = model.getPlayer().getBag().getItem(itemId);
            model.setNewMessage(item.getDescription());
            model.setGamePaused(true);
        }
    }

}
