package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserDAO userDao;
	
	public UserController(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	@RequestMapping(path= "", method=RequestMethod.GET)
	public List<User> users() {
		return userDao.listUser();
	}
	
	@RequestMapping(path= "/{userId}/username", method=RequestMethod.GET)
	public String userNameById(@PathVariable ("userId") int userId) {
		return userDao.returnUsernameById(userId);
	}

}
