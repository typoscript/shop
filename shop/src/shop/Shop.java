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