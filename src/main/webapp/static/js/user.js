// 회원 관련 기능 API 오퍼레이션 목록
var apiMemberOperations = null;
// 로그인 관련 기능 API 오퍼레이션 목록
var apiLoginOperations = null;

//회원 관련 기능 API 모델
var apiMemberModels = null;
// 로그인 관련 기능 API 모델
var apiLoginModels = null;

/**
 * 회원가입 폼
 */
var resisteMember = function(){
	var operationPath = '/member/add';
	
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
						removeErrorMessage();
						$.post("."+operationPath+".json", $("#resistMemberForm").serializeArray(), function(data){
							if(data.status == 200){
								alert(data.result.message);	
								window.setTimeout(function(){
									document.location = document.location.href;	
								}, 1000)
							} else {
								addErrorMessage(data);
							}
						});
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
		],
		autoOpen: false,
		width: 700,
		height:300,
		modal: true,
	});
	
	$( "#commonsPopup" ).dialog( "open" );
	
	// html 생성
	var html='<form name="resistMemberForm" id="resistMemberForm">';
	html+=createHorizontalForm(apiMemberOperations,operationPath);
	html+='</form>';
	$( "#commonsPopup" ).html(html);
}

/**
 * 로그인 폼 
 */
var loginForm = function(){
	var operationPath = '/member/doLogin';
	
	/**
	 * dialog 설정
	 */
	$("#commonsPopup").dialog({
		title: '로그인',
		buttons: [
			{
				text: "로그인",
				click: function() {
					removeErrorMessage();
					$.post("."+operationPath+".json", $("#doLoginForm").serializeArray(), function(data){
						if(data.status == 200){
							window.setTimeout(function(){
								document.location = document.location.href;	
							}, 1000)
						} else {
							addErrorMessage(data);
						}
					});
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
		],
		autoOpen: false,
		width: 600,
		height:200,
		modal: true,
	});
	
	$( "#commonsPopup" ).dialog( "open" );
	
	// html 생성
	var html='<form name="doLoginForm" id="doLoginForm">';
	html+=createHorizontalForm(apiLoginOperations,operationPath);
	html+='</form>';
	$( "#commonsPopup" ).html(html);
};


/**
 * 회원 리스트 폼
 */
var memberListForm = function(){
	var operationPath = '/member/list';
	
	/**
	 * dialog 설정
	 */
	$("#commonsPopup").dialog({
		title: '회원리스트',
		buttons: [
			{
				text: "닫기",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		],
		autoOpen: false,
		width: 700,
		height:300,
		modal: true,
	});
	
	$( "#commonsPopup" ).dialog( "open" );

	
	
	// 검색 조건 설정
	// html 생성
	var html='<form name="memberListForm" id="memberListForm">';
	html+=createVerticalForm(apiMemberOperations,operationPath);
	html+='</form>';
	console.log(html);
	$( "#commonsPopup" ).html(html);
	
	
	// 오퍼레이션과 모델을 검색 한다.
	var operation = findOperation(apiMemberOperations, operationPath);
	var model = findModel(operation, apiMemberModels);
	var htmlWrap='	<table class="table-list valid">';
	var htmlHead='		<thead>';
	var htmlBody='		<tbody>';
	// 화면 리스트 
	$.get("./member/list.json", null, function(data){
		if(data.status == 200){
			$.each(data.result , function(){
				$.each(this, function(k,v){
					// 첫번째 row 일 때 head 를 생성한다.
					if(k==0){
						htmlHead+='<tr>';
						$.each(v,function(name,value){
							htmlHead+='<th>'+model[name]+'</th>';	
						});
						htmlHead+='<th>관리</th>';
						htmlHead+='</tr>';
					} 
					htmlBody+='<tr>';
					$.each(v,function(name,value){
						htmlBody+='<td>'+value+'</td>';	
					});
					htmlBody+='<td>';
					htmlBody+=' <a href="javascript:memberModifyForm(\''+v.id+'\')"><input type="button" id="modify" value="수정"></a>';
					htmlBody+=' <a href="javascript:memberRemove(\''+v.id+'\')"><input type="button" id="remove" value="삭제"></a>';
					htmlBody+='</td>';
					htmlBody+='</tr>';
				});
			});
			htmlWrap +=htmlHead+htmlBody+'</table>';
		} else {
			htmlWrap+='<tr><td>'+ data.desc +'</td></tr></tbody></table>'
		}
		$( "#commonsPopup" ).html($( "#commonsPopup" ).html()+htmlWrap);
	});
};

/**
 * 회원정보 수정 폼
 */
var memberModifyForm = function(id){
	
};

/**
 * 로그 아웃 프로세스 처리
 */
var logout = function(){
	var operationPath="/member/doLogout";
	
	$.post("."+operationPath+".json", function(data){
		if(data.status == 200){
			window.setTimeout(function(){
				document.location = document.location.href;	
			}, 1000)
		} else {
			alert(data);
		}
	});
};

$(document).ready(function(){
    /**
     * 로그인 정보 획득 로직
     */
	$.ajax({
	    url: './member/getLogin.json',
	    method: 'GET',
	    success:  function(data){
	    	var headhtml = "";
	    	var menuHtml = "";
	    	if(null!=data.result.message 
	    			&& ""!=data.result.message){

	    		headhtml+='<span id="memberId"><a href="javascript:memberModifyForm(\''+data.result.message+'\');">'+data.result.message+'(수정)</a></span>';
	    		headhtml+=' <span id="logout"><a href="javascript:logout();">[로그아웃]</a></span>';

	    		menuHtml+='<li><a href="javascript:databaseManager();">Database 관리</a></li>';
    			menuHtml+='<li><a href="javascript:memberListForm();">회원 관리</a></li>';

	    	} else {
	    		headhtml+='<span id="resistMember"><a href="javascript:resisteMember();">[회원가입]</a></span>';
	    		headhtml+=' <span id="login"><a href="javascript:loginForm();">[로그인]</a></span>';
	    	}
    		$("#loginInfo").html(headhtml)
    		$("#jd_menu").html(menuHtml)
	    }
	});
	
	/**
	 * 회원 관련 기능 API 목록
	 */
	$.get("./api-docs/api/member", null, function(data){
		apiMemberOperations = data.apis;
		apiMemberModels = data.models;
	});
	
	/**
	 * 로그인 관련 기능 API 목록
	 */
	$.get("./api-docs/api/login", null, function(data){
		apiLoginOperations = data.apis;
		apiLoginModels = data.models;
	});
});