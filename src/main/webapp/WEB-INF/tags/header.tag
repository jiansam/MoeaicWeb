<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="org.dasin.tools.*" %>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="selected" %>

<script type="text/javascript">
// function selective_link(anchor){
// 	if($(window).width() > 767){
// 		window.location = $(anchor).attr('href');
// 	}
// }
</script>

<%
	selected = dTools.trim(selected);
%>
<!-- header -->
<div id="divHeader">
	<a tabindex="1" title="跳到主要內容區塊" class="sr-only u-focusable" href="#C" style="float:left;">跳到主要內容區塊</a>
	<div class="container" style="position: relative;">
		<div id="divLogo">
			<h1 style="margin: 0;">
			<a href="${pageContext.request.contextPath}/chinese/index.jsp" style="display: block;" title="經濟部投資審議司"> 
				<img src="${pageContext.request.contextPath}/images/moeadir_logo.png" alt="經濟部投資審議司" class="ImgLogo">
			</a>
			</h1>
		</div>
		<div id="divSearch">

			<div id="divTopMenu">
				<a id="U" href="#U" accesskey="U" class="sr-only u-focusable" style="line-height:30px;" title="上方選單">:::</a>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/chinese/index.jsp" title="回到首頁">回到首頁</a></li>
					<li>｜<a href="${pageContext.request.contextPath}/english/index.jsp" title="English">English</a></li>
					<li>｜<a href="${pageContext.request.contextPath}/chinese/FAQ.jsp" title="常見問答">常見問答</a></li>  <!--2023年新增-->
					<li>｜<a href="${pageContext.request.contextPath}/about.view?type=ctIf&lang=CH" title="聯絡我們">聯絡我們</a></li>
					<li>｜<a href="${pageContext.request.contextPath}/chinese/sitemap.jsp" title="網站導覽">網站導覽</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- menu -->
<div id="divMenu">
	<nav class="navbar navbar-default">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#topFixedNavbar1">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a href="${pageContext.request.contextPath}/english/index.jsp" id="navbar-language" title="English">English</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="topFixedNavbar1">
				<ul class="nav navbar-nav" style="width: 100%">
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=ch"
							class="dropdown-toggle <%= "about".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" data-toggle="dropdown" role="button" aria-expanded="false" title="關於本司">關於本司</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=ch" title="認識本司">認識本司</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atMr&lang=ch" title="認識召集人及司長">認識召集人及司長</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atOn&lang=ch" title="組織與職掌">組織與職掌</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atPy&lang=ch" title="本司政策">本司政策</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atImPn&lang=ch" title="施政計畫">施政計畫</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atTc&lang=ch" title="交通位置">交通位置</a></li>
						</ul>
					</li>
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp"
							class="dropdown-toggle <%= "news".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" data-toggle="dropdown" role="button" aria-expanded="false" title="焦點消息">焦點消息</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp" title="最新公告">最新公告</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_bsAn.jsp" title="業務統計">業務統計</a></li>
							<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=ch" title="業務統計圖表">業務統計圖表</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_stRt.jsp" title="研究報告">研究報告</a></li>
						</ul>
					</li>
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1"
							class="dropdown-toggle <%= "business".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" data-toggle="dropdown" 
							role="button" aria-expanded="false" title="申辦業務及案件查詢">申辦業務及案件查詢</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]申請案件查詢"
								><span style="color:#3A70C0;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
								<span style="color:#3A70C0;font-weight:bolder" class="application">申請案件查詢</span></a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="申請事項及表單"
								><span style="color:#3A70C0;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
								<span style="color:#3A70C0;font-weight:bolder" class="application">申請事項及表單</span></a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="僑外來臺投資">僑外來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=3" title="陸資來臺投資">陸資來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=4" title="國外(含港澳地區)投資">國外(含港澳地區)投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=5" title="對中國大陸投資">對中國大陸投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=6" title="創業家簽證">創業家簽證</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=38" title="經濟部推薦申請歸化國籍之高級專業人才">經濟部推薦申請歸化國籍之高級專業人才</a></li>							
							<li><a href="#" title="其他">其他</a>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=9" title="大陸人士來臺">&nbsp;&nbsp;<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>大陸人士來臺</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=8" title="外籍人士來臺">&nbsp;&nbsp;<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>外籍人士來臺</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=7" title="大陸地區產業技術引進">&nbsp;&nbsp;<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>大陸地區產業技術引進</a></li>
						</ul>
					</li>
					<li class="TopNav"><a href="${urlManager.pages_map.apply_online_CH}" target="_blank" title="[另開新視窗]線上申辦">線上申辦</a></li>
<%-- 					<li class="TopNav"><a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]申請案件查詢">申請案件查詢</a></li> --%>
					<li class="dropdown TopNav"><a href="${pageContext.request.contextPath}/openData.view" class="visible-xs-block dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="政府資訊公開">政府資訊公開</a>
						<ul class="dropdown-menu" role="menu">
							<c:forEach var="data" items="${optionManager.openData_type}">
							<li><a href="${pageContext.request.contextPath}/openData.view#op${data.id}" title="${data.name}">${data.name}</a></li>
							</c:forEach>
						</ul>
					</li>
<%-- 					<li class="TopNav"><a href="${urlManager.pages_map.attachedFile_CH}" target="_blank" title="[另開新視窗]附件下載">附件下載</a></li> --%>
					<li class="TopNav"><a class="<%= "links".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" href="${pageContext.request.contextPath}/links.view?do=index" title="相關網站">相關網站</a></li>
					<li class="visible-xs-block dropdown TopNav">
	                   	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="頂端功能">頂端功能</a>
	                   	<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/chinese/index.jsp" title="回到首頁">&nbsp;回到首頁</a></li>
							<li><a href="${pageContext.request.contextPath}/english/index.jsp" title="English">&nbsp;English</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/FAQ.jsp" title="常見問答">&nbsp;常見問答</a></li>  <!--2023年新增-->							
							<li><a href="${pageContext.request.contextPath}/about.view?type=ctIf&lang=CH" title="聯絡我們">&nbsp;聯絡我們</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=ctAy&lang=CH" title="陳情管道">&nbsp;陳情管道</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/sitemap.jsp" title="網站導覽">&nbsp;網站導覽</a></li>
						</ul>
                	</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>

<moeaic:noscript />