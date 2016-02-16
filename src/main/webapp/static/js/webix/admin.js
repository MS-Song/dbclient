// 데이터 베이스 관리 리스트 팝업
var adminDatabaseListPopup = function(){
	if($$("admin_database_list_popup")==undefined){
		webix.ui({
	        view:"window",
	        id:"admin_database_list_popup",
	        autowidth:true,
	        position:"center",
	        modal:true,
	        head:{
	        	cols:[
	  		        {
	  		        	view:"button",value:"서버 관리 닫기" , click:function(){
	  		        		$$("admin_database_list_popup").hide();
	  		        	}
	  		        },
			    	{
	  		        	view:"button",value:"서버 추가" , click:function(){
	  		        		adminAddDatabasePopup();
	  		        	}
			    	}
	        	]
	        },
	        body:{
	        	id:"admin_database_list_view",
	        	view:"datatable",
	        	columns:[],
				tooltip:true,
				select:"row",
				resizeColumn:true,
				autowidth:true,
				autoheight:true,
				data:[]
	        }
	    });
	}
	$$("admin_database_list_popup").show();

    // side menu 닫기
    $$("menu").hide();

    
    // 정보를 조회하기 전에 리셋 한다. 
	// 이미 있는 내용은 모두 지운다 
	$$("admin_database_list_view").config.columns = [];
	$$("admin_database_list_view").clearAll();
	
    // 데이터베이스 정보를 조회한다.
	webix.ajax().get("/database/serverList.json", function(text,data){
		// 데이터베이스 정보를 획득한 경우에 테이블에 넣는다.
		if(data.json().status ==200 && null!=data.json().result){	
			// 데이터 1개를 꺼내서 columns 를 만든다. 
			// row 1개를 꺼내서 필드를 구성한다.
			var loop=0;
			$.each(data.json().result.serverInfo[0],function(index){
				$$("admin_database_list_view").config.columns[loop]={};
				$$("admin_database_list_view").config.columns[loop].id = index;
				$$("admin_database_list_view").config.columns[loop].header = index;
				$$("admin_database_list_view").config.columns[loop].adjust = true;
				if(!isNaN(this)){
					$$("admin_database_list_view").config.columns[loop].sort="int";
				} else {
					$$("admin_database_list_view").config.columns[loop].sort="string";	
				}
				loop++;
			});
			
			// 관리 메뉴를 추가한다.
			$$("admin_database_list_view").config.columns[loop]={};
			$$("admin_database_list_view").config.columns[loop].id = "modify_database";
			$$("admin_database_list_view").config.columns[loop].header = "수정";
			$$("admin_database_list_view").config.columns[loop].adjust = true;
			$$("admin_database_list_view").config.columns[loop].template='<input type="button" value="수정" style="width:40px;" onClick="adminModifyDatabasePopup(#serverInfoSeq#);"/>',
			$$("admin_database_list_view").config.columns[loop+1]={};
			$$("admin_database_list_view").config.columns[loop+1].id = "delete_database";
			$$("admin_database_list_view").config.columns[loop+1].header = "삭제";
			$$("admin_database_list_view").config.columns[loop+1].adjust = true;
			$$("admin_database_list_view").config.columns[loop+1].template='<input type="button" value="삭제" style="width:40px;" onClick="adminDeleteDatabase(#serverInfoSeq#);"/>',
			
			// 관리자 메뉴 추가 종료
			$$("admin_database_list_view").refreshColumns();

			$$("admin_database_list_view").parse(data.json().result.serverInfo);
    		$$("admin_database_list_view").refresh();
		} else {
			webix.message(data.json().desc);
		}
	});
};

// 데이터 베이스 추가
var adminAddDatabasePopup=function(){
	webix.ui({
	    view:"window",
	    id:"admin_add_database_popup",
	    autowidth:true,
	    position:"center",
	    modal:true,
	    head:{
	    	view:"button",value:"서버 관리 닫기" , click:function(){
	    		$$("admin_add_database_popup").hide();
	    	}
	    },
	    body:{
	    	id:"admin_add_database_form",
			view:"form",
			borderless:true,
			elements: [
				{ id:"host", 		view:"text", label:'host', 			name:"host" 		},
				{ id:"hostAlias", 	view:"text", label:'hostAlias', 	name:"hostAlias" 	},
				{ id:"schemaName", 	view:"text", label:'schemaName', 	name:"schemaName" 	},
				{ id:"account", 	view:"text", label:'account', 		name:"account" 		},
				{ id:"password", 	view:"text", label:'password', 		name:"password" 	},
				{ id:"driver", 		view:"text", label:'driver', 		name:"driver" 		},
				{ id:"charset", 	view:"text", label:'charset', 		name:"charset" 		},
				{ id:"port", 		view:"text", label:'port', 			name:"port" 		},
				{
					cols:[
		    			{ id:"resist", 	view:"button", label:'등록', click:function(){
		    				webix.ajax().post("/database/addDatabases.json", this.getFormView().getValues(), function(text,data){
		    					// 가입 실패
		    					if(data.json().status !=200){
		    						// validate 메세지 
	    							webix.message({ type:"error", text:data.json().desc});
		    					} else { // 가입 성공
		    						webix.message("데이터베이스 등록 완료");
		    						// 0.3 초 후에 팝업 닫고 데이터베이스 팝업창 이로드 한다.	
		    						window.setTimeout(function(){
		    							$$("admin_add_database_popup").hide();
		    							adminDatabaseListPopup();
		    						}, 300)										
		    					}
		    				});
		    			}},
		    			{ id:"cancel", 	view:"button", label:'취소',	click:function(){
		    				$$("admin_add_database_popup").hide();
		    			}}
					]
				}
			]	        
	    }
    }).show();
};

