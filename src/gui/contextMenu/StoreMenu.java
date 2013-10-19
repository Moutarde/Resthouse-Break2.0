package gui.contextMenu;

import gui.UserInterface;
import model.items.Item;
import model.items.Price;
import model.player.Player;
import controller.actions.ContinueSpeech;
import controller.actions.ShowTransactionMenu;

/**
 * @author Nicolas
 *
 */
public class StoreMenu extends BagMenu {

    private Player seller;
    private Player buyer;

    public void init(Player seller, Player buyer) {
        super.init(seller.getBag());

        this.seller = seller;
        this.buyer = buyer;
    }

    @Override
    public void clean() {
        super.clean();

        this.seller = null;
        this.buyer = null;
    }

    public String getPointedElementDescr() {
        assert isInitialized : "Menu not initialized";

        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            return "";
        }
        else {
            return pointedItem.getDescription();
        }
    }

    public String getPointedElementPrice() {
        assert isInitialized : "Menu not initialized";

        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            return "";
        }
        else {
            Price price = seller.getPrice(pointedItem);
            return UserInterface.getLang().getString("price") + " :\nx" + price.getAmount() + " " + price.getItem().getName() + " (" + buyer.getBag().getAmountOf(price.getItem()) + " " + UserInterface.getLang().getString("owned") + ")";
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
            Price price = seller.getPrice(pointedItem);
            if (buyer.getBag().getAmountOf(price.getItem()) >= price.getAmount()) {
                setChanged();
                notifyObservers(new ShowTransactionMenu(pointedItem, seller, buyer));
            }
        }
    }

    @Override
    public void close() {
        super.close();
        setChanged();
        notifyObservers(new ContinueSpeech());
    }

}
