<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta  name = "viewport" content = "initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no">
	<!-- jQuery -->
	<script src="./static/js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
	<!-- webix -->
	<!-- jQuery -->
	<script src="./static/js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
	<!-- webix -->
	<link rel="stylesheet" href="./static/codebase/skins/web.css" type="text/css" media="screen">	
	<script src="./static/codebase/webix.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/indentHelper.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/databaseUtils.js" type="text/javascript" charset="utf-8"></script>
	<!-- codemirror 	-->	
	<link rel="stylesheet" href="./static/codemirror-5.10/lib/codemirror.css">
	<link rel="stylesheet" href="./static/codemirror-5.10/addon/hint/show-hint.css">
	<script src="./static/codemirror-5.10/lib/codemirror.js"></script>
	<script src="./static/codemirror-5.10/addon/hint/show-hint.js"></script>
	<script src="./static/codemirror-5.10/addon/hint/sql-hint.js"></script>
	<script src="./static/codemirror-5.10/mode/sql/sql.js"></script>

	<title>DB Client</title>
</head>
<body>
	<script src="./static/js/webix/layout.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/member.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/database.js" type="text/javascript" charset="utf-8"></script>
	
	<script>
	
	// 사이즈 안맞는 문제
	// webix 에서 다시 그리면 사라지는 문제 
	// 에디터에서 생성된 내용이 textarea 로 전송 안되는 문제
	webix.ready(function(){
		var editor = CodeMirror.fromTextArea(document.getElementsByName("database_query_input")[0], {
        lineNumbers: true,
	    indentWithTabs: true,
	    smartIndent: true,
	    lineNumbers: true,
	    matchBrackets : true,
	    autofocus: true,
	    extraKeys: {"Ctrl-Space": "autocomplete"},
	    hintOptions: {tables: {
	      users: {name: null, score: null, birthDate: null},
	      countries: {name: null, population: null, size: null}
	    }}
      });
	});

    </script>
</body>
</html>