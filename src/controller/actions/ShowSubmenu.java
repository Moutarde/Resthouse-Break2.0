package controller.actions;

import gui.contextMenu.ContextMenu;
import controller.handlers.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowSubmenu implements IAction {

    @Override
    public void execute(Object origin, Object handler) {
        assert origin instanceof ContextMenu : "menu is not a ContextMenu";
        assert handler instanceof MenuHandler : "handler is not a MenuHandler";

        ContextMenu.MenuCategory pointedCategory = ContextMenu.MenuCategory.values()[((ContextMenu)origin).getPointedElementId()];
        if (pointedCategory == ContextMenu.MenuCategory.BAG) {
            ((MenuHandler)handler).showBag();
        }
        else {
            assert false : "Trying to show a subMenu that doesn't exist : " + pointedCategory;
        }
    }

}
