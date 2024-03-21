package shop;

public class Item {
	private String name;
	private int price;
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}
	
	public Item clone() {
		return new Item(this.name, this.price);
	}
}
