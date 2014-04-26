var TIME=0;

var databaseDrivers=new Array();
databaseDrivers.push('mysql');
databaseDrivers.push('oracle');


var showLoading=function(){
	$("#loading").show();
	setTimeout(load_error, 10000);
};
var hideLoading=function(){
	$("#loading").hide();
};
var load_error=function(){
	hideLoading();
};

var inArray=function (value,array){
	var isInArray=false;
	$.each(array,function(){
		if(value==this.toString()){
			return isInArray=true;
		}
	});
	return isInArray;
};

var createSelect = function (values,textes,name,selectedValue,firsetOpetionValue,selectAppend,optionAppend){
	var sb= new Array();
	sb.push("<select name='"+name+"' " + selectAppend + " title='선택하세요'>"+"\n");

	if(null!=firsetOpetionValue)
		sb.push("<option value=''>"+firsetOpetionValue+"</option>"+"\n");
	
	$.each(values,function(loop){
		sb.push("<option value='"+this+"' ");
		if(values==selectedValue){
			sb.push(" selected ");
		}
		sb.push(optionAppend+">");
		sb.push(textes[loop]+"</option>"+"\n");
	});
	
	sb.push("</select>"+"\n");
	return sb.join("");
};

var showHost=function(){
	$.get("/database/serverList.json", null, function(data){
		var values = new Array();
		var textes = new Array();
		var name='server';
		var selectedValue = '';
		var firsetOpetionValue='--서버선택--';
		var selectAppend=' onchange="showDatabase(this.value)"  id="'+name+'" size="8" style="width:100%" ';
		var optionAppend='';
		$.each(data.result,function(){
			if(inArray(this.host,values)!=true){
				values.push(this.host);
				textes.push(this.host);
			}
		});
		$("#serverList").html(createSelect(values,textes,name,selectedValue,firsetOpetionValue,selectAppend,optionAppend));
	});
};

var showDatabase=function(serverName){
	if($("[name=server]").val()!=""  && $("[name=server]").val() != null){
		$("#serverName").html(serverName);
		$.get("/database/schemaList.json", {"server":$("[name=server]").val()}, function(data){
			var values = new Array();
			var textes = new Array();
			var name='database';
			var selectedValue = '';
			var firsetOpetionValue='--데이터베이스 선택--';
			var selectAppend=' onchange="showTables(this.value)"  id="'+name+'" size="8" style="width:100%" ';
			var optionAppend='';
			$.each(data.result,function(){
				if(inArray(this.host,values)!=true){
					values.push(this.schemaName+'|'+this.account);
					textes.push(this.schemaName+' ['+this.account+']');
				}
			});
			$("#databaseList").html(createSelect(values,textes,name,selectedValue,firsetOpetionValue,selectAppend,optionAppend));
		});
	} else{
		$("#serverName").html('');
		$("#databaseList").html('');
		$("#spanTableName").html('');
	}
	$("#databaseName").html('');
	$("#tableName").html('');
	$("#tablesList").html('');
	$("#fieldList").html('');
	$("#showCreateTable").html('');
	$("[name=query]").val('');
	$("#spanTableName").html('');
	$("#ALT2").val('');
	$("#ALT3").val('');
};

var showTables=function(databaseName){
	$("[name=mode]").val(2);
	if($("[name=database]").val()!="" && $("[name=database]").val() != null){
		// ajax 호출이 가지 않도록 리퀘스트 데이터를 저장 처리
		if($("#databaseName").data($("[name=database]").val()) == null){
			showLoading();
			$.get("/query/DatabaseViewContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
				$("#databaseName").data($("[name=database]").val(),data);
				$("#tablesList").html(data);
				hideLoading();
			});
		}
		else{
			$("#tablesList").html($("#databaseName").data($("[name=database]").val()));
		}

		$("#databaseName").html(databaseName);
	}
	else{
		$("#tablesList").html('');
	}
	$("#tableName").html('');
	$("#fieldList").html('');
	$("#showCreateTable").html('');
	$("[name=query]").val('');
	$("#spanTableName").html('');
	$("#ALT3").val('');

	// 테이블 코드 어시스트 초기화
	tableKeywordStatus = true;
	searchKeywords = [];
	resultKeyword = [];
};

var showFieldes=function(tableName){
	$("[name=mode]").val(3);
	$("[name=table]").val(tableName);
	if($("[name=table]").val()!=""   && $("[name=table]").val() != null){
		// ajax 호출이 가지 않도록 리퀘스트 데이터를 저장 처리
		if($("#tableName").data($("[name=table]").val()) == null){
			showLoading();
			$.get("/query/DatabaseViewContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
				$("#tableName").data($("[name=table]").val(),data);
				var html = data.split('{{{^^^}}}}');
				$("#fieldList").html(html[0]);
				$("#showCreateTable").html(html[1]);
				$("#spanTableName").html(html[2]);
				$("#hTableName").val(html[2]);
				hideLoading();
			});
		}
		else{

			var data = $("#tableName").data($("[name=table]").val());
			var html = data.split('{{{^^^}}}}');
			$("#fieldList").html(html[0]);
			$("#showCreateTable").html(html[1]);
			$("#spanTableName").html(" [ "+html[2]+" ]");

			// 테이블 명칭이 같지 않으면 리셋시킨다.
			if($("[name=table]").val() != html[2] ){
				 $("#tableName").data($("[name=table]").val(),null);
				 showFieldes($("[name=table]").val());
				 return;
			}
		}
		$("#tableName").html(tableName);
		// 넘겨받은 테이블 명칭을 통해 테이블의  comment 를 획득한다.
		$("[name='table'] > option").each(function(){
			if($(this).val() == tableName){
				$("#tableComment").val($(this).text().substring($(this).text().indexOf("[",0)+1,$(this).text().indexOf("]",0)));						
			}
		});
		columns('not',tableName);
	}
	else{
		$("#fieldList").html('');
		$("#tableName").html('');
		$("#showCreateTable").html('');
		$("#spanTableName").html('');
		$("#tableComment").val('');
	}
};

var showHTML=function(){
	if($("[name='htmlAllow']").val() ==1){
		$("[name='htmlAllow']").val(2);
		$("#bntHtmlAllow").val('결과HTML표현');
	}
	else{
		$("[name='htmlAllow']").val(1);
		$("#bntHtmlAllow").val('결과HTML불가');
	}
};

var excuteQuery=function(){
	$("[name=mode]").val('excute');
	showLoading();

	if($("[name=driver]").val()=='mysql'){
		setTimeout(processQuery, 1000);
		TIME=1;
	}

	// ajax 호출시 인코딩
	$("[name='query']").val(encodeURIComponent($("[name='query']").val()));
	$.post("/query/QueryExcuteContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
		var html = data.split('{{{^^^}}}}');
		printQuery(html[0]);
		$("#queryResult").html(html[1]);
		$("#queryResultTime").html(html[2]);
		$("[name='resueltHistoryData[]']:eq(0)").data("result", html[1]);
		$("[name='resueltHistoryData[]']:eq(0)").data("resultTime", html[2]);
		resizeQueryResult();
		if($("#queryResult").css("display") == 'none'){
			displayResult();
		}
		hideLoading();
		TIME=0;
	});
	// ajax 호출끝나면 디코딩
	$("[name='query']").val(decodeURIComponent($("[name='query']").val()));
};

var processQuery=function(){
	if(TIME==1){
		$("[name=mode]").val('processlist');
		var time=new Date();
		$.post("/query/QueryExcuteContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
			if(data!='noResult'){
				var html="<tr><td class=\"layout_fixed\">"+data+"</td><td width=\"120\" class=\"layout_fixed\">"+time.getDate()+'일 '+time.getHours()+'시 '+time.getMinutes()+'분 '+time.getSeconds()+'초 '+"</td><td class=\"layout_fixed\"></td></tr>";
				$("#printQueryPrepend").after(html);
			}
		});
	}
};

