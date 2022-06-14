package com.song7749.log.value;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("로그인 인증 정보")
public class LogLoginVo extends AbstractVo {

	private static final long serialVersionUID = 8796038916710177418L;

	private Long id;

	private String ip;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	private String loginId;

	private String cipher;
}