/**
 * java 관련 유틸리티 
 * 
 */

/**
 * DB 타입에 매치되는 JAVA 타입을 검색한다.
 */
var columnJavaTypeSearch=function(columnTypeFull){
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

/**
 * 테이블 명칭을 java 모델에 맞게 변경한다.
 */
var tableStyleConverter=function(table){
	if(null==table) return "";
	
	var className="";
  	// 컬럼 명칭을 변경하기 위한 처리
  	if(table.indexOf('_')>=0){
  		var names = table.split('_');
  		for(var i=0;i<names.length;i++){
  			className+=names[i].substring(0,1).toUpperCase()+names[i].substring(1, names[i].length).toLowerCase();	
  		}
  	} else {
  		className+=table.substring(0,1).toUpperCase()+table.substring(1, table.length).toLowerCase();
  	}
  	
  	return className;
};

/**
 * DB 필드를 java Model 에 맞게 변경 한다.
 */
var columnStyleConverter=function(column){
	var columnName="";
	
	// 컬럼 명칭을 변경하기 위한 처리
	var postFix = "";
	if(column.indexOf('_')>=0){
		var names = column.split('_');
		
		for(var i=0;i<names.length;i++){
			if(i==0 && names[i].length==1){
				if(names[i].toLowerCase() == 'i'){
					postFix="Number";
				} else if(names[i].toLowerCase() == 'd'){
					postFix="Date";
				}					
			} else{
				if(columnName==""){
					columnName+=names[i].toLowerCase();					
				}
				else{
					columnName+=names[i].substring(0,1)+names[i].substring(1, names[i].length).toLowerCase();	
				}
			}
		}
	} else {
		columnName=column.toLowerCase();
	}
	
	return columnName+postFix;
};

var javaModel=function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	// 테이블 네임을 변경하기 때문에 치환 한다.
	var tableNameAlias=database.name;
	
	if(tableNameAlias.indexOf('t')==0){
		tableNameAlias=tableNameAlias.substring(1, tableNameAlias.length);
	}	
	
	// 컬럼명칭
	var columnList=getColumns('selectList');
	// 코멘트
	var columnCommentList=getColumns('commentList');
	// 데이터 타입
	var columnTypeFullList=getColumns('dataTypeList');
	
	var getterSetters="";
	
	
	
	var html='\n/**\n\r* Table Name '+database.name+'\n\r*/\n\r';
		html+='public class '+ tableStyleConverter(tableNameAlias) + " { \n";

	for(var i=0;i<columnList.length;i++){
		var column=columnStyleConverter(columnList[i]);
		var columnComment=columnCommentList[i];
		var dataType=columnJavaTypeSearch(columnTypeFullList[i]);
  
		html+='\n\t/**\n\t* column name : '+ columnList[i] +'\n\t* '+columnComment+'\n\t*/';
		html+='\n\tprivate '+dataType+ ' ' + column +';\n';
  
		// getset
		columnGetSet=column.substring(0,1).toUpperCase()+column.substring(1, column.length);
		getterSetters+='\n\t/**\n\t* column name : '+ columnList[i] +' \n\t* '+columnComment+' setter \n\t*/';
		getterSetters+='\n\tpublic void set'+columnGetSet+'('+dataType+' '+column+'){\n\t\tthis.'+column+' = '+column+';\n\t}';
		getterSetters+='\n\t/**\n\t* column name : '+ columnList[i] +' \n\t* '+columnComment+' getter \n\t*/';
		getterSetters+='\n\tpublic '+dataType+' get'+columnGetSet+'(){\n\t\treturn this.'+column+';\n\t}';
	}
	html+=getterSetters;
	html+="\n} ";

	
	$$("database_java_input").setValue(html);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
  };
  
  
