package com.techelevator.tenmo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Transfer;



@RestController
@RequestMapping("/users")
public class AccountController {

	private AccountDAO accountDao;
	
	public AccountController(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	
	
	@RequestMapping(path= "/{userId}/balance", method=RequestMethod.GET)
	public Float getBalance(@PathVariable("userId") int userId) {
		return accountDao.returnBalance(userId);
		
	}
	
	@RequestMapping(path= "/{userId}/deposit", method=RequestMethod.PUT)
	public void addBalance(@PathVariable int userId, @RequestBody Transfer transfer ) {
		accountDao.addBalance(userId, transfer.getAmount());
		
	}
	
	@RequestMapping(path= "/{userId}/withdraw", method=RequestMethod.PUT)
	public void deleteBalance(@PathVariable int userId, @RequestBody Transfer transfer) {
		accountDao.deleteBalance(userId, transfer.getAmount());
	}
	
}
