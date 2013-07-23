package model.chests;

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
	
	public Item(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
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
		
		while ((line = reader.readLine()) != null) {
			// ID
			if (line.startsWith("O_")) {
				currentItemId = line;
			}
			// NAME
			else if (line.startsWith("name=")) {
				String[] tokens = line.split("=");
				currentItemName = tokens[1];
			}
			// END LINE -> construct item
			else if (line.equals("END"))
			{
				Item item = new Item(currentItemName);
				itemList.put(currentItemId, item);
				
				currentItemId = null;
				currentItemName = null;
			}
		}
		
		reader.close();
	}
}