var javaHibernateModel=function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	// 컬럼명칭
	var columnList=getColumns('selectList');
	// 코멘트
	var columnCommentList=getColumns('commentList');
	// 데이터 타입
	var columnTypeFullList=getColumns('dataTypeList');
	// null 필드 여부
	var columnIsNullAbleList=getColumns('nullAbleList');
	// pk 여부
	var columnPKList=getColumns('columnKeyList');
	// AI 여부
	var columnAIList=getColumns('extraList');

	// 테이블 네임을 변경하기 때문에 치환 한다.
	var tableNameAlias=database.name;
	
	var tableAnnotation ='@Entity'+"\n";
	tableAnnotation +='@Table(name = "'+tableNameAlias+'")'+"\n";
	tableAnnotation +='@org.hibernate.annotations.Table(comment = "'+ database.tableComment +'", appliesTo = "'+tableNameAlias+'")'+"\n";
	
	if(tableNameAlias.indexOf('t')==0){
		tableNameAlias=tableNameAlias.substring(1, tableNameAlias.length);
	}
	
	var html=tableAnnotation+'public class '+ tableStyleConverter(tableNameAlias) +"{ \n";
	// 기본 생성자
	var constructBase ='\n\t/**\n\t* 기본 생성자 \n\t*/'+'\n\tpublic '+tableNameAlias +'(){}';
  
  			// 전체 생성자
	// setter 생성자 코멘트
	var constructSetterComment= '\n\t/**\n\t* Setter 전체 생성자';
	// setter 생성자 
	var constructSetterBody= '\n\tpublic '+tableNameAlias +'(';
	// setter 생성자 파라메터
	var constructSetterParamsList = new Array();
	// setter 생성자 바디			
  	var constructSetterBodyList = new Array();
  
  	// 필수값 생성자
	// setter 생성자 코멘트
	var constructSetterRequireComment= '\n\t/**\n\t* Setter 필수값 생성자';
	// setter 생성자 
	var constructSetterRequireBody= '\n\tpublic '+tableNameAlias +'(';
	// setter 생성자 파라메터
	var constructSetterRequireParamsList = new Array();
	// setter 생성자 바디			
	var constructSetterRequireBodyList = new Array();
	
	// getter/setter
	var getterSetters="";
	
	var constructIndex=0;
	var constructRequireIndex=0;
	for(var i=0;i<columnList.length;i++){
		var column=columnStyleConverter(columnList[i]);
		var columnComment=columnCommentList[i];
		var dataType=columnJavaTypeSearch(columnTypeFullList[i]);
		var printNullAble = 'nullable=true';
		if(columnIsNullAbleList[i] == 'NO')
			printNullAble = 'nullable=false';
  
  		// construct require
		// construct All
		if(!(columnPKList[i] == 'PK' || columnPKList[i] == 'PRI')){ // pk 가 아닌 경우에만 생성한다.
			constructSetterComment+='\n\t* @param '+column;
			constructSetterParamsList[constructIndex]=dataType + ' ' + column; 
			constructSetterBodyList[constructIndex]	='this.'+column+'='+column+';';
			constructIndex++;
			
			// 필수값 생성자
			if(columnIsNullAbleList[i]=='NO'){
				constructSetterRequireComment+='\n\t* @param '+column;
				constructSetterRequireParamsList[constructRequireIndex]=dataType + ' ' + column; 
				constructSetterRequireBodyList[constructRequireIndex]	='this.'+column+'='+column+';';
  						constructRequireIndex++;
  					}
  				}
  
  				html+='\n\t/**\n\t* '+columnComment+'\n\t*/';
		if(columnPKList[i] == 'PK' || columnPKList[i] == 'PRI'){ // pk 인 경우에는 ID 를 생성
			html+='\n\t@Id';
		}
		if(columnAIList[i] == 'auto_increment'){
			html+='\n\t@GeneratedValue(strategy = GenerationType.AUTO)';
		}
		
		html+='\n\t@Column(name="' + columnList[i] +'" , columnDefinition="'+columnTypeFullList[i]+' COMMENT \''+columnCommentList[i]+'\'", '+printNullAble+')';
		html+='\n\tprivate '+dataType+ ' ' + column +';\n';
		
		// getset
		columnGetSet=column.substring(0,1).toUpperCase()+column.substring(1, column.length);
		getterSetters+='\n\t/**\n\t* column name : '+ columnList[i] +' \n\t* '+columnComment+' setter '; 
		getterSetters+='\n\t* @param '+column;
		getterSetters+='\n\t*/';
		getterSetters+='\n\tpublic void set'+columnGetSet+'('+dataType+' '+column+'){\n\t\tthis.'+column+' = '+column+';\n\t}';
		getterSetters+='\n\t/**\n\t* column name : '+ columnList[i] +' \n\t* '+columnComment+' getter';
		getterSetters+='\n\t* @return '+dataType;
		getterSetters+='\n\t*/';
		getterSetters+='\n\tpublic '+dataType+' get'+columnGetSet+'(){\n\t\treturn this.'+column+';\n\t}';
  	}
  
  	constructSetterBody+=constructSetterParamsList.join(',')+'){';
	constructSetterBody+='\n\t\t'+constructSetterBodyList.join('\n\t\t');
	constructSetterBody+='\n\t}';
	constructSetterComment+='\n\t*/';
	
	constructSetterRequireBody+=constructSetterRequireParamsList.join(',')+'){';
	constructSetterRequireBody+='\n\t\t'+constructSetterRequireBodyList.join('\n\t\t');
	constructSetterRequireBody+='\n\t}';
	constructSetterRequireComment+='\n\t*/';
	
	html+=constructBase;
	html+=constructSetterRequireComment;
	html+=constructSetterRequireBody;
	html+=constructSetterComment;
	html+=constructSetterBody;
	html+=getterSetters;
	html+="\n} ";

	$$("database_java_input").setValue(html);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};
  
