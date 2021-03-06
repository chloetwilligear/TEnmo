package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.view.ConsoleService;

public class AccountService {
	
	private static AuthenticatedUser user = new AuthenticatedUser();
	private final String API_BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();


	public AccountService(String url) {
		API_BASE_URL = url;
	}
	
	public void addBalance(int userId, float transferAmount) {
		Transfer transfer = new Transfer();
		transfer.setAmount(transferAmount);
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(user.getToken());
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	    
	   
	    restTemplate.exchange(API_BASE_URL + "users/" +userId + "/deposit", HttpMethod.PUT , entity, Transfer.class).getBody();
	  
	}
	
	public void deleteBalance(int userId, float transferAmount) {
		
		Transfer transfer = new Transfer();
		transfer.setAmount(transferAmount);
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	    
	    restTemplate.exchange(API_BASE_URL + "users/" + userId + "/withdraw", HttpMethod.PUT , entity, Transfer.class).getBody();
	}
}
