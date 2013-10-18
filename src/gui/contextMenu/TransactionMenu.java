package gui.contextMenu;

import java.util.ArrayList;
import java.util.List;

import model.items.Item;
import model.items.Price;
import model.player.Player;
import controller.actions.CloseMenu;
import controller.actions.IAction;
import controller.actions.MakeTransaction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class TransactionMenu extends Menu {

    private Item item;
    private Price price;
    private Player seller, buyer;
    private int maxNb;
    private int nbAlreadyOwned;

    private int selectedNb = 1;

    public TransactionMenu(Item item, Player seller, Player buyer) {
        super("", 1);

        this.item = item;
        this.seller = seller;
        this.buyer = buyer;

        price = seller.getPrice(item);

        maxNb = Math.min(seller.getBag().getAmountOf(item), buyer.getBag().getAmountOf(price.getItem()) / price.getAmount());
        assert maxNb > 0 : "Not enough money to buy this";

        nbAlreadyOwned = buyer.getBag().contains(item) ? buyer.getBag().getAmountOf(item) : 0;
    }

    public int getNbAlreadyOwned() {
        return nbAlreadyOwned;
    }

    public void incrementSelectedNb() {
        if (selectedNb < maxNb) ++selectedNb;
    }

    public void decrementSelectedNb() {
        if (selectedNb > 0) --selectedNb;
    }

    @Override
    public void changePointedElement(int i) {
        int nb = selectedNb + i;
        if (nb < 1) {
            nb = 1;
        }
        else if (nb > maxNb) {
            nb = maxNb;
        }

        selectedNb = nb;
    }

    @Override
    public String getContent() {
        String content = selectedNb + "x " + item.getName();

        return content;
    }

    @Override
    public void selectElement() {
        List<IAction> actions = new ArrayList<IAction>();
        actions.add(new MakeTransaction(item, price, seller, buyer, selectedNb));
        actions.add(new CloseMenu());

        setChanged();
        notifyObservers(actions);
    }

    @Override
    public String getElementString(int index) {
        assert false : "This function can't be called";
        return null;
    }

}
