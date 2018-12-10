
// 관리자 메뉴 지연 로딩
var adminMenuLazyLoading = function(){
	setTimeout(() => {
		if(undefined==member.authType){
			adminMenuLazyLoading();
		} else {
    		if(member.authType=='ADMIN') { // 관리자 권한이 있는 경우 메뉴 활성화
    			$$("menu").getBody().data.add({id: 5, value: null, 				icon: null, 			func: null},						4);
    			$$("menu").getBody().data.add({id: 6, value: " 관리자 메뉴", 		icon: "cog", 			func: null},						5);
        		$$("menu").getBody().data.add({id: 8, value: " 회원 관리", 			icon: "user", 			func: "adminMemberListPopup"},		7);
        		$$("menu").getBody().data.add({id: 9, value: " 카테고리관리", 		icon: "file", 			func: "adminCategoryPopup"},		8);
        		$$("menu").getBody().data.add({id: 10,value: " 관리자 메세지 전송", 	icon: "mail-reply",		func: "adminSendMessagePopup"},		9);
    		}
		}
	}, 100);
}

webix.ready(function(){
	// admin menu loading
	adminMenuLazyLoading();
});

// 회원 리스트 팝업
var adminMemberListPopup = function(){
	webix.ui({
        view:"window",
        id:"admin_member_list_popup",
        autowidth:true,
		autoheight:true,
        position:"center",
        modal:true,
        head:{
        	view:"button",value:"회원 관리 닫기" , click:function(){
        		$$("admin_member_list_popup").hide();
        	},hotkey: "esc"
    	},
        body:{
        	rows:[{
            	id:"admin_member_list_search_form",
    			view:"form",
    			borderless:true,
    			elements: [{ 
    					id:"admin_search_page", 	
    					view:"text", 	
    					name:"page",
    					value:1,
    					type:"hidden",
    					height:0,
    					width:0,
					},{
    				cols:[{ 
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
    				},
					{ 
    					id:"admin_search_button", 	
    					view:"button", 	
    					label:'검색',		 				
    					on:{"onItemClick":function(){
    					    $$('admin_search_page').setValue(0);
    					    $$("admin_member_list_page").config.page=0;
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
					},
					{ 
						id:"admin_add_member_button", 		
						view:"button", 	
						label:'회원추가',		 				
						on:{"onItemClick":function(){
							resister_member_popup();
						}}
					}] // end cols
    			}] // end elements
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
				//autoheight:true,
				height:500,
            	scroll:"y",
				data:[]
        	},{
        		cols : [{},{ // 페이지를 가운데 두기 위해 앞뒤로 처리
	        		id:"admin_member_list_page",
					view: 'pager',
					template: '{common.first()} {common.prev()} {common.pages()} {common.next()} {common.last()}',
					master:false,
					size: 20,
					group: 5,
					count: 1000,
					align:'center',
					on: {
					    onItemClick: function(id, e, node) {
						    $$("admin_search_page").setValue(id*1 + 1);
							loadAdminMemberList();
					    }
					}
        		},{}] // end cols 
        	}] // rows
        } // body
    }).show()

    // side menu 닫기
	if($$("menu").isVisible()) $$("menu").hide();
    loadAdminMemberList();
};

// 회원 리스트 Loading
var loadAdminMemberList = function(){
	webix.ajax().get("/member/list", $$("admin_member_list_search_form").getValues(), function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents){
			$$("admin_member_list_page").config.size=data.json().contents.size;
			$$("admin_member_list_page").config.count=data.json().contents.totalElements;
			$$("admin_member_list_page").refresh();
			$$("admin_member_list_view").clearAll();// 이미 있는 내용은 모두 지운다
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
        				let formMember = $$('admin_modify_member_form').getValues();
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
        		{ id:"mobileNumber",			view:"text", 	label:'핸드폰 번호',	labelWidth:100, name:"mobileNumber" 							},
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
         					{ id:"schemaOwner",		header:"schemaOwner",	width:100, sort:"string"},
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

        				sendData.apikey = null;
        				webix.ajax().put("/member/modifyByAdmin", sendData, function(text,data){
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
        			},hotkey: "esc"},
        		]},
        	]
        }
    }).show();
    // side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();
    // database 정보 로딩
    getDataParseView("/database/list",null,"select_database_use_member_database_view",false,false,false);
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
						webix.message(data.json().message);
					}
				});
			}
		}
	});
};


