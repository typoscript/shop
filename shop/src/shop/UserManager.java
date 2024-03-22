package shop;

import java.util.ArrayList;

public class UserManager {
	private static UserManager instance = new UserManager();
	private ArrayList<User> users = new ArrayList<User>();
	
	private UserManager() { }
	
	public static UserManager getInstance() {
		return instance;
	}
}