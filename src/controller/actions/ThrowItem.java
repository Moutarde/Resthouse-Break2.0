package controller.actions;

import gui.UserInterface;
import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import model.GameModel;
import model.items.Item;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ThrowItem implements IMenuAction {

    private Item item;

    public ThrowItem(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        Menu subMenu = model.getSubMenu();
        if (subMenu != null && subMenu instanceof BagMenu) {
            assert model.isSubMenuDisplayed() : "Trying to throw item while subMenu isn't displayed";
            assert item.isThrowable() : "Trying to throw a non-throwable item";

            model.getPlayer().getBag().removeItemIFP(item);
            model.setNewMessage(UserInterface.getLang().getString("thrown") + item.getName());

            ((BagMenu)subMenu).setNbElements(model.getPlayer().getBag().getContent().size() + 1);
            menu.display(false);
        }
    }

}
