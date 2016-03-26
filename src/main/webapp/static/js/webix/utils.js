/**
 * 데이터를 조회하여, data view 에 넣는다.
 */
var getDataParseView = function(url,parmeters,viewName,isCreateHeader,isCache){
	
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
			// 데이터가 있는 경우에만 진입
			if(data.json().status ==200 && null!=data.json().result){
				$.each(data.json().result,function(index, obj){
//					console.log(obj);
					// header 를 만들어야 하는 경우에 처리
					if(isCreateHeader){
						// 기존 헤더를 삭제 한다.
						if(isCreateHeader) $$(viewName).config.columns = [];
						// row 1개를 꺼내서 필드를 구성한다.
						var loop=0;
						$.each(obj,function(header){
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
						$$(header).refreshColumns();
					} else { // 헤더가 있는 경우에 처리
						if(null != obj){
							// 데이터를 파싱 한다.
							$$(viewName).parse(obj)
							// 캐시를 사용한다면 캐시에 넣는다.
							if(isCache){
								webix.storage.local.put(JSON.stringify(parmeters, null, 2)+"_"+viewName);
							}
						}
					}
				});
			} else {
				var message = data.json().desc.split("\n");
				webix.message({ type:"error", text:message[0].replace("="," ") });
			}
			// view 를 refresh 한다.
			$$(viewName).refresh();
			// progress 를 닫는다.
    		$$(viewName).hideProgress();
		});
	}
};



/**
 * 데이터를 조회하여, editor 에 넣는다.
 */
var getDateParseEditor = function(url,parmeters,viewName){
	
};

/**
 * POST Method 로 데이터를 서버에 전송한다.
 */
var postDateSend = function(url, parameters){
	
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