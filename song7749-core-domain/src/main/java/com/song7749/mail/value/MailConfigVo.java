package com.song7749.mail.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.song7749.common.base.AbstractVo;
import com.song7749.mail.type.EmailProtocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("메일 환경 설정")
public class MailConfigVo extends AbstractVo{

	private static final long serialVersionUID = -2837827506976032498L;

	@ApiModelProperty(position=1,	value="HOST")
	private String host;

	@ApiModelProperty(position=2,	value="PORT")
	private Integer port;

	@ApiModelProperty(position=3,	value="AUTH")
	private Boolean auth = true;

	@ApiModelProperty(position=4,	value="USER NAME")
	private String username;

	@ApiModelProperty(position=5,	value="PASSWORD")
	private String password;

	@ApiModelProperty(position=6,	value="PROTOCOL")
	@Enumerated(EnumType.STRING)
	private EmailProtocol protocol;

	@ApiModelProperty(position=7,	value="ENABLE SSL")
	private Boolean enableSSL = true;

	@ApiModelProperty(position=8,	value="START TLS")
	private Boolean starttls = true;
}