package model.player;

import java.util.HashMap;

import model.chests.Item;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Bag {
	private HashMap<Item, Integer> items = new HashMap<Item, Integer>();
	
	public Bag() {
		
	}
	
	public HashMap<Item, Integer> getContent() {
		return items;
	}
	
	public void addItem(Item i) {
		if (items.containsKey(i)) {
			int currentAmount = items.get(i);
			items.put(i, currentAmount++);
		}
		else {
			items.put(i, 1);
		}
	}
	
	public void removeItemIFP(Item i) {
		if (!items.containsKey(i)) {
			return;
		}
		
		int currentAmount = items.get(i);
		if (currentAmount == 1) {
			items.remove(i);
		}
		else {
			items.put(i, currentAmount--);
		}
	}
}
