package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser; 
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private UserService userService = new UserService(API_BASE_URL);
    private TransferService transferService = new TransferService(API_BASE_URL);
    private AccountService accountService = new AccountService(API_BASE_URL);
   

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }
    
    public App () {
    	
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		console.welcomeScreen();
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory(currentUser.getUser().getId(), currentUser);
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests(currentUser.getUser().getId());
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		User user = currentUser.getUser();
		int id = user.getId();
		float userBalance = userService.getUserBalance(id);
		System.out.printf("$%-10.2f\n", userBalance);
		
	}

	private void viewTransferHistory(int userId, AuthenticatedUser currentUser) {
		
		console.transferByIdScreen();
		console.viewTransferHistoryById(userId, currentUser);
		printUserTransferDetailsHistory(currentUser);
	}

	private void viewPendingRequests(int userId) {
		
		
		try {
		console.transferByIdPendingScreen();
		console.viewTransferHistoryPendingById(userId);
		int choice = console.getUserInputInteger("Please Enter Transfer ID To Approve/Reject(0 to cancel)");
		console.approveOrReject();
		int choice2 = console.getUserInputInteger("Please Choose An Option");
		
		if (choice2 == 1) {
			
			int pendingID = transferService.getTransferById(userId, choice).getAccountTo();
			float pendingAmount = transferService.getTransferById(userId, choice).getAmount();
			
			accountService.addBalance( pendingID  , pendingAmount);
			accountService.deleteBalance(userId, pendingAmount);
			transferService.transferUpdate(choice, 2);
		} else if ( choice2 == 2) {
			transferService.transferUpdate(choice, 3);
		} else if ( choice == 0) {
			
			mainMenu();
		}
		} catch(Exception e) {
			System.out.println("Incorrect Values, Please Try Again");
		}
		
	}

	private void sendBucks() {
		int approvedStatus = 2;
		int transferType = 2;
		console.sendTeBucksMenu();
		User[] users = userService.getUsers();
		for(int i = 0; i < users.length; i++) {
			int id = users[i].getId();
			
			System.out.printf( "%-13d  %-20s \n" , id, users[i].getUsername()); 
				
		}
		
		try {
		System.out.println("----------------------------------\n");	
		int choice = console.getUserInputInteger("Enter id of user you're sending to(0 to cancel)");
		
		if (choice == 0) {
			mainMenu();
		}
		
		String choice2 = console.getUserInput("Enter amount");
		if(Float.parseFloat(choice2) > userService.getUserBalance(currentUser.getUser().getId())) {
			System.out.println("Insufficient funds. Please try again!");
			sendBucks();
		} else {
			accountService.addBalance(choice, Float.parseFloat(choice2));
			accountService.deleteBalance(currentUser.getUser().getId(), Float.parseFloat(choice2));
			transferService.createTransfer(transferType, approvedStatus, currentUser.getUser().getId(), choice, Float.parseFloat(choice2));
		}
		
		} catch (Exception e) {
			System.out.println("Incorrect Values, Please Try Again");
			sendBucks();
		}
	}

	private void requestBucks() {
		int approvedStatus = 1;
		int transferType = 1;
		console.sendTeBucksMenu();
		User[] users = userService.getUsers();
		for(int i = 0; i < users.length; i++) {
			int id = users[i].getId();
			
			System.out.printf( "%-13d  %-20s \n" , id, users[i].getUsername()); 
				
		}
		
		try {
			System.out.println("----------------------------------\n");	
			int choice = console.getUserInputInteger("Enter id of user you're sending to(0 to cancel)");
			
			if (choice == 0) {
				mainMenu();
			}
			
			String choice2 = console.getUserInput("Enter amount");
			
			transferService.createTransfer(transferType, approvedStatus, choice, currentUser.getUser().getId(),  Float.parseFloat(choice2));
			
		} catch (Exception e) {
			System.out.println("Incorrect Values, Please Try Again");
			requestBucks();
		}
		
	}
	
	
	public void printUserTransferDetailsHistory (AuthenticatedUser currentUser) {
	
		
		try {
			
		
		int choice = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel)");
		
		if ( choice == 0 ) {
			mainMenu();
		}
		Transfer transfer = transferService.getTransferById(currentUser.getUser().getId(), choice);
		
		System.out.println("------------------------------------------------------------");
		System.out.println("Transfer Details");
		System.out.println("------------------------------------------------------------");
		System.out.println("ID: " + transfer.getTransferId());
		System.out.println("From: " + userService.getUsernameById(transfer.getAccountFrom())  );     //currentUser.getUser().getUsername());
		System.out.println("To: " +  userService.getUsernameById(transfer.getAccountTo()));//transfer.getUsername());
		if(transfer.getTypeId() == 1) {
		System.out.println("Type: Request");
		} else {
			System.out.println("Type: Send");
		} 
		System.out.println("Status: Approved");
		
		System.out.printf("Amount: $%-10.2f\n", transfer.getAmount());
		
		} catch (Exception e) {
			
			System.out.println("Transfer Does Not Exist Please Try Again!");
		}

		
	}
	
	
	
	
	//Given Code
	
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
