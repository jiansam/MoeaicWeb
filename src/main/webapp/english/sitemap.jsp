<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

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
<moeaic:libraries_en />
<title>INVESTMENT COMMISSION, MOEA - SiteMap</title>
</head>

<body>
	<moeaic:header_en />
    <moeaic:slide />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
	    			<h3 class="p" id="pageTitle">SiteMap</h3>
	        	</div>
				<div>
				This website is based on the design principle of accessibility and uses 
				keyboard shortcuts to strengthen the browsing convenience. The layout is divided into 4 areas:<br> 
				　1. the main menu zone at the top, <br>
				　2. the content zone at the center, <br> 
				　3. the navigation menu zone on the left side, <br>
				　4. the bottom menu zone at the bottom. <br><br>
				The following keys are set as the access keys (or speed keys) of this website:<br>
				Alt+U: Main menu block, containing the links to the services of this site. <br>
				Alt+C: Main content area shows the content of each page.<br>
				Alt+L: Navigation links on the Left area.<br>
				Alt+Z: Bottom menu area.<br>
				</div>
				<hr>
				<ul class="sitemap_ul" style="width: 100%;font-size:18px;">
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=en" title="1. About Us">1. About Us</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=en" title="1-1. Dept. of Investment Review, MOEA">1-1. Dept. of Investment Review, MOEA</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atOn&lang=en" title="1-2. Organization">1-2. Organization</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atPy&lang=en" title="1-3. Policies">1-3. Policies</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atImPn&lang=en" title="1-4. Implementation Plan">1-4. Implementation Plan</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atRy&lang=en" title="1-5. Responsibility">1-5. Responsibility</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atFr&lang=en" title="1-6. Future Directions">1-6. Future Directions</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atTc&lang=en" title="1-7. Map">1-7. Map</a></li>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/english/news_newAn.jsp" title="2. What's New">2. What's New</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/english/news_newAn.jsp" title="2-1. Latest News">2-1. Latest News</a></li>
							<li><a href="${pageContext.request.contextPath}/english/news_bsAn.jsp" title="2-2. Statistics">2-2. Statistics</a></li>
							<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=en" title="2-3. Statistics Chart">2-3. Statistics Chart</a></li>
							<%-- 							<li><a href="${pageContext.request.contextPath}/english/news_stRt.jsp">Publications</a></li> --%>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1" title="3. Services">3. Services</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1" title="3-1. Overseas Chinese & Foreign Investment">3-1. Overseas Chinese & Foreign Investment</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=3" title="3-2. Investment From Mainland China">3-2. Investment From Mainland China</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=4" title="3-3. Outward Investment">3-3. Outward Investment</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=5" title="3-4. Investment To Mainland China">3-4. Investment To Mainland China</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=6" title="3-5. Entrepreneur Visa">3-5. Entrepreneur Visa</a></li>
							<li>
								<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=8" title="3-6. Other">3-6. Other</a>
								<ul>
									<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=9" title="3-6-1. Entry Permit for People of Mainland China Area">3-6-1. Entry Permit for People of Mainland China Area</a></li>
									<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=8" title="3-6-2. Entry Permit for Foreigners">3-6-2. Entry Permit for Foreigners</a></li>									
								</ul>
							</li>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/english/FAQ_EN.jsp" title="4. FAQs">4. FAQs</a>  <%--2023年新增 --%>
					</li>					
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/links.view?do=index&lang=en" title="5. Related Links">5. Related Links</a>  <%--2023年調整編號 --%>
					</li>
				</ul>
			</div>
        </div>
        </div>
     </div>
     
     <moeaic:footer_en />
</body>
</html>
