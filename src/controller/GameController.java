package controller;

import gui.UserInterface;
import model.GameModel;
import model.chests.Item;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameController {
    private GameModel model;
    private MoveHandler moveHandler;
    private MenuHandler menuHandler;
    private ConversationHandler conversationHandler;

    public GameController(GameModel model) {
        this.model = model;
        this.moveHandler = new MoveHandler(model);
        this.menuHandler = new MenuHandler(model);
        this.conversationHandler = new ConversationHandler(model);

        this.model.setNewMessage(UserInterface.getLang().getString("firstMessage"));
    }

    public MenuHandler getMenuHandler() {
        return menuHandler;
    }

    public void update(float delta) {
        if (!model.isGamePaused()) {
            moveHandler.evolvePlayers();
            moveHandler.evolveMove(model.getPlayer());
        }
    }

    public void onStartMovingAsked(Direction d) {
        if (!model.isGamePaused()) {
            moveHandler.startMoving(d);
        }
    }

    public void onStopMovingAsked(Direction d) {
        moveHandler.stopMoving(d);
    }

    public void onValidate() {
        if (model.isMessageDisplayed()) {
            if (conversationHandler.isSpeaking()) {
                conversationHandler.continueSpeech();
            }
            else {
                model.hideMessage();
            }
        }
        else if (model.isMenuDisplayed()) {
            menuHandler.validate();
        }
        else if (!moveHandler.isMoving()) {
            if (model.getPlayer().isInFrontOfAChest()) {
                Item item = model.getPlayer().pickChestContentIFP();
                if (item != null) {
                    model.setNewMessage(UserInterface.getLang().getString("itemFound") + item.getName());
                }
                else {
                    model.setNewMessage(UserInterface.getLang().getString("nothingHere"));
                }
            }
            else if (model.getPlayer().isInFrontOfACharacter()) {
                conversationHandler.start();
            }
        }
    }

    public void onOpenMenu() {
        menuHandler.openOrClose();
    }

    public void onMoveMenuSelection(Direction d) {
        menuHandler.moveSelection(d == Direction.DOWN ? 1 : -1);
    }
}
