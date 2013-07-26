package gui.contextMenu;

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
            notifyObservers(MenuAction.SHOW_SUBMENU);
            break;
        case QUIT:
            UserInterface.quit();
            break;
        case RETURN:
            setChanged();
            notifyObservers(MenuAction.RETURN);
            break;
        default:
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        return MenuCategory.values()[index].toString();
    }
}
