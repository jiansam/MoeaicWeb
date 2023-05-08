<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<moeaic:libraries />
	<title>${type_name} - 政府資訊公開 - 經濟部投資審議委員會(INVESTMENT COMMISSION, MOEA)</title>
	<style type="text/css">
		#con br {line-height: 200%; }
	</style>
</head>

<body>
	<moeaic:header selected="opendata" />
    <moeaic:slide category="g"/>
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:openData />
                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">${type_name}</h3>
	        	</div>
	        	
				<table class="table table-striped table-hover" id="newsOne" style="width:100%;font-size:18px;">
					<tbody id="rows">
						<tr>
					  		<td nowrap="nowrap" width="100px">發佈主旨</td>
					  		<td>${newsOne.ch_title}</td></tr>
						<tr>
					  		<td>發佈時間</td>
					  		<td>${newsOne.publish_date_AD}</td></tr>
					  	
					  	<tr><td>發佈內容</td>
					  		<td id="con">${newsOne.ch_content}</td></tr>			
						
						<c:if test="${not empty files}">
						<tr>
							<td>檔案下載</td>
							<td >
							   <c:forEach var="data" items="${files}">
								<div style="margin-bottom:10px;">
									<c:url value="/download-file.jsp" var="down" scope="page">
										<c:param name="id" value="${data.id_encrypt}" />
										<c:param name="do" value="OD" />
									</c:url>		                        		
									<div style="display: inline-block;width: 20px;height:20px;vertical-align:middle;background-image: url('/images/icon_download_s.png')"></div>
									<a href="${down}" title="[另開新視窗下載]${data.file_name}" target="_blank"><span>${data.file_name}</span></a>
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
     
     <moeaic:footer />
</body>
</html>
