package com.fgmariano.dao;

import com.fgmariano.entities.User;

public interface UserDAO {
	
	public User find(int id);
	
	public User findByUsernameAndPassword(String username, String password);
	
	public boolean createUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
	
}
