package controller.actions;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.messages.Message;
import controller.handlers.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class UseItem implements IAction {

    private Item item;
    private GameModel model;

    public UseItem(Item item, GameModel model) {
        this.item = item;
        this.model = model;
    }

    @Override
    public void execute(Object origin, Object handler) {
        assert handler instanceof MenuHandler : "handler is not a MenuHandler";

        Message message;
        if (item.isUsable(model)) {
            boolean useSucceed = item.use(model);
            message = new Message(useSucceed ? item.getUseFeedback() : item.getUseFailFeedback());
        }
        else {
            message = new Message(UserInterface.getLang().getString("notUsable"));
        }
        ((MenuHandler)handler).showMessage(message);
    }

}
