package com.song7749.dbclient.exception;

public class MemberNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -6014320124361207409L;

	public MemberNotFoundException(){
		super("회원 데이터가 존재하지 않습니다.");
	}

	public MemberNotFoundException(String message){
		super(message);
	}
}
