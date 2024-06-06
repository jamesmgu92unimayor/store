package com.example.store.exception;

import org.springframework.http.HttpStatus;

public class ModelNotFoundException extends RuntimeException{
	private final HttpStatus code;
	private final String errorCode;
	public ModelNotFoundException(HttpStatus code, String errorCode, String message) {
		super(message);
		this.code = code;
		this.errorCode = errorCode;
	}

	public HttpStatus getCode() {
		return this.code;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
}
