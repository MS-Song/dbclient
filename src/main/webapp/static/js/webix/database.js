// 데이터 베이스 선택 정보
var serverInfo = {
		serverInfoSeq:null,
		server:null,
		schema:null,
		account:null,
		driver:null,
		tableName:null,
		tableComment:null,
		autoCommit:false,
		htmlAllow:false,
		useCache:true,
}
/**
 * serverInfo 객체에 파라메터를 변조하기 위해 복제 한다.
 */
var copyServerInfo = function(serverInfoObject){
	var obj={};
	obj.serverInfoSeq 	=  serverInfoObject.serverInfoSeq; 	
	obj.server			=  serverInfoObject.server;			
	obj.schema			=  serverInfoObject.schema;			
	obj.account			=  serverInfoObject.account;			
	obj.driver			=  serverInfoObject.driver;			
	obj.tableName		=  serverInfoObject.tableName;		
	obj.autoCommit		=  serverInfoObject.autoCommit;		
	obj.htmlAllow		=  serverInfoObject.htmlAllow;		
	obj.useCache		=  serverInfoObject.useCache;		
	return obj;
}


// 데이터베이스 드라이버 정보
var drivers=null;
// database driver 리스트 조회
webix.ajax().get("/database/getDatabaseDriver.json",function(text,data){
	if(data.json().status !=200){
		// validate 메세지 
		webix.message({ type:"error", text:data.json().desc});
	} else { // database driver loading
		drivers=data.json().result.databaseDriverList;
	}
});

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
        	        { id:"serverInfoSeq",	header:"serverInfoSeq",	width:100, sort:"int"},
 					{ id:"driver",			header:"Driver",		width:100, sort:"string"},
					{ id:"host",			header:"Host",			width:100, sort:"string"},
 					{ id:"hostAlias",		header:"HostAlias",		width:100, sort:"string"},
 					{ id:"schemaName",		header:"SchemaName",	width:100, sort:"string"},
 					{ id:"account",			header:"Account",		width:100, sort:"string"},
 					{ id:"port",			header:"Port",			width:80,  sort:"int"},
 					{ id:"selected",		header:"선택",			width:50,
 						template:function(obj){
 							return  obj.serverInfoSeq == serverInfo.serverInfoSeq ? "선택":"";
 						}
 					}
			],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			autowidth:true,
			autoheight:true,
			data:[],
	    	on:{
	    		onSelectChange:function(id){
	    			var selectedRow = $$("select_database_loader").getSelectedItem();
	    			
	    			// 이미 선택된 값을 삭제 한다.
	    			$$("select_database_loader").eachRow( 
	    				function (id){
	    					$$("select_database_loader").getItem(id).selected="";
	    				}
	    			);
	    			
	    			// 설정 정보 저장
	    			serverInfo.serverInfoSeq=selectedRow.serverInfoSeq;
	    			serverInfo.server=selectedRow.host;
	    			serverInfo.schema=selectedRow.schemaName;
	    			serverInfo.account=selectedRow.account;
	    			serverInfo.driver=selectedRow.driver;
	    			serverInfo.tableName=null;
	    			serverInfo.tableComment=null;
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
	    			$$("toolbar").addView({id:"toolbar_notices",view: "label", label: selectedRow.hostAlias+" ["+serverInfo.server+"] 선택"},3);
	    			
	    			// 캐시 삭제 버튼을 노출 한다.
	    			$$("toolbar").removeView("toolbar_cache_remove");
	    			$$("toolbar").addView({id:"toolbar_cache_remove",
	    				view:"button", 
	    				value:"캐시삭제" , 
	    				type:"form",
	    				width:100,
	    				on:{"onItemClick":function(){// 캐시 삭제 실행
	    					webix.ajax().get("/database/deleteCache.json", function(text,data){
	    						if(data.json().status ==200){
	    							// local storage 의 캐시도 삭제 한다.
	    							webix.storage.local.clear();
	    							webix.message("캐시 삭제가 완료 되었습니다.");
	    							// 1초 후에 리로드 한다.	
	    							window.setTimeout(function(){
	    								document.location = document.location.href;	
	    							}, 2000)										
	    						} else { // 캐시 삭제 실패
	    							// validate 메세지 
	    							var message = data.json().desc.split("\n");
	    							webix.message({ type:"error", text:message[0].replace("="," ") });
	    						}
	    					});
	    				}
	    			}});
	    			// 선택된 데이터베이스의 정보를 읽는다.
	    			database_info_data_load();
	            }}
	        }
    }).show();
	// 사이드 메뉴가 열려 있을 경우에만 닫는다.
	if($$("menu").isVisible()) $$("menu").hide();
	// 데이터베이스 정보를 조회한다.
	getDataParseView("/database/serverList",serverInfo,"select_database_loader",false,true,false);
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
					adjust:true,	sort:"int",
					template:"#tableSeq#"
				},					         
				{ id:"tableName",	header:"Name",		sort:"string", adjust:true},
				{ id:"tableComment",header:"Comment",	sort:"string", adjust:true},
			],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			navigation:true,
	    	on:{	// 테이블 아이템 클릭시에 동작
	    		onItemClick:function(id){
    				database_info_field_data_load();
	    		}
	    	}
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"table_info_tab",
			animate:false,
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
							header:'<input type="checkbox" onClick="develop_field_clear(\'checkbox\',this);" checked />',
							width:40,
							editor:"checkbox",
							template : function(obj){
								var html='<input type="checkbox" name="field_checkbox[]" value="1"' ;
								if(undefined == obj.field_checkbox){
									obj.field_checkbox=1;
								}
								if(obj.field_checkbox == 1) {
									html +='checked />';	
								}else {
									html +='/>';
								}
								
								return html; 
							},
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_set",
							header:['SET','<input type="button" value="reset" onClick="develop_field_clear(\'set\');" />'],
							width:70,
							template : function(obj){
								var html='<input type="text" name="field_set[]" value="';
								if(undefined == obj.field_set || null == obj.field_set){
									obj.field_set="";
								}
								html +=obj.field_set+'" style="width:50px;" />';
								return html; 
							},
							editor:"inline-text",
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_where",
							header:['WHERE','<input type="button" value="reset" onClick="develop_field_clear(\'where\');" />'],
							width:70,
							template : function(obj){
								var html='<input type="text" name="field_where[]" value="';
								if(undefined == obj.field_where || null == obj.field_where){
									obj.field_where="";
								}
								html +=obj.field_where+'" style="width:50px;" />';
								return html; 
							},
							editor:"inline-text",
						},
						{ 
							id:"field_operation",
							header:['Operation','<input type="button" value="reset" onClick="develop_field_clear(\'operation\');" />'],	
							width:80,
							editor:"select",
							options:["=",">=","<=","IN()","%like","like%","%like%"],
							template : function(obj){
								if(undefined==obj.field_operation || null == obj.field_operation){
									obj.field_operation="=";
								} 
								return obj.field_operation;
							},

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
					navigation:true
				},
				{	
					header:"DDL",
					view : "textarea",
					id:"show_create_table_source", 	
					adjust:true,
				}
			]
		}]
	},
	{	
		header:"View",		
		rows:[{
			view : "datatable", 
			id:"database_info_view_list_view", 						
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:4}], 
					adjust:true,	
					sort:"int",
					template:"#seq#"
				},					         
				{ 	
					id:"name",
					adjust:true,	
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(serverInfo.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},
				{ 
					id:"lastUpdateTime",
					header:"last update",
					sort:"string",
					adjust:true
				},
				{ 
					id:"comments",
					header:"comments",
					sort:"string",
					adjust:true
				}
			],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_view_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;

	    			getDataParseProperty("/database/viewDetailLis",param,"view_info_detail_property");
	    			getDataParseTextarea("/database/viewSourceList",param,"view_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_view_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;
	    			getDataParseEditor("/database/viewSourceList",param,"editText");
	    		}
	    	}
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"view_info_tab",
			animate:false,
			cells: [{
				header:"View Property",
				view : "property",
				id:"view_info_detail_property", 	
				tooltip:true,
				scroll:"xy",
				navigation:true,
				columns:[],
				elements:[]
			},
			{	
				header:"View Source",
				view : "textarea",
				id:"view_info_source", 	
				adjust:true,
			}]
		}]
	},
	{	
		
		header:"Procedure",		
		rows:[{		
			view : "datatable", 
			id:"database_info_procedure_list_view", 
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:3}], 
					adjust:true,	
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					adjust:true,	
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(serverInfo.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},
				{ 
					id:"lastUpdateDate", 
					adjust:true,	
					sort:"string" 
				}
			],	
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_procedure_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;

	    			getDataParseProperty("/database/procedureDetailList",param,"procedure_info_detail_property");
	    			getDataParseTextarea("/database/procedureSourceList",param,"procedure_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_procedure_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;
	    			getDataParseEditor("/database/procedureSourceList",param,"editText");
	    		}
			}
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"procedure_info_tab",
			animate:false,
			cells: [{
				header:"Procedure Property",
				view : "property",
				id:"procedure_info_detail_property", 	
				tooltip:true,
				scroll:"xy",
				navigation:true,
				columns:[],
				elements:[]
			},
			{	
				header:"Procedure Source",
				view : "textarea",
				id:"procedure_info_source", 	
				adjust:true,
			}]
		}]
	},
	{	
		header:"Function",		
		rows:[{		
			view : "datatable", 
			id:"database_info_function_list_view",				
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:3}], 
					adjust:true,	
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					adjust:true,	
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(serverInfo.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},
				{ 
					id:"lastUpdateDate", 
					adjust:true,	
					sort:"string" 
				}
			],	
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_function_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;

	    			getDataParseProperty("/database/functionDetailList",param,"function_info_detail_property");
	    			getDataParseTextarea("/database/functionSourceList",param,"function_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_function_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;
	    			getDataParseEditor("/database/functionSourceList",param,"editText");
	    		}

	    	},
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"function_info_tab",
			animate:false,
			cells: [{
				header:"Function Property",
				view : "property",
				id:"function_info_detail_property", 	
				tooltip:true,
				scroll:"xy",
				navigation:true,
				columns:[],
				elements:[]
			},
			{	
				header:"Function Source",
				view : "textarea",
				id:"function_info_source", 	
				adjust:true,
			}]
		}]
	},
	{	
		header:"Trigger",		
		rows:[{			
			view : "datatable", 
			id:"database_info_trigger_list_view",				
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:3}], 
					adjust:true,	
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					adjust:true,	
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(serverInfo.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},
				{ 
					id:"lastUpdateDate", 
					adjust:true,	
					sort:"string" 
				}
			],	
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_trigger_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;

	    			getDataParseProperty("/database/triggerDetailList",param,"trigger_info_detail_property");
	    			getDataParseTextarea("/database/triggerSourceList",param,"trigger_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_trigger_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;
	    			getDataParseEditor("/database/triggerSourceList",param,"editText");
	    		}
	    	},
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"trigger_info_tab",
			animate:false,
			cells: [{
				header:"Trigger Property",
				view : "property",
				id:"trigger_info_detail_property", 	
				tooltip:true,
				scroll:"xy",
				navigation:true,
				columns:[],
				elements:[]
			},
			{	
				header:"Trigger Source",
				view : "textarea",
				id:"trigger_info_source", 	
				adjust:true,
			}]
		}]			
	},
	{	
		header:"Sequence",		
		rows:[{				
			view : "datatable", 
			id:"database_info_sequence_list_view",				
			columns:[
				{ id:"seq",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:6}], 
					adjust:true,	
					sort:"int",
					template:"#seq#"
				},					         
				{ 	
					id:"name",
					adjust:true,	
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(serverInfo.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},			         
				{ id:"lastValue",		header:"lastValue",		sort:"int", 	adjust:true},
				{ id:"minValue",		header:"minValue",		sort:"int", 	adjust:true},
				{ id:"maxValue",		header:"maxValue",		sort:"int", 	adjust:true},
				{ id:"incrementBy",		header:"incrementBy",	sort:"int", 	adjust:true}
			],	
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_sequence_list_view").getSelectedItem();
	    			var param = copyServerInfo(serverInfo);
	    			param.name=selectedRow.name;

	    			getDataParseProperty("/database/sequenceDetailList",param,"sequence_info_detail_property");
	    		}
			}
		},
		{ view:"resizer"},
		{
			view:"tabview",
			id:"sequence_info_tab",
			animate:false,
			cells: [{
				header:"Sequence Property",
				view : "property",
				id:"sequence_info_detail_property", 	
				tooltip:true,
				scroll:"xy",
				navigation:true,
				columns:[],
				elements:[]
			}]
		}]
	}
];