var javaModelSet=function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	// 컬럼명칭
	var columnList=getColumns('selectList');
	
	var columnHtml='';
	var columnName='';
	var columnParamName='';
	var tableNameAlias=database.name;

	for(var i=0;i<columnList.length;i++){
		columnParamName=columnStyleConverter(columnList[i]);
		columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
		columnHtml+=tableStyleConverter(tableNameAlias)+'.set'+columnName+'('+columnParamName+');\n';
	}
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};
  
var javaModelGet=function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}

	// 컬럼명칭
	var columnList=getColumns('selectList');
	var columnHtml='';
	var columnName='';
	var columnParamName='';
	var tableNameAlias=database.name;
 
	for(var i=0;i<columnList.length;i++){
		columnParamName=columnStyleConverter(columnList[i]);
		columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
		columnHtml+=tableStyleConverter(tableNameAlias)+'.get'+columnName+'();\n';
	}
	
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};


var mybatisSelect = function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
	$$("database_java_input").setValue(getColumns('selectWithComment').join("\n"));
  	var columnHtml ='<sql id="selectBy'+tableStyleConverter(database.name)+'">\n';
  	columnHtml+=$$("database_java_input").getValue();
  	columnHtml+='\n</sql>';	
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};

var mybatisInsert = function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
  	var columnHtml ='<insert parameterType="'+tableStyleConverter(database.name)+'" id="insert'+tableStyleConverter(database.name)+'" statementType="PREPARED">\n';
  	columnHtml+='\n/* insert'+tableStyleConverter(database.name)+' */\n';
  	columnHtml+=insertIntoQuery(true);
  	columnHtml+='\n</insert>';
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};

var mybatisUpdate = function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
  	var columnHtml ='<update parameterType="'+tableStyleConverter(database.name)+'" id="update'+tableStyleConverter(database.name)+'" statementType="PREPARED">\n';
  	columnHtml+='\n/* update'+tableStyleConverter(database.name)+' */\n';
  	columnHtml+=updateSetQuery(true)
  	columnHtml+='\n</update>';
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};

var mybatisDelete = function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
  	var columnHtml ='<delete parameterType="'+tableStyleConverter(database.name)+'" id="delete'+tableStyleConverter(database.name)+'" statementType="PREPARED">\n';
  	columnHtml+='\n/* delete'+tableStyleConverter(database.name)+' */\n';
  	columnHtml+=deleteQuery(true);
  	columnHtml+='\n</delete>';
  	$("[name=query]").val(columnHtml);
	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};

var mybatisResultMap = function(){
	if(null==database.name){
		webix.message({ type:"error", text:"테이블을 먼저 선택해주세요"});
		return;
	}
	var columnList=getColumns('columnList');
	var columnHtml='';
	var columnName='';
	var columnParamName='';
	var tableNameAlias=database.name;

	columnHtml+='<resultMap type="'+tableStyleConverter(database.name)+'" id="resultBy'+tableStyleConverter(database.name)+'">\n';
  
	for(var i=0;i<columnList.length;i++){
		columnParamName=columnStyleConverter(columnList[i]);
		columnName=columnParamName.substring(0,1).toLowerCase()+columnParamName.substring(1, columnParamName.length);
		columnHtml+='\t<result property="'+columnName+'" column="'+ aliasTable(tableNameAlias).toUpperCase() +"_"+columnList[i]+'" />\n';
	}
	columnHtml+='</resultMap>';

	$$("database_java_input").setValue(columnHtml);
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_java_input").focus(); 
};
