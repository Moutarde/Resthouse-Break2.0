package gui.contextMenu;

import gui.UserInterface;

/**
 * @author Nicolas Kniebihler
 *
 */
public enum MenuCategory {
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
