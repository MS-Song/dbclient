
// 최초 기동시에는 캐시를 모두 삭제 처리 한다.
webix.storage.local.clear();

// 데이터 베이스 선택 정보
var serverInfoSeq=null;
var server=null;
var schema=null;
var account=null;
var driver=null;
var drivers=null;
var tableName=null;
var tableComment=null;
var autoCommit=false;
var htmlAllow=false;
// 서버 인포 캐시 활성화
var useCache=true;


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
	    			serverInfoSeq=selectedRow.id;
	    			server=selectedRow.host;
	    			schema=selectedRow.schemaName;
	    			account=selectedRow.account;
	    			driver=selectedRow.driver;
	    			tableName=null;
	    			tableComment=null;
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
	    			clearAllViews();
	    			database_info_data_load();
	            }}
	        }
    });
	
	// 사이드 메뉴가 열려 있을 경우에만 닫는다.
	if($$("menu").isVisible()) $$("menu").hide();
	
	$$("select_database_popup").show();
	
	
	// 데이터 초기화
	$$("select_database_loader").clearAll();
    // 데이터베이스 정보를 조회한다.
	webix.ajax().get("/database/serverList.json",{useCache:useCache}, function(text,data){
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
		} else {
			webix.message(data.json().desc);
		}
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
	    	on:{	// 테이블 아이템 클릭시에 동작
	    		onSelectChange:function(id){
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
							header:'<input type="checkbox" onClick="develop_field_clear(\'checkbox\',this);"/>',
							width:40,
							template:"{common.checkbox()} ",
							editor:"checkbox"
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_set",
							header:['SET','<input type="button" value="reset" onClick="develop_field_clear(\'set\');" />'],
							width:70,
							template:'<input type="text" name="field_set[]" value="#field_set#" style="width:50px;" />',
							editor:"inline-text",
						},
						{ 
							// date 의 경우 달력 출력 
							id:"field_where",
							header:['WHERE','<input type="button" value="reset" onClick="develop_field_clear(\'where\');" />'],
							width:70,
							template:'<input type="text" name="field_where[]" value="#field_where#" style="width:50px;" />',
							editor:"inline-text",
						},
						{ 
							id:"field_operation",
							header:['Operation','<input type="button" value="reset" onClick="develop_field_clear(\'operation\');" />'],	
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
					navigation:true
				}
			]
		}]
	},
	{	view : "datatable", 
		header:"View",			
		id:"database_info_view_list_view", 						
		columns:[
			{ id:"viewName",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"view name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.viewName, filter)) return true;
							return false;
					}, colspan:1}], 
				adjust:true,	sort:"string"
			},
			{ 
				id:"viewExecute",
				header:"실행",		
				width:60,
				template:function(obj){
					var htmlHeader 	= '<input type="button" value="실행" style="width:40px;" data="';
					var html 		= 'select * from '+obj.viewName; 
					var htmlFooter 	= '" onClick="reUseQuery(this);"/>';
					
					switch (driver) {
					case 'mysql'	: 	html+=' limit 10';										break;
					case 'oracle'	: 	html='select * from (\r'+html+'\r) where rownum <= 10';	break;
					}

					return  htmlHeader+html+htmlFooter;
				}
			},
			{ 
				id:"viewModify",
				header:"수정",		
				width:60,
				template:function(obj){
					var html = '<input type="button" value="수정" style="width:40px;" data="';
					html+= htmlEntities(obj.text); 
					html+= '" onClick="reUseQuery(this);"/>';
					return html;
				}
			},				
		],
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true
	},
	{	view : "datatable", 
		header:"Procedure",	
		id:"database_info_procedure_list_view", 
		columns:[
			{ id:"name",	header:["#", {	// 검색창 구현
					content:"textFilter", placeholder:"name search",
					compare:function(value, filter, obj){ // 검색창 필터조건 구현
							if (equals(obj.name, filter)) return true;
							return false;
					}, colspan:1}], 
				adjust:true,	sort:"string"
			},
			{ 
				id:"procedureModify",
				header:"수정",		
				width:60,
				template:function(obj){
					var html = '<input type="button" value="수정" style="width:40px;" data="';
					html+= htmlEntities(obj.name); 
					html+= '" onClick="database_info_procedure_detail_loading(this);"/>';
					return html;
				}
			},
			{ id:"lastUpdateDate", adjust:true,	sort:"string" }
		],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	},
	{	view : "datatable", 
		header:"Function",		
		id:"database_info_function_list_view",				
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	},
	{	view : "datatable", 
		header:"Sequence",		
		id:"database_info_Sequence_list_view",				
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	},
	{	view : "datatable", 
		header:"Trigger",		
		id:"database_info_Trigger_list_view",				
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	}
];

