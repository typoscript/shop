package shop;

import java.util.ArrayList;

public class Cart {
	ArrayList<Item> items;

	public Cart() {
		this.items = new ArrayList<Item>();
	}
	
	public Cart(ArrayList<Item> items) {
		this.items = items;
	}

	private Item getItemByUID(int UID) {
		int index = getIndexOfItemByUID(UID);
		return index >= 0 ? items.get(index) : null;
	}

	public Item getItem(int index) {
		return items.get(index).clone();
	}

	public ArrayList<Item> getItems() {
		return (ArrayList<Item>) items.clone();
	}

	public int getIndexOfItemByUID(int UID) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getUID() == UID)
				return i;
		}

		return -1;
	}

	public void setItem(int index, Item item) {
		items.set(index, item);
	}
	
	public int size() {
		return items.size();
	}
	
	public void add(Item item) {
		items.add(item);
	}

	public void delete(Item item) {
		Item foundItem = getItemByUID(item.getUID());
		items.remove(foundItem);
	}

	public boolean hasItem(Item item) {
		return getItemByUID(item.getUID()) != null;
	}
	
	@Override
	public Cart clone() {
		return new Cart(items);
	}
	
	@Override
	public String toString() {
		String data = "";
		
		for (Item item : items)
			data += item + "\n";
			
		return data;
	}
}