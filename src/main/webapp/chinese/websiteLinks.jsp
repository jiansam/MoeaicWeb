<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<moeaic:libraries />
	<title>${title_ch} - 經濟部投資審議司(INVESTMENT COMMISSION, MOEA)</title>
	
	<script type="text/javascript">
		$(document).ready(function () {
			$("#weblink").show();
		});
		
		function getWebLinkOne() {
			var id = $("select[name='lintTitle']").val();
			window.location = "${pageContext.request.contextPath}/links.view?do=" + id;
		}
	</script>
</head>

<body>
	<moeaic:header selected="links" />
    <moeaic:slide category="b" />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
			<moeaic:websiteLinks />
			
			<div id="divRightContent" style="width:100%;">
				<div id="divTitleBorder">
					<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">${title_ch}</h3>
	        	</div>
				<label for="lintTitle">分類：</label>
				<select id="lintTitle" name="lintTitle" style="height:40px;border-radius:7px;margin-bottom: 10px;margin-right: 5px;">
					<c:forEach var="parent" items="${linkTitle}" >
						<option value="${parent.id}" ${parent.ch_title == titleOne.ch_title ? 'selected="selected"' : ''} 
							>&nbsp;${parent.ch_title}</option>
					</c:forEach>					
				</select>
				<input type="button" value="送出" title="送出" onclick="getWebLinkOne()" 
					style="height:40px;border-radius:5px;margin-bottom: 10px;width:50px;">
				
				<moeaic:noscript/>
				
				<table id="weblink" class="table table-striped table-bordered table-hover" style="width:100%;font-size:18px;display:none;">
					<tbody>
                       	<tr>
                       		<td style="color:white;background:#245091;" nowrap="nowrap" ><b>${titleOne.ch_title}</b></td>
                       	</tr>
					    
						<tr id="title${titleOne.id}">
							<td>
								<table class="table table-striped table-bordered table-hover" style="width:100%;">
								    <c:forEach var="data" items="${list[titleOne.ch_title]}">
			                        <tr>
										<td class="name" >
											<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span> <a href="${data.url}" target="_blank" title="[另開新視窗]${data.name}">${data.name}</a>
										</td>
									</tr>
			                        </c:forEach>									
								</table>
							</td>							
						</tr>
				    </tbody>
					
				</table>
			    <div style="width:100%;float:right;margin-bottom:20px;">
			    	<a href="#top" tabindex="0"><img alt="回到最上方" title="回到最上方" border="0" src="/images/upload/1b41b423-7181-4801-88a7-44ddcdb33f72.png" style="float: right;" width="32" /></a>
			    </div>
			</div>
        	</div>
        </div>
     </div>
     <moeaic:footer />
</body>
</html>