// 데이터베이스 정보 로드 
var database_info_data_load=function(){
	// 테이블 리스트 조회
	getDataParseView("/database/tableList",serverInfo,"database_info_table_list_view",false,true,false);
	// view list 조회
	getDataParseView("/database/viewList",serverInfo,"database_info_view_list_view",false,true,false);	
	// procedure list 조회
	getDataParseView("/database/procedureList",serverInfo,"database_info_procedure_list_view",false,true,false);
	// function list 조회
	getDataParseView("/database/functionList",serverInfo,"database_info_function_list_view",false,true,false);
	// trigger list 조회
	getDataParseView("/database/triggerList",serverInfo,"database_info_trigger_list_view",false,true,false);
	// sequence List 로딩
	getDataParseView("/database/sequenceList",serverInfo,"database_info_sequence_list_view",false,true,false);
	// 자동완성용 테이블 데이터 로딩
	database_query_all_field_load();
	// 자동완성 이벤트 호출
	autoCompleteEvent();
};

// 테이블 정보 로딩
var database_info_field_data_load = function(){
	// 선택된 row
	var selectedRow = $$("database_info_table_list_view").getSelectedItem();
	// 테이블 명칭 저장
	try {
		if(undefined != selectedRow.tableName) {
			serverInfo.tableName = selectedRow.tableName;
			serverInfo.tableComment = selectedRow.tableComment;
		}
	} catch (e) {
		$$("database_info_table_list_view").hideProgress();
	}
	
	// 필드 리스트
	getDataParseView("/database/fieldList",serverInfo,"table_info_field_list",false,true,false);
	// 개발자 도구 리스트
	getDataParseView("/database/fieldList",serverInfo,"table_info_develop_list",false,true,false);
	// 인덱스 리스트
	getDataParseView("/database/indexList",serverInfo,"table_info_index_list",false,true,false);
	// create table view
	getDataParseTextarea("/database/showCreateTable",serverInfo,"show_create_table_source","showCreateTable");

};


