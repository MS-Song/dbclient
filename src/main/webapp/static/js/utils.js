/**
 * 프로그래스바 오픈
 */
var showLoading=function(){
	$("#loading").show();
	setTimeout(load_error, 10000);
};

/**
 * 프로그래스바 클로즈
 */
var hideLoading=function(){
	$("#loading").hide();
};
var load_error=function(){
	hideLoading();
};

/**
 * 배열안의 원소 검색
 */
var inArray=function (value,array){
	var isInArray=false;
	$.each(array,function(){
		if(value==this.toString()){
			return isInArray=true;
		}
	});
	return isInArray;
};

/**
 * 셀렉트 박스 생성
 */
var createSelect = function (values,textes,name,selectedValue,firsetOpetionValue,selectAppend,optionAppend){
	var sb= new Array();
	sb.push("<select name='"+name+"' " + selectAppend + " title='선택하세요'>"+"\n");

	if(null!=firsetOpetionValue)
		sb.push("<option value=''>"+firsetOpetionValue+"</option>"+"\n");
	
	$.each(values,function(loop){
		sb.push("<option value='"+this+"'");
		if(values[loop]==selectedValue){
			sb.push(" selected ");
		}
		sb.push(optionAppend+">");
		sb.push(textes[loop]+"</option>"+"\n");
	});
	
	sb.push("</select>"+"\n");
	return sb.join("");
};


/**
 * 세로형 폼 Head 만들기
 */
var createVerticalFormHead = function(apiMemberOperations,operationPath){
	var html='';
	var operation = findOperation(apiMemberOperations,operationPath);
	$(operation).each(function(){	
		$(this.parameters).each(function(){
			html+='			<tr>';
			html+='				<th>'+this.description+'</th>';				
			html+='			</tr>';
		});
	});
};

/**
 * 세로형 폼 Body 만들기
 */
var createVerticalFormBody = function(apiMemberOperations,operationPath){
	var html='';
	var operation = findOperation(apiMemberOperations,operationPath);
	$(operation).each(function(){	
		$(this.parameters).each(function(){
			html+='			<tr>';
			if(this.enum == undefined){
				html+='				<td><input type="text" name="'+this.name+'" value="" /></td>';			
			} else {
				html+='				<td>'+createSelect(this.enum,this.enum,this.name,null,null,null,null,null);+'</td>';				
			}
			html+='			</tr>';
		});
	});
};


/**
 * 가로형 폼 만들기
 */
var createHorizontalForm = function(apiMemberOperations,operationPath){
	var htmlWrap='	<table class="table-list">';
	var htmlBody='		<tbody>';
	// 필드 생성
	var operation = findOperation(apiMemberOperations,operationPath);
	$(operation).each(function(){	
		$(this.parameters).each(function(){
			htmlBody+='			<tr>';
			htmlBody+='				<th>'+this.description+'</th>';
			if(this.enum == undefined){
				htmlBody+='				<td><input type="text" name="'+this.name+'" value="" /></td>';			
			} else {
				htmlBody+='				<td>'+createSelect(this.enum,this.enum,this.name,null,null,null,null,null);+'</td>';				
			}
			htmlBody+='			</tr>';
		});
	});
	htmlBody+='		</tbody>';	
	htmlWrap+=htmlBody+'</table>';
	return htmlWrap;
};

/**
 * operationPath 와 동일한 operation 을 리턴한다.
 */
var findOperation = function(apiOperations,operationPath){
	var operations = null;
	$(apiOperations).each(function(){
		if(this.path==operationPath){
			operations=this.operations;
		}
	});
	return operations;
};

$(document).ready(function(){
	/**
	 * 공통 팝업
	 */
	$("#commonsPopup").dialog({
		autoOpen: false,
		width: 1000,
		height: 600,
		modal: true,
		open: function(event, ui) {
			$( this ).dialog("option", "height", $( window ).height());
		}
	});
});