<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">
<%--2023年新增 --%>
<style>
	.sitemap_ul li{
		padding-left: 2em;
	}
	
	.sitemap_ul ul{
		list-style: square;
		list-style-position: inside;
	}
</style>
<head>
<moeaic:libraries />
<title>常見問答 - 經濟部投資審議委員會(INVESTMENT COMMISSION, MOEA)</title>
</head>

<body>
	<moeaic:header />
    <moeaic:slide />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">常見問答</h3>
	        	</div>
				<ul style="width: 100%;font-size:18px;line-height: 40px" class="sitemap_ul">
					<li>
						<p><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1&tab=3" title="僑外來臺投資常見問答">僑外來臺投資常見問答</a></p>
						<p><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=3&tab=3" title="陸資來臺投資常見問答">陸資來臺投資常見問答</a></p>
						<p><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=4&tab=3" title="國外(含港澳地區)投資常見問答">國外(含港澳地區)投資常見問答</a></p>
						<p><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=5&tab=4" title="對中國大陸投資常見問答">對中國大陸投資常見問答</a></p>
						<p><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=6&tab=3" title="創業家簽證常見問答">創業家簽證常見問答</a></p>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						其他
						<ul>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=7&tab=2" title="大陸地區產業技術引進常見問答">大陸地區產業技術引進常見問答</a></li>
							<li><a href="${pageContext.request.contextPath}//businessPub.view?lang=ch&op_id_one=8&tab=2" title="外籍人士來臺常見問答">外籍人士來臺常見問答</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=9&tab=2" title="大陸人士來臺常見問答">大陸人士來臺常見問答</a></li>
						</ul>
					</li>
				</ul>
			</div>
        </div>
        </div>
     </div>
     
     <moeaic:footer />
</body>
</html>
