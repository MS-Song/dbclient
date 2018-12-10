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
				{ id:"logo", view: "label", label: "Traffic Guard"},
				{ },
				{ }
			]},
			{	// 메인 화면 구성
				id:"main_body",
				multi:true,
				view:"accordion",
				cols:[
					{ 
						id:"traffic_guard_category",	
						header:"Select Category", 
						width:250, 
						body:{
							view:"menu",
							id:"traffic_guard_category_menu",
							layout:"y",
							scroll:true,
							tooltip:true,
							select:true,
							data: [],
							on:{
								onMenuItemClick:function(id){
									// 사이드 메뉴 액션
									try {
										// 기능이 정의 되어 있는 경우에만 실행
										if(null!=this.getMenuItem(id).func){
											eval(this.getMenuItem(id).func+"(this.getMenuItem(id))");
										}
									} catch (e) {
										console.log("기능 호출에 실패 했습니다: "+e);
									}
								}
							}
						}
					},
					{ view:"resizer", id:"screen_heighter"},
					{
						id:"traffic_guard_status",	
						margin:5,
						body:{ 
							rows:[{
								cols:[{
							    	id:"category_selected",
							    	view: "label",
									label: "<< 카테고리를 선택 하세요",
									
								},{
									id:"incident_alarm_job_add_button",
									view:"button",
									value:"신규 정책 등록 ",
									width:150,
									click:function(){
										add_category_popup();
									}
								}
								,{}]
							},{
								view : "datatable", 
								id:"traffic_guard_list_view", 						
								columns:[],	
								data:[],
								tooltip:true,
								select:"row",
								resizeColumn:true,
								scroll:true,
								multiselect:true,
								clipboard:"selection",
								on:{"onItemClick":function(){
									modify_category_popup(this.getSelectedItem());
								}}
							}] // end rows
						} // end body	
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