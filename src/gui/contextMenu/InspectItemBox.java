package gui.contextMenu;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.messages.Message;
import controller.actions.CloseMenu;
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
            notifyObservers(new ThrowItem(item));
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
