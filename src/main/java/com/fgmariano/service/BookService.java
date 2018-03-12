package com.fgmariano.service;

import java.util.List;

import com.fgmariano.entities.Book;

public interface BookService {
	
	public Book find(int id);
	
	public List<Book> findAll();
	
	public boolean createBook(Book book);
	
	public boolean updateBook(Book book);
	
	public boolean deleteBook(Book book);
	
}
