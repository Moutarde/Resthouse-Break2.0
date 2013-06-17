package model.chests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Item {
	private static String itemsDescriptorFilePath = "src/resources/item/items.txt";
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
	
	public static void createItems() throws IOException {
		itemList = new HashMap<String, Item>();
		
		BufferedReader reader = new BufferedReader(new FileReader(itemsDescriptorFilePath));
		
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
