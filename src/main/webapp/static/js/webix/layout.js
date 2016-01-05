/**
 * 페이지 전체 화면구성 layout 
 */
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
				autoheigth:true,
				autowidth:true,
				cols:[
					{ 
						id:"database_info_view",	
						header:"Database 정보", 
						width:350, 
						body:{
							view:"tabview",
							id:"database_info_tab",
							animate:false,
		    				autowidth:true,
		    				autoheight:true,	
							cells: database_info_cell
						}
					},
					{ view:"resizer"},
					{rows:[
						{ 
							id:"database_query_view",	
							header:"쿼리 & 개발자도구", 	
							body:{
								view:"tabview",
								id:"database_query_tab",
								animate:false,
			    				autowidth:true,
			    				autoheight:true,
								cells: database_query_cell
							}	
						},
						{ view:"resizer",height:3},
						{ 
							view:"tabview",
							id:"database_result_tab",
							animate:false,
							cells: database_result_cell
						}
					]},
				]
			},
			{	// footer 구성
				id:"footer",
				height:30,
				template: "Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved.",
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
			template: "<span class='webix_icon fa-#icon#'></span> #value#",
			data:[
				{id: 1, value: "Login", icon: "user", func: "login_popup"},
				{id: 3, value: "Database 선택", icon: "database", func: "select_database_popup"},
				{id: 4, value: "Settings", icon: "cog", func: "config_popup"}
			],
			select:true,
			type:{ height: 30 },
			click:function(id,e){
				// 사이드 메뉴 액션
				console.log(this.getItem(id).func);
				eval(this.getItem(id).func+"()");
			}
		}
	});
}); 