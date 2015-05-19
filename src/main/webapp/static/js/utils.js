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