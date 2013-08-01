package controller.actions;

import gui.UserInterface;
import gui.contextMenu.Menu;
import model.GameModel;
import model.items.Item;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class UseItem implements IMenuAction {

    private Item item;

    public UseItem(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
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
