package gui.contextMenu;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.player.Bag;
import controller.actions.CloseMenu;
import controller.actions.ShowInspectItemBox;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu {
    protected Bag bag;

    public BagMenu(GameModel model, Bag bag) {
        super("bag", bag.getSize() + 1, model);
        this.bag = bag;
    }

    protected Item getPointedItem() {
        int id = getPointedElementId();
        if (id == getNbElements() - 1) {
            return null;
        }
        else {
            assert id < bag.getSize() : "Bag size and BagMenu size are not equal";
            return bag.getItem(id);
        }
    }

    public void updateContent() {
        this.setNbElements(bag.getSize() + 1);
    }

    @Override
    public void selectElement() {
        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            setChanged();
            notifyObservers(new CloseMenu());
        }
        else {
            setChanged();
            notifyObservers(new ShowInspectItemBox(pointedItem));
        }
    }

    @Override
    public String getElementString(int index) {
        if (index == getNbElements() - 1) {
            return UserInterface.getLang().getString("return");
        }

        Item item = bag.getItem(index);
        return "" + bag.getAmountOf(item) + "x " + item.getName();
    }

}
