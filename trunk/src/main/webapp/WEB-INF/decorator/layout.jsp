<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge" /> -->
	<meta http-equiv="X-UA-Compatible" content="IE=9"/>
	
	<title>Danawa db Client</title>
	<link rel="stylesheet" href="/static/css/global.css"/>
	<link rel="stylesheet" href="/static/css/smoothness/jquery-ui-1.10.4.custom.css" />
	<link rel="stylesheet" href="/static/js/textComplete/bootstrap.css"/>
	<script type='text/javascript' src='/static/js/jquery-1.11.0.js' ></script>
	<script type='text/javascript' src='/static/js/jquery-ui-1.10.4.js' ></script>
	<script type="text/javascript" src="/static/js/index.js"></script>
	<script type="text/javascript" src="/static/js/clipboard.js"></script>
<style>
<!--
 textarea{width: 100%;resize: horizontal }
-->
</style>

</head>
<body>
	<div class="header">
		<div id="topMenu">
			<table>
				<tr>
					<td valign="top">
						<div id="logo"><a href="/" onfocus="this.blur()"></a></div>
						<div id="marketPlaceList" style="text-align: left">
						<h3> 데이터베이스 관리 </h3>
						</div>
						<div id="loginInfo">
							<span id="id">ID</span>
							<span id="name">Name</span>
							<br/>
							<span id="logout">[로그아웃]</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<ul class="jd_menu">
			<li><a id="databaseManager" href="#">Database 관리</a></li>
			<li><a id="userManager" href="#">회원 관리</a></li>
		</ul>
		<div id="topPadding"></div>
	</div>
	<div id="wrapper-outer">
		<div id="wrapper" align="center">
			<decorator:body />
		</div>
	</div>
	<div id="bottomMenu">
		<table>
			<tr>
				<td>Copyrightⓒ Song7749 Co., Ltd. All Rights Reserved.</td>
			</tr>
		</table>
	</div>
</body>
</html>