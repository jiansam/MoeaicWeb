<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
<moeaic:libraries />
<title>${title_ch} - 聯絡我們 - 經濟部投資審議司(INVESTMENT COMMISSION, MOEA)</title>
</head>

<body>
	<moeaic:header />
    
    <moeaic:slide />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:contactUs />
                
            <div id="divRightContent" style="width:100%;font-size:18px;word-break: break-all;">
            	<div id="divTitleBorder">
            		<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
    				<h3 class="p" id="pageTitle">${title_ch}</h3>
        		</div>
        		${pagesOne.page_content}
        	</div>
        </div>
        </div>
     </div>
     
     <moeaic:footer />
</body>
</html>
