
// 회원 권한 리스트 로딩
var authtypeList = null;
//database driver 리스트 조회
webix.ajax().get("/member/getAuthTypes.json",function(text,data){
	if(data.json().status !=200){
		// validate 메세지 
		webix.message({ type:"error", text:data.json().desc});
	} else { // database driver loading
		authtypeList=[];
		authtypeList.push('');
	
		for(var i=0;i<data.json().result.authTypeList.length;i++)
			authtypeList.push(data.json().result.authTypeList[i]);

	}
});

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
	if($$("menu").isVisible()) $$("menu").hide();

    
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
				{ id:"host", 		view:"text", 	label:'host', 			name:"host" 		},
				{ id:"hostAlias", 	view:"text", 	label:'hostAlias', 		name:"hostAlias" 	},
				{ id:"schemaName", 	view:"text", 	label:'schemaName', 	name:"schemaName" 	},
				{ id:"account", 	view:"text", 	label:'account', 		name:"account" 		},
				{ id:"password", 	view:"text", 	label:'password', 		name:"password" 	},
				{ id:"charset", 	view:"text", 	label:'charset', 		name:"charset" 		},
        		{ id:"driver",		view:"select",	label:'driver',			name:"driver", 		options:drivers },
				{ id:"port", 		view:"text", 	label:'port', 			name:"port" 		},
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
				{ id:"host", 			view:"text", 	label:'host', 			name:"host" 		},
				{ id:"hostAlias", 		view:"text", 	label:'hostAlias', 		name:"hostAlias" 	},
				{ id:"schemaName", 		view:"text", 	label:'schemaName', 	name:"schemaName" 	},
				{ id:"account", 		view:"text", 	label:'account', 		name:"account" 		},
				{ id:"password", 		view:"text", 	label:'password', 		name:"password" 	},
        		{ id:"driver",			view:"select",	label:'driver',			name:"driver", 		options:drivers },
				{ id:"charset", 		view:"text", 	label:'charset', 		name:"charset" 		},
				{ id:"port", 			view:"text", 	label:'port', 			name:"port" 		},
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
	
// 회원 리스트 팝업
var adminMemberListPopup = function(){
	if($$("admin_member_list_popup")==undefined){
		webix.ui({
	        view:"window",
	        id:"admin_member_list_popup",
	        autowidth:true,
	        position:"center",
	        modal:true,
	        head:{
	        	view:"button",value:"회원 관리 닫기" , click:function(){
	        		$$("admin_member_list_popup").hide();
	        	}
        	},
	        body:{
	        	rows:[{
	            	id:"admin_member_list_search_form",
	    			view:"form",
	    			borderless:true,
	    			elements: [{
	    				cols:[
		    				{ 
		    					id:"admin_search_id", 		
		    					view:"text", 	
		    					placeholder:'id search',		
		    					name:"id",			
		    					on:{"onKeyPress":function(key,e){// 검색 실행
		    						if(key==13) loadAdminMemberList();
		    					}}
		    				},
		    				{ 
		    					id:"admin_search_email", 		
		    					view:"text", 	
		    					placeholder:'email search', 	
		    					name:"email",
		    					on:{"onKeyPress":function(key,e){// 검색 실행
		    						if(key==13) loadAdminMemberList();
		    					}}
		    						
		    				},
		    				{ 
		    					id:"admin_search_authType",	
		    					view:"select",  	
		    					name:"authType",
		    					options:authtypeList,
		    					on:{"onChange":function(key,e){// 검색 실행
		    						loadAdminMemberList();
		    					}}
		    				},
    						{ 
		    					id:"admin_search_button", 	
		    					view:"button", 	
		    					label:'검색',		 				
		    					on:{"onItemClick":function(){
		    						loadAdminMemberList();
		    					}}
    						},
    						{ 
    							id:"admin_reset_button", 		
    							view:"button", 	
    							label:'리셋',		 				
    							on:{"onItemClick":function(){
	    							$$('admin_member_list_search_form').setValues({ 
	    								id:"",
	    								email:"",
	    								authType:""
	    							});
	    						}}
    						}
		    			]
	    			}]
	        	},        	      
	        	{
		        	id:"admin_member_list_view",
		        	view:"datatable",
		        	columns:[],
					tooltip:true,
					select:"row",
					resizeColumn:true,
					autowidth:true,
					autoheight:true,
					data:[]
	        	}]
	        }
	    });
	}
	$$("admin_member_list_popup").show();
    // side menu 닫기
	if($$("menu").isVisible()) $$("menu").hide();
    
    loadAdminMemberList();
};

