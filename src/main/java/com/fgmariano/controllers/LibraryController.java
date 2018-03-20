package com.fgmariano.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fgmariano.entities.Book;
import com.fgmariano.entities.User;
import com.fgmariano.service.BookService;
import com.fgmariano.service.UserService;

@Controller
@RequestMapping("/")
public class LibraryController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpSession session) {
		if(session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			modelMap.put("book", new Book());
			modelMap.put("books", bookService.findAll());
			modelMap.put("user_books", user.getBooks());
			modelMap.put("function", "new");
			return "library/index";
		} else {
			modelMap.put("error", "User not logged in");
			return "redirect:/user";
		}
	}
	
	@RequestMapping(value = "book/new", method = RequestMethod.POST)
	public String newBook(@ModelAttribute("book") Book book, ModelMap modelMap, HttpSession session) {
		if(session.getAttribute("user") != null) {
			if(bookService.createBook(book)) {
				return "redirect:/";
			} else {
				modelMap.put("error", "Book invalid");
				return "redirect:/";
			}
		} else {
			modelMap.put("error", "You're not logged in");
			return "redirect:user/index";
		}
	}
	
	@RequestMapping(value = "book/edit/{id}", method = RequestMethod.GET)
	public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book, ModelMap modelMap, HttpSession session) {
		if(session.getAttribute("user") != null) {
			modelMap.put("book", bookService.find(id));
			modelMap.put("function", "edit");
//			return "redirect:/";
			return "library/index";
		} else {
			modelMap.put("error", "You're not logged in");
			return "redirect:user/index";
		}
	}
	
	@RequestMapping(value = "book/edit", method = RequestMethod.POST)
	public String editBook(@ModelAttribute("book") Book book) {
		System.out.println(book.toString());
		bookService.updateBook(book);
		return "redirect:/";
	}
	
	@RequestMapping(value = "book/del/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") int id) {
		bookService.deleteBook(bookService.find(id));
		return "redirect:/";
	}
	
	@RequestMapping(value = "book/add/{id}", method = RequestMethod.GET)
	public String addBook(@PathVariable("id") int id, @ModelAttribute("book") Book book, ModelMap modelMap, HttpSession session) {
		User user = (User) session.getAttribute("user");
		user.getBooks().add(bookService.find(book.getId()));
		userService.updateUser(user);
		session.setAttribute("user", user);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/user";
	}
}
