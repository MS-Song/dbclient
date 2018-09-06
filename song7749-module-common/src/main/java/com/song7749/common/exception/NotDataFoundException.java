package com.song7749.common.exception;

public class NotDataFoundException extends RuntimeException{

	private static final long serialVersionUID = -6014320124361207409L;

	public NotDataFoundException(){
		super("데이터가 존재하지 않습니다.");
	}

	public NotDataFoundException(String message){
		super(message);
	}
}
