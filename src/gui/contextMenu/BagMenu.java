package gui.contextMenu;

import gui.UserInterface;
import model.items.Item;
import model.player.Bag;
import controller.actions.ShowInspectItemBox;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu {
    protected Bag bag;

    public BagMenu(Bag bag) {
        super("bag", bag.getSize() + 1);
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
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
            close();
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
