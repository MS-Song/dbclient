// 사용 가능한 데이터베이스 로딩
let useDatabaseOptions = [];
let useAllDatabaseOptions = [];
let useDatabaseOptionsCreator = function(){
	// 사용자 접근 가능 Database
	webix.ajax().get("/database/list",{accessAll:false},function(text,data){
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
	// 전체 데이터베이스 --> 승인 이후에 사용함
	webix.ajax().get("/database/list",{accessAll:true},function(text,data){
		if(data.json().httpStatus ==200 
				&& null!=data.json().contents){	
			var obj = data.json().contents.content;
			// 기본값 입력
			useAllDatabaseOptions.push({
				id:"",
				value:'database 를 선택 하세요'
			});
			$.each(obj,function(index,db){
				useAllDatabaseOptions.push({
					id:db.id,
					value:db.hostAlias+' ['+db.account+"@"+db.host+':'+db.port+']'
				});
			});
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
	});

	
}
useDatabaseOptionsCreator();

// 검색 화면
var search_form_creator = function(){
	// 검색 조건 doc 로딩
	let searchParams = swaggerApiDocs.paths['/alarm/list'].get.parameters;
	let elementsList = [];

	// 검색 조건 로딩
	$.each(searchParams,function(index,param){
		// 제외 문자열
		if($.inArray(param.name,excludeParams) == -1) {
			elementsList.push(getFromView(param,false,false));
		}
	});
	
	// 페이지 element 추가
	elementsList.push({ 
		id:"incident_alarm_search_page", 	
		view:"text", 	
		name:"page",
		value:1,
		type:"hidden",
		height:0,
		width:0,
	});
	
	elementsList.push({
		cols:[{
			id:"incident_alarm_search_reset",
			view:"button",
			value:"리셋",
			on:{"onItemClick":function(){
				$$("incident_alarm_search_form").setValues("");
			}}
		}, {
			id:"incident_alarm_search_form_commit",
			view:"button",
			value:"검색",
			on:{"onItemClick":function(){
				// 페이지 초기화
			    $$('incident_alarm_search_page').setValue(0);
			    $$("incident_alarm_list_page").config.page=0;
			    // 리스트 검색
				incident_alarm_list_create();
			}}
		}] // end cols
	});	

	// eleements 추가
	for(let index in elementsList){
		$$("incident_alarm_search_form").addView(elementsList[index]);	
	}
}

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
	
	
	// progress 시작
	try {
		$$("incident_alarm_list_view").showProgress();
	} catch (e) {
		// progress 가 없을 경우 생성 한다음 다시 실행 한다.
		webix.extend($$("incident_alarm_list_view"), webix.ProgressBar);
		$$("incident_alarm_list_view").showProgress();
	}

	// 데이터 로딩
	webix.ajax().get("/alarm/list",  $$("incident_alarm_search_form").getValues(), function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents){

			$$("incident_alarm_list_page").config.size=data.json().contents.size;
			$$("incident_alarm_list_page").config.count=data.json().contents.totalElements;
			$$("incident_alarm_list_page").refresh();
			$$("incident_alarm_list_view").clearAll();
			$$("incident_alarm_list_view").parse(data.json().contents.content);		
			$$("incident_alarm_list_view").refresh();
		} else {
			webix.message({ type:"error", text:data.json().message });	
		}
		// progress 를 닫는다.
		$$("incident_alarm_list_view").hideProgress();
	});
};

// 항목 로딩을 지연시키기 위한 처리
var swaggerlazyLoading = function(){
	if(null==swaggerApiDocs || useDatabaseOptions.length==0){
		setTimeout(() => {swaggerlazyLoading()}, 100);		
	} else {
		// 검색창 호출
		search_form_creator();
		// 알람 리스트 조회 호출
		incident_alarm_list_create();
	}
}

// 항목 로딩
webix.ready(function(){
	// 검색창과, 알람 리스트 지연 호출
	swaggerlazyLoading();
});


/**
 * 알람 등록 팝업
 */
