package gui.contextMenu;

import controller.actions.CloseMenu;
import controller.actions.ShowSubmenu;
import gui.UserInterface;
import model.GameModel;


/**
 * @author Nicolas Kniebihler
 *
 */

public class ContextMenu extends Menu {

    public ContextMenu(GameModel model) {
        super("menu", MenuCategory.values().length, model);
    }

    @Override
    public void selectElement() {
        MenuCategory pointedCategory = MenuCategory.values()[getPointedElementId()];
        switch (pointedCategory) {
        case BAG:
            setChanged();
            notifyObservers(new ShowSubmenu());
            break;
        case QUIT:
            UserInterface.quit();
            break;
        case RETURN:
            setChanged();
            notifyObservers(new CloseMenu());
            break;
        default:
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        return MenuCategory.values()[index].toString();
    }

    @Override
    public void display(boolean value) {
        super.display(value);
        getModel().setGamePaused(value);
    }

}
