<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<moeaic:libraries />
	<title>${title_ch} - 申辦業務 - 經濟部投資審議司(INVESTMENT COMMISSION, MOEA)</title>
	<style type="text/css">
		#con br {line-height: 200%; }
		
		#con .mimg img{
			width:100%;
		}
		@media (min-width: 992px) {
			#con{
				display:flex;
			}
			#con .mimg{
				flex: 0 0 30%;
				max-width:200px;
				padding-left:24px;
			}
		}
	</style>
</head>

<body>
	<moeaic:header selected="news" />
    <moeaic:slide category="e"/>
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:news />
                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">${title_ch}</h3>
	        	</div>
	        	
				<table class="table table-striped table-hover" id="newsOne" style="width:100%;font-size:18px;">
					<tbody id="rows">
						<tr>
					  		<th nowrap="nowrap" width="100px">發佈主旨</th>
					  		<td>${newsOne.ch_title}</td></tr>
						<tr>
					  		<th>發佈時間</th>
					  		<td>${newsOne.publish_date_AD}</td></tr>
					  	
					  	<tr>
					  		<th>發佈內容</th>
					  		<td id="con">
					  			<div>${newsOne.ch_content}</div>
					  			
					  			<!-- RSS 圖片 -->
					  			<div class="mimg">
					  				<c:choose>
									  
									   <c:when test="${newsOne.image_type_ch == '0'}"> 
											<img alt="${newsOne.rss_text_ch }" src="${pageContext.request.contextPath}${newsOne.rss_image_ch }">	   		
									   </c:when>
									   
									   <c:otherwise>
									   		<img alt="${newsOne.rss_text_ch }" src="${pageContext.request.contextPath}${newsOne.photo_ch }">
									   </c:otherwise>
									</c:choose>
					  			</div>
					  		</td>
					  	</tr>			
						
						<c:if test="${not empty files_ch}">
						<tr>
							<th>檔案下載</th>
							<td >
							   <c:forEach var="data" items="${files_ch}">
								<div style="margin-bottom:10px;">
									<c:url value="/download-file.jsp" var="down" scope="page">
										<c:param name="id" value="${data.id_str}" />
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
