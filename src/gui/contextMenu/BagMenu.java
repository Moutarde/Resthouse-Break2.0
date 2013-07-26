package gui.contextMenu;

import gui.UserInterface;

import java.util.HashMap;

import model.GameModel;
import model.chests.Item;
import model.player.Bag;

/**
 * @author Nicolas Kniebihler
 *
 */
public class BagMenu extends Menu {
    private Bag bag;

    public BagMenu(String name, GameModel model) {
        super(name, model.getPlayer().getBag().getContent().size() + 1, model);
        this.bag = model.getPlayer().getBag();
    }

    @Override
    public void selectElement() {
        if (getPointedElementId() == getNbElements() - 1) {
            setChanged();
            notifyObservers(MenuAction.RETURN);
        }
    }

    @Override
    public String getElementString(int index) {
        if (index == getNbElements() - 1) {
            return UserInterface.getLang().getString("return");
        }

        int i = 0;
        HashMap<Item, Integer> bagContent = bag.getContent();
        for (Item item : bagContent.keySet()) {
            if (i == index) {
                return "" + bagContent.get(item) + "x " + item.getName();
            }
        }

        assert false : "No item for index " + index;
        return null;
    }

}
