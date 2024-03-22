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
				payFromCart();
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