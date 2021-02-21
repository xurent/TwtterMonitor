package com.xj.Server.exception;

public class LoginSQLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Exception e =null;
	public LoginSQLException(Exception e) {
		this.e=e;
	}
	
	/**
	 * ¥Ú”°“Ï≥£
	 */
	public static void PrintException(){
		
		System.out.println(e.getMessage());
	}
}
