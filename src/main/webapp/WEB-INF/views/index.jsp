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
	<script src="./static/codemirror-5.10/lib/codemirror.js"></script>
	<script src="./static/codemirror-5.10/mode/sql/sql.js"></script>
	<script src="./static/codemirror-5.10/addon/hint/show-hint.js"></script>
	<script src="./static/codemirror-5.10/addon/hint/sql-hint.js"></script>

	<title>DB Client</title>
</head>
<body>
	<script src="./static/js/webix/layout.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/member.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/database.js" type="text/javascript" charset="utf-8"></script>
	
	<form><textarea id="code" name="code">
	
	</textarea>
	
	<script>
	webix.ready(function(){
      var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true,
        extraKeys: {"Ctrl-Space": "autocomplete"},
        mode: {name: "sql", globalVars: true}
      });
	});
    </script>
</body>
</html>