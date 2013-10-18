package controller.actions;

import model.items.Item;
import controller.handlers.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowInspectItemBox implements IAction {

    private Item item;

    public ShowInspectItemBox(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Object origin, Object handler) {
        assert handler instanceof MenuHandler : "handler is not a MenuHandler";

        ((MenuHandler)handler).showInspectItemBox(item);
    }

}
