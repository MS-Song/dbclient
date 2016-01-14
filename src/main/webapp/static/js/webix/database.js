// 최초 기동시에는 캐시를 모두 삭제 처리 한다.
webix.storage.local.clear();

// 데이터 베이스 선택 정보
var serverInfoSeq=null;
var server=null;
var schema=null;
var account=null;
var driver=null;
var tableName=null;
var autoCommit=false;
var htmlAllow=false;

function custom_checkbox(obj, common, value){
	if (value)
		return "<div class='webix_table_checkbox checked'> YES </div>";
	else
		return "<div class='webix_table_checkbox notchecked'> NO </div>";
}

// Database 관리
// 서버 & DB 선택
var select_database_popup=function(){
    webix.ui({
        view:"window",
        id:"select_database_popup",
        autowidth:true,
        position:"center",
        modal:true,
        head:{view:"button",value:"서버 선택 닫기" , click:function(){
       		$$("select_database_popup").hide();
    	}},
        body:{
        	id:"select_database_loader",
        	view:"datatable",
        	columns:[
 					{ id:"driver",		header:"Driver",	width:100, sort:"string"},
					{ id:"host",		header:"Host",		width:100, sort:"string"},
 					{ id:"hostAlias",	header:"HostAlias",	width:100, sort:"string"},
 					{ id:"schemaName",	header:"SchemaName",width:100, sort:"string"},
 					{ id:"account",		header:"Account",	width:100, sort:"string"},
 					{ id:"port",		header:"Port",		width:80,  sort:"int"},
 					{ id:"selected",	header:"선택",		width:50}
			],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			autowidth:true,
			autoheight:true,
			data:[]
        }
    }).show();

    // side menu 닫기
    $$("menu").hide();
    
    // 데이터베이스 정보를 조회한다.
	webix.ajax().get("/database/serverList.json", function(text,data){
		// 데이터베이스 정보를 획득한 경우에 테이블에 넣는다.
		if(data.json().status ==200 && null!=data.json().result){	
    		$.each(data.json().result,function(){
    			$.each(this,function(index){
					// 이미 선택되어진 값인가 검증 한다. 
					var selectedRow = "";
					if(this.serverInfoSeq == serverInfoSeq){
						selectedRow="선택";
					}
    				$$("select_database_loader").data.add({
    					id:this.serverInfoSeq,
    					driver:this.driver, 
    					host:this.host, 
    					hostAlias:this.hostAlias, 
    					schemaName:this.schemaName,
    					account:this.account,
    					port:this.port,
    					selected:selectedRow}
    				,index);
    			});
    		});
		}
		// 이벤트 부여
		$$("select_database_loader").attachEvent("onSelectChange", function(){

			var selectedRow = $$("select_database_loader").getSelectedItem();
			
			// 이미 선택된 값을 삭제 한다.
			$$("select_database_loader").eachRow( 
				function (id){
					$$("select_database_loader").getItem(id).selected="";
				}
			);
			
			// 설정 정보 저장
			serverInfoSeq=selectedRow.id;
			server=selectedRow.host;
			schema=selectedRow.schemaName;
			account=selectedRow.account;
			driver=selectedRow.driver;
			selectedRow.selected="선택";
			// 입력한 값을 재 로딩 한다.
			$$("select_database_loader").refresh();
			webix.message("데이터베이스를 선택 했습니다.");
			// 선택 창을 닫는다.
	   		window.setTimeout(function(){
				$$("select_database_popup").hide();	
			}, 500)										

			// 선택된 서버 정보를 보여준다.
			$$("toolbar").removeView("toolbar_notices");
			$$("toolbar").addView({id:"toolbar_notices",view: "label", label: selectedRow.hostAlias+" ["+server+"] 선택"},3);
			// 선택된 데이터베이스의 정보를 읽는다.
			database_info_data_load();
			
		});
	});
}

