package gui.contextMenu;

import controller.actions.CloseMenu;
import controller.actions.ShowMessage;
import controller.actions.UseItem;
import gui.UserInterface;
import model.GameModel;

/**
 * @author Nicolas Kniebihler
 *
 */
public class InspectItemBox extends Menu {

    private static enum Choice {
        INSPECT, USE, RETURN;

        public String toString() {
            switch (this) {
            case INSPECT:
                return UserInterface.getLang().getString("inspect");
            case USE:
                return UserInterface.getLang().getString("use");
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
        case INSPECT:
            setChanged();
            notifyObservers(new ShowMessage());
            break;
        case USE:
            setChanged();
            notifyObservers(new UseItem());
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        return Choice.values()[index].toString();
    }

}
