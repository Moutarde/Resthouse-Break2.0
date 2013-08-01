package gui.contextMenu;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import model.items.Item;
import model.messages.Message;
import model.messages.Question;
import controller.actions.CloseMenu;
import controller.actions.IMenuAction;
import controller.actions.ShowMessage;
import controller.actions.ThrowItem;
import controller.actions.UseItem;

/**
 * @author Nicolas Kniebihler
 *
 */
public class InspectItemBox extends Menu implements Observer {

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

    public InspectItemBox(GameModel model, Item item) {
        super("", Choice.values().length, model);

        this.item = item;
    }

    @Override
    public void selectElement() {
        Choice pointedChoice = Choice.values()[getPointedElementId()];
        switch (pointedChoice) {
        case RETURN:
            setChanged();
            notifyObservers(new CloseMenu());
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
                List<IMenuAction> actions = new ArrayList<IMenuAction>();
                actions.add(new ThrowItem(item));
                actions.add(new ShowMessage(null));
                Question q = new Question(UserInterface.getLang().getString("confirmThrow"), possibleAnswers, actions);
                q.addObserver(this);

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

    @Override
    public void update(Observable obs, Object arg) {
        assert obs instanceof Question : "Observable is not a Question";

        setChanged();
        notifyObservers(((Question)obs).getAnswerActionIFP());
    }

}
