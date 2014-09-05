<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="databasePopup" title=""></div>
<div id="userPopup" title=""></div>
<div id="changePasswordPopup" title=""></div>
<div id="loading" style="display: none;position: absolute;width: 300px;height: 35px;top:500px;left:30% ;background:gray;">
	<div style="color: white;"> <br/> <strong> Lading Now ......</strong> </div>
</div>
<div style="width: 100%">
	<form name="selectDatabaseServer" id="selectDatabaseServer" method="get">
	<input type="hidden" name="processKey" >
	<input type="hidden" name="pid">
	<input type="hidden" name="time">
	<input type="hidden" name="autoCommit" value="false">
	<input type="hidden" id="tableComment" value=""/>
	
	<table class="table-list" id="tblSelectServerInfo">
		<thead>
			<tr>
				<th width="25%"> 서버 선택 	[ALT+1] [↑↓ select focus] </th>
				<th width="25%"> DB 선택 	[ALT+2] [↑↓ select focus] </th>
				<th width="25%"> 테이블 선택 	[ALT+3] [↑↓ select focus] </th>
				<th width="25%"> Join Table </th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>서버 	검색 <input id="ALT1" type="text" onkeyup="searchServer(this.value);" 		onclick="this.select();" 	style="width: 70%;" title="[ALT+1]" /></td>
				<td>DB		검색 <input id="ALT2" type="text" onkeyup="searchDatabase(this.value);" 	onclick="this.select();"	style="width: 70%;" title="[ALT+2]" /></td>
				<td>TABLE 	검색 <input id="ALT3" type="text" onkeyup="searchTable(this.value);"		onclick="this.select();"	style="width: 70%;" title="[ALT+3]" /></td>
				<td rowspan="3" valign="top">
					<div valign="top">
						<table class="table-list" id="printSelectedTable">
							<tr height="10" id="printSelectedTablePrepend">
								<th width="30" title="add join">선택</th>
								<th width="30" title="left join">LEFT</th>
								<th title="table name">table</th>
								<th width="30" title="alias">AS</th>
								<th width="30">삭제</th>
								<th width="30">순서</th>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td id="serverList"></td>
				<td id="databaseList"></td>
				<td id="tablesList"></td>
			</tr>
			<tr>
				<td id="serverName"></td>
				<td id="databaseName"></td>
				<td id="tableName"></td>
			</tr>
		</tbody>
	</table>
	<br/>
	<span id="printAction"></span>
	<table class="table-list">
		<thead>
			<tr>
				<th>
					<input type="button" name="bntDisplayResult" value="결과닫기" onclick="displayResult();" style="width: 60px;height: 20px;" title="결과창 닫기">
					<input type="button" value="결과 엑셀저장" onclick="excelDown();" style="width: 120px;height: 20px;" title="결과를 엑셀파일로 저장">
					<input type="button" value="결과복사 html" onclick="copyResult('html');" style="width: 120px;height: 20px;" title="결과 클립보드로 복사 html 태그 포함">
					<input type="button" value="결과복사 No html" onclick="copyResult('noHtml');" style="width: 120px;height: 20px;" title="결과 클립보드로 복사 html 태그 제거">
					<input type="button" value="결과복사 IN() 넣기" onclick="copyResult('insertIn');" style="width: 120px;height: 20px;" title="결과 클립보드로 복사 html 태그 제거">
					<input type="button" name="bntWidthAll" value="넓게하기" onclick="displayWidthAll();" style="width: 60px;height: 20px;" title="결과 화면을 넓게 펴기">
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<div id="queryResult"></div><br/>
					<div id="queryResultTime"></div>
					<div id="queryResultData"></div>
				</td>
			</tr>
		</tbody>
	</table>
	<br/>
	<br/>

	<table class="table-list" style="table-layout:fixed;">
		<tr>
			<th valign="top" width="50%">
				<span id="spanTableName"></span>
			</th>
			<th valign="top" width="50%">쿼리 [엔터=\n | ';'엔터=execute | TAB=TAB] [ctrl+1|2|3|... 상단기능] [ctrl+0:execute] </th>
		</tr>
		<tr>
			<td rowspan="3" id="fieldList" valign="top" > &nbsp; </td>
			<td valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tbody>
						<tr>
							<td width="85%" valign="top">
								<input type="button" id="bntSelectCount"		value="select count(PK)"	onclick="selectCountQuery();" 			style="width: 100px;height: 40px;" title="select count(pk) from table | ctrl+1">
								<input type="button" id="bntSelectColumn" 		value="select 필드명 " 		onclick="selectNameQuery();" 			style="width: 80px;	height: 40px;" title="select 필드명 from table | ctrl+2">
								<input type="button" id="bntJoinSelectAll" 		value="join select" 		onclick="selectJoinQuery();" 			style="width: 70px;	height: 40px;" title="select 필드입력 from table join | ctrl+3">
								<input type="button" id="bntSelectInsertFrom" 	value="select Insert SET"	onclick="selectInsertQuery('SET');" 	style="width: 100px;height: 40px;" title="select 를 실행해서 insert 구문을 생성한다.">
								<input type="button" id="bntSelectInsertFrom" 	value="select Insert VAL"	onclick="selectInsertQuery('VALUE');" 	style="width: 100px;height: 40px;" title="select 를 실행해서 insert 구문을 생성한다.">
								<hr/>								
								<input type="button" id="bntInsertInto" 		value="insert into" 		onclick="insertQuery('into');" 			style="width: 70px;	height: 40px;" title="">
								<input type="button" id="bntInsertSet" 			value="insert set" 			onclick="insertQuery('set');" 			style="width: 70px;	height: 40px;" title="">
								<input type="button" id="bntUpdateSet" 			value="update" 				onclick="updateQuery();" 				style="width: 60px;	height: 40px;" title="">
								<input type="button" id="bntDeleteFrom" 		value="delete" 				onclick="deleteQuery();" 				style="width: 60px;	height: 40px;" title="">
								<br/>
								<textarea rows="14" name="query" onkeydown="useTab(this)" cols="100%"></textarea>
								<table>
									<tr>
										<td rowspan="3"><input type="button" id="bntAutoCommit" 	value="autoCommit false" 	onclick="selectAutoCommit();" 	style="width: 120px;height: 50px;" title="auto commit true/false"></td>
										<td>
											쿼리 입력폼 크기 : <span id="inputForm_size">14</span>
											<input type="button" name="upsizeForm" value="크게" onclick="this.form.query.rows=this.form.query.rows+1;document.getElementById('inputForm_size').innerHTML=this.form.query.rows"  style="width: 30px;height: 20px;">
											<input type="button" name="downsizeForm" value="작게" onclick="this.form.query.rows=this.form.query.rows-1;document.getElementById('inputForm_size').innerHTML=this.form.query.rows" style="width: 30px;height: 20px;"></td>
									</tr>
									<tr>
										<td><span id="autoCommitInfo" style="color: red;"> AutoCommit False 상태로 데이터가 입력되지 안습니다</span></td>
									</tr>
									<tr>
										<td><span id="prepareStyle" style="color: red;"> JAVA prepare 스타일</span></td>
									</tr>
								</table>
								<input type="hidden" name="preparedStyle" id="preparedStyle" 	value="JAVA">
								<hr/>
								<input type="button" name="bntPreparedStyleJava" 	value="prepare JAVA"  			onclick="this.form.preparedStyle.value='JAVA';document.getElementById('prepareStyle').innerHTML='JAVA prepare 스타일'" style="width: 85px;height: 40px;" title="prepare style java">
								<input type="button" id="bntJavaModel" 				value="JAVA MODEL"				onclick="javaModel();" 			style="width: 85px;height: 40px;" title="java에서 사용될 MODEL">
								<input type="button" id="bntJavaHibernateModel" 	value="JAVA H-Model"			onclick="javaHibernateModel();" style="width: 90px;height: 40px;" title="java + Hibernate 에서 사용될 MODEL">
								<input type="button" id="bntJavaModelSet" 			value="JAVA SET" 				onclick="javaModelSet();"		style="width: 85px;height: 40px;" title="java에서 사용될 MODEL SET">
								<input type="button" id="bntJavaModelGet" 			value="JAVA GET" 				onclick="javaModelGet();"		style="width: 75px;height: 40px;" title="java에서 사용될 MODEL GET">
								<input type="button" id="bntDBUnit" 				value="DBUnit XML" 				onclick="dbunitXML();" 			style="width: 75px;height: 40px;" title="DB 유닛에서 사용될 XML"><br/><br/>

								<input type="button" id="MybatisSelect" 			value="MybatisSelect"			onclick="mybatisSelect();" 		style="width: 90px;height: 40px;" title="Mybatis 에서 Select">								
								<input type="button" id="MybatisInsert" 			value="MybatisInsert"			onclick="mybatisInsert();" 		style="width: 90px;height: 40px;" title="Mybatis 에서 Insert">
								<input type="button" id="MybatisUpdate" 			value="MybatisUpdate"			onclick="mybatisUpdate();" 		style="width: 90px;height: 40px;" title="Mybatis 에서 Update">								
								<input type="button" id="MybatisDelete" 			value="MybatisDelete"			onclick="mybatisDelete();" 		style="width: 90px;height: 40px;" title="Mybatis 에서 Delete">
								<input type="button" id="bntResultMap" 				value="ResultMap XML"			onclick="resultMap();" 			style="width: 100px;height: 40px;" title="Mybatis 에서 ResultMap">
								<hr/>
								<input type="button" name="bntPreparedStylePHP" 	value="prepare PHP" 	 		onclick="this.form.preparedStyle.value='PHP';document.getElementById('prepareStyle').innerHTML='PHP prepare 스타일'" style="width: 90px;height: 40px;" title="prepare style php">
								<input type="button" id="bntPhpModel" 				value="PHP MODEL" 				onclick="phpModel();" 			style="width: 90px;height: 40px;" title="php에서 사용될 MODEL">
								<input type="button" id="bntPhpModelSet" 			value="PHP SET" 				onclick="phpModelSet();"		style="width: 90px;height: 40px;" title="php에서 사용될 MODEL SET">
								<input type="button" id="bntPhpModelGet" 			value="PHP GET" 				onclick="phpModelGet();"		style="width: 90px;height: 40px;" title="php에서 사용될 MODEL GET">
							</td>
							<td valign="top">
								<input type="hidden" name="htmlAllow" value="false"/>
								<input type="button" id="bntEmpty" value="내용지우기"
									onclick="this.form.query.value='';document.getElementById('queryResult').innerHTML='';document.getElementById('queryResultTime').innerHTML;resizeQueryResult();"
									style="width: 100px;height: 50px;" title="쿼리 내용 삭제 및 결과 삭제">
								<br/>
								<input type="button" id="bntHtmlAllow" value="결과HTML불가" onclick="showHTML()" 					style="width: 100px;height: 50px;" title="결과에 HTML 테그 표현 여부"><br/>
								<input type="button" id="bntShowProcesslist" value="processlist" onclick="showProcesslist();"	style="width: 100px;height: 50px;" title="프로세스 리스트 뷰"><br/>
								<input type="button" id="bntExplainQuery" value="explain" onclick="explainQuery();"				style="width: 100px;height: 50px;" title="explain query"><br/>
								<input type="button" id="bntExecuteQuery" value="execute" onclick="executeQuery();"				style="width: 100px;height: 50px;" title="입력 쿼리 실행"><br/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div><br/>
									<table class="table-list layout_fixed" id="printQuery">
										<tr id="printQueryPrepend"><th colspan="4" class="layout_fixed">history</th></tr>
									</table>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th width="50%" valign="top">create table</th>
		</tr>
		<tr>
			<td id="showCreateTable"  valign="top"> &nbsp; </td>
		</tr>
	</table>
	</form>
</div>
<div id="tmpTable" style="display: none;"></div>