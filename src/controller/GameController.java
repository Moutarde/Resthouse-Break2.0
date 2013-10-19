package controller;

import gui.UserInterface;
import gui.contextMenu.MessageBox;
import model.GameModel;
import model.items.Item;
import model.items.Key;
import model.messages.Message;
import controller.handlers.EventHandler;
import controller.handlers.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class GameController {
    private GameModel model;
    private MoveHandler moveHandler;

    private MenuHandler menuHandler;

    private EventHandler eventHandler;

    public GameController(GameModel model) {
        this.model = model;

        this.moveHandler = new MoveHandler(model);
        this.menuHandler = new MenuHandler(model);
        this.eventHandler = new EventHandler(model);
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
        if (model.isGamePaused()) {
            menuHandler.validate();

            if (model.getPrioritaryDisplayedMenu() == null) {
                model.setGamePaused(false);
            }
        }
        else if (!moveHandler.isMoving()) {
            if (model.getPlayer().isInFrontOfAChest()) {
                Item item = model.getPlayer().pickChestContentIFP();
                Message message;
                if (item != null) {
                    message = new Message(UserInterface.getLang().getString("itemFound") + item.getName());
                }
                else {
                    message = new Message(UserInterface.getLang().getString("nothingHere"));
                }
                ((MessageBox)model.getMenu(GameModel.MenuID.messageBox)).init(message);
                model.showMenu(GameModel.MenuID.messageBox);
                model.setGamePaused(true);
            }
            else if (model.getPlayer().isInFrontOfACharacter()) {
                model.startConversation();
                model.setGamePaused(true);
            }
            else if (model.getPlayer().isInFrontOfALockedDoor()) {
                Key key = model.getPlayer().getKeyForFrontDoorIFP();
                Message message;
                if (key != null) {
                    boolean useSucceed = key.use(model);
                    message = new Message(useSucceed ? key.getUseFeedback() : key.getUseFailFeedback());
                }
                else {
                    message = new Message(UserInterface.getLang().getString("doorLocked"));
                }
                ((MessageBox)model.getMenu(GameModel.MenuID.messageBox)).init(message);
                model.showMenu(GameModel.MenuID.messageBox);
                model.setGamePaused(true);
            }
        }
    }

    public void onOpenMenu() {
        menuHandler.openOrClose();

        if (model.getPrioritaryDisplayedMenu() == null) {
            model.setGamePaused(false);
        }
    }

    public void onMoveMenuSelection(Direction d) {
        menuHandler.moveSelection(d == Direction.DOWN ? 1 : -1);
    }
}
