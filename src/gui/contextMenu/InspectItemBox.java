package gui.contextMenu;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;
import model.player.Bag;
import controller.actions.IAction;
import controller.actions.ThrowItem;
import controller.actions.UseItem;
import controller.actions.menu.CloseMenu;
import controller.actions.menu.ShowMessage;

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
    private GameModel model;

    public InspectItemBox(Item item, Bag bag, GameModel model) {
        super("", Choice.values().length);

        this.item = item;
        this.bag = bag;
        this.model = model;
    }

    @Override
    public void selectElement() {
        Choice pointedChoice = Choice.values()[getPointedElementId()];
        switch (pointedChoice) {
        case RETURN:
            close();
            break;
        case USE:
            setChanged();
            notifyObservers(new UseItem(item, model));
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
                cancelActionList.add(new ShowMessage(null));

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
        return Choice.values()[index].toString();
    }

}
