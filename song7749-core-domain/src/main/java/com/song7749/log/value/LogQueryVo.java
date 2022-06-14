package com.song7749.log.value;

import java.util.Date;

import com.song7749.common.base.AbstractVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogQueryVo extends AbstractVo {

	private static final long serialVersionUID = 8473352501114190074L;

	private Long id;
	private String ip;
	private Date date;
	private String loginId;
	private Long databaseId;
	private String host;
	private String hostAlias;
	private String schemaName;
	private String account;
	private String query;
}