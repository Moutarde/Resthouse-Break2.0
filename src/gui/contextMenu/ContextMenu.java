package gui.contextMenu;

import gui.UserInterface;
import controller.actions.ShowSubmenu;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ContextMenu extends Menu {

    public static enum MenuCategory {
        BAG, QUIT, RETURN;

        public String toString() {
            switch (this) {
            case BAG:
                return UserInterface.getLang().getString("bag");
            case QUIT:
                return UserInterface.getLang().getString("menuQuit");
            case RETURN:
                return UserInterface.getLang().getString("return");
            }

            return null;
        }
    }

    public ContextMenu() {
        super("menu", MenuCategory.values().length);
    }

    @Override
    public void selectElement() {
        assert isInitialized : "Menu not initialized";

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
            close();
            break;
        default:
            break;
        }
    }

    @Override
    public String getElementString(int index) {
        assert isInitialized : "Menu not initialized";

        return MenuCategory.values()[index].toString();
    }

}