// develop 기능
var develop_field_clear=function(mode,obj){
	$.each($$("table_info_develop_list").data.pull,function(index){
		switch(mode){
		case 'checkbox': 
			if(obj.checked==true) 	this.field_checkbox=1;
			else 					this.field_checkbox=0;
			break;
		case 'set': this.field_set="";break;
		case 'where': this.field_where="";break;
		case 'operation': this.field_operation="=";break;
		}
	
	});
	$$("table_info_develop_list").refresh();
	$$("table_info_develop_list").hideProgress();
};

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
						tooltip:"select count(pk) from 쿼리를 생성한다. 단축키: Alt+1 ",
						click:"selectCountQuery"
				    }, 
				    {
						id:"database_query_button_select_all_field",
						view:"button",
						value:"select *",
						tooltip:"select * from 쿼리를 생성한다. 단축키: Alt+2",
						click:"selectAllQuery"
				    }, 
				    {
						id:"database_query_button_select_name_field",
						view:"button",
						value:"select field",
						tooltip:"select 필드명 from 쿼리를 생성한다. 단축키: Alt+3",
						click:"selectNameQuery"
				    }, 
				    {
						id:"database_query_button_insert_into",
						view:"button",
						value:"insert into",
						tooltip:"INSERT INTO 쿼리를 생성한다. 단축키: Alt+4",
						click:"insertIntoQuery"
				    }, 
				    {
						id:"database_query_button_insert_set",
						view:"button",
						value:"insert set",
						tooltip:"INSERT SET 쿼리를 생성한다. 단축키: Alt+5",
						click:"insertSetQuery"
				    }, 
				    {
						id:"database_query_button_update_set",
						view:"button",
						value:"update set",
						tooltip:"Update SET 쿼리를 생성한다. 단축키: Alt+6",
						click:"updateSetQuery"
				    }, 
				    {
						id:"database_query_button_delete",
						view:"button",
						value:"delete",
						tooltip:"DELETE 쿼리를 생성한다. 단축키: Alt+7",
						click:"deleteQuery"
				    }, 
					{
						id:"database_query_button_allow_html",
						view:"button",
						value:"allow HTML",
						tooltip:"결과에 HTML 을 허용-비허용. 단축키: Alt+8",
						on:{"onItemClick":function(){
							if(serverInfo.htmlAllow) 	serverInfo.htmlAllow=false;
							else 						serverInfo.htmlAllow=true;

							$$("database_query_html_allow_info").define("label","HTML-Allow : "+serverInfo.htmlAllow);
							$$("database_query_html_allow_info").refresh();
							webix.message({ type:"error", text:"HTML Allow : " + serverInfo.htmlAllow +" 상태로 변경"});
						}}
					},
				    {
						id:"database_query_button_auto_commit",
						view:"button",
						value:"auto commit",
						tooltip:"Auto Commit 활성-비활성., 단축키:Alt+9",
						on:{"onItemClick":function(){
							if(serverInfo.autoCommit) 	serverInfo.autoCommit=false;
							else 						serverInfo.autoCommit=true;

							$$("database_query_auto_commit_info").define("label","Auto-Commit : "+serverInfo.autoCommit);
							$$("database_query_auto_commit_info").refresh();
							webix.message({ type:"error", text:"Auto-Commit : " + serverInfo.autoCommit +" 상태로 변경"});
						}}
					},
				    {
						id:"database_query_button_kill_execute",
						view:"button",
						value:"중단",
						tooltip:"실행중인 쿼리를 중단한다. 단축키: Alt+0 ",
						on:{"onItemClick":"killExecuteQuery"}
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
						id:"database_developer_combo_prepare_style",
						view:"combo",
						value:"#{field}",
						options:["?", ":field", "#{field}","${field}"],
						tooltip:"prepare 스타일을 정의 한다.<br/>?,:field,#{field},${field} 정의 가능"
				    }, 
				    {
						id:"database_developer_button_java_model",
						view:"button",
						value:"java model",
						tooltip:"java model 을 생성한다. 단축키 : Ctrl+1",
						click:"javaModel"
				    }, 
				    {
						id:"database_developer_button_java_hibernate_model",
						view:"button",
						value:"java H-model",
						tooltip:"java hibernate model 을 생성한다. 단축키 :  Ctrl+2",
						click:"javaHibernateModel"
				    }, 
				    {
						id:"database_developer_button_java_model_set",
						view:"button",
						value:"java setter",
						tooltip:"java model 의 setter 를 생성한다. 단축키 :  Ctrl+3",
						click:"javaModelSet"
				    }, 
				    {
						id:"database_developer_button_java_model_get",
						view:"button",
						value:"java getter",
						tooltip:"java model 의 getter 를 생성한다. 단축키 :  Ctrl+4",
						click:"javaModelGet"
				    },				      
				    {
						id:"database_developer_button_mybatis_select",
						view:"button",
						value:"mybatis select",
						tooltip:"mybatis select 구문을 생성한다. 단축키 :  Ctrl+5",
						click:"mybatisSelect"
						
				    }, 
				    {
						id:"database_developer_button_mybatis_insert",
						view:"button",
						value:"mybatis insert",
						tooltip:"mybatis insert 구문을 생성한다. 단축키 :  Ctrl+6",
						click:"mybatisInsert"						
				    }, 
				    {
						id:"database_developer_button_mybatis_update",
						view:"button",
						value:"mybatis update",
						tooltip:"mybatis update 구문을 생성한다. 단축키 :  Ctrl+7",
						click:"mybatisUpdate"
				    }, 
				    {
						id:"database_developer_button_mybatis_delete",
						view:"button",
						value:"mybatis delete",
						tooltip:"mybatis delete 구문을 생성한다. 단축키 :  Ctrl+8",
						click:"mybatisDelete"
				    }, 
				    {
						id:"database_developer_button_mybatis_result",
						view:"button",
						value:"mybatis result",
						tooltip:"mybatis result 를 생성한다. 단축키 :  Ctrl+9",
						click:"mybatisResultMap"						
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
			label:"HTML-Allow : "+serverInfo.htmlAllow, 
			align:"right",
			adjust:true,
			tooltip : "true : result 에 HTML 이 실행됨. false : result 에 HTML 이 TEXT 형태로 표시됨." 
		},
		{
			// auto-commit 상태 확인
			id:"database_query_auto_commit_info",
			view:"label", 
			label:"Auto-Commit : "+serverInfo.autoCommit, 
			align:"right",
			adjust:true,
			tooltip : "true : 실행한 내용이 바로 DB에 반영됩니다. false : 실행한 내역이 DB에 반영되지 않습니다." 
		}]
	}	
] // end rows
}];

