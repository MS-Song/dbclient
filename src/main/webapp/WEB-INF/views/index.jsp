<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta  name = "viewport" content = "initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no">
	<link rel="stylesheet" href="./static/codebase/skins/web.css" type="text/css" media="screen" charset="utf-8">	
	<script src="./static/codebase/webix.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/indentHelper.js" type="text/javascript" charset="utf-8"></script>

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

   		// 데이터 베이스 선택 정보
   		var serverInfoSeq=null;
   		var server=null;
   		var schema=null;
   		var account=null;
   		var tableName=null;
   		
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
						multi:true,
						view:"accordion",
						cols:[
							{ 
								id:"database_info_view",	header:"Database 정보", width:280, body:{
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
								{ id:"database_query_view",	header:"쿼리 & 개발자도구", 	body:{
										view:"tabview",
										id:"database_query_tab",
										animate:false,
					    				autowidth:true,
					    				autoheight:true,
										cells: database_query_cell
									}	
								},
								{ view:"resizer",height:3},
								{ id:"database_result_vew",	header:"Result", 		body:{
										view:"tabview",
										id:"database_result_tab",
										animate:false,
					    				autowidth:true,
					    				autoheight:true,
										cells: database_result_cell
									}	
								}
							]},
						],
						autoheigth:true,
						autowidth:true
					},
					{	// footer 구성
						id:"footer",
						height:30,
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
					type:{ height: 30 },
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
				if(data.json().status ==200 && null!=data.json().result){	
		    		$.each(data.json().result,function(){
		    			$.each(this,function(){
		    				id=this.id;
		    				authType=this.authType;
		    				email=this.email;
		    		   		passwordQuestion=this.passwordQuestion;

		    			});
		    		});
		    		// 로그인 정보 획득에 성공한 경우에는 메뉴의 로그인 버튼을 변경한다.
		    		$$("menu").getBody().data.remove(1);	// TODO ID Search 으로 변경
		    		$$("menu").getBody().data.add({id: 1, value: id+" 님  (수정)", icon: "user", func: "modify_member_popup"},0);
		    		$$("menu").getBody().data.add({id: 2, value: " 로그아웃 ", icon: "user", func: "log_out"},1);
		    		
		    		// 권한 할당이 안된 경우 표시
		    		if(authType==null){
		    			// Database 관련 기능 비 활성화
		    			$$("menu").getBody().data.remove(3);
		    			$$("toolbar").addView({view: "label", label: "권한이 없습니다. 관리자에게 연락하시기 바랍니다."},3);	
		    		}
				} else { // 로그인 되어 있지 않으면 비 활성화
	    			$$("menu").getBody().data.remove(3);
				}
			});			
		});		
		
		// 로그인 폼
		var login_form = {
			id:"login_form",
			view:"form",
			borderless:true,
			elements: [
				{ id:"login_id_input", view:"text", label:'ID', name:"id" },
				{ id:"login_password_input", view:"text", label:'Password', name:"password" ,type:"password",
					on:{"onKeyPress":function(key,e){// 로그인 실행
							// enter 를 입력하면, 로그인을 실행 한다.
							if(key==13) $$("login_button").callEvent("onItemClick");
						}
					}
				},
				{margin:5, cols:[
					{ id:"login_button",view:"button", value:"Login" , type:"form", 
						on:{"onItemClick":function(){// 로그인 실행
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
						}
					}},
					{ view:"button", value:"Cancel", click:function(){ // 로그인 취소
						// 로그인 팝업 닫기
						$$("login_popup").hide();
					}},
					{ view:"button", value:"회원가입", click:function(){// 회원 가입
						// 로그인 팝업 닫기 
						$$("login_popup").hide();
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
	        $$("menu").hide();
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
	        // side menu 닫기
	        $$("menu").hide();
	        
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
	            head:{view:"button",value:"서버 선택 닫기" , click:function(){
	           		$$("select_database_popup").hide();
            	}},
	            body:{
	            	id:"select_database_loader",
	            	view:"datatable",
	            	columns:[
	     					{ id:"driver",		header:"Driver",	width:100, sort:"string"},
							{ id:"host",		header:"Host",		width:100, sort:"string"},
	     					{ id:"hostAlias",	header:"HostAlias",	width:100, sort:"string"},
	     					{ id:"schemaName",	header:"SchemaName",width:100, sort:"string"},
	     					{ id:"account",		header:"Account",	width:100, sort:"string"},
	     					{ id:"port",		header:"Port",		width:80,  sort:"int"},
	     					{ id:"selected",	header:"선택",		width:50}
     				],
     				tooltip:true,
     				select:"row",
     				resizeColumn:true,
    				autowidth:true,
    				autoheight:true,
    				data:[]
	            }
	        }).show();

	        // side menu 닫기
	        $$("menu").hide();
	        
	        // 데이터베이스 정보를 조회한다.
      		webix.ajax().get("/database/serverList.json", function(text,data){
				// 데이터베이스 정보를 획득한 경우에 테이블에 넣는다.
				if(data.json().status ==200 && null!=data.json().result){	
		    		$.each(data.json().result,function(){
		    			$.each(this,function(index){
							// 이미 선택되어진 값인가 검증 한다. 
							var selectedRow = "";
							if(this.serverInfoSeq == serverInfoSeq){
								selectedRow="선택";
							}
		    				$$("select_database_loader").data.add({
		    					id:this.serverInfoSeq,
		    					driver:this.driver, 
		    					host:this.host, 
		    					hostAlias:this.hostAlias, 
		    					schemaName:this.schemaName,
		    					account:this.account,
		    					port:this.port,
		    					selected:selectedRow}
		    				,index);
		    			});
		    		});
				}
				// 이벤트 부여
				$$("select_database_loader").attachEvent("onSelectChange", function(){
					var selectedRow = $$("select_database_loader").getSelectedItem();
					
					// 이미 선택된 값을 삭제 한다.
					$$("select_database_loader").eachRow( 
    					function (id){
    						$$("select_database_loader").getItem(id).selected="";
    					}
    				);
					
					// 설정 정보 저장
					serverInfoSeq=selectedRow.id;
					server=selectedRow.host;
					schema=selectedRow.schemaName;
					account=selectedRow.account;
					selectedRow.selected="선택";
					// 입력한 값을 재 로딩 한다.
					$$("select_database_loader").refresh();
					webix.message("데이터베이스를 선택 했습니다.");
					// 선택 창을 닫는다.
			   		window.setTimeout(function(){
						$$("select_database_popup").hide();	
					}, 500)										

					// 선택된 서버 정보를 보여준다.
					$$("toolbar").removeView("toolbar_notices");
					$$("toolbar").addView({id:"toolbar_notices",view: "label", label: selectedRow.hostAlias+" ["+server+"] 선택"},3);
					// 선택된 데이터베이스의 정보를 읽는다.
					database_info_data_load();
					
				});
      		});
		}

  		// database info cell
   		var database_info_cell = [
			{	 
				header:"Table",		
				rows:[{
					view : "datatable",
					id:"database_info_table_list_view", 	
					columns:[
						{ id:"seq",	header:["#", {	// 검색창 구현
								content:"textFilter", placeholder:"table name OR comment search",
								compare:function(value, filter, obj){ // 검색창 필터조건 구현
										if (equals(obj.tableName, filter)) return true;
										if (equals(obj.tableComment, filter)) return true;
										return false;
								}, colspan:3}], 
							adjust:true,	sort:"int"},					         
	   					{ id:"tableName",	header:"Name",		sort:"string", adjust:true},
						{ id:"tableComment",header:"Comment",	sort:"string", adjust:true},
	   				],
					data:[],
					tooltip:true,
	 				select:"row",
	 				resizeColumn:true,
	 				navigation:true,
	 				autowidth:true
				},
				{ view:"resizer"},
				{
					view:"tabview",
					id:"table_info_tab",
					animate:false,
    				autowidth:true,
    				autoheight:true,	
					cells: [
						{	
							header:"Field",
							view : "datatable",
							id:"table_info_field_list", 	
							columns:[
								{ id:"columnId",	header:["#", {	// 검색창 구현
										content:"textFilter", placeholder:"column name OR comment search",
										compare:function(value, filter, obj){ // 검색창 필터조건 구현
												if (equals(obj.columnName	, filter)) return true;
												if (equals(obj.comment, filter)) return true;
												return false;
										}, colspan:10}], 
									adjust:true,	sort:"int"},					         
			   					{ id:"columnName",		header:"name",		sort:"string", adjust:true},
								{ id:"nullable",		header:"null able",	sort:"string", adjust:true},
								{ id:"columnKey",		header:"key",		sort:"string", adjust:true},
								{ id:"dataType",		header:"type",		sort:"string", adjust:true},
								{ id:"dataLegnth",		header:"length",	sort:"string", adjust:true},
								{ id:"comment",			header:"comment",	sort:"string", adjust:true},
								{ id:"defaultValue",	header:"defualt",	sort:"string", adjust:true},
								{ id:"characterset",	header:"charset",	sort:"string", adjust:true},
								{ id:"extra",			header:"extra",		sort:"string", adjust:true}

							],
							data:[],
							tooltip:true,
			 				resizeColumn:true,
							autowidth:true,
							select:"row",
							navigation:true
						}, {
							header:"index",
							id:"table_info_index_list", 
							view : "datatable",
							columns:[
			   					{ id:"owner",			header:"owner",			sort:"string", adjust:true},
								{ id:"indexName",		header:"name",			sort:"string", adjust:true},
								{ id:"indexType",		header:"type",			sort:"string", adjust:true},
								{ id:"columnName",		header:"colName",		sort:"string", adjust:true},
								{ id:"columnPosition",	header:"position",		sort:"string", adjust:true},
								{ id:"cardinality",		header:"cardinality",	sort:"string", adjust:true},
								{ id:"unique",			header:"unique",		sort:"string", adjust:true},
								{ id:"descend",			header:"descend",		sort:"string", adjust:true}
			   				],
							data:[],
							tooltip:true,
			 				resizeColumn:true,
							autowidth:true,
							select:"row",
							navigation:true
						}
					]
				}]
			},
			{	view : "datatable", 
				header:"View",			
				id:"database_info_view_list_view", 						
				columns:[],	
				data:[],
				tooltip:true,
 				select:"row",
 				resizeColumn:true,
				autowidth:true,
				autoheight:true
			},
			{	view : "datatable", 
				header:"Procedure",	
				id:"database_info_procedure_list_view", 
				columns:[],	
				data:[],
				tooltip:true,
 				select:"row",
 				resizeColumn:true,
				autowidth:true,
				autoheight:true
			},
			{	view : "datatable", 
				header:"Function",		
				id:"database_info_function_list_view",				
				columns:[],	
				data:[],
				tooltip:true,
 				select:"row",
 				resizeColumn:true,
				autowidth:true,
				autoheight:true
			}
   		];
		
		// 데이터베이스 정보 로드 
		var database_info_data_load=function(){
			// progress 추가
			webix.extend($$("database_info_table_list_view"), webix.ProgressBar);
			// 로딩 프로그레스 
			$$("database_info_table_list_view").showProgress();
			// 담기 전에 모두 지운다
			$$("database_info_table_list_view").clearAll();
			// 테이블 정보 로딩
			webix.ajax().get("/database/tableList.json",{
				server:server,
				schema:schema,
				account:account}, function(text,data){
					// 테이블 정보를 획득한 경우에 넣는다.
					if(data.json().status ==200 && null!=data.json().result){	
						$.each(data.json().result,function(){
			    			$.each(this,function(index){
			    				$$("database_info_table_list_view").data.add({
			    					seq:index,
			    					tableName:this.tableName,
			    					tableComment:this.tableComment
			    				},index);
				   			});
		    			});
						// 다시 읽는다.
			    		$$("database_info_table_list_view").refresh();
			    		$$("database_info_table_list_view").hideProgress();
					}
				}
			);
			
			// change 이벤트 부여 -- 테이블 선택 값 이벤트
			$$("database_info_table_list_view").attachEvent("onSelectChange", function(){
				// 선택된 row
				var selectedRow = $$("database_info_table_list_view").getSelectedItem();
				// 테이블 명칭 저장
				if(undefined != selectedRow.tableName)
					tableName = selectedRow.tableName; 

				// progress 추가
				webix.extend($$("table_info_field_list"), webix.ProgressBar);
				// 로딩 프로그레스 
				$$("table_info_field_list").showProgress();
				// 담기 전에 모두 지운다
				$$("table_info_field_list").clearAll();
				// 필드 조회 
				webix.ajax().get("/database/fieldList.json",{
					server:server,
					schema:schema,
					account:account,
					table:tableName}, 
					function(text,data){
						// 필드 정보를 획득한 경우에 넣는다.
						if(data.json().status ==200 && null!=data.json().result){
							$$("table_info_field_list").parse(data.json().result.fieldList)
							// 다시 읽는다.
				    		$$("table_info_field_list").refresh();
				    		$$("table_info_field_list").hideProgress();
						}
					}
				);
				
				// 인덱스 내용 조회
				// progress 추가
				webix.extend($$("table_info_index_list"), webix.ProgressBar);
				// 로딩 프로그레스 
				$$("table_info_index_list").showProgress();
				// 담기 전에 모두 지운다
				$$("table_info_index_list").clearAll();
				// 인덱스 조회 
				webix.ajax().get("/database/indexList.json",{
					server:server,
					schema:schema,
					account:account,
					table:tableName}, 
					function(text,data){
						// 필드 정보를 획득한 경우에 넣는다.
						if(data.json().status ==200 && null!=data.json().result){
							$$("table_info_index_list").parse(data.json().result.indexList)
							// 다시 읽는다.
				    		$$("table_info_index_list").refresh();
				    		$$("table_info_index_list").hideProgress();
						}
					}
				);
			});
		}
		
		// function 리스트
		// view 리스트
		// procedure
		
		// 쿼리 입력창
		var database_query_cell = [{
			header:"sql1",
			cols:[{	
				id:"database_query_form",
				adjust:true,
				view : "form", 
				minHeight:300,
				minWidth:500,
				autowidth:true,
				autoheight:true,
				scroll:false,
				elements:[
					{	id:"database_query_input",	
						view : "textarea",
						placeholder:"input query"
					}
				]
			},
			{
				rows:[{
					id:"database_query_execute_button",
					view:"button",
					value:"execute",
					hotkey:"enter-Ctrl",
					width:100,
					height:100,
					on:{
						"onItemClick":function(){ // 쿼리 실행
							console.log($$("database_query_input").getValue());

							webix.ajax().post("/database/executeQuery.json",{
								server:server,
								schema:schema,
								account:account,
								query:encodeURIComponent($$("database_query_input").getValue()),
					  		  	autoCommit:false,
					  		  	htmlAllow:false}, 
								function(text,data){
									// 필드 정보를 획득한 경우에 넣는다.
										if(data.json().status ==200 && null!=data.json().result){
										// 이미 있는 내용은 모두 지운다 
										$$("database_result_list_view").config.columns = [];
										$$("database_result_list_view").clearAll();
											
										// 필드 정보를 갱신한다.
										// row count 가 0이면 데이터가 없다고 표시한다.
										if(data.json().result.rowCount == 0){
											$$("database_result_list_view").config.columns = [
                                            	{
                                           			id:"result1",
                                           			header:"데이터가 없습니다.",
                                           			adjust:true
                                           		}
                                            ];
											$$("database_result_list_view").refreshColumns();
										} else {
											// row 1개를 꺼내서 필드를 구성한다.
											console.log(data.json().result.resultList);
											
											var loop=0;
											$.each(data.json().result.resultList[0],function(index){
												$$("database_result_list_view").config.columns[loop]={};
												$$("database_result_list_view").config.columns[loop].id = index;
												$$("database_result_list_view").config.columns[loop].header = index;
												$$("database_result_list_view").config.columns[loop].adjust = true;
												loop++;
											});
											
											$$("database_result_list_view").refreshColumns();

											$$("database_result_list_view").parse(data.json().result.resultList)
								    		$$("database_result_list_view").refresh();
										}
										
										
										// 데이터를 갱신한다.

									}
								}
							);
						}
					}
				}]
			}]
		}];
		
		// 쿼리 입력창 tab 키 허용
		$(document).delegate("[name='database_query_input']", "keydown", function(e) {
			var keyCode = e.keyCode || e.which;
			if (keyCode == 9) {
				event.preventDefault();
				IndentHelper.indent(this, event.shiftKey);
			}
		});
		
		// 결과 창
		var database_result_cell = [
			{	view : "datatable", 
				header:"sql1",			
				id:"database_result_list_view", 						
				columns:[],	
				data:[],
				tooltip:true,
 				select:"row",
 				resizeColumn:true,
				minHeight:280,
 				autowidth:true,
				scroll:true
			},
		]
		

		//grid.config.columns = [..new collection of columns..];
		//grid.refreshColumns();

		
		
		// CURD
		// Mybatis
		// hibernate
		// model 생성
		// 히스토리
		// create 테이블
		// 인덱스
		
		// 결과 값
		
		// 유틸리티
		var equals=function (a,b){
			if(a != null && b != null){
				a = a.toString().toLowerCase();
				return a.indexOf(b) !== -1;
			} else {
				return false;
			}
		};
		
		webix.UIManager.tabControl = false; 
	</script>

	<!-- 메뉴 리스트의 템플릿 -->
	<div id="menu_template" style="display: none;">
		<span class="webix_icon fa-#icon#"></span> #value#
	</div>
	<!-- footer의 템플릿 -->
	<div id="footer" style="display: none;">
		Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved.
	</div>
</body>
</html>