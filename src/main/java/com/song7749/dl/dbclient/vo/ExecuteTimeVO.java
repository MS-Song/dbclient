package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.Vo;

public class ExecuteTimeVO extends Vo{

	private static final long serialVersionUID = -7808224511799246117L;

	private Long executeTime;
	private Long queryTime;
	private Long fetchTime;

	public ExecuteTimeVO() {}

	public ExecuteTimeVO(Long executeTime, Long queryTime, Long fetchTime) {
		this.executeTime = executeTime;
		this.queryTime = queryTime;
		this.fetchTime = fetchTime;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public Long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Long queryTime) {
		this.queryTime = queryTime;
	}

	public Long getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Long fetchTime) {
		this.fetchTime = fetchTime;
	}
}