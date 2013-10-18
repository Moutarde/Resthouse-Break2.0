package controller.handlers;

import gui.contextMenu.BagMenu;
import gui.contextMenu.InspectItemBox;
import gui.contextMenu.Menu;
import gui.contextMenu.MessageBox;
import gui.contextMenu.SelectAnswerBox;

import java.util.Observable;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MenuHandler extends Handler {

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
