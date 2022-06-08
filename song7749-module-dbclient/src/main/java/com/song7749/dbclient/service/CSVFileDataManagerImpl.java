package com.song7749.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.song7749.common.base.MessageVo;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.member.service.LoginSession;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVFileDataManagerImpl implements CSVFileDataManager{

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	DBclientManager dbclientManager;

	@Autowired
	LoginSession session;

	@Autowired
	ModelMapper mapper;


	@Override
	public MessageVo saveData(MultipartFile file, Long databaseId, String tableName, Boolean isCreateTable) {
		int count = 0;
		if(!file.isEmpty()) {
			// TODO - 파일을 저장 한 뒤에 비동기로 처리 하는 부분에 대한 고민이 필요하다.
			try {
		    		Database database = dbclientManager.getDatabase(databaseId);
					ExecuteQueryDto dto = new ExecuteQueryDto(database.getId(), session.getLogin().getLoginId());
					dto.setIp("CVS UPLOAD");
					dto.setAutoCommit(true);
					dto.setHtmlAllow(false);
					dto.setUseCache(false);
					dto.setUseLimit(false);

		    		String createTableSql = null;
		    		String columnType = null;
		    		String tableCharset = "";
		    		String insertSql = null;
		    		List<String> insertValues = new ArrayList<String>();

		    		// 테이블 사이즈 정의
		    		switch(database.getDriver()) {
		    		case ORACLE :
		    			columnType=" varchar2(2000) ";
		    			break;
		    		case MYSQL :
		    			columnType=" varchar(1000) ";
		    			tableCharset="DEFAULT CHARSET=utf8";
		    			break;
		    		case MARIADB :
		    			columnType=" varchar(1000) ";
		    			tableCharset="DEFAULT CHARSET=utf8";
		    			break;
		    		case MSSQL :
		    			columnType=" varchar(2000) ";
		    			break;
		    		case H2 :
		    			columnType=" varchar(2000) ";
		    			break;
					default:
						columnType=" varchar(1000) ";
						break;
		    		}

		    		InputStream is = file.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    		String line = null;
		    		String[] column = null;
					int loop=0;
					while ((line = br.readLine()) != null) {
			    		String[] values = line.split(",");
			    		if(loop==0) {
				    		// 테이블을 생성할 것인지?
				    		if(isCreateTable) {
				    			createTableSql= "create table " + tableName  + "( ";

				    			String[] columnDefine = new String[values.length];
				    			for(int i=0; i<values.length;i++) {
				    				columnDefine[i] = values[i].replaceAll("[^A-Za-z0-9]","") + columnType;
				    			}
				    			createTableSql+=String.join(",", columnDefine) + ") " + tableCharset;
				    			logger.trace(format("{}", " CSV create table SQL "),createTableSql);

								dto.setQuery(createTableSql);
								dbclientManager.executeQuery(dto);
							} //  테이블 생성 완료
							// insert 하기 위해 insert 구문 생성
							insertSql="insert into " + tableName ;
							// values column 생성
							column= new String[values.length];
							for(int i=0; i<values.length;i++) {
								column[i] = values[i].replaceAll("[^A-Za-z0-9]","");
							}
				    	} else {
				    		// 필드를 기준으로 없는 데이터는 생성하여 넣는다.
				    		String[] insertColumn = new String[values.length];
			    			for(int i=0; i<values.length;i++) {
			    				insertColumn[i] = column[i];
			    			}
			    			insertValues.add(insertSql + " ( " + String.join(",",insertColumn) + " ) " + "values('" + String.join("','",values) + "')");
				    	}
				    	loop++;
				    }
					// insert 작업 시작
					count=insertValues.size();

					String query="";
					for(String insertSQL : insertValues) {
						query+=insertSQL+";";
					}
					dto.setQuery(query);
					dbclientManager.executeQuery(dto);

			} catch (Exception e) {
				throw new IllegalArgumentException("CSV 파일 저장 실패. 파일 내용을 확인해주세요\n" + e.getMessage());
			}
		}
		return new MessageVo(HttpStatus.OK.value(), count, "데이터 생성이 완료되었습니다.");
	}
}