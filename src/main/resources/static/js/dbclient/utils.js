// swagger 정의를 가져온다.
var swaggerApiDocs = null;
var getSwaggerApiDocs = function(){
	if(null==swaggerApiDocs){
		webix.ajax().get("/v2/api-docs",function(text,data){
			swaggerApiDocs=data.json();
		});
	}
};
// swagger doc 로딩
getSwaggerApiDocs();


/**
 * 데이터를 조회하여, data view 에 넣는다.
 */
var views = [];
var timeInterval=null;
var startTime=null;
var getDataParseView = function(url,parmeters,viewName,isCreateHeader,isCache,isWriteLog){
	// 기존에 등록되어 있는 뷰 인가 검증한다.
	var isAlreadyInputView = false;
	for(var key in views){
		if(viewName == views[key]){
			isAlreadyInputView=true;
			break;
		}
	}
	// 이미 등록된 경우가 아니면 view 를 넣는다.
	if(isAlreadyInputView==false){
		views.push(viewName);
	}
	
	// progress 시작
	try {
		$$(viewName).showProgress();
	} catch (e) {
		// progress 가 없을 경우 생성 한다음 다시 실행 한다.
		webix.extend($$(viewName), webix.ProgressBar);
		$$(viewName).showProgress();
	}
	
	// 로그를 기록할 경우 시간 측정을 시작 한다.
	startTime = new Date().getTime();
	if(isWriteLog){
		timeInterval = setInterval(function(){
			var nowTime = new Date().getTime();
			var waitTime = new Date().getTime() - startTime;

			$$("database_query_execute_info").define("label",'wait time : ' + comma(waitTime) + 'ms');
			$$("database_query_execute_info").refresh();
		}, 200); 
	}
	
	// view 에 있는 내용을 모두 지운다.
	$$(viewName).clearAll();
	// 캐시 객체를 생성한다.
	var cachedList=null;
	// 캐시를 사용할 경우에
	if(isCache){
		cachedList = webix.storage.local.get(JSON.stringify(parmeters, null, 2)+"_"+viewName);		
	}
	// cache 에 데이터가 존재하면 캐시의 데이터를 노출한다.
	if(null != cachedList){
		$$(viewName).parse(cachedList);
		// 다시 읽는다.
		$$(viewName).refresh();
		$$(viewName).hideProgress();
	} else {
		webix.ajax().get(url,parmeters, function(text,data){
			if(data.json().httpStatus ==200 && null!=data.json().contents){
				// 배열인 경우에만 처리한다. response 에 배열은 결과 데이터외에 없다.
				var obj = null;
				if(undefined!=data.json().contents.content){ //page 구조
					obj=data.json().contents.content;
				} else { //일반
					obj=data.json().contents;
				}
		
				if(Array.isArray(obj)){
					// header 를 만들어야 하는 경우에 처리
					if(isCreateHeader){
						// 데이터가 없는 경우를 걸러낸다.
						if(obj.length>0){
							// 기존 헤더를 삭제 한다.
							$$(viewName).config.columns = [];
							// row 1개를 꺼내서 필드를 구성한다.
							var loop=0;
							$.each(obj[0],function(header,name){
								$$(viewName).config.columns[loop]={};
								$$(viewName).config.columns[loop].id = header;
								$$(viewName).config.columns[loop].header = header;
								$$(viewName).config.columns[loop].adjust = true;
								if(!isNaN(this)){
									$$(viewName).config.columns[loop].sort="int";
								} else {
									$$(viewName).config.columns[loop].sort="string";	
								}
								loop++;
							});
							$$(viewName).refreshColumns();
						} else {
							webix.message({ type:"error", text:"데이터가 없습니다."});
						}
					}
					// 객체가 있는 경우 리스트를 그린다.
					if(null != obj){
						// 파싱에 버그가 있는 경우가 있다 직접 파싱 한다.
						if(viewName=="database_query_favorities_view"){
							$.each(obj,function(objIndex){
								$$("database_query_favorities_view").data.add({
									id:this.id,
									memo:this.memo,
									query:this.query,
									createDate:this.createDate,
									reTry:"",
									favorities:""
								},objIndex);
							});
						}
						else {
							// 데이터를 파싱 한다.
							$$(viewName).parse(obj)								
						}
						// 캐시를 사용한다면 캐시에 넣는다.
						if(isCache){
							webix.storage.local.put(JSON.stringify(parmeters, null, 2)+"_"+viewName,obj);
						}
					}
				}
			} else {
				// 공용 에러처리
				errorControll(data.json());
			}
    		// 실행 로그 기록
    		if(isWriteLog){
    			// 실행이 종료되면 결과를 보여준다
    			if(data.json().httpStatus==200){
        			try {
        				// 시간 객체 제거
        				clearInterval(timeInterval);
        				$$(viewName).config.executedTime=parseInt(data.json().processTime);
        				$$("database_query_execute_info").define("label",'Rows: '+ comma(data.json().rowCount) + ', Time: '+comma(data.json().processTime) + ' ms');	
    				} catch (e) {
    					reslutPrintError(data.json().message);
    				}
    			} else {
					reslutPrintError(data.json().message);
    			}
    			$$("database_query_execute_info").refresh();
    			//쿼리 로그 기록
    			var time = new Date();
    			$$("database_query_log_view").data.add({
    				seq:$$("database_query_log_view").data.order.length+1,
    				date:time.getHours()+'시 '+time.getMinutes()+'분 '+time.getSeconds()+'초 <br/>'+time.getFullYear()+'년 '+(time.getMonth()+1)+'월 '+time.getDate()+'일',
    				query:$$(viewName).config.query,
    				reTry:"",
    				favorities:""
    			},$$("database_query_log_view").data.order.length+1);
    			$$("database_query_log_view").sort("seq", "desc","int");
    			$$("database_query_log_view").refresh();
    		}
    		
			// view 를 refresh 한다.
			$$(viewName).refresh();
			// progress 를 닫는다.
    		$$(viewName).hideProgress();
		});
	}
	// 시간 객체 제거 -- 오류가 나도 제거한다.
	clearInterval(timeInterval);
};