// 데이터 베이스 변경 popup
var adminModifyDatabasePopup = function(serverInfoSeq){
	webix.ui({
	    view:"window",
	    id:"admin_modify_database_popup",
	    autowidth:true,
	    position:"center",
	    modal:true,
	    head:{
	    	view:"button",value:"서버 관리 닫기" , click:function(){
	    		$$("admin_modify_database_popup").hide();
	    	}
	    },
	    body:{
	    	id:"admin_modify_database_form",
			view:"form",
			borderless:true,
			elements: [
				{ id:"host", 			view:"text", label:'host', 			name:"host" 		},
				{ id:"hostAlias", 		view:"text", label:'hostAlias', 	name:"hostAlias" 	},
				{ id:"schemaName", 		view:"text", label:'schemaName', 	name:"schemaName" 	},
				{ id:"account", 		view:"text", label:'account', 		name:"account" 		},
				{ id:"password", 		view:"text", label:'password', 		name:"password" 	},
				{ id:"driver", 			view:"text", label:'driver', 		name:"driver" 		},
				{ id:"charset", 		view:"text", label:'charset', 		name:"charset" 		},
				{ id:"port", 			view:"text", label:'port', 			name:"port" 		},
				{
					cols:[
		    			{ id:"resist", 	view:"button", label:'수정', click:function(){
		    				webix.ajax().post("/database/modifyDatabase.json", this.getFormView().getValues(), function(text,data){
		    					// 가입 실패
		    					if(data.json().status !=200){
		    						// validate 메세지 
	    							webix.message({ type:"error", text:data.json().desc});
		    					} else { // 가입 성공
		    						webix.message("데이터베이스 수정 완료");
		    						// 0.3 초 후에 팝업 닫고 데이터베이스 팝업창 이로드 한다.	
		    						window.setTimeout(function(){
		    							$$("admin_modify_database_popup").hide();
		    							adminDatabaseListPopup();
		    						}, 300)										
		    					}
		    				});
		    			}},
		    			{ id:"cancel", 	view:"button", label:'취소',	click:function(){
		    				$$("admin_modify_database_popup").hide();
		    			}}
					]
				}
			]	        
	    }
    }).show();

	webix.ajax().get("/database/server.json", {"serverInfoSeq":serverInfoSeq}, function(text,data){
		if(data.json().status !=200){
			// validate 메세지 
			webix.message({ type:"error", text:data.json().desc});
		} else { // 기존 입력 정보 획득
			$.each(data.json().result,function(){
				$$('admin_modify_database_form').setValues({ 
					serverInfoSeq:this.serverInfoSeq,
					host:this.host,
					hostAlias:this.hostAlias,
					schemaName:this.schemaName,
					account:this.account,
					password:this.password,
					driver:this.driver,
					charset:this.charset,
					port:this.port
				}); 
			});
		}
	});
};


// 데이터 베이스 삭제
var adminDeleteDatabase = function(serverInfoSeq){
	webix.confirm({
		title: "데이터베이스 정보 삭제",
		ok:"Yes", cancel:"No",
		text:"데이터베이스 정보를 삭제하시겠습니까?",
		callback:function(result){
			if(result==true){
				// 데이터 베이스 삭제 실행
				webix.ajax().del("/database/deleteDatabases.json?serverInfoSeq="+serverInfoSeq, function(text,data){
					// 삭제 결과 확인
					if(data.json().status ==200){	
						window.setTimeout(function(){
							adminDatabaseListPopup();
						}, 100)
					} else {
						webix.message(data.json().desc);
					}
				}); 					
			}
		}
	});
};
	
// 회원 관리 처리
// 회원 권한 처리

// 데이터 베이스와 회원간의 연결
// 각종 환경 설정