// database info cell
var database_info_cell = [
	{	 
		header:"Table",		
		rows:[{
			view : "datatable",
			id:"database_info_table_list_view", 	
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
						content:"textFilter", placeholder:"table name OR comment search",
						compare:function(value, filter, obj){ // 검색창 필터조건 구현
								if (equals(obj.tableName, filter)) return true;
								if (equals(obj.tableComment, filter)) return true;
								return false;
						}, colspan:3}], 
					adjust:true,	sort:"int"},					         
				{ id:"tableName",	header:"Name",		sort:"string", adjust:true},
				{ id:"tableComment",header:"Comment",	sort:"string", adjust:true},
			],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			navigation:true,
			autowidth:true
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"table_info_tab",
			animate:false,
			autowidth:true,
			autoheight:true,	
			cells: [
				{	
					header:"Field",
					view : "datatable",
					id:"table_info_field_list", 	
					columns:[
						{ id:"columnId",	header:["#", {	// 검색창 구현
								content:"textFilter", placeholder:"column name OR comment search",
								compare:function(value, filter, obj){ // 검색창 필터조건 구현
										if (equals(obj.columnName	, filter)) return true;
										if (equals(obj.comment, filter)) return true;
										return false;
								}, colspan:10}], 
							adjust:true,	sort:"int"},					         
	   					{ id:"columnName",		header:"name",		sort:"string", 	adjust:true},
						{ id:"nullable",		header:"null able",	sort:"string", 	adjust:true},
						{ id:"columnKey",		header:"key",		sort:"string", 	adjust:true},
						{ id:"dataType",		header:"type",		sort:"string", 	adjust:true},
						{ id:"dataLegnth",		header:"length",	sort:"int", 	adjust:true},
						{ id:"comment",			header:"comment",	sort:"string", 	adjust:true},
						{ id:"defaultValue",	header:"defualt",	sort:"string", 	adjust:true},
						{ id:"characterset",	header:"charset",	sort:"string", 	adjust:true},
						{ id:"extra",			header:"extra",		sort:"string", 	adjust:true}

					],
					data:[],
					tooltip:true,
	 				resizeColumn:true,
					autowidth:true,
					select:"row",
					navigation:true
				}, 
				{
					header:"Index",
					id:"table_info_index_list", 
					view : "datatable",
					columns:[
	   					{ id:"owner",			header:"owner",			sort:"string", 	adjust:true},
						{ id:"indexName",		header:"name",			sort:"string", 	adjust:true},
						{ id:"indexType",		header:"type",			sort:"string", 	adjust:true},
						{ id:"columnName",		header:"colName",		sort:"string", 	adjust:true},
						{ id:"columnPosition",	header:"position",		sort:"int", 	adjust:true},
						{ id:"cardinality",		header:"cardinality",	sort:"int", 	adjust:true},
						{ id:"unique",			header:"unique",		sort:"string", 	adjust:true},
						{ id:"descend",			header:"descend",		sort:"string", 	adjust:true}
	   				],
					data:[],
					tooltip:true,
	 				resizeColumn:true,
					autowidth:true,
					select:"row",
					navigation:true
				}, 
				{	
					header:"Develop",
					id:"table_info_develop_list",
					view : "datatable",
					editable:true,
					columns:[
						{ id:"columnId",	header:["SEQ", {	// 검색창 구현
								content:"textFilter", placeholder:"column name OR comment search",
								compare:function(value, filter, obj){ // 검색창 필터조건 구현
										if (equals(obj.columnName	, filter)) return true;
										if (equals(obj.comment, filter)) return true;
										return false;
								}, colspan:3}], 
							width:40,	sort:"int"},					         
	   					{ id:"columnName",		header:"name",		sort:"string", 	adjust:true},
						{ id:"comment",			header:"comment",	sort:"string", 	width:100},
						{ 
							id:"field_checkbox",		
							header:'S',
							width:40,
							template:"{common.checkbox()} ",
							editor:"checkbox"
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_set",
							header:'SET',
							width:70,
							template:function(obj){
								if(obj.field_set == undefined){
									obj.field_set="";
								}
								return '<input type="text" name="field_set[]" value="'+obj.field_set+'" style="width:50px;">';
							},
							editor:"inline-text",
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_where",
							header:'WHERE',
							width:70,
							template:function(obj){
								if(obj.field_where == undefined){
									obj.field_where="";
								}
								return '<input type="text" name="field_where[]" value="'+obj.field_where+'" style="width:50px;">';
							},
							editor:"inline-text",
						},
						{ 
							id:"field_operation",
							header:'Operation',	
							width:80,
							editor:"select",
							options:["=",">=","<=","IN()","%like","like%","%like%"]
						}
					],
					data:[],
					tooltip:function(obj, common){
						if(common.column.id=="columnId"
							|| common.column.id=="columnName"
							|| common.column.id=="comment"
						){
							return obj[common.column.id];
						} else {
							return "";
						}
				    },
	 				resizeColumn:true,
					autowidth:true,
					navigation:true
				}
			]
		}]
	},
	{	view : "datatable", 
		header:"View",			
		id:"database_info_view_list_view", 						
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		autowidth:true,
		autoheight:true
	},
	{	view : "datatable", 
		header:"Procedure",	
		id:"database_info_procedure_list_view", 
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		autowidth:true,
		autoheight:true
	},
	{	view : "datatable", 
		header:"Function",		
		id:"database_info_function_list_view",				
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		autowidth:true,
		autoheight:true
	}
];

