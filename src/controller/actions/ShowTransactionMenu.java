package controller.actions;

import gui.contextMenu.StoreMenu;
import model.items.Item;
import model.player.Player;
import controller.ConversationHandler;

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
    public void execute(Object origin, Object handler) {
        assert origin instanceof StoreMenu : "origin is not a StoreMenu";
        assert handler instanceof ConversationHandler : "handler is not a ConversationHandler";

        ((ConversationHandler)handler).showTransactionMenu(item, seller, buyer);
    }

}
