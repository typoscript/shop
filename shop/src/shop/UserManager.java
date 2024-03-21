package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> users = new ArrayList<User>();
	
	public void setUser(int index, User user) {
		users.set(index, user);
	}
	
	public void addUser(User user) {
		users.add(user);
	}

	public void deleteUser(User user) {
		int index = getIndexOfUser(user);
		
		if (index >= 0)
			users.remove(index);
	}
	
	private int getIndexOfUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user))
				return i;
		}

		return -1;
	}
}