// 데이터베이스 정보 로드 
var database_info_data_load=function(){
	// 로딩 프로그레스 
	$$("database_info_table_list_view").showProgress();
	// 담기 전에 모두 지운다
	$$("database_info_table_list_view").clearAll();
	// 테이블 정보 로딩
	webix.ajax().get("/database/tableList.json",{
		server:server,
		schema:schema,
		account:account,
		useCache:useCache}, function(text,data){
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
	
	// view list 조회
	database_info_view_list_view_loading();
	// procedure list 조회
	database_info_procedure_list_view_loading();
};

// 테이블 정보 로딩
var database_info_field_data_load = function(){
	// 선택된 row
	var selectedRow = $$("database_info_table_list_view").getSelectedItem();
	// 테이블 명칭 저장
	try {
		if(undefined != selectedRow.tableName) {
			tableName = selectedRow.tableName;
			tableComment = selectedRow.tableComment;
		}
	} catch (e) {
		//webix.message({ type:"error", text:"데이터 베이스 정보 로드 실패.</br>데이터베이스를 다시 선택해주세요"});
		$$("database_info_table_list_view").hideProgress();
	}
 
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
		$$("table_info_develop_list").parse(cachedFieldDeveloperList);
		$$("table_info_develop_list").refresh();
		$$("table_info_develop_list").hideProgress();    		
		
	} else {
		webix.ajax().get("/database/fieldList.json",{
			server:server,
			schema:schema,
			account:account,
			table:tableName,
			useCache:useCache}, 
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
		    					nullable:this.nullable,
		    					columnKey:this.columnKey,
		    					extra:this.extra,
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
			table:tableName,
			useCache:useCache}, 
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
};

//view 리스트
var database_info_view_list_view_loading = function(){
	// 로딩 프로그레스 
	$$("database_info_view_list_view").showProgress();
	
	// 담기 전에 모두 지운다
	$$("database_info_view_list_view").clearAll();
	 
	// 캐시에 데이터가 있으면 ajax 를 실행하지 않는다.
	var cachedViewList = webix.storage.local.get(server + "_" + schema + "_" + account +"_viewList");

	if(null != cachedViewList){
		$$("database_info_view_list_view").parse(cachedViewList);
		// 다시 읽는다.
		$$("database_info_view_list_view").refresh();
		$$("database_info_view_list_view").hideProgress();
	} else {
		// 인덱스 조회
		webix.ajax().get("/database/viewList.json",{
			server:server,
			schema:schema,
			account:account,
			useCache:useCache}, 
			function(text,data){
				// 필드 정보를 획득한 경우에 넣는다.
				console.log(data.json());
				if(data.json().status ==200 && null!=data.json().result){
					// 인덱스는 없는 경우가 존재한다.
					if(null != data.json().result.viewList){
						$$("database_info_view_list_view").parse(data.json().result.viewList)
					}
					webix.storage.local.put(server + "_" + schema + "_" + account +"_viewList",$$("database_info_view_list_view").data.serialize());
					// 다시 읽는다.
		    		$$("database_info_view_list_view").refresh();
		    		$$("database_info_view_list_view").hideProgress();
				}
			}
		);
	}
} 

// procedure loading
var database_info_procedure_list_view_loading = function(){
	// 로딩 프로그레스 
	$$("database_info_procedure_list_view").showProgress();
	
	// 담기 전에 모두 지운다
	$$("database_info_procedure_list_view").clearAll();
	 
	// 캐시에 데이터가 있으면 ajax 를 실행하지 않는다.
	var cachedViewList = webix.storage.local.get(server + "_" + schema + "_" + account +"_procedureList");

	if(null != cachedViewList){
		$$("database_info_procedure_list_view").parse(cachedViewList);
		// 다시 읽는다.
		$$("database_info_procedure_list_view").refresh();
		$$("database_info_procedure_list_view").hideProgress();
	} else {
		// 프로시저 조회
		webix.ajax().get("/database/procedureList.json",{
			server:server,
			schema:schema,
			account:account,
			useCache:useCache}, 
			function(text,data){
				console.log(data.json());
				if(data.json().status ==200 && null!=data.json().result){
					if(null != data.json().result.procedureList){
						$$("database_info_procedure_list_view").parse(data.json().result.procedureList)
					}
					webix.storage.local.put(server + "_" + schema + "_" + account +"_procedureList",$$("database_info_procedure_list_view").data.serialize());
					// 다시 읽는다.
		    		$$("database_info_procedure_list_view").refresh();
		    		$$("database_info_procedure_list_view").hideProgress();
				} else {
					var message = data.json().desc.split("\n");
					webix.message({ type:"error", text:message[0].replace("="," ") });
				}
			}
		);
	}
} 

var database_info_procedure_detail_loading = function(obj){
	var name = obj.getAttribute("data");
	if(null!=name){
		// 프로시저 상세 조회
		webix.ajax().get("/database/procedureDetailList.json",{
			server:server,
			schema:schema,
			account:account,
			name:name,
			useCache:useCache}, 
			function(text,data){
				console.log(data.json());
				if(data.json().status ==200 && null!=data.json().result){
					$$("database_query_input").setValue(data.json().result.procedureList[0].text);
					// 에디터 창으로 focus 를 되돌린다.
					$$("database_query_input").focus(); 
				} else {
					var message = data.json().desc.split("\n");
					webix.message({ type:"error", text:message[0].replace("="," ") });
				}
			}
		);
	}
}; 



// TODO function 리스트
// TODO Sequence 
// TODO Trigger 


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
						tooltip:"Auto Commit 활성-비활성., 단축키:Alt+9",
						on:{"onItemClick":function(){
							if(autoCommit) 	autoCommit=false;
							else 			autoCommit=true;

							$$("database_query_auto_commit_info").define("label","Auto-Commit : "+autoCommit);
							$$("database_query_auto_commit_info").refresh();
							webix.message({ type:"error", text:"Auto-Commit : " + autoCommit +" 상태로 변경"});
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
				
				//쿼리 로그 기록
				var time = new Date();
				$$("database_query_log_view").data.add({
					seq:$$("database_query_log_view").data.order.length+1,
					date:time.getHours()+'시 '+time.getMinutes()+'분 '+time.getSeconds()+'초 <br/>'+time.getFullYear()+'년 '+(time.getMonth()+1)+'월 '+time.getDate()+'일',
					query:$$("database_query_input").getValue(),
					reTry:"",
					favorities:""
				},$$("database_query_log_view").data.order.length+1);
				$$("database_query_log_view").sort("seq", "desc","int");
				$$("database_query_log_view").refresh();
			} else { // 에러가 발생할 경우
				webix.message({ type:"error", text:data.json().desc });
			}
			$$("database_result_list_view").hideProgress();
		}
	);
}