// 데이터베이스 정보 로드 
var database_info_data_load=function(){
	// progress 추가
	webix.extend($$("database_info_table_list_view"), webix.ProgressBar);
	// 로딩 프로그레스 
	$$("database_info_table_list_view").showProgress();
	// 담기 전에 모두 지운다
	$$("database_info_table_list_view").clearAll();
	// 테이블 정보 로딩
	webix.ajax().get("/database/tableList.json",{
		server:server,
		schema:schema,
		account:account}, function(text,data){
			// 테이블 정보를 획득한 경우에 넣는다.
			if(data.json().status ==200 && null!=data.json().result){	
				$.each(data.json().result,function(){
	    			$.each(this,function(index){
	    				$$("database_info_table_list_view").data.add({
	    					seq:index,
	    					tableName:this.tableName,
	    					tableComment:this.tableComment
	    				},index);
		   			});
    			});
				// 다시 읽는다.
	    		$$("database_info_table_list_view").refresh();
	    		$$("database_info_table_list_view").hideProgress();
			}
		}
	);
	
	// change 이벤트 부여 -- 테이블 선택 값 이벤트
	$$("database_info_table_list_view").attachEvent("onSelectChange", function(){
		// 선택된 row
		var selectedRow = $$("database_info_table_list_view").getSelectedItem();
		// 테이블 명칭 저장
		if(undefined != selectedRow.tableName)
			tableName = selectedRow.tableName; 

		// progress 추가
		webix.extend($$("table_info_field_list"), webix.ProgressBar);
		webix.extend($$("table_info_develop_list"), webix.ProgressBar);
		// 로딩 프로그레스 
		$$("table_info_field_list").showProgress();
		$$("table_info_develop_list").showProgress();
		
		// 담기 전에 모두 지운다
		$$("table_info_field_list").clearAll();
		$$("table_info_develop_list").clearAll();
		// 필드 조회 
		// 캐시에 데이터가 있으면 ajax 를 실행하지 않는다.
		var cachedFieldList = webix.storage.local.get(server + "_" + schema + "_" + account +"_fields_"+ tableName);
		var cachedFieldDeveloperList = webix.storage.local.get(server + "_" + schema + "_" + account +"fieldDeveloperList"+ tableName);
		// 캐시에 존재 하면..
		if(null != cachedFieldList && cachedFieldList.length != 0){
			// 필드정보
			$$("table_info_field_list").parse(cachedFieldList);
    		$$("table_info_field_list").refresh();
    		$$("table_info_field_list").hideProgress();
    		// 개발자 화면
    		$$("table_info_develop_list").parse(cachedFieldList);
    		$$("table_info_develop_list").refresh();
    		$$("table_info_develop_list").hideProgress();    		
    		
		} else {
			webix.ajax().get("/database/fieldList.json",{
				server:server,
				schema:schema,
				account:account,
				table:tableName}, 
				function(text,data){
					// 필드 정보를 획득한 경우에 넣는다.
					if(data.json().status ==200 && null!=data.json().result){
						// datatable 저장
						$$("table_info_field_list").parse(data.json().result.fieldList);
						//cache 저장
						webix.storage.local.put(server + "_" + schema + "_" + account +"_fields_"+ tableName,$$("table_info_field_list").data.serialize());
						// 다시 읽는다.
			    		$$("table_info_field_list").refresh();
			    		$$("table_info_field_list").hideProgress();
			    		// 자동완성에 테이블 추가
			    		autoCompleteAddTables(tableName,data.json().result.fieldList);
			    		
			    		// 개발자 도구 설정
			    		// 테이블 정보를 획득한 경우에 넣는다.
						$.each(data.json().result,function(){
			    			$.each(this,function(index){
			    				$$("table_info_develop_list").data.add({
			    					columnId:this.columnId,
			    					columnName:this.columnName,
			    					comment:this.comment,
			    					dataType:this.dataType,
			    					field_checkbox:"1",
			    					field_set:"",
			    					field_where:"",
			    					field_operation:"="
			    				},index);
				   			});
		    			});
			    		$$("table_info_develop_list").refresh();
			    		$$("table_info_develop_list").hideProgress();
			    		// 캐시 저장
			    		webix.storage.local.put(server + "_" + schema + "_" + account +"fieldDeveloperList"+ tableName,$$("table_info_develop_list").data.serialize());
					}
				}
			);
		}
		
		// 인덱스 내용 조회
		// progress 추가
		webix.extend($$("table_info_index_list"), webix.ProgressBar);
		// 로딩 프로그레스 
		$$("table_info_index_list").showProgress();
		// 담기 전에 모두 지운다
		$$("table_info_index_list").clearAll();
		 
		// 캐시에 데이터가 있으면 ajax 를 실행하지 않는다.
		var cachedIndexList = webix.storage.local.get(server + "_" + schema + "_" + account +"_indexes_"+ tableName);

		if(null != cachedIndexList){
			$$("table_info_index_list").parse(cachedIndexList);
			// 다시 읽는다.
    		$$("table_info_index_list").refresh();
    		$$("table_info_index_list").hideProgress();
		} else {
			// 인덱스 조회
			webix.ajax().get("/database/indexList.json",{
				server:server,
				schema:schema,
				account:account,
				table:tableName}, 
				function(text,data){
					// 필드 정보를 획득한 경우에 넣는다.
					if(data.json().status ==200 && null!=data.json().result){
						// 인덱스는 없는 경우가 존재한다.
						if(null != data.json().result.indexList){
							$$("table_info_index_list").parse(data.json().result.indexList)
						}
						webix.storage.local.put(server + "_" + schema + "_" + account +"_indexes_"+ tableName,$$("table_info_index_list").data.serialize());
						// 다시 읽는다.
			    		$$("table_info_index_list").refresh();
			    		$$("table_info_index_list").hideProgress();
					}
				}
			);
		}
	});
};

