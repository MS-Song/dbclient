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
	<script src="./static/js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
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
		// 회원정보
   		var id=null;
   		var authType=null;
   		var email=null;
   		var passwordQuestion=null;

   		// 상단 화면 구성
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
						{id: 3, value: "Database 선택", icon: "database", func: "select_database_popup"},
						{id: 4, value: "Settings", icon: "cog", func: "config_popup"}
					],
					select:true,
					type:{ height: 40 },
					click:function(id,e){
						// 사이드 메뉴 액션
						console.log(this.getItem(id).func);
						eval(this.getItem(id).func+"()");
					}
				}
			});
		}); 
		
		// 로그인 된 경우 처리 
		webix.ready(function(){
			// 로그인 정보 획득
			webix.ajax().get("/member/getLogin.json", function(text,data){
				// 로그인 정보를 획득한 경우
				console.log(data.json().result);
				if(data.json().status ==200 && null!=data.json().result){	
		    		console.log(data.json().result);
		    		$.each(data.json().result,function(){
		    			$.each(this,function(){
		    				id=this.id;
		    				authType=this.authType;
		    				email=this.email;
		    		   		passwordQuestion=this.passwordQuestion;

		    			});
		    		});
		    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
		    		$$("menu").getBody().data.remove(1);
		    		$$("menu").getBody().data.add({id: 1, value: id+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
		    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);
		    		console.log($$("menu").getBody().data.getItem(1));
		    		
		    		// 권한 할당이 안된 경우 표시
		    		if(authType==null){
		    			$$("toolbar").addView({view: "label", label: "권한이 없습니다. 관리자에게 연락하시기 바랍니다."},3);	
		    		}
				}
			});			
		});		
		
		// 로그인 폼
		var login_form = {
			id:"login_form",
			view:"form",
			borderless:true,
			elements: [
				{ view:"text", label:'ID', name:"id" },
				{ view:"text", label:'Password', name:"password" ,type:"password", on:{
					"onKeyPress":function(id,e){
						if(e.keyCode == 13){
							// 버튼을  trigger 할 방법을 찾아보자.. 버튼의 이벤트가 나중에 걸려서 문제다.
						}
					}
				}},
				{margin:5, cols:[
					{ id:"login_button",view:"button", value:"Login" , type:"form", click:function(){// 로그인 실행
						//console.log(this.getFormView().getValues());
						webix.ajax().post("/member/doLogin.json", this.getFormView().getValues(), function(text,data){
							// 로그인 실패 
							if(data.json().status !=200){
								// validate 메세지 
								var message = data.json().desc.split("\n");
								webix.message(message[0].replace("="," "));
							} else { // 로그인 성공
								// 로그인 성공 액션
								webix.message("로그인 처리 완료");
								// 1초 후에 리로드 한다.	
								window.setTimeout(function(){
									document.location = document.location.href;	
								}, 1000)										
							}
						});
					}},
					{ view:"button", value:"Cancel", click:function(){ // 로그인 취소
						// 로그인 팝업 닫기
						$$("login_popup").hide();
						$$("login_popup").blockEvent();
					}},
					{ view:"button", value:"회원가입", click:function(){// 회원 가입
						// 로그인 팝업 닫기 
						$$("login_popup").hide();
						$$("login_popup").blockEvent();
						// 회원 가입 팝업
						resister_member_popup();
					}}
				]},
			],
			elementsConfig:{
				labelPosition:"top"
			}
		};
		
		// 로그인 팝업
		var login_popup = function(){
	        webix.ui({
	            view:"window",
	            id:"login_popup",
	            width:300,
	            position:"center",
	            modal:true,
	            head:"Log In",
	            body:webix.copy(login_form)
	        }).show();
	        $$("login_popup").unblockEvent();
		}

		// 로그 아웃 처리
		var log_out=function(){
			webix.confirm({
				title: "로그 아웃 확인",
				ok:"Yes", cancel:"No",
				text:"로그아웃 하시겠습니까?",
				callback:function(result){
					if(result==true){
			 			// 로그 아웃 실행
						webix.ajax().post("/member/doLogout.json", function(text,data){
							// 로그 아웃 결과 확인
							console.log(data.json().result);
							if(data.json().status ==200){	
								window.setTimeout(function(){
									document.location = document.location.href;	
								}, 1000)
							} else {
								webix.message(data.json().desc);
							}
						}); 					
					}
				}
			});
		};
		
		// 회원가입 폼
		var resister_member_form = {
			id:"resister_member_form",
			view:"form",
			borderless:true,
			elements: [
				{ view:"text", label:'ID', name:"id" ,value:""},
				{ view:"text", label:'email', name:"email" },
				{ view:"text", label:'패스워드', name:"password" ,type:"password"},
				{ view:"text", label:'비밀번호 찾기 질문', name:"passwordQuestion" },
				{ view:"text", label:'비밀번호 찾기 답변', name:"passwordAnswer" },
				{margin:5, cols:[
					{ view:"button", value:"가입" , type:"form", click:function(){
						webix.ajax().post("/member/add.json", this.getFormView().getValues(), function(text,data){
							// 가입 실패
							if(data.json().status !=200){
								// validate 메세지 
								var message = data.json().desc.split("\n");
								webix.message(message[0].replace("="," "));
							} else { // 가입 성공
								webix.message("가입이 완료되었습니다.");
								// 1초 후에 리로드 한다.	
								window.setTimeout(function(){
									document.location = document.location.href;	
								}, 1000)										
							}
						});
					}},
					{ view:"button", value:"취소", click:function(){ // 가입취소
						$$("resister_member_popup").hide();
					}},
				]},
			],
			elementsConfig:{
				labelPosition:"top"
			}
		};
		
		// 회원가입 팝업
		var resister_member_popup = function(){
	        webix.ui({
	            view:"window",
	            id:"resister_member_popup",
	            width:500,
	            position:"center",
	            modal:true,
	            head:"회원 가입",
	            body:webix.copy(resister_member_form)
	        }).show();
		}
		
		// 회원 정보 수정
		var modify_member_form = {
			id:"modify_member_form",
			view:"form",
			borderless:true,
			elements: [
				{ view:"text", name:"id" ,type:"hidden",height:0},
				{ view:"text", label:'email', name:"email" },
				{ view:"text", label:'패스워드', name:"password" ,type:"password"},
				{ view:"text", label:'패스워드 재입력', name:"password" ,type:"password"},
				{ view:"text", label:'비밀번호 찾기 질문', name:"passwordQuestion" },
				{ view:"text", label:'비밀번호 찾기 답변', name:"passwordAnswer" },
				{margin:5, cols:[
					{ view:"button", value:"수정" , type:"form", click:function(){
						webix.ajax().post("/member/modify.json", this.getFormView().getValues(), function(text,data){
							// 가입 실패
							if(data.json().status !=200){
								// validate 메세지 
								var message = data.json().desc.split("\n");
								webix.message(message[0].replace("="," "));
							} else { // 가입 성공
								webix.message("수정이 완료되었습니다.");
								$$("modify_member_popup").hide();
							}
						});
					}},
					{ view:"button", value:"취소", click:function(){ // 가입취소
						$$("modify_member_popup").hide();
					}},
				]},
			],
			elementsConfig:{
				labelPosition:"top"
			}
		};
		
		// 회원정보 수정
		var modify_member_popup = function(){
	        webix.ui({
	            view:"window",
	            id:"modify_member_popup",
	            width:500,
	            position:"center",
	            modal:true,
	            head:"회원정보 수정",
	            body:webix.copy(modify_member_form)
	        }).show();
	        
	        //$$("modify_member_form").getFormView().getValues();
	        $$("modify_member_form").setValues({
				id:id,
				authType:authType,
				email:email,
		   		passwordQuestion:passwordQuestion
	        });
		}
		
		
		// 회원 관리 처리
		// 회원 권한 처리

		// Database 관리
		
		
		// 서버 & DB 선택
		var select_database_popup=function(){
	        webix.ui({
	            view:"window",
	            id:"select_database_popup",
	            autowidth:true,
	            position:"center",
	            modal:true,
	            head:"database 선택",
	            body:{
	            	id:"select_database_loader",
	            	view:"datatable",
	            	columns:[
	     					{ id:"driver",		header:"Driver",	width:100},
							{ id:"host",		header:"Host",		width:120},
	     					{ id:"hostAlias",	header:"HostAlias",	width:180},
	     					{ id:"schemaName",	header:"SchemaName",width:120},
	     					{ id:"account",		header:"Account",	width:120},
	     					{ id:"port",		header:"Port",		width:80},
	     					{ id:"selected",	header:"선택",		width:50}
     				],
    				autowidth:true,
    				autoheight:true,
    				data:[]
	            }
	        }).show();
	        // 데이터베이스 정보를 조회한다.
      		webix.ajax().get("/database/serverList.json", function(text,data){
				// 데이터베이스 정보를 획득한 경우에 테이블에 넣는다.
				console.log(data.json().result);
				if(data.json().status ==200 && null!=data.json().result){	
		    		$.each(data.json().result,function(){
		    			$.each(this,function(index){
		    				console.log(this);
		    				$$("select_database_loader").data.add({
		    					id:this.serverInfoSeq,
		    					driver:this.driver, 
		    					host:this.host, 
		    					hostAlias:this.hostAlias, 
		    					schemaName:this.schemaName,
		    					account:this.account,
		    					port:this.port,
		    					selected:"선택"}
		    				,index);
		    			});
		    		});
				}
      		});
		}

		// 테이블 리스트
		// function 리스트
		// view 리스트
		// procedure
		// 쿼리 도우미
		// CURD
		// Mybatis
		// hibernate
		// model 생성
		// 히스토리
		// create 테이블
		// 인덱스
		
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