// 카테고리 관리
var adminCategoryPopup = function(){
	if($$("admin_category_popup")==undefined){
		webix.ui({
		    view:"window",
		    id:"admin_category_popup",
		    autowidth:true,
			minHeight:500,
	//		autoheight:true,
		    position:"center",
		    modal:true,
		    head:{
	        	cols:[
	  		        {
	  		        	view:"button",value:"카테고리관리 닫기" , click:function(){
	  		        		$$("admin_category_popup").hide();
	  		        	},hotkey: "esc"
	  		        },
			    	{
	  		        	view:"button",value:"카테고리 추가" , click:function(){
	  		        		category_add_or_modify_popup(null,null);
	  		        	}
			    	}
	        	]
	        },
		    body:{
		    	id:"admin_category_list_view",
	        	view:"datatable",
	        	columns:[],
				tooltip:true,
				select:"row",
				resizeColumn:true,
				autowidth:true,
				autoheight:true,
				data:[]
		    }
		}).show();
	}
	
	$$("admin_category_popup").show();

	// side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();
    
    // 카테고리 리스트 생성
    adminCategoryListView();
};

//카테고리 리스트
var adminCategoryListView = function(){

    // 정보를 조회하기 전에 리셋 한다. 
	// 이미 있는 내용은 모두 지운다 
	$$("admin_category_list_view").config.columns = [];
	$$("admin_category_list_view").clearAll();
	
	webix.ajax().get('/category/list', {}, function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents
				&& 0<data.json().contents.length
		){	
			// 데이터 1개를 꺼내서 columns 를 만든다. 
			// row 1개를 꺼내서 필드를 구성한다.
			var loop=0;
			$.each(data.json().contents[0],function(index){
				$$("admin_category_list_view").config.columns[loop]={};
				$$("admin_category_list_view").config.columns[loop].id = index;
				$$("admin_category_list_view").config.columns[loop].header = index;
				$$("admin_category_list_view").config.columns[loop].adjust = true;
				if(!isNaN(this)){
					$$("admin_category_list_view").config.columns[loop].sort="int";
				} else {
					$$("admin_category_list_view").config.columns[loop].sort="string";	
				}
				loop++;
			});
			// 관리 메뉴를 추가한다.
			$$("admin_category_list_view").config.columns[loop]={};
			$$("admin_category_list_view").config.columns[loop].id = "modify_database";
			$$("admin_category_list_view").config.columns[loop].header = "수정";
			$$("admin_category_list_view").config.columns[loop].adjust = true;
			$$("admin_category_list_view").config.columns[loop].template='<input type="button" value="수정" style="width:40px;" onClick="category_add_or_modify_popup(#id#,\'#name#\');"/>',
			$$("admin_category_list_view").config.columns[loop+1]={};
			$$("admin_category_list_view").config.columns[loop+1].id = "delete_database";
			$$("admin_category_list_view").config.columns[loop+1].header = "삭제";
			$$("admin_category_list_view").config.columns[loop+1].adjust = true;
			$$("admin_category_list_view").config.columns[loop+1].template='<input type="button" value="삭제" style="width:40px;" onClick="category_delete(#id#);"/>',
			// 관리자 메뉴 추가 종료
			$$("admin_category_list_view").refreshColumns();

			$$("admin_category_list_view").parse(data.json().contents);
    		$$("admin_category_list_view").refresh();
		} else {
			errorControll(data.json());
		}
	});
};

//카테고리 추가/변경 팝업
var category_add_or_modify_popup = function(id,name){
	if($$("category_add_or_modify_popup")==undefined){
		webix.ui(
			{
		        view:"window",
		        id:"category_add_or_modify_popup",
		        width:300,
		        height:120,
		        position:"center",
		        modal:true,
		        head:"카테고리 추가 또는 수정",
		        body:{
		        	id:"category_add_or_modify_form",
		        	view:"form",
		        	elements:[
		    			{ id:"category_id",		view:"text", type:"hidden", 	name:"id",	height:0},
		    			{ id:"category_name",	view:"text", label:'카테고리명', 	name:"name", 				
		    				on:{"onKeyPress":function(key,e){// 카테고리 입력/수정 실행
		    					// enter 를 입력하면, 실행한다.
		    					if(key==13) {
		    						$$("category_add_or_modify_confirm_button").callEvent("onItemClick");
		    					}
		    				}}	
		    			},
		    			{margin:5, 
		    				cols:[
			    				{ id:"category_add_or_modify_confirm_button",view:"button", value:"확인" , type:"form", 
			    					on:{"onItemClick":function(){// 입력 처리
			    						webix.ajax().post("/category/save", this.getFormView().getValues(), function(text,data){
			    							if(data.json().httpStatus==200){
			    								webix.message(data.json().message);
			    								$$("category_add_or_modify_popup").hide();
			    								// 관리자 목록 및 사용자 목록 리프래시
			    								adminCategoryListView();
			    								traffic_guard_category_menu_createor();
			    								
			    							} else {
			    								webix.message({ type:"error", text:data.json().message });
			    							}
			    						});
			    					}},
			    				},
			    				{ id:"category_add_or_modify_cansel_button", view:"button", value:"취소",click:function(){
			    					$$("category_add_or_modify_popup").hide();
			    				},hotkey: "esc"}
			    			]
		    			}
		    			
		        	]
		        }
		    });
	};
	$$("category_add_or_modify_popup").show();
	
	// 변경인 경우에는 값을 읽어서 처리해야 함.
	if(id!=null){
		$$("category_add_or_modify_form").setValues({id:id,name:name});
	} 
};


