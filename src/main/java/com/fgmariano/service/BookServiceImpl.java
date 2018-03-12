package com.fgmariano.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fgmariano.dao.BookDAO;
import com.fgmariano.entities.Book;

@Repository("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDAO bookDAO;
	
	@Override
	public Book find(int id) {
		return this.bookDAO.find(id);
	}

	@Override
	public List<Book> findAll() {
		return this.bookDAO.findAll();
	}

	@Override
	public boolean createBook(Book book) {
		return this.bookDAO.createBook(book);
	}

	@Override
	public boolean updateBook(Book book) {
		return this.bookDAO.updateBook(book);
	}

	@Override
	public boolean deleteBook(Book book) {
		return this.bookDAO.deleteBook(book);
	}

}
