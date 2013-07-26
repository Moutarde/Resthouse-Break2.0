package model.chests;

import gui.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Item {
    private static String itemsDescriptorFilePath = "/item/items.txt";
    private static HashMap<String, Item> itemList;

    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return UserInterface.getLang().getString(description);
    }

    public static Item getItem(String id) {
        return itemList.get(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
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

        while ((line = reader.readLine()) != null) {
            // ID
            if (line.startsWith("O_")) {
                currentItemId = line;
                currentItemDescription = currentItemId.substring(2) + "_descr";
            }
            // NAME
            else if (line.startsWith("name=")) {
                String[] tokens = line.split("=");
                currentItemName = tokens[1];
            }
            // END LINE -> construct item
            else if (line.equals("END"))
            {
                Item item = new Item(currentItemName, currentItemDescription);
                itemList.put(currentItemId, item);

                currentItemId = null;
                currentItemName = null;
                currentItemDescription = null;
            }
        }

        reader.close();
    }
}
