package gui.contextMenu;

import gui.UserInterface;

import java.util.Observable;
import java.util.Observer;

import model.items.Item;
import model.player.Bag;
import controller.actions.menu.ShowInspectItemBox;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu implements Observer {
    protected Bag bag;

    public BagMenu(Bag bag) {
        super("bag", bag.getSize() + 1);
        this.bag = bag;
        this.bag.addObserver(this);
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

    @Override
    public void update(Observable obs, Object obj) {
        assert obs instanceof Bag : "BagMenu observes an Object that is not a Bag...";

        this.setNbElements(bag.getSize() + 1);
    }

}
