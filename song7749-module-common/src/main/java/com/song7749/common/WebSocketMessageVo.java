package com.song7749.common;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <pre>
 * Class Name : WebSocketMessageVo.java
 * Description : 웹 소켓을 이용해서 통신할 경우 주고 받는 메세지 포멧.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 6. 4.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 6. 4.
*/
public class WebSocketMessageVo extends AbstractVo {

	private static final long serialVersionUID = -6105295488339936341L;

	/**
	 * <pre>
	 * http 상태 코드.
	 * HttpServletResponse의 상수사용
	 * =============================
	 * 200 - OK
	 * 400 - Bad Request
	 * 500 - Internal Server Error
	 * 201 - Created
	 * 304 - Not Modified
	 * 404 - Not Found
	 * 401 - Unauthorized
	 * 403 - Forbidden
	 * =============================
	 * </pre>
	 *
	 * <code>
	 * Response.setStatus(HttpStatus.OK)
	 * </code>
	 */
	private Integer httpStatus;

	/**
	 * return contents
	 */
	private Object contents;

	/**
	 * error message
	 */
	private String message;

	/**
	 * response day
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-dd-MM")
	private Date date = new Date(System.currentTimeMillis());

	/**
	 * response time
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	private Time time = new Time(System.currentTimeMillis());

	/**
	 * run time (ms)
	 */
	private Long processTime;

	/**
	 * sender key
	 */
	private String senderApikey;

	/**
	 * reciever key
	 */
	private List<String> recieverApikey;

	/**
	 *
	 */
	public WebSocketMessageVo() {}

	/**
	 * @param senderApikey
	 */
	public WebSocketMessageVo(String senderApikey) {
		this.senderApikey = senderApikey;
	}

	/**
	 * @param httpStatus
	 * @param contents
	 * @param message
	 * @param date
	 * @param time
	 * @param processTime
	 * @param senderApikey
	 * @param recieverApikey
	 */
	public WebSocketMessageVo(Integer httpStatus, Object contents, String message, Date date, Time time,
			Long processTime, String senderApikey, List<String> recieverApikey) {
		this.httpStatus = httpStatus;
		this.contents = contents;
		this.message = message;
		this.date = date;
		this.time = time;
		this.processTime = processTime;
		this.senderApikey = senderApikey;
		this.recieverApikey = recieverApikey;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public String getSenderApikey() {
		return senderApikey;
	}

	public void setSenderApikey(String senderApikey) {
		this.senderApikey = senderApikey;
	}

	public List<String> getRecieverApikey() {
		return recieverApikey;
	}

	public void setRecieverApikey(List<String> recieverApikey) {
		this.recieverApikey = recieverApikey;
	}
}