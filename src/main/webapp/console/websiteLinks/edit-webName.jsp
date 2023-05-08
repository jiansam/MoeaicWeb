<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		$(document).ready(function() {
			setDialog("#dialog-webOrder", 400, true);
			$("#tabs").tabs({
				width : $("#tabs").parent().width(), 
			});
		});
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/sort-common.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>資料管理</b></h2>
                </div>
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    
                        <div class="panel-heading">
							網站資料列表
                        </div>
                        <!-- /.panel-heading --> 
                        
                        <div class="panel-body" id="tabs">
                        
							<ul class="tabLi">
								<li><a href="#chDiv">中文版</a></li>
								<li><a href="#enDiv">英文版</a></li>
							</ul>                        	
                        
                            <div class="table-responsive" id="chDiv">
				 				<div>
									<a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=newWebName&lang=ch"
										class="btn btn-primary" style="float:right;margin:5px;color:white;">新增網站資料</a>
								</div>                   
								
                                <table class="table table-bordered table-hover" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th width="10%" nowrap="nowrap">分類/順序</th>
                                            <th width="12%">更新日期</th>
                                            <th width="8%" nowrap="nowrap">首頁顯示</th>
                                            <th>標題</th>
                                            <th width="8%" >編輯</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="parent" items="${title}">
                                    	<tr>
                                    		<td colspan="5" style="background:#EFF5FB;" nowrap="nowrap"><b>${parent.ch_title}</b></td>
                                    	</tr>
                                    	<c:forEach var="data" items="${list[parent.ch_title]}">
                                        <tr>
                                            <td class="sort" >${data.sort}</td>
                                            <td class="update_date">${data.update_date_AD}</td>
                                            <td class="showIndex">${data.showIndex == true ? "是" : "否"}</td>
                                            <td class="ch_name">${data.name}</td>
                                            <td class="id" nowrap="nowrap" align="center">
                                            	<input type="hidden" name="webOrder_id" value="${data.id}" class="form-control">
                                            	<input type="hidden" name="webTitle_id" value="${data.parent_id}" class="form-control">
                                            	<input type="hidden" name="webName_id" value="${data.webName_id}" class="form-control">
                                            	<input type="hidden" name="web_titles" value="${data.web_titles}" class="form-control">
                                            	
												<a href="#web_TitleList">
													<img src="${pageContext.request.contextPath}/images/icons/sort.png" width="24"
														onclick="list_sort(this)"></a>
												
												<c:url value="/console/edit-pages.view" var="sel" scope="page">
													<c:param name="webName_id"  value="${data.webName_id}"/>
													<c:param name="doThing"  value="selWebName"/>
												</c:url>													
												<a href="${sel}"><img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>
													
												<a href="#web_TitleList" style="display:none;">
													<img src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"
														onclick="list_dele(this)"></a>
											</td>
                                        </tr>
                                        </c:forEach>
                                    </c:forEach>                                   
                                    </tbody>
                                    
                                </table>
                            </div>
                            <!-- /.table-responsive -->
							
							<!-- *************************************************************** -->
                            
                            
                            <div class="table-responsive" id="enDiv">
				 				<div>
									<a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=newWebName&lang=en"
										class="btn btn-primary" style="float:right;margin-bottom:5px;color:white;">新增網站資料</a>
								</div>                   
								
                                <table class="table table-bordered table-hover" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th width="10%" nowrap="nowrap">分類/順序</th>
                                            <th width="12%">更新日期</th>
                                            <th width="8%" nowrap="nowrap">首頁顯示</th>
                                            <th>標題</th>
                                            <th width="8%" >編輯</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="parent" items="${title}">
                                    	<tr>
                                    		<td colspan="5" style="background:#EFF5FB;" nowrap="nowrap"><b>${parent.en_title}</b></td>
                                    	</tr>
                                    	<c:forEach var="data" items="${list[parent.en_title]}">
                                        <tr>
                                            <td class="sort" >${data.sort}</td>
                                            <td class="update_date">${data.update_date_ROC}</td>
                                            <td class="showIndex">${data.showIndex == true ? "是" : "否"}</td>
                                            <td class="ch_name">${data.name}</td>
                                            <td class="id" nowrap="nowrap" align="center">
                                            	<input type="hidden" name="webOrder_id" value="${data.id}" class="form-control">
                                            	<input type="hidden" name="webTitle_id" value="${data.parent_id}" class="form-control">
                                            	<input type="hidden" name="webName_id" value="${data.webName_id}" class="form-control">
                                            	<input type="hidden" name="web_titles" value="${data.web_titles}" class="form-control">
                                            	
												<a href="#web_TitleList">
													<img src="${pageContext.request.contextPath}/images/icons/sort.png" width="24"
														onclick="list_sort(this)"></a>
												
												<c:url value="/console/edit-pages.view" var="sel" scope="page">
													<c:param name="webName_id"  value="${data.webName_id}"/>
													<c:param name="doThing"  value="selWebName"/>
												</c:url>													
												<a href="${sel}"><img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>
													
												<a href="#web_TitleList" style="display:none;">
													<img src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"
														onclick="list_dele(this)"></a>
											</td>
                                        </tr>
                                        </c:forEach>
                                    </c:forEach>                                   
                                    </tbody>
                                    
                                </table>                            
                            </div>
                            
                            
                            
                            
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            
        </div>
    </div>
<div id="dialog-webOrder" title="顯示順序"></div> 
<console:footer />     
</body>
</html>