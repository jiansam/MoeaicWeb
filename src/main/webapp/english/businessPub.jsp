<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
	<moeaic:libraries_en />
	
	<title>INVESTMENT COMMISSION, MOEA - ${title_en}</title>
	<script type="text/javascript">
		var isShowFlow = "${isShowFlow}";
	</script>
	<script src="${pageContext.request.contextPath}/js/front-end/businessPub.js"></script>

<style>
.hover-red:hover{
	color: red;
}

.ui-accordion, .qs{
	padding: 5px;
}

.ui-accordion{
	padding: 5px;
}

.ui-widget{
	font-family: \5FAE\8EDF\6B63\9ED1\9AD4, Arial;
	font-size: 16px;
}

.ui-helper-reset{
	line-height: 32px;
}

.ipstyle {
  height: 34px;
  padding: 6px 12px;
  font-size: 16px;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #AAAAAA; /*#ccc*/
  border-radius: 4px;
}

label {
	font-weight: normal;
}
</style>
</head>

<body>
	<moeaic:header_en selected="business" />
	<moeaic:slide category="h"/>

	<div id="divContent">
		<div id="divWhiteBack">
			<div class="container" style="min-height: 800px;">
				<moeaic:businessPub_en />

				<div id="divRightContent" style="width: 100%;">
					<div id="divTitleBorder">
						<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
						<h3 class="p" id="pageTitle">${title_en}</h3>
					</div>

					<c:if test="${not empty flow_chart}">
						<div id="choicediv" style="line-height:22px!important;padding-bottom:14px;text-align:right;">
							<a href="javascript:void(0)" onclick="openDiv('file_div')" onkeypress="openDiv('file_div')" title="open application file">
								<img alt="application file" src="${pageContext.request.contextPath}/images/icons/file.jpg" width="32"></a>
							<a href="javascript:void(0)" onclick="openDiv('flow_div')" onkeypress="openDiv('flow_div')" title="open flow chart">
								<img alt="flow chart" src="${pageContext.request.contextPath}/images/icons/flow.jpg" width="32"></a>
						</div>
					</c:if>

					<div class="flow_div" style="display:none">${flow_chart}</div>
					<div class="file_div" style="color:gray;line-height:16px!important;padding-bottom:5px;">Please use tab key to choose one of page bookmarks, and then press enter key to show the bookmark's contents.</div>
					<div class="file_div" >
						<div id="horizontalTab">

							<ul class="resp-tabs-list">
								<c:forEach var="name" items="${list_name}">
									<li style="font-size:18px;" tabindex='0'>${name}</li>
								</c:forEach>
							</ul>


							<div class="resp-tabs-container">
								<c:forEach var="beans" items="${lists}">
								
									<c:choose>
									
									<c:when test="${beans[0].type eq '14'}">
									<div id="QS" class="qs">
									
										<div class="qs" style="font-size:18px;text-align:left;display:inline-block;width:100%;" >
											<c:if test="${not empty QA_type}">
												<div id="bp_sel">
													<label for="chooseQA">Category:</label>
													<select id="chooseQA" style="height:36px;border-radius:5px;width:186px;margin-bottom:5px;">
														<c:forEach var="option" items="${QA_type}">
															<option value="${option.id}">${option.ename}</option>
														</c:forEach>
													</select>
													<input type="image" alt="choose type" src="${pageContext.request.contextPath}/images/searchbutton.png" title="Select a category」"
														style="height: 36px;vertical-align:bottom;margin-bottom:5px;" onclick="showQA()" onkeypress="showQA()" />
													
												</div>											
											</c:if>
											
											<div id="bp_key" >
												<label for="query">Keyword:</label>
												<input type="text" name="query" id="query" title="keywordSerach" class="ipstyle" style="width:186px;margin-left:3px;margin-bottom:5px;" />
												<input type="image" alt='search keywords' src="${pageContext.request.contextPath}/images/searchbutton.png" title="Search keywords"
													style="height: 36px;vertical-align: bottom;margin-bottom:5px;" onclick="search()" onkeypress="search()" />
											</div>

										</div>
										<div style="color:gray;margin-left:5px;line-height:16px !important;">Please use arrow key, like up or down, to choose one question, and then press enter key to show or close the answer.</div>
										<div id="faq_accordion" style="font-size:18px;">
											<c:forEach var="data" items="${beans}">
												<h3 class="Q${data.id}" keyword="${data.en_QA_type}">${data.en_title}</h3>
												<div class="Q${data.id}" keyword="${data.en_QA_type}">${data.en_content}</div>
											</c:forEach>
										</div>
									</div>								
									</c:when>
									
									<c:otherwise>
									<div>
										<table style="width: 100%;font-size:18px;word-wrap: break-word;word-break: break-all;">
										

											<thead>
												<tr>
													<th id="hd0${beans[0].type}" width="1%" nowrap="nowrap" valign="top"></th>
													<th id="hd1${beans[0].type}" colspan="1">Content</th>
													<th id="hd2${beans[0].type}" style="width: 20%;text-align: left">&nbsp;&nbsp;File</th>
												</tr>
											</thead>
										
											<tbody>
												<c:forEach var="data" items="${beans}">
												<tr>
													<td headers="hd0${beans[0].type}" width="1%" nowrap="nowrap" valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
													<td headers="hd1${beans[0].type}" valign="top">${data.en_title}<br><span style="color:#084B8A;">${data.en_content}</span></td>
													
													<td headers="hd2${beans[0].type}" align="left" valign="top" style="padding-left: 5px;">      
																				   
																				   
														<c:forEach var="file" items="${files[data.businessPub_id_str]}">
															<div style="display: inline-block;width: 20px;height:20px;vertical-align:middle;background-image: url('/images/icon_download_s.png')"></div>
															<a href="${pageContext.request.contextPath}/download-file.jsp?do=BP&id=${file.id_encrypt}" class="hover-red" title="[Download in a new window]${data.en_title}${file.file_name_str}">${file.file_name}</a><br>
												 		</c:forEach>
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									</c:otherwise>
									
									</c:choose>
								</c:forEach>
							</div>


						</div>
					</div>

					<br>
										
				    <div style="width:100%;float:right;margin-bottom:20px;">
				    	<a href="#top" tabindex="0"><img alt="Back to Top" title="Back to Top" border="0" src="/images/upload/1b41b423-7181-4801-88a7-44ddcdb33f72.png" style="float: right;" width="32" /></a>
				    </div>					
					
				</div>
			</div>
		</div>
	</div>

	<moeaic:footer_en />
</body>
</html>
