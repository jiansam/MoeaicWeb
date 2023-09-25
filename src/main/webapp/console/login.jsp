<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%@ page import="org.dasin.tools.*" %>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%
	dWebTools.setResponseNoCache(response);
%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<META HTTP-EQUIV="EXPIRES" CONTENT="0">
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%-- 	<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" /> --%>
	<title>經濟部投資審議司 - 後台管理</title>
	<style type="text/css">
		#login_page{
			background-image: url(../images/console/consoleBG.png);
			margin:0 auto;
			width:1050px;
			height:600px;
		}
		#content{
			margin:0 auto;
			width:960px;
			min-height:450px;
			height:auto !important;
			height:450px;
			padding:20px;
			background-repeat:repeat-y;
			background-position:center;
			overflow:hidden;
		}
		#content #leftdiv{
			float:left;
			margin-left:6px;
			margin-top:15px;
			width:440px;
			min-height:200px;
			height:auto !important;
			height:200px;
			overflow:hidden;
		}
		#content #rightdiv{
			float:right;
			margin-left:15px;
			margin-top:240px;
			width:440px;
			min-height:200px;
			height:auto !important;
			height:200px;
		}
		#rightdiv #loginForm{
			width:314px;
			padding:10px;	
			margin:42px 15px 15px 60px;
			/* background-color: #fbdfcc; */
			font-size:16px;
			font-family:Microsoft JhengHei, 新細明體, PMingLiU, Helvetica, sans-serif;
			-webkit-border-radius: 20px;
			-moz-border-radius: 20px;
			border-radius: 20px;
		}		
	</style>
	<script type="text/javascript">
		$(function() {
			$( "#dialog" ).dialog({
				autoOpen: false,
				width: 320,
				show: {
					effect: "blind",
					duration: 250
				},
				hide: {
					effect: "blind",
					duration: 250
				}
			});
			$( "#opener" ).click(function() {
				$( "#dialog" ).dialog( "open" );
			});
		});
	</script>
</head>
<body>
	<div id="login_page">
		<div id="content">
			<div id="leftdiv"></div>
			<div id="rightdiv">
				<div id="loginForm"  style="background-color:#E8EEEE; ">
					<form action="${pageContext.request.contextPath}/console/edit-pages.view" method="post">
						<h4 style="margin-left:5px;color:#B53333;"><b>歡迎登入投審會網站後台管理</b></h4>
						<div style="margin-top:5px;margin-left:20px;color:#383838; font-weight:bold;">帳號：<input type="text" name="account" style="width: 210px;" required /> </div>
						<div style="margin-top:5px;margin-left:20px;color:#383838; font-weight:bold;">密碼：<input type="password" name="password"  style="width: 210px;" required/> </div>
						<div style="margin-top:5px;margin-left:5px;color:#383838; font-weight:bold;">
							動態碼：<input type="text" name="captcha"  style="width: 65px;" required /> 
							<img src="${pageContext.request.contextPath}/captcha" style="margin-left: 5px;vertical-align: text-bottom;"/>
							<span style="margin-left: 5px;color:#0B3861;"><a href="#" id="opener">忘記密碼</a></span>
							
								<div id="dialog" title="忘記密碼" style="font-size: 15px;">
									<p>當您忘記密碼時，請與網站管理員聯繫。</p>
									<br/>
									聯絡人：蘇琪彥<br/>
									Tel：02-33435736<br/>
									E-mail: <a href="mailto:cysu@moeaic.gov.tw">cysu@moeaic.gov.tw</a>
								</div>
							
						</div>
						<div style="margin-top:10px; text-align:right; width:280px;">
							<span style="color: red;font-size: 13px;">${errors.inputER}</span>
<%-- 							<span style="color: red;font-size: 13px;">${errors.noER}&nbsp;</span> --%>
							<input type="hidden" name="doThing" value="PSW_userManage">
							<input id="sent" type="submit" value="登入" />
							<input type="reset" value="重填" />
						</div>											
					</form>
				</div>
			</div>
		</div>
    </div>

<console:footer />

</body>
</html>