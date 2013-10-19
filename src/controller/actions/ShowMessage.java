package controller.actions;

import gui.contextMenu.MessageBox;
import gui.contextMenu.SelectAnswerBox;
import gui.contextMenu.StoreMenu;
import model.GameModel;
import model.GameModel.MenuID;
import model.messages.Message;
import model.messages.OpenStore;
import model.messages.Question;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowMessage implements IAction {

    private Message message;

    public ShowMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(Object origin, Handler handler) {
        GameModel model = handler.getModel();

        ((MessageBox)model.getMenu(MenuID.messageBox)).init(message);
        if (!model.isMenuDisplayed(MenuID.messageBox)) {
            model.showMenu(MenuID.messageBox);
        }

        if (message instanceof Question) {
            ((SelectAnswerBox)model.getMenu(MenuID.selectAnswerBox)).init((Question)message);
            model.showMenu(MenuID.selectAnswerBox);
        }
        else if (message instanceof OpenStore) {
            ((StoreMenu)model.getMenu(MenuID.storeMenu)).init(model.getConversation().getSpeaker(), model.getPlayer());
            model.showMenu(MenuID.storeMenu);
        }
    }

}