var explainQuery=function(){
	$("[name=mode]").val('explain');
	showLoading();
	$.post("/query/QueryExcuteContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
		var html = data.split('{{{^^^}}}}');
		printQuery(html[0]);
		$("#queryResult").html(html[1]);
		$("#queryResultTime").html(html[2]);
		$("[name='resueltHistoryData[]']:eq(0)").data("result", html[1]);
		$("[name='resueltHistoryData[]']:eq(0)").data("resultTime", html[2]);
		resizeQueryResult();
		if($("#queryResult").css("display") == 'none'){
			displayResult();
		}
		hideLoading();
	});
};

var showProcesslist=function(){
	$("[name=mode]").val('excute');
	$("[name=query]").val('show full processlist');
	showLoading();
	$.post("/query/QueryExcuteContoller.php", $("#selectDatabaseServer").serializeArray(), function(data){
		var html = data.split('{{{^^^}}}}');
		printQuery(html[0]);
		$("#queryResult").html(html[1]);
		$("#queryResultTime").html(html[2]);
		resizeQueryResult();
		if($("#queryResult").css("display") == 'none'){
			displayResult();
		}
		hideLoading();
	});
};

var resizeQueryResult=function(){
	$("#queryResult").css('height','auto');
	if(parseInt($("#queryResult").height()) >=300){
		$("#queryResult").css('height','300px');
		$("#queryResult").css('overflow','scroll');
		$("#queryResult").css('overflow-x','hidden');
	}
	else{
		$("#queryResult").css('overflow','visible');
	}
};

