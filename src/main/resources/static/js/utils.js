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
			// view 를 refresh 한다.
			$$(viewName).refresh();
			// progress 를 닫는다.
    		$$(viewName).hideProgress();

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
    					$$("database_query_execute_info").define("label",'Error :'+data.json().message);
        				$$("database_query_execute_info").define("tooltip",'Error :'+data.json().message);    					
    				}
    			} else {
    				$$("database_query_execute_info").define("label",'Error :'+data.json().message);
    				$$("database_query_execute_info").define("tooltip",'Error :'+data.json().message);
    			}
    			$$("database_query_execute_info").refresh();
    			
    			//쿼리 로그 기록
    			var time = new Date();
    			$$("database_query_log_view").data.add({
    				seq:$$("database_query_log_view").data.order.length+1,
    				date:time.getHours()+'시 '+time.getMinutes()+'분 '+time.getSeconds()+'초 <br/>'+time.getFullYear()+'년 '+(time.getMonth()+1)+'월 '+time.getDate()+'일',
    				query:$$(viewName).config.executedQuery,
    				reTry:"",
    				favorities:""
    			},$$("database_query_log_view").data.order.length+1);
    			$$("database_query_log_view").sort("seq", "desc","int");
    			$$("database_query_log_view").refresh();
    		}
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
	webix.ajax().sync().get(url,parmeters, function(text,data){
		if(data.json().httpStatus ==200 && null!=data.json().contents){
			$.each(data.json().contents,function(index, obj){
				// 배열인 경우에만 처리한다. response 에 배열은 결과 데이터외에 없다.
				if(Array.isArray(obj)){
					// 객체가 있는 경우 리스트를 그린다.
					$$(viewName).parse(obj)								
				}
			});
		} else {
			// 공용 에러처리
			errorControll(data.json());
		}
		// view 를 refresh 한다.
		$$(viewName).refresh();

		// 실행이 종료되면 결과를 보여준다
		try {
			$$(viewName).config.executedTime=$$(viewName).config.executedTime+parseInt(data.json().contents.processTime);
			$$("database_query_execute_info").define("label",'Rows: '+ $$(viewName).count() + ', Time: '+$$(viewName).config.executedTime + ' ms');	
		} catch (e) {
			$$("database_query_execute_info").define("label",'Error :'+data.json().desc.replace("="," ").replace("\n"," "));
		}
		$$("database_query_execute_info").refresh();
	
	});
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
					$$(viewName).setValue(obj[returnValueName].unescapeHtml());
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
 * TODO-캐시처리
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
 * TODO-캐시처리
 */
var getDataParseProperty = function(url,parmeters,viewName){
	webix.ajax().get(url,parmeters, 
			function(text,data){
//				console.log(data.json());
				if(data.json().httpStatus ==200 && null!=data.json().contents){

					var elementList=[];
					var valueList={};
					$.each(data.json().contents,function(index, obj){
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