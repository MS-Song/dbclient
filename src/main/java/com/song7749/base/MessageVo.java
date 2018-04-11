package com.song7749.base;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MessageVo.java
 * Description : Client 에 보내는 메세지를 정의 한다.
 * 모든 결과 값이 MessageVo 로 Wrapping 되어 전달 된다.
 * 정상 적인 경우 GlobalResponseAdvice.java 에서 객체를 wrapping 한다.
 * exception 이 발생할 경우 GlobalControllerAdvice.java 에서 wrapping 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 7.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 3. 7.
 */

@ApiModel("response wrapper")
public class MessageVo extends AbstractVo {

	private static final long serialVersionUID = -1400334184587170703L;

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
	@ApiModelProperty("HttpStatus")
	private Integer httpStatus;

	/**
	 * work database row or work Object count
	 */
	@ApiModelProperty("list count or affected row Count")
	private Integer rowCount;

	/**
	 * return contents
	 */
	@ApiModelProperty("list or 1row contents")
	private Object contents;

	/**
	 * error message
	 */
	@ApiModelProperty("exception message or server send user message")
	private String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-dd-MM")
	@ApiModelProperty(value = "api call date")
	private Date date = new Date(System.currentTimeMillis());

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
	@ApiModelProperty(value = "api call time")
	private Time time = new Time(System.currentTimeMillis());

	private Long processTime;

	public MessageVo() {}

	/**
	 * @param httpStatus
	 * @param message
	 */
	public MessageVo(Integer httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * @param httpStatus
	 * @param contents
	 */
	public MessageVo(Integer httpStatus, Object contents) {
		this.httpStatus = httpStatus;
		this.contents = contents;
	}

	/**
	 * @param httpStatus
	 * @param contents
	 * @param message
	 */
	public MessageVo(Integer httpStatus, Object contents, String message) {
		this.httpStatus = httpStatus;
		this.contents = contents;
		this.message = message;
	}

	/**
	 * @param httpStatus
	 * @param affectedRows
	 * @param message
	 */
	public MessageVo(Integer httpStatus, Integer rowCount, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.rowCount = rowCount;
	}

	/**
	 * @param httpStatus
	 * @param rowCount
	 * @param contents
	 */
	public MessageVo(Integer httpStatus, Integer rowCount, Object contents) {
		this.httpStatus = httpStatus;
		this.rowCount = rowCount;
		this.contents = contents;
	}

	/**
	 * @param httpStatus
	 * @param rowCount
	 * @param contents
	 * @param message
	 */
	public MessageVo(Integer httpStatus, Integer rowCount, Object contents, String message) {
		this.httpStatus = httpStatus;
		this.rowCount = rowCount;
		this.contents = contents;
		this.message = message;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
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

	public Time getTime() {
		return time;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}


}