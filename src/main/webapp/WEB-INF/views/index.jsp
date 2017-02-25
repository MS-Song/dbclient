<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>DB Client</title>
	<meta  name = "viewport" content = "initial-scale = 0.8, maximum-scale = 1.0, user-scalable = no">
	<link rel="stylesheet" href="./static/codebase/skins/web.css" type="text/css" media="screen">
	<style type="text/css">
      .CodeMirror {border-top: 1px solid black; border-bottom: 1px solid black;}
      .CodeMirror-focused .cm-matchhighlight {
        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFklEQVQI12NgYGBgkKzc8x9CMDAwAAAmhwSbidEoSQAAAABJRU5ErkJggg==);
        background-position: bottom;
        background-repeat: repeat-x;
      }
      .cm-matchhighlight {background-color: lightgreen}
      .CodeMirror-selection-highlight-scrollbar {background-color: green}
    </style>	
	<!-- jQuery -->
	<script src="./static/js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
	<!-- webix -->
	<script src="./static/codebase/webix.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/utils.js" type="text/javascript" charset="utf-8"></script>
	<!-- codemirror 	-->	
	<script src="./static/js/webix/codemirror-sql.js" type="text/javascript"></script>
	<script src="./static/js/webix/codemirror-java.js" type="text/javascript"></script>
</head>
<body>
	<script src="./static/js/webix/layout.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/member.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/database.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/databaseUtils.js" type="text/javascript" charset="utf-8"></script>
	<script src="./static/js/webix/javaUtils.js" type="text/javascript" charset="utf-8"></script>

	<c:if test="${isAdmin=='true'}">
  		<!-- 관리자 로그인 시에 admin 기능 활성화 -->
		<script src="./static/js/webix/admin.js" type="text/javascript" charset="utf-8"></script>
	</c:if>  	
</body>
</html>