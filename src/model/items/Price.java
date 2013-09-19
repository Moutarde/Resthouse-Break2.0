package model.items;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Price {

    private String itemId;
    private int amount;

    public Price(String itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public Item getItem() {
        return Item.getItem(itemId);
    }

    public int getAmount() {
        return amount;
    }

}
