package com.song7749.dl.base;

import java.sql.Date;
import java.sql.Time;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * Class Name : ResponseResult.java
 * Description : 컨트롤러에서 호출되는 모든 기능의 result data 를 wrapping 하여 처리한다.
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 28.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 28.
*/
@XStreamAlias("response")
public class ResponseResult extends BaseObject {

	private static final long serialVersionUID = -5957119625139293119L;

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
	 * Response.setStatus(HttpServletResponse.SC_OK)
	 * </code>
	 */
	private int status;

	/**
	 * 설명.
	 */
	private String desc;

	/**
	 * 추가 정보.
	 */
	private BaseEtc etc;

	/**
	 * 결과물 < List , Model 등이 들어간다 >
	 */
	private Object result;

	private Date date = new Date(System.currentTimeMillis());
	private Time time = new Time(System.currentTimeMillis());

	public ResponseResult() {}

	public ResponseResult(int status, String desc, BaseEtc etc) {
		this.status	= status;
		this.desc	= desc;
		this.etc	= etc;
	}

	public ResponseResult(int status, BaseEtc etc) {
		this.status	= status;
		this.etc	= etc;
	}

	public ResponseResult(int status, String desc) {
		this.status	= status;
		this.desc	= desc;
	}

	public ResponseResult(int status) {
		this.status	= status;
	}

	public ResponseResult(int status, int affectedRows) {
		BaseEtc baseEtc = new BaseEtc();
		baseEtc.setAffectedRows(affectedRows);

		this.status	= status;
		this.etc	= baseEtc;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the etc
	 */
	public Object getEtc() {
		return etc;
	}

	/**
	 * @param etc the etc to set
	 */
	public void setEtc(BaseEtc etc) {
		this.etc = etc;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}
}