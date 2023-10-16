<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<moeaic:libraries />
	<title>政府資訊公開 - 經濟部投資審議司(Department of Investment Review, MOEA)</title>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			$('.datalist').DataTable({
				"responsive" : true,
				"columnDefs" : [ { "targets" : 0, "orderable" : false ,} ],
				"orderClasses" : false,
				"order" : [],
				"searching" : false,  //不顯示上方搜尋框
				"pageLength" : 5,
				"lengthChange" : false, 
				"drawCallback" : function( settings ) {
					$("table.datalist thead th").each(function(index){
						$(this).removeAttr("rowspan");
						$(this).removeAttr("colspan");
					});
					$('.paginate_button').each(function(){
						var text = $(this).text();
						if(/\d+/.test(text)){
							text = "第" + text + "頁";	
						}
						
						$(this).attr('title', text);
					});
				}
			});

		});
	</script>
	
	<style type="text/css">
		@media screen and (min-width: 992px) {
			#divRightContent {
				padding-left:40px;
			}
			
			#pageTitle {
				padding-left:180px;
			}
		}
	</style>
</head>

<body>
	<moeaic:header selected="opendata" />
    <moeaic:slide category="g" />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container" >
        	
	        	<div id="#divRightContent" style="width:100%;">
	            	<div id="divTitleBorder" >
	            		<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    				<h3 class="p" id="pageTitle">政府資訊公開</h3>
	        		</div>
	        		
	        		<div style="width:100%;">
	            		<div style="text-align: center;">
		  				<table class="table table-striped table-bordered table-hover" style="font-size:18px;">
		  					<thead>
			  					<tr>
			  						<th scope="col" style="width:30%;text-align:center;">項目</th>
			  						<th scope="col" style="text-align:center;">內容</th>
			  					</tr>
		  					</thead>
	
							<tbody>	
				  				<c:forEach var="map" items="${optionManager.openData_type}">
				  				<tr>
				  				
			  						<th scope="row" id="op${map.id}" style="text-align:left;">${map.name}</th>
			  						<td style="text-align:left;">
			  							<c:choose>
			  							
				  							<c:when test="${fn:length(list_map[map.name]) == 1}">
				  							
				  								<c:forEach var="data" items="${list_map[map.name]}">
													<c:choose>
														<c:when test="${empty data.ch_title}">
															${data.ch_content}
														</c:when>
														
														<c:otherwise>
															<c:url value="/openData.view" var="news" scope="page">
																<c:param name="do"  value="news"/>
																<c:param name="id"  value="${data.id}"/> 															
																<c:param name="type"  value="${data.type}"/> 															
															</c:url>
															<a href="${news}" title="${data.ch_title}">${data.ch_title}</a>
														</c:otherwise>
													</c:choose>
												</c:forEach>		
												
				  							</c:when>
				  							
				  							<c:when test="${empty list_map[map.name]}">
												<span>資料建置中</span>												
				  							</c:when>
				  							
				  							<c:otherwise>
				  								<table class="table table-striped table-bordered table-hover datalist"  style="width:100%;">
													<thead style="display: none;">
														<tr style="background:#F4F3FB;">
														    <th>標題</th>
														</tr>
													</thead>
													
													<tbody>			  									
													    <c:forEach var="data" items="${list_map[map.name]}">
														<tr>
															<c:url value="/openData.view" var="news" scope="page">
																<c:param name="do"  value="news"/>
																<c:param name="id"  value="${data.id}"/> 	
																<c:param name="type"  value="${data.type}"/> 														
															</c:url>
															<td><a href="${news}" title="${data.ch_title}"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span> ${data.ch_title}</a></td>
														</tr>
								  						</c:forEach>
													</tbody>	    	
				  								</table>
				  							</c:otherwise>
			  							</c:choose>
			  						</td>
			  						
				  				</tr>
				  				</c:forEach>
			  				</tbody>
			  			</table>
			  			</div>
	        		</div>
	        	</div>
        	</div>
        </div>
     </div>
     
     <moeaic:footer />
</body>
</html>
