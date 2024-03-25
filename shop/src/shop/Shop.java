package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private final int USER = 1;
	private final int FILE = 2;
	private final int ADMIN = 3;
	
	private final int USER_ADD = 1;
	private final int USER_DELETE = 2;
	private final int LOG_IN = 3;
	private final int LOG_OUT = 4;
	private final int SHOP = 5;
	private final int MY_PAGE = 6;
	
	private final int MY_CART_VIEW = 1;
	private final int MY_CART_DELETE_ITEM = 2;
	private final int MY_CART_CHANGE_QUANTITY = 3;
	private final int MY_CART_PAYMENT = 4;

	private final int FILE_SAVE = 1;
	private final int FILE_LOAD = 2;
	
	private final int ADMIN_ITEM = 1;
	private final int ADMIN_VIEW_REVENUE = 2;

	private final int ADMIN_ITEM_ADD = 1;
	private final int ADMIN_ITEM_DELETE = 2;
	private final int ADMIN_ITEM_MODIFY = 3;

	private final int ADMIN_UID = 1;

	private final String NAME;

	private int revenue;
	private int loginUserUID;

	private UserManager userManager; 
	private ItemManager itemManager;
		
	private Scanner sc = new Scanner(System.in);

	public Shop(String name) {
		this.NAME = name;
	}
	
	private void reset() {
		userManager = UserManager.getInstance();
		itemManager = ItemManager.getInstance();
		addAdminUser();

		revenue = 0;
		loginUserUID = -1;
	}
	
	private void addAdminUser() {
		final String ID = "a";
		final String PW = "a";

		userManager.addUser(new User(ID, PW), ADMIN_UID);
	}

	public void run() {
		reset();

		while (true) {
			printMainMenu();
			
			int menu = getInputNumber("메뉴");
			runMainMenu(menu);
		}
	}
	
	private void runMainMenu(int menu) {
		switch (menu) {
			case USER:
				printUserMenu();
				runUserMenu();
				break;
			case FILE:
				printFileMenu();
				runFileMenu();
				break;
			case ADMIN:
				if (loginUserUID != ADMIN_UID) {
					System.out.println("관리자가 아닙니다");
					break;
				}

				printAdminMenu();
				runAdminMenu();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}
	
	private void runUserMenu() {
		int menu = getInputNumber("메뉴");

		if (!canAccessMenu(menu))
			return;

		switch (menu) {
			case USER_ADD:
				addUser();
				break;
			case USER_DELETE:
				deleteUser();
				break;
			case LOG_IN:
				login();
				break;
			case LOG_OUT:
				logout();
				break;
			case SHOP:
				shop();
				break;
			case MY_PAGE:
				printMyPageMenu();
				runMyPageMenu();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}

	private void runMyPageMenu() {
		int menu = getInputNumber("메뉴");
		
		switch (menu) {
			case MY_CART_VIEW:
				viewUserCart();
				break;
			case MY_CART_DELETE_ITEM:
				deleteItemFromUserCart();
				break;
			case MY_CART_CHANGE_QUANTITY:
				changeItemQuantityFromUserCart();
				break;
			case MY_CART_PAYMENT:
				payFromUserCart();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}

	private void runFileMenu() {
		int menu = getInputNumber("메뉴");

		switch (menu) {
			case FILE_SAVE:
				FileManager.save(convertDataToString());
				break;
			case FILE_LOAD:
				loadDataFromFile();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}

	private void runAdminMenu() {
		int menu = getInputNumber("메뉴");

		switch (menu) {
			case ADMIN_ITEM:
				printAdminItemMenu();
				runAdminItemMenu();
				break;
			case ADMIN_VIEW_REVENUE:
				printRevenue();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}

	private void runAdminItemMenu() {
		int menu = getInputNumber("메뉴");

		switch (menu) {
			case ADMIN_ITEM_ADD:
				runAddItem();
				break;
			case ADMIN_ITEM_DELETE:
				runDeleteItem();
				break;
			case ADMIN_ITEM_MODIFY:
				runModifyItem();
				break;
			default:
				System.out.println("잘못된 메뉴입니다");
				break;
		}
	}

	private void addUser() {
		String id = getInputString("아이디");
		String password = getInputString("비밀번호");
		
		if (userManager.doesUserIdExist(id)) {
			System.out.println("이미 존재하는 아이디입니다");
			return;
		}
		
		userManager.addUser(new User(id, password));
		System.out.println("회원가입 성공");
	}

	private void deleteUser() {
		String id = getInputString("아이디");
		String password = getInputString("비밀번호");
		
		User user = userManager.getUserByIdAndPassword(id, password);
		
		if (user == null) {
			System.out.println("정보가 일치하지 않습니다");
			return;
		}

		userManager.deleteUser(user);
		loginUserUID = -1;
		System.out.println("회원탈퇴 성공");
	}
		
	private void login() {
		String id = getInputString("아이디");
		String password = getInputString("비밀번호");
		
		User user = userManager.getUserByIdAndPassword(id, password);
		
		if (user == null) {
			System.out.println("정보가 일치하지 않습니다");
			return;
		}

		loginUserUID = user.getUID();
		System.out.println("로그인 성공");
	}

	private void logout() {
		loginUserUID = -1;
		System.out.println("로그아웃 성공");
	}

	private void shop() {
		itemManager.printItemList();

		int index = getInputNumber("상품의 숫자") - 1;
		
		if (index < 0 || index >= itemManager.getItemSize()) {
			System.out.println("잘못된 상품 번호입니다");
			return;
		}
		
		Item item = itemManager.getItemByIndex(index);
		User user = userManager.getUserByUID(loginUserUID);
		
		if (user.hasItem(item)) {
			System.out.println("이미 장바구니에 존재하는 상품입니다");
			return;
		}
		
		item.setQuantity(1);
		user.addItem(item);
		userManager.updateUser(user);
		System.out.println("상품 담기 성공");
	}

	private void viewUserCart() {
		User user = userManager.getUserByUID(loginUserUID);
		
		if (user.isCartEmpty()) {
			System.out.println("빈 장바구니입니다");
			return;
		}

		System.out.println(user.getCart());
	}
	
	private void deleteItemFromUserCart() {
		User user = userManager.getUserByUID(loginUserUID);

		if (user.isCartEmpty()) {
			System.out.println("빈 장바구니입니다");
			return;
		}

		Cart cart = user.getCart();
		ArrayList<Item> items = cart.getItems();
		
		for (int i = 0; i < items.size(); i++)
			System.out.println((i + 1) + ") " + items.get(i));
		
		int index = getInputNumber("상품의 숫자: ") - 1;
		
		if (index < 0 || index >= items.size()) {
			System.out.println("잘못된 상품 번호입니다");
			return;
		}
		
		Item userItem = items.get(index);
		Item shopItem = itemManager.getItem(userItem);
		
		user.deleteItem(userItem);
		userManager.updateUser(user);
		itemManager.increaseItemQuantity(shopItem, userItem.getQuantity());
		
		System.out.println("상품 삭제 성공");
	}
	
	private void changeItemQuantityFromUserCart() {
		User user = userManager.getUserByUID(loginUserUID);
		Cart cart = user.getCart();
		ArrayList<Item> items = cart.getItems();

		for (int i = 0; i < items.size(); i++)
			System.out.println((i + 1) + ") " + items.get(i));
		
		int index = getInputNumber("상품의 숫자: ") - 1;
		
		if (index < 0 || index >= items.size()) {
			System.out.println("잘못된 상품 번호입니다");
			return;
		}
		
		int quantity = getInputNumber("수량");
		Item userItem = items.get(index);
		Item shopItem = itemManager.getItem(userItem);
		
		if (quantity < 1 || quantity > shopItem.getQuantity()) {
			System.out.println("잘못된 수량입니다");
			return;
		}
		
		user.setItemQuantity(userItem, quantity);
		userManager.updateUser(user);

		System.out.println("수량 변경 성공");
	}

	private void payFromUserCart() {
		User user = userManager.getUserByUID(loginUserUID);
		ArrayList<Item> items = user.getCart().getItems();
		
		System.out.println("=== 영수증 ===");

		for (int i = 0; i < items.size(); i++) {
			Item userItem = items.get(i);
			Item item = itemManager.getItem(userItem);
			
			if (item == null)
				continue;
			
			if (item.getQuantity() < userItem.getQuantity())
				continue;
			
			int totalPrice = item.getPrice() * userItem.getQuantity();
			revenue += totalPrice;
			
			String info = String.format(
						"상품: %s | 가격: %d원 | 수량: %d개 = %d원",
						item.getName(), item.getPrice(), userItem.getQuantity(), totalPrice
					);

			System.out.println(info);
			
			user.deleteItem(userItem);
			userManager.updateUser(user);

			itemManager.decreaseItemQuantity(item, userItem.getQuantity());
		}

		System.out.println("===========");
	}
	
	private void loadItemData(String data) {
		itemManager.clearItems();
		
		String[] items = data.split("/");
		
		for (String item : items) {
			String[] info = item.split(",");

			int UID = Integer.parseInt(info[0]);
			String name = info[1];
			int price = Integer.parseInt(info[2]);
			int quantity = Integer.parseInt(info[3]);

			itemManager.addItem(new Item(name, price, quantity), UID);
		}
	}
	
	private void loadUserData(String[] users) {
		userManager.clearUsers();

		for (int i = 1; i < users.length; i++) {
			String[] userData = users[i].split("/");
			String[] userInfo = userData[0].split(",");
		
			int UID = Integer.parseInt(userInfo[0]);
			String id = userInfo[1];
			String password = userInfo[2];
			
			User user = new User(UID, id, password);
			
			for (int j = 1; j < userData.length; j++) {
				String[] itemInfo = userData[j].split(",");

				int itemUID = Integer.parseInt(itemInfo[0]);
				int quantity = Integer.parseInt(itemInfo[1]);
				
				Item item = itemManager.getItemByUID(itemUID);

				Item userItem = new Item(itemUID, item.getName(), item.getPrice(), quantity);
				user.addItem(userItem);
			}

			if (UID == ADMIN_UID)
				userManager.deleteUser(user);

			userManager.addUser(user, UID);
		}
	}
	
	private void loadDataFromFile() {
		String data = FileManager.load();
		String[] dataArr = data.split("\n");

		loadItemData(dataArr[0]);
		loadUserData(dataArr);
		
		System.out.println("파일 로드 성공");
	}
	
	private void runAddItem() {
		String name = getInputString("상품 이름");
		int price = getInputNumber("가격");
		int quantity = getInputNumber("수량");
		
		if (price < 1 || quantity < 1) {
			System.out.println("잘못된 가격 혹은 수량입니다");
			return;
		}
		
		itemManager.addItem(new Item(name, price, quantity));
		System.out.println("상품 추가 성공");
	}

	private void runDeleteItem() {
		if (itemManager.getItemSize() == 0) {
			System.out.println("상품이 없습니다");
			return;
		}

		itemManager.printItemList();
		
		int index = getInputNumber("상품의 숫자") - 1;
		
		if (index < 0 || index >= itemManager.getItemSize()) {
			System.out.println("잘못된 번호입니다");
			return;
		}
		
		Item item = itemManager.getItemByIndex(index);
		
		itemManager.deleteItem(item);
		
		for (User user : userManager.getUsers()) {
			user.deleteItem(item);
			userManager.updateUser(user);
		}
		
		System.out.println("상품 삭제 성공");
	}

	private void runModifyItem() {
		if (itemManager.getItemSize() == 0) {
			System.out.println("상품이 없습니다");
			return;
		}

		itemManager.printItemList();
		
		int index = getInputNumber("상품의 숫자") - 1;
		
		if (index < 0 || index >= itemManager.getItemSize()) {
			System.out.println("잘못된 번호입니다");
			return;
		}
		
		Item item = itemManager.getItemByIndex(index);
		
		System.out.println(item);
		
		String name = getInputString("이름");
		int price = getInputNumber("가격");
		int quantity = getInputNumber("수량");
		
		if (price < 1 || quantity < 1) {
			System.out.println("잘못된 입력값입니다");
			return;
		}

		Item modifiedItem = new Item(item.getUID(), name, price, quantity);
		
		itemManager.setItem(index, modifiedItem);
		
		System.out.println("상품 수정 완료");
	}
	
	private String convertDataToString() {
		String data = "";
		
		ArrayList<Item> items = itemManager.getItems();
		
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);

			data += item.getUID() + "," + item.getName() + "," + item.getPrice() + "," + item.getQuantity();
			
			if (i < items.size() - 1)
				data += "/";
		}
		
		data += "\n";
		
		ArrayList<User> users = userManager.getUsers();
		
		for (User user : users) {
			data += user.getUID() + "," + user.getId() + "," + user.getPassword();

			ArrayList<Item> userItems = user.getCart().getItems();
			
			if (userItems.size() > 0) {
				data += "/";
			
				for (int i = 0; i < userItems.size(); i++) {
					Item userItem = userItems.get(i);
					
					data += userItem.getUID() + "," + userItem.getQuantity();
					
					if (i < userItems.size() - 1)
						data += "/";
				}
			}
			
			data += "\n";
		}
		
		return data;
	}
	
	private boolean canAccessMenu(int menu) {
		if (isUserLoggedIn()) {
			if (doesMenuRequireLogout(menu)) {
				System.out.println("로그아웃 후 이용 가능합니다");
				return false;
			}

			return true;
		} else {
			if (doesMenuRequireLogin(menu)) {
				System.out.println("로그인 후 이용 가능합니다");
				return false;
			}

			return true;
		}
	}

	private boolean doesMenuRequireLogin(int menu) {
		switch (menu) {
			case USER_DELETE:
			case LOG_OUT:
			case SHOP:
			case MY_PAGE:
				return true;
			
			default:
				return false;
		}
	}

	private boolean doesMenuRequireLogout(int menu) {
		switch (menu) {
			case USER_ADD:
			case LOG_IN:
				return true;
			
			default:
				return false;
		}
	}
	
	private boolean isUserLoggedIn() {
		return loginUserUID != -1;
	}

	private String getInputString(String message) {
		System.out.print(message + ": ");
		return sc.next();
	}
	
	private int getInputNumber(String message) {
		int number = -1;

		try {
			String input = getInputString(message);
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.out.println("숫자만 입력 가능합니다");
		}
		
		return number;
	}

	private void printMainMenu() {
		System.out.printf("== %s ==\n", NAME);
		System.out.println("1. 유저");
		System.out.println("2. 파일");
		System.out.println("3. 관리자");
	}	
	
	private void printUserMenu() {
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 로그인");
		System.out.println("4. 로그아웃");
		System.out.println("5. 쇼핑");
		System.out.println("6. 마이페이지");
	}
	
	private void printMyPageMenu() {
		System.out.println("1. 나의 장바구니");
		System.out.println("2. 항목 삭제");
		System.out.println("3. 수량 수정");
		System.out.println("4. 결제");
	}	
	
	private void printFileMenu() {
		System.out.println("1. 자동저장");
		System.out.println("2. 자동로드");
	}	

	private void printAdminMenu() {
		System.out.println("1. 아이템");
		System.out.println("2. 조회");
	}

	private void printAdminItemMenu() {
		System.out.println("1. 아이템 등록");
		System.out.println("2. 아이템 삭제");
		System.out.println("3. 아이템 수정");
	}
	
	private void printRevenue() {
		System.out.printf("매출액: %d원\n", revenue);
	}
}