// function 리스트
// view 리스트
// procedure

// 쿼리 입력창
var database_query_cell = [{
	header:"sql1",
	rows:[
	{
		id:"database_query_form",
		view : "form", 
		scroll:false,
		elements:[{	
			rows:[{
				cols:[ // 상단 기능 버튼 
				    {
						id:"database_query_button_select_count_pk",
						view:"button",
						value:"select count",
						tooltip:"select count(pk) from 쿼리를 생성한다. 단축키: ",
						click:"selectCountQuery",
				    }, 
				    {
						id:"database_query_button_select_all_field",
						view:"button",
						value:"select *",
						tooltip:"select * from 쿼리를 생성한다. 단축키: ",
						click:"selectAllQuery",
				    }, 
				    {
						id:"database_query_button_select_name_field",
						view:"button",
						value:"select field",
						tooltip:"select 필드명 from 쿼리를 생성한다. 단축키: ",
						click:"selectNameQuery",
				    }, 
				    {
						id:"database_query_button_insert_into",
						view:"button",
						value:"insert into",
						tooltip:"INSERT INTO 쿼리를 생성한다. 단축키: ",
				    }, 
				    {
						id:"database_query_button_insert_set",
						view:"button",
						value:"insert set",
						tooltip:"INSERT SET 쿼리를 생성한다. 단축키: ",
				    }, 
				    {
						id:"database_query_button_update_set",
						view:"button",
						value:"update set",
						tooltip:"Update SET 쿼리를 생성한다. 단축키: ",
				    }, 
				    {
						id:"database_query_button_delete",
						view:"button",
						value:"delete",
						tooltip:"DELETE 쿼리를 생성한다. 단축키: ",
				    }, 
					{
						id:"database_query_button_allow_html",
						view:"button",
						value:"allow HTML",
						tooltip:"결과에 HTML 을 허용-비허용. 단축키:",
						on:{"onItemClick":function(){
							if(htmlAllow) 	htmlAllow=false;
							else 			htmlAllow=true;

							$$("database_query_html_allow_info").define("label","HTML-Allow : "+htmlAllow);
							$$("database_query_html_allow_info").refresh();
							webix.message({ type:"error", text:"HTML Allow : " + htmlAllow +" 상태로 변경"});
						}}
					},
				    {
						id:"database_query_button_auto_commit",
						view:"button",
						value:"auto commit",
						tooltip:"Auto Commit 활성-비활성., 단축키:",
						on:{"onItemClick":function(){
							if(autoCommit) 	autoCommit=false;
							else 			autoCommit=true;

							$$("database_query_auto_commit_info").define("label","Auto-Commit : "+autoCommit);
							$$("database_query_auto_commit_info").refresh();
							webix.message({ type:"error", text:"Auto-Commit : " + autoCommit +" 상태로 변경"});
						}}
					},
				    {
						id:"database_query_button_execute",
						view:"button",
						value:"실행",
						tooltip:"입력된 쿼리를 실행한다. 단축키: Ctrl+Enter ",
						on:{"onItemClick":"executeQuery"}
					}
				]
			},
			{
				// 에디터 창
				id:"database_query_input",	
				view : "codemirror-editor",
			},
			{	// 하단 기능 버튼
				cols:[
				    {
						id:"database_developer_button_java_model",
						view:"button",
						value:"java model",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_java_hibernate_model",
						view:"button",
						value:"java H-model",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_java_model_set",
						view:"button",
						value:"java setter",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_java_model_get",
						view:"button",
						value:"java getter",
						tooltip:""
				    },				      
				    {
						id:"database_developer_button_mybatis_select",
						view:"button",
						value:"mybatis select",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_mybatis_insert",
						view:"button",
						value:"mybatis insert",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_mybatis_update",
						view:"button",
						value:"mybatis update",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_mybatis_delete",
						view:"button",
						value:"mybatis delete",
						tooltip:""
				    }, 
				    {
						id:"database_developer_button_mybatis_result",
						view:"button",
						value:"mybatis result",
						tooltip:""
				    }
				] // end cols	
			}] // end rows
		}]
	},	
	{
		cols:[{
			// 쿼리 결과 info 창
			id:"database_query_execute_info",
			view:"label", 
			label:"", 
			align:"left",
			adjust:true
		},
		{
			// HTML Allow 상태 확인
			id:"database_query_html_allow_info",
			view:"label", 
			label:"HTML-Allow : "+htmlAllow, 
			align:"right",
			adjust:true,
			tooltip : "true : result 에 HTML 이 실행됨. false : result 에 HTML 이 TEXT 형태로 표시됨." 
		},
		{
			// auto-commit 상태 확인
			id:"database_query_auto_commit_info",
			view:"label", 
			label:"Auto-Commit : "+autoCommit, 
			align:"right",
			adjust:true,
			tooltip : "true : 실행한 내용이 바로 DB에 반영됩니다. false : 실행한 내역이 DB에 반영되지 않습니다." 
		}]
	}	
] // end rows
}];

