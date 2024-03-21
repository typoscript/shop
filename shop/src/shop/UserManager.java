package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> users = new ArrayList<User>();
	
	public User getUserByIndex(int index) {
		return this.users.get(index);
	}
	
	public User getUserByIdAndPassword(String id, String password) {
		User user = getUserById(id);
		
		if (user == null || !user.getPassword().equals(password))
			return null;

		return user;
	}
	
	private User getUserById(String id) {
		for (User user : users) {
			if (user.getId() == id)
				return user;
		}
		
		return null;
	}
	
	private int getIndexOfUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user))
				return i;
		}

		return -1;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void deleteUser(User user) {
		int index = getIndexOfUser(user);
		
		if (index >= 0)
			users.remove(index);
	}

	public int genereateUserUID() {
		int id = (int)(Math.random() * 1000000);

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUID() == id) {
				id = (int)(Math.random() * 1000);
				i = -1;
			}
		}
		
		return id;
	}
	
	public boolean doesUserIdExist(String id) {
		return getUserById(id) == null ? false : true;
	}
}