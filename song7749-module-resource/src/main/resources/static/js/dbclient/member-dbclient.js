// 로그인 된 경우 처리 
webix.ready(function(){
	// 로그인 정보 획득
	webix.ajax().get("/member/getLogin", function(text,data){
		// 로그인 정보를 획득한 경우
		if(null!= data.json() 
				&& data.json().httpStatus ==200 
				&& null!=data.json().contents){	
			// 로그인 정보 획득 
			member = data.json().contents;
    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
    		$$("menu").getBody().data.remove(1);	
    		$$("menu").getBody().data.add({id: 1, value: member.name+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);

    		if(member.authType!=null){ // 권한 할당이 된 경우
    			$$("menu").getBody().data.add({id: 3, value: "Database 선택", 	icon: "database", 	func: "select_database_popup"});    			
    		} else if(member.authType==null){// 권한 할당이 안된 경우 표시
    			$$("toolbar").addView({view: "label", label: "권한이 없습니다. 관리자에게 연락하시기 바랍니다."},3);
    			return false;
    		} 
    		
    		// 로그인 된 경우 서버 선택이 되어 있지 않으면 서버 선택 창을 활성화 한다. 
    		if(database.id==null){
    			select_database_popup();
    		}
    		
    		// 로그인이 된 경우 회원 권한 정보를 로딩 한다.
    		if(authtypeList==null){
    			getMemberAuthTypes();
    		} 
    		// 회원의 저장되어 있는 에디터 내용을 불러 온다.
    		findEditorContents();
    		
		} else { // 로그인 되어 있지 않으면
			// 메뉴 삭제
			$$("menu").getBody().data.remove(3);
			// 에러처리
			errorControll(data.json());
		}
	});			
});