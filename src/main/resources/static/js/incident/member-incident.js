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