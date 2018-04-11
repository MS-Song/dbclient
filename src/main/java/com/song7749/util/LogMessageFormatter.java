package com.song7749.util;


public class LogMessageFormatter {
	public LogMessageFormatter(){}

	/**
	 * 로그 메세지의 포멧을 보기 편한 형식으로 변환해 준다.
	 * @param message
	 * @return String logMessage
	 */
	public static String format(String message){
		return LogMessageFormatter.makeFormat(message, "");
	}

	/**
	 * 로그 메세지의 포멧을 보기 편한 형식으로 변환해 준다.<br/>
	 * 로그 메세지에 타이틀을 추가한다.<br/>
	 * @param message
	 * @param title
	 * @return String logMessage
	 */
	public static String format(String message,String title){
		return LogMessageFormatter.makeFormat(message, title);
	}

	private static String makeFormat(String message,String title){
		if(title.equals("")){
			title = "Log Message";
		}

		// ** 로 표시되는 개수를 기본값 100개로 정한다.
		int bothPadSize = 100;

		StringBuffer sb = new StringBuffer();
		sb.append("\n");

		for(int i=0;i<bothPadSize/2 - title.length()/2 - 1;i++){
			sb.append("*");
		}
		sb.append(" ");
		sb.append(title);
		sb.append(" ");
		for(int i=0;i<bothPadSize/2 -title.length()/2 - 2;i++){
			sb.append("*");
		}

		sb.append("\n");
		sb.append(message);
		sb.append("\n");

		for(int i=0;i<bothPadSize;i++){
			sb.append("*");
		}
		sb.append("\n");
		return sb.toString();
	}

}