// 데이터 베이스 쿼리 실행
var executeQuery = function (){
	// 객체 복사
	var newServerInfo = copyServerInfo(serverInfo);
	// 쿼리 정보 입력
	newServerInfo.query=encodeURIComponent($$("database_query_input").getValue());
	
	getDataParseView("/database/executeQuery",newServerInfo,"database_result_list_view",true,false,true);
}

// 실행중인 쿼리 중단
var killExecuteQuery = function (){
	webix.ajax().post("/database/killExecuteQuery.json",{
		server:serverInfo.server,
		schema:serverInfo.schema,
		account:serverInfo.account,
		query:encodeURIComponent($$("database_query_input").getValue())}, 
		function(text,data){
			if(data.json().status ==200){
				webix.message("쿼리가 실행 중지 되었습니다. ");
			} else { // 실패
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });		
			}
	});
	$$("database_result_list_view").hideProgress();
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
			scroll:true,
			multiselect:true,
			clipboard:"selection",
		}
	] // end rows
}];

// 결과창 Context 메뉴
webix.ui({
    view:"contextmenu",
    id:"database_result_context_menu",
    width:170,
    data:[
          "create insert query",
          "create update query",
          "create delete query",
          "create select query",
          { $template:"Separator" },
          "result excel download"],
    on:{
        onItemClick:function(id){

        	// 선택된 row 에 대한 작업
        	var rowObject = this.getContext().obj.getItem(this.getContext().id.row);
        	var contextMenu = this;
            var setValue="";
            var whereValue="";
            // develop 창에 객체를 복사한다.
            $.each($$("table_info_develop_list").data.pull,function(index){
            	var value=rowObject[this.columnName];
            	// 초기화
            	this.field_set="";
            	this.field_where="";
            	
            	switch (contextMenu.getItem(id).value) {
				case 'create insert query':   	
					this.field_set=value;
					break;
				case 'create update query':   	
					this.field_set=value;
					this.field_where=value;
					break;
				case 'create delete query':   	
					this.field_where=value;
					break;
				case 'create select query':
					this.field_where=value;					
					break;
				}
            });
            
            $$("table_info_develop_list").refresh();
            
            // 액션을 클릭한다.
            switch (contextMenu.getItem(id).value) {
			case 'create insert query':   	
				insertIntoQuery();
				break;
			case 'create update query':   	
				updateSetQuery();
				break;
			case 'create delete query':   	
				deleteQuery();
				break;
			case 'create select query':
				selectAllQuery();
				break;
			case 'result excel download':
				var nowDate = new Date();
				var nowDateString = "";
				nowDateString += nowDate.getFullYear();
				// Month 별도 처리
				if(nowDate.getMonth()<9){
					nowDateString += "0"+parseInt(nowDate.getMonth()+1);	
				} else {
					nowDateString += parseInt(nowDate.getMonth()+1);
				}
				nowDateString += nowDate.getDate();
				nowDateString += nowDate.getMinutes();
				nowDateString += nowDate.getMilliseconds();
				webix.toExcel($$("database_result_list_view"), 
					{ 
						header: false,
						filename: "dbClient"+nowDateString,
					}
				);
				break;
			}

            webix.message(contextMenu.getItem(id).value+" Complete");
        }
    }
});
// result 에 context menu 추가
webix.ready(function(){
	$$("database_result_context_menu").attachTo($$("database_result_list_view"));
});


