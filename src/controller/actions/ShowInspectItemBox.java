package controller.actions;

import gui.contextMenu.InspectItemBox;
import model.GameModel;
import model.items.Item;
import controller.handlers.Handler;

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
    public void execute(Object origin, Handler handler) {
        GameModel model = handler.getModel();
        ((InspectItemBox)model.getMenu(GameModel.MenuID.inspectItemBox)).init(item, model.getPlayer().getBag());
        model.showMenu(GameModel.MenuID.inspectItemBox);
    }

}
