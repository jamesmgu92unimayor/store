package com.example.store.exception;

public class BusinessLogicException  extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public BusinessLogicException(String mensaje) {		
		super(mensaje);
	}
}
