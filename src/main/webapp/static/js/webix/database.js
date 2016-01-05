// 최초 기동시에는 캐시를 모두 삭제 처리 한다.
webix.storage.local.clear();

// 데이터 베이스 선택 정보
var serverInfoSeq=null;
var server=null;
var schema=null;
var account=null;
var driver=null;
var tableName=null;

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
				}, {
					header:"index",
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
		// 로딩 프로그레스 
		$$("table_info_field_list").showProgress();
		// 담기 전에 모두 지운다
		$$("table_info_field_list").clearAll();
		// 필드 조회 
		
		// 캐시에 데이터가 있으면 ajax 를 실행하지 않는다.
		var cachedFieldList = webix.storage.local.get(server + "_" + schema + "_" + account +"_fields_"+ tableName);
		if(null != cachedFieldList && cachedFieldList.length != 0){
			$$("table_info_field_list").parse(cachedFieldList);
			// 다시 읽는다.
    		$$("table_info_field_list").refresh();
    		$$("table_info_field_list").hideProgress();
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
						$$("table_info_field_list").parse(data.json().result.fieldList)
						//cache 저장
						webix.storage.local.put(server + "_" + schema + "_" + account +"_fields_"+ tableName,$$("table_info_field_list").data.serialize());
						// 다시 읽는다.
			    		$$("table_info_field_list").refresh();
			    		$$("table_info_field_list").hideProgress();
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
	rows:[{
		cols:[{	
			id:"database_query_form",
			adjust:true,
			view : "form", 
			minHeight:200,
			minWidth:500,
			autowidth:true,
			autoheight:true,
			scroll:false,
			elements:[
				{	id:"database_query_input",	
					view : "textarea",
					placeholder:"input query"
				}
			]
		},
		{
			rows:[{
				id:"database_query_execute_button",
				view:"button",
				value:"execute",
				width:100,
				height:100,
				on:{
					"onItemClick":function(){ // 쿼리 실행

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
				  		  	autoCommit:false,
				  		  	htmlAllow:false}, 
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
										$$("database_result_list_view").hideProgress();
									}
									// 실행이 종료되면 결과를 보여준다
									$$("database_query_execute_info").define("label",'Row Count : '+data.json().result.rowCount + ', Total Time  : '+data.json().result.processTime + ' ms');
									$$("database_query_execute_info").refresh();
								} else { // 에러가 발생할 경우
									webix.message({ type:"error", text:data.json().desc });
								}
							}
						);
						// 쿼리가 실행되는 동안 포커스를 되돌린다.
						$$("database_query_form").focus();
					}
				}
			}] // end rows
		}] // end cols
	},{// 쿼리 결과 info 창
		id:"database_query_execute_info",
		view:"label", 
	    label:"", 
	    align:"left"
	}] // end rows
}];

/**
 * 쿼리창에 이벤트 부여
 */

$(document).delegate("[name='database_query_input']", "keydown", function(e) {
	var keyCode = e.keyCode || e.which;
	
	// 쿼리 입력창 tab 키 허용
	if (keyCode == 9) {
		event.preventDefault();
		IndentHelper.indent(this, event.shiftKey);
	} 
	// 컨트롤+엔터 로 쿼리 실행
	else if(keyCode == 13 && event.ctrlKey){
		event.preventDefault();
		$$("database_query_execute_button").callEvent("onItemClick");
	}
});

// 결과 창
var database_result_cell = [
	{	view : "datatable", 
		header:"result1",			
		id:"database_result_list_view", 						
		columns:[],	
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
		scroll:true
	},
]

// CURD
// Mybatis
// hibernate
// model 생성
// 히스토리
// create 테이블
// 인덱스

// 결과 값