package gui.contextMenu;

import gui.UserInterface;
import model.GameModel;
import model.items.Item;
import model.items.Price;
import model.player.Player;
import controller.actions.CloseMenu;
import controller.actions.ShowTransactionMenu;

/**
 * @author Nicolas
 *
 */
public class StoreMenu extends BagMenu {

    private Player seller;
    private Player buyer;

    public StoreMenu(GameModel model, Player seller, Player buyer) {
        super(model, seller.getBag());

        this.seller = seller;
        this.buyer = buyer;
    }

    public String getPointedElementDescr() {
        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            return "";
        }
        else {
            return pointedItem.getDescription();
        }
    }

    public String getPointedElementPrice() {
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
        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            setChanged();
            notifyObservers(new CloseMenu());
        }
        else {
            Price price = seller.getPrice(pointedItem);
            if (buyer.getBag().getAmountOf(price.getItem()) >= price.getAmount()) {
                setChanged();
                notifyObservers(new ShowTransactionMenu(pointedItem, seller, buyer));
            }
        }
    }

}
