package controller.actions.menu;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import model.items.Item;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowInspectItemBox extends MenuAction {

    private Item item;

    public ShowInspectItemBox(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        if (menu instanceof BagMenu) {
            handler.showInspectItemBox(item);
        }
        else {
            assert false : "Trying to show a choice box that doesn't exist";
        }
    }

}
