package controller.handlers;

import gui.contextMenu.ContextMenu;
import gui.contextMenu.Menu;

import java.util.Observable;

import model.GameModel;
import model.GameModel.MenuID;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MenuHandler extends Handler {

    public MenuHandler(GameModel model) {
        super(model);

        this.model.getMenu(GameModel.MenuID.messageBox).addObserver(this);
        this.model.getMenu(GameModel.MenuID.menu).addObserver(this);
        this.model.getMenu(GameModel.MenuID.subMenu).addObserver(this);
        this.model.getMenu(GameModel.MenuID.inspectItemBox).addObserver(this);
        this.model.getMenu(GameModel.MenuID.selectAnswerBox).addObserver(this);
        this.model.getMenu(GameModel.MenuID.storeMenu).addObserver(this);
        this.model.getMenu(GameModel.MenuID.transactionMenu).addObserver(this);

        this.model.getConversation().addObserver(this);
    }

    @Override
    public void update(Observable obs, Object action) {
        super.update(obs, action);
    }

    public void validate() {
        Menu prioritaryDisplayedMenu = model.getPrioritaryDisplayedMenu();
        if (prioritaryDisplayedMenu != null) {
            prioritaryDisplayedMenu.selectElement();
        }
    }

    public void openOrClose() {
        Menu prioritaryDisplayedMenu = model.getPrioritaryDisplayedMenu();
        if (prioritaryDisplayedMenu != null) {
            prioritaryDisplayedMenu.close();
        }
        else {
            ((ContextMenu)model.getMenu(MenuID.menu)).init();
            model.showMenu(MenuID.menu);
            model.setGamePaused(true);
        }
    }

    public void moveSelection(int i) {
        Menu prioritaryDisplayedMenu = model.getPrioritaryDisplayedMenu();
        if (prioritaryDisplayedMenu != null) {
            prioritaryDisplayedMenu.changePointedElement(i);
        }
    }
}
