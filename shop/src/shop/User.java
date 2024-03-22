package shop;

public class User {
	private final int UID;
	private String id;
	private String password;
	private Cart cart;
	
	public User(String id, String password) {
		this.UID = -1;
		this.id = id;
		this.password = password;
		this.cart = new Cart();
	}
	
	public User(int uid, String id, String password) {
		this.UID = uid;
		this.id = id;
		this.password = password;
		this.cart = new Cart();
	}

	public User(int uid, String id, String password, Cart cart) {
		this.UID = uid;
		this.id = id;
		this.password = password;
		this.cart = cart;
	}
	
	public int getUID() {
		return UID;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	public Cart getCart() {
		return cart.clone();
	}
	
	public boolean isCartEmpty() {
		return cart.size() == 0;
	}
	
	public void setItemQuantity(Item item, int quantity) {
		int index = cart.getIndexOfItemByUID(item.getUID());
		Item foundItem = cart.getItem(index);

		foundItem.setQuantity(quantity);
		
		cart.setItem(index, foundItem);
	}

	public void setItem(Item item) {
		int index = cart.getIndexOfItemByUID(item.getUID());
		cart.setItem(index, item);
	}
	
	public void addItem(Item item) {
		cart.add(item);
	}

	public void deleteItem(Item item) {
		cart.delete(item);
	}
	
	public boolean hasItem(Item item) {
		return cart.hasItem(item);
	}

	public User clone() {
		return new User(UID, id, password, cart);
	}
}
