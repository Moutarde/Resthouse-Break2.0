package gui.contextMenu;

import gui.UserInterface;

import java.util.Observable;
import java.util.Observer;

import model.items.Item;
import model.player.Bag;
import controller.actions.ShowInspectItemBox;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu implements Observer {
    protected Bag bag;

    public BagMenu() {
        super("bag", 0);
    }

    public void init(Bag bag) {
        this.setNbElements(bag.getSize() + 1);
        this.bag = bag;
        this.bag.addObserver(this);

        super.init();
    }

    @Override
    public void clean() {
        this.bag.deleteObserver(this);
        this.bag = null;

        super.clean();
    }

    public Bag getBag() {
        assert isInitialized : "Menu not initialized";

        return bag;
    }

    protected Item getPointedItem() {
        assert isInitialized : "Menu not initialized";

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
        assert isInitialized : "Menu not initialized";

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
        assert isInitialized : "Menu not initialized";

        if (index == getNbElements() - 1) {
            return UserInterface.getLang().getString("return");
        }

        Item item = bag.getItem(index);
        return "" + bag.getAmountOf(item) + "x " + item.getName();
    }

    @Override
    public void update(Observable obs, Object obj) {
        assert isInitialized : "Menu not initialized";

        assert obs instanceof Bag : "BagMenu observes an Object that is not a Bag...";

        this.setNbElements(bag.getSize() + 1);
    }

}
