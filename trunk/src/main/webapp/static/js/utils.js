var showLoading=function(){
	$("#loading").show();
	setTimeout(load_error, 10000);
};
var hideLoading=function(){
	$("#loading").hide();
};
var load_error=function(){
	hideLoading();
};

var inArray=function (value,array){
	var isInArray=false;
	$.each(array,function(){
		if(value==this.toString()){
			return isInArray=true;
		}
	});
	return isInArray;
};

var createSelect = function (values,textes,name,selectedValue,firsetOpetionValue,selectAppend,optionAppend){
	var sb= new Array();
	sb.push("<select name='"+name+"' " + selectAppend + " title='선택하세요'>"+"\n");

	if(null!=firsetOpetionValue)
		sb.push("<option value=''>"+firsetOpetionValue+"</option>"+"\n");
	
	$.each(values,function(loop){
		sb.push("<option value='"+this+"' ");
		if(values==selectedValue){
			sb.push(" selected ");
		}
		sb.push(optionAppend+">");
		sb.push(textes[loop]+"</option>"+"\n");
	});
	
	sb.push("</select>"+"\n");
	return sb.join("");
};