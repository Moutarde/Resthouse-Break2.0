package controller.actions;

import model.items.Item;
import model.items.Price;
import model.player.Player;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MakeTransaction implements IAction {

    private Item item;
    private Price price;
    private Player seller, buyer;
    private int amount;

    public MakeTransaction(Item item, Price price, Player seller, Player buyer, int amount) {
        this.item = item;
        this.price = price;
        this.seller = seller;
        this.buyer = buyer;
        this.amount = amount;
    }

    @Override
    public void execute(Object origin, Handler handler) {
        seller.getBag().removeItemIFP(item, amount);
        if (seller.getPriceMap().containsKey(price.getItem())) {
            seller.getBag().addItem(price.getItem(), amount * price.getAmount());
        }

        buyer.getBag().addItem(item, amount);
        buyer.getBag().removeItemIFP(price.getItem(), amount * price.getAmount());
    }

}