var database_developer_cell = [{
		id:"database_query_log_view",
		header:"Query Log",
		view : "datatable", 
    	columns:[
			{ 	id:"seq",	header:["Seq", {	// 검색창 구현
				content:"textFilter", placeholder:"sql search",
				compare:function(value, filter, obj){ // 검색창 필터조건 구현
						if (equals(obj.query	, filter)) return true;
						return false;
				}, colspan:5}]
				,width:40
			},
			{ id:"date",		header:"DateTime",	width:95},
			{ id:"query",		header:"Query",		width:150},
			{ 
				id:"reTry",		header:"재사용",		width:60,
				template:'<input type="button" value="사용" style="width:40px;" data="#query#" onClick="reUseQuery(this);"/>',
			},
			{ 
				id:"favorities",	header:"즐겨찾기",		width:70,
				template:'<input type="button" value="저장" style="width:40px;" data="#query#" onClick="addFavorityQueryPopup(this);"/>',
			}
		],
		data:[],
		tooltip:true,
		resizeColumn:true,
	},
	{
		id:"database_query_favorities_view",
		header:"Favorites Query",
		view : "datatable", 
    	columns:[
			{ 	id:"favorityQuerySeq",	header:["Seq", {	// 검색창 구현
				content:"textFilter", placeholder:"memo and sql search",
				compare:function(value, filter, obj){ // 검색창 필터조건 구현
						if (equals(obj.memo	, filter)) return true;
						if (equals(obj.query, filter)) return true;
						return false;
				}, colspan:5}]
				,width:40
			},
			{ id:"memo",			header:"Memo",		width:100},
			{ id:"query",			header:"Query",		width:150,
				template:function(obj){
					obj.query=decodeURIComponent(obj.query)
					return obj.query;
				}
			},
			{ 
				id:"reTry",			header:"사용",		width:60,
				template:'<input type="button" value="사용" style="width:40px;" data="#query#" onClick="reUseQuery(this);"/>',
			},
			{ 
				id:"favorities",	header:"삭제",	width:60,
				template:'<input type="button" value="삭제" style="width:40px;" data="#query#" onClick="removeFavorityQuery(#favorityQuerySeq#);"/>',
			}
		],
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	}
];

