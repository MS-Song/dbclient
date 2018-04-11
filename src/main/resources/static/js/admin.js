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
	webix.ajax().get("/database/list", function(text,data){
		// 데이터베이스 정보를 획득한 경우에 테이블에 넣는다.
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents
				&& null!=data.json().contents.content
				&& 0<data.json().contents.content.length
		){	
			// 데이터 1개를 꺼내서 columns 를 만든다. 
			// row 1개를 꺼내서 필드를 구성한다.
			var loop=0;
			$.each(data.json().contents.content[0],function(index){
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
			$$("admin_database_list_view").config.columns[loop].template='<input type="button" value="수정" style="width:40px;" onClick="adminModifyDatabasePopup(#id#);"/>',
			$$("admin_database_list_view").config.columns[loop+1]={};
			$$("admin_database_list_view").config.columns[loop+1].id = "delete_database";
			$$("admin_database_list_view").config.columns[loop+1].header = "삭제";
			$$("admin_database_list_view").config.columns[loop+1].adjust = true;
			$$("admin_database_list_view").config.columns[loop+1].template='<input type="button" value="삭제" style="width:40px;" onClick="adminDeleteDatabase(#id#);"/>',
			
			// 관리자 메뉴 추가 종료
			$$("admin_database_list_view").refreshColumns();

			$$("admin_database_list_view").parse(data.json().contents.content);
    		$$("admin_database_list_view").refresh();
		} else {
			webix.message(data.json().message);
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
				{ id:"charset", 	view:"select", 	label:'charset', 		name:"charset",		options:charset	},
        		{ id:"driver",		view:"select",	label:'driver',			name:"driver", 		options:drivers },
				{ id:"port", 		view:"text", 	label:'port', 			name:"port" 		},
				{
					cols:[
		    			{ id:"resist", 	view:"button", label:'등록', click:function(){
		    				webix.ajax().post("/database/add", this.getFormView().getValues(), function(text,data){
		    					if(data.json().httpStatus ==200){
		    						webix.message("데이터베이스 등록 완료");
		    						window.setTimeout(function(){
		    							$$("admin_add_database_popup").hide();
		    							adminDatabaseListPopup();
		    						}, 300)										

		    					} else { 
	    							webix.message({ type:"error", text:data.json().desc});
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
var adminModifyDatabasePopup = function(id){
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
				{ id:"id", 			view:"text", 	type:"hidden",			name:"id",			height:0		},
				{ id:"host", 		view:"text", 	label:'host', 			name:"host" 		},
				{ id:"hostAlias", 	view:"text", 	label:'hostAlias', 		name:"hostAlias" 	},
				{ id:"schemaName", 	view:"text", 	label:'schemaName', 	name:"schemaName" 	},
				{ id:"account", 	view:"text", 	label:'account', 		name:"account" 		},
				{ id:"password", 	view:"text", 	label:'password', 		name:"password" 	},
        		{ id:"driver",		view:"select",	label:'driver',			name:"driver", 		options:drivers },
				{ id:"charset", 	view:"select", 	label:'charset', 		name:"charset",		options:charset	},
				{ id:"port", 		view:"text", 	label:'port', 			name:"port" 		},
				{
					cols:[
		    			{ id:"resist", 	view:"button", label:'수정', click:function(){
		    				webix.ajax().put("/database/modify", this.getFormView().getValues(), function(text,data){
		    					// 가입 실패
		    					if(data.json().httpStatus ==200){
		    						webix.message("데이터베이스 수정 완료");
		    						// 0.3 초 후에 팝업 닫고 데이터베이스 팝업창 이로드 한다.	
		    						window.setTimeout(function(){
		    							$$("admin_modify_database_popup").hide();
		    							adminDatabaseListPopup();
		    						}, 300)										
		    					} else { 
		    						// validate 메세지 
	    							webix.message({ type:"error", text:data.json().message});
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

	webix.ajax().get("/database/list", {"id":id}, function(text,data){
		if(data.json().httpStatus ==200){
			$.each(data.json().contents.content,function(){
				$$('admin_modify_database_form').setValues({ 
					id:this.id,
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
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
	});
};

// 데이터 베이스 삭제
var adminDeleteDatabase = function(databaseId){
	webix.confirm({
		title: "데이터베이스 정보 삭제",
		ok:"Yes", cancel:"No",
		text:"데이터베이스 정보를 삭제하시겠습니까?",
		callback:function(result){
			if(result==true){
				// 데이터 베이스 삭제 실행
				webix.ajax().del("/database/deleteDatabases.json?databaseId="+databaseId, function(text,data){
					// 삭제 결과 확인
					if(data.json().httpStatus ==200){	
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
		    					id:"admin_search_login_id", 		
		    					view:"text", 	
		    					placeholder:'loginId search',		
		    					name:"loginId",			
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
		        	columns:[
		        		{id:"id", 					header:"Member No",		adjust:true, sort:"int" 	},
		        		{id:"loginId", 				header:"Login ID",		adjust:true, sort:"string" 	},
		        		{id:"teamName", 			header:"Team Name",		adjust:true, sort:"string" 	},
		        		{id:"name", 				header:"Member Name",	adjust:true, sort:"string" 	},
		        		{id:"authType", 			header:"Auth Type",		adjust:true, sort:"string" 	},
		        		{id:"createDate", 			header:"Join Data",		adjust:true, sort:"string" 	},
		        		{id:"admin_modify_member", 	header:"Modify",		adjust:true,
		        			template:'<input type="button" value="수정" style="width:40px;" onClick="adminModifyMemberPopup(\'#id#\');"/>'
		        		},
		        		{id:"admin_delete_member",	header:"Delete",		adjust:true,
		        			template:'<input type="button" value="삭제" style="width:40px;" onClick="adminDeleteMember(\'#id#\');"/>'
		        		}
		        	],
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
	$$("admin_member_list_view").clearAll();
	webix.ajax().get("/member/list", $$("admin_member_list_search_form").getValues(), function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents
				&& null!=data.json().contents.content
				&& 0<data.json().contents.content.length){
	
					$$("admin_member_list_view").parse(data.json().contents.content);
					$$("admin_member_list_view").refresh();
		} else {
			webix.message({ type:"error", text:data.json().message});
		}  
	});
}

// 회원 수정 Popup
var adminModifyMemberPopup = function(memberId){
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
        		{margin:5, cols:[
        			{ id:"apikey", 				view:"text", 	label:'인증키', 	adjust:true,	name:"apikey"},
        			{ view:"button", 			value:"갱신" , 	type:"form", 	width:50, click:function(){
        				var formMember = $$('admin_modify_member_form').getValues();
        				webix.ajax().put("/member/renewApiKeyByAdmin", {"loginId":formMember.loginId}, function(text,data){
        					if(data.json().httpStatus ==200){
        						webix.message("인증키가 갱신되었습니다.");
        						$$('admin_modify_member_form').setValues({apikey:data.json().contents.apikey},true);
        					} else { 
        						webix.message({ type:"error", text:data.json().message});
        					}
        				});
        			}}
        		]},
        		{ id:"id", 						view:"text", 	type:"hidden",		height:0,		name:"id"										},
        		{ id:"loginId", 				view:"text", 	label:'로그인ID', 	labelWidth:100, name:"loginId",	 		readonly:true			},
        		{ id:"password", 				view:"text", 	label:'패스워드', 		labelWidth:100, name:"password",		type:"password"			},
        		{ id:"password_repeat",			view:"text", 	label:'패스워드 재입력', labelWidth:100, name:"password_repeat",	type:"password"			},
        		{ id:"teamName", 				view:"text", 	label:'팀명', 		labelWidth:100, name:"teamName" 								},
        		{ id:"name", 					view:"text", 	label:'성명', 		labelWidth:100, name:"name" 									},
        		{ id:"passwordQuestion", 		view:"text", 	label:'비밀번호질문', 	labelWidth:100, name:"passwordQuestion" 						},
        		{ id:"passwordAnswer", 			view:"text", 	label:'비밀번호답변', 	labelWidth:100, name:"passwordAnswer" 							},
        		{ id:"authType", 				view:"select",	label:'회원 권한', 		labelWidth:100, name:"authType",		options:authtypeList 	},
        		{
                	id:"select_database_use_member_database_view",
                	view:"datatable",
                	columns:[
                			{ id:"id",				header:"database Id",	width:100, sort:"int"	},
        					{ id:"host",			header:"Host",			width:100, sort:"string"},
         					{ id:"hostAlias",		header:"Host Alias",	width:100, sort:"string"},
         					{ id:"schemaName",		header:"SchemaName",	width:100, sort:"string"},
         					{ id:"account",			header:"Account",		width:100, sort:"string"},
         					{ id:"port",			header:"Port",			width:60,  sort:"int"	},
         					{ id:"selected",		header:"선택",			width:50				},         					
         					{ id:"memberDatabaseId",view:"text", 			type:"hidden",	width:0 }
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
	    	    			var formMember = $$('admin_modify_member_form').getValues();
	    	    			params = {};
	    	    			params.id=clickedRow.memberDatabaseId;
	    	    			params.databaseId=clickedRow.id;
	    	    			params.memberId = formMember.id;
	    	    			
	    	    			webix.ajax().post("/member/addOrModifyMemberDatabaseByAdmin", params, function(text,data){
	        					// 업데이트 실패
	        					if(data.json().httpStatus ==200){
	        						webix.message("수정이 완료되었습니다.");
	        						findMemberDatabase(formMember.id);
	        					} else { // 업데이트 성공
	        						webix.message({ type:"error", text:data.json().message});
	        					}
	        				});
	    	    		}
	    			}
        		},
        		{margin:5, cols:[
        			{ view:"button", value:"수정" , type:"form", click:function(){
        				var sendData = this.getFormView().getValues();
        				if(sendData.password != sendData.password_repeat) {
        					webix.message({ type:"error", text:"패스워드와 재입력한 패스워드가 다릅니다. 패스워드를 확인해주세요"});
        					return;
        				}
        				// 회원정보 수정
        				webix.ajax().put("/member/modifyByAdmin", this.getFormView().getValues(), function(text,data){
        					if(data.json().httpStatus ==200){
        						webix.message("수정이 완료되었습니다.");
        						$$("admin_modify_member_popup").hide();
        						loadAdminMemberList();

        					} else {
        						webix.message({ type:"error", text:data.json().message});
        					}
        				});
        			}},
        			{ view:"button", value:"취소", click:function(){
        				$$("admin_modify_member_popup").hide();
        			}},
        		]},
        	]
        }
    }).show();
    // side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();
    // database 정보 로딩
    getDataParseView("/database/list",database,"select_database_use_member_database_view",false,false,false);
    // 데이터 베이스 리스트를 그리고 선택된 객체를 선택시키기 위해 delay 시킨다 
    setTimeout(function(){
    	findMemberForModify(memberId);	
    }, 1000)
};

// 회원정보 로딩.
var findMemberForModify = function(memberId){
    // 회원정보 로딩
    webix.ajax().get("/member/list", {"id":memberId}, function(text,data){
		if(data.json().httpStatus ==200
				&& null!=data.json().contents
				&& null!=data.json().contents.content
				&& 0<data.json().contents.content.length){

			// 회원 리스트
			$$('admin_modify_member_form').setValues(data.json().contents.content[0]);
			webix.ajax().get("/member/list", $$("admin_member_list_search_form").getValues(), function(text,data){
				if(data.json().httpStatus == 200 
						&& null!=data.json().contents
						&& null!=data.json().contents.content
						&& 0<data.json().contents.content.length) {
			
							$$("admin_member_list_view").parse(data.json().contents.content);
							$$("admin_member_list_view").refresh();
				} else {
					webix.message({ type:"error", text:data.json().message});
				}  
				// 회원의  Database 로딩
				findMemberDatabase(memberId);
	
			});
		} else {
			webix.message({ type:"error", text:data.json().message});
		}
    });
};

var findMemberDatabase = function(memberId){
	webix.ajax().get("/member/findMemberDatabase",{"memberId":memberId} , function(text,data){
		if(data.json().httpStatus == 200 
			&& null!=data.json().contents
			&& null!=data.json().contents.content){
			
			var memberDatabaseList = data.json().contents.content;
			$$("select_database_use_member_database_view").eachRow(function (id){				
				$$("select_database_use_member_database_view").getItem(id).selected = "";
				$$("select_database_use_member_database_view").getItem(id).memberDatabaseId="";
				for(var i in memberDatabaseList){
    				if(memberDatabaseList[i].databaseVo.id == $$("select_database_use_member_database_view").getItem(id).id ){
    					$$("select_database_use_member_database_view").getItem(id).selected = "선택";
    					$$("select_database_use_member_database_view").getItem(id).memberDatabaseId=memberDatabaseList[i].id;
    				}
				}
			});
		}
		// 입력한 값을 재 로딩 한다.
		$$("select_database_use_member_database_view").refresh();
   });		
};


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
					if(data.json().httpStatus ==200){	
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
};

// TODO 쿼리 로그 검색 

// TODO 개인정보 필드에 대한 정의

// TODO 각종 환경 설정