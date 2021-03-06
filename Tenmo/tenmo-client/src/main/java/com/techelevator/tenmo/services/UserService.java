package com.techelevator.tenmo.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {
	
	private static AuthenticatedUser user = new AuthenticatedUser();
	private final String API_BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public UserService(String url) {
		API_BASE_URL = url;
	}
	
	public Float getUserBalance(int userId) {
		float balance = 0;
		balance = restTemplate.getForObject(API_BASE_URL + "users/" + userId + "/balance", float.class );
		return balance;
	}
	
	public User[] getUsers() {
		User[] users = restTemplate.getForObject(API_BASE_URL + "users", User[].class);
		return users;
	}
	
	public String getUsernameById(int userId) {
		String userName = restTemplate.getForObject(API_BASE_URL + "users/" + userId + "/username", String.class);
		return userName;
	}

	
}
