package controller.actions;

import model.items.Item;
import model.player.Bag;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ThrowItem implements IAction {

    private Item item;
    private Bag bag;

    public ThrowItem(Item item, Bag bag) {
        this.item = item;
        this.bag = bag;
    }

    @Override
    public void execute(Object origin, Object handler) {
        assert item.isThrowable() : "Trying to throw a non-throwable item";

        bag.removeItemIFP(item);
    }

}