// 쿼리 재사용
var reUseQuery=function(obj){
	$$("database_query_input").setValue(obj.getAttribute("data"));
	// 에디터 창으로 focus 를 되돌린다.
	$$("database_query_input").focus(); 
};

// 즐겨찾는 쿼리 폼
var add_favority_query_form = {
	id:" add_favority_query_form",
	view:"form",
	borderless:true,
	elements: [
		{ id:"favority_memo", 	view:"text", label:'memo', 	name:"memo", value:"",
			on:{"onKeyPress":function(key,e){// 실행
				// enter 를 입력하면, 즐겨찾는 쿼리 저장을 실행 한다.
				if(key==13) $$("favority_query_button").callEvent("onItemClick");
			}
		}},
		{ id:"favority_query", 	view:"text", label:'query', name:"query", value:""},
		{margin:5, cols:[
			{ 
				id:"favority_query_button",view:"button", value:"추가" , type:"form", 
				on:{"onItemClick":function(){// 로그인 실행
					webix.ajax().post("/database/saveFavoritiesQuery.json", 
						{
							memo:$$("favority_memo").getValue(),
							query:encodeURIComponent($$("favority_query").getValue())
						}, 
						function(text,data){
							// 저장 실패
							if(data.json().status !=200){
								// validate 메세지 
								var message = data.json().desc.split("\n");
								webix.message({ type:"error", text:message[0].replace("="," ") });
							} else { // 저장성공
								webix.message(data.json().result.message);
								database_query_favorities_view_load();
								$$("add_favority_query_popup").hide();
							}
					});
				}
			}},
			{ view:"button", value:"취소", click:function(){
				// 팝업 닫기
				$$("add_favority_query_popup").hide();
			}}
		]},
	],
	elementsConfig:{
		labelPosition:"top"
	}
};

