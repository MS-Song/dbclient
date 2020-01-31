webix.codebase = "/static/js/codemirror-5.10/";
webix.protoUI({
	name:"codemirror-editor-sql",
	defaults:{
		lineNumbers: true,
		indentWithTabs: true,
		smartIndent: true,
		autofocus: true,
		extraKeys: {
			"Ctrl-Space": "autocomplete",
			"Cmd-Enter": "autocomplete",
			"Ctrl-Enter":function(){
				// 컨트롤 엔터 입력시 쿼리 실행
				event.preventDefault();
				$$("database_query_button_execute").callEvent("onItemClick");
			},
		    "F11": function(cm) {
		    	cm.setOption("fullScreen", !cm.getOption("fullScreen"));
		    },
		    "Esc": function(cm) {
		    	if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
		    },
		    "Ctrl-1":function(cm){selectCountQuery();},
		    "Ctrl-2":function(cm){selectAllQuery();},
		    "Ctrl-3":function(cm){selectNameQuery();},
		    "Ctrl-4":function(cm){insertIntoQuery();},
		    "Ctrl-5":function(cm){updateSetQuery();},
		    "Ctrl-6":function(cm){deleteQuery();},
		    "Ctrl-7":function(cm){$$("database_query_button_auto_commit").callEvent("onItemClick");},
		    "Ctrl-8":function(cm){$$("database_query_button_use_limit").callEvent("onItemClick");},
		    "Ctrl-9":function(cm){csvFileUploadForm();},
		    "Ctrl-0":function(cm){killExecuteQuery();},
		},
		hintOptions: {tables: {}}
	},
	$init:function(config){
		this.$view.innerHTML = "<textarea style='width:100%;height:100%;'></textarea>";
		this.$ready.push(this._render_cm_editor);
	},
	_render_cm_editor:function(){
		var deps = [
		    "lib/codemirror.css",
		    "doc/docs.css",
		    "addon/hint/show-hint.css",
		    "addon/display/fullscreen.css",
		    "theme/eclipse.css",
			"lib/codemirror.js",
			"mode/sql/sql.js",
			"addon/hint/show-hint.js",
			"addon/hint/sql-hint.js",
			"addon/display/fullscreen.js",
			"addon/selection/active-line.js",
			"addon/edit/closebrackets.js",
			"addon/scroll/annotatescrollbar.js",
			"addon/search/matchesonscrollbar.js",
			"addon/search/searchcursor.js",
			"addon/search/match-highlighter.js"
			
		];
		webix.require(deps, this._render_when_ready, this);
	},

	_render_when_ready:function(){
		this.editor = CodeMirror.fromTextArea(this.$view.firstChild, {
			lineNumbers		:this.config.lineNumbers,
			indentWithTabs	:this.config.indentWithTabs,
			smartIndent		:this.config.smartIndent,
			lineNumbers		:this.config.lineNumbers,
			autofocus		:this.config.autofocus,
			extraKeys		:this.config.extraKeys,
			mode			:'text/x-sql',
			styleActiveLine : true,
			autoCloseBrackets: true,
			highlightSelectionMatches: {showToken: /\w/, annotateScrollbar: true},
			hintOptions		:this.config.hintOptions
		});

//		console.log(document.getElementsByClassName("CodeMirror cm-s-default")[0]);
//		CodeMirror.commands.autocomplete = function(cm) {
//			cm.showHint({
//				hint: CodeMirror.hint.sql,
//				container: document.getElementsByClassName("CodeMirror cm-s-default")[0]
//			});
//		};
		
		this.setValue(this.config.value);
		
		if (this._focus_await){
			this.focus();
		}
		
		// 이동 경로 이벤트 추가
		this.editor.on("cursorActivity",function(cm){
			editorPositionWrite(cm);
		});
		
	},

	_set_inner_size:function(){
		if (!this.editor || !this.$width) return;
		this._updateScrollSize();
		this.editor.scrollTo(0,0); //force repaint, mandatory for IE
	},
	_updateScrollSize:function(){
		var box = this.editor.getWrapperElement();
		var height = (this.$height || 0) + "px";

		box.style.height = height;
		box.style.width = (this.$width || 0) + "px";

		var scroll = this.editor.getScrollerElement();
		if (scroll.style.height != height){
			scroll.style.height = height;
			this.editor.refresh();
		}
	},
	$setSize:function(x,y){
		if (webix.ui.view.prototype.$setSize.call(this, x, y)){
			this._set_inner_size();
		}
	},

	setValue:function(value){
		if(!value && value !== 0)
			value = "";

		this.config.value = value;
		if(this.editor){
			this.editor.setValue(value);
			//by default - clear editor's undo history when setting new value
			if(!this.config.preserveUndoHistory)
				this.editor.clearHistory();
			this._updateScrollSize();
		}
	},

	getValue:function(){
		return this.editor?this.editor.getValue():this.config.value;
	},

	focus:function(){
		this._focus_await = true;
		if (this.editor)
			this.editor.focus();
	},

	getEditor:function(){
		return this.editor;
	},


	//undo, redo, etc
	undo:function(){
		this.editor.undo();
	},
	redo:function(){
		this.editor.redo();
	},
	undoLength:function(){
		return this.editor.historySize().undo;
	}
}, webix.ui.view);