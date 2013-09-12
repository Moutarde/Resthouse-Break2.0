package gui.contextMenu;

import model.GameModel;
import model.items.Item;
import model.player.Player;

/**
 * @author Nicolas
 *
 */
public class StoreMenu extends BagMenu {

    public StoreMenu(GameModel model, Player player) {
        super(model, player);
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

}
