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
	<a tabindex="1" title="Jump to the main content block" class="sr-only u-focusable" href="#C" style="float:left;">Jump to the main content block</a>
	<div class="container" style="position: relative;">
		<div id="divLogo">
			<h1 style="margin: 0;">
			<a href="${pageContext.request.contextPath}/english/index.jsp" style="display: block;"> 
				<img src="${pageContext.request.contextPath}/images/moeadir_logo.png" alt="Department of Investment Review, MOEA" title="Department of Investment Review, MOEA" class="ImgLogo">
			</a>
			</h1>
		</div>
		
		<div id="divSearch">
			<div id="divTopMenu">
				<a id="U" href="#U" accesskey="U" class="sr-only u-focusable" style="line-height:30px;" title="Main Menu">:::</a>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/english/index.jsp" title="Home">Home</a></li>
					<li>｜<a href="${pageContext.request.contextPath}/chinese/index.jsp" title="Chinese">正體中文</a></li>
					<li>｜<a href="${pageContext.request.contextPath}/english/FAQ_EN.jsp" title="FAQs">FAQs</a></li>  <!--2023年新增-->
					<li>｜<a href="${pageContext.request.contextPath}/english/sitemap.jsp" title="Sitemap">Sitemap</a></li>
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
				<a href="${pageContext.request.contextPath}/chinese/index.jsp" id="navbar-language" title="正體中文">正體中文</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="topFixedNavbar1">
				<ul class="nav navbar-nav" style="width: 100%">
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=en"
							class="dropdown-toggle <%= "about".equalsIgnoreCase(selected) ? "selectedCategory" : "" %> " data-toggle="dropdown" role="button" aria-expanded="false" title="About Us">About Us</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=en" title="Dept. of Investment Review, MOEA">Dept. of Investment Review, MOEA</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atOn&lang=en" title="Organization">Organization</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atPy&lang=en" title="Policies">Policies</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atImPn&lang=en" title="Implementation Plan">Implementation Plan</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atRy&lang=en" title="Responsibility">Responsibility</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atFr&lang=en" title="Future Directions">Future Directions</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atTc&lang=en" title="Map">Map</a></li>
						</ul>
					</li>
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/english/news_newAn.jsp"
							class="dropdown-toggle <%= "news".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" data-toggle="dropdown" role="button" aria-expanded="false" title="What's New">What's New</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/english/news_newAn.jsp" title="Latest News">Latest News</a></li>
							<li><a href="${pageContext.request.contextPath}/english/news_bsAn.jsp" title="Statistics">Statistics</a></li>
							<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=en" title="Statistics Chart">Statistics Chart</a></li>
<%-- 							<li><a href="${pageContext.request.contextPath}/english/news_stRt.jsp">Publications</a></li> --%>
						</ul>
					</li>
					<li class="dropdown TopNav">
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1"
							class="dropdown-toggle <%= "business".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" data-toggle="dropdown" role="button" aria-expanded="false" title="Services">Services</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1" title="Overseas Chinese & Foreign Investment">Overseas Chinese & Foreign Investment</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=3" title="Investment From Mainland China">Investment From Mainland China</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=4" title="Outward Investment">Outward Investment</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=5" title="Investment To Mainland China">Investment To Mainland China</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=6" title="Entrepreneur Visa">Entrepreneur Visa</a></li>
							<li><a href="#" title="Other">Other</a>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=9" title="Entry Permit for People of Mainland China Area">&nbsp;&nbsp;<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>Entry Permit for People of Mainland China Area</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=8" title="Entry Permit for Foreigners">&nbsp;&nbsp;<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>Entry Permit for Foreigners</a></li>
						</ul>
					</li>
<%-- 					<li class="TopNav"><a href="${urlManager.pages_map.apply_online_EN}" target="_blank">線上申辦</a></li> --%>
<%-- 					<li class="TopNav"><a href="${urlManager.pages_map.apply_search_EN}" target="_blank">申請案件查詢</a></li> --%>
<%-- 					<li class="TopNav"><a href="${urlManager.pages_map.attachedFile_EN}" target="_blank">附件下載</a></li> --%>
					
					<li class="TopNav"><a class="<%= "links".equalsIgnoreCase(selected) ? "selectedCategory" : "" %>" href="${pageContext.request.contextPath}/links.view?do=index&lang=en" title="Related Links">Related Links</a></li>
					<li class="visible-xs-block dropdown TopNav">
	                   	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" title="Top Menu">Top Menu</a>
	                   	<ul class="dropdown-menu" role="menu">
							<li><a href="${pageContext.request.contextPath}/english/index.jsp" title="Home">Home</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/index.jsp" title="正體中文">正體中文</a></li>
							<li><a href="${pageContext.request.contextPath}/english/FAQ_EN.jsp" title="FAQs">FAQs</a></li>  <!--2023年新增-->							
							<li><a href="${pageContext.request.contextPath}/english/sitemap.jsp" title="Sitemap">Sitemap</a></li>
						</ul>
                	</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>

<moeaic:noscript_en />