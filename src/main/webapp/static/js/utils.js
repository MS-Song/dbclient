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
 * 세로형 폼 만들기
 */
var createVerticalForm = function(operations,operationPath){
	var html='<table class="table-list valid"><thead>';
	var operation = findOperation(operations,operationPath);
	$(operation).each(function(){	
		html+='			<tr>';
		$(this.parameters).each(function(){
			html+='				<th>'+this.description+'</th>';				
		});
		html+='			</tr>';		
	});
	html+='</thead>';
	html='<tbody>';
	$(operation).each(function(){	
		html+='			<tr>';
		$(this.parameters).each(function(){
			if(this.enum == undefined){
				html+='				<td><input type="text" name="'+this.name+'" value="" /></td>';			
			} else {
				html+='				<td>'+createSelect(this.enum,this.enum,this.name,this.value,null,null,null,null);+'</td>';				
			}
		});
		html+='			</tr>';
	});
	html='</tbody></table>';
	return html;
};



/**
 * 가로형 폼 만들기
 */
var createHorizontalForm = function(operations,operationPath){
	var htmlWrap='	<table class="table-list valid">';
	var htmlBody='		<tbody>';
	// 필드 생성
	var operation = findOperation(operations,operationPath);

	$(operation).each(function(){	
		$(this.parameters).each(function(){
			htmlBody+='			<tr>';
			htmlBody+='				<th>'+this.description+'</th>';
			if(this.enum == undefined){
				var inputType = this.name!='password' ? 'text': 'password';
				htmlBody+='	<td><input type="'+inputType+'" name="'+this.name+'" value="" />';					
			} else {
				htmlBody+='	<td>'+createSelect(this.enum,this.enum,this.name,null,null,null,null,null);				
			}
			htmlBody+='<label id="label_'+this.name+'"/></td>';
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

/**
 * response 모델을 확인해서 해당하는 모델에 대한 정보를 리턴한다.
 */
var findModel = function(operation,models){
	var model = null;
	$.each(operation,function(index,attribute){
		if(null!=models[attribute.type]){
			model = new Object();
			$.each(models[attribute.type].properties,function(k,v){
				model[k]=v.description;
			});
		}
	});
	return model;
}

/**
 * 에러 메세지 제거
 */
var removeErrorMessage = function(){
	$("label[id^='label_']").removeClass("error").html("");
};

/**
 * 에러 메세지 추가
 */
var addErrorMessage = function(data){
	console.log(data);
	var messageList = data.desc.split("\n");
	for(var i=0;i<messageList.length;i++){
		var message = messageList[i].split("=");
		if(message.length > 1){
			$("#label_"+message[0]).addClass("error").html(message[1]);
		}
	}
};

$(document).ready(function(){

});