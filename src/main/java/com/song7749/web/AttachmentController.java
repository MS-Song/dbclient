package com.song7749.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.song7749.base.MessageVo;
import com.song7749.dbclient.annotation.Login;
import com.song7749.dbclient.service.CSVFileDataManager;
import com.song7749.dbclient.type.AuthType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "파일 업로드")
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CSVFileDataManager csvFileDataManager;


	@ApiOperation(value = "CSV 파일 업로드"
			,notes = "파일의 내용을 Database Insert 한다."
			,response=MessageVo.class)
	@PostMapping(value="/addCSVUploadCreateData")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo addCSVUploadCreateData( HttpServletRequest request,HttpServletResponse response,
			@RequestPart(name="upload",required=true) MultipartFile file,
			@RequestParam(required=true) Long databaseId,
			@RequestParam(required=true) String tableName,
			@RequestParam(required=true) Boolean isCreateTable
		) throws IOException {

		return csvFileDataManager.saveData(file, databaseId, tableName, isCreateTable);
	}

}
