// 로그인 된 경우 처리 
webix.ready(function(){
	// 로그인 정보 획득
	webix.ajax().get("/member/getLogin", function(text,data){
		// 로그인 정보를 획득한 경우
		if(data.json().httpStatus ==200 
				&& null!=data.json().contents){	
			// 로그인 정보 획득 
			member = data.json().contents;
    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
    		$$("menu").getBody().data.remove(1);	
    		$$("menu").getBody().data.add({id: 1, value: member.name+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);
		} else { // 로그인 되어 있지 않으면
			// 에러처리
			errorControll(data.json());
		}
	});			
});

// 회원 검색 
var member_list_popup = function(obj,view,multi){
	if($$("member_list_popup")==undefined){
		webix.ui({
	        view:"window",
	        id:"member_list_popup",
	        autowidth:true,
	        position:"center",
	        modal:true,
	        head:{
	        	view:"button",value:"회원 선택 닫기" , click:function(){
	        		$$("member_list_popup").hide();
	        	},hotkey: "esc"
        	},
	        body:{
	        	rows:[{
	            	id:"member_list_search_form",
	    			view:"form",
	    			borderless:true,
	    			elements: [{ 
	    					id:"member_search_page", 	
	    					view:"text", 	
	    					name:"page",
	    					value:1,
	    					type:"hidden",
	    					height:0,
	    					width:0,
					},	    				
    				{
	    				cols:[
	    				{ 
	    					id:"member_search_login_id", 		
	    					view:"text", 	
	    					placeholder:'loginId search',		
	    					name:"loginId",			
	    					on:{"onKeyPress":function(key,e){// 검색 실행
	    						if(key==13) loadMemberList();
	    					}}
	    				},
	    				{ 
	    					id:"member_search_name", 		
	    					view:"text", 	
	    					placeholder:'name search',		
	    					name:"name",			
	    					on:{"onKeyPress":function(key,e){// 검색 실행
	    						if(key==13) loadMemberList();
	    					}}
	    				},
	    				{ 
	    					id:"member_search_team_name", 		
	    					view:"text", 	
	    					placeholder:'team name search',		
	    					name:"teamName",			
	    					on:{"onKeyPress":function(key,e){// 검색 실행
	    						if(key==13) loadMemberList();
	    					}}
	    				},
						{ 
	    					id:"member_search_button", 	
	    					view:"button", 	
	    					label:'검색',		 				
	    					on:{"onItemClick":function(){
	    						loadAdminMemberList();
	    					}}
						},
						{ 
							id:"member_reset_button", 		
							view:"button", 	
							label:'리셋',		 				
							on:{"onItemClick":function(){
    							$$('member_list_search_form').setValues({ 
    								id:"",
    								email:"",
    								authType:""
    							});
    						}}
						}]// end cols
	    			}] // end elements
	        	},        	      
	        	{
		        	id:"member_list_view",
		        	view:"datatable",
		        	columns:[
		        		{id:"id", 					header:"Member No",		adjust:true, sort:"int" 	},
		        		{id:"loginId", 				header:"Login ID",		adjust:true, sort:"string" 	},
		        		{id:"teamName", 			header:"Team Name",		adjust:true, sort:"string" 	},
		        		{id:"name", 				header:"Member Name",	adjust:true, sort:"string" 	},
		        		{id:"member_selected", 		header:"선택",			adjust:true,
		        			template:'<input type="button" value="선택" style="width:40px;" onClick=";"/>'
		        		}
		        	],
					tooltip:true,
					select:"row",
					multiselect:true,
					resizeColumn:true,
					autowidth:true,
					autoheight:true,
					data:[]
	        	},{
	        		cols : [{},{ // 페이지를 가운데 두기 위해 앞뒤로 처리
		        		id:"member_list_page",
						view: 'pager',
						template: '{common.first()} {common.prev()} {common.pages()} {common.next()} {common.last()}',
						master:false,
						size: 20,
						group: 5,
						count: 1000,
						align:'center',
						on: {
						    onItemClick: function(id, e, node) {
							    this.select(id);
							    $$('member_list_search_form').setValues({ 
							    	page:this.config.page*1 + 1,
								});
							    loadMemberList();
						    }
						}
	        		},{}] // end cols 
	        	}] // end rows
	        }
	    });
	}
	$$("member_list_popup").show();
	loadMemberList();
};

webix.ready(function(){
member_list_popup();
});

//회원 리스트 Loading
var loadMemberList = function(){
	// 이미 있는 내용은 모두 지운다 
	$$("member_list_view").clearAll();
	webix.ajax().get("/member/list", $$("member_list_search_form").getValues(), function(text,data){

		if(data.json().httpStatus == 200 
				&& null!=data.json().contents){
			$$("member_list_page").config.size=data.json().contents.size;
			$$("member_list_page").config.count=data.json().contents.totalElements;
			$$("member_list_page").refresh();

			$$("member_list_view").clearAll();
			$$("member_list_view").parse(data.json().contents.content);
			$$("member_list_view").refresh();
		} else {
			webix.message({ type:"error", text:data.json().message});
		}  
	});
};
