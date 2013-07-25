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

    public GameController(GameModel model) {
        this.model = model;
        this.moveHandler = new MoveHandler(model);

        model.setNewMessage(UserInterface.getLang().getString("firstMessage"));
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
            model.hideMessage();
        }
        else if (model.isMenuDisplayed()) {
            if (model.isSubMenuDisplayed()) {
                model.getSubMenu().selectElement();
            }
            else {
                model.getMenu().selectElement();
            }
        }
        else if (!moveHandler.isMoving() && model.getPlayer().isInFrontOfAChest()) {
            Item item = model.getPlayer().pickChestContentIFP();
            if (item != null) {
                model.setNewMessage(UserInterface.getLang().getString("itemFound") + item.getName());
            }
            else {
                model.setNewMessage(UserInterface.getLang().getString("nothingHere"));
            }
        }
    }

    public void onOpenMenu() {
        if (model.isMenuDisplayed()) {
            model.hideMenu();
        }
        else if (!model.isMessageDisplayed()) {
            model.showMenu();
        }
    }

    public void onMoveMenuSelection(Direction d) {
        if (model.isMenuDisplayed()) {
            if (model.isSubMenuDisplayed()) {
                model.getSubMenu().changePointedElement(d == Direction.DOWN ? 1 : -1);
            }
            else {
                model.getMenu().changePointedElement(d == Direction.DOWN ? 1 : -1);
            }
        }
    }
}
