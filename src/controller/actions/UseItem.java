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
public class UseItem implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        Menu subMenu = model.getSubMenu();
        if (subMenu != null && subMenu instanceof BagMenu) {
            assert model.isSubMenuDisplayed() : "Trying to use item while subMenu isn't displayed";

            int itemId = subMenu.getPointedElementId();
            Item item = model.getPlayer().getBag().getItem(itemId);
            if (item.isUsable(model)) {
                boolean useSucceed = item.use(model);
                model.setNewMessage(useSucceed ? item.getUseFeedback() : item.getUseFailFeedback());
            }
            else {
                model.setNewMessage(UserInterface.getLang().getString("notUsable"));
            }
            model.setGamePaused(true);
        }
    }

}
