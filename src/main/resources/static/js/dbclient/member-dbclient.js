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