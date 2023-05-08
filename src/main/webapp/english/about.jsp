<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
<moeaic:libraries_en />
<title>INVESTMENT COMMISSION, MOEA - ${title_en}</title>
</head>

<body>
	<moeaic:header_en selected="about" />
    <moeaic:slide />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:about_en />
                
            <div id="divRightContent" style="width:100%;font-size:18px;">
            	<div id="divTitleBorder">
            		<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
    				<h2 class="p" id="pageTitle">${title_en}</h2>
        		</div>
        		${pagesOne.page_content}
        	</div>
        </div>
        </div>
     </div>
     
     <moeaic:footer_en />
</body>
</html>
