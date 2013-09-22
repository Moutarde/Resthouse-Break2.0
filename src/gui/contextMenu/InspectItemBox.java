package gui.contextMenu;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;
import model.player.Bag;
import controller.actions.IMenuAction;
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
                List<IMenuAction> actions = new ArrayList<IMenuAction>();
                actions.add(new ThrowItem(item, bag));
                actions.add(new ShowMessage(null));
                Question q = new Question(UserInterface.getLang().getString("confirmThrow"), possibleAnswers, actions);

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
