package controller.actions;

import gui.contextMenu.BagMenu;
import gui.contextMenu.ContextMenu;
import model.GameModel;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowSubmenu implements IAction {

    @Override
    public void execute(Object origin, Handler handler) {
        assert origin instanceof ContextMenu : "menu is not a ContextMenu";

        ContextMenu.MenuCategory pointedCategory = ContextMenu.MenuCategory.values()[((ContextMenu)origin).getPointedElementId()];
        if (pointedCategory == ContextMenu.MenuCategory.BAG) {
            GameModel model = handler.getModel();
            ((BagMenu)model.getMenu(GameModel.MenuID.subMenu)).init(model.getPlayer().getBag());
            model.showMenu(GameModel.MenuID.subMenu);
        }
        else {
            assert false : "Trying to show a subMenu that doesn't exist : " + pointedCategory;
        }
    }

}
