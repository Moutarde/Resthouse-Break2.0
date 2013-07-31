package controller;

import gui.contextMenu.BagMenu;
import gui.contextMenu.InspectItemBox;
import gui.contextMenu.Menu;

import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import controller.actions.IMenuAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MenuHandler implements Observer {

    private GameModel model;

    public MenuHandler(GameModel model) {
        this.model = model;
        model.getMenu().addObserver(this);
    }

    // SUB MENU

    public void showBag() {
        Menu subMenu = new BagMenu(model);
        subMenu.addObserver(this);
        subMenu.display(true);
        model.setSubMenu(subMenu);
    }

    // CHOICE BOX

    public void showInspectItemBox() {
        Menu inspectItemBox = new InspectItemBox(model);
        inspectItemBox.addObserver(this);
        inspectItemBox.display(true);
        model.setChoiceBox(inspectItemBox);
    }

    @Override
    public void update(Observable obs, Object action) {
        assert obs instanceof Menu : "Trying to update MenuHandler with an obs which is not a Menu";
        assert action instanceof IMenuAction : "Trying to update MenuHandler with an action which is not a MenuAction";

        ((IMenuAction)action).execute((Menu)obs, this);
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
            prioritaryDisplayedMenu.display(false);
        }
        else {
            model.getMenu().display(true);
        }
    }

    public void moveSelection(int i) {
        Menu prioritaryDisplayedMenu = model.getPrioritaryDisplayedMenu();
        if (prioritaryDisplayedMenu != null) {
            prioritaryDisplayedMenu.changePointedElement(i);
        }
    }
}
