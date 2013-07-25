package model.chests;


/**
 * @author Nicolas Kniebihler
 *
 */
public class Chest {
    Item item = null;

    public Chest() {

    }

    public void setItem(Item i) {
        assert item == null : "There is already an object in this chest";
        item = i;
    }

    public Item getItem() {
        return item;
    }

    public void empty() {
        item = null;
    }
}