// 카테고리 삭제
var category_delete = function (id){
	webix.ajax().del("/category/delete?id="+id, function(text,data){
		if(data.json().httpStatus==200){
			webix.message(data.json().message);
			// 관리자 목록 및 사용자 목록 리프래시
			adminCategoryListView();
			traffic_guard_category_menu_createor();
		} else {
			webix.message({ type:"error", text:data.json().message });
		}
	});
}

/**
 * 관리자 메세지 전송
 */
var adminSendMessagePopup = function(){
	webix.ui({
	    view:"window",
	    id:"admin_send_message_popup",
		width:500,
		autoheight:true,
	    position:"center",
	    modal:true,
	    head:" 관리자 메세지 (모든 사용자에게 메세지를 전송한다) ",
	    body:{
	    	id:"admin_send_message_form",
	    	view:"form",
	    	borderless:true,
	    	elements: [
	    		{				
	    			id:"admin_send_message_text",
	    			view:"textarea",
					height:120,
					adjust:true,
					name:"message",
				},
				{ 
					id:"admin_send_message_button", 	
					view:"button", 	
					label:'전송',		 				
					on:{"onItemClick":function(){
						client.send("/app/send", {}, JSON.stringify({"httpStatus":200,"message":$$("admin_send_message_text").getValue(),"senderApikey":member.apikey}));
						webix.message("관리자 메세지가 전송되었습니다.");
					}}
				},
				{ 
					id:"admin_send_message_reset_button", 		
					view:"button", 	
					label:'취소',		 				
					click:function(){
						$$('admin_send_message_popup').hide();
					},hotkey: "esc"
				}
			]// end elements
	    }
	}).show();
    // side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();
}

/**
 * 관리자 메세지 수신
 */
var adminRecieveMessagePopup = function(message,sender){
	webix.ui({
	    view:"window",
	    id:"admin_recieve_message_popup",
		width:500,
		autoheight:true,
	    position:"center",
	    modal:true,
	    head:{
			view:"button", value:"관리자 메세지 닫기", click:function() {
				$$("admin_recieve_message_popup").hide();	
			} ,hotkey: "esc"
		},
	    body:{
	    	rows:[{
		    	id:"admin_recieve_message",
		    	template: message.replace("\n","<br/>")
	    	},{
		    	view:"label",
		    	label:"발신자: " + sender.loginId
	    	},{
		    	view:"label",
		    	label:"수신시간  : " + formatDate(new Date())
	    	}]
	    }
	}).show();
}

let sock;
let client;
webix.ready(function(){
	sock = new SockJS("/alarm");			// sock 생성
	client = Stomp.over(sock);				// client 호출
	client.debug = null						// debug off
	client.connect({}, function (frame) {	// 연결
		client.subscribe('/topic/recieve', function (message) {
			let body = JSON.parse(message.body);
			// 정상적인  상황이면 메세지 전송
			if(body.httpStatus == 200) {
				// 전송자 본인에게는 전송하지 않는다.
				if(body.contents.id!=member.id){
					adminRecieveMessagePopup(body.message,body.contents);					
				}
			} else {
				if(body.contents.id==member.id){
					// 에러 메세지를 관리자에게 출력
					webix.message({ type:"error", text:data.json().message });
				}
			}
        });
		if(undefined!=$$("incident_alarm_run_log")){
			client.subscribe('/topic/runAlarms', function (message) {
				let body = JSON.parse(message.body);
				$$("incident_alarm_run_log").add({
					"alarmId"		: body.contents.id,
					"status"		: body.httpStatus == 200 ? "성공":"실패"	
					,"subject" 		: body.contents.subject
					,"confirmYN" 	: "Y"
					,"processTime"	: body.processTime + ' ms'
					,"time"			: body.date + ' ' + body.time
				});	 
    			$$("incident_alarm_run_log").sort("time", "desc","string");
    			$$("incident_alarm_run_log").refresh();

			});
		}
	 });		
});