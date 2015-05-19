
/**
 * 회원가입 폼
 */
var resisteMember = function(){
	/**
	 * dialog 설정
	 */
	$("#commonsPopup").dialog({
		title: '회원가입',
		buttons: [
			{
				text: "가입",
				click: function() {
					if(confirm("회원 가입을 하시겠습니까??")){
//						$.post("./database/saveDatabases.json", $("#databaseManageTable").serializeArray(), function(data){
//							alert(data.result.message);
//						});
						window.setTimeout(function(){
							document.location = document.location.href;	
						}, 1000)
						
					};

				}
			},
			{
				text: "취소",
				click: function() {
					if(confirm("취소 하시겠습니까?")){
						$( this ).dialog( "close" );
					};
				}
			}
		]
	});
	
	$( "#commonsPopup" ).dialog( "open" );
	resistMemberForm();
}

/**
 * 회원가입 폼 
 */
var resistMemberForm = function(){
	
};

/**
 * 로그인 폼 
 */
var loginForm = function(){
	
};


/**
 * 회원 관리 매니저 이벤트 호출
 */
var memberManager = function(){
	
};

/**
 * 회원 리스트 폼
 */
var memberListForm = function(){
	
};

/**
 * 회원정보 수정 폼
 */
var memberModifyForm = function(){
	
};

/**
 * 로그 아웃 프로세스 처리
 */
var logout = function(){
	
};

$(document).ready(function(){
    /**
     * 로그인 정보 획득 로직
     */
	$.ajax({
	    url: './member/getLogin.json',
	    method: 'GET',
	    success:  function(data){
	    	var html = "";
	    	if(null!=data.result.message){
	    		html+='<span id="memberId">'+data.result.message+'</span>';
	    		html+='<span id="logout">[로그아웃]</span>';
	    	} else {
	    		html+='<span id="resistMember"><a href="javascript:resisteMember();">[회원가입]</a></span>';
	    		html+='<span id="login">[로그인]</span>';
	    	}
    		$("#loginInfo").html(html)
	    }
	});
});