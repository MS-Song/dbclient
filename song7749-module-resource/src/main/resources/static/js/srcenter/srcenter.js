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
			errorControll(data.json());
		}
	});

	// 전체 데이터베이스 --> 관리자만 사용 가능함
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
			errorControll(data.json());
		}
	});
}
useDatabaseOptionsCreator();

// 검색 화면
let search_form_creator = function(){
	// 검색 조건 doc 로딩
	let searchParams = swaggerApiDocs.paths['/srDataRequest/list'].get.parameters;
	let elementList = [];

	// 검색 조건 로딩
	$.each(searchParams,function(index,param){
		// 제외 문자열
		if($.inArray(param.name,excludeParams) == -1) {
			elementList.push(createWebForm(param,false,false));
		}
	});
	
	// 페이지 element 추가
	elementList.push({
		id:"sr_data_request_search_page",
		view:"text", 	
		name:"page",
		value:1,
		type:"hidden",
		height:0,
		width:0,
	});
	
	elementList.push({
		cols:[{
			id:"sr_data_request_search_reset",
			view:"button",
			value:"리셋",
			on:{"onItemClick":function(){
				$$("sr_data_request_search_form").setValues("");
			}}
		}, {
			id:"sr_data_request_search_form_commit",
			view:"button",
			value:"검색",
			on:{"onItemClick":function(){
				// 페이지 초기화
			    $$('sr_data_request_search_page').setValue(0);
			    $$("sr_data_request_list_page").config.page=0;
			    // 리스트 검색
				sr_data_request_list_create();
			}}
		}] // end cols
	});	

	// elements 추가
	for(let index in elementList){
		$$("sr_data_request_search_form").addView(elementList[index]);
	}
}



/**
 *  리스트
 */
let sr_data_request_list_create = function(){
	// 리스트에서 표시할 데이터를 가져온다.
	if($$("sr_data_request_list_view").config.columns.length==0){
		let loop=0;
		// swagger 의 정보를 읽어서 리스트 생성
		let fields = swaggerApiDocs.definitions['SR DATA REQUEST MODEL'].properties;
		$.each(fields,function(header,obj){
			if(obj.type=="string" || obj.type=="integer"){
				$$("sr_data_request_list_view").config.columns[loop]={};
				$$("sr_data_request_list_view").config.columns[loop].id = header;
				$$("sr_data_request_list_view").config.columns[loop].header = obj.description;
				$$("sr_data_request_list_view").config.columns[loop].adjust = true;
				loop++;
			}
		});
		$$("sr_data_request_list_view").refreshColumns();
	} // end create header
	
	
	// progress 시작
	try {
		$$("sr_data_request_list_view").showProgress();
	} catch (e) {
		// progress 가 없을 경우 생성 한다음 다시 실행 한다.
		webix.extend($$("sr_data_request_list_view"), webix.ProgressBar);
		$$("sr_data_request_list_view").showProgress();
	}

	// 데이터 로딩
	webix.ajax().get("/srDataRequest/list",  $$("sr_data_request_search_form").getValues(), function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents){

			$$("sr_data_request_list_page").config.size=data.json().contents.size;
			$$("sr_data_request_list_page").config.count=data.json().contents.totalElements;
			$$("sr_data_request_list_page").refresh();
			$$("sr_data_request_list_view").clearAll();
			$$("sr_data_request_list_view").parse(data.json().contents.content);		
			$$("sr_data_request_list_view").refresh();
		} else {
			errorControll(data.json());
		}
		// progress 를 닫는다.
		$$("sr_data_request_list_view").hideProgress();
	});
};

// 항목 로딩을 지연시키기 위한 처리 -- 회원 정보까지 조회를 기다린다.
let swaggerlazyLoading = function(){
	// 로딩을 기다리는 경우.. swagger data 확인
	let isLoading = null==swaggerApiDocs || useDatabaseOptions.length==0;
	// 회원 정보가 로딩
	isLoading = isLoading || member.name == undefined;

	if(isLoading){
		setTimeout(() => {swaggerlazyLoading()}, 100);
	} else {
		// 검색창 호출
		search_form_creator();
		//  리스트 조회 호출
		sr_data_request_list_create();
	}
}