var selectCountQuery=function(){
	if($("#tableName").html() != ''){
		var columnList=columns('columnList',$("#tableName").html());
		var pk=columnList[0];

		$("[name=query]").val('select count('+pk+') from '+$("#tableName").html()  + columns('where',$("#tableName").html()));
	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var selectNameQuery=function(){
	if($("#tableName").html() != ''){
		if($("[name=driver]").val()=='mysql')
			$("[name=query]").val('select \n'+columns('select',$("#tableName").html())+'\nfrom '+$("#tableName").html() + ' ' + columns('where',$("#tableName").html()) + ' \nlimit 10');
		else
			$("[name=query]").val('select \n'+columns('select',$("#tableName").html())+'\n from (select \n'+columns('select',$("#tableName").html())+'\nfrom '+$("#tableName").html() + ' ' + columns('where',$("#tableName").html()) + ' ) where rownum<=10');
	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var selectInsertQuery=function(mode){
	if($("#tableName").html() != ''){

		var html="CONCAT('INSERT INTO "+$("#tableName").html();
		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		if(mode=='SET'){
			html+=" SET ',";
			var htmlList=new Array();
			for(var i=0;i<columnList.length;i++){
				htmlList[i]="'"+columnList[i]+"=', CONCAT(\"'\"," + columnList[i] + ",\"'\")";
			}
			html+=htmlList.join(", ',' ")+")";

			$("[name=query]").val('select \n'+html+'\nfrom '+$("#tableName").html() + ' ' + columns('where',$("#tableName").html()) + ' \nlimit 10');
		}
		else{
			html+=" VALUE(',";
			var htmlList=new Array();
			for(var i=0;i<columnList.length;i++){
				htmlList[i]="CONCAT(\"'\"," + columnList[i] + ",\"'\")";
			}
			html+=htmlList.join(", ',' , ") +  ", ')' )";
			$("[name=query]").val('select \n'+html+'\nfrom '+$("#tableName").html() + ' ' + columns('where',$("#tableName").html()) + ' \nlimit 10');
		}
	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var selectJoinQuery=function(){
	var count=0;
	$("[name='joinList[]'").each(function(){
		if($(this).attr("checked")=="checked")
			count++;
	});
	if(count>0){
		if($("[name=driver]").val()=='mysql')
			$("[name=query]").val('select 필드입력 from '+ columns('table', $("#tableName").html()) + ' ' + columns('where',$("#tableName").html()) +  ' limit 10');
		else
			$("[name=query]").val('select 필드입력 from (select 필드입력 from '+columns('table', $("#tableName").html()) + ' ' + columns('where',$("#tableName").html()) +  ') where rownum<=10');
	}
	else{
		printQuery('JOIN 테이블을 선택하세요');
	}
};

var selectJoinCountQuery=function(){
	var count=0;
	$("[name='joinList[]'").each(function(){
		if($(this).attr("checked")=="checked")
			count++;
	});
	if(count>0){
		$("[name=query]").val('select count(*) from '+ columns('table', $("#tableName").html())  + columns('where',$("#tableName").html()));
	}
	else{
		printQuery('JOIN 테이블을 선택하세요');
	}
};

var dbunitXML = function(){
	if($("#tableName").html() != ''){
		var html='<'+$("#tableName").html();
		var columnList=columns('columnList',$("#tableName").html());
		var setList=columns('setList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			if(setList[i] != ""){
				html+= ' ' + columnList[i] + '=' + '"'+ setList[i] +'"' ;
			}
		}
		html+=" />";
		$("[name=query]").val(html);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var phpModel=function(){
	if($("#tableName").html() != ''){
		var tableName=$("#tableName").html();
		if(tableName.indexOf('t')==0){
			tableName=tableName.substring(1, tableName.length);
		}
		var html='class '+tableName + " { \n";
		var getterSetters="";
		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		// 코멘트
		var columnCommentList=columns('columnCommentList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			var column=columnStyleConverter(columnList[i]);
			var columnComment=columnCommentList[i];
			html+='\n\t/**\n\t* '+columnComment+'\n\t*/';
			html+='\n\t private $' + column +';\n';

			// getset
			columnGetSet=column.substring(0,1).toUpperCase()+column.substring(1, column.length);
			getterSetters+='\n\t/**\n\t* '+columnComment+' setter \n\t*/';
			getterSetters+='\n\tpublic function set'+columnGetSet+'($'+column+'){\n\t\t$this->'+column+' = $'+column+';\n\t}';
			getterSetters+='\n\t/**\n\t* '+columnComment+' getter \n\t*/';
			getterSetters+='\n\tpublic function get'+columnGetSet+'(){\n\t\treturn $this->'+column+';\n\t}';
		}
		html+=getterSetters;
		html+="\n} ";
		$("[name=query]").val(html);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};


var phpModelSet=function(){
	if($("#tableName").html() != ''){
		var columnHtml='';
		var columnName='';
		var columnParamName='';
		var tableName=$("#tableName").html();
		if(tableName.indexOf('t')==0){
			// 첫글자 자르기
			tableName=tableName.substring(1, tableName.length);
			// 첫글자 소문자
			tableName=tableName.substring(0,1).toLowerCase()+tableName.substring(1, tableName.length);
		}

		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		// set 되어 있는 값
		var setList=columns('setList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			columnParamName=columnStyleConverter(columnList[i]);
			columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
			if(setList[i]!="")
				columnParamName=setList[i];
			else
				columnParamName='$'+columnParamName;
			columnHtml+='$'+tableName+'->set'+columnName+'('+columnParamName+');\n';
		}
		$("[name=query]").val(columnHtml);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var phpModelGet=function(){
	if($("#tableName").html() != ''){
		var columnHtml='';
		var columnName='';
		var columnParamName='';
		var tableName=$("#tableName").html();
		if(tableName.indexOf('t')==0){
			// 첫글자 자르기
			tableName=tableName.substring(1, tableName.length);
			// 첫글자 소문자
			tableName=tableName.substring(0,1).toLowerCase()+tableName.substring(1, tableName.length);
		}

		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			columnParamName=columnStyleConverter(columnList[i]);
			columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
			columnHtml+='$'+tableName+'->get'+columnName+'();\n';
		}
		$("[name=query]").val(columnHtml);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var javaModel=function(){
	if($("#tableName").html() != ''){
		if($("#tableName").html() != ''){
			var tableName=$("#tableName").html();
			if(tableName.indexOf('t')==0){
				tableName=tableName.substring(1, tableName.length);
			}
			var html='public class '+tableName + " { \n";
			var getterSetters="";
			// 컬럼명칭
			var columnList=columns('columnList',$("#tableName").html());
			// 코멘트
			var columnCommentList=columns('columnCommentList',$("#tableName").html());
			// 데이터 타입
			var columnTypeFullList=columns('columnTypeFullList',$("#tableName").html());


			for(var i=0;i<columnList.length;i++){
				var column=columnStyleConverter(columnList[i]);
				var columnComment=columnCommentList[i];
				var dataType=columnTypeSearch(columnTypeFullList[i]);

				html+='\n\t/**\n\t* '+columnComment+'\n\t*/';
				html+='\n\tprivate '+dataType+ ' ' + column +';\n';

				// getset
				columnGetSet=column.substring(0,1).toUpperCase()+column.substring(1, column.length);
				getterSetters+='\n\t/**\n\t* '+columnComment+' setter \n\t*/';
				getterSetters+='\n\tpublic void set'+columnGetSet+'('+dataType+' '+column+'){\n\t\tthis.'+column+' = '+column+';\n\t}';
				getterSetters+='\n\t/**\n\t* '+columnComment+' getter \n\t*/';
				getterSetters+='\n\tpublic '+dataType+' get'+columnGetSet+'(){\n\t\treturn this.'+column+';\n\t}';
			}
			html+=getterSetters;
			html+="\n} ";
			$("[name=query]").val(html);

		}
		else{
			printQuery('테이블을 선택하세요');
		}
	}
};


var javaHibernateModel=function(){
	if($("#tableName").html() != ''){
		if($("#tableName").html() != ''){
			var tableName=$("#tableName").html();

			var tableAnnotation ='@Entity'+"\n";
			tableAnnotation +='@Table(name = "'+tableName+'")'+"\n";
			tableAnnotation +='@org.hibernate.annotations.Table(comment = "'+$("#tableComment").val()+'", appliesTo = "'+tableName+'")'+"\n";
			
			if(tableName.indexOf('t')==0){
				tableName=tableName.substring(1, tableName.length);
			}
			
			var html=tableAnnotation+'public class '+tableName + " extends BaseObject { \n";
			// 기본 생성자
			var constructBase ='\n\t/**\n\t* 기본 생성자 \n\t*/'+'\n\tpublic '+tableName +'(){}';

			// 전체 생성자
			// setter 생성자 코멘트
			var constructSetterComment= '\n\t/**\n\t* Setter 전체 생성자';
			// setter 생성자 
			var constructSetterBody= '\n\tpublic '+tableName +'(';
			// setter 생성자 파라메터
			var constructSetterParamsList = new Array();
			// setter 생성자 바디			
			var constructSetterBodyList = new Array();

			// 필수값 생성자
			// setter 생성자 코멘트
			var constructSetterRequireComment= '\n\t/**\n\t* Setter 필수값 생성자';
			// setter 생성자 
			var constructSetterRequireBody= '\n\tpublic '+tableName +'(';
			// setter 생성자 파라메터
			var constructSetterRequireParamsList = new Array();
			// setter 생성자 바디			
			var constructSetterRequireBodyList = new Array();
			
			// getter/setter
			var getterSetters="";
			// 컬럼명칭
			var columnList=columns('columnList',$("#tableName").html());
			// 코멘트
			var columnCommentList=columns('columnCommentList',$("#tableName").html());
			// 데이터 타입
			var columnTypeFullList=columns('columnTypeFullList',$("#tableName").html());
			// null 필드 여부
			var columnIsNullAbleList=columns('columnIsNullAbleList',$("#tableName").html());
			// pk 여부
			var columnPKList=columns('columnPKList',$("#tableName").html());
			// AI 여부
			var columnAIList=columns('columnAIList',$("#tableName").html());
			
			var constructIndex=0;
			var constructRequireIndex=0;
			for(var i=0;i<columnList.length;i++){
				var column=columnStyleConverter(columnList[i]);
				var columnComment=columnCommentList[i];
				var dataType=columnTypeSearch(columnTypeFullList[i]);
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
				getterSetters+='\n\t/**\n\t* '+columnComment+' setter'; 
				getterSetters+='\n\t* @param '+column;
				getterSetters+='\n\t*/';
				getterSetters+='\n\tpublic void set'+columnGetSet+'('+dataType+' '+column+'){\n\t\tthis.'+column+' = '+column+';\n\t}';
				getterSetters+='\n\t/**\n\t* '+columnComment+' getter';
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
			$("[name=query]").val(html);

		}
		else{
			printQuery('테이블을 선택하세요');
		}
	}
};

var javaModelSet=function(){
	if($("#tableName").html() != ''){
		var columnHtml='';
		var columnName='';
		var columnParamName='';
		var tableName=$("#tableName").html();
		if(tableName.indexOf('t')==0){
			// 첫글자 자르기
			tableName=tableName.substring(1, tableName.length);
			// 첫글자 소문자
			tableName=tableName.substring(0,1).toLowerCase()+tableName.substring(1, tableName.length);
		}

		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		// set 되어 있는 값
		var setList=columns('setList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			columnParamName=columnStyleConverter(columnList[i]);
			columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
			if(setList[i]!="")
				columnParamName=setList[i];
			columnHtml+=tableName+'.set'+columnName+'('+columnParamName+');\n';
		}
		$("[name=query]").val(columnHtml);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var javaModelGet=function(){
	if($("#tableName").html() != ''){
		var columnHtml='';
		var columnName='';
		var columnParamName='';
		var tableName=$("#tableName").html();
		if(tableName.indexOf('t')==0){
			// 첫글자 자르기
			tableName=tableName.substring(1, tableName.length);
			// 첫글자 소문자
			tableName=tableName.substring(0,1).toLowerCase()+tableName.substring(1, tableName.length);
		}

		// 컬럼명칭
		var columnList=columns('columnList',$("#tableName").html());
		for(var i=0;i<columnList.length;i++){
			columnParamName=columnStyleConverter(columnList[i]);
			columnName=columnParamName.substring(0,1).toUpperCase()+columnParamName.substring(1, columnParamName.length);
			columnHtml+=tableName+'.get'+columnName+'();\n';
		}
		$("[name=query]").val(columnHtml);

	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var resultMap = function(){
	if($("#tableName").html() != ''){
		if($("#tableName").html() != ''){
			var columnHtml='';
			var columnName='';
			var columnParamName='';
			var tableName=$("#tableName").html();
			if(tableName.indexOf('t')==0){
				// 첫글자 자르기
				tableName=tableName.substring(1, tableName.length);
			}
			columnHtml+='<resultMap type="'+tableName+'" id="resultBy'+tableName+'">\n';

			// 컬럼명칭
			var columnList=columns('columnList',$("#tableName").html());
			for(var i=0;i<columnList.length;i++){
				columnParamName=columnStyleConverter(columnList[i]);
				columnName=columnParamName.substring(0,1).toLowerCase()+columnParamName.substring(1, columnParamName.length);
				columnHtml+='\t<result property="'+columnName+'" column="'+columnList[i]+'" />\n';
			}
			columnHtml+='</resultMap>';
			$("[name=query]").val(columnHtml);

		}
		else{
			printQuery('테이블을 선택하세요');
		}
	}
};

var prepareAll=function(mode){
	var columnList=columns('columnList',$("#tableName").html());

	if(mode=='prepare_set_all'){
		$("[name='setValue[]']").each(function(loop){
			for(var i=0;i<columnList.length;i++){
				var column=prepareStyleConverter(columnStyleConverter(columnList[i]));
				if(loop==i){
					$(this).val(column);
				}
			}
		});
	}
	else{
		$("[name='whereValue[]']").each(function(loop){
			for(var i=0;i<columnList.length;i++){
				var column=prepareStyleConverter(columnStyleConverter(columnList[i]));
				if(loop==i){
					$(this).val(column);
				}
			}
		});
	}
};


var insertQuery=function(mode){
	if($("#tableName").html() != ''){
		var sql='수정하세요 \n\nINSERT INTO '+$("#tableName").html() ;
		if(mode=='set'){
			sql+=' SET \n'+columns('set',$("#tableName").html());
		}
		else{
			sql+=' '+columns('into',$("#tableName").html());
		}
		$("[name=query]").val(sql);
	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var updateQuery=function(){
	if($("#tableName").html() != ''){
		var sql='수정하세요 \n\nUPDATE '+$("#tableName").html() ;
			sql+=' SET \n'+columns('set',$("#tableName").html());
			var where = columns('where',$("#tableName").html());
			if(where == "")
				sql+=' \n\n WHERE \n'+columns('where_all',$("#tableName").html());
			else
				sql+=where;

		$("[name=query]").val(sql);
	}
	else{
		printQuery('테이블을 선택하세요');
	}

};

var deleteQuery=function(){
	if($("#tableName").html() != ''){
		var sql='수정하세요\n\nDELETE FROM '+$("#tableName").html() ;
		var where = columns('where',$("#tableName").html());
		if(where == "")
			sql+=' \n\n WHERE \n'+columns('where_all',$("#tableName").html());
		else
			sql+=where;

		$("[name=query]").val(sql);
	}
	else{
		printQuery('테이블을 선택하세요');
	}
};

var printQuery=function(text){
	var time = new Date();
	var html ="<tr>";
		html+="<td class=\"layout_fixed\">"+text+"</td>";
		html+="<td width=\"120\" class=\"layout_fixed\">"+time.getDate()+'일 '+time.getHours()+'시 '+time.getMinutes()+'분 '+time.getSeconds()+'초 '+"</td>";
		html+="<td class=\"layout_fixed\"><input type=\"button\" value=\"결과복사\" onclick=\"copyHistoryResult(this)\" name=\"resueltHistoryData[]\"></td>";
		html+="<td class=\"layout_fixed\"><input type=\"button\" value=\"쿼리복사\" onclick=\"copyQuery(this)\"></td>";
		html+="</tr>";
	$("#printQueryPrepend").after(html);

	$("#printQuery").find('tr').each(function(loop){
		// 스크롤 되게 변경
		if(loop>10){
			if($("#printQuery").parent().css('overflow') != 'scroll' ){
				$("#printQuery").parent().height($("#printQuery").height());
				$("#printQuery").parent().css('overflow','scroll');
				$("#printQuery").parent().css('overflow-x','hidden');
			}
		}
		if(loop>50)
			$(this).remove();
	});
};

var copyQuery=function(obj){
	var query=$(obj).parent().parent().find("td:eq(0)").text();
	var start=$(obj).parent().parent().find("td:eq(0)").text().indexOf("*/");
	query=query.substring(start).replace("*/ ","");
	$("[name=query]").val(query);
};

var useTab=function(el) {
	if(9==event.keyCode){
		(el.selection=document.selection.createRange()).text="\t";
		event.returnValue=false;
	}
	else if(13==event.keyCode){
		if(el.value.indexOf(";")>=0){
			excuteQuery();
			el.blur();
		}
	}
};
var searchServer=function(text){
	if($("#serverList").data("serverList")==undefined){
		$("#server").val('');
		$("#serverList").data("serverList",$("#serverList").html());
	}

	$("#serverList").html($("#serverList").data("serverList"));
	moveFocus($("#server"));
	if(text.length>1){
		var loop=0;
		$("#server option").each(function(){
			if($(this).text().toLowerCase().indexOf(text.toLowerCase())<0){
				$(this).remove();
			}
			else{
				if(loop==0){
					$(this).attr("selected",true);
					loop++;
				}
			}
		});
	}
	if($("#server option").size() == 0){
		showDatabase('');
	}else{
		showDatabase($("#server").val());
	}
};

var searchDatabase=function(text){
	if($("#databaseList").data($("[name=server]").val())==undefined){
		$("#database").val('');
		$("#databaseList").data($("[name=server]").val(),$("#databaseList").html());
	}

	$("#databaseList").html($("#databaseList").data($("[name=server]").val()));
	moveFocus($("#database"));
	if(text.length>1){
		var loop=0;
		$("#database option").each(function(){
			if($(this).text().toLowerCase().indexOf(text.toLowerCase())<0){
				$(this).remove();
			}
			else{
				if(loop==0){
					$(this).attr("selected",true);
					loop++;
				}
			}
		});
	}
	if($("#database option").size() == 0){
		showTables('');
	}else{
		showTables($("#database").val());
	}
};
var searchTable=function(text){
	if($("#tablesList").data($("[name=database]").val())==undefined){
		$("#table").val('');
		$("#tablesList").data($("[name=database]").val(),$("#tablesList").html());
	}


	$("#tablesList").html($("#tablesList").data($("[name=database]").val()));

	moveFocus($("#table"));
	if(text.length>1){
		var loop=0;
		$("#table option").each(function(){
			if($(this).text().toLowerCase().indexOf(text.toLowerCase())<0){
				$(this).remove();
			}
			else{
				if(loop==0){
					$(this).attr("selected",true);
					loop++;
				}
			}
		});
	}
	if($("#table option").size() == 0){
		showFieldes('');
	}else{
		showFieldes($("#table").val());
	}
};

var moveFocus=function(obj){
	$(obj).keydown(function(e) {
		if(	e.keyCode==39 ||
			e.keyCode==40
		)
			$(obj).focus();
		else
			return ;
	});
/*
	if(	event.keyCode==39 ||
		event.keyCode==40
	)
		$(obj).focus();
	else
		return ;
*/
};

var displayResult=function(){
	if($("#queryResult").css("display") == 'none'){
		$("#queryResult").css("display",'block');
		$("#queryResultTime").css("display",'block');
		$("[name=bntDisplayResult]").val('결과닫기');
		$("#printAction").html('결과가 열려 있는 상태입니다.');
	}
	else{
		$("#queryResult").css("display",'none');
		$("#queryResultTime").css("display",'none');
		$("[name=bntDisplayResult]").val('결과열기');
		$("#printAction").html('결과가 닫혀 있는 상태입니다.');
	}

};

var columns=function(mode,tableName){
	var columnList= new Array();
	var columnNameList= new Array();
	var columnTypeList= new Array();
	var columnTypeFullList= new Array();
	var columnIsNullAbleList= new Array();
	var columnIndexList= new Array();
	var columnPKList= new Array();
	var columnAIList= new Array();
	var columnCommentList= new Array();

	var selectList= new Array();
	var setList = new Array();
	var whereList= new Array();
	var whereOrperatorList= new Array();

	var listCount=0;
	var indexCount=0;
	$("#table_columns").find("tr").each(function(mainLoop){
		if(mainLoop==1){
			$(this).find("th").each(function(loop){
				columnNameList[loop]=$(this).text();
			});
		}
		if(mainLoop>1){
			$(this).find("td").each(function(loop){
				if(columnNameList[loop]=='COLUMN_NAME'){
					columnList[listCount] = $(this).text();
				}
				if(columnNameList[loop] == 'COLUMN_COMMENT' || columnNameList[loop] == 'COMMENTS'){
					columnCommentList[listCount]= $(this).text();
				}
				if(columnNameList[loop]=='COLUMN_TYPE' || columnNameList[loop]=='DATA_TYPE'){
					columnTypeFullList[listCount]= $(this).text();
					var type=null;
					if($(this).text().indexOf("NUMBER")>=0 || $(this).text().indexOf("int")>=0)
						type='N';
					else
						type='S';
					columnTypeList[listCount] =type;
				}
				if(columnNameList[loop]=='IS_NULLABLE'){
					columnIsNullAbleList[listCount] = $(this).text();
				}

				if(columnNameList[loop]=='PK' || columnNameList[loop]=='COLUMN_KEY'){
					if($(this).text()=="PRI" || $(this).text()=="MUL" || $(this).text()=="UNI" || $(this).text()=="Y"){
						columnIndexList[indexCount] = columnList[listCount];
						indexCount++;
					}
					columnPKList[listCount]=$(this).text();
				}
				
				if(columnNameList[loop]=='EXTRA'){
					columnAIList[listCount]=$(this).text();	
				}

				if(columnNameList.length-4 == loop){
					selectList[listCount] = $(this).find("input").attr("checked");
				}
				if(columnNameList.length-3 == loop){
					setList[listCount] = $(this).find("input").val();
				}
				if(columnNameList.length-2 == loop){
					whereList[listCount] = $(this).find("input").val();
				}
				if(columnNameList.length-1 == loop){
					whereOrperatorList[listCount] = $(this).find("select").val();
				}
			});
			listCount++;
		}
	});

	$("#tableName").data(tableName+'_columnIndexList',columnIndexList);

	var returnValue=null;
	if(mode=='table'){
		var checkedCount=0;
		var tableString="";
		$("[name='joinList[]']").each(function(loop){
			if($(this).attr("checked")=="checked"){
				if(checkedCount==0){
					tableString+="\n " + $("[name='joinTableNameList[]']:eq("+loop+")").val() + " " + $("[name='aliasTableName[]']:eq("+loop+")").val();
				}
				else{
					var tmpcolumnIndexList = $("#tableName").data($("[name='joinTableNameList[]']:eq("+loop+")").val()+'_columnIndexList');
					var tmpColumnText=new Array();
					for(var i=0;i<tmpcolumnIndexList.length;i++){
						// 파라메터 명칭 변경
						var column=prepareStyleConverter(columnStyleConverter(tmpcolumnIndexList[i]));
						tmpColumnText[i]= $("[name='aliasTableName[]']:eq("+loop+")").val()+"."+tmpcolumnIndexList[i] + "="+column;
					}
					if($("[name='joinLeftList[]']:eq("+loop+")").attr("checked")=="checked"){
						tableString+="\n\t LEFT JOIN " + $("[name='joinTableNameList[]']:eq("+loop+")").val() + " " + $("[name='aliasTableName[]']:eq("+loop+")").val() + " ON ("+tmpColumnText.join(" AND ")+")";
					}
					else{
						tableString+="\n\t INNER JOIN " + $("[name='joinTableNameList[]']:eq("+loop+")").val() + " " + $("[name='aliasTableName[]']:eq("+loop+")").val() + " ON ("+tmpColumnText.join(" AND ")+")";
					}
				}
				checkedCount++;
			};
		});
		returnValue=tableString;
	}
	else if(mode=='select'){
		var tmpColumnList = new Array();
		var nowLoop=0;
		
		for(var i=0;i<columnList.length;i++){
			if(selectList[i]=="checked"){
				tmpColumnList[nowLoop]=columnList[i];
				nowLoop++;
			}
		}
		returnValue=tmpColumnList.join(",\n");
	}
	else if(mode=='where'){
		var tmpColumnList = new Array();
		var nowLoop=0;
		var where="";
		for(var i=0;i<columnList.length;i++){
			if(whereList[i] != ""){
				tmpColumnList[nowLoop]=columnList[i] + whereOperatorValue(whereOrperatorList[i],whereList[i],columnTypeList[i]);
				nowLoop++;
			}
		}
		if(nowLoop>0){
			where=" WHERE "+tmpColumnList.join("\n\tAND ");
		}
		returnValue=where;
	}
	else if(mode=='set'){
		var returnArray=new Array();
		var tmpColumnList = new Array();

		for(var i=0;i<columnList.length;i++){
			if(setList[i] != ""){
				if(columnTypeList[i] == 'N' || setList[i] == 'now()'  || whereList[i].indexOf('#{') == 0 || whereList[i].indexOf(':')==0)
					tmpColumnList.push(columnList[i] + "=" + setList[i]);
				else
					tmpColumnList.push(columnList[i] + "='" + setList[i]+ "'");
			} else {
				// 파라메터 명칭 변경
				var column=prepareStyleConverter(columnStyleConverter(columnList[i]));
				returnArray.push(columnList[i] + " = "+column);
			}
		}

		if(tmpColumnList.length>0){
			returnArray=tmpColumnList;
		}
		returnValue=returnArray.join(",\n");
	}
	else if(mode=='into'){
		var columnValueList	= new Array();
		var tmpColumnList 	= new Array();

		for(var i=0;i<columnList.length;i++){
			// 파라메터 명칭 변경
			var column=prepareStyleConverter(columnStyleConverter(columnList[i]));
			columnValueList[i]=column;
		}

		for(var i=0;i<columnList.length;i++){
			if(setList[i] != ""){
				if(columnTypeList[i] == 'N' || setList[i] == 'now()'  || whereList[i].indexOf('#{') == 0 || whereList[i].indexOf(':')==0)
					tmpColumnList[i]=setList[i];
				else
					tmpColumnList[i]="'" + setList[i]+ "'";
			}else{
				tmpColumnList[i]=columnValueList[i];
			}
		}

		returnValue= "\n\t(" + columnList.join(",") + ")" + "\nVALUES \n\t(" + tmpColumnList.join(",") + ")";
	}
	else if(mode=='where_all'){
		var returnArray=new Array();
		for(var i=0;i<columnList.length;i++){
			// 파라메터 명칭 변경
			var column=prepareStyleConverter(columnStyleConverter(columnList[i]));
			returnArray[i]=columnList[i] + " = "+column;
		}
		returnValue="\t"+returnArray.join("\n\tAND ");
	}
	else if(mode=='columnIndexList'){
		returnValue=columnIndexList;
	}
	else if(mode=='columnPKList'){
		returnValue=columnPKList;
	}
	else if(mode=='columnList'){
		returnValue=columnList;
	}
	else if(mode=='columnCommentList'){
		returnValue=columnCommentList;
	}
	else if(mode=='columnTypeFullList'){
		returnValue=columnTypeFullList;
	}
	else if(mode=='columnIsNullAbleList'){
		returnValue=columnIsNullAbleList;
	}
	else if(mode=='columnAIList'){
		returnValue=columnAIList;
	}
	else if(mode=='setList'){
		returnValue=setList;
	}
	else if(mode=='whereList'){
		returnValue=whereList;
	}
	else if(mode=='whereOrperatorList'){
		returnValue=whereOrperatorList;
	}
	return returnValue;
};

var copyColumns=function(){
	copy_clip(columns('select',$("#tableName").html()));
	$("#printAction").html('컬럼명칭이 클립보드에 복사되었습니다.');
};

var copyResult=function(mode){
	if(mode=='html'){
		copy_clip($("#queryResult").html());
		$("#printAction").html('결과가 클립보드에 HTML 형태로 복사되었습니다.');
	}
	else if(mode=='excel'){
		return $("#queryResult").html();
	}
	else if(mode=='insertIn'){
		var tmp = new Array();
		$("#queryResult").find(" TBODY > TR").each(function(loopTR){
			var loop=loopTR-1;
			$(this).find(">TD").each(function(loopTD){
				if(typeof tmp[loop] == 'undefined'){
					tmp[loop]=new Array();		
				}
				tmp[loop][loopTD] = $(this).text();   
			});
		});
		
		var clip=new Array();
		for(var i=0;i<tmp.length;i++){
			for(var j=0;j<tmp[i].length;j++){
				if(typeof clip[j] == 'undefined'){
					clip[j] = new Array();
				}
				if(isNaN(tmp[i][j])){
					clip[j][i]= "'" + tmp[i][j] + "'";
				} else {
					clip[j][i]= tmp[i][j];
				}
			}
		}
		var html = new Array();
		for(var i=0;i<clip.length;i++){
			html.push(clip[i].join(","));
		}
		copy_clip(html.join('\n'));
		$("#printAction").html('결과가 클립보드에 IN () 에 넣을 수 있도록 , 로 연결되어 복사되었습니다.');
	}
	else{
		var html=$("#queryResult").html();
			html=html.replace(new RegExp("<table>", "gi"),"");
			html=html.replace(new RegExp("<TABLE class=\"layout_fixed\">", "gi"),"");
			html=html.replace(new RegExp("</table>", "gi"),"");
			html=html.replace(new RegExp("<tbody>", "gi"),	"");
			html=html.replace(new RegExp("</tbody>", "gi"),	"");
			html=html.replace(/\s/g,'');
			html=html.replace(new RegExp("<tr>", "gi"),		"\n");
			html=html.replace(new RegExp("</tr>", "gi"),	"");
			html=html.replace(new RegExp("<TH class=\"layout_fixed\">", "gi")," | ");
			html=html.replace(new RegExp("<TH>", "gi")," | ");
			html=html.replace(new RegExp("</TH>", "gi"),"");
			html=html.replace(new RegExp("<TD class=\"layout_fixed\">", "gi")," | ");
			html=html.replace(new RegExp("<TD>", "gi")," | ");
			html=html.replace(new RegExp("</TD>", "gi"), "");

			//alert(html);
			copy_clip(html);
		$("#printAction").html('결과가 클립보드에 TEXT 형태로 복사되었습니다.');
	}
};

var copyHistoryResult=function(obj){
	$("#queryResult").html($(obj).data("result"));
	$("#queryResultTime").html($(obj).data("resultTime"));
	resizeQueryResult();
};

var excelDown=function(){
	$("#selectDatabaseServer").attr("method","POST");
	$("#selectDatabaseServer").attr("action",'/util/excelDown.php');
	$("[name='contents']").val(copyResult('excel'));
	$("#selectDatabaseServer").submit();
};


var displayWidthAll=function(){
	$("#queryResult").width(parseInt($("#tblSelectServerInfo").width()-11));
	if($("#queryResult").css('overflow-x')=='scroll'){
		$("#queryResult").find("TH").addClass("layout_fixed");
		$("#queryResult").find("TD").addClass("layout_fixed");
		$("#queryResult").css('overflow-x','hidden');
		$("[name='bntWidthAll']").val('넓게하기');
	}
	else{
		$("#queryResult").find("TD").removeClass("layout_fixed");
		$("#queryResult").find("TH").removeClass("layout_fixed");
		$("#queryResult").css('overflow-x','scroll');
		$("[name='bntWidthAll']").val('좁게하기');
	}
};

var checkedAll=function(obj){
	var objName=obj.name.split("_");
	$("[name^='"+objName[0]+"']").prop("checked",$(obj).prop("checked"));
};

var clearAll=function(objName){
	$("[name='"+objName+"'").val('');
};

var selectAutoCommit=function(){
	if($("[name='autoCommit']").val() == 'false'){
		$("[name='autoCommit']").val('true');
		$("#autoCommitInfo").html('AutoCommit True 상태로 데이터가 입력됩니다.');
	}
	else{
		$("[name='autoCommit']").val('false');
		$("#autoCommitInfo").html('AutoCommit False 상태로 데이터가 입력되지 안습니다.');
	}
	$("#bntAutoCommit").val('autoCommit '+$("[name='autoCommit']").val());
};

var trPosition=function(obj,position){
	// 위로 이동
	if(position=='up'){
		// 1보다 큰 경우에만..
		if(parseInt($(obj).parent().parent().attr("rowIndex"))>1){
			var tmp=$(obj).parent().parent().parent().find("tr").eq(parseInt($(obj).parent().parent().attr("rowIndex"))-1).html();
			$(obj).parent().parent().parent().find("tr").eq(parseInt($(obj).parent().parent().attr("rowIndex"))-1).html($(obj).parent().parent().html());
			$(obj).parent().parent().html(tmp);
		}
	}
	else{
		// 맨 아래가 아닌 경우에
		if(parseInt($(obj).parent().parent().attr("rowIndex")) < $(obj).parent().parent().parent().find("tr").size()-1){
			var tmp=$(obj).parent().parent().parent().find("tr").eq(parseInt($(obj).parent().parent().attr("rowIndex"))+1).html();
			$(obj).parent().parent().parent().find("tr").eq(parseInt($(obj).parent().parent().attr("rowIndex"))+1).html($(obj).parent().parent().html());
			$(obj).parent().parent().html(tmp);
		}
	}
};

var addJoin=function(obj){
	if($("#printSelectedTable").data($("[name=table]").val()) == null){
		var html ="<tr>";
			html+="<td><input type=\"checkbox\" name=\"joinList[]\" value=\"1\" onclick=\"showFieldes('"+obj.value+"')\"></td>";
			html+="<td><input type=\"checkbox\" name=\"joinLeftList[]\" value=\"1\"></td>";
			html+="<td class=\"layout_fixed\">"+obj.value+"<input type=\"hidden\" name=\"joinTableNameList[]\" value=\""+obj.value+"\"></td>";
			html+="<td title=\""+aliasTable(obj.value)+"\"><input type=\"text\" name=\"aliasTableName[]\" value=\""+aliasTable(obj.value)+"\" size=\"4\"></td>";
			html+="<td><input type=\"button\" value=\"삭제\" onclick=\"deleteJoin(this,'"+obj.value+"')\"></td>";
			html+="<td><a href=\"#\" onclick=\"trPosition(this,'up');\">▲</a><a href=\"#\" onclick=\"trPosition(this,'down');\">▼</a></td>";
			html+="</tr>";
		$("#printSelectedTable").append(html);
		$("#printSelectedTable").data($("[name=table]").val(),html);
		$("#printSelectedTable").find('tr').each(function(loop){
			// 스크롤 되게 변경
			if(loop>5){
				if($("#printSelectedTable").parent().css('overflow') != 'scroll' ){
					$("#printSelectedTable").parent().css('height','180px');
					$("#printSelectedTable").parent().css('overflow','scroll');
					$("#printSelectedTable").parent().css('overflow-x','hidden');
				}
			}
		});
	};
};

var deleteJoin=function(obj,tableName){
	$("#printSelectedTable").data(tableName,null);
	$(obj).parent().parent().remove();
};

var aliasTable=function(tableName){
	var underbar = /_/g;
	var regAlphabetCapital = /[a-z]/g;
	var aliase="";
	// 테이블 명칭에 underVar 가 존재하면
	if(underbar.test(tableName)){
		var tmp=tableName.split("_");
		var tmpalias="";
		for(var i=0;i<tmp.length;i++){
			if(tmp[i] != null)
				tmpalias+=tmp[i].substring(0,1).toLowerCase();
		}
		aliase=tmpalias;
	} else if(tableName.replace(regAlphabetCapital,"").toLowerCase() != ""){
		aliase=tableName.replace(regAlphabetCapital,"").toLowerCase();
	} else{
		if(tableName.substring(0,1).toLowerCase()!='t'){
			aliase=tableName.substring(0,1);
		} else{
			aliase=tableName.substring(1,2);
		}
	}

	return aliase;
};

/**
 * DB 필드를 java/php 스타일로 변경해준다.
 */
var columnStyleConverter=function(column){
	if(column.indexOf('n')==0){
		column=column.substring(1,2).toLowerCase()+column.substring(2, column.length);
	}
	else if(column.indexOf('f')==0){
		column=column.substring(1,2).toLowerCase()+column.substring(2, column.length);
	}
	else if(column.indexOf('s')==0){
		column=column.substring(1,2).toLowerCase()+column.substring(2, column.length);
	}
	else if(column.indexOf('dt')==0){
		column=column.substring(2,3).toLowerCase()+column.substring(3, column.length);
	}
	else if(column.indexOf('em')==0){
		column=column.substring(2,3).toLowerCase()+column.substring(3, column.length);
	}

	return column;
};

/**
 * DB 타입에 매치되는 JAVA 타입을 검색한다.
 */
var columnTypeSearch=function(columnTypeFull){
	// datatype
	var dataType="";
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
	else if(columnTypeFull.indexOf('varchar')>=0 || columnTypeFull.indexOf('char')>=0 || columnTypeFull.indexOf('text')>=0 || columnTypeFull.indexOf('enum')>=0 ){
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
	return dataType;
};

var prepareStyleConverter=function(column){
	if($("#preparedStyle").val()=='JAVA'){
		column="#{"+column+"}";
	}
	else{
		column=":"+column;
	}
	return column;
};

var whereOperatorValue=function(operator,value,type){

	var quate="";
	// 타입이 ' 를 입력하지 않는 경우
	if(type == 'N' ||  type == 'now()' || type.indexOf('#{') == 0 || type.indexOf(':')==0){
		// 숫자 타입 안에 , 가 포햄되어 있다면, in 으로 판단한다.
		if(type == 'N' && value.indexOf(',')>0){
			operationValue='IN()';
		}
	} else {
	// 타입이 ' 을 입력하는 경우
		quate="'";
	}
	
	var operationValue="";
	switch (operator) {
	case '=':
		operationValue= " = " + quate + value + quate + ' ';	
		break;
	case '>=':
		operationValue= " >= " + quate + value + quate + ' ';
		break;
	case '<=':
		operationValue= " <= " + quate + value + quate + ' ';
		break;
	case '%like':
		operationValue= " like '%" + value + "' ";
		break;
	case 'like%':
		operationValue= " like '" + value + "%' ";
		break;
	case '%like%':
		operationValue= " like '%" + value + "%' ";
		break;
	case 'IN()':
		operationValue= " IN (" + value + ") ";
		break;
	}
	return operationValue;
};

var addDate=function(obj){
	$(obj).datepicker({
		altFormat: 'yy-mm-dd',
		dateFormat: 'yy-mm-dd',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
		changeYear: true,
		inline: true
	});
	$(obj).datepicker( "show" );
};


var databaseManageForm=function(){
	$.get("/database/serverList.json", null, function(data){
		var html='<form name="databaseManageTable" id="databaseManageTable">';
			html+='	<table class="table-list">';
			html+='		<thead>';
			html+='			<tr>';
			html+='				<th>host<input type="hidden" name="mode" value="save"></th>';
			html+='				<th>schemaName</th>';
			html+='				<th>account</th>';
			html+='				<th>password</th>';
			html+='				<th>dirver</th>';
			html+='				<th>charset</th>';
			html+='				<th>port</th>';
			html+='				<th><input type="button" id="add" value="추가"></th>';
			html+='			</tr>';
			html+='		</thead>';
			html+='	<tbody id="rowBody">';;
		
		var subHtml = '';
		$.each(data.result,function(){
			html+=databaseManageGetSubHTML(this);
		});
		
		subHtml;
		html+='<tbody></table></form>';
		$( "#databasePopup" ).html(html);

		// add button click event
		$( "#add" ).click(function( event ) {
			$('#rowBody').append(databaseManageGetSubHTML(null));
		});
		
		// delete button click event
		$(document).on("click", "#rowBody input[name=delete]", function(event){
			var targetRow = $(this).parents('.subHtml');
			var inputs = targetRow.find('input[type!=button]');
			var hasValue = false;
			for(var i=0; i<inputs.length; i++) {
				if ($(inputs[i]).val() != '') {
					hasValue = true;
					break;
				}
			}
			if (hasValue && !confirm('작성된 내용이 있습니다. 삭제하시겠습니까?')) {
				return;
			}
			targetRow.remove();
		});
	});
};

var databaseManageGetSubHTML = function(obj){
	// 객체 검증
	
	var serverInfoSeq	= obj != null && obj.serverInfoSeq 	!= 'undefined' ? obj.serverInfoSeq 	: '';
	var host			= obj != null && obj.host 			!= 'undefined' ? obj.host 			: '';
	var schemaName 		= obj != null && obj.schemaName 	!= 'undefined' ? obj.schemaName 	: '';
	var account 		= obj != null && obj.account 		!= 'undefined' ? obj.account 		: '';
	var password 		= obj != null && obj.password 		!= 'undefined' ? obj.password 		: '';
	var driver	 		= obj != null && obj.driver			!= 'undefined' ? obj.driver 		: null;
	var charset	 		= obj != null && obj.charset 		!= 'undefined' ? obj.charset		: '';
	var port 			= obj != null && obj.port 			!= 'undefined' ? obj.port 			: '';
	
	
	var subHtml="";
	var dbdriver = createSelect(databaseDrivers,databaseDrivers,'dbdriver[]',driver,null,null,null,null);
	subHtml+='<tr class="subHtml">';
	subHtml+='<td>';
	subHtml+='	<input type="hidden"   name="serverInfoSeq[]" 	value="'+serverInfoSeq+'" />';
	subHtml+='	<input type="text"     name="host[]" 			value="'+host+'"		size="20" />';
	subHtml+='</td>';
	subHtml+='<td><input type="text"     name="schemaName[]" 	value="'+schemaName+'"	size="16" /></td>';
	subHtml+='<td><input type="text"     name="account[]" 		value="'+account+'"		size="14" /></td>';
	subHtml+='<td><input type="password" name="password[]" 		value="'+password+'"	size="14" /></td>';
	subHtml+='<td>'+dbdriver+'</td>';
	subHtml+='<td><input type="text"     name="charset[]" 		value="'+charset+'" 	size="14" /></td>';
	subHtml+='<td><input type="text"     name="port[]" 			value="'+port+'" 		size="4" /></td>';
	subHtml+='<td><input type="button"   name="delete" 			value="삭제" /></td>';
	subHtml+='</tr>';
	return subHtml; 
};

var changePasswordForm=function() {
    var html='<form name="changePasswordTable" id="changePasswordTable"><table class="table-list">'
            +'<tr>'
            +'<th>현재비밀번호</th>'
            +'<td><input type="password" name="current_password" id="current_password" /></td>'
            +'</tr>'
            +'<tr>'    
            +'<th>새 비밀번호</th>'
            +'<td><input type="password" name="new_password" id="new_password" /></td>'
            +'</tr>'
            +'<tr>'
            +'<th>새 비밀번호 확인</th>'
            +'<td><input type="password" name="new_password_confirm" id="new_password_confirm" /></td>'    
            +'</tr>'
            +'</table>'
            +'<input type="hidden" name="mode" value="changePW" />'
            +'</form>';
    $( "#changePasswordPopup" ).html(html);
};

var userGroupList = new Array('root' , 'user');

var userManageForm = function() {
        $.post("/controller/user/MemberManageController.php", {"mode": "list","time" : function() { return new Date().getTime(); }}, function(data) {
            var html = '<form name="userManageTable" id="userManageTable"><table class="table-list">'
                + '<thead>'
                + '<tr>'
                + '<th>account'
                + '<input type="hidden" name="mode" value="save">'
                + '</th>'
                + '<th>username</th>'
                + '<th>password</th>'        
                + '<th>group</th>'
                + '<th><input type="button" id="addUser" value="추가"></th>'
                + '</tr>'
                + '</thead>'
                + '<tbody id="rowUserBody">';
            

            var subHtml = '';
            var usergroup = new Array();
            usergroup[0] = '0';
            usergroup[1] = '1';
            
            try {
                data = eval(data);
            } catch(e) {
                alert('데이터 변환중 오류가 발생하였습니다.');
                console.log(data);
            }
            
            for (var i = 0; i < data.length; i++) {
                
                var account = data[i]['account'];
                var username = data[i]['name'];
                var group = data[i]['group'];
                
                var usergroupText = '<select name="group[]">';
                for (var j = 0; j < usergroup.length; j++) {
                    var selected = '';
                    console.log(group + ' , ' + usergroup[j]);
                    if (group == usergroup[j]) {
                        selected = 'selected';
                    }
                    usergroupText += '<option value="' + usergroup[j] + '" ' + selected + '>' + userGroupList[j] + '</option>';
                }
                usergroupText += '</select>';                
                subHtml += '<tr class="subHtml">';
                subHtml += '<td><input type="text"     name="account[]" 		value="' + account + '"></td>';
                subHtml += '<td><input type="text"     name="username[]" 		value="' + username + '"></td>';
                subHtml += '<td><input type="text"     name="password[]" 		value=""></td>';                
                subHtml += '<td>' + usergroupText + '</td>';
                subHtml += '<td><input type="button"   name="delete" 		value="삭제"></td>';
                subHtml += '</tr>';

            }
                html += subHtml;
                html += '<tbody></table></form>';            
                $("#userPopup").html(html);            
            
             $("#addUser").click(function(event) {
                $('#rowUserBody').append(userManageGetSubHTML());
            });
            
            $(document).on("click", "#rowUserBody input[name=delete]", function(event) {
                var targetRow = $(this).parents('.subHtml');
                var inputs = targetRow.find('input[type!=button]');
                var hasValue = false;
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).val() != '') {
                        hasValue = true;
                        break;
                    }
                }
                if (hasValue && !confirm('작성된 내용이 있습니다. 삭제하시겠습니까?')) {
                    return;
                }
                targetRow.remove();
            });

            
        });
};

var userManageGetSubHTML = function() {
    var usergroup = new Array();
    usergroup[0] = '0';
    usergroup[1] = '1';
    var usergroupText = '<select name="new_group[]">';
    for (var i = 0; i < usergroup.length; i++) {
        usergroupText += '<option value="' + usergroup[i] + '">' + userGroupList[i] + '</option>';
    }
    usergroupText += '</select>';

    var subHtml = '';
    
    subHtml += '<tr class="subHtml jq-UserAdd">';
    subHtml += '<td><input type="text"     name="new_account[]" 		value=""></td>';
    subHtml += '<td><input type="text"     name="new_username[]" 		value=""></td>';
    subHtml += '<td><input type="text"     name="new_password[]" 		value=""></td>';                
    subHtml += '<td>' + usergroupText + '</td>';
    subHtml += '<td><input type="button"   name="delete" 		value="삭제"></td>';
    subHtml += '</tr>';

    return subHtml;
};

$(document).ready(function(){
	// serverList
	showHost();
	
	if($("#server").val() !=""){
		showDatabase($("#server").val());
	}

	if($("#database").val() !=""){
		showTables($("#database").val());
	}
	// 오류가 있음
	if($("#table").val() !=""){
		//showFieldes($("#table").val());
	}

	
	$("#databasePopup").dialog({
		autoOpen: false,
		width: 1000,
		height: 600,
		modal: true,
		title: '데이터 베이스 관리',
		buttons: [
			{
				text: "저장",
				click: function() {
					if(confirm("변경 내용을 저장 하시겠습니까?")){
						$.post("/database/saveDatabases.json", $("#databaseManageTable").serializeArray(), function(data){
							alert(data.result);
						});
						$( this ).dialog( "close" );
					};

				}
			},
			{
				text: "취소",
				click: function() {
					if(confirm("취소 하시겠습니까?")){
						$( this ).dialog( "close" );
					};
				}
			}
		],
		open: function(event, ui) {
			$( this ).dialog("option", "height", $( window ).height());
		}
	});
        
    $("#userPopup").dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        modal: true,
        title: '사용자 관리',
        buttons: [
            {
                text: "저장",
                click: function() {
                    if (confirm("변경 내용을 저장 하시겠습니까?")) {
                        var status=0;

                        var invalid = false;
                        $(".jq-UserAdd input[type='text']").each( function () {
                            if( $(this).val() == '') {
                                invalid = true;
                            }
                        });

                        if( invalid == true ) {
                            alert('추가되는 항목은 모두 입력되어야 합니다.');
                            return;
                        }


                        $.post("/controller/user/MemberManageController.php", $("#userManageTable").serializeArray(), function(data) {
                            status = data.status;

                        }).done(function (data) {
                            if( status != 'error') {
                                $("#userPopup").dialog("close");
                            }
                        }).fail(function(xhr,txt) {
                            alert(txt);
                            console.log(txt);
                        });
                    };
                }
            },
            {
                text: "취소",
                click: function() {
                    if (confirm("취소 하시겠습니까?")) {
                        $(this).dialog("close");
                    }
                    ;
                }
            }
        ],
        open: function(event, ui) {
            $(this).dialog("option", "height", $(window).height());
        }
    });   
        
	$("#changePasswordPopup").dialog({
	    autoOpen: false,
	    width: 400,
	    height: 220,
	    modal: true,
	    title: '비밀번호변경',
	    buttons: [
	        {
	            text: "저장",
	            click: function() {
	                if (confirm("변경 내용을 저장 하시겠습니까?")) {
	                    
	                    if( $("#current_password").val() == "" ) {
	                        alert("현재 비밀번호를 입력해주세요");
	                        return false;
	                    }
	
	                    if( $("#new_password").val() == '') {
	                        alert('새 비밀번호를 입력해주세요');
	                        return false;
	                    }
	                    
	                    if( $("#new_password_confirm").val() == '' ) {
	                        alert('새 비밀번호 확인을 입력해주세요');
	                        return false;
	                    }
	                    
	                    if( $("#new_password").val() != $("#new_password_confirm").val()) {
	                        alert("새 비밀번호와 새 비밀번호 확인 값이 다릅니다.");
	                        return false;
	                    }
	
	                    $.post("/controller/user/MemberManageController.php", $("#changePasswordTable").serializeArray(), function(data) {
	
	                        console.log(data);                            
	                        alert(data.message);
	                        status = data.status;
	
	                    }).done(function () {
	                        if( status != 'error') {
	                            $("#changePasswordPopup").dialog("close");
	                        }
	                    });
	
	                };
	
	            }
	        },
	        {
	            text: "취소",
	            click: function() {
	                if (confirm("취소 하시겠습니까?")) {
	                    $(this).dialog("close");
	                }
	                ;
	            }
	        }
	    ],
	    open: function(event, ui) {
	        $(this).dialog("option", "height",220);
	    }
	});           	
	
	
	// Link to open the dialog
	$( "#databaseManager" ).click(function( event ) {
		$( "#databasePopup" ).dialog( "open" );
		event.preventDefault();
		databaseManageForm();
	});
        
    $("#userManager").click(function(event) {
        $("#userPopup").dialog("open");     
        event.preventDefault();
        userManageForm();         
    });
	
	// 단축키 이벤트
	$(document).keydown(function(e){
		// alt 를 누르는 단축키
		if($(e).attr("altKey") == true){
			if($(e).attr("keyCode")==49){
				$("#ALT1").focus();
			}
			if($(e).attr("keyCode")==50){
				$("#ALT2").focus();
			}
			if($(e).attr("keyCode")==51){
				$("#ALT3").focus();
			}
		}
		// ctrl 을 누르는 단축키
		if($(e).attr("ctrlKey") == true){
			if($(e).attr("keyCode")==48){
				excuteQuery();
			}
			if($(e).attr("keyCode")==49){
				selectCountQuery();
			}
			if($(e).attr("keyCode")==50){
				selectNameQuery();
			}
			if($(e).attr("keyCode")==51){
				selectJoinQuery();
			}
			if($(e).attr("keyCode")==52){
				dbunitXML();
			}
		}
	});
});