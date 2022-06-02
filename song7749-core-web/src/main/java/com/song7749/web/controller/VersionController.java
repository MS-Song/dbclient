package com.song7749.web.controller;

import com.song7749.common.base.MessageVo;
import com.song7749.common.config.ConfigProperties;
import com.song7749.common.config.ConfigProperties.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Software Version APIs")
@RestController
@RequestMapping("/version")
public class VersionController {
    
    @Autowired
    private ConfigProperties config;

    @ApiOperation(value = "버전",notes = "S/W 버전 조회")
    @GetMapping("/{name}")
    public MessageVo getVersion(@PathVariable String name){
        for(Version version : config.getVersions()){
            if(name.equals(version.getName())){
                return new MessageVo(HttpStatus.OK.value(), (Object)version.getVersion());
            }
        }
        return new MessageVo(HttpStatus.NO_CONTENT.value(), "해당하는 버전이 없습니다.");
    }
}
