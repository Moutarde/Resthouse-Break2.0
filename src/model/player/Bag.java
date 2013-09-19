package model.player;

import java.util.HashMap;
import java.util.Set;

import model.items.Item;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Bag {
    private HashMap<Item, Integer> items = new HashMap<Item, Integer>();

    public Bag() {

    }

    public int getAmountOf(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        else {
            return 0;
        }
    }

    public int getSize() {
        return items.size();
    }

    public boolean contains(Item item) {
        return items.containsKey(item);
    }

    public Set<Item> getItems() {
        return items.keySet();
    }

    public void addItem(Item i) {
        addItem(i, 1);
    }

    public void addItem(Item i, int nb) {
        if (items.containsKey(i)) {
            int currentAmount = items.get(i);
            items.put(i, currentAmount + nb);
        }
        else {
            items.put(i, nb);
        }
    }

    public void removeItemIFP(Item i) {
        removeItemIFP(i, 1);
    }

    public void removeItemIFP(Item i, int nb) {
        if (!items.containsKey(i)) {
            return;
        }

        int currentAmount = items.get(i);
        if (currentAmount <= nb) {
            items.remove(i);
        }
        else {
            items.put(i, currentAmount - nb);
        }
    }

    public Item getItem(int index) {
        int i = 0;
        for (Item item : items.keySet()) {
            if (i == index) {
                return item;
            }
            ++i;
        }

        assert false : "No item for index " + index;
        return null;
    }
}
