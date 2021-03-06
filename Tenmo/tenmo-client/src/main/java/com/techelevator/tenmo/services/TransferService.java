package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.view.ConsoleService;

public class TransferService {

	private final String API_BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public TransferService(String url) {
		API_BASE_URL = url;
	}
	
	public void createTransfer(int typeId, int transferStatus, int accountFrom, int accountTo, float amount) {
		Transfer transfer = new Transfer();
		transfer.setTypeId(typeId);
		transfer.setTransferstatus(transferStatus);
		transfer.setAccountFrom(accountFrom);
		transfer.setAccountTo(accountTo);
		transfer.setAmount(amount);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	    
	    restTemplate.exchange(API_BASE_URL + "users/transfer", HttpMethod.POST , entity, Transfer.class).getBody();
		
	}
	
	public Transfer[] getTransfers(int userId) {
		Transfer[] transfers = restTemplate.getForObject(API_BASE_URL + "users/" + userId + "/transfer", Transfer[].class);
		return transfers;
	}
	
	public Transfer[] getPendingTransfers(int userId) {
		Transfer[] transfers = restTemplate.getForObject(API_BASE_URL + "users/" + userId + "/transfer/pending", Transfer[].class);
		return transfers;
	}
	
	public Transfer getTransferById(int userId, int transferId) {
		Transfer transfer = restTemplate.getForObject(API_BASE_URL + "users/" + userId + "/transfer/" + transferId, Transfer.class);
		return transfer;
	}
	
	
	public void transferUpdate(int transferId, int transferStatus) {
		Transfer transfer = new Transfer();
		transfer.setTransferstatus(transferStatus);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		
		restTemplate.exchange(API_BASE_URL + "users/transfer/" + transferId + "/update", HttpMethod.PUT, entity, Transfer.class).getBody();
		
	}
	
	

}
