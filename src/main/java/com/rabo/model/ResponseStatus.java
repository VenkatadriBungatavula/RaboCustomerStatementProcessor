package com.rabo.model;

public class ResponseStatus {
	
	private String result;
	private ErrorRecord [] errorRecords;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ErrorRecord[] getErrorRecords() {
		return errorRecords;
	}
	public void setErrorRecords(ErrorRecord[] errorRecords) {
		this.errorRecords = errorRecords;
	}
	
}
