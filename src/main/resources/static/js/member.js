/**
 * 회원 컨트롤
 */

// 회원정보
var member={}

//회원 권한 리스트 로딩
var authtypeList = null;
var getMemberAuthTypes = function(){
	webix.ajax().get("/member/getAuthTypes",function(text,data){
		if(data.json().httpStatus ==200){
			authtypeList=data.json().contents;
		} else { 
			webix.message({ type:"error", text:data.json().message});
		}
	});
};

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
    		// 권한 할당이 안된 경우 표시
    		if(member.authType==null){
    			// Database 관련 기능 비 활성화
    			$$("menu").getBody().data.remove(3);
    			$$("toolbar").addView({view: "label", label: "권한이 없습니다. 관리자에게 연락하시기 바랍니다."},3);
    			return false;
    		} else if(member.authType=='ADMIN') { // 관리자 권한이 있는 경우 메뉴 활성화
    			$$("menu").getBody().data.add({id: 5, value: null, 				icon: null, 		func: null},					4);
    			$$("menu").getBody().data.add({id: 6, value: " 관리자 메뉴", 		icon: "cog", 		func: null},					5);
        		$$("menu").getBody().data.add({id: 7, value: " Database 관리", 	icon: "database", 	func: "adminDatabaseListPopup"},6);
        		$$("menu").getBody().data.add({id: 8, value: " 회원 관리", 			icon: "user", 		func: "adminMemberListPopup"},	7);
    		}

    		// 로그인 된 경우 서버 선택이 되어 있지 않으면 서버 선택 창을 활성화 한다. 
    		if(database.id==null){
    			select_database_popup();
    		}
    		
    		// 로그인이 된 경우 회원 권한 정보를 로딩 한다.
    		if(authtypeList==null){
    			getMemberAuthTypes();
    		}
    		
		} else { // 로그인 되어 있지 않으면
			// 메뉴 삭제
			$$("menu").getBody().data.remove(3);
			// 에러처리
			errorControll(data.json());
		}
	});			
});		

// 로그인 폼
var login_form = {
	id:"login_form",
	view:"form",
	borderless:true,
	elements: [
		{ id:"login_id_input", view:"text", label:'ID', name:"loginId" },
		{ id:"login_password_input", view:"text", label:'Password', name:"password" ,type:"password",
			on:{"onKeyPress":function(key,e){// 로그인 실행
					// enter 를 입력하면, 로그인을 실행 한다.
					if(key==13) $$("login_button").callEvent("onItemClick");
				}
			}
		},
		{margin:5, cols:[
			{ id:"login_button",view:"button", value:"Login" , type:"form", 
				on:{"onItemClick":function(){// 로그인 실행
					webix.ajax().post("/member/doLogin", this.getFormView().getValues(), function(text,data){
						if(data.json().httpStatus==200){
							webix.message(data.json().message);
							reload();										
						} else {
							webix.message({ type:"error", text:data.json().message });
						}
					});
				}
			}},
			{ view:"button", value:"Cancel", click:function(){ // 로그인 취소
				$$("login_popup").hide();
			}},
			{ view:"button", value:"회원가입", click:function(){// 회원 가입
				$$("login_popup").hide();
				resister_member_popup();
			}}
		]},
	],
	elementsConfig:{
		labelPosition:"top"
	}
};

// 로그인 팝업
var login_popup = function(){
    webix.ui({
        view:"window",
        id:"login_popup",
        width:300,
        position:"center",
        modal:true,
        head:"Log In",
        body:webix.copy(login_form)
    }).show();
	if($$("menu").isVisible()) $$("menu").hide();
    $$("login_id_input").focus();
}

// 로그 아웃 처리
var log_out=function(){
	webix.confirm({
		title: "로그 아웃 확인",
		ok:"Yes", cancel:"No",
		text:"로그아웃 하시겠습니까?",
		callback:function(result){
			if(result==true){
	 			// 로그 아웃 실행
				webix.ajax().post("/member/doLogout", function(text,data){
					// 로그 아웃 결과 확인
					if(data.json().httpStatus==200){	
						reload();
					} else {
						errorControll(data.json())
					}
				}); 					
			}
		}
	});
};

