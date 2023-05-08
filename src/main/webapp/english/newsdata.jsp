<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
	<moeaic:libraries_en />
	<title>INVESTMENT COMMISSION, MOEA - ${title_en}</title>
</head>

<body>
	<moeaic:header_en selected="news"/>
	<moeaic:slide category="e"/>    
    
    <div id="divContent">
    	
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:news_en />
                
            <div id="divRightContent" style="width:100%;">
		        <div id="divTitleBorder">
		        	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
		    		<h3 class="p" id="pageTitle">${title_en}</h3>
		        </div>
				<table class="table table-striped table-hover" id="newsOne" style="width:100%;font-size:18px;">
					<tbody id="rows">
						<tr>
					  		<th nowrap="nowrap" width="100px">Subject</th>
					  		<td>${newsOne.en_title}</td></tr>
						<tr>
					  		<th>Date</th>
					  		<td>${newsOne.publish_date_AD}</td></tr>
					  	
					  	<tr>
					  		<th>Content</th>
					  		<td style="word-break: break-all;">${newsOne.en_content}</td>
					  	</tr>			

						<c:if test="${not empty files_en}">
						<tr>
							<th>Download</th>
							<td >
							   <c:forEach var="data" items="${files_en}">
								<div style="margin-bottom:10px;">
									<c:url value="/download-file.jsp" var="down" scope="page">
										<c:param name="id" value="${data.id_str}" />
									</c:url>
									<div style="display: inline-block;width: 20px;height:20px;vertical-align:middle;background-image: url('/images/icon_download_s.png')"></div>
									<a href="${down}" title="[]Download in a new window]${data.file_name}" target="_blank"><span>${data.file_name}</span></a>										                        		
								</div>						
							   </c:forEach>										
							</td>
						</tr>
						</c:if>						  		
				    </tbody>
				</table>
				<br>
            </div>
        </div>
        </div>
     </div>
     
     <moeaic:footer_en />
</body>
</html>
