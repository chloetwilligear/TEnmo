package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;

public class ConsoleService {

	private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticationService authenticationService;
    private TransferService transferService = new TransferService(API_BASE_URL);
    private AccountService accountService = new AccountService(API_BASE_URL);
	private UserService userService = new UserService(API_BASE_URL);
	private App app = new App();
    
	private PrintWriter out;
	private Scanner in;
	
	
	public ConsoleService() {
		
	}

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
	
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
	
	public void viewTransferHistoryById(int userId, AuthenticatedUser currentUser) {
		
		Transfer[] transfers = transferService.getTransfers(userId);
		String from = "From: ";
		String to = "To: ";
		String result = "";
		
		for(int i = 0; i < transfers.length; i++) {
		
			if (transfers[i].getTypeId() == 1) {
				
				if(transfers[i].getUsername().equals(currentUser.getUser().getUsername())) {
					result = to;
				} else if (!transfers[i].getUsername().equals(currentUser.getUser().getUsername())) {
					result = from;
				}
				
			} else {
				
				if(!transfers[i].getUsername().equals(currentUser.getUser().getUsername())) {
					result = to;
				} else if (transfers[i].getUsername().equals(currentUser.getUser().getUsername())) {
					result = from;
				}
			}
						
			System.out.printf( "%-10d  %-6s %-25s $%-13.2f\n" , transfers[i].getTransferId(), result , transfers[i].getUsername(),  transfers[i].getAmount() ); 
			
		}
		
		
			
		System.out.println("----------");
		System.out.println(" ");
	}
	
	
	public void viewTransferHistoryPendingById(int userId) {
		
		Transfer[] transfers = transferService.getPendingTransfers(userId);
	
		
		for(int i = 0; i < transfers.length; i++) {
			
			System.out.printf( "%-10d  %-6s  $%-13.2f\n" , transfers[i].getTransferId(), userService.getUsernameById(transfers[i].getAccountTo()),  transfers[i].getAmount() ); 
		}
					
			
		System.out.println("----------");
		System.out.println(" ");
	}
	

	public void approveOrReject() {
		System.out.println("1: Approve");
		System.out.println("2: Reject ");
		System.out.println("0: Dont Approve Or Reject");
		System.out.println("----------------------------------");
		
	}
	
	public void sendTeBucksMenu() {
		System.out.println("----------------------------------");
		System.out.println("Users ");
		System.out.println("ID             Name");
		System.out.println("----------------------------------");
		
	}
	
	public void welcomeScreen() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
	}
	
	public void transferByIdScreen() {
		System.out.println("------------------------------------------------------------");
		System.out.println("Transfers");
		System.out.println("ID            From/To                        Amount");
		System.out.println("------------------------------------------------------------");
	}
	
	public void transferByIdPendingScreen() {
		System.out.println("------------------------------------------------------------");
		System.out.println("Pending Transfers");
		System.out.println("ID           To                              Amount");
		System.out.println("------------------------------------------------------------");
	}
	
	
}
