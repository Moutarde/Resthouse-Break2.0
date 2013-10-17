package controller;

import gui.contextMenu.BagMenu;
import gui.contextMenu.InspectItemBox;
import gui.contextMenu.Menu;
import gui.contextMenu.MessageBox;
import gui.contextMenu.SelectAnswerBox;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;
import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MenuHandler implements Observer {

    protected GameModel model;

    public MenuHandler(GameModel model) {
        this.model = model;
    }

    // MESSAGE BOX

    public void showMessage(Message message) {
        Menu messageBox = new MessageBox(message);
        messageBox.addObserver(this);
        messageBox.display(true);
        model.setMessageBox(messageBox);
    }

    public void hideMessage() {
        model.getMessageBox().display(false);
    }

    // SUB MENU

    public void showBag() {
        Menu subMenu = new BagMenu(model.getPlayer().getBag());
        subMenu.addObserver(this);
        subMenu.display(true);
        model.setSubMenu(subMenu);
    }

    // INSPECT ITEM BOX

    public void showInspectItemBox(Item item) {
        Menu inspectItemBox = new InspectItemBox(item, model.getPlayer().getBag(), model);
        inspectItemBox.addObserver(this);
        inspectItemBox.display(true);
        model.setInspectItemBox(inspectItemBox);
    }

    public void hideInspectItemBox() {
        model.getInspectItemBox().display(false);
    }

    // SELECT ANSWER BOX

    public void showSelectAnswerBox(Question question) {
        Menu selectAnswerBox = new SelectAnswerBox(question);
        selectAnswerBox.addObserver(this);
        selectAnswerBox.display(true);
        model.setSelectAnswerBox(selectAnswerBox);
    }

    @Override
    public void update(Observable obs, Object action) {
        assert obs instanceof Menu : "Trying to update MenuHandler with an obs which is not a Menu";

        if (action instanceof List) {
            for (Object o : (List<?>)action) {
                assert o instanceof IAction : "Trying to update MenuHandler with an action which is not a IAction";

                ((IAction)o).execute((Menu)obs, this);
            }
        }
        else {
            assert action instanceof IAction : "Trying to update MenuHandler with an action which is not a IAction";

            ((IAction)action).execute((Menu)obs, this);
        }
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
            model.getMenu().display(true);
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
