package com.techelevator.tenmo.models;

public class Transfer {
	
	private int transferId;
	private int typeId;
	private int transferstatus;
	private int accountFrom;
	private int accountTo;
	private Float amount;
	private String username;
	
	public Transfer() { }

    public Transfer(int transferId, int typeId, int transferstatus, int accountFrom,  int accountTo, Float amount, String username) {
       this.transferId = transferId;
       this.typeId = typeId;
       this.transferstatus = transferstatus;
       this.accountFrom = accountFrom;
       this.accountTo = accountTo;
       this.amount = amount;
       this.username = username; 
    }
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getTransferstatus() {
		return transferstatus;
	}
	public void setTransferstatus(int transferstatus) {
		this.transferstatus = transferstatus;
	}
	public int getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	
	
		

}
