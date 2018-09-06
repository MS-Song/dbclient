/**
 * 쿼리 실행 정보
 */
var executeQueryParams = {
	id:null,
	query:null,
	name:null,
	autoCommit:false,
	htmlAllow:false,
	usePLSQL:false,
	useLimit:true,
	offset:0,
	limit:10
}; 	

/**
 * DatabaseDriver 정보
 */
var drivers=null;
webix.ready(function(){
	webix.ajax().get("/database/getDatabaseDriver",function(text,data){
		if(data.json().httpStatus==200){
			drivers=data.json().contents;
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
	});
});

/**
 * Charset 정보
 */
var charset = null;
webix.ready(function(){
	webix.ajax().get("/database/getCharset",function(text,data){
		if(data.json().httpStatus==200){
			charset=data.json().contents;
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
	});
});

/**
 * Database Query 입력 Popup
 */
var database_sql_execute_popup = function(view,databaseId,sql){
	
	if(databaseId =="" || undefined==databaseId){
		webix.message({ type:"error", text:"데이터 베이스가 선택되지 않았습니다. database 를 선택해 주세요"});
		return;
		
	} else {// database 설정
		executeQueryParams.id=databaseId;
	}
	
	webix.ui({
	    view:"window",
	    id:"database_sql_execute_popup",
		width:950,
		height:750,
	    position:"center",
	    modal:true,
        head:{
        	view:"button",value:"데이터베이스 실행 닫기" , click:function(){
        		$$("database_sql_execute_popup").hide();
        	},hotkey: "esc"
    	},
	    body:{
			rows:[{
				id:"database_query_form",
				view : "form", 
				borderless:true,
				margin:0,
				scroll:false,
				elements:[{	
					rows:[{
						cols:[ // 상단 기능 버튼 
					       {
			    				id:"database_sql_execute_select_database",
			    				view:"select", 
			    				options:useDatabaseOptions,
			    				value:executeQueryParams.id,
			    				on:{"onChange":function(id,beforId){
			    					executeQueryParams.id=id;
			    					// 자동완성용 테이블 데이터 로딩
			    					database_query_all_field_load(executeQueryParams.id);
								}}
					        },   
						    {
								id:"database_query_button_auto_commit",
								view:"button",
								value:"auto commit",
								tooltip:"Auto Commit 활성-비활성., 단축키:Ctrl+7",
								on:{"onItemClick":function(){
									if(executeQueryParams.autoCommit){
										executeQueryParams.autoCommit=false;
									} else{
										executeQueryParams.autoCommit=true;
									} 
									$$("database_query_auto_commit_info").define("label","Auto-Commit : "+executeQueryParams.autoCommit);
									$$("database_query_auto_commit_info").refresh();
									webix.message({ type:"error", text:"Auto-Commit : " + executeQueryParams.autoCommit +" 상태로 변경"});
								}}
							},
						    {
								id:"database_query_button_use_limit",
								view:"button",
								value:"use limit",
								tooltip:"Limit 사용 활성-비활성., 단축키:Ctrl+8",
								on:{"onItemClick":function(){
									if(executeQueryParams.useLimit){
										executeQueryParams.useLimit=false;
									} else{
										executeQueryParams.useLimit=true;
									} 
									$$("database_query_use_limit_info").define("label","Use Limit : "+executeQueryParams.useLimit);
									$$("database_query_use_limit_info").refresh();
									webix.message({ type:"error", text:"Use Limit  : " + executeQueryParams.useLimit +" 상태로 변경"});
								}}
							},
						    {
								id:"database_query_button_kill_execute",
								view:"button",
								value:"Stop",
								tooltip:"실행중인 쿼리를 중단한다. 단축키: Ctrl+0 ",
								on:{"onItemClick":function(){
									killExecuteQuery(false);
								}}
							},					
						    {
								id:"database_query_button_execute",
								view:"button",
								value:"Execute",
								tooltip:"입력된 쿼리를 실행한다. 단축키: Ctrl+Enter ",
								on:{"onItemClick":function(){
										executeQuery(false,null);
									}
								}
							},
							{
								id:"database_query_button_input_text",
								view:"button",
								value:"SQL 사용",
								tooltip:"입력된 쿼리를 사용한다.",
								on:{"onItemClick":function(){
									$$(view).setValue($$("database_query_input").getEditor().getValue());
									$$("database_sql_execute_popup").hide();
								}}
							}
						]
					},
					{// 에디터 창
						id:"database_query_input",	
						view : "codemirror-editor-sql"
					},{ // 쿼리 정보 창
						height:27,paddingY:2, cols:[{
							// 쿼리 결과 info 창
							id:"database_query_execute_info",
							view:"label", 
							label:"Rows: 0, Time: 0 ms", 
							align:"left",
							minWidth:150,
							maxWidth:200,
							width:150
						},
						{
							view: "label",
							label:"|",
							width:10
						},	
						{ 
							id:"database_query_server_return",   
							align:"left",
							adjust:true,
							view:"text", 
							value:"",
							inputHeight:25,
							readonly:true
						},
						{
							view: "label",
							label:"|",
							width:10
						},	
						{
							// auto-commit 상태 확인
							id:"database_query_auto_commit_info",
							view:"label", 
							label:"Auto-Commit : "+executeQueryParams.autoCommit, 
							align:"right",
							width : 120,
							tooltip : "true : 실행한 내용이 바로 DB에 반영됩니다. false : 실행한 내역이 DB에 반영되지 않습니다." 
						},
						{
							view: "label",
							label:"|",
							width:10
						},	
						{
							// use limit 상태 확인
							id:"database_query_use_limit_info",
							view:"label", 
							label:"Use Limit : "+executeQueryParams.useLimit, 
							align:"right",
							width : 90,
							tooltip : "true : 데터를 limit 개수 100개 만큼씩 가져 옵니다. false : 전체 데이터를 조회 합니다." 
						}]
					}] // end rows
				}] // end elements
			},	
			{ view:"resizer", id:"screen_heighter"},
			{
				view:"tabview",
				id:"database_result_list_tab",
				animate:false,
				cells: [{	
					header:"result1",
					view : "datatable", 
					id:"database_result_list_view", 						
					columns:[],	
					data:[],
					tooltip:true,
					select:"row",
					resizeColumn:true,
					scroll:true,
					multiselect:true,
					clipboard:"selection",
					dataLimit:"",
					dataOffset:"",
					dataPage:1,
					executedQuery:"",
					executedTime:0,
					isDataLoading:false,
				}] // end cells			
			},
			{
				// footer 구성
				cols:[
					{
						id:"editerSelectedStartLine",
						view: "label",
						align:"right",
						label:"",
					},
					{
						view: "label",
						label:"|",
						width:10
					},				
					{
						id:"editorCurrentLine",					
						view: "label",
						align:"right",
						label:"1:0",
					},
					{
						view: "label",
						align:"right",
						label:"",
					}
				] // end cols				
			}] // end rows
	    }
	}).show();

	// 에디터가 생성되는 시간을 감안하여 지연로딩 시킨다.
	editorLazyLoading(sql);
}

//항목 로딩을 지연시키기 위한 처리 
var editorLazyLoading = function(sql){
	if(undefined==$$("database_query_input").getEditor()){
		setTimeout(() => {editorLazyLoading(sql)}, 100);		
	} else {
		// 에디터에 SQL 설정
		$$("database_query_input").getEditor().setValue(sql);
		// 자동완성 데이터 로딩
		database_query_all_field_load(executeQueryParams.id);
	}
}

/**
 * 에디터에 존재하는 실행 하려고하는 쿼리 
 * TODO - 에디터를 동적으로 가져오는 방법에 대한 연구가 필요하다.
 */
var getQueryString = function(){
	var editor	= $$("database_query_input").getEditor();
	var doc 	= editor.doc;
	var Pos		= CodeMirror.Pos;
	var cmpPos 	= CodeMirror.cmpPos;
	var retQuery= editor.getValue();
	if(executeQueryParams.usePLSQL){
		// PLSQL인 경우 별도 가공을 하지 않고 PLSQL 모드로 변경 한다.
	} else {
		if(""!=doc.getSelection()){ // drag 되어 있는 쿼리가 있으면, 해당 부분만 가져온다.
			retQuery=doc.getSelection().replace(/;/g,"");
		} else { // 쿼리의 포인터를 찾아 해당 위치에서 실행 한다.  
		    var separator = [];
		    var validRange = {
		      start: Pos(0, 0),
		      end: Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).length)
		    };

		    var indexOfSeparator = retQuery.indexOf(";");
		    while(indexOfSeparator != -1) {
		    	separator.push(doc.posFromIndex(indexOfSeparator+1));
		    	indexOfSeparator = retQuery.indexOf(";", indexOfSeparator+1);
		    }
		    
		    separator.unshift(Pos(0, 0));
		    separator.push(Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).text.length));
		
		    //find valid range
		    var prevItem = null;
		    var current = editor.getCursor();
		    
		    // line 과 ch 가 둘다 0인 경우에 오류가 발생하여 정정한다.
		    if(current.line == 0 && current.ch==0){
		    	current.ch++;
		    }

		    for (var i = 0; i < separator.length; i++) {
		    	if ((prevItem == null || cmpPos(current, prevItem) > 0) && cmpPos(current, separator[i]) <= 0) {
		    		validRange = {start: prevItem, end: separator[i]};
		    		break;
		    	}
		    	prevItem = separator[i];
		    }

		    var queries = doc.getRange(validRange.start, validRange.end, false);
	        doc.setSelection(validRange.start, validRange.end);

	        // 쿼리의 시작이 ; 로 시작할 경우
	        if(null!=queries && queries.length>0){
	        	if(queries[0].indexOf(";")>=0){
	        		queries[0].substring(parseInt(queries[0].indexOf(";")+1),queries[0].length);	
	        	}
	        }
	        retQuery=queries.join("\n").replace(/;/g,"");
		}		
	}
	return retQuery;
}

//실행중인 쿼리 중단
var killExecuteQuery = function (){
	webix.ajax().post("/database/killExecuteQuery",{executeQueryParams}, 
		function(text,data){
			if(data.json().httpStatus ==200){
				webix.message("쿼리가 실행 중지 되었습니다. ");
			} else { // 실패
				errorControll(data.json());
			}
	});
	$$("database_result_list_view").hideProgress();
	null!=timeInterval ? clearInterval(timeInterval) : timeInterval=null;
}

var executeQuery = function (){
	resultView = $$("database_result_list_view");
	// 실행 쿼리 파라메터 설정
	executeQueryParams.query=getQueryString();
	// 데이터를 조회하여 넣는다.
	getDataParseView("/database/executeQuery", executeQueryParams, resultView.config.id,true,false,false);
}

// 결과 창
var database_result_cell = [];