package controller.actions;

import gui.contextMenu.Menu;
import model.GameModel;
import model.messages.Message;
import model.messages.Question;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowMessage implements IMenuAction {

    private Message message;

    public ShowMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();

        if (message == null) {
            model.hideMessage();
            if (!model.isMenuDisplayed()) {
                model.setGamePaused(false);
            }
        }
        else {
            model.setNewMessage(message);
            model.setGamePaused(true);

            if (message instanceof Question) {
                handler.showSelectAnswerBox((Question)message);
            }
        }
    }

}
