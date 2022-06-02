package com.song7749.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
* <pre>
* Class Name : ConfigProperties
* Description : 추가 Config 설정을 Load 한다.
*
*
*  Modification Information
*  Modify Date     Modifier        Comment
*  -----------------------------------------------
*  2022.06.02       Song7749       New
*
* </pre>
*
* @author Song7749
* @since 2022.06.02
*/

@Getter
@Setter
@NoArgsConstructor
@ToString
@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {

    private List<Version> versions;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Version {
        private String name;
        private String version;
 
    }
}