package com.song7749.mail.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.common.base.AbstractDto;
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
@ApiModel("email server 환경 설정")
public class MailConfigDto extends AbstractDto{

	private static final long serialVersionUID = -1609376767301971918L;

	@ApiModelProperty(position=1,	value="HOST || EX) mail.google.co.kr")
	@NotBlank
	private String host;

	@ApiModelProperty(position=2,	value="PORT || EX) NORMAL:25 / SSL:465 / TLS:587")
	@NotNull
	private Integer port;

	@ApiModelProperty(position=3,	value="AUTH || 메일 전송시 사용자 인증 필요 여부")
	private Boolean auth = true;

	@ApiModelProperty(position=4,	value="USER NAME || 인증이 필요할 경우 사용자 ID EX) 지메일 주소")
	private String username;

	@ApiModelProperty(position=5,	value="PASSWORD || 인증이 필요할 경우 사용자 패스워드")
	private String password;

	@ApiModelProperty(position=6,	value="PROTOCOL || 메일 프로토콜 선택")
	@NotNull
	@Enumerated(EnumType.STRING)
	private EmailProtocol protocol = EmailProtocol.smtp;

	@ApiModelProperty(position=7,	value="ENABLE SSL || SSL 통신 : 포트 번호도 변경해야 함.")
	private Boolean enableSSL = true;

	@ApiModelProperty(position=8,	value="START TLS || TLS 통신 : 포트 번호도 변경해야 함.")
	private Boolean starttls = true;
}