/**
 * 기존 데이터에 데이터를 추가한다.
 * 
 */
var addDataParseView = function(url,parmeters,viewName){
	if($$(viewName).config.isDataLoading){
		webix.message("이전 페이지 요청이 진행 중입니다.");
	} else {
		// 데이터 요청을 시작 한다.
		$$(viewName).config.isDataLoading=true;
		// 데이터를 조회 한다.
		webix.ajax().get(url,parmeters, function(text,data){
			if(data.json().httpStatus ==200 && null!=data.json().contents){
				// 배열인 경우에만 처리한다. response 에 배열은 결과 데이터외에 없다.
				var obj = null;
				if(undefined!=data.json().contents.content){ //page 구조
					obj=data.json().contents.content;
				} else { //일반
					obj=data.json().contents;
				}
				if(Array.isArray(obj)){
					// 객체가 있는 경우 리스트를 그린다.
					$$(viewName).parse(obj)								
				}
			} else {
				// 공용 에러처리
				errorControll(data.json());
			}
			// view 를 refresh 한다.
			$$(viewName).refresh();

			// 실행이 종료되면 결과를 보여준다
			if(data.json().httpStatus==200){
				try {
					$$(viewName).config.executedTime=parseInt(data.json().processTime);
					$$("database_query_execute_info").define("label",'Rows: '+ comma(data.json().rowCount) + ', Time: '+comma(data.json().processTime) + ' ms');	
				} catch (e) {
					reslutPrintError(data.json().message);
				}
			} else {
				reslutPrintError(data.json().message);
			}
			$$("database_query_execute_info").refresh();
		
		});		
	}
	$$(viewName).config.isDataLoading=false;
};

//쿼리에 에러가 발생할 경우 result set 공간에 에러를 보여준다.
var reslutPrintError = function(errorMessage){
	$$("database_result_list_view").config.columns = [];
	$$("database_result_list_view").config.columns[0]={};
	$$("database_result_list_view").config.columns[0].id = "error";
	$$("database_result_list_view").config.columns[0].header = "SQL ERROR";
	$$("database_result_list_view").config.columns[0].adjust = true;
	$$("database_result_list_view").config.columns[0].sort="string";	
	$$("database_result_list_view").refreshColumns();

	$$("database_result_list_view").data.add({error:errorMessage},1);
	$$("database_result_list_view").refresh();
};


/**
 * 데이터를 조회하여, editor 에 넣는다.
 * TODO-캐시처리
 */
var getDataParseEditor = function(url,parmeters,viewName,returnValueName){
	webix.ajax().get(url,parmeters, 
		function(text,data){
			if(data.json().httpStatus ==200 && null!=data.json().contents){
				$.each(data.json().contents,function(index, obj){
					if(null==returnValueName){
						$$(viewName).setValue(obj.unescapeHtml());
					} else {
						$$(viewName).setValue(obj[returnValueName].unescapeHtml());
					}
				});
				$$(viewName).focus(); 
			} else {
				errorControll(data.json());
			}
		}
	);
};

/**
 * 데이터를 조회하여 TextArea 에 넣는다.
 */
