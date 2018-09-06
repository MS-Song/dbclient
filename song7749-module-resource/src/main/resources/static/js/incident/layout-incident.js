/**
 * 페이지 전체 화면구성 layout 
 */

// 에디터의 tab 을 활성화 하기 위해 webix 의 tab 을 제거 한다.
webix.UIManager.tabControl = false;
webix.ready(function(){
	webix.ui({
		rows:[{	// 상단 타이틀 구성 및 sidemenu 제어
				view: "toolbar", id:"toolbar", elements:[
				{ id:"menu_left_icon", view: "icon", icon: "bars", click: function(){
						if( $$("menu").config.hidden) 
							$$("menu").show();
						else 
							$$("menu").hide();
					}
				},
				{ id:"logo", view: "label", label: "Incident Alert"},
				{ id:"toolbar_cache_remove"},
				{ id:"menu_right_icon", view: "icon", icon: "bars", click: function(){
						if( $$("menu_right").config.hidden) 
							$$("menu_right").show();
						else 
							$$("menu_right").hide();
					}
				}
			]},
			{	// 메인 화면 구성
				id:"main_body",
				multi:true,
				view:"accordion",
				cols:[
					{ 
						id:"incident_alarm_search",	
						header:"Search Task Scheduler", 
						width:450, 
						body:{
							view:"form",
							id:"incident_alarm_search_form",
							animate:false,
							elements: [],
							scroll:true,
							tooltip:true
						}
					},
					{ view:"resizer", id:"screen_heighter"},
					{
						id:"incident_alarm_job",	
						margin:5,
						body:{ 
							rows:[{
								cols:[{},{
									id:"incident_alarm_list_page",
									view: 'pager',
									template: '{common.first()} {common.prev()} {common.pages()} {common.next()} {common.last()}',
									master:false,
									size: 20,
									group: 5,
									count: 1000,
									align:'center',
									on: { onItemClick: function(id, e, node) {
										    $$('incident_alarm_search_page').setValue(id*1 + 1);
										    incident_alarm_list_create();
									}}
								},{
									id:"incident_alarm_job_add_button",
									view:"button",
									value:"알람 신규 등록",
									width:150,
									click:function(){
										incident_alarm_popup();
									}
								},{
									
								}]
							},{
								view : "datatable", 
								id:"incident_alarm_list_view", 						
								columns:[],	
								data:[],
								tooltip:true,
								select:"row",
								resizeColumn:true,
								scroll:true,
								multiselect:true,
								clipboard:"selection",
								on:{"onItemClick":function(){
									incident_alarm_popup(this.getSelectedItem());
								}}
							}] // end rows
						} // end body	
					},
					{ view:"resizer", id:"screen_heighter"},
					{ 
						id:"incident_alarm_run_tab",	
						header:"Alarm Task Run Log", 
						collapsed:false,
						width:400, 
						body:{
							view:"datatable",
							id:"incident_alarm_run_log", 	
							columns:[
				        		{id:"status", 				header:"실행",	adjust:true},
								{id:"id", 					header:"ID",	adjust:true},
				        		{id:"subject", 				header:"제목",	adjust:true},
				        		{id:"processTime", 			header:"실행시간",	adjust:true},
				        		{id:"time", 				header:"시간",	adjust:true},
							],	
							data:[],
							tooltip:true,
							select:"row",
							resizeColumn:true,
							scroll:true,
							clipboard:"selection",
							on:{"onItemClick":function(){
								incident_alarm_popup(this.getSelectedItem());
							}}
						}
					}
				]// end cols
			},
			{
				// footer 구성
		    	id:"footer",
		    	view: "label",
				label: "Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved.",
				height:25,
				adjust:true
			}
		]
	});
	
	// 사이드 메뉴 LEFT
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

	// 사이드 메뉴 RIGHT
	webix.ui({
		view: "sidemenu",
		id: "menu_right",
		width: 200,
		position: "right",
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
				{id: 1, value: "DBClient", 				icon: "bookmark", 		func: "mvSite('index.html')"},
				{id: 3, value: "IncidentAlert", 		icon: "bookmark", 		func: "mvSite('/static/incident.html')"},
				{id: 4, value: "SR Data Center", 		icon: "bookmark", 		func: "mvSite('/static/service_reqeust.html')"},
				{id: 5, value: "API Development", 		icon: "bookmark", 		func: "mvSite('/static/api_development.html')"}
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