// 회원 리스트 Loading
var loadAdminMemberList = function(){
	// 이미 있는 내용은 모두 지운다 
	$$("admin_member_list_view").config.columns = [];
	$$("admin_member_list_view").clearAll();
	
	webix.ajax().get("/member/list.json", $$("admin_member_list_search_form").getValues(), function(text,data){
		if(data.json().status !=200){
			// validate 메세지 
			webix.message({ type:"error", text:data.json().desc});
		} else { // 회원 리스트 
			// row 1개를 꺼내서 필드를 구성한다.
			var loop=0;
			$.each(data.json().result.memberList[0],function(index){
				$$("admin_member_list_view").config.columns[loop]={};
				$$("admin_member_list_view").config.columns[loop].id = index;
				$$("admin_member_list_view").config.columns[loop].header = index;
				$$("admin_member_list_view").config.columns[loop].adjust = true;
				if(index=='memberDatabaseVOList'){
					$$("admin_member_list_view").config.columns[loop].template=function(obj){
						var temp = [];
						for(var i in obj.memberDatabaseVOList){
							temp.push(obj.memberDatabaseVOList[i].serverInfo.hostAlias);
						}
						return temp.join(",");
					}
				}
				
				if(!isNaN(this)){
					$$("admin_member_list_view").config.columns[loop].sort="int";
				} else {
					$$("admin_member_list_view").config.columns[loop].sort="string";	
				}
				loop++;
			});
			
			// 관리 메뉴를 추가한다.
			$$("admin_member_list_view").config.columns[loop]={};
			$$("admin_member_list_view").config.columns[loop].id = "admin_modify_member";
			$$("admin_member_list_view").config.columns[loop].header = "수정";
			$$("admin_member_list_view").config.columns[loop].adjust = true;
			$$("admin_member_list_view").config.columns[loop].template='<input type="button" value="수정" style="width:40px;" onClick="adminModifyMemberPopup(\'#id#\');"/>',
			$$("admin_member_list_view").config.columns[loop+1]={};
			$$("admin_member_list_view").config.columns[loop+1].id = "admin_delete_member";
			$$("admin_member_list_view").config.columns[loop+1].header = "삭제";
			$$("admin_member_list_view").config.columns[loop+1].adjust = true;
			$$("admin_member_list_view").config.columns[loop+1].template='<input type="button" value="삭제" style="width:40px;" onClick="adminDeleteMember(\'#id#\');"/>',
			
			// 관리자 메뉴 추가 종료
			$$("admin_member_list_view").refreshColumns();

			$$("admin_member_list_view").parse(data.json().result.memberList);
    		$$("admin_member_list_view").refresh();
		}
	});
}

