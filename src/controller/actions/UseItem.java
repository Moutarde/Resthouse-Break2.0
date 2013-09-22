package controller.actions;

import gui.UserInterface;
import gui.contextMenu.Menu;
import model.GameModel;
import model.items.Item;
import model.messages.Message;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class UseItem implements IMenuAction {

    private Item item;
    private GameModel model;

    public UseItem(Item item, GameModel model) {
        this.item = item;
        this.model = model;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        Message message;
        if (item.isUsable(model)) {
            boolean useSucceed = item.use(model);
            message = new Message(useSucceed ? item.getUseFeedback() : item.getUseFailFeedback());
        }
        else {
            message = new Message(UserInterface.getLang().getString("notUsable"));
        }
        handler.showMessage(message);
    }

}
