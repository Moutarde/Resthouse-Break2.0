package gui.contextMenu;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;

import model.GameModel.MenuID;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;
import model.player.Bag;
import controller.actions.CloseMenu;
import controller.actions.IAction;
import controller.actions.ShowMessage;
import controller.actions.ThrowItem;
import controller.actions.UseItem;

/**
 * @author Nicolas Kniebihler
 *
 */
public class InspectItemBox extends Menu {

    private static enum Choice {
        USE, INSPECT, THROW, RETURN;

        public String toString() {
            switch (this) {
            case USE:
                return UserInterface.getLang().getString("use");
            case INSPECT:
                return UserInterface.getLang().getString("inspect");
            case THROW:
                return UserInterface.getLang().getString("throw");
            case RETURN:
                return UserInterface.getLang().getString("return");
            }

            return null;
        }
    }

    private Item item;
    private Bag bag;

    public InspectItemBox() {
        super("", Choice.values().length);
    }

    public void init(Item item, Bag bag) {
        this.item = item;
        this.bag = bag;

        super.init();
    }

    @Override
    public void clean() {
        this.item = null;
        this.bag = null;

        super.clean();
    }

    @Override
    public void selectElement() {
        assert isInitialized : "Menu not initialized";

        Choice pointedChoice = Choice.values()[getPointedElementId()];
        switch (pointedChoice) {
        case RETURN:
            close();
            break;
        case USE:
            setChanged();
            notifyObservers(new UseItem(item));
            break;
        case INSPECT:
            setChanged();
            notifyObservers(new ShowMessage(new Message(item.getDescription())));
            break;
        case THROW:
            setChanged();
            if (item.isThrowable()) {
                List<String> possibleAnswers = new ArrayList<String>();
                possibleAnswers.add(UserInterface.getLang().getString("yes"));
                possibleAnswers.add(UserInterface.getLang().getString("no"));

                List<List<IAction>> actionLists = new ArrayList<List<IAction>>();

                List<IAction> throwActionList = new ArrayList<IAction>();
                throwActionList.add(new ThrowItem(item, bag));
                throwActionList.add(new ShowMessage(new Message(UserInterface.getLang().getString("thrown") + item.getName())));
                throwActionList.add(new CloseMenu(this));

                List<IAction> cancelActionList = new ArrayList<IAction>();
                cancelActionList.add(new CloseMenu(MenuID.messageBox));

                actionLists.add(throwActionList);
                actionLists.add(cancelActionList);

                Question q = new Question(UserInterface.getLang().getString("confirmThrow"), possibleAnswers, actionLists);

                notifyObservers(new ShowMessage(q));
            }
            else {
                notifyObservers(new ShowMessage(new Message(UserInterface.getLang().getString("notThrowable"))));
            }
            break;
        default:
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        assert isInitialized : "Menu not initialized";

        return Choice.values()[index].toString();
    }

}
