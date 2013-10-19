package controller.actions;

import gui.UserInterface;
import gui.contextMenu.MessageBox;
import model.GameModel;
import model.items.Item;
import model.messages.Message;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class UseItem implements IAction {

    private Item item;

    public UseItem(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Object origin, Handler handler) {
        GameModel model = handler.getModel();

        Message message;
        if (item.isUsable(model)) {
            boolean useSucceed = item.use(model);
            message = new Message(useSucceed ? item.getUseFeedback() : item.getUseFailFeedback());
        }
        else {
            message = new Message(UserInterface.getLang().getString("notUsable"));
        }
        ((MessageBox)model.getMenu(GameModel.MenuID.messageBox)).init(message);
        model.showMenu(GameModel.MenuID.messageBox);
    }

}
