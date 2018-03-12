package com.fgmariano.dao;

import java.util.List;

import com.fgmariano.entities.Book;

public interface BookDAO {
	
	public Book find(int id);
	
	public List<Book> findAll();
	
	public boolean createBook(Book book);
	
	public boolean updateBook(Book book);
	
	public boolean deleteBook(Book book);
	
}
