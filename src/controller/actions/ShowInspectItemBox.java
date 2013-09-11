package controller.actions;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import gui.contextMenu.StoreMenu;
import model.GameModel;
import model.items.Item;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowInspectItemBox implements IMenuAction {

    private Item item;

    public ShowInspectItemBox(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        assert !model.isInspectItemBoxDisplayed() : "Trying to show item inspection box while it is already displayed";
        if ((model.isSubMenuDisplayed() && menu instanceof BagMenu) || (model.isStoreMenuDisplayed() && menu instanceof StoreMenu)) {
            handler.showInspectItemBox(item);
        }
        else {
            assert false : "Trying to show a choice box that doesn't exist";
        }
    }

}
