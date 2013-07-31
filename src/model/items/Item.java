package model.items;

import gui.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.GameModel;
import model.rooms.RoomDoorPair;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Item {
    private static String itemsDescriptorFilePath = "/item/items.txt";
    private static HashMap<String, Item> itemList;
    private static enum ItemType { NORMAL, KEY };

    private String name;
    private String description;
    private String useFeedback;
    private String useFailFeedback;
    private boolean isUsable = false;

    public Item(String name, String description, String useFeedback, String useFailFeedback, boolean isUsable) {
        this.name = name;
        this.description = description;
        this.useFeedback = useFeedback;
        this.useFailFeedback = useFailFeedback;
        this.isUsable = isUsable;
    }

    public String getName() {
        return UserInterface.getLang().getString(name);
    }

    public String getDescription() {
        return UserInterface.getLang().getString(description);
    }

    public String getUseFeedback() {
        return UserInterface.getLang().getString(useFeedback);
    }

    public String getUseFailFeedback() {
        return UserInterface.getLang().getString(useFailFeedback);
    }

    public boolean isUsable(GameModel model) {
        return isUsable;
    }

    public boolean use(GameModel model) {
        assert false : "Simple items are not usable !";
        return false;
    }

    public static Item getItem(String id) {
        return itemList.get(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Item) {
            Item item = (Item)obj;

            if (this.name != item.name) {
                if (this.name == null || !this.name.equals(item.name)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public static void createItems() throws IOException {
        itemList = new HashMap<String, Item>();

        URL url = Item.class.getResource(itemsDescriptorFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        assert reader != null;

        String line = null;
        String currentItemId = null;
        String currentItemName = null;
        String currentItemDescription = null;
        String currentItemUseFeedback = null;
        String currentItemUseFailFeedback = null;
        ItemType currentItemType = null;

        // KEY
        List<RoomDoorPair> currentKeyDoorList = new ArrayList<RoomDoorPair>();

        while ((line = reader.readLine()) != null) {
            // ID
            if (line.startsWith("O_")) {
                currentItemId = line;

                String id = currentItemId.substring(2);
                currentItemName = id + "_name";
                currentItemDescription = id + "_descr";
                currentItemUseFeedback = id + "_use";
                currentItemUseFailFeedback = id + "_useFail";
            }
            // TYPE
            else if (line.startsWith("type=")) {
                String[] tokens = line.split("=");
                String type = tokens[1];

                if (type.equals("normal")) {
                    currentItemType = ItemType.NORMAL;
                }
                else if (type.equals("key")) {
                    currentItemType = ItemType.KEY;
                }
                else {
                    assert false : "Item type unknown : " + type;
                }
            }
            // DOOR
            else if (line.startsWith("DOOR:")) {
                assert currentItemType == ItemType.KEY : "Trying to define doors field for an item that is not a key : " + currentItemType;

                String[] tokens = line.split(":");
                String[] infos = tokens[1].split(";");

                int doorId = Integer.parseInt(infos[0]);

                String roomId = infos[1];
                assert roomId.startsWith("R_");
                RoomDoorPair door = new RoomDoorPair(roomId, doorId);

                currentKeyDoorList.add(door);
            }
            // END LINE -> construct item
            else if (line.equals("END"))
            {
                Item item = null;
                switch (currentItemType) {
                case NORMAL:
                    item = new Item(currentItemName, currentItemDescription, currentItemUseFeedback, currentItemUseFailFeedback, false);
                    break;
                case KEY:
                    item = new Key(currentItemName, currentItemDescription, currentItemUseFeedback, currentItemUseFailFeedback, currentKeyDoorList);
                    break;
                }

                itemList.put(currentItemId, item);

                currentItemId = null;
                currentItemName = null;
                currentItemDescription = null;
                currentItemType = null;
                currentItemUseFeedback = null;
                currentItemUseFailFeedback = null;

                currentKeyDoorList.clear();
            }
        }

        reader.close();
    }
}
