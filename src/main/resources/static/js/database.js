/**
 * Database 정보
 */
var database = {};

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
	offset:0,
	limit:100
}; 	

/**
 * DatabaseDriver 정보
 */
var drivers=null;
webix.ajax().get("/database/getDatabaseDriver",function(text,data){
	if(data.json().httpStatus==200){
		drivers=data.json().contents;
	} else {
		webix.message({ type:"error", text:data.json().message});
	}
});

/**
 * Charset 정보
 */
var charset = null;
webix.ajax().get("/database/getCharset",function(text,data){
	if(data.json().httpStatus==200){
		charset=data.json().contents;
	} else {
		webix.message({ type:"error", text:data.json().message});
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
        	        { id:"id",				header:"ID",			width:100, sort:"int"},
					{ id:"host",			header:"Host",			width:100, sort:"string"},
 					{ id:"hostAlias",		header:"HostAlias",		width:100, sort:"string"},
 					{ id:"schemaName",		header:"SchemaName",	width:100, sort:"string"},
 					{ id:"account",			header:"Account",		width:100, sort:"string"},
 					{ id:"driver",			header:"Driver",		width:100, sort:"string"},
 					{ id:"port",			header:"Port",			width:80,  sort:"int"},
 					{ id:"selected",		header:"선택",			width:50,
 						template:function(obj){
 							return  obj.id == database.id ? "선택":"";
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
	    			database=selectedRow;
	    			selectedRow.selected="선택";
	    			// 입력한 값을 재 로딩 한다.
	    			$$("select_database_loader").refresh();
	    			// 선택 창을 닫는다.
	    	   		window.setTimeout(function(){$$("select_database_popup").hide();}, 500);										
	    			webix.message("데이터베이스를 선택 했습니다.");
	    	   		
	    			// 선택된 서버 정보를 보여준다.
	    			$$("toolbar").removeView("toolbar_notices");
	    			$$("toolbar").addView({id:"toolbar_notices",view: "label", label: database.hostAlias+" ["+database.host+"] 선택"},3);

    				// SQL Bind Style 정의한다.
	    			$$("toolbar").removeView("database_developer_combo_prepare_style");
	    			$$("toolbar").addView({
	    				id:"database_developer_combo_prepare_style",
						view:"combo",
						width:100,
						value:"#{field}",
						options:["?", ":field", "#{field}","${field}"],
						tooltip:"prepare 스타일을 정의 한다.<br/>?,:field,#{field},${field} 정의 가능"
					});
	    			// 캐시 삭제 버튼을 노출 한다.
	    			$$("toolbar").removeView("toolbar_cache_remove");
	    			$$("toolbar").addView({id:"toolbar_cache_remove",
	    				view:"button", 
	    				value:"Remove Cache" , 
	    				type:"form",
	    				width:100,
	    				on:{"onItemClick":function(){// 캐시 삭제 실행 - FIXME
							// local storage 의 캐시먼저 
							webix.storage.local.clear();
							webix.message("캐시 삭제가 완료 되었습니다.");
	    					webix.ajax().get("/database/deleteCache", function(text,data){
	    						if(data.json().httpStatus ==200){
	    							reload();
	    						} else { // 캐시 삭제 실패
	    							// validate 메세지 
	    							webix.message({ type:"error", text:data.json().message});
	    						}
	    					});
	    				}}
	    			});
	    			// 선택된 데이터베이스의 정보를 읽는다.
	    			database_info_data_load();
	            }}
	        }
    }).show();
	// 사이드 메뉴가 열려 있을 경우에만 닫는다.
	if($$("menu").isVisible()) $$("menu").hide();
	// 데이터베이스 정보를 조회한다.
	getDataParseView("/database/list", null, "select_database_loader", false, false, false);
}

