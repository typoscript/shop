package shop;

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

	private final String NAME = "";

	private int revenue;
	private int loginUserUID;

	private UserManager userManager; 
	private ItemManager itemManager;
		
	private Scanner sc = new Scanner(System.in);
}