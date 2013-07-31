package gui.contextMenu;

import gui.UserInterface;
import model.GameModel;
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

    public InspectItemBox(GameModel model) {
        super("", Choice.values().length, model);
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
            notifyObservers(new UseItem());
            break;
        case INSPECT:
            setChanged();
            notifyObservers(new ShowMessage());
            break;
        case THROW:
            setChanged();
            notifyObservers(new ThrowItem());
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
