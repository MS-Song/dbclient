// 카테고리 값 저장
let category = {};

// 카테고리 선택 화면
var traffic_guard_category_menu_createor = function(){
	webix.ajax().get('/category/list', {}, function(text,data){
		if(data.json().httpStatus == 200 
				&& null!=data.json().contents
				&& 0<data.json().contents.length
		){	
			// 전체 삭제
			$$("traffic_guard_category_menu").clearAll();
			// 카테고리 출력
			$.each(data.json().contents,function(index,obj){
				$$("traffic_guard_category_menu").add({
					category_id:obj.id,
					value:obj.name,
					func:"traffic_guard_list",
					icon:"file"
				});
			});
			// 리프래시
			$$("traffic_guard_category_menu").refresh();
		}
	});
};


var traffic_guard_list = function(obj){
	// 카테고리 정보 저장
	category = obj;
	// 선택된 카테고리 정보 표시
	$$("category_selected").define("label",'선택 카테고리 : ' + obj.value);
	$$("category_selected").refresh();
	
}

webix.ready(function(){
	traffic_guard_category_menu_createor();	// 카테고리 메뉴 생성
});