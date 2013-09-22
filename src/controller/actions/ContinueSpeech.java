package controller.actions;

import gui.contextMenu.Menu;
import controller.ConversationHandler;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ContinueSpeech implements IMenuAction {

    public ContinueSpeech() {
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert handler instanceof ConversationHandler : "handler is not a ConversationHandler";

        menu.display(false);
        ((ConversationHandler)handler).continueSpeech();
    }

}