// 회원가입 폼
var resister_member_form = {
	id:"resister_member_form",
	view:"form",
	borderless:true,
	elements: [
		{ view:"text", label:'로그인ID', 			name:"loginId" 							},
		{ view:"text", label:'패스워드', 			name:"password",		type:"password"	},
		{ view:"text", label:'패스워드 재입력', 		name:"password_repeat",	type:"password"	},
		{ view:"text", label:'성명', 				name:"name" 							},
		{ view:"text", label:'팀명', 				name:"teamName" 						},
		{ view:"text", label:'비밀번호 찾기 질문', 	name:"passwordQuestion" 				},
		{ view:"text", label:'비밀번호 찾기 답변', 	name:"passwordAnswer" 					},
		{margin:5, cols:[
			{ view:"button", value:"가입" , type:"form", click:function(){
				// 패스워드 일치 여부 확인
				var sendData = this.getFormView().getValues();
				if(sendData.password != sendData.password_repeat) {
					webix.message({ type:"error", text:"패스워드와 재입력한 패스워드가 다릅니다. 패스워드를 확인해주세요"});
					return;
				}
				webix.ajax().post("/member/add", sendData, function(text,data){
					if(data.json().httpStatus ==200) {
						webix.message(data.json().message);
						reload();										
					} else {
						errorControll(data.json())
					}
				});
			}},
			{ view:"button", value:"취소", click:function(){ // 가입취소
				$$("resister_member_popup").hide();
			}},
		]},
	],
	elementsConfig:{
		labelPosition:"top"
	}
};

// 회원가입 팝업
var resister_member_popup = function(){
    webix.ui({
        view:"window",
        id:"resister_member_popup",
        width:500,
        position:"center",
        modal:true,
        head:"회원 가입",
        body:webix.copy(resister_member_form)
    }).show();
}

// 회원 정보 수정
var modify_member_form = {
	id:"modify_member_form",
	view:"form",
	borderless:true,
	elements: [
		{ view:"text", type:"hidden",			name:"id",				height:0		},
		{ view:"text", label:'로그인ID', 			name:"loginId",			readonly:true	},
		{ view:"text", label:'패스워드', 			name:"password",		type:"password"	},
		{ view:"text", label:'패스워드 재입력', 		name:"password_repeat",	type:"password"	},
		{ view:"text", label:'성명', 				name:"name" 							},
		{ view:"text", label:'팀명', 				name:"teamName" 						},
		{ view:"text", label:'비밀번호 찾기 질문', 	name:"passwordQuestion" 				},
		{ view:"text", label:'비밀번호 찾기 답변', 	name:"passwordAnswer" 					},
		{margin:5, cols:[
			{ view:"button", value:"수정" , type:"form", click:function(){
				var sendData = this.getFormView().getValues();
				if(sendData.password != sendData.password_repeat) {
					webix.message({ type:"error", text:"패스워드와 재입력한 패스워드가 다릅니다. 패스워드를 확인해주세요"});
					return;
				}
				webix.ajax().put("/member/modify", sendData, function(text,data){
					if(data.json().httpStatus ==200){
						member=data.json().contents;
						webix.message(data.json().message);
						// 메뉴의 성명 변경
						$$("menu").getBody().data.remove(1);	
			    		$$("menu").getBody().data.add({id: 1, value: member.name+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
						$$("modify_member_popup").hide();
					} else { 
						errorControll(data.json())
					}
				});
			}},
			{ view:"button", value:"취소", click:function(){
				$$("modify_member_popup").hide();
			}},
		]},
	],
	elementsConfig:{
		labelPosition:"top"
	}
};

// 추석 추가 

// 회원정보 수정
var modify_member_popup = function(){
    webix.ui({
        view:"window",
        id:"modify_member_popup",
        width:500,
        position:"center",
        modal:true,
        head:"회원정보 수정",
        body:webix.copy(modify_member_form)
    }).show();
    
    // side menu 닫기
    if($$("menu").isVisible()) $$("menu").hide();

    //$$("modify_member_form").getFormView().getValues();
    $$("modify_member_form").setValues({
    	id:member.id,
    	loginId:member.loginId,
    	name:member.name,
    	teamName:member.teamName,
    	passwordQuestion:member.passwordQuestion,
    	passwordAnswer:member.passwordAnswer
    });
};