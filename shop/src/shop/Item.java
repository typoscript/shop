package shop;

public class Item {
	private final int UID;
	private int quantity;
	private int price;
	private String name;

	public Item(String name, int price, int quantity) {
		this.UID = -1;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Item(int uid, String name, int price, int quantity) {
		this.UID = uid;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public int getUID() {
		return UID;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return price;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}

	public void decreaseQuantity(int quantity) {
		this.quantity -= quantity;
	}
	

	@Override
	public Item clone() {
		return new Item(UID, name, price, quantity);
	}

	@Override
	public String toString() {
		return String.format("상품: %s | 가격: %d원 | 수량: %d개", name, price, quantity);
	}
	
}
