package com.song7749.common.exception;

/**
 * <pre>
 * Class Name : MemberNotIdentificationException.java
 * Description : 로그인 한 회원이 아닌 사람의 데이터에 접근,수정시에 발생한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 6. 3.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 6. 3.
 */
public class MemberNotIdentificationException extends RuntimeException {

	private static final long serialVersionUID = -6014320124361207409L;

	public MemberNotIdentificationException() {
		super("본인의 데이터만 수정할 수 있습니다.");
	}

	public MemberNotIdentificationException(String message) {
		super(message);
	}
}
