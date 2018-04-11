/**
 * Database 관련 유틸리티 
 * 
 */

/**
 * select count Query
 */
var selectCountQuery=function(){
	selectQuery("count");
}

/**
 * select 필드명 Query
 */
var selectNameQuery=function(){
	selectQuery("name");
}

/**
 * select * Query
 */
var selectAllQuery=function(){
	selectQuery("*");
}

/**
 * select Query
 */
var selectQuery=function(mode){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	var columnList=null;
	var whereList=null;

	var html='select ';
	switch(mode){
		case 'count':// count query 	
			columnList=getColumns('columnList');
			// PK 를 구한다.
			var pk="*";
			if(columnList.length>0){
				pk=columnList[0];
			}
			html+='\r\tcount('+pk+')';		
			break;
		case 'name'	://select query	
			columnList=getColumns('selectList');
			html+=columnList.join(",\r\t");	
			break;
		case '*'	://select all query	
			html+='\r\t* ';					
			break;
	}

	html+='\rfrom ';
	html+=database.name;
	
	whereList=getColumns('selectWhereList');
	if(whereList.length>0){
		html+='\rwhere \r\t' + whereList.join("\r\tand ");
	}

	addEditorValue(html);
	
};

/**
 * delete Query
 */
var deleteQuery=function(ret){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	var html='delete from '+database.name;
	var whereList=getColumns('whereList');

	if(whereList.length>0){
		html+='\rwhere \r\t' + whereList.join("\r\tand ");
	} else {
		webix.alert("delete query 에 where 가 없습니다.<br/>진행하시려면 확인을 눌러주세요");
	}
	if(ret){
		return html;
	} else {
		addEditorValue(html);	
	}
};

/**
 * insert into query
 */
var insertIntoQuery=function(ret){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	var columnList=getColumns('selectList');
	var intoList=getColumns('selectIntoList');
	var html='INSERT INTO '+database.name;
	
	html+='\r(';
	html+=columnList.join(",");
	html+=')\rVALUES(';
	html+=intoList.join(",");
	html+=')';
	
	if(ret){
		return html;
	} else {
		addEditorValue(html);	
	}
	
};

/**
 * update Query
 */
var updateSetQuery=function(ret){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	var setList=getColumns('selectSetList');
	var whereList=getColumns('whereList');
	
	var html='UPDATE '+database.name;
	html+='\rSET\r\t';
	html+=setList.join("\r\t,");
	if(whereList.length>0){
		html+='\rwhere \r\t' + whereList.join("\r\tand ");
	} else {
		webix.alert("update query 에 where 가 없습니다.<br/>진행하시려면 확인을 눌러주세요");
	}
	
	if(ret){
		return html;
	} else {
		addEditorValue(html);	
	}
	
}

/**
 * 테이블에서 mode 에 따라 컬럼 데이터를 획득한다.
 */
