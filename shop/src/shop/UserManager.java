package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> users = new ArrayList<User>();

	private int getIndexOfUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUID() == user.getUID())
				return i;
		}
		
		return -1;
	}
	
	private User getUserById(String id) {
		for (User user : users) {
			if (user.getId().equals(id))
				return user;
		}
		
		return null;
	}

	public User getUserByUID(int UID) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUID() == UID)
				return users.get(i).clone();
		}

		return null;
	}
	
	public User getUserByIdAndPassword(String id, String password) {
		User user = getUserById(id);
		
		if (user == null || !user.getPassword().equals(password))
			return null;

		return user.clone();
	}

	public ArrayList<User> getUsers() {
		return (ArrayList<User>) users.clone();
	}
	
	public void addUser(User user) {
		users.add(new User(generateUserUID(), user.getId(), user.getPassword(), user.getCart()));
	}

	public void deleteUser(User user) {
		User foundUser = getUserById(user.getId());
		users.remove(foundUser);
	}
	
	public void updateUser(User user) {
		users.set(getIndexOfUser(user), user);
	}

	public boolean doesUserIdExist(String id) {
		return getUserById(id) == null ? false : true;
	}

	private int generateUserUID() {
		int id = (int)(Math.random() * 1000000);

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUID() == id) {
				id = (int)(Math.random() * 1000);
				i = -1;
			}
		}
		
		return id;
	}
}