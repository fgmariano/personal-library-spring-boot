package com.fgmariano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fgmariano.dao.UserDAO;
import com.fgmariano.entities.User;

@Repository("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User find(int id) {
		return this.userDAO.find(id);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return this.userDAO.findByUsernameAndPassword(username, password);
	}

	@Override
	public boolean createUser(User user) {
		return this.userDAO.createUser(user);
	}

	@Override
	public boolean updateUser(User user) {
		return this.userDAO.updateUser(user);
	}

	@Override
	public boolean deleteUser(User user) {
		return this.userDAO.deleteUser(user);
	}

}
