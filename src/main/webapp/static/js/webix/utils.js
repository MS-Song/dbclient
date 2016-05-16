
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

/**
 * 데이터를 조회하여, data view 에 넣는다.
 */
var views = [];
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
		webix.ajax().get(url+".json",parmeters, function(text,data){
			if(data.json().status ==200 && null!=data.json().result){
				$.each(data.json().result,function(index, obj){
					// 배열인 경우에만 처리한다. response 에 배열은 결과 데이터외에 없다.
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
						// 객체가 있는 경우 리스를 그린다.
						if(null != obj){
							// 데이터를 파싱 한다.
							$$(viewName).parse(obj)
							// 캐시를 사용한다면 캐시에 넣는다.
							if(isCache){
								webix.storage.local.put(JSON.stringify(parmeters, null, 2)+"_"+viewName);
							}

							// view 가 필드 리스트 인 경우 자동완성 데이터를 생성 한다.
							if(viewName == "table_info_field_list"){
								if(null!=serverInfo.tableName){
									var fieldList={}									
									$.each(obj,function(index,fieldInfo){
										fieldList[fieldInfo.columnName]=fieldInfo.comment;
									});
									autoCompleteAddTables(serverInfo.tableName, fieldList);
								}
							}
						}
					}
				});
			} else {
				// 공용 에러처리
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });
			}
			// view 를 refresh 한다.
			$$(viewName).refresh();
			// progress 를 닫는다.
    		$$(viewName).hideProgress();

    		// 실행 로그 기록
    		if(isWriteLog){
    			// 실행이 종료되면 결과를 보여준다
    			$$("database_query_execute_info").define("label",'Rows: '+data.json().result.rowCount + ', Time: '+data.json().result.processTime + ' ms');
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
    		}
		});
	}
};

/**
 * 데이터를 조회하여, editor 에 넣는다.
 */
var getDataParseEditor = function(url,parmeters,returnValueName){
	webix.ajax().get(url+".json",parmeters, 
		function(text,data){
			
			console.log(data.json());
			if(data.json().status ==200 && null!=data.json().result){
				
				$.each(data.json().result,function(index, obj){
					$$("database_query_input").setValue(obj[0][returnValueName].unescapeHtml());
				});
				$$("database_query_input").focus(); 
			} else {
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });
			}
		}
	);
};

var getDataParseTextarea = function(url,parmeters,viewName,returnValueName){
	webix.ajax().get(url+".json",parmeters, 
		function(text,data){
			if(data.json().status ==200 && null!=data.json().result){
				
				$.each(data.json().result,function(index, obj){
					$$(viewName).setValue(obj[0][returnValueName].unescapeHtml());
				});

			} else {
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });
			}
		}
	);
};


var getDataParseProperty = function(url,parmeters,viewName){
	webix.ajax().get(url+".json",parmeters, 
			function(text,data){
				console.log(data.json());
				if(data.json().status ==200 && null!=data.json().result){

					var elementList=[];
					var valueList={};
					$.each(data.json().result,function(index, obj){
						var loop=0
						$.each(obj[0],function(name,value){
							elementList.push({"label":name,type:"text","id":name,width:300})
							valueList[name]=value;
							
						})
					});
					$$(viewName).define("elements",elementList);
					$$(viewName).setValues(valueList);
					$$(viewName).refresh();
				} else {
					var message = data.json().desc.split("\n");
					webix.message({ type:"error", text:message[0].replace("="," ") });
				}
			}
		);
	};
/**
 * POST Method 로 데이터를 서버에 전송한다.
 */
var postDateSend = function(url, parameters){
	
};


//자동완성 데이터 저장
var autoCompleteAddTables = function(tableName,fieldList){
	// 자동완성에 테이블을 입력한다.
	if(null==$$("database_query_input").config.hintOptions.tables[tableName]){
		// 테이블을 만든다.
		$$("database_query_input").config.hintOptions.tables[tableName]={};							
		// 필드를 만든다.
		$.each(fieldList,function(columnName,columnComment){
			$$("database_query_input").config.hintOptions.tables[tableName][columnName] = columnComment;
		});
//		console.log($$("database_query_input").config.hintOptions.tables[tableName]);
	}
};

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