// 데이터 베이스 쿼리 실행
var executeQuery = function (){
	// 로딩 프로그레스						
	webix.extend($$("database_result_list_view"), webix.ProgressBar);
	$$("database_result_list_view").showProgress();
	// 이미 있는 내용은 모두 지운다 
	$$("database_result_list_view").config.columns = [];
	$$("database_result_list_view").clearAll();
	webix.ajax().post("/database/executeQuery.json",{
		server:server,
		schema:schema,
		account:account,
		query:encodeURIComponent($$("database_query_input").getValue()),
		  	autoCommit:autoCommit,
		  	htmlAllow:htmlAllow}, 
		function(text,data){
			// 필드 정보를 획득한 경우에 넣는다.
			if(data.json().status ==200 && null!=data.json().result){
				// 필드 정보를 갱신한다.
				// row count 가 0이면 데이터가 없다고 표시한다.
				if(data.json().result.rowCount == 0){
					$$("database_result_list_view").config.columns = [
                    	{
                   			id:"result1",
                   			header:"데이터가 없습니다.",
                   			adjust:true
                   		}
                    ];
					$$("database_result_list_view").refreshColumns();
				} else {
					// row 1개를 꺼내서 필드를 구성한다.
					var loop=0;
					$.each(data.json().result.resultList[0],function(index){
						$$("database_result_list_view").config.columns[loop]={};
						$$("database_result_list_view").config.columns[loop].id = index;
						$$("database_result_list_view").config.columns[loop].header = index;
						$$("database_result_list_view").config.columns[loop].adjust = true;
						if(!isNaN(this)){
							$$("database_result_list_view").config.columns[loop].sort="int";
						} else {
							$$("database_result_list_view").config.columns[loop].sort="string";	
						}
						loop++;
					});
					
					$$("database_result_list_view").refreshColumns();

					$$("database_result_list_view").parse(data.json().result.resultList)
		    		$$("database_result_list_view").refresh();
				}
				// 실행이 종료되면 결과를 보여준다
				$$("database_query_execute_info").define("label",'Row Count : '+data.json().result.rowCount + ', Total Time  : '+data.json().result.processTime + ' ms');
				$$("database_query_execute_info").refresh();
			} else { // 에러가 발생할 경우
				webix.message({ type:"error", text:data.json().desc });
			}
			$$("database_result_list_view").hideProgress();
		  	}
	);
	// 쿼리가 실행되는 동안 포커스를 되돌린다.
	$$("database_query_form").focus();	
}

