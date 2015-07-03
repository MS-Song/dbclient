
/**
 * 데이터베이스 매니저 호출 이벤트
 */
var databaseManager = function(){
	/**
	 * dialog 설정
	 */
	$("#commonsPopup").dialog({
		title: '데이터 베이스 관리',
		buttons: [
			{
				text: "저장",
				click: function() {
					if(confirm("변경 내용을 저장 하시겠습니까?")){
						$.post("./database/saveDatabases.json", $("#databaseManageTable").serializeArray(), function(data){
							alert(data.result.message);
						});
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
		],
		autoOpen: false,
		width: 1000,
		height: 600,
		modal: true,
		open: function(event, ui) {
			$( this ).dialog("option", "height", $( window ).height());
		}		
	});
	
	$( "#commonsPopup" ).dialog( "open" );
	databaseManageForm();
	
	// 창에 포커스 이동
	$( "#commonsPopup" ).find("input:eq(0)").focus();
}

/**
 * 데이터베이스 매니저 폼
 */
var databaseManageForm=function(){
	$.get("./database/serverList.json", null, function(data){
		var html='<form name="databaseManageTable" id="databaseManageTable">';
			html+='	<table class="table-list">';
			html+='		<thead>';
			html+='			<tr>';
			html+='				<th>host<input type="hidden" name="mode" value="save"></th>';
			html+='				<th>host alias</th>';
			html+='				<th>schemaName</th>';
			html+='				<th>account</th>';
			html+='				<th>password</th>';
			html+='				<th>driver</th>';
			html+='				<th>charset</th>';
			html+='				<th>port</th>';
			html+='				<th><input type="button" id="add" value="추가"></th>';
			html+='			</tr>';
			html+='		</thead>';
			html+='	<tbody id="rowBody">';
		
		var subHtml = '';
		$.each(data.result.serverInfo,function(){
			html+=databaseManageGetSubHTML(this);
		});
		
		subHtml;
		html+='<tbody></table></form>';
		$( "#commonsPopup" ).html(html);

		// add button click event
		$( "#add" ).click(function( event ) {
			$('#rowBody').append(databaseManageGetSubHTML(null));
		});
		
		// delete button click event
		$(document).on("click", "#rowBody input[name=delete]", function(event){
			var targetRow = $(this).parents('.subHtml');
			var inputs = targetRow.find('input[type!=button]');

			var hasValue = false;
			for(var i=0; i<inputs.length; i++) {
				if ($(inputs[i]).val() != '') {
					hasValue = true;
					break;
				}
			}
			if (hasValue && !confirm('작성된 내용이 있습니다. 삭제하시겠습니까?')) {
				return;
			}
			
			// seq 찾기
			var serverInfoSeq = targetRow.find("input[name='serverInfoSeq[]']").val();
			console.log("serverInfoSeq =" + serverInfoSeq);
			if(serverInfoSeq!=""){
				$.ajax({
				    url: './database/deleteDatabases.json'+ '?' + $.param({'serverInfoSeq':serverInfoSeq}),
				    method: 'DELETE',
				    success:  function(data){
						alert(data.result.message);
						targetRow.remove();
				    }
				});
			} else {
				targetRow.remove();
			}
		});
	});
};

/**
 * 데이터베이스 매니저 데이터 생성
 */
var databaseManageGetSubHTML = function(obj){
	// 객체 검증
	console.log(obj);
	
	var serverInfoSeq	= obj != null && obj.serverInfoSeq 	!= 'undefined' ? obj.serverInfoSeq 	: '';
	var host			= obj != null && obj.host 			!= 'undefined' ? obj.host 			: '';
	var hostAlias		= obj != null && obj.hostAlias 		!= 'undefined' ? obj.hostAlias 	: '';
	var schemaName 		= obj != null && obj.schemaName 	!= 'undefined' ? obj.schemaName 	: '';
	var account 		= obj != null && obj.account 		!= 'undefined' ? obj.account 		: '';
	var password 		= obj != null && obj.password 		!= 'undefined' ? obj.password 		: '';
	var driver	 		= obj != null && obj.driver			!= 'undefined' ? obj.driver 		: null;
	var charset	 		= obj != null && obj.charset 		!= 'undefined' ? obj.charset		: '';
	var port 			= obj != null && obj.port 			!= 'undefined' ? obj.port 			: '';
	
	
	var subHtml="";
	var dbdriver = createSelect(databaseDrivers,databaseDrivers,'driver[]',driver,null,null,null,null);
	subHtml+='<tr class="subHtml">';
	subHtml+='<td>';
	subHtml+='	<input type="hidden"   name="serverInfoSeq[]" 	value="'+serverInfoSeq+'" />';
	subHtml+='	<input type="text"     name="host[]" 			value="'+host+'"		size="15" />';
	subHtml+='</td>';
	subHtml+='<td><input type="text"     name="hostAlias[]" 	value="'+hostAlias+'"	size="15" /></td>';
	subHtml+='<td><input type="text"     name="schemaName[]" 	value="'+schemaName+'"	size="10" /></td>';
	subHtml+='<td><input type="text"     name="account[]" 		value="'+account+'"		size="10" /></td>';
	subHtml+='<td><input type="password" name="password[]" 		value="'+password+'"	size="10" /></td>';
	subHtml+='<td>'+dbdriver+'</td>';
	subHtml+='<td><input type="text"     name="charset[]" 		value="'+charset+'" 	size="10" /></td>';
	subHtml+='<td><input type="text"     name="port[]" 			value="'+port+'" 		size="4" /></td>';
	subHtml+='<td><input type="button"   name="delete" 			value="삭제" /></td>';
	subHtml+='</tr>';
	return subHtml; 
};