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
    
    <div id="divContent" >
        <div id="divWhiteBack">
         	<div class="container" >                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
	    			<h3 class="p" id="pageTitle">FAQs</h3>
	        	</div>
				<ul class="sitemap_ul" style="width: 100%;font-size:18px;line-height: 40px">
					<li>
						<P><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1&tab=3" title="Overseas Chinese & Foreign Investment">Overseas Chinese & Foreign Investment</a></P>
						<P><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=3&tab=1" title="Investment From Mainland China">Investment From Mainland China</a></P>
						<P><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=5&tab=1" title="Investment To Mainland Chinas">Investment To Mainland China</a></P>
						<P><span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=9&tab=0" title="Entry Permit for People of Mainland China Area">Entry Permit for People of Mainland China Area</a></P>
				</ul>
			</div>
        </div>
        </div>
     </div>     
     <moeaic:footer_en />
</body>
</html>
