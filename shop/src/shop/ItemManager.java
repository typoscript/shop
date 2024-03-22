package shop;

import java.util.ArrayList;

public class ItemManager {
	private static ItemManager instance = new ItemManager();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private ItemManager() { }
	
	public static ItemManager getInstance() {
		return instance;
	}

	private Item getItemByClonedItem(Item item) {
		for (Item currentItem : items) {
			if (currentItem.getUID() == item.getUID()) {
				return currentItem;
			}
		}
		
		return null;
	}

	public Item getItemByUID(int UID) {
		for (Item item : items) {
			if (item.getUID() == UID)
				return item.clone();
		}

		return null;
	}

	public Item getItem(Item item) {
		Item foundItem = getItemByUID(item.getUID());

		return foundItem == null ? null : foundItem;
	}

	public ArrayList<Item> getItems() {
		return (ArrayList<Item>) items.clone();
	}
	
	public Item getItemByIndex(int index) {
		return items.get(index).clone();
	}

	public int getItemSize() {
		return items.size();
	}
	
	public void setItem(int index, Item item) {
		items.set(index, item);
	}
	
	private int generateItemUID() {
		int id = (int)(Math.random() * 1000000);

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getUID() == id) {
				id = (int)(Math.random() * 1000);
				i = -1;
			}
		}
		
		return id;
	}

	public void addItem(Item item, int UID) {
		items.add(new Item(
			UID,
			item.getName(),
			item.getPrice(),
			item.getQuantity()
		));
	}
	
	public void addItem(Item item) {
		items.add(new Item(
			generateItemUID(),
			item.getName(),
			item.getPrice(),
			item.getQuantity()
		));
	}

	public void deleteItem(Item item) {
		Item foundItem = getItemByClonedItem(item);
		items.remove(foundItem);
	}
	
	public void increaseItemQuantity(Item item, int quantity) {
		Item foundItem = getItemByClonedItem(item);
		foundItem.increaseQuantity(quantity);
	}

	public void decreaseItemQuantity(Item item, int quantity) {
		Item foundItem = getItemByClonedItem(item);
		foundItem.decreaseQuantity(quantity);
	}
	
	public void printItemList() {
		for (int i = 0; i < items.size(); i++)
			System.out.println((i + 1) + ") " + items.get(i));
	}
}
