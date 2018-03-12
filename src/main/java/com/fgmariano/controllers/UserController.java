package com.fgmariano.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fgmariano.entities.User;
import com.fgmariano.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("user", new User());
		return "user/index";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession session) {
		User e = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(e != null) {
			session.setAttribute("user", e);
//			return "user/welcome";
			return "redirect:/";
		} else {
			modelMap.put("error", "User invalid");
			return "user/index";
		}
	}
	
	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession session) {		
		if(userService.createUser(user)) {
			session.setAttribute("user", user);
			session.setAttribute("username", user.getUsername());
//			return "user/welcome";
			return "redirect:/";
		} else {
			modelMap.put("error", "Something happened");
			return "user/index";
		}
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:../user";
	}
	
}