// database info cell
var database_info_cell = [{	 
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
					sort:"int",
					template:"#seq#"
				},					         
				{ id:"tableName",	header:"Name",		sort:"string"},
				{ id:"tableComment",header:"Comment",	sort:"string"},
			],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			navigation:true,
	    	on:{	// 테이블 아이템 클릭시에 동작
	    		onItemClick:function(id){
    				database_info_field_data_load();
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("tableName");
					this.adjustColumn("tableComment");
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
							sort:"int"
						},					         
	   					{ id:"columnName",		header:"name",		sort:"string"},
						{ id:"nullable",		header:"null able",	sort:"string"},
						{ id:"columnKey",		header:"key",		sort:"string"},
						{ id:"dataType",		header:"type",		sort:"string"},
						{ id:"dataLegnth",		header:"length",	sort:"int"	 },
						{ id:"comment",			header:"comment",	sort:"string"},
						{ id:"defaultValue",	header:"defualt",	sort:"string"},
						{ id:"characterset",	header:"charset",	sort:"string"},
						{ id:"extra",			header:"extra",		sort:"string"}

					],
					data:[],
					tooltip:true,
	 				resizeColumn:true,
					select:"row",
					navigation:true,
			    	on:{
			    		onAfterLoad:function(){
							this.adjustColumn("columnId");
							this.adjustColumn("columnName");
							this.adjustColumn("nullable");
							this.adjustColumn("columnKey");
							this.adjustColumn("dataType");
							this.adjustColumn("dataLegnth");
							this.adjustColumn("comment");
							this.adjustColumn("defaultValue");
							this.adjustColumn("characterset");
							this.adjustColumn("extra");
						}
			    	}
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
							width:40,	
							sort:"int"
						},					         
	   					{ id:"columnName",		header:"name",		sort:"string"},
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
					navigation:true,
			    	on:{
			    		onAfterLoad:function(){
							this.adjustColumn("columnName");
						}
			    	}
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
					sort:"int",
					template:"#seq#"
				},					         
				{ 	
					id:"name",
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(database.driver.indexOf("oracle") >=0){
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
				},
				{ 
					id:"comments",
					header:"comments",
					sort:"string",
				}
			],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_view_list_view").getSelectedItem();
	    			database.name=selectedRow.name;

	    			getDataParseProperty("/database/getViewDetailLis",database,"view_info_detail_property");
	    			getDataParseTextarea("/database/getViewSourceList",database,"view_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_view_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			$$("database_query_develop_mode").setValue("PLSQL");
	    			getDataParseEditor("/database/getViewSourceList",database,"database_query_input","editText");
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("name");
					this.adjustColumn("lastUpdateTime");
					this.adjustColumn("comments");
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
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(database.driver.indexOf("oracle") >=0){
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
	    			database.name=selectedRow.name;
	    			getDataParseProperty("/database/getProcedureDetailList",database,"procedure_info_detail_property");
	    			getDataParseTextarea("/database/getProcedureSourceList",database,"procedure_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_procedure_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			$$("database_query_develop_mode").setValue("PLSQL");
	    			getDataParseEditor("/database/getProcedureSourceList",database,"database_query_input","editText");
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("name");
					this.adjustColumn("lastUpdateDate");
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
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(database.driver.indexOf("oracle") >=0){
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
					sort:"string" 
				}
			],	
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_function_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			getDataParseProperty("/database/getFunctionDetailList",database,"function_info_detail_property");
	    			getDataParseTextarea("/database/getFunctionSourceList",database,"function_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_function_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			$$("database_query_develop_mode").setValue("PLSQL");
	    			getDataParseEditor("/database/getFunctionSourceList",database,"database_query_input","editText");
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("name");
					this.adjustColumn("lastUpdateDate");
				}
	    	}
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
					sort:"int",
					template:"#seq#"
				},					         
			         
				{ 	
					id:"name",
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(database.driver.indexOf("oracle") >=0){
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
	    			database.name=selectedRow.name;
	    			getDataParseProperty("/database/getTriggerDetailList",database,"trigger_info_detail_property");
	    			getDataParseTextarea("/database/getTriggerSourceList",database,"trigger_info_source","editText");
	    		},
	    		onItemDblClick:function(){
	    			var selectedRow = $$("database_info_trigger_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			$$("database_query_develop_mode").setValue("PLSQL");
	    			getDataParseEditor("/database/getTriggerSourceList",database,"database_query_input","editText");
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("name");
					this.adjustColumn("lastUpdateDate");
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
					sort:"int",
					template:"#seq#"
				},					         
				{ 	
					id:"name",
					sort:"string",
					template:function(obj){
						var html=obj.name;
						if(database.driver.indexOf("oracle") >=0){
							// 사용불가능 일 경우
							if(obj.status == "INVALID"){
								html="<del>"+html+"</del>";		
							}
						}
						return html;
					}
				},			         
				{ id:"lastValue",		header:"lastValue",		sort:"int"},
				{ id:"minValue",		header:"minValue",		sort:"int"},
				{ id:"maxValue",		header:"maxValue",		sort:"int"},
				{ id:"incrementBy",		header:"incrementBy",	sort:"int"}
			],	
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			on:{	
				onItemClick:function(){
	    			var selectedRow = $$("database_info_sequence_list_view").getSelectedItem();
	    			database.name=selectedRow.name;
	    			getDataParseProperty("/database/getSequenceDetailList",database,"sequence_info_detail_property");
	    		},
	    		onAfterLoad:function(){
					this.adjustColumn("seq");
					this.adjustColumn("name");
					this.adjustColumn("lastValue");
					this.adjustColumn("minValue");
					this.adjustColumn("maxValue");
					this.adjustColumn("incrementBy");
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
	},
	// TODO -- 추가되는 데이터 베이스의 기능은 여기 사이에 넣는다.
	{
		id:"database_query_log_view",
		header:"Query Log",
		view : "datatable", 
    	columns:[
			{ 	id:"id",	header:["id", {	// 검색창 구현
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
		resizeColumn:true
	},
	{
		id:"database_query_favorities_view",
		header:"Favorites",
		view : "datatable", 
    	columns:[
			{ 	id:"id",	header:["id", {	// 검색창 구현
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
				template:'<input type="button" value="삭제" style="width:40px;" data="#query#" onClick="removeFavorityQuery(#id#);"/>',
			}
		],
		data:[],
		tooltip:true,
		select:"row",
		resizeColumn:true,
	}
];

// 데이터베이스 정보 로드 
var database_info_data_load=function(){
	// 테이블 리스트 조회
	getDataParseView("/database/getTableList",database,"database_info_table_list_view",false,true,false);
	// 테이블 정보 초기화
	database_info_field_data_load();
	// view list 조회
	getDataParseView("/database/getViewList",database,"database_info_view_list_view",false,true,false);	
	// procedure list 조회
	getDataParseView("/database/getProcedureList",database,"database_info_procedure_list_view",false,true,false);
	// function list 조회
	getDataParseView("/database/getFunctionList",database,"database_info_function_list_view",false,true,false);
	// trigger list 조회
	getDataParseView("/database/getTriggerList",database,"database_info_trigger_list_view",false,true,false);
	// sequence List 로딩
	getDataParseView("/database/getSequenceList",database,"database_info_sequence_list_view",false,true,false);
	// 자동완성용 테이블 데이터 로딩
	database_query_all_field_load();
	// 자동완성 이벤트 호출
	autoCompleteEvent();
	// 즐겨 찾는 배송지 로딩
	database_query_favorities_view_load();
};

// 테이블 정보 로딩
var database_info_field_data_load = function(){
	// 선택된 row
	var selectedRow = $$("database_info_table_list_view").getSelectedItem();
	// 테이블 명칭 저장
	try {
		if(undefined != selectedRow.tableName) {
			database.name = selectedRow.tableName;
		}
	} catch (e) {
		$$("database_info_table_list_view").hideProgress();
	}
	
	if(null==selectedRow || undefined==selectedRow){
		try {
			var clearViewArray=['table_info_field_list','table_info_develop_list','table_info_index_list','show_create_table_source'];
			for(var key in clearViewArray){
				clearViewArray[key] == "show_create_table_source" ? $$(clearViewArray[key]).setValue(""):$$(clearViewArray[key]).clearAll();	
			}
		} catch (e) {}
	} else {
		// 필드 리스트
		getDataParseView("/database/getTableFieldList",database,"table_info_field_list",false,false,false);
		// 개발자 도구 리스트
		getDataParseView("/database/getTableFieldList",database,"table_info_develop_list",false,false,false);
		// 인덱스 리스트
		getDataParseView("/database/getTableIndexList",database,"table_info_index_list",false,false,false);
		// create table view
		getDataParseTextarea("/database/showCreateTable",database,"show_create_table_source","showCreateTable");
	}
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
		header:"SQL DEVELOP",
		rows:[
		{
			id:"database_query_form",
			view : "form", 
			borderless:true,
			margin:0,
			scroll:false,
			elements:[{	
				rows:[{
					cols:[ // 상단 기능 버튼 
				       {
		    				id:"database_query_develop_mode",
							view:"combo",
							width:100,
							value:"SQL",
							options:["SQL", "PLSQL"],
							tooltip:"에디터 모드를 변경한다.[SQL:일반SQL모드][PLSQL:PLSQL개발모드]"
						},   
					    {
							id:"database_query_button_select_count_pk",
							view:"button",
							value:"select count",
							tooltip:"select count(pk) from 쿼리를 생성한다. 단축키: Ctrl+1 ",
							on:{"onItemClick":function(){
								selectCountQuery();
							}}
					    }, 
					    {
							id:"database_query_button_select_all_field",
							view:"button",
							value:"select *",
							tooltip:"select * from 쿼리를 생성한다. 단축키: Ctrl+2",
							on:{"onItemClick":function(){
								selectAllQuery();
							}}
					    }, 
					    {
							id:"database_query_button_select_name_field",
							view:"button",
							value:"select field",
							tooltip:"select 필드명 from 쿼리를 생성한다. 단축키: Ctrl+3",
							on:{"onItemClick":function(){
								selectNameQuery();
							}}
					    }, 
					    {
							id:"database_query_button_insert_into",
							view:"button",
							value:"insert into",
							tooltip:"INSERT INTO 쿼리를 생성한다. 단축키: Ctrl+4",
							on:{"onItemClick":function(){
								insertIntoQuery(false);
							}}
					    }, 
					    {
							id:"database_query_button_update_set",
							view:"button",
							value:"update set",
							tooltip:"Update SET 쿼리를 생성한다. 단축키: Ctrl+5",
							on:{"onItemClick":function(){
								updateSetQuery(false);
							}}
					    }, 
					    {
							id:"database_query_button_delete",
							view:"button",
							value:"delete",
							tooltip:"DELETE 쿼리를 생성한다. 단축키: Ctrl+6",
							on:{"onItemClick":function(){
								deleteQuery(false);
							}}
					    }, 
					    {
							id:"database_query_button_auto_commit",
							view:"button",
							value:"auto commit",
							tooltip:"Auto Commit 활성-비활성., 단축키:Ctrl+9",
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
						}
					]
				},
				{// 에디터 창
					id:"database_query_input",	
					view : "codemirror-editor-sql"
				},{ // 쿼리 정보 창
					cols:[{
						// 쿼리 결과 info 창
						id:"database_query_execute_info",
						view:"label", 
						label:"", 
						align:"left",
						adjust:true,
						height:25,
						tooltip:true
					},
					{
						// auto-commit 상태 확인
						id:"database_query_auto_commit_info",
						view:"label", 
						label:"Auto-Commit : "+executeQueryParams.autoCommit, 
						align:"right",
						width : 170,
						height: 25,
						tooltip : "true : 실행한 내용이 바로 DB에 반영됩니다. false : 실행한 내역이 DB에 반영되지 않습니다." 
					},
					{
						// next data image
						id:"database_query_next_data_image",
						view:"button", 
						type:"image",
						align:"right",
						width : 24,
						height: 25,
						image:"/static/img/next_data_arrow.png",
						click:function(){
							executeQuery(true,null);
						}
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
		    	on:{
		    		onScrollY:function(){
		    			// result 화면의 크기를 가져온다.
		    			var height=$("[view_id='"+this.config.id+"']").find(".webix_vscroll_y").find(".webix_vscroll_body").height();
		    			// 현재 위치를 확인한다. 
		    			var pos = this.getScrollState();
		    			// 스크롤의 길이 만큼 현재 스크롤 크기가 클 경우 실행 한다. 
		    			if(pos.y > height-this.$height){
		    				// 추가 데이터 쿼리 실행
		    				executeQuery(true,this);
		    			}
		    		}
		    	}
			}] // end cells			
		}] // end rows
	},{
		header:"JAVA DEVELOP",
		rows:[
		{
			id:"database_java_form",
			view : "form", 
			scroll:false,
			borderless:true,
			elements:[{	
				rows:[{	// 상단 기능 버튼
					cols:[{
							id:"database_developer_button_java_model",
							view:"button",
							value:"java model",
							tooltip:"java model 을 생성한다. 단축키 : ALT+1",
							click:"javaModel"
					    }, 
					    {
							id:"database_developer_button_java_hibernate_model",
							view:"button",
							value:"java H-model",
							tooltip:"java hibernate model 을 생성한다. 단축키 :  ALT+2",
							click:"javaHibernateModel"
					    }, 
					    {
							id:"database_developer_button_java_model_set",
							view:"button",
							value:"java setter",
							tooltip:"java model 의 setter 를 생성한다. 단축키 :  ALT+3",
							click:"javaModelSet"
					    }, 
					    {
							id:"database_developer_button_java_model_get",
							view:"button",
							value:"java getter",
							tooltip:"java model 의 getter 를 생성한다. 단축키 :  ALT+4",
							click:"javaModelGet"
					    },				      
					    {
							id:"database_developer_button_mybatis_select",
							view:"button",
							value:"mybatis select",
							tooltip:"mybatis select 구문을 생성한다. 단축키 :  ALT+5",
							click:"mybatisSelect"
							
					    }, 
					    {
							id:"database_developer_button_mybatis_insert",
							view:"button",
							value:"mybatis insert",
							tooltip:"mybatis insert 구문을 생성한다. 단축키 :  ALT+6",
							click:"mybatisInsert"						
					    }, 
					    {
							id:"database_developer_button_mybatis_update",
							view:"button",
							value:"mybatis update",
							tooltip:"mybatis update 구문을 생성한다. 단축키 :  ALT+7",
							click:"mybatisUpdate"
					    }, 
					    {
							id:"database_developer_button_mybatis_delete",
							view:"button",
							value:"mybatis delete",
							tooltip:"mybatis delete 구문을 생성한다. 단축키 :  ALT+8",
							click:"mybatisDelete"
					    }, 
					    {
							id:"database_developer_button_mybatis_result",
							view:"button",
							value:"mybatis result",
							tooltip:"mybatis result 를 생성한다. 단축키 :  ALT+9",
							click:"mybatisResultMap"						
					    }
					] // end cols	
				},				      
				{
					// 에디터 창
					id:"database_java_input",	
					view : "codemirror-editor-java",
				}] // end rows
			}]		
		}]
	}
];

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
	if(database.usePLSQL){
		// PLSQL인 경우 별도 가공을 하지 않고 PLSQL 모드로 변경 한다.
	}
	else if(""!=doc.getSelection()){ // drag 되어 있는 쿼리가 있으면, 해당 부분만 가져온다.
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
	return retQuery;
}

/**
 * 데이터 베이스 쿼리 실행
 * 결과창의 datarow 가 설정된 값 이상이고, 스크롤을 아래로 더 내리고자 하면, Next Data 를 로딩 한다.
 * 결과 창에 대한 마지막 실행 쿼리를 보유하고 있어야 한다.
 * 결과 창에 대한 limit 와 offset 을 가고 있어야 한다.
 */
var executeQuery = function (isNextData,resultView){
	executeQueryParams.id=database.id
	executeQueryParams.usePLSQL=$$("database_query_develop_mode").getValue()=="PLSQL" ? true : false;
	
	// 스크롤 한 경우에 추가 데이터가 없음을 표시하지 않는다.
	var isScrolledMessage=true;
	// view 가 정의되지 않을 경우 view 찾아낸다. 
	if(resultView==undefined || resultView==null){
		// TODO multi view 가 되면, 데이터를 카운트해서 이미 쓴 경우에 +1 해서 새로 만든다.
		resultView = $$("database_result_list_view");
	} else {
		isScrolledMessage=false;
	} 
		
	// 다음페이지 조회
	if(isNextData==true){ 
		// 조건에 부합할 경우 추가데이터 로딩을 시작 한다.
		// 현재 카운트가 Limit*페이지 값보다 클 경우에 실행
		var isAddData = resultView.count() >= resultView.config.dataLimit*resultView.config.dataPage;
		// limit 값을 나눈 몫이 0인 경우에 Next가 있을 수 있다.
		isAddData = isAddData && resultView.count()%resultView.config.dataLimit == 0;
		// 데이터가 이미 로딩중이 아니라면..
		isAddData = isAddData && !resultView.config.isDataLoading;
		
		if(isAddData){
			resultView.config.isDataLoading=true;
			// 기존에 저장되어 있던 쿼리 재 실행
			executeQueryParams.query=resultView.config.executedQuery;
			// limit 설정
			executeQueryParams.limit=resultView.config.dataLimit;
			// offset 에다가 limit 를 더해서 다음 페이지를 호출하게 처리 한다.
			executeQueryParams.offset=resultView.config.dataOffset=(dataLimit*resultView.config.dataPage);
			// 데이터를 가져온다.
			try {
				webix.message("추가 데이터 로딩을 시작합니다<br/> start : " +executeQueryParams.offset + ", end :" + parseInt(executeQueryParams.offset+executeQueryParams.limit));
				addDataParseView("/database/executeQuery", executeQueryParams, resultView.config.id);
				resultView.config.dataPage++;
			} catch (e) {
				webix.message({ type:"error", text:"추가 데이터 로딩에 실패 했습니다." });
			}
			resultView.config.isDataLoading=false;
		} else {
			if(isScrolledMessage) webix.message("추가 데이터가 더 이상 없습니다.");
		}

	} else { // 첫 실행
		// 실행 쿼리 파라메터 설정
		executeQueryParams.query=getQueryString();
 		// 데이터를 조회하여 넣는다.
		getDataParseView("/database/executeQuery", executeQueryParams, resultView.config.id,true,false,true);
		
		// 현재 실행 환경 저장
		resultView.config.executedQuery=executeQueryParams.query
		resultView.config.dataLimit = executeQueryParams.limit;
		resultView.config.dataOffset = executeQueryParams.offset;
		resultView.config.dataPage=1;
		resultView.config.isDataLoading=false;
	}
}

// 결과 창
var database_result_cell = [{

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
		{ id:"favority_query", 	view:"textarea", label:'query', name:"query", value:"", height:150 },
		{margin:5, cols:[
			{ 
				id:"favority_query_button",view:"button", value:"추가" , type:"form", 
				on:{"onItemClick":function(){
					var params = this.getFormView().getValues();
					params.databaseId = database.id;
					params.memberId = member.id;
					webix.ajax().post("/member/addMemberSaveQuery", params, function(text,data){
							if(data.json().httpStatus ==200){
								console.log(data.json());
								webix.message(data.json().message);
								database_query_favorities_view_load();
								$$("add_favority_query_popup").hide();
							} else { // 저장성공
								webix.message({ type:"error", text:data.json().message});
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
	webix.ui({
        view:"window",
        id:"add_favority_query_popup",
        width:300,
        position:"center",
        modal:true,
        head:"즐겨 찾는 쿼리 추가",
        body:webix.copy(add_favority_query_form)
    }).show();
	// 쿼리 value 입력
	$$("favority_query").setValue(obj.getAttribute("data"));
	$$("favority_memo").focus();
};

/**
 * 즐겨 찾는 쿼리 제거
 */
var removeFavorityQuery = function(id){
	webix.confirm({
		title: "즐겨찾는 쿼리 삭제",
		ok:"Yes", cancel:"No",
		text:"즐겨 찾는 쿼리를 삭제 하시겠습니까?",
		callback:function(result){
			if(result==true){
				webix.ajax().del("/member/removeMemberSaveQuery?id="+id, 
					function(text, data){
						if(data.json().httpStatus ==200){
							webix.message(data.json().message);
							database_query_favorities_view_load();
						} else { 
							webix.message({ type:"error", text:data.json().message});
						}
				});
			}
		}
	});
};

/**
 * 즐겨찾는 쿼리 로딩
 */
var database_query_favorities_view_load = function(){
	getDataParseView("/member/findMemberSaveQuery",
			{memberId:member.id,databaseId:database.id},
			"database_query_favorities_view",false,false,false);
}; 

/** 
 * 자동완성 테이블/필드 데이터 로딩
 * 1 초 이후에 실행 한다. 대량의 데이터가 들어 올 위험이 있다. 
 */
var database_query_all_field_load = function(){
	var	cachedList = webix.storage.local.get(JSON.stringify(database, null, 2)+"_autoComplete");
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
			if(null!=database.id){
				webix.ajax().get("/database/getAllFieldList",database, 
					function(text,data){
						if(data.json().httpStatus ==200 && null!=data.json().contents){
							var obj = data.json().contents;
								// 정렬
								obj.sort(function(a,b){
									return(a.tableName < b.tableName) ? -1 : (a.tableName > b.tableName) ? 1 : 0;
								});
								
								$.each(obj,function(objIndex){
									autoCompleteAddTablesAll(this.tableName,this.columnName,this.comment);								
								});
								try {
									webix.storage.local.put(JSON.stringify(database, null, 2)+"_autoComplete",obj);	
								} catch (e) {
									// 캐시에 저장 실패
								}
								
						} else {
							errorControll(data.json());
						}
					}
				);
			}		
		},1000);	
	}
};