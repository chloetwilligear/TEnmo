package com.techelevator.tenmo.models;

public class Account {

	private int userId;
	private int accountId;
	private float balance;
	
	public Account() { }

    public Account(int userId, int accountId, float balance) {
       this.userId = userId;
       this.accountId = accountId;
       this.balance = balance;
    }
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [userId=" + userId + ", accountId=" + accountId + ", balance=" + balance + "]";
	}
	
	
}
