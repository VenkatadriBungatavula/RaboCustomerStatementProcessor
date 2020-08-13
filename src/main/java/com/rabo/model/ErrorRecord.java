package com.rabo.model;

public class ErrorRecord {
	
	public ErrorRecord() {
		
	}
	
	public ErrorRecord(long reference, String accountNumber) {
		this.reference = reference;
		this.accountNumber = accountNumber;
	}
	
	private long reference;
	private String accountNumber;
	public long getReference() {
		return reference;
	}
	public void setReference(long reference) {
		this.reference = reference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	

}