//즐겨찾는 쿼리 추가 창
var addFavorityQueryPopup = function(obj){
	// 쿼리 value 입력
	add_favority_query_form.elements[1].value=obj.getAttribute("data");

	webix.ui({
        view:"window",
        id:"add_favority_query_popup",
        width:300,
        position:"center",
        modal:true,
        head:"즐겨 찾는 쿼리 추가",
        body:webix.copy(add_favority_query_form)
    }).show();
    $$("favority_memo").focus();
};

/**
 * 즐겨 찾는 쿼리 제거
 */
var removeFavorityQuery = function(favorityQuerySeq){
	webix.ajax().del("/database/removeFavoritiesQuery.json?favorityQuerySeq="+favorityQuerySeq, 
	function(text, data){
		// 실패
		if(data.json().status !=200){
			// validate 메세지 
			var message = data.json().desc.split("\n");
			webix.message({ type:"error", text:message[0].replace("="," ") });
		} else { // 성공
			webix.message(data.json().result.message);
			database_query_favorities_view_load();
		}
	});
};

/**
 * 즐겨찾는 쿼리 로딩
 */
var database_query_favorities_view_load = function(){
	setTimeout(function(){
		if(null!=$("database_query_favorities_view")){
			try {
				getDataParseView("/database/findFavoritiesQueryList",{},"database_query_favorities_view",false,false,false);
				$("database_query_favorities_view").sort("favorityQuerySeq","desc");
			} catch (e) {
				database_query_favorities_view_load();
			}
		}	
	}, 100);

}; 
database_query_favorities_view_load();

