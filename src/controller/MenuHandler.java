package controller;

import gui.contextMenu.BagMenu;
import gui.contextMenu.Menu;
import gui.contextMenu.MenuAction;
import gui.contextMenu.MenuCategory;

import java.util.Observable;
import java.util.Observer;

import model.GameModel;
import model.chests.Item;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MenuHandler implements Observer {

    private GameModel model;

    public MenuHandler(GameModel model) {
        this.model = model;
        model.getMenu().addObserver(this);
    }

    // MENU

    public void showMenu() {
        model.getMenu().display(true);
        model.setGamePaused(true);
    }

    public void hideMenu() {
        model.getMenu().display(false);
        model.setGamePaused(false);
    }

    // SUB MENU

    public void showBag() {
        Menu subMenu = new BagMenu("bag", model);
        subMenu.addObserver(this);
        subMenu.display(true);
        model.setSubMenu(subMenu);
    }

    public void hideSubMenu() {
        model.setSubMenu(null);
    }

    @Override
    public void update(Observable obs, Object action) {
        assert action instanceof MenuAction : "Trying to update MenuHandler with an object which is not a MenuAction";

        switch ((MenuAction)action) {
        case RETURN:
            if (model.isSubMenuDisplayed()) {
                hideSubMenu();
            }
            else if (model.isMenuDisplayed()) {
                hideMenu();
            }
            else {
                assert false : "Trying to hide menu while it is not displayed";
            }
            break;
        case SHOW_SUBMENU:
            assert !model.isSubMenuDisplayed() : "Trying to show subMenu while it is already displayed";
            MenuCategory pointedCategory = MenuCategory.values()[model.getMenu().getPointedElementId()];
            if (pointedCategory == MenuCategory.BAG) {
                showBag();
            }
            else {
                assert false : "Trying to show a subMenu that doesn't exist : " + pointedCategory;
            }
            break;
        case SHOW_MESSAGE:
            Menu subMenu = model.getSubMenu();
            if (subMenu != null && subMenu instanceof BagMenu) {
                assert model.isSubMenuDisplayed() : "Trying to show item description while subMenu isn't displayed";

                int itemId = model.getSubMenu().getPointedElementId();
                Item item = model.getPlayer().getBag().getItem(itemId);
                model.setNewMessage(item.getDescription());
            }
            break;
        default:
            break;
        }
    }

    public void validate() {
        if (model.isSubMenuDisplayed()) {
            model.getSubMenu().selectElement();
        }
        else {
            model.getMenu().selectElement();
        }
    }

    public void openOrClose() {
        if (model.isMenuDisplayed()) {
            if (model.isSubMenuDisplayed()) {
                hideSubMenu();
            }
            else {
                hideMenu();
            }
        }
        else if (!model.isMessageDisplayed()) {
            showMenu();
        }
    }

    public void moveSelection(int i) {
        if (model.isMenuDisplayed()) {
            if (model.isSubMenuDisplayed()) {
                model.getSubMenu().changePointedElement(i);
            }
            else {
                model.getMenu().changePointedElement(i);
            }
        }
    }
}
