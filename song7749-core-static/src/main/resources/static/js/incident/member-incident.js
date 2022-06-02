// 로그인 된 경우 처리 
webix.ready(function(){
	// 로그인 정보 획득
	webix.ajax().get("/member/getLogin", function(text,data){
		// 로그인 정보를 획득한 경우
		console.log();
		if(null!= data.json() 
				&& data.json().httpStatus ==200 
				&& null!=data.json().contents){	
			// 로그인 정보 획득 
			member = data.json().contents;
    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
    		$$("menu").getBody().data.remove(1);	
    		$$("menu").getBody().data.add({id: 1, value: member.name+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);
    		
       		// 로그인이 된 경우 회원 권한 정보를 로딩 한다.
    		if(authtypeList==null){
    			getMemberAuthTypes();
    		} 

		} else { // 로그인 되어 있지 않으면
			// 에러처리
			errorControll(data.json());
		}
	});	
	
	// 캐시 삭제 버튼 추가
	// 캐시 삭제 버튼을 노출 한다.
	$$("toolbar").addView({id:"toolbar_cache_remove",
		view:"button", 
		value:"Refresh Schema" , 
		type:"form",
		width:200,
		align:"right",
		on:{"onItemClick":function(){// 캐시 삭제 실행 
			// local storage 의 캐시먼저 
			webix.storage.local.clear();
			webix.message("캐시 삭제가 완료 되었습니다.");
			webix.ajax().get("/database/deleteCache", function(text,data){
				if(data.json().httpStatus !=200){
					webix.message({ type:"error", text:data.json().message});
				}
			});
		}}
	},4);

});

// 회원 검색 
var member_list_popup = function(view,multi){
	webix.ui({
        view:"window",
        id:"member_list_popup",
        width:1000,
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
    						if(key==13) {
    							// 페이지 초기화
    						    $$('member_search_page').setValue(0);
    						    $$("member_list_page").config.page=0;
    							loadMemberList(view);
    						}
    					}}
    				},
    				{ 
    					id:"member_search_name", 		
    					view:"text", 	
    					placeholder:'name search',		
    					name:"name",			
    					on:{"onKeyPress":function(key,e){// 검색 실행
    						if(key==13) {
    							// 페이지 초기화
    						    $$('member_search_page').setValue(0);
    						    $$("member_list_page").config.page=0;
    							loadMemberList(view);
    						}
    					}}
    				},
    				{ 
    					id:"member_search_team_name", 		
    					view:"text", 	
    					placeholder:'team name search',		
    					name:"teamName",			
    					on:{"onKeyPress":function(key,e){// 검색 실행
    						if(key==13) {
    							// 페이지 초기화
    						    $$('member_search_page').setValue(0);
    						    $$("member_list_page").config.page=0;
    							loadMemberList(view);
    						}
    					}}
    				},
					{ 
    					id:"member_search_button", 	
    					view:"button", 	
    					label:'검색',		 				
    					on:{"onItemClick":function(){
							// 페이지 초기화
						    $$('member_search_page').setValue(0);
						    $$("member_list_page").config.page=0;
							loadMemberList(view);
    					}}
					},
					{ 
						id:"member_confirm_button", 		
						view:"button", 	
						label:'적용',		 				
						on:{"onItemClick":function(){
							if(multi==false
								&& $$("member_list_view").$$("right").serialize().length>1 ){
									webix.message({ type:"error", text:"1명의 회원만 선택해야 합니다."});
								return;
							}
							$$(view).setValue($$("member_list_view").getValue());
							$$("member_list_popup").hide();
						}}
					}]// end cols
    			}] // end elements
        	},        	      
        	{
	        	id:"member_list_view",
	        	view:"dbllist",
	        	list:{ 
					autowidth:true,
					height:400,
	        		scroll:true 
	        	},
	            labelLeft:"회원리스트",
	            labelRight:"대상자리스트",
				data:[]
        	},{
        		cols : [{ // 페이지를 가운데 두기 위해 앞뒤로 처리
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
						    $$('member_search_page').setValue(id*1 + 1)
						    loadMemberList(view);
					    }
					}
        		}] // end cols 
        	}] // end rows
        }
    }).show();
	loadMemberList(view);
};

//회원 리스트 Loading
var loadMemberList = function(view){
	webix.ajax().get("/member/list", $$("member_list_search_form").getValues(), function(text,data){

		if(data.json().httpStatus == 200 
				&& null!=data.json().contents){
			// 페이지 초기화
			$$("member_list_page").config.size=data.json().contents.size;
			$$("member_list_page").config.count=data.json().contents.totalElements;
			$$("member_list_page").refresh();

			// 지우기 전에 오른쪽에 있는 객체를 미리 저장한다.
			var selectedMembers = $$("member_list_view").getValue();
			
			// 이미 있는 내용은 모두 지운다
			$$("member_list_view").$$("left").clearAll();
    		$$("member_list_view").parse(convertMemberList(data.json().contents.content));

    		// 이미 선택된 회원을 조회해서 right 에 넣는다.
    		if(""!=$$(view).getValue().trim()
    				&& $$(view).getValue().trim().length>0){

    			// 이미 선택된 회원이 없을 경우에는 넘겨받은 값을 사용, 있는 경우에는 있는값을 넣는다.
    			if(undefined==selectedMembers || ""==selectedMembers){
    				$$("member_list_view").setValue($$(view).getValue());	
    			} else { // 이미 선택된 회원들을 오른쪽에 넣는다.
    				$$("member_list_view").setValue(selectedMembers);
    			}
    			// 조회된 개수가 다를 경우에는 다른 페이지에 있는 회원이다. (ID 를 추가로 가져와서 셋팅한다)
    			var values = $$(view).getValue().split(",");
    			if(values.length != $$("member_list_view").$$("right").serialize().length){
    				// ID 를 추가로 가져온다.
    				webix.ajax().get("/member/list", {ids:$$(view).getValue()} , function(text,data){
    					if(data.json().httpStatus == 200 
    							&& null!=data.json().contents){
    						console.log(data.json().contents.content);
    						$$("member_list_view").parse(convertMemberList(data.json().contents.content));    					
    					}
    				});
    			}
    		}
		} else {
			webix.message({ type:"error", text:data.json().message});
		}  
	});
};

// 회원 객체 변경
var convertMemberList = function(data){
	var member_list = [];
	$.each(data,function(index,obj){
		member_list.push({id:obj.id, value:"["+obj.loginId + "] "+ "["+obj.teamName + "] " + obj.name });
	});
	return member_list
}