/** 
 * 자동완성 테이블/필드 데이터 로딩
 * 1 초 이후에 실행 한다. 대량의 데이터가 들어 올 위험이 있다. 
 */
var database_query_all_field_load = function(){
	var	cachedList = webix.storage.local.get(JSON.stringify(serverInfo, null, 2)+"_autoComplete");
	// cache 에 데이터가 존재하면 캐시의 데이터를 노출한다.
	if(null != cachedList){
		autoCompleteAddTablesReset();
		$.each(cachedList,function(objIndex){
			autoCompleteAddTablesAll(this.tableName,this.columnName,this.comment);								
		});
	} else {
		setTimeout(function(){
			// 테이블_필드 리스트 조회
			autoCompleteAddTablesReset();
			if(null!=serverInfo.serverInfoSeq){
				webix.ajax().get("/database/allFieldList"+".json",serverInfo, 
					function(text,data){
						if(data.json().status ==200 && null!=data.json().result){
							$.each(data.json().result,function(index, obj){
								// 정렬
								obj.sort(function(a,b){
									return(a.tableName < b.tableName) ? -1 : (a.tableName > b.tableName) ? 1 : 0;
								});
								$.each(obj,function(objIndex){
									autoCompleteAddTablesAll(this.tableName,this.columnName,this.comment);								
								});
								webix.storage.local.put(JSON.stringify(serverInfo, null, 2)+"_autoComplete",obj);
							});
						} else {
							errorControll(data.json());
						}
					}
				);
			}		
		},1000);	
	}
};