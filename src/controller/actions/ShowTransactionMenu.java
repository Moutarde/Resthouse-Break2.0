package controller.actions;

import gui.contextMenu.Menu;
import gui.contextMenu.StoreMenu;
import model.GameModel;
import model.items.Item;
import model.player.Player;
import controller.ConversationHandler;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowTransactionMenu implements IMenuAction {

    private Item item;
    private Player seller, buyer;

    public ShowTransactionMenu(Item item, Player seller, Player buyer) {
        this.item = item;
        this.seller = seller;
        this.buyer = buyer;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        GameModel model = menu.getModel();
        assert !model.isTransactionMenuDisplayed() : "Trying to show item inspection box while it is already displayed";
        if (model.isStoreMenuDisplayed() && menu instanceof StoreMenu && handler instanceof ConversationHandler) {
            ((ConversationHandler)handler).showTransactionMenu(item, seller, buyer);
        }
        else {
            assert false : "Trying to show a choice box that doesn't exist";
        }
    }

}