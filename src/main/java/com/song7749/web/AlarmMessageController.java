package com.song7749.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.song7749.base.MessageVo;

@Controller
public class AlarmMessageController {

    @MessageMapping("/send")
    @SendTo("/topic/recieve")
    public MessageVo messageSend(MessageVo vo) throws Exception {
    	return vo;
    }

    @MessageMapping("/runAlarms")
    @SendTo("/topic/runAlarms")
    public MessageVo runAlarms(MessageVo vo) throws Exception {
    	return vo;
    }
}