var getDataParseTextarea = function(url,parmeters,viewName,returnValueName){
	webix.ajax().get(url,parmeters, 
		function(text,data){
			if(data.json().httpStatus ==200 && null!=data.json().contents){
				$.each(data.json().contents,function(index, obj){
					$$(viewName).setValue(obj[returnValueName].unescapeHtml());
				});

			} else {
				errorControll(data.json());
			}
		}
	);
};


/**
 * 데이터를 조회하여 Property 에 넣는다.
 */
var getDataParseProperty = function(url,parmeters,viewName){
	webix.ajax().get(url,parmeters, 
			function(text,data){
//				console.log(data.json());
				if(data.json().httpStatus ==200 
						&& null!=data.json().contents){

					var contents = null;
					if(undefined!=data.json().contents.content){ //page 구조
						contents=data.json().contents.content;
					} else { //일반
						contents=data.json().contents;
					}
					
					var elementList=[];
					var valueList={};
					$.each(contents,function(index, obj){
						var loop=0
						$.each(obj,function(name,value){
							elementList.push({"label":name,type:"text","id":name,width:300})
							valueList[name]=value;
							
						})
					});
					$$(viewName).define("elements",elementList);
					$$(viewName).setValues(valueList);
					$$(viewName).refresh();
				} else {
					errorControll(data.json());
				}
			}
		);
	};

//자동완성 데이터 저장 (all Table)
var autoCompleteAddTablesAll = function(tableName,columnName,columnComment){
	//해당 테이블 존재 확인
	if(null==$$("database_query_input").config.hintOptions.tables[tableName]){
		$$("database_query_input").config.hintOptions.tables[tableName]={};							
	}
	// 필드 입력
	if(null==$$("database_query_input").config.hintOptions.tables[tableName][columnName]){
		$$("database_query_input").config.hintOptions.tables[tableName][columnName]={};							
	}
	$$("database_query_input").config.hintOptions.tables[tableName][columnName]['text'] = columnName;
	$$("database_query_input").config.hintOptions.tables[tableName][columnName]['comment'] = columnComment;
};

// 자동완성 데이터 리셋
var autoCompleteAddTablesReset = function(){
	$$("database_query_input").config.hintOptions.tables={};
}

// 자동완성 이벤트 추가 -- 온오프 기능이 필요하다.
var autoCompleteEvent_Const=0;
var autoCompleteEvent = function(){
	// 중복 호출 방지
	if(autoCompleteEvent_Const==0){
		$$("database_query_input").getEditor().on("keyup", function(cm, e) {
			if (e.keyCode == 190) {
			  $$("database_query_input").getEditor().execCommand("autocomplete")
		  }
		})
	}
	autoCompleteEvent_Const++;
}

// 동등성 비교
var equals=function (a,b){
	if(a != null && b != null){
		a = a.toString().toLowerCase();
		b = b.toString().toLowerCase();
		return a.indexOf(b) !== -1;
	} else {
		return false;
	}
};

var errorControll = function(response){
	// 로그인 에러 처리
	webix.message({ type:"error", text:response.message });
	if(response.httpStatus == 405){
		login_popup();
	} 
};

//콤마찍기
var comma=function(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
};