// 결과 창
var database_result_cell = [{
	header:"result1",
	rows:[
		{	
			view : "datatable", 
			id:"database_result_list_view", 						
			columns:[],	
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			scroll:true
		}
	] // end rows
}]

// CURD
// Mybatis
// hibernate
// model 생성
// 히스토리
// create 테이블
// 인덱스

// 결과 값


/**
 * 쿼리 로그 및 즐겨찾는 쿼리 
 */
var database_developer_cell = [{
		id:"database_query_log_view",
		header:"Query Log",
		view : "datatable", 
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		autowidth:true,
		autoheight:true
	},
	{
		id:"database_query_favorities_view",
		header:"Favorites Query",
		view : "datatable", 
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		autowidth:true,
		autoheight:true
	}
];

// 자동완성 데이터 저장
var autoCompleteAddTables = function(tableName,fieldList){
	// 자동완성에 테이블을 입력한다.
	if(null==$$("database_query_input").config.hintOptions.tables[tableName]){
		// 테이블을 만든다.
		$$("database_query_input").config.hintOptions.tables[tableName]={};							
		// 필드를 만든다.
		$.each(fieldList,function(index){
			$$("database_query_input").config.hintOptions.tables[tableName][this.columnName] = null;
		});							
		$$("database_query_input").config.hintOptions.tables[tableName];	
	}
};