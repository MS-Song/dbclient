var databaseManageForm=function(){
	$.get("./database/serverList.json", null, function(data){
		var html='<form name="databaseManageTable" id="databaseManageTable">';
			html+='	<table class="table-list">';
			html+='		<thead>';
			html+='			<tr>';
			html+='				<th>host<input type="hidden" name="mode" value="save"></th>';
			html+='				<th>schemaName</th>';
			html+='				<th>account</th>';
			html+='				<th>password</th>';
			html+='				<th>driver</th>';
			html+='				<th>charset</th>';
			html+='				<th>port</th>';
			html+='				<th><input type="button" id="add" value="추가"></th>';
			html+='			</tr>';
			html+='		</thead>';
			html+='	<tbody id="rowBody">';;
		
		var subHtml = '';
		$.each(data.result.serverInfo,function(){
			html+=databaseManageGetSubHTML(this);
		});
		
		subHtml;
		html+='<tbody></table></form>';
		$( "#databasePopup" ).html(html);

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
			console.log("serverInfo Seq =" + serverInfoSeq);
			if(serverInfoSeq!=""){
				$.post("./database/deleteDatabases.json", {"serverInfoSeq":serverInfoSeq}, function(data){
					alert(data.result.message);
					targetRow.remove();
				});
			} else {
				targetRow.remove();
			}
		});
	});
};

var databaseManageGetSubHTML = function(obj){
	// 객체 검증
	
	var serverInfoSeq	= obj != null && obj.serverInfoSeq 	!= 'undefined' ? obj.serverInfoSeq 	: '';
	var host			= obj != null && obj.host 			!= 'undefined' ? obj.host 			: '';
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
	subHtml+='	<input type="text"     name="host[]" 			value="'+host+'"		size="20" />';
	subHtml+='</td>';
	subHtml+='<td><input type="text"     name="schemaName[]" 	value="'+schemaName+'"	size="16" /></td>';
	subHtml+='<td><input type="text"     name="account[]" 		value="'+account+'"		size="14" /></td>';
	subHtml+='<td><input type="password" name="password[]" 		value="'+password+'"	size="14" /></td>';
	subHtml+='<td>'+dbdriver+'</td>';
	subHtml+='<td><input type="text"     name="charset[]" 		value="'+charset+'" 	size="14" /></td>';
	subHtml+='<td><input type="text"     name="port[]" 			value="'+port+'" 		size="4" /></td>';
	subHtml+='<td><input type="button"   name="delete" 			value="삭제" /></td>';
	subHtml+='</tr>';
	return subHtml; 
};

$(document).ready(function(){
	$("#databasePopup").dialog({
		autoOpen: false,
		width: 1000,
		height: 600,
		modal: true,
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
		open: function(event, ui) {
			$( this ).dialog("option", "height", $( window ).height());
		}
	});
	
	// Link to open the dialog
	$( "#databaseManager" ).click(function( event ) {
		$( "#databasePopup" ).dialog( "open" );
		event.preventDefault();
		databaseManageForm();
	});
});