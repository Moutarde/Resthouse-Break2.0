package controller.actions;

import gui.contextMenu.Menu;
import model.GameModel;
import model.messages.Message;
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
        model.setNewMessage(message.getString());
        model.setGamePaused(true);
    }

}