var getColumns = function (mode){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
	
  	var list= new Array();
  	var listLength = $$("table_info_develop_list").data.order.length;
  	var loop = 0;
  	
  	$.each($$("table_info_develop_list").data.pull,function(index){
  		// Template에는 있으나, Data에 없는 객체를 추가로 처리한다
  		this.field_checkbox 	= (null==this.field_checkbox)	? "1": this.field_checkbox;
  		this.field_set 			= (null==this.field_set)		? "" : this.field_set;
  		this.field_where 		= (null==this.field_where)		? "" : this.field_where;
  		this.field_operation 	= (null==this.field_operation)	? "=": this.field_operation;
  		
		switch(mode){
		case 'columnList':	// 전체 컬럼 조회
			list.push(this.columnName);
			break;
		case 'selectList':	// 체크박스에 선택된 컬럼 조회
			
			if(this.field_checkbox == undefined || this.field_checkbox == 1){
				list.push(this.columnName);
			}
			break;
		case 'selectWithComment':
			var html=aliasTable(database.name).toUpperCase();
			html+=".";
			html+=this.columnName;
			html+=" AS ";
			html+=aliasTable(database.name).toUpperCase();
			html+="_";
			html+=this.columnName; 
			html+= (listLength -1 > loop) ? ",":"";
			html+=" \t\t /*";
			html+= this.comment != null ? this.comment.replace("\n"," ") : ""; 
			html+= "*/";
			list.push(html);
			break;
		case 'selectIntoList':	// 체크박스에 선택된 set 값 into 스타일 조회
			if(this.field_checkbox == 1){
				if(this.field_set==""){ // 값이 없는 경우 prepare 스타일로
					list.push(prepareStyleConverter(this.dataType,this.columnName));					
				} else {
					list.push(columnTypeConverter(null,this.dataType,this.field_set));					
				}
			}
			break;
		case 'selectSetList':
			if(this.field_checkbox == 1){
				if(this.field_set==""){ // 값이 없는 경우 prepare 스타일로
					list.push(this.columnName + '=' + prepareStyleConverter(this.dataType,this.columnName));					
				} else {
					list.push(this.columnName + columnTypeConverter(this.field_operation,this.dataType,this.field_set));					
				}
			}
			break;
		case 'selectWhereList':	// 체크박스에 선택되고 where 에 값이 있는 것만..
			if(this.field_checkbox == 1){
				if(this.field_where != ""){
					list.push(this.columnName + columnTypeConverter(this.field_operation,this.dataType,this.field_where));
				}
			}
			break;
		case 'whereList':	// 체크박스에 선택되고 값이 없으면 prepare 스타일로..
			if(this.field_checkbox == 1){
				if(this.field_where != ""){
					list.push(this.columnName + columnTypeConverter(this.field_operation,this.dataType,this.field_where));
				} else {
					list.push(this.columnName + this.field_operation +  prepareStyleConverter(this.dataType,this.columnName));
				}
			}
			break;
		case 'commentList':
			if(this.field_checkbox == 1){
				list.push(this.comment != null ? this.comment.replace("\n"," ") : "");
			}
			break;
		case 'dataTypeList':
			if(this.field_checkbox == 1){
				list.push(this.dataType);
			}
			break;
		case 'nullAbleList':
			if(this.field_checkbox == 1){
				list.push(this.nullable);
			}
			break;
		case 'columnKeyList':
			if(this.field_checkbox == 1){
				list.push(this.columnKey);
			}
			break;
		case 'extraList':
			if(this.field_checkbox == 1){
				list.push(this.extra);
			}
			break;
		}
		loop++;
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
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("INTERGER")>=0		// 숫자인 경우
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("DATE")>=0			// 날짜 형식인 경우
  	isNotQuate=isNotQuate || type.toUpperCase().indexOf("TIME")>=0			// 날짜 형식인 경우
  	
  	if(isNotQuate==false)	quate="'";
  	
  	
	// database 종류에 따라 날짜 관련 설정을 한다.
  	if(value!=null && type.toUpperCase().indexOf("DATE")>=0 ){
  		switch (database.driver) {
		case 'oracle':
			//날짜에 0 이 들어가 있으면 잘라 낸다.
			if(value.indexOf(".") >=0){
				value = value.substring(0, value.indexOf("."));
			}
			value="to_date('"+value+"' , 'yyyy-mm-dd hh24:mi:ss')";		
			break;
  		}
  	} else if(type.toUpperCase().indexOf("TIME")>=0){
  		// TODO time 에 대한 처리는 좀더 지켜본다.
  	}
  	
  	if(value==null || value.toUpperCase()=="NULL"){
  		console.log(operationValue);
  		if(operation==null){
  			operationValue = "null";
  		} else {
  			operationValue = " is null ";	
  		}
  		
  	} else {
  	  	switch (operation) {
	  	case '=': 		operationValue= " = " + quate + value + quate + ' '; 	break;
	  	case '>=': 		operationValue= " >= " + quate + value + quate + ' ';  	break; 
	  	case '<=': 		operationValue= " <= " + quate + value + quate + ' ';	break;
	  	case '%like':	operationValue= " like '%" + value + "' ";		 		break;
	  	case 'like%':	operationValue= " like '" + value + "%' ";		 		break;
	  	case '%like%':	operationValue= " like '%" + value + "%' ";		 		break;
	  	case 'IN()':	operationValue= " IN (" + value + ") ";			 		break;
	  	default : 		operationValue= quate + value + quate;					break;
  	  	}
  		
  	}
  	return operationValue;
};

/**
 * prepare 스타일에 정의되어 있는 양식으로 필드명을 변경 한다.
 * TODO data type 처리는 차후에 잔행 한다. 
 */
var prepareStyleConverter = function(dataType,columnName){
	var prepareStyle = $$("database_developer_combo_prepare_style").getValue();
	return prepareStyle.replace("field",columnStyleConverter(columnName));
}

/**
 * 테이블 명을 이용해서 Alias 를 생성한다.
 */
var aliasTable=function(table){
  	var underbar = /_/g;
  	var regAlphabetCapital = /[a-z]/g;
  	var aliase="";
  	// 테이블 명칭에 underVar 가 존재하면
  	if(underbar.test(table)){
  		var tmp=table.split("_");
  		var tmpalias="";
  		for(var i=0;i<tmp.length;i++){
  			if(tmp[i] != null)
  				tmpalias+=tmp[i].substring(0,1).toLowerCase();
  		}
  		aliase=tmpalias;
  	} else if(table.replace(regAlphabetCapital,"").toLowerCase() != ""){
  		aliase=table.replace(regAlphabetCapital,"").toLowerCase();
  	} else{
  		if(table.substring(0,1).toLowerCase()!='t'){
  			aliase=table.substring(0,1);
  		} else{
  			aliase=table.substring(1,2);
  		}
  	}
  
  	return aliase;
};

/**
 * 에디터 창에 추가 값을 넣는다.
 */
var addEditorValue = function (addValue){
	var editor	= $$("database_query_input").getEditor();
	var doc		= $$("database_query_input").getEditor().doc;
	var retval	= $$("database_query_input").getEditor().getValue();

	// 기존 에디터 창의 내용 아래에 더한다.
	if(null==retval || ""==retval){
		retval=addValue;
	} else {
		retval=retval+";\n\n"+addValue;
	}
	editor.setValue(retval);
	editor.focus();
	try {
		doc.setCursor(doc.lastLine(),doc.getLine(doc.lastLine()).length);	
	} catch (e) {}
	
}

var addslashes = function (str) {
	return (str + '')
		.replace(/[\\"']/g, '\\$&')
		.replace(/\u0000/g, '\\0');
};

var htmlEntities=function (str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
};