var incident_alarm_popup = function(alarmItem){

	// 일반 사용자는 등록을 차단 한다.
	if(member.authType == '' || member.authType =='NORMAL'){
		return alert("개발자만 등록/수정이 가능 합니다.");
	}

	webix.ui({
	    view:"window",
	    id:"incident_alarm_popup",
		width:950,
		autoheight:true,
		minHeight:400,
	    position:"center",
	    modal:true,
	    head:(undefined==alarmItem ? "ADD" : "Modify")+" Incident Alarm",
	    body:{
	    	id:"incident_alarm_form",
	    	view:"form",
	    	borderless:true,
	    	elements: [],
	    	scroll:"y",
	    }
	}).show();

	// form 생성
	incident_alarm_form_creator(alarmItem);
}

/**
 * 알람 팝업 elements 생성
 */
var incident_alarm_form_creator = function(alarmItem){
	let path='';
	let formParams=[];
	let elementsList = [];

	if(undefined==alarmItem){ // 신규 등록
		// path 기록
		path='/alarm/add';
		// params 설정
		formParams=swaggerApiDocs.paths[path].post.parameters;
		// elements 객체 생성
		$.each(formParams,function(index,param){
			// 제외 문자열
			if($.inArray(param.name,excludeParams) == -1) {
				// DB 선택을 맨 위로
				if(param.name.toLowerCase().indexOf("databaseid") >=0){
					elementsList.unshift(getFromView(param,true,false));
				} else {
					elementsList.push(getFromView(param,true,false));
				}
			}
		});
		// 신규 등록 버튼 추가
		elementsList.push({
			cols:[{
					view:"button", value:"취소", click:function() { // 취소
						$$("incident_alarm_popup").hide();	
					} ,hotkey: "esc"
				},{
					view:"button", value:"등록", click:function(){// 등록
						let addAlaramParameter = $$("incident_alarm_form").getValues();
						addAlaramParameter.beforeSql = window.btoa(encodeURIComponent(addAlaramParameter.beforeSql));
						addAlaramParameter.runSql = window.btoa(encodeURIComponent(addAlaramParameter.runSql));
						webix.ajax().post(path, addAlaramParameter, function(text,data){
							if(data.json().httpStatus==200){
								webix.message(data.json().message);
								$$("incident_alarm_popup").hide();
								incident_alarm_list_create();
							} else {
								webix.message({ type:"error", text:data.json().message });
							}
						});
					}
				}
			] // end cols
		});
		
		// eleements 추가
		for(let index in elementsList){
			$$("incident_alarm_form").addView(elementsList[index]);	
		}

	} else { // 수정
		// console.log(alarmItem);
		// detail parameter 설정
		let pDetailVo=swaggerApiDocs.definitions['알람작업 상세'].properties;
		// parameter 형식으로 변경 한다.
		let pDetail = [];
		$.each(pDetailVo, function(name,value){
			pDetail.push({
				name:name,
				description:value.description,
			});
		});
		
		// moidfy able parameter 설정
		let pModify={};
		// confirm 상태 확인하여 수정범위 지정
		if(alarmItem.confirmYN == "Y"){
			path='/alarm/modifyAfterConfirm';
		} else {
			path='/alarm/modifyBeforeConfirm';
		}
		// parameter 설정
		pModify=swaggerApiDocs.paths[path].put.parameters;		
		
		// elements 객체 생성
		$.each(pDetail,function(pIndex,param){
			// 제외 문자열
			if($.inArray(param.name,excludeParams) == -1) {		
				// 수정 대상 필드를 찾는다.
				let modifyParam = null;
				$.each(pModify,function(cIndex,modify){
					let isModifyParam = param.name!='id' && param.name==modify.name;								
					isModifyParam = isModifyParam || (param.name=='sendMemberVos' && modify.name=='sendMemberIds'); 
					isModifyParam = isModifyParam || (param.name=='databaseVo' && modify.name=='databaseId'  && alarmItem.confirmYN!="Y");
					if(isModifyParam){
						modifyParam = modify;
					}
				});

				if(null!=modifyParam){
					elementsList.push(getFromView(modifyParam,true,false));	
				} else {
					elementsList.push(getFromView(param,true,true));
				}
			}
		});
		
		// 취소 버튼
		let cancelButton={
			view:"button", value:"취소", click:function() { // 취소
				$$("incident_alarm_popup").hide();	
			} ,hotkey: "esc"
		}

		// 즉시 실행 버튼
		let runNow = {
			view:"button", value:"즉시 실행-전체구성원에게 메일 발송", click:function(){
				webix.ajax().put('/alarm/runNow',{id:alarmItem.id,test:"false"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		}
		
		// 테스트 실행 
		let runTest = {
			view:"button", value:"테스트 실행-본인에게만 메일 발송", click:function(){
				webix.ajax().put('/alarm/runNow',{id:alarmItem.id,test:"true"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		}
		
		// 수정 버튼
		let modifyButton ={
			view:"button", value:"수정", click:function(){// 수정
				let modifyAlaramParameter = $$("incident_alarm_form").getValues();
				modifyAlaramParameter.beforeSql = window.btoa(encodeURIComponent(modifyAlaramParameter.beforeSql));
				modifyAlaramParameter.runSql = window.btoa(encodeURIComponent(modifyAlaramParameter.runSql));
				webix.ajax().put(path, modifyAlaramParameter, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("incident_alarm_popup").hide();
						incident_alarm_list_create();
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		} 

		// 승인 요청
		let confirmRequestButton={
			view:"button", value:"승인요청", click:function(){// 승인요청
				webix.ajax().put('/alarm/confirmRequest', {id : alarmItem.id}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("incident_alarm_popup").hide();
						incident_alarm_list_create();
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		}
		
		// 승인 버튼
		let confirmButton={
			view:"button", value:"승인", click:function(){// 승인
				webix.ajax().put('/alarm/confirm', {id : alarmItem.id, confirmYN:"Y"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("incident_alarm_popup").hide();
						incident_alarm_list_create();
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		}

		// 승인 취소
		let confirmCancelButton={
			view:"button", value:"승인취소", click:function(){
				webix.ajax().put('/alarm/confirm', {id : alarmItem.id, confirmYN:"N"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("incident_alarm_popup").hide();
						incident_alarm_list_create();
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		}

		// 테스트 실행 및 즉시 실행
		if(alarmItem.confirmYN == "Y"){
			elementsList.push({cols:[runTest,runNow]});
		} else {
			elementsList.push({cols:[runTest,{}]});
		}
		
		if(alarmItem.confirmYN != "Y" && member.authType=='ADMIN') { 					// 승인 버튼 노출
			elementsList.push({cols:[cancelButton,modifyButton,confirmButton]});	
		} else if(alarmItem.confirmYN == "Y" && member.authType=='ADMIN') {				// 승인 취소 버튼
			elementsList.push({cols:[cancelButton,modifyButton,confirmCancelButton]});
		}  else if(alarmItem.confirmYN != "Y") {										// 승인 전 승인 요청 버튼
			elementsList.push({cols:[cancelButton,modifyButton,confirmRequestButton]});
		}  else {																		// 승인 후 수정
			elementsList.push({cols:[cancelButton,modifyButton]});
		}

		// eleements 추가
		for(let index in elementsList){
			$$("incident_alarm_form").addView(elementsList[index]);	
		}

		// run log 에서 온 경우에는 Id가 다르다. -- UI 상의 문제로 변경 처리 
		let alarmId;
		if(undefined!=alarmItem.alarmId){
			alarmId=alarmItem.alarmId;
		} else if(undefined!= alarmItem.id){
			alarmId=alarmItem.id;
		}
		// 대상 데이터를 로딩
		webix.ajax().get('/alarm/detail', {id:alarmId}, function(text,data){
			if(data.json().httpStatus==200){
				// 설정 값
				let values = {};
				$.each(data.json().contents,function(key,value){
					if(value!=null){
						if(key.indexOf('MemberVo')>= 0) { // 회원인 경우
							if(key=='sendMemberVos'){ // 전송 대상자 -- 배열로 들어온다.
								var sendMembers=[];
								for(index in value){
									sendMembers.push(value[index].id);
								}
								// 키도 변경 한다.
								values['sendMemberIds']=sendMembers.join(',');
							} else { // 그외
								values[key]='['+ value.loginId +']'+'['+ value.teamName +'] ' + value.name;							
							}
						} else if (key.indexOf('databaseVo')>= 0){ // database
																	// 경우
							values['databaseId']=value.id;
						} else {
							values[key]=value;
						}
					}
				});
				// form 에 값을 넣는다.
				$$("incident_alarm_form").setValues(values);
			} else {
				webix.message({ type:"error", text:data.json().message });
				$$("incident_alarm_popup").hide();
			}
		});
	}
};

/**
 * crontab schedule 확인
 */
var crontabSchedulePopup = function(view,value){
	webix.ui({
	    view:"window",
	    id:"crontab_schedule_popup",
		width:600,
		minHeight:500,
		autoheight:true,
	    position:"center",
	    modal:true,
	    head:{
			id:"crontab_schedule_cansel", 	
			view:"button", 
			label:'Crontab 점검 팝업 닫기', 
			click:function(){
			    $$("crontab_schedule_popup").hide();
			},hotkey: "esc"
		},
	    body:{
	    	id:"crontab_schedule_form",
	    	view:"form",
	    	borderless:true,
	    	elements: [{
	    		rows:[{
			    	id:"crontab_generator_url",
			    	view: "label",
					label: "<a href='https://www.freeformatter.com/cron-expression-generator-quartz.html' target='_blank'>crontab expression 생성기</a> (년을 제외하세요)",
					height:25,
					adjust:true
	    			},{
	    			cols:[{
	    				id:"crontab_schedule_expression",
	    				view:"text", 	
	    				label:'schedule', 			
	    				name:"schedule",
	    				value:value,
	    				placeholder:"* */10 * * * *",
	    				width:250,
	    				on:{"onKeyPress":function(key,e){// 실행
	    					// enter 를 입력하면, 점검을 시작 한다.
	    					if(key==13){
	    						 $$("crontab_schedule_validate_button").callEvent("onItemClick");
	    					}
	    				}}
	    			},{
	    				id:"crontab_schedule_validate_button", 	
	    				view:"button", 
	    				label:'점검', 
	    				click:function(){
	    					webix.ajax().get("/alarm/nextSchedule",this.getFormView().getValues(),function(text,data){
	    						if(data.json().httpStatus ==200 
	    								&& null!=data.json().contents){	
	    							let beforeDate = null;
	    							
	    							$$("crontab_schedule_list").clearAll();
	    							$.each(data.json().contents, function(key,value){
	    								// 시간차 구하기
	    								let gap = "";
	    								let runDate = new Date(value);
	    								if(beforeDate != null){
	    									gap = gapDate(beforeDate,runDate);
	    								}
	    								
		    							$$("crontab_schedule_list").add({
		    								"crontab_schedule_next" : formatDate(runDate)
		    								,"crontab_schedule_gap" : gap});	    
		    							// 이전 시간 값 설정
		    							beforeDate=runDate;
	    							})
	    						} else {
	    							webix.message({ type:"error", text:data.json().message });
	    						}
	    					});
	    				}
	    			},{
	    				id:"crontab_schedule_accept", 	
	    				view:"button", 
	    				label:'적용', 
	    				click:function(){
    					    $$(view).setValue($$("crontab_schedule_expression").getValue());
    					    $$("crontab_schedule_popup").hide();
	    				}
	    			}] // end cols
	    		},{
		        	id:"crontab_schedule_list",
		        	view:"datatable",
					tooltip:true,
					select:"row",
					resizeColumn:true,
					columns:[
						{ id:"crontab_schedule_next",	header:"다음 Crontab Schedule 리스트", width:300},
						{ id:"crontab_schedule_gap",	header:"시간 간격", 					width:200},
            		],
					data:[]
	    		}]// end rows;
	    	}] // end elements
	    }
	}).show();
};

// confirm 메일을 통해 진입할 경우
var confirmPopupLazyLoading = function(){
	if(null==member || undefined==member || undefined==member.authType){
		setTimeout(() => {confirmPopupLazyLoading()}, 100);		
	} else {
		incident_alarm_popup({"id":getParam("id"),"confirmYN":null});
	}
}

webix.ready(function(){
	// 승인을 위해 호출한 것으로 간주하고, 승인하도록 처리 해 준다.
	if(getParam("id") > 0){
		confirmPopupLazyLoading();
	}
});