// 항목 로딩
webix.ready(function(){
	// 검색창과,  리스트 지연 호출
	swaggerlazyLoading();
});


/**
 * 등록 팝업
 */
let sr_data_request_popup = function(requestItem){
	// 일반 사용자는 등록을 차단 한다.
	if(member.authType == '' || member.authType =='NORMAL'){
		return alert("개발자/관리자 만 등록/수정이 가능 합니다.");
	}

	webix.ui({
	    view:"window",
	    id:"sr_data_request_popup",
		width:'100%',
		height:'100%',
	    position:"center",
	    modal:true,
	    head:(undefined==requestItem ? "ADD" : "Modify")+" SR Data Request",
	    body:{ cols:[
	    	{
				id:"sr_data_request_form_left",
				view:"form",
				borderless:true,
				elements: [],
				scroll:"y"
			},
			{
				id:"sr_data_request_form_right",
				view:"form",
				borderless:true,
				elements: [],
				scroll:"y"
			},
		]},
	}).show();

	// form 생성
	sr_data_request_form_creator(requestItem);
}

/**
 * 팝업 elements 생성
 */
let sr_data_request_form_creator = function(requestItem){

	let path='';							// swagger path
	let formParams=[];						// swagger form parameters
	let elementList = [];					// request from elements
	let dynamicElements = [];				// dynamic form elements

	// common buttons
	// 취소
	let buttonCancel = {
		view:"button", value:"취소", click:function() {
			$$("sr_data_request_popup").hide();
		} ,hotkey: "esc"
	};

	// SQL 조건 추가
	let buttonAddConditions = {
		view:"button", value:"조건 추가 >> ", click:function() {
			addConditions(dynamicElements);
		}
	};

	// Conditions 폼 생성
	let addConditions = function(obj) {
		$$("sr_data_request_form_right").addView({
			view:"fieldset",
			label:"SQL Condition Info",
			body:{cols:[
				{
					rows: webix.copy(obj)
				},
				{
					width:50,
					view:"button",
					value:"삭제", click:function() {
						if("Y" == $$("sr_data_request_form_left").queryView({name:"confirmYN"},'all')[0].getValue()){
							webix.message({ type:"error", text:"승인이 완료된 데이터는 조건 수정이 불가능 합니다." });
							return;
						}
						let isRemove = confirm("정말 삭제 하시겠습니까?");
						if(isRemove){
							let removeID = this.getParentView().getParentView().config.id;
							$$("sr_data_request_form_right").removeView(removeID);
						} else {
							return ;
						}
					}
				}]
			}
		});
	};

	if(undefined==requestItem){ // 신규 등록
		// path 기록
		path='/srDataRequest/add';
		// params 설정
		formParams=swaggerApiDocs.paths[path].post.parameters;
		// elements 객체 생성
		$.each(formParams,function(index,param){
			// 제외 문자열
			if($.inArray(param.name,excludeParams) == -1) {
				// console.log(param);
				// DB 선택을 맨 위로
				if(param.name.toLowerCase().indexOf("database") >=0){
					elementList.unshift(createWebForm(param,true,false));
				}
				// 배열이면서, 컨디션인 경우에만..
				else if(param.name.toLowerCase().indexOf("condition") >= 0){
					dynamicElements.push(createWebForm(param,true,false));
				}
				// 그 외
				else {
					elementList.push(createWebForm(param,true,false));
				}
			}
		});

		// 등록 버튼
		let buttonResist = {
			view:"button", value:"등록", click:function(){
				// 왼쪽 뷰의 데이터 정리
				let addLeftParameter = $$("sr_data_request_form_left").getValues();
				addLeftParameter.runSql = window.btoa(encodeURIComponent(addLeftParameter.runSql));

				// 오른쪽 폼은 멀티플 데이터 이다.
				let addRightParameter = $$("sr_data_request_form_right").getValues();
				$.each(addRightParameter,function(name){
					let paramViews = $$("sr_data_request_form_right").queryView({name:name},'all')
					let values = []
					$.each(paramViews,function(index){
						let currentValue = "";
						// sql 은 인코딩 처리 한다.
						if(name=="conditionWhereSql" || name=="conditionValue"){
							currentValue = window.btoa(encodeURIComponent(this.getValue()));
						} else {
							currentValue = this.getValue();
						}
						// 원본 값이 undefined 인 경우에는 공백으로 전송 한다.
						values.push(this.getValue() == "undefined" ? "" : currentValue);
					});
					addLeftParameter[name] = values.join(",");
				});

				webix.ajax().post(path, addLeftParameter, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		};

		// 구역 구분
		elementList.unshift({ template:"SR Request INFO", type:"section"});

		// 등록/취소 조건 추가
		elementList.push({
			cols:[buttonCancel,buttonResist]
		});

		let buttonRunTestAlert = {
			view:"button", value:"테스트", click:function() {
				alert("테스트는 등록 후 수정 시 가능 합니다.");
			}
		};

		// 조건 추가 및 테스트
		elementList.push({
			cols:[buttonRunTestAlert,buttonAddConditions]
		});

		// elements left 추가
		for(let index in elementList){
			$$("sr_data_request_form_left").addView(elementList[index]);
		}



	} else { // 수정

		// confirm 상태 확인하여 수정범위 지정
		if(requestItem.confirmYN == "Y"){
			path='/srDataRequest/modifyAfterConfirm';
		} else {
			path='/srDataRequest/modifyBeforeConfirm';
		}

		// parameter 설정
		formParams=swaggerApiDocs.paths[path].put.parameters;

		// 객체 모델
		let mSrRequest=swaggerApiDocs.definitions['SR DATA REQUEST MODEL'].properties;
		let mSrRequestCondition=swaggerApiDocs.definitions['SR Data Request 검색 조건'].properties;

		// parameter 형식을 맞춘다.
		let elementsModel = [];
		$.each(mSrRequest, function(mName,mObject){
			elementsModel.push({
				name:mName,
				description:mObject.description
			});
		});

		let dynamicElementsModel = [];
		$.each(mSrRequestCondition, function(mName,mObject){
			dynamicElementsModel.push({
				name:mName,
				description:mObject.description
			});
		});

		// 수정 가능한 form 을 생성 한다.
		$.each(formParams,function(index,param){
			// 제외 문자열
			if($.inArray(param.name,excludeParams) == -1) {
				// DB 선택을 맨 위로
				let isDisable = param.name == 'id'; // ID 는 수정할 수 없음으로, 예외처리
				if(param.name.toLowerCase().indexOf("database") >=0){
					elementList.unshift(createWebForm(param,true,isDisable));
				}
				// 배열이면서, 컨디션인 경우에만..
				else if(param.name.toLowerCase().indexOf("condition") >= 0){
					dynamicElements.push(createWebForm(param,true,isDisable));
				}
				// 그 외
				else {
					elementList.push(createWebForm(param,true,isDisable));
				}
			}
		});

		// 추가 정보성 데이터를 넣는다.
		$.each(elementsModel,function(index,param){
			let isAdditional = true;
			// 1차 이름으로 걸러낸다.
			$.each(elementList, function(i,o){
				if(param.name==o.name){
					isAdditional=false;
					return;
				}
				// DB 선택이 이미 있는 경우 DB는 걸러 낸다.
				if(o.name.toLowerCase().indexOf("database") >=0
					&& param.name.toLowerCase().indexOf("database") >= 0){
					isAdditional=false;
					return;
				}
			});

			// 2차 Dynamic 처리는 제외
			if(param.name.toLowerCase().indexOf("condition") >= 0){
				isAdditional=false;
			}

			// 이미 들어 있거나, 제외 되어야 항목은 제거 한다.
			if(isAdditional){
				elementList.push(createWebForm(param,true,true));
			}
		});

		// dynamic 에도 수정 가능 필드를 정의 한다.
		$.each(dynamicElementsModel,function(index,param) {
			let isAdditional = true;
			$.each(dynamicElements, function(i,o){
				// 모델에는 condition 이라는 prefix 가 생략되어 있어 이름을 맞춘다.
				if(("condition"+param.name).toLowerCase()==o.name.toLowerCase()){
					isAdditional=false;
					return;
				}
			});

			// 이미 들어 있거나, 제외 되어야 항목은 제거 한다. (ID 는 제외 처리 한다)
			if(isAdditional && param.name!='id'){
				dynamicElements.push(createWebForm(param,true,true));
			}
		});

		// 수정 버튼
		let modifyButton ={
			view:"button", value:"수정", click:function(){
				// 왼쪽 뷰의 데이터 정리
				let addLeftParameter = $$("sr_data_request_form_left").getValues();
				addLeftParameter.runSql = window.btoa(encodeURIComponent(addLeftParameter.runSql));

				// 오른쪽 폼은 멀티플 데이터 이다.
				let addRightParameter = $$("sr_data_request_form_right").getValues();
				$.each(addRightParameter,function(name){
					let paramViews = $$("sr_data_request_form_right").queryView({name:name},'all')
					let values = []
					$.each(paramViews,function(index){
						let currentValue = "";
						// sql 은 인코딩 처리 한다.
						if(name=="conditionWhereSql" || name=="conditionValue"){
							currentValue = window.btoa(encodeURIComponent(this.getValue()));
						} else {
							currentValue = this.getValue();
						}
						// 원본 값이 undefined 인 경우에는 공백으로 전송 한다.
						values.push(this.getValue() == "undefined" ? "" : currentValue);

					});
					addLeftParameter[name] = values.join(",");
				});

				webix.ajax().put(path, addLeftParameter, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						$$("sr_data_request_run_popup").hide();
						sr_data_request_run_popup(requestItem);
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		}

		// 승인 요청
		let confirmRequestButton={
			view:"button", value:"승인요청", click:function(){
				webix.ajax().put('/srDataRequest/confirmRequest', {id : requestItem.id, confirmYN:"Y"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						$$("sr_data_request_run_popup").hide();
						sr_data_request_run_popup(requestItem);
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		}

		// 승인 취소 요청
		let confirmCancelRequestButton={
			view:"button", value:"승인취소요청", click:function(){
				webix.ajax().put('/srDataRequest/confirmRequest', {id : requestItem.id, confirmYN:"N"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						$$("sr_data_request_run_popup").hide();
						sr_data_request_run_popup(requestItem);
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		}

		// 승인 버튼
		let confirmButton={
			view:"button", value:"승인", click:function(){// 승인
				webix.ajax().put('/srDataRequest/confirm', {id : requestItem.id, confirmYN:"Y"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						$$("sr_data_request_run_popup").hide();
						sr_data_request_run_popup(requestItem);
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		}

		// 승인 취소
		let confirmCancelButton={
			view:"button", value:"승인취소", click:function(){
				webix.ajax().put('/srDataRequest/confirm', {id : requestItem.id, confirmYN:"N"}, function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_popup").hide();
						$$("sr_data_request_run_popup").hide();
						sr_data_request_run_popup(requestItem);
						sr_data_request_list_create();
					} else {
						errorControll(data.json());
					}
				});
			}
		}

		// 담당자 변경
		let changeResistMemberButton={
			view:"button", value:"담당자변경", click:function(){
				changeResistMember(requestItem.id);
			}
		}

		if(requestItem.confirmYN != "Y" && member.authType=='ADMIN') { 					// 승인 버튼 노출
			elementList.push({cols:[buttonCancel,modifyButton,changeResistMemberButton,confirmButton]});
		} else if(requestItem.confirmYN == "Y" && member.authType=='ADMIN') {			// 승인 취소 버튼
			elementList.push({cols:[buttonCancel,modifyButton,changeResistMemberButton,confirmCancelButton]});
		}  else if(requestItem.confirmYN != "Y") {										// 승인 전 승인 요청 버튼
			elementList.push({cols:[buttonCancel,modifyButton,confirmRequestButton]});
		}  else {																		// 승인 후 수정 및 승인 취소 요청
			elementList.push({cols:[buttonCancel,modifyButton,confirmCancelRequestButton]});
		}

		// SQL 구문 확인, 테스트 실행 등 확인 작업
		elementList.push({cols:[{},buttonAddConditions]});

		// 구역 구분
		elementList.unshift({ template:"SR Request INFO", type:"section"});

		// elements 추가
		for(let index in elementList){
			$$("sr_data_request_form_left").addView(elementList[index]);
		}

		// 대상 데이터를 로딩
		webix.ajax().get('/srDataRequest/one', {id:requestItem.id}, function(text,data){
			if(data.json().httpStatus==200){
				// 설정 값
				let values = {};
				let dynamicValues = [];
				$.each(data.json().contents,function(key,value){
					if(value!=null){
						if(key.indexOf('MemberVo')>= 0) { // 회원인 경우
							values[key]='['+ value.loginId +']'+'['+ value.teamName +'] ' + value.name;
						} else if (key.indexOf('database')>= 0){ // database
							values['databaseId']=value.id;
						} else {
							values[key]=value;
						}
						//
						if(key.toLowerCase().indexOf("condition") >=0){
							$.each(value, function(index,conditionValue){
								// 기본 객체를 복제하여 값을 추가 한다.
								let addDynamicElements = webix.copy(dynamicElements);
								// 객체의 값을 매핑 처리 하기 위함
								for(let i=0; i<addDynamicElements.length;i++){
									$.each(conditionValue,function(conditionKey, conditionValue){
										// model 의 명칭에는 condition 이 빠져 있어 이를 채워서 비교해야 한다.
										if(addDynamicElements[i]['name'].toLowerCase() == ('condition'+conditionKey).toLowerCase()){
											addDynamicElements[i]['value'] = conditionValue;
											// console.log(addDynamicElements[i]['name'].toLowerCase() + " : " + conditionValue);
										}
									})
								}
								addConditions(addDynamicElements);
							});
						}
					}
				});
				// form 에 값을 넣는다.
				$$("sr_data_request_form_left").setValues(values);

			} else {
				errorControll(data.json());
				$$("sr_data_request_popup").hide();
			}
		});
	}
};

/**
 * SR Data 실행 화면 생성
 */

let sr_data_request_run_popup = function(requestItem){
	webix.ui({
		view:"window",
		id:"sr_data_request_run_popup",
		width:'100%',
		height:'100%',
		position:"center",
		modal:true,
		head:"  ",
		body:{
			cols:[
				{
					id:"sr_data_request_run_search_form",
					view:"form",
					borderless:true,
					elements: [],
					scroll: "Y",
					width:350,
					autoheight:true
				},
				{ view:"resizer"},
				{
					view:"accordion",
					multi:true,
					rows:[{
						id:"sr_data_request_run_header",
						view: "label",
						label: " ",
						height:30,
						adjust:true
					},
					{
						id:"sr_data_request_run_result_list",
						view:"datatable",
						borderless:true,
						scroll: true,
						data:[],
						select:true
					},
					{ view:"resizer"},
					{
						id:"sr_data_request_run_debug_list",
						header : "DEBUG",
						hidden:true,
						body:{cols:[{
								view : "textarea",
								id:"sr_data_request_run_sql_text",
								label:"SQL DEBUG",
								labelPosition:"top",
								adjust:true
							},{
								view : "textarea",
								id:"sr_data_request_run_error_text",
								label:"Error LOG",
								labelPosition:"top",
								adjust:true
							}
						]},
					}]
				}
		]}
	}).show();

	// form 생성
	sr_data_request_run_creator(requestItem);
}

/**
 * 팝업 elements 생성
 */
let sr_data_request_run_creator = function (requestItem){
	// 검색 가능 값을 조회 한다.
	let searchFormElements=[];

	// 일반 사용자의 경우 디버그 창을 보여주지 않고, 개발자-관리자 에게만 열어 준다.
	if(member.authType == '' || member.authType =='NORMAL'){
		$$("sr_data_request_run_debug_list").hide();
	} else {
		$$("sr_data_request_run_debug_list").show();
	}

	// 해당 SR의 검색 가능 값을 조회하여 form 을 생성 한다.
	webix.ajax().get('/srDataRequest/searchFromCreate', {id:requestItem.id}, function(text,data){
		if(data.json().httpStatus==200){
			let requestObject = data.json().contents;
			// header 설정
			$$("sr_data_request_run_popup").getHead().setHTML("[" + requestObject.id + "] " + requestObject.subject);

			// 임시 객체에 넣는다.
			let searchFormElements = [];
			$.each(requestObject.srDataConditionVos,function(index, value){
				let element = {};

				element.name 			= value.key.replace("{","").replace("}","");
				element.description 	= value.name;
				element.required 		= value.required == 'Y' ? true : false;

				if(value.type == 'ARRAY' || value.type == 'SQL'){
					element.type 		= 	'select';
					element.values 		=	[];
					if(null!=value.values){
						$.each(value.values,function(index,obj){
							element.values.push({id:obj.name == null ? '' : obj.name , value:obj.value});
						});
					}				
				} else if(value.type == 'DATE') {
					element.type = value.type;
				}
				searchFormElements.push(createWebForm(element,true,false));
			});
			// id 값을 추가로 넣는다.
			searchFormElements.push(createWebForm({name:'id',type:'hidden',value:requestObject.id},false,false));

			// 개발자/관라자의 경우 debug 창을 추가로 처리 한다.
			if($$("sr_data_request_run_debug_list").config.hidden == false){
				searchFormElements.push(createWebForm({name:"debug",type:"hidden",value:"true"},false,false));
			}

			// 다운로드 가능 기간 및 정보 노출
			//console.log(requestObject);
			let downloadInfo ="";
			if(null!=requestObject.downloadStartDate && null!=requestObject.downloadEndDate){
				downloadInfo+="[ 다운로드가능 기간 : "+ requestObject.downloadStartDate.substring(0,10) + " ~ " + requestObject.downloadEndDate.substring(0,10) + "] ";
			} else if(null!=requestObject.downloadStartDate){
				downloadInfo+="[ 다운로드가능 기간 : "+ requestObject.downloadStartDate.substring(0,10) + " 부터 ] ";
			} else if (null!=requestObject.downloadEndDate){
				downloadInfo+="[ 다운로드가능 기간 : "+ requestObject.downloadEndDate.substring(0,10) + " 까지 ] ";
			} else {
				downloadInfo+="[ 다운로드가능 기간 : 영구 ]";
			}
			downloadInfo+=" [Grid 에는 최대 100개 까지 표기 됩니다. 엑셀 다운로드를 사용하세요] "

			$$("sr_data_request_run_header").define("label", downloadInfo);
			$$("sr_data_request_run_header").refresh();

			// 검색
			let buttonSearch = {
				view:"button",
				value:"검색",
				click:function(){
					// progress 시작
					try {
						$$("sr_data_request_run_result_list").showProgress();
					} catch (e) {
						// progress 가 없을 경우 생성 한다음 다시 실행 한다.
						webix.extend($$("sr_data_request_run_result_list"), webix.ProgressBar);
						$$("sr_data_request_run_result_list").showProgress();
					}

					// 기존 헤더를 삭제 한다.
					$$("sr_data_request_run_result_list").config.columns = [];
					$$("sr_data_request_run_result_list").refreshColumns();
					// 기존 입력 데이터를 삭제 한다.
					$$("sr_data_request_run_result_list").clearAll();
					$$("sr_data_request_run_result_list").refresh();

					webix.ajax().get("/srDataRequest/runNow",  $$("sr_data_request_run_search_form").getValues(), function(text,data){
						// 디버그 모드가 활성화 된 경우 SQL 로그와 Error 로그를 넣는다.
						if($$("sr_data_request_run_debug_list").config.hidden == false){
							if(data.json().httpStatus == 200 || data.json().httpStatus == 204){
								$$("sr_data_request_run_sql_text").setValue(data.json().message);
								if(data.json().httpStatus == 204){
									$$("sr_data_request_run_result_list").config.columns = [];
									$$("sr_data_request_run_result_list").clearAll();
									webix.message({ type:"error", text:"조회된 데이터가 없습니다." });
								}
							} else {
								$$("sr_data_request_run_error_text").setValue(data.json().message);
							}
						}

						// 데이터가 잘 온 경우 데이터를 파싱하여 처리 한다.
						if(data.json().httpStatus == 200
							&& null!=data.json().contents
							&& null!=data.json().contents.length>0){
							// 헤더 루프 생성
							let loop=0;
							// header 생성
							$.each(data.json().contents[0],function(header,name){
								$$("sr_data_request_run_result_list").config.columns[loop]={};
								$$("sr_data_request_run_result_list").config.columns[loop].id = header;
								$$("sr_data_request_run_result_list").config.columns[loop].header = header;
								$$("sr_data_request_run_result_list").config.columns[loop].adjust = true;
								if(!isNaN(this)){
									$$("sr_data_request_run_result_list").config.columns[loop].sort="int";
								} else {
									$$("sr_data_request_run_result_list").config.columns[loop].sort="string";
								}
								loop++;
							});
							$$("sr_data_request_run_result_list").refreshColumns();
							$$("sr_data_request_run_result_list").parse(data.json().contents);
							$$("sr_data_request_run_result_list").refresh();
						} else {
							errorControll(data.json());
						}
						$$("sr_data_request_run_result_list").hideProgress();
					}
				);
			}};

			let buttonReset = {
				view: "button",
				value: "리셋",
				on: {
					"onItemClick": function () {
						$$("sr_data_request_run_search_form").setValues("");
					}
				}
			};

			searchFormElements.push({cols:[buttonReset,buttonSearch]});

			// 엑셀 다운로드 버튼
			let buttonExcelDownload = {
				view: "button",
				value: "엑셀다운로드",
				click: function () {
					// 기존 검색 내용이 없을 경우 실행을 하지 않는다.
					if($$("sr_data_request_run_result_list").config.columns == undefined
						|| $$("sr_data_request_run_result_list").config.columns.length==0){

						return webix.message({ type:"error", text:"조회된 결과가 없어 엑셀 생성이 불가능 합니다. 검색을 먼저 진행 하세요" });
					}
					webix.send(
						"/srDataRequest/excelDownload",
						$$("sr_data_request_run_search_form").getValues(),
						"GET",
						"iframeExcelDownload"
					)
				}
			};

			searchFormElements.push({cols:[{},buttonExcelDownload]});

			let buttonCancel = {
				view:"button", value:"닫기", click:function() {
					$$("sr_data_request_run_popup").hide();
				} ,hotkey: "esc"
			};

			let buttonModify = {
				view:"button", value:"수정", click:function() {
					sr_data_request_popup(requestItem);
				}
			}

			// 일반 사용자는 닫기만 가능
			if(member.authType == '' || member.authType =='NORMAL'){
				searchFormElements.unshift({cols:[buttonCancel,{}]});
			} else {
				searchFormElements.unshift({cols:[buttonCancel,buttonModify]});
			}

			// 구역 구분
			searchFormElements.unshift({ template:"검색 조건", type:"section"});

			// elements 추가
			for(let index in searchFormElements){
				$$("sr_data_request_run_search_form").addView(searchFormElements[index]);
			}
		} else {
			// 에러가 발생할 경우 수정 폼으로 이동 시킨다. (폼 자체를 열수 없을 경우에 한 함)
			errorControll(data.json());
			$$("sr_data_request_run_popup").hide();
			sr_data_request_popup(requestItem);
		}
	});
}

let changeResistMember = function(id){
	// 일반 사용자는 등록을 차단 한다.
	if(member.authType != 'ADMIN'){
		return alert("관리자만 사용 가능 합니다.");
	}

	// element setup
	let elements = [];
	// alarm id
	elements.push({
		view:"text",
		label:"ID",
		labelWidth:100,
		adjust:true,
		name:"srDataRequestId",
		readonly:true,
		value:id
	});
	// member id
	elements.push({
		view:"text",
		label:"등록회원",
		labelWidth:100,
		name:"resistMemberId",
		readonly:true,
		on:{"onItemClick":function(view,e){
				member_list_popup(view,false);
			}}
	});

	// 담당자 변경
	elements.push({
		cols:[{
			view:"button", value:"변경", click:function(){
				webix.ajax().put('/srDataRequest/modifyResistMember',  $$("sr_data_request_change_resist_member_form").getValues(), function(text,data){
					if(data.json().httpStatus==200){
						webix.message(data.json().message);
						$$("sr_data_request_change_resist_member_popup").hide();
						$$("sr_data_request_popup").hide();
						sr_data_request_list_create();
					} else {
						webix.message({ type:"error", text:data.json().message });
					}
				});
			}
		},
			{
				view:"button", value:"취소", click:function() {
					$$("sr_data_request_change_resist_member_popup").hide();
				}
			}
		]
	});

	webix.ui({
		view:"window",
		id:"sr_data_request_change_resist_member_popup",
		width:500,
		height:400,
		position:"center",
		modal:true,
		head:"담당자 변경 선택",
		body:{
			id:"sr_data_request_change_resist_member_form",
			view:"form",
			borderless:true,
			elements:elements
		}
	}).show();
};

// confirm 메일을 통해 진입할 경우
let confirmPopupLazyLoading = function(){
	if(null==member || undefined==member || undefined==member.authType){
		setTimeout(() => {confirmPopupLazyLoading()}, 100);		
	} else {
		// URL 을 통해서 진입한 경우에는 confirm 여부의 확인이 필요 하다. (간단 버전 API 제공 필요)
		webix.ajax().get('/srDataRequest/one', {id:getParam("id")}, function(text,data){
			if(data.json().httpStatus==200){
				let params = {id:data.json().contents.id, confirmYN:data.json().contents.confirmYN}
				sr_data_request_run_popup(params);
			} else {
				errorControll(data.json());
			}
		});
	}
}

webix.ready(function(){
	// 승인을 위해 호출한 것으로 간주하고, 승인하도록 처리 해 준다.
	if(getParam("id") > 0){
		confirmPopupLazyLoading();
	}
});

// 엑셀이 만들어 지는 동안에는 읽지 못한다.. 기다려야 한다...
let iframeLoading = function (){
	try {
		// text 가 있으면..
		if($('#iframeExcelDownload').contents().find('pre').text()!=""){
			let responseMessage = eval("("+$('#iframeExcelDownload').contents().find('pre').text()+")");
			webix.message({ type:"error", text:responseMessage.message });
		}
	} catch(e){
		// 오류가 있으면 엑셀이다...
	}
}