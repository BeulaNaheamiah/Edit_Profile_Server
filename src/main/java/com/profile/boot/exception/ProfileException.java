package com.profile.boot.exception;

public class ProfileException extends Throwable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProfileException(Exception e) {
		super(e);
	}

	public ProfileException(String message,Exception e) {
		super(message,e);
	}
	
	
}
