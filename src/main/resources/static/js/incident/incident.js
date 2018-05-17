// swagger 정의를 가져온다.
var swaggerApiDocs = null;
var getSwaggerApiDocs = function(){
	webix.ajax().get("/v2/api-docs",function(text,data){
		swaggerApiDocs=data.json();
	});
};
getSwaggerApiDocs();

// 사용 가능한 데이터베이스 로딩
var useDatabaseOptions = [];
var useDatabaseOptionsCreator = function(){
	webix.ajax().get("/database/list",function(text,data){
		if(data.json().httpStatus ==200 
				&& null!=data.json().contents){	
			var obj = data.json().contents.content;
			// 기본값 입력
			useDatabaseOptions.push({
				id:"",
				value:'database 를 선택 하세요'
			});
			$.each(obj,function(index,db){
				useDatabaseOptions.push({
					id:db.id,
					value:db.hostAlias+' ['+db.account+"@"+db.host+':'+db.port+']'
				});
			});
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
	});
}
// 회원의 데이터베이스 로딩
useDatabaseOptionsCreator();

// search 항목을 추가 한다.
var incident_alarm_search_elements = [];
webix.ready(function(){
	var searchParams = swaggerApiDocs.paths['/alarm/list'].get.parameters;
	var resetValues = [];
	$.each(searchParams,function(index,param){
		if(param.name != "useCache" &&  param.name != "apikey"){
			if(param.enum!=undefined){
				$$("incident_alarm_search_form").addView({ 
					view:"combo", 
					label:param.description, 
					labelWidth:180 ,
					value:"",
					options:param.enum,
					name:param.name});		
			} else if (param.name.indexOf("databaseId") >= 0){
				$$("incident_alarm_search_form").addView({ 
					view:"select", 
					label:param.description, 
					labelWidth:180 ,
					value:"",
					options:useDatabaseOptions,
					name:param.name});		
			} else { // text
				$$("incident_alarm_search_form").addView({ 
					view:"text", 
					label:param.description, 
					labelWidth:180 ,
					name:param.name,
					on:{"onKeyPress":function(key,e){ // enter 검색 추가
						if(key==13) incident_alarm_list_create();
					}}
				});		
			}
			// reset-value 
			resetValues[param.name]="";
		}
	});
	// 검색버튼 추가
	$$("incident_alarm_search_form").addView({
		cols:[{
			id:"incident_alarm_search_rest",
			view:"button",
			value:"리셋",
			on:{"onItemClick":function(){
				$$("incident_alarm_search_form").setValues(resetValues);
			}}
		}, {
			id:"incident_alarm_search_form_commit",
			view:"button",
			value:"검색",
			on:{"onItemClick":function(){
				incident_alarm_list_create();
			}}
		}] // end cols
	});	

	// 알람 리스트 조회 호출 
	incident_alarm_list_create();
	// 알람 신규 등록 팝업 폼 생성
	add_incident_alarm_form_creator();
});

/**
 * 알람 리스트
 */
var incident_alarm_list_create = function(){
	// 리스트에서 표시할 데이터를 가져온다.
	if($$("incident_alarm_list_view").config.columns.length==0){
		var fields = swaggerApiDocs.definitions['알람작업 리스트'].properties;
		var loop=0;
		$.each(fields,function(header,obj){
			$$("incident_alarm_list_view").config.columns[loop]={};
			$$("incident_alarm_list_view").config.columns[loop].id = header;
			$$("incident_alarm_list_view").config.columns[loop].header = obj.description;
			$$("incident_alarm_list_view").config.columns[loop].adjust = true;
			loop++;
		});
		$$("incident_alarm_list_view").refreshColumns();
	} // end create header
	getDataParseView("/alarm/list",$$("incident_alarm_search_form").getValues(),"incident_alarm_list_view",false,false,false);
};

/**
 * 알람 신규 등록 팝업
 */
var add_incident_alarm_popup = function(){
	if($$("add_incident_alarm_popup")==undefined){
		webix.ui({
		    view:"window",
		    id:"add_incident_alarm_popup",
			width:900,
			autoheight:true,
		    position:"center",
		    modal:true,
		    head:"Add Incident Alarm",
		    body:{
		    	id:" add_incident_alarm_form",
		    	view:"form",
		    	borderless:true,
		    	elements: add_incident_alarm_form_elements
		    }
		}).show();
	}
	$$("add_incident_alarm_popup").show();
}

/**
 * 알람 팝업 elements 생성
 */
var add_incident_alarm_form_elements = [];
var add_incident_alarm_form_creator = function(){
	var path='/alarm/add';
	var formParams=swaggerApiDocs.paths[path].post.parameters;
	$.each(formParams,function(index,param){
		if(param.name != "useCache" &&  param.name != "apikey"){
			// label을 명칭과 설명으로 나눈다.
			var discriptions = param.description.split("||");
			// cron 값은 미리 설정한다.
			var placeholder = param.name.indexOf("schedule") >= 0 ? "* */10 * * * *" : ""; 
			// 필수값 여부를 추가 한다.
			var required = param.required ? " * " : " ";
			if(param.enum!=undefined){
				add_incident_alarm_form_elements.push({
					cols:[{
						view:"combo", 
						label:discriptions[0] + required, 
						labelWidth:130,
						adjust:true,
						value:"",
						options:param.enum,
						name:param.name
					},{
				    	view: "label",
						label: discriptions[1],
						adjust:true
					}]
				});		
			} else if (param.name.indexOf("databaseId") >= 0){
				add_incident_alarm_form_elements.push({
					cols:[{
						view:"select", 
						label:discriptions[0] + required, 
						labelWidth:130,
						adjust:true,
						value:"",
						options:useDatabaseOptions,
						name:param.name
					},{
				    	view: "label",
						label: discriptions[1],
						adjust:true
					}]
				});				
			} else { // text
				//placeholder:placeholder 를 넣어야 하나, 값이 유지되어야 해서 value 에 넣는다.
				add_incident_alarm_form_elements.push({ 
					cols:[{
						view:"text",
						label:discriptions[0] + required, 
						labelWidth:130,
						adjust:true,
						name:param.name,
						value:placeholder
					},{
				    	view: "label",
						label: discriptions[1],
						adjust:true
					}]
				});		
			}
		}
	});
	
	add_incident_alarm_form_elements.push({
		cols:[{
				view:"button", value:"취소", click:function() { // 취소
					$$("add_incident_alarm_popup").hide();	
				} ,hotkey: "esc"
			},{
				view:"button", value:"등록", click:function(){// 등록
					webix.ajax().post(path, $("add_incident_alarm_form").getValues(), function(text,data){
						if(data.json().httpStatus==200){
							webix.message(data.json().message);
							incident_alarm_list_create();
						} else {
							webix.message({ type:"error", text:data.json().message });
						}
					});
					$$("add_incident_alarm_popup").hide();
				}
			}
		] // end cols
	});
};