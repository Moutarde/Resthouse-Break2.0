package gui.contextMenu;

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
            notifyObservers(MenuAction.RETURN);
            break;
        case INSPECT:
            setChanged();
            notifyObservers(MenuAction.SHOW_MESSAGE);
            break;
        case USE:
            setChanged();
            notifyObservers(MenuAction.USE_ITEM);
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        return Choice.values()[index].toString();
    }

}
