/**
 * 로그 아웃 프로세스 처리
 */
var logout = function(){
	
};

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
 * 회원 리스트 폼
 */
var memberListForm = function(){
	
};

/**
 * 회원정보 수정 폼
 */
var memberModifyForm = function(){
	
};

$(document).ready(function(){

	// Link to open the dialog
	$( "#resistMember" ).click(function( event ) {
		$( "#resistUserPopup" ).dialog( "open" );
		event.preventDefault();
		resistMemberForm();
	});	
	
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
	    		html+='<span id="resistMember">[회원가입]</span>';
	    		html+='<span id="login">[로그인]</span>';
	    	}
    		$("#loginInfo").html(html)
	    }
	});
});