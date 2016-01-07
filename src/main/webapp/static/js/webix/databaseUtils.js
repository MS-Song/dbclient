/**
 * Database 관련 유틸리티 
 * 
 */

/**
 * 개발자 메뉴 
 */
var database_developer_cell = [{
	header:"JAVA",
	rows:[{
		id:"database_developer_java_form",
		view : "form", 
		elements:[{	
			rows:[{
				cols:[
					    {
							id:"database_developer_button_java_model",
							view:"button",
							value:"java model",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_java_hibernate_model",
							view:"button",
							value:"java H-model",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_java_model_set",
							view:"button",
							value:"java setter",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_java_model_get",
							view:"button",
							value:"java getter",
							tooltip:""
					    }
				    ] // end cols
				}] // end rows
			}] // end elements
		},
		{ view:"resizer"},
		{
			//테이블 인포
			id:"database_developer_table_info_java",
			view : "datatable",
			columns:[],
			data:[],
			tooltip:true,
			select:"row",
			resizeColumn:true,
			navigation:true,
		}]
	},
	{
		header:"Myabtis",
		rows:[{		
			id:"database_developer_mybatis_form",
			view : "form", 
			elements:[{	
				rows:[{
					cols:[
					    {
							id:"database_developer_button_mybatis_select",
							view:"button",
							value:"mybatis select",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_mybatis_insert",
							view:"button",
							value:"mybatis insert",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_mybatis_update",
							view:"button",
							value:"mybatis update",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_mybatis_delete",
							view:"button",
							value:"mybatis delete",
							tooltip:""
					    }, 
					    {
							id:"database_developer_button_mybatis_result",
							view:"button",
							value:"mybatis result",
							tooltip:""
					    }
					] // end cols
				}] // end rows
			}] // end elements
		},
		{ view:"resizer"},
		{
			// 데이터베이스 인포
			id:"database_developer_table_info_mybatis"
		}]
	},
	{
		header:"PHP",
		rows:[{		
			id:"database_developer_php_form",
			view : "form", 
			elements:[]
		},
		{ view:"resizer"},
		{
			// 데이터베이스 인포
			id:"database_developer_table_info_php"
		}]
	}
];