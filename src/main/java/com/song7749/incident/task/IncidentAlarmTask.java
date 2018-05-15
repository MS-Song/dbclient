package com.song7749.incident.task;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.base.MessageVo;
import com.song7749.base.SendMethod;
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

		// 전송 대상자가 있는가?
		isExecute= isExecute
				&& null!=incidentAlarm.getSendMembers()
				&& !incidentAlarm.getSendMembers().isEmpty();

		ExecuteQueryDto dto = new ExecuteQueryDto();
		dto.setId(incidentAlarm.getDatabase().getId());
		dto.setLoginId(incidentAlarm.getResistMember().getLoginId());
		dto.setIp("local-test");
		dto.setUseLimit(false);
		dto.setUseCache(false);

		// send Message;
		String sendMessage = "";

		// 전송 해야하는 상태인가 확인 한다.
		if(isExecute) {
			dto.setQuery(incidentAlarm.getBeforeSql());

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
			dto.setQuery(incidentAlarm.getRunSql());
			dto.setUseLimit(false);
			dto.setUseCache(false);

			try {
				MessageVo vo = dbClientManager.executeQuery(dto);
				List<Map<String,String>> contents = (List<Map<String, String>>) vo.getContents();
				if(null!=contents && contents.size()>0) {
					sendMessage = sendMessageFormatter(incidentAlarm.getSendMethod(),contents);
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
		if(isExecute
				&& !StringUtils.isEmpty(sendMessage)) {

			// size 가 과도할 경우에는 SMS --> Email 로 변경 처리 하고 로그를 쌓는다.
			logger.info(format("{}", "Send Message"),sendMessage);
		}

		logger.trace(format("{}", "TASK RUN END"),incidentAlarm.getId());
	}

	private String sendMessageFormatter(SendMethod sendMethod
			, List<Map<String,String>> contents) {
		StringBuffer sendMessageBuffer = new StringBuffer();
		if(SendMethod.EMAIL.equals(incidentAlarm.getSendMethod())) {
			sendMessageBuffer.append("<table>");
			for(int i=0; i < contents.size(); i++) {
				// table head 만들기
				if(i==0) {
					sendMessageBuffer.append("<thead>");
					sendMessageBuffer.append("<tr>");
					for(String head : contents.get(i).keySet()) {
						sendMessageBuffer.append("<th>");
						sendMessageBuffer.append(head);
						sendMessageBuffer.append("</th>");
					}
					sendMessageBuffer.append("</tr>");
					sendMessageBuffer.append("</thead>");
				} else {
					sendMessageBuffer.append("<tr>");
					for(String head : contents.get(i).keySet()) {
						sendMessageBuffer.append("<th>");
						sendMessageBuffer.append(contents.get(i).get(head));
						sendMessageBuffer.append("</th>");
					}
					sendMessageBuffer.append("</tr>");
				}
			}
			sendMessageBuffer.append("</table>");
		} else {
			for(int i=0; i < contents.size(); i++) {
				for(String head : contents.get(i).keySet()) {
					sendMessageBuffer.append(contents.get(i).get(head));
				}
				sendMessageBuffer.append("\n");
			}
		}
		return sendMessageBuffer.toString();
	}
}