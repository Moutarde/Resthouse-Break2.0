package controller;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.items.Key;

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
        this.model.setGamePaused(true);
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
            if (model.isSelectAnswerBoxDisplayed()) {
                model.getSelectAnswerBox().selectElement();
            }
            else if (conversationHandler.isSpeaking()) {
                conversationHandler.continueSpeech();
            }
            else {
                model.hideMessage();
                if (!model.isMenuDisplayed()) {
                    model.setGamePaused(false);
                }
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
                model.setGamePaused(true);
            }
            else if (model.getPlayer().isInFrontOfACharacter()) {
                conversationHandler.start();
            }
            else if (model.getPlayer().isInFrontOfALockedDoor()) {
                Key key = model.getPlayer().getKeyForFrontDoorIFP();
                if (key != null) {
                    boolean useSucceed = key.use(model);
                    model.setNewMessage(useSucceed ? key.getUseFeedback() : key.getUseFailFeedback());
                }
                else {
                    model.setNewMessage(UserInterface.getLang().getString("doorLocked"));
                }
                model.setGamePaused(true);
            }
        }
    }

    public void onOpenMenu() {
        if (!model.isMessageDisplayed()) {
            menuHandler.openOrClose();
        }
    }

    public void onMoveMenuSelection(Direction d) {
        if (!model.isMessageDisplayed()) {
            menuHandler.moveSelection(d == Direction.DOWN ? 1 : -1);
        }
        else if (model.isSelectAnswerBoxDisplayed()) {
            model.getSelectAnswerBox().changePointedElement(d == Direction.DOWN ? 1 : -1);
        }
    }
}
