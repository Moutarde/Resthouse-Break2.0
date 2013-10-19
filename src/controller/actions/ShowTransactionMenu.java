package controller.actions;

import gui.contextMenu.StoreMenu;
import gui.contextMenu.TransactionMenu;
import model.GameModel;
import model.items.Item;
import model.player.Player;
import controller.handlers.Handler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowTransactionMenu implements IAction {

    private Item item;
    private Player seller, buyer;

    public ShowTransactionMenu(Item item, Player seller, Player buyer) {
        this.item = item;
        this.seller = seller;
        this.buyer = buyer;
    }

    @Override
    public void execute(Object origin, Handler handler) {
        assert origin instanceof StoreMenu : "origin is not a StoreMenu";

        GameModel model = handler.getModel();
        ((TransactionMenu)model.getMenu(GameModel.MenuID.transactionMenu)).init(item, seller, buyer);
        model.showMenu(GameModel.MenuID.transactionMenu);
    }

}
