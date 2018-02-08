package com.vishank.desai.Exception;

public class VendingMachineException extends RuntimeException {

	private String typeOfException;
	private String message;
	
	
	public VendingMachineException(String typeOfException, String message) {
		super();
		this.typeOfException = typeOfException;
		this.message = message;
	}
	public String getTypeOfException() {
		return typeOfException;
	}
	public void setTypeOfException(String typeOfException) {
		this.typeOfException = typeOfException;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
