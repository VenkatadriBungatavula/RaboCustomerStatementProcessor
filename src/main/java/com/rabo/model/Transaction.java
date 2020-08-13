package com.rabo.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is a component and it represents transaction unit.
 * @author Venkatadri Bungatavula
 * Initial version: Aug 12, 2020
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
	
	@NotNull
	@JsonProperty(value = "Reference")
	private long reference;
	@NotNull
	@JsonProperty(value = "AccountNumber")
	private String accountNumber;
	
	@JsonProperty(value = "Description")
	private String description;
	
	@NotNull
	@JsonProperty(value = "Start Balance")
	private double startBalance;
	
	@NotNull
	@JsonProperty(value = "Mutation")
	private double mutation;
	
	@NotNull
	@JsonProperty(value = "End Balance")
	private double endBalance;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}
	public double getMutation() {
		return mutation;
	}
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}
	public double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}
	
	

}
