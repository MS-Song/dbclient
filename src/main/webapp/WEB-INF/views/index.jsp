<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta  name = "viewport" content = "initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no">
	<link rel="stylesheet" href="./static/codebase/webix.css" type="text/css" media="screen" charset="utf-8">
	<script src="./static/codebase/webix.js" type="text/javascript" charset="utf-8"></script>
	<title>DB Client</title>
	<style>
		.my_menu .webix_view{
			background-color: #ECEFF1;
		}
		.my_menu .webix_list_item{
			line-height: 35px;
			border-bottom-color: #ddd;
		}
		h3{
			text-align:center;
			font-size: 15px
		}
	</style>
</head>
<body>

	<script type="text/javascript" charset="utf-8">
		// 상단 화면 구성
		webix.ready(function(){
			webix.ui({
				rows:[{	// 상단 타이틀 구성 및 sidemenu 제어
						view: "toolbar", id:"toolbar", elements:[
						{ view: "icon", icon: "bars", click: function(){
								if( $$("menu").config.hidden){
									$$("menu").show();
								}
								else{
									$$("menu").hide();
								}
						}},
						{ view: "label", label: "DB CLient"}
					]},
					{	// 메인 화면 구성
						id:"main_body",
						template: "Click 'menu' icon to show a menu"
					},
					{	// footer 구성
						template: "html->footer",
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
					template: "html->menu_template",
					data:[
						{id: 1, value: "Login", icon: "user", func: "login_popup"},
						{id: 2, value: "Database", icon: "database", func: "login_popup"},
						{id: 3, value: "Settings", icon: "cog", func: "login_popup"}
					],
					select:true,
					type:{ height: 40 },
					click:function(id,e){
						// 사이드 메뉴 액션
						console.log(this.getItem(id).func);
			            $$(this.getItem(id).func).show();
						//eval(this.getItem(id).func+"()");
					}
				}
			});
		}); 
		
		// 로그인 폼
		var login_form = {
				view:"form",
				borderless:true,
				elements: [
					{ view:"text", label:'ID', name:"id" },
					{ view:"text", label:'Password', name:"password" },
					{margin:5, cols:[
							{ view:"button", value:"Login" , type:"form", click:function(){
								
							}},
							{ view:"button", value:"Cancel", click:function(){
								$$("login_popup").hide();
							}},
							{ view:"button", value:"Resist", click:function(){
								$$("login_popup").hide();
								// 회원 가입 팝업
							}}
					]},
				],
				rules:{
					"email":webix.rules.isEmail,
					"login":webix.rules.isNotEmpty
				},
				elementsConfig:{
					labelPosition:"top"
				}
			};
		// 로그인 팝업
        webix.ui({
            view:"window",
            id:"login_popup",
            width:300,
            position:"center",
            modal:true,
            head:"Log In",
            body:webix.copy(login_form)
        });

		// 로그인 처리
		// 로그 아웃 처리
		// 회원가입 처리
		// 회원 관리 처리

		
	</script>

	<!-- 메뉴 리스트의 템플릿 -->
	<div id="menu_template" style="display: none;">
		<span class="webix_icon fa-#icon#"></span> #value#
	</div>
	<!-- footer의 템플릿 -->
	<div id="footer" style="display: none;">
		<h3> Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved. </h3>
	</div>
</body>
</html>