// swagger 정의를 가져온다.
var swaggerApiDocs = null;
var getSwaggerApiDocs = function(){
	webix.ajax().get("/v2/api-docs",function(text,data){
		swaggerApiDocs=data.json();
	});
};
getSwaggerApiDocs();

// search 항목을 추가 한다.
var incident_alarm_search_elements = [];
webix.ready(function(){
	var searchParams = swaggerApiDocs.paths['/alarm/list'].get.parameters;
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
			} else { // text
				$$("incident_alarm_search_form").addView({ 
					view:"text", 
					label:param.description, 
					labelWidth:180 ,
					name:param.name});		
			}
		}
	});
	// 검색버튼 추가
	$$("incident_alarm_search_form").addView({
		id:"incident_alarm_search_form_commit",
		view:"button",
		value:"검색",
		on:{"onItemClick":function(){
			incident_alarm_list_create();
		}}
	});	

	// 알람 리스트 조회 호출 
	incident_alarm_list_create();
});

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