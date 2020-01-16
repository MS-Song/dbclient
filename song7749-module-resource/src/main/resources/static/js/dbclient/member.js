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
		// 맨 앞에 빈값 추가
		authtypeList.unshift('');
	});
};

// 로그인 폼
var login_form = {
	id:"login_form",
	view:"form",
	borderless:true,
	elements: [
		{ id:"login_id_input", view:"text", label:'ID', name:"loginId", type:"email" },
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
			},hotkey: "esc"},
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
	if($$("login_popup")==undefined){
	    webix.ui({
	        view:"window",
	        id:"login_popup",
	        width:300,
	        position:"center",
	        modal:true,
	        head:"Log In",
	        body:webix.copy(login_form)
	    }).show();
	    $$("login_id_input").focus();
	} else {
		$$("login_popup").show();
	}
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
		{ view:"text", label:'로그인ID', 			name:"loginId", 		type:"email"	},
		{ view:"text", label:'패스워드', 			name:"password",		type:"password"	},
		{ view:"text", label:'패스워드 재입력', 		name:"password_repeat",	type:"password"	},
		{ view:"text", label:'성명', 				name:"name" 							},
		{ view:"text", label:'팀명', 				name:"teamName" 						},
		{ view:"text", label:'핸드폰 번호',			name:"mobileNumber" 					},
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
						$$("resister_member_popup").hide();
						try {
							// 관리자 회원 등록인 경우에 회원 리스트 새로 고침을 위해서 호출 한다.
							$$("admin_search_button").callEvent("onItemClick");
						} catch (e) {}
					} else {
						errorControll(data.json())
					}
				});
			}},
			{ view:"button", value:"취소", click:function(){ // 가입취소
				$$("resister_member_popup").hide();
			},hotkey: "esc"},
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
 		{margin:5, cols:[
			{ id:"apikey", 				view:"text", 	label:'인증키', 	adjust:true,	name:"apikey"},
			{ view:"button", 			value:"갱신" , 	type:"form", 	width:50, click:function(){
				let formMember = $$('modify_member_form').getValues();
				webix.ajax().put("/member/renewApiKey", {"loginId":formMember.loginId,"password":formMember.password}, function(text,data){
					if(data.json().httpStatus ==200){
						webix.message("인증키가 갱신되었습니다.");
						$$('modify_member_form').setValues({apikey:data.json().contents.apikey},true);
					} else { 
						webix.message({ type:"error", text:data.json().message});
					}
				});
			}}
		]},
		{ view:"text", type:"hidden",			name:"id",				height:0		},
		{ view:"text", label:'로그인ID', 			name:"loginId",			readonly:true	},
		{ view:"text", label:'패스워드', 			name:"password",		type:"password"	},
		{ view:"text", label:'패스워드 재입력', 		name:"password_repeat",	type:"password"	},
		{ view:"text", label:'성명', 				name:"name" 							},
		{ view:"text", label:'팀명', 				name:"teamName" 						},
		{ view:"text", label:'핸드폰 번호',			name:"mobileNumber" 					},
		{ view:"text", label:'비밀번호 찾기 질문', 	name:"passwordQuestion" 				},
		{ view:"text", label:'비밀번호 찾기 답변', 	name:"passwordAnswer" 					},
		{margin:5, cols:[
			{ view:"button", value:"수정" , type:"form", click:function(){
				var sendData = this.getFormView().getValues();
				if(sendData.password != sendData.password_repeat) {
					webix.message({ type:"error", text:"패스워드와 재입력한 패스워드가 다릅니다. 패스워드를 확인해주세요"});
					return;
				}
				sendData.apikey = null;	// api key 는 넘기지 않는다.
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
			},hotkey: "esc"},
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
    if($$("menu").isVisible()) $$("menu").hide();

    //$$("modify_member_form").getFormView().getValues();
    $$("modify_member_form").setValues({
    	apikey:member.apikey,
    	id:member.id,
    	loginId:member.loginId,
    	name:member.name,
    	teamName:member.teamName,
    	passwordQuestion:member.passwordQuestion,
    	passwordAnswer:member.passwordAnswer
    });
};