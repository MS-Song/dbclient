/**
 * 페이지 전체 화면구성 layout 
 */

// 에디터의 tab 을 활성화 하기 위해 webix 의 tab 을 제거 한다.
webix.UIManager.tabControl = false;
webix.ready(function(){
	webix.ui({
		rows:[{	// 상단 타이틀 구성 및 sidemenu 제어
				view: "toolbar", id:"toolbar", elements:[
				{ view: "icon", icon: "bars", click: function(){
					if( $$("menu").config.hidden) 
						$$("menu").show();
					else 
						$$("menu").hide();
				}},
				{ view: "label", label: "DB CLient"}
			]},
			{	// 메인 화면 구성
				id:"main_body",
				multi:true,
				view:"accordion",
				cols:[
					{ 
						id:"database_info_view",	
						header:"Database", 
						width:450, 
						body:{
							view:"tabview",
							id:"database_info_tab",
							animate:false,
							cells: database_info_cell
						}
					},
					{ view:"resizer", id:"screen_heighter"},
					{
						id:"database_query_view",	
						margin:0,
						body:{
							view:"tabview",
							id:"database_query_tab",
							animate:false,
							cells: database_query_cell
						}	
					}
				]
			},
			{
				// footer 구성
				cols:[
				    {
				    	id:"footer",
				    	view: "label",
						label: "Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved.",
						height:25,
						adjust:true
				    },     
					{
						id:"editerSelectedStartLine",
						view: "label",
						align:"right",
						label:"",
						minWidth:50,
						maxWidth:120,
						width:90
					},
					{
						view: "label",
						label:"|",
						width:10
					},				
					{
						id:"editorCurrentLine",					
						view: "label",
						align:"right",
						label:"1:0",
						minWidth:30,
						maxWidth:100,
						width:70
					},
					{
						view: "label",
						align:"right",
						label:"",
						width:10
					}
				]				
			}
		]
	});
	
	// 사이드 메뉴
	webix.ui({
		view: "sidemenu",
		id: "menu",
		width: 200,
		position: "left",
		state:function(state){
			var toolbarHeight = $$("toolbar").$height;
			state.top = toolbarHeight;
			state.height -= toolbarHeight;
		},
		css: "my_menu",
		body:{
			view:"list",
			borderless:true,
			scroll: false,
			template:function(obj){
				var html='<span';
				if(null!=obj.icon){
					html+=' class="webix_icon fa-' + obj.icon +'"';
				}
				html+='></span>';
				if(null!=obj.value){
					html+=' '+obj.value;
				}
				return html;
			},
			data:[
				{id: 1, value: "Login", 		icon: "user", 		func: "login_popup"},
				{id: 3, value: "Database 선택", 	icon: "database", 	func: "select_database_popup"},
				{id: 4, value: "Settings", 		icon: "cog", 		func: "config_popup"}
			],
			select:true,
			type:{ height: 30 },
			click:function(id,e){
				// 사이드 메뉴 액션
				try {
					// 기능이 정의 되어 있는 경우에만 실행
					if(null!=this.getItem(id).func){
						eval(this.getItem(id).func+"()");
					}
				} catch (e) {
					console.log("기능 호출에 실패 했습니다: "+e);
				}
			}
		}
	});
	
	// layout 화면 사이즈를 재 계산한다.
	setTimeout(function(){
		// 화면에서 toolbar 와 footer 의 사이즈를 제외하고 리사이즈 시킨다.
		var height=$(window).height()-$$("toolbar").$height-$$("footer").$height
		// 리사이즈 이벤트 1회 발생 시킨다.
		$$("main_body").define("height", height);
		$$("main_body").resize();
		$$("main_body").define("height", "auto");
	}, 300);

	// 뒤로가기 버튼으로 뒤로가기 금지 
	$(document).keydown(function(e){   
        if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){       
        	if(e.keyCode === 8){   
        		return false;
        	}
        }
	});

	history.pushState(null, null, location.href);
	window.onpopstate = function(event) {
		history.go(1);
	};
}); 