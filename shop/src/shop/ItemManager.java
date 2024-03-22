package shop;

import java.util.ArrayList;

public class ItemManager {
	private static ItemManager instance = new ItemManager();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private ItemManager() { }
	
	public static ItemManager getInstance() {
		return instance;
	}

}
