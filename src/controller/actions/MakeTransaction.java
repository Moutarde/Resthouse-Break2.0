package controller.actions;

import gui.contextMenu.Menu;
import gui.contextMenu.TransactionMenu;
import model.items.Item;
import model.items.Price;
import model.player.Player;
import controller.ConversationHandler;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MakeTransaction implements IMenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert menu instanceof TransactionMenu : "menu is not a TransactionMenu";
        assert handler instanceof ConversationHandler : "handler is not a ConversationHandler";

        Item item = ((TransactionMenu)menu).getItem();
        Price price = ((TransactionMenu)menu).getPrice();
        Player seller = ((TransactionMenu)menu).getSeller();
        Player buyer = ((TransactionMenu)menu).getBuyer();
        int amount = ((TransactionMenu)menu).getSelectedNb();

        seller.getBag().removeItemIFP(item, amount);
        if (seller.getPriceMap().containsKey(price.getItem())) {
            seller.getBag().addItem(price.getItem(), amount * price.getAmount());
        }

        buyer.getBag().addItem(item, amount);
        buyer.getBag().removeItemIFP(price.getItem(), amount * price.getAmount());

        menu.display(false);
        ((ConversationHandler)handler).updateStore();
    }

}