// 실행중인 쿼리 중단
var killExecuteQuery = function (){
	webix.ajax().post("/database/killExecuteQuery.json",{
		server:server,
		schema:schema,
		account:account,
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
			scroll:true
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
          "copy selected row",
          { $template:"Separator" }
          ,"create All insert query "
          ,"result excel download"
          ,"copy all rows "],
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
    		case 'create insert query All':
				break;
			case 'result excel download':
				break;
			case 'copy data':
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

//자동완성 데이터 저장
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

// view
// function
// procedure
// sequence
// create 테이블


/**
 * 쿼리 로그 및 즐겨찾는 쿼리 
 */
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
			{ id:"query",			header:"Query",		width:150},
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
	console.log(favorityQuerySeq);
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
			$$("add_favority_query_popup").hide();
		}
	});
};


// 즐겨 찾는 쿼리 리스트 loading
var database_query_favorities_view_load = function(){
	// 로그인 되어 있는 경우에만 호출
	if(null!=id){
		// 프로그레스 바
		$$("database_query_favorities_view").showProgress();
		// 이미 있는 내용은 모두 지운다 
		$$("database_query_favorities_view").clearAll();
		webix.ajax().get("/database/findFavoritiesQuery.json",{},function(text,data){
			// 정보를 획득한 경우에 넣는다.
			if(data.json().status ==200 && null!=data.json().result){
	    		$.each(data.json().result,function(){
		    		$.each(this,function(index){
						$$("database_query_favorities_view").data.add({
							favorityQuerySeq:this.favorityQuerySeq,
							memo:this.memo, 
							query:decodeURIComponent(this.query), 
							inputDate:this.inputDate, 
							reTry:"",
							favorities:""}
						,index);
		    		});
	    		});
				$$("database_query_favorities_view").sort("favorityQuerySeq", "desc","int");
				$$("database_query_favorities_view").refresh();
	    		$$("database_query_favorities_view").hideProgress();
			} else {
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });
				$$("database_query_favorities_view").hideProgress();
			}
		});
	}
};

// 즐겨 찾는 쿼리 로딩
webix.ready(function(){
	// 로그인 확인 후에 실행 한다.
	setTimeout(database_query_favorities_view_load, 500);
});


// 뷰의 데이터를 모두 초기화 한다.
var clearAllViews = function(){
	$$("database_info_table_list_view").clearAll();
	$$("table_info_field_list").clearAll();
	$$("table_info_develop_list").clearAll();
	$$("table_info_index_list").clearAll();
	$$("database_result_list_view").clearAll();	
};