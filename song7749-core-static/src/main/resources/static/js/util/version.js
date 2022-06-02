let getVersion = function (name){
	webix.ajax().get("/version/"+name, function(text,data){
		if(data.json().httpStatus ==200){
            // 버전 정보 추가
            $$("toolbar").removeView("version");
            $$("toolbar").addView({
                id:"version",
                view: "label", 
                label: data.json().contents,
                align: "left"
            },2);
            console.log(data.json().contents)
		} else { 
			webix.message({ type:"error", text:data.json().message});
		}
	});
}