//콤마풀기
var uncomma=function(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

/**
 * html entity 를 decode 한다.
 */
String.prototype.unescapeHtml = function () {
    var temp = document.createElement("div");
    temp.innerHTML = this;
    var result = temp.childNodes[0].nodeValue;
    temp.removeChild(temp.firstChild);
    return result;
}

var editorPositionWrite = function (cm){
	var position = parseInt(cm.getCursor().line+1) + ":"+cm.getCursor().ch;
	$$("editorCurrentLine").define("label",position);
	$$("editorCurrentLine").refresh();
	
	// 선택된 내역이 있으면..
	if(""!=cm.getSelection()){
		var first = cm.listSelections()[0];
		var position = parseInt(first.anchor.line+1) + ":" +  first.anchor.ch + "-" + parseInt(first.head.line+1) + ":" + first.head.ch;
		var last = null;
		if(cm.listSelections().length>1){
			last = cm.listSelections()[cm.listSelections().length-1];
			position+= "|" + parseInt(last.anchor.line+1) + ":" +  last.anchor.ch + "-" + parseInt(last.head.line+1) + ":" + last.head.ch;
		}
		$$("editerSelectedStartLine").define("label",position);
		
	} else {
		$$("editerSelectedStartLine").define("label","");
	}
	$$("editerSelectedStartLine").refresh();
};

var reload = function() {
	window.setTimeout(function(){document.location = document.location.href;}, 1000)	
}

var mvSite = function(src) {
	document.location = src;	
}

/**
 * form view 를 생성 한다. 
 */
/**
 * 제외 시켜야 하는 파라메터
 */
var excludeParams = ["useCache","apiAuthkey","page","size","sort"];
var getFromView = function(param,isRightDescription=false,isDisable=false){
	// 제외 되는 파라메터 인 경우 null을 리턴한다.
	if($.inArray(param.name,excludeParams) != -1) {
		return null;
	}
	
	// view 객체
	let viewElement = {};

	// 필수값 여부를 추가 한다.
	let required = param.required ? " * " : " ";
	// label을 명칭과 설명으로 나눈다.
	
	let discriptions;
	if(undefined==param.description){
		discriptions=[param.name,""];
	} else {
		discriptions = param.description.split("||");
	}
	
	let leftDescription		= discriptions[0] + required;
	let rightDescription	= isRightDescription && undefined!=discriptions[1] ? discriptions[1] : ""; 
	
	if(param.enum!=undefined){
		viewElement={
				view:"combo", 
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				options:param.enum,
				name:param.name,
				disabled:isDisable
		};		
	} else if(param.type=="boolean"){
		viewElement={
				view:"combo", 
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				options:["true","false"],
				name:param.name,
				disabled:isDisable
		};		
	} else if (param.name.toLowerCase().indexOf("databaseid") >= 0
				|| param.name.toLowerCase().indexOf('databasevo')>= 0){
		viewElement={
				view:"select", 
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				options:useDatabaseOptions,
				name:'databaseId',
				disabled:isDisable
		};				
	} else if (param.name.toLowerCase().indexOf("member") >= 0){
		viewElement={ 
				view:"text",
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				name:param.name,
				readonly:true,
				disabled:isDisable,
				on:{"onItemClick":function(view,e){ // enter 검색 추가
					if(undefined!=param.collectionFormat 
							&& param.collectionFormat=="multi"){
						member_list_popup(view,true);	
					} else {
						member_list_popup(view,false);
					}					
				}}						
		};		
	} 
	else if (param.name.toLowerCase().indexOf("sql") >= 0){
		viewElement={ 
				view:"textarea",
				label:leftDescription, 
				labelWidth:150,
				height:60,
				adjust:true,
				name:param.name,
				disabled:isDisable,
				on:{"onItemClick":function(view,e){ // query popup 창 추가
					// 검색 창에서는 실행이 불가능하다.
					if('incident_alarm_search_form'!=$$(view).getFormView().config.id){
						database_sql_execute_popup(view,$$("incident_alarm_form").getValues().databaseId,$$(view).getValue());						
					} else {
						console.log("검색창 호출");
					}
				}}						
		};		
	} else if(param.name.indexOf("schedule") >= 0 ){
		viewElement={ 
				view:"text",
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				name:param.name,
				placeholder:"* */10 * * * *",
				disabled:isDisable,
				on:{"onItemClick":function(view,e){ // crontab 확인
					// 검색 창에서는 실행이 불가능하다.
					if('incident_alarm_search_form'!=$$(view).getFormView().config.id){
						crontabSchedulePopup(view,$$(view).getValue());						
					} else {
						console.log("검색창 호출");
					}
				}}	
		};		
	} else { // text
		viewElement={ 
				view:"text",
				label:leftDescription, 
				labelWidth:150,
				adjust:true,
				name:param.name,
				disabled:isDisable
		};		
	}
	// 오른쪽 설명이 필요한 경우에 오른족 설명을 붙인다
	if(isRightDescription){
		viewElement = {cols:[viewElement,{
	    	view: "label",
			label: rightDescription,
			adjust:true
		}]}
	}
	return viewElement;
};

var formatDate = function (date) {
  return date.getFullYear() + '년 ' + 
    (date.getMonth() + 1) + '월 ' + 
    date.getDate() + '일 ' + 
    date.getHours() + '시 ' + 
    date.getMinutes() + '분 ' + 
    date.getSeconds() + '초';
 };
 
 var gapDate = function (beforeDate, afterDate){
	 console.log(beforeDate);
	 console.log(afterDate);
	 let gap 		= afterDate.getTime() - beforeDate.getTime();
	 let sec_gap 	= parseInt(gap / 1000);
	 var min_gap 	= parseInt(gap / 1000 /60);
	 var hour_gap 	= parseInt(gap / 1000 /60 / 60);
	 var day_gap 	= parseInt( gap / 1000 /60 / 60 / 24);
	 // 시간 단위보다 클 경우 윗단계에 포함됨으로 초기화
	 if(sec_gap>=60) 	sec_gap=0; 
	 if(min_gap>=60) 	min_gap=0;
	 if(hour_gap>=24) 	hour_gap=0;
	 return day_gap + "일 " + hour_gap + "시 " + min_gap + "분 " + sec_gap + "초";
 }