// 회원 수정 Popup
var adminModifyMemberPopup = function(member_id){
    webix.ui({
        view:"window",
        id:"admin_modify_member_popup",
        position:"center",
        modal:true,
        autowidth:true,
        head:"관리자 회원정보 수정",
        body:{
        	id:"admin_modify_member_form",
        	view:"form",
        	borderless:true,
            autowidth:true,
        	elements: [
        		{ view:"text", 	name:"id",			type:"hidden",	height:0},
        		{ view:"text", 	label:'email', 		labelWidth:100, name:"email" },
        		{ view:"text", 	label:'패스워드', 		labelWidth:100, name:"password" ,type:"password"},
        		{ view:"text", 	label:'패스워드 재입력', labelWidth:100, name:"password" ,type:"password"},
        		{ view:"text", 	label:'비밀번호 질문', 	labelWidth:100, name:"passwordQuestion" },
        		{ view:"text", 	label:'비밀번호 답변', 	labelWidth:100, name:"passwordAnswer" },
        		{ view:"select",label:'회원 권한', 	labelWidth:100, name:"authType",options:authtypeList },
        		{
                	id:"select_database_use_member_database_view",
                	view:"datatable",
                	columns:[
                	        { id:"serverInfoSeq",	header:"serverInfoSeq",	width:100, sort:"int"},
        					{ id:"host",			header:"Host",			width:100, sort:"string"},
         					{ id:"hostAlias",		header:"HostAlias",		width:100, sort:"string"},
         					{ id:"schemaName",		header:"SchemaName",	width:100, sort:"string"},
         					{ id:"account",			header:"Account",		width:100, sort:"string"},
         					{ id:"port",			header:"Port",			width:60,  sort:"int"},
         					{ id:"selected",		header:"선택",			width:50}         					
         			],
	        		tooltip:true,
	    			select:"row",
	    			resizeColumn:true,
	    			autowidth:true,
	    			autoheight:true,
					scroll:"y",
	    			data:[],
	    			on:{
	    	    		onItemClick:function(id){
	    	    			var clickedRow = $$("select_database_use_member_database_view").getSelectedItem();
	    	    			var input = clickedRow.selected == "선택" ? false:true;

	    	    			params = {};
	    	    			params.serverInfoSeq=clickedRow.serverInfoSeq;
	    	    			params.id=member_id;
	    	    			params.input = input;
	    	    			
	    	    			webix.ajax().post("/member/modifyMemberDatabaseByAdmin.json", params, function(text,data){
	        					// 업데이트 실패
	        					if(data.json().status !=200){
	        						// validate 메세지 
	        						var message = data.json().desc.split("\n");
	        						webix.message({ type:"error", text:message[0].replace("="," ") });
	        					} else { // 업데이트 성공
	        						webix.message("수정이 완료되었습니다.");
	        						findMemberDatabse(member_id);
	        					}
	        				});
	    	    		}
	    			}
        		},
        		{margin:5, cols:[
        			{ view:"button", value:"수정" , type:"form", click:function(){
        				webix.ajax().post("/member/modifyByAdmin.json", this.getFormView().getValues(), function(text,data){
        					// 가입 실패
        					if(data.json().status !=200){
        						// validate 메세지 
        						var message = data.json().desc.split("\n");
        						webix.message({ type:"error", text:message[0].replace("="," ") });
        					} else { // 가입 성공
        						webix.message("수정이 완료되었습니다.");
        						$$("admin_modify_member_popup").hide();
        						loadAdminMemberList();
        					}
        				});
        			}},
        			{ view:"button", value:"취소", click:function(){ // 가입취소
        				$$("admin_modify_member_popup").hide();
        			}},
        		]},
        	]
        }
    }).show();
    // side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();
    // database 정보 로딩
    getDataParseView("/database/serverList",serverInfo,"select_database_use_member_database_view",false,false,false);
    setTimeout(function(){
    	findMemberDatabse(member_id);	
    }, 200)
};

// 회원정보 로딩.
var findMemberDatabse = function(member_id){
    // 회원정보 로딩
    webix.ajax().get("/member/list.json", {"id":member_id}, function(text,data){
		if(data.json().status !=200){
			// validate 메세지 
			webix.message({ type:"error", text:data.json().desc});
		} else { 
			// 회원 리스트
			$$('admin_modify_member_form').setValues(data.json().result.memberList[0]);
			// 회원의 데이터베이스 처리
			if(null!=data.json().result.memberList[0]){
				var memberDatabaseList = data.json().result.memberList[0].memberDatabaseVOList;

    			$$("select_database_use_member_database_view").eachRow(function (id){				
    				$$("select_database_use_member_database_view").getItem(id).selected = "";
    				for(var i in memberDatabaseList){
        				if(memberDatabaseList[i].serverInfo.serverInfoSeq ==$$("select_database_use_member_database_view").getItem(id).serverInfoSeq){
        					$$("select_database_use_member_database_view").getItem(id).selected = "선택";
        				}
					}
    			});
				// 입력한 값을 재 로딩 한다.
    			$$("select_database_use_member_database_view").refresh();
			}
		}
    });
}

// 회원 정보 삭제
var adminDeleteMember = function(id){
	webix.confirm({
		title: "회원정보 삭제",
		ok:"Yes", cancel:"No",
		text:"회원정보를 삭제하시겠습니까?",
		callback:function(result){
			if(result==true){
				webix.ajax().del("/member/remove.json?id="+id, function(text,data){
					// 삭제 결과 확인
					if(data.json().status ==200){	
						window.setTimeout(function(){
							webix.message("회원 삭제가 완료되었습니다.");
							loadAdminMemberList();
						}, 100)
					} else {
						webix.message(data.json().desc);
					}
				});
			}
		}
	});
}

// TODO 데이터 베이스와 회원간의 연결

// TODO 쿼리 로그 검색 

// TODO 개인정보 필드에 대한 정의

// TODO 각종 환경 설정