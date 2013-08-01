package controller;

import gui.contextMenu.BagMenu;
import gui.contextMenu.InspectItemBox;
import gui.contextMenu.Menu;
import gui.contextMenu.SelectAnswerBox;

import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import model.items.Item;
import model.messages.Question;
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

    // INSPECT ITEM BOX

    public void showInspectItemBox(Item item) {
        Menu inspectItemBox = new InspectItemBox(model, item);
        inspectItemBox.addObserver(this);
        inspectItemBox.display(true);
        model.setInspectItemBox(inspectItemBox);
    }

    // SELECT ANSWER BOX

    public void showSelectAnswerBox(Question question) {
        Menu selectAnswerBox = new SelectAnswerBox(model, question);
        selectAnswerBox.addObserver(this);
        selectAnswerBox.display(true);
        model.setSelectAnswerBox(selectAnswerBox);
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
