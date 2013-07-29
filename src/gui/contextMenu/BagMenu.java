package gui.contextMenu;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.player.Bag;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu {
    private Bag bag;

    public BagMenu(GameModel model) {
        super("bag", model.getPlayer().getBag().getContent().size() + 1, model);
        this.bag = model.getPlayer().getBag();
    }

    @Override
    public void selectElement() {
        if (getPointedElementId() == getNbElements() - 1) {
            setChanged();
            notifyObservers(MenuAction.RETURN);
        }
        else {
            setChanged();
            notifyObservers(MenuAction.SHOW_CHOICE_BOX);
        }
    }

    @Override
    public String getElementString(int index) {
        if (index == getNbElements() - 1) {
            return UserInterface.getLang().getString("return");
        }

        Item item = bag.getItem(index);
        return "" + bag.getContent().get(item) + "x " + item.getName();
    }

}
