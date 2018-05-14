package com.song7749.incident.task;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.base.MessageVo;
import com.song7749.base.YN;
import com.song7749.dbclient.service.DBclientManager;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.incident.domain.IncidentAlarm;
import com.song7749.incident.repository.IncidentAlarmRepository;

/**
 * <pre>
 * Class Name : IncidentAlarmTask.java
 * Description : 알람 실행
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 5. 13.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 5. 13.
*/
public class IncidentAlarmTask implements Runnable {

	Logger logger = LoggerFactory.getLogger(getClass());

	private DBclientManager dbClientManager;

	private IncidentAlarm incidentAlarm;

	private IncidentAlarmRepository incidentAlarmRepository;

	public IncidentAlarmTask(DBclientManager dbClientManager
			, IncidentAlarm incidentAlarm
			, IncidentAlarmRepository incidentAlarmRepository) {

		this.dbClientManager=dbClientManager;
		this.incidentAlarm=incidentAlarm;
		this.incidentAlarmRepository=incidentAlarmRepository;
	}

	public IncidentAlarm getIncidentAlarm() {
		return incidentAlarm;
	}

	@Override
	public void run() {
		logger.trace(format("{}", "TASK RUN START"),incidentAlarm.getId());

		// 실행 상태
		 boolean isExecute = YN.Y.equals(incidentAlarm.getEnableYN());
		// 승인 상태
		isExecute = isExecute && YN.Y.equals(incidentAlarm.getConfirmYN());

		// 전송 해야하는 상태인가 확인 한다.
		if(isExecute) {
			ExecuteQueryDto dto = new ExecuteQueryDto();
			dto.setId(incidentAlarm.getDatabase().getId());
			dto.setQuery(incidentAlarm.getBeforeSql());
			dto.setUseLimit(false);
			dto.setUseCache(false);

			try {
				MessageVo vo = dbClientManager.executeQuery(dto);
				List<Map<String,String>> contents = (List<Map<String, String>>) vo.getContents();

				if(null!=contents && contents.size()>0) {
					for(String  key : contents.get(0).keySet()) {
						if("Y".equals(contents.get(0).get(key))){
							isExecute=true;
							break;
						}
					}
				} else {
					throw new IllegalArgumentException("before sql 이 올바르지 않습니다. SQL : " + incidentAlarm.getBeforeSql());
				}
			} catch (Exception e) {
				isExecute=false; // 실행 중지
				incidentAlarm.setLastErrorMessage(e.getMessage());
				incidentAlarm.setLastRunDate(new Date(System.currentTimeMillis()));
				incidentAlarmRepository.saveAndFlush(incidentAlarm);
			}
		}

		// 전송해야 하는 데이터
		if(isExecute) {

			ExecuteQueryDto dto = new ExecuteQueryDto();
			dto.setId(incidentAlarm.getDatabase().getId());
			dto.setQuery(incidentAlarm.getRunSql());
			dto.setUseLimit(false);
			dto.setUseCache(false);

			try {
				MessageVo vo = dbClientManager.executeQuery(dto);
				List<Map<String,String>> contents = (List<Map<String, String>>) vo.getContents();
				if(null!=contents && contents.size()>0) {


				} else {
					throw new IllegalArgumentException("run sql 이 올바르지 않습니다. SQL : " + incidentAlarm.getRunSql());
				}
			} catch (Exception e) {
				isExecute=false; // 실행 중지
				incidentAlarm.setLastErrorMessage(e.getMessage());
				incidentAlarm.setLastRunDate(new Date(System.currentTimeMillis()));
				incidentAlarmRepository.saveAndFlush(incidentAlarm);
			}
		}

		// 전송
		if(isExecute) {

		}

		logger.trace(format("{}", "TASK RUN END"),incidentAlarm.getId());
	}
}
