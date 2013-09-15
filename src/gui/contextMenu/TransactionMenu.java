package gui.contextMenu;

import model.items.Item;
import model.player.Player;
import controller.actions.CloseMenu;

/**
 * @author Nicolas Kniebihler
 *
 */
public class TransactionMenu extends Menu {

    private Item item;
    private Player seller, buyer;
    private int maxNb;
    private int nbAlreadyOwned;

    private int selectedNb = 1;

    public TransactionMenu(Item item, Player seller, Player buyer) {
        super("", 1, null);

        this.item = item;
        this.seller = seller;
        this.buyer = buyer;

        maxNb = seller.getBag().getContent().get(item);
        nbAlreadyOwned = buyer.getBag().getContent().containsKey(item) ? buyer.getBag().getContent().get(item) : 0;
    }

    public int getNbAlreadyOwned() {
        return nbAlreadyOwned;
    }

    public int getSelectedNb() {
        return selectedNb;
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
        seller.getBag().removeItemIFP(item, selectedNb);
        buyer.getBag().addItem(item, selectedNb);
        setChanged();
        notifyObservers(new CloseMenu());
    }

    @Override
    public String getElementString(int index) {
        assert false : "This function can't be called";
        return null;
    }

}
