package gui.contextMenu;

import model.GameModel;
import model.items.Item;
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

    @Override
    public void selectElement() {
        Item pointedItem = getPointedItem();
        if (pointedItem == null) {
            setChanged();
            notifyObservers(new CloseMenu());
        }
        else {
            setChanged();
            notifyObservers(new ShowTransactionMenu(pointedItem, seller, buyer));
        }
    }

}
