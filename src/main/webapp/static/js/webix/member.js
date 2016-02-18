/**
 * 회원 컨트롤
 */

// 회원정보
var id=null;
var authType=null;
var email=null;
var passwordQuestion=null;   		

// 로그인 된 경우 처리 
webix.ready(function(){
	// 로그인 정보 획득
	webix.ajax().get("/member/getLogin.json", function(text,data){
		// 로그인 정보를 획득한 경우
		if(data.json().status ==200 && null!=data.json().result){	
    		$.each(data.json().result,function(){
    			$.each(this,function(){
    				id=this.id;
    				authType=this.authType;
    				email=this.email;
    		   		passwordQuestion=this.passwordQuestion;
    			});
    		});
    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
    		$$("menu").getBody().data.remove(1);	// TODO ID Search 으로 변경
    		$$("menu").getBody().data.add({id: 1, value: id+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);
    		
    		// 권한 할당이 안된 경우 표시
    		if(authType==null){
    			// Database 관련 기능 비 활성화
    			$$("menu").getBody().data.remove(3);
    			$$("toolbar").addView({view: "label", label: "권한이 없습니다. 관리자에게 연락하시기 바랍니다."},3);
    			return false;
    		} else if(authType=='ADMIN') { // 관리자 권한이 있는 경우 메뉴 활성화
    			$$("menu").getBody().data.add({id: 5, value: null, 				icon: null, 		func: null},					4);
    			$$("menu").getBody().data.add({id: 6, value: " 관리자 메뉴", 		icon: "cog", 		func: null},					5);
        		$$("menu").getBody().data.add({id: 7, value: " Database 관리", 	icon: "database", 	func: "adminDatabaseListPopup"},6);
        		$$("menu").getBody().data.add({id: 8, value: " 회원 관리", 		icon: "user", 		func: "adminMemberListPopup"},	7);
    		}

    		// 로그인 된 경우 서버 선택이 되어 있지 않으면 서버 선택 창을 활성화 한다. 
    		if(serverInfoSeq==null){
    			select_database_popup();
    		}
    		
		} else { // 로그인 되어 있지 않으면 비 활성화
			$$("menu").getBody().data.remove(3);
		}
	});			
});		

// 로그인 폼
var login_form = {
	id:"login_form",
	view:"form",
	borderless:true,
	elements: [
		{ id:"login_id_input", view:"text", label:'ID', name:"id" },
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
					webix.ajax().post("/member/doLogin.json", this.getFormView().getValues(), function(text,data){
						// 로그인 실패 
						if(data.json().status !=200){
							// validate 메세지 
							var message = data.json().desc.split("\n");
							webix.message({ type:"error", text:message[0].replace("="," ") });
						} else { // 로그인 성공
							// 로그인 성공 액션
							webix.message("로그인 처리 완료");
							// 1초 후에 리로드 한다.	
							window.setTimeout(function(){
								document.location = document.location.href;	
							}, 1000)										
						}
					});
				}
			}},
			{ view:"button", value:"Cancel", click:function(){ // 로그인 취소
				// 로그인 팝업 닫기
				$$("login_popup").hide();
			}},
			{ view:"button", value:"회원가입", click:function(){// 회원 가입
				// 로그인 팝업 닫기 
				$$("login_popup").hide();
				// 회원 가입 팝업
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
    $$("menu").hide();
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
				webix.ajax().post("/member/doLogout.json", function(text,data){
					// 로그 아웃 결과 확인
					if(data.json().status ==200){	
						window.setTimeout(function(){
							document.location = document.location.href;	
						}, 1000)
					} else {
						webix.message(data.json().desc);
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
		{ view:"text", label:'ID', name:"id" ,value:""},
		{ view:"text", label:'email', name:"email" },
		{ view:"text", label:'패스워드', name:"password" ,type:"password"},
		{ view:"text", label:'비밀번호 찾기 질문', name:"passwordQuestion" },
		{ view:"text", label:'비밀번호 찾기 답변', name:"passwordAnswer" },
		{margin:5, cols:[
			{ view:"button", value:"가입" , type:"form", click:function(){
				webix.ajax().post("/member/add.json", this.getFormView().getValues(), function(text,data){
					// 가입 실패
					if(data.json().status !=200){
						// validate 메세지 
						var message = data.json().desc.split("\n");
						webix.message({ type:"error", text:message[0].replace("="," ") });
					} else { // 가입 성공
						webix.message("가입이 완료되었습니다.");
						// 1초 후에 리로드 한다.	
						window.setTimeout(function(){
							document.location = document.location.href;	
						}, 1000)										
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
		{ view:"text", name:"id" ,type:"hidden",height:0},
		{ view:"text", label:'email', name:"email" },
		{ view:"text", label:'패스워드', name:"password" ,type:"password"},
		{ view:"text", label:'패스워드 재입력', name:"password" ,type:"password"},
		{ view:"text", label:'비밀번호 찾기 질문', name:"passwordQuestion" },
		{ view:"text", label:'비밀번호 찾기 답변', name:"passwordAnswer" },
		{margin:5, cols:[
			{ view:"button", value:"수정" , type:"form", click:function(){
				webix.ajax().post("/member/modify.json", this.getFormView().getValues(), function(text,data){
					// 가입 실패
					if(data.json().status !=200){
						// validate 메세지 
						var message = data.json().desc.split("\n");
						webix.message({ type:"error", text:message[0].replace("="," ") });
					} else { // 가입 성공
						webix.message("수정이 완료되었습니다.");
						$$("modify_member_popup").hide();
					}
				});
			}},
			{ view:"button", value:"취소", click:function(){ // 가입취소
				$$("modify_member_popup").hide();
			}},
		]},
	],
	elementsConfig:{
		labelPosition:"top"
	}
};

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
    $$("menu").hide();
    
    //$$("modify_member_form").getFormView().getValues();
    $$("modify_member_form").setValues({
		id:id,
		authType:authType,
		email:email,
   		passwordQuestion:passwordQuestion
    });
};