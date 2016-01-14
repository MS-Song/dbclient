/**
 * Database 관련 유틸리티 
 * 
 */

/**
 * select count Query
 */
var selectCountQuery=function(){
	return selectQuery("count");
}

/**
 * select 필드명 Query
 */
var selectNameQuery=function(){
	return selectQuery("name");
}

/**
 * select * Query
 */
var selectAllQuery=function(){
	return selectQuery("*");
}

/**
 * select Query
 */
var selectQuery=function(mode){
	var columnList=getColumns('columnList');
	var whereList=getColumns('whereList');

	// PK 를 구한다.
	var pk="*";
	if(columnList.length>0){
		pk=columnList[0];
	}
	
	var html='select ';
	switch(mode){
		case 'count': 	html+='count('+pk+')';		break;
		case 'name'	:	html+=columnList.join(",");	break;
		case '*'	:	html+='*';					break;
	}

	html+=' from ';
	html+=tableName;
	if(whereList.length>0){
		html+=' where ' + whereList.join(" and ");
	}
	
	// database 종류에 따라 한정자를 넣는다.
	switch (driver) {
		case 'mysql'	: 	html+=' limit 10';										break;
		case 'oracle'	: 	html='select * from ('+html+') where rownum <= 10';	break;
	}
	
//	html+= ';'; 		
	$$("database_query_input").setValue(html);
};


/**
 * 테이블에서 mode 에 따라 컬럼 데이터를 획득한다.
 */
var getColumns = function (mode){
	if(null==tableName){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
	
  	var list= new Array();
	
	$.each($$("table_info_develop_list").data.pull,function(index){
		switch(mode){
		case 'columnList':
			list.push(this.columnName);
			break;
		case 'whereList':
			if(this.field_where.trim() != ""){
				list.push(this.columnName + columnTypeConverter(this.field_operation,this.dataType,this.field_where));
			}
			break;
		}
	});
	return list;
};


/**
 * 필드의 데이터 타입을 문자/숫자/기타로 분류 한다.
 */
var columnTypeConverter=function(operation,type,value){
  	var quate="";
  	var operationValue="";
  	
  	// quate 를 붙이지 않는 타입
  	var isNotQuate = false;
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("NUMBER")>=0;		// 숫자인 경우
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("INT")>=0			// 숫자인 경우
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("INTERGER")>=0	// 숫자인 경우
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("NOW()")>=0		// 날짜
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("SYSDATE")>=0		// 날짜
  	
  	if(isNotQuate==false)	quate="'";
  	
  	switch (operation) {
	  	case '=': 		operationValue= " = " + quate + value + quate + ' '; 	break;
	  	case '>=': 		operationValue= " >= " + quate + value + quate + ' ';  	break; 
	  	case '<=': 		operationValue= " <= " + quate + value + quate + ' ';	break;
	  	case '%like':	operationValue= " like '%" + value + "' ";		 		break;
	  	case 'like%':	operationValue= " like '" + value + "%' ";		 		break;
	  	case '%like%':	operationValue= " like '%" + value + "%' ";		 		break;
	  	case 'IN()':	operationValue= " IN (" + value + ") ";			 		break;
  	}
  	return operationValue;
};

/**
 * DB 타입에 매치되는 JAVA 타입을 검색한다.
 */
var columnJavaTypeSearch=function(columnType){
	// datatype
	var dataType="";
	
	// MYSQL 
	if(columnTypeFull.indexOf('float')>=0){
		dataType='Float';
	}
	else if(columnTypeFull.indexOf('tinyint')>=0){
		dataType='Integer';
	}
	else if(columnTypeFull.indexOf('smallint')>=0){
		dataType='Integer';
	}
	else if(columnTypeFull.indexOf('int')>=0){
		if(columnTypeFull.indexOf('unsigned')>=0){
			dataType='Long';
		}
		else{
			dataType='Integer';
		}
	}
	else if(columnTypeFull.indexOf('varchar')>=0 
			|| columnTypeFull.indexOf('char')>=0 
			|| columnTypeFull.indexOf('text')>=0 
			|| columnTypeFull.indexOf('enum')>=0 ){
		dataType='String';
	}
	else if(columnTypeFull.indexOf('datetime')>=0){
		dataType='Timestamp';
	}
	else if(columnTypeFull.indexOf('date')>=0){
		dataType='Date';
	}
	else if(columnTypeFull.indexOf('time')>=0){
		dataType='Time';
	}
	// MYSQL END
	
	// ORACLE 
	if(columnTypeFull.indexOf('NUMBER')>=0){
		dataType='Integer';
	}
	else if(columnTypeFull.indexOf('DATE')>=0){
		dataType='Date';
	}
	else if(columnTypeFull.indexOf('VARCHAR2')>=0 
			|| columnTypeFull.indexOf('CHAR')>=0 
			|| columnTypeFull.indexOf('CLOB')>=0 
			|| columnTypeFull.indexOf('enum')>=0 ){
		dataType='String';
	}
	// ORACLE END
		
	return dataType;
};