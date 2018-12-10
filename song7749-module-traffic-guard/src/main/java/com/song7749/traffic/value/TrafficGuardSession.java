package com.song7749.traffic.value;

import java.sql.Timestamp;

import com.song7749.common.AbstractVo;

public class TrafficGuardSession extends AbstractVo{

	private static final long serialVersionUID = -1061073271538943808L;

	private Timestamp time;

	private Long trafficGuadId;

	private String sessionId;

	private String userIp;

	public TrafficGuardSession() {}

	/**
	 * @param time
	 * @param trafficGuadId
	 * @param sessionId
	 * @param userIp
	 */
	public TrafficGuardSession(Timestamp time, Long trafficGuadId, String sessionId, String userIp) {
		this.time = time;
		this.trafficGuadId = trafficGuadId;
		this.sessionId = sessionId;
		this.userIp = userIp;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Long getTrafficGuadId() {
		return trafficGuadId;
	}

	public void setTrafficGuadId(Long trafficGuadId) {
		this.trafficGuadId = trafficGuadId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
}