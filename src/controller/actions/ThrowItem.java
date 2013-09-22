package controller.actions;

import gui.UserInterface;
import gui.contextMenu.Menu;
import model.items.Item;
import model.messages.Message;
import model.player.Bag;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ThrowItem implements IMenuAction {

    private Item item;
    private Bag bag;

    public ThrowItem(Item item, Bag bag) {
        this.item = item;
        this.bag = bag;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert item.isThrowable() : "Trying to throw a non-throwable item";

        bag.removeItemIFP(item);
        handler.showMessage(new Message(UserInterface.getLang().getString("thrown") + item.getName()));
        handler.updateBag();
        handler.hideInspectItemBox();
    }

}
