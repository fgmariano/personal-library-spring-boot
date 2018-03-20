package com.fgmariano.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fgmariano.entities.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		return(hashed_password);
	}
	
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		return BCrypt.checkpw(password_plaintext, stored_hash);
	}
	
	@Override
	public User find(int id) {
		User user = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user = (User) session.createQuery("from User where id = :id")
					.setInteger("id", id)
					.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		User user = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user = (User) session.createQuery("from User where username = :username")
					.setString("username", username)
					.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		System.out.println("Pwd from db: " + user.getPassword());
		System.out.println("Pwd from login hash: " + hashPassword(password));
		if(user != null && checkPassword(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	public boolean createUser(User user) {
		boolean saved = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setPassword(hashPassword(user.getPassword()));
			session.save(user);
			transaction.commit();
			saved = true;
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return saved;
	}

	@Override
	public boolean updateUser(User user) {
		boolean saved = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setPassword(hashPassword(user.getPassword()));
			session.update(user);
			transaction.commit();
			saved = true;
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return saved;
	}

	@Override
	public boolean deleteUser(User user) {
		boolean saved = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setPassword(hashPassword(user.getPassword()));
			session.delete(user);
			transaction.commit();
			saved = true;
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return saved;
	}

}
