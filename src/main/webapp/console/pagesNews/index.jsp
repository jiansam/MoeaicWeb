<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<title>${title.typeheader}</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var type = "${title.type}";  // -- 每頁必須修改處  --   
		//console.log("type="+type)
		$(document).ready(function() {
	        $('#pagesNewsList').DataTable({
	            responsive: true
	        });
 			setDialog("#order-dialog", 400, true);
		});
		</script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pagesNews-common.js"></script>  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pagesNews-common.js?t=20210818"></script> <!--20210818修改-->

</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>${title.typeName}</b></h2>
                </div>
            </div>
            
            
            <div class="row">
				<div class="col-lg-12" >
					<c:url value="/console/edit-pages.view" var="add" scope="page">
						<c:param name="doThing"  value="newPagesNews_One"/>
						<c:param name="id"  value="0"/> 
						<c:param name="type"  value="${title.type}"/>   <!-- 每頁必須修改處    -->
					</c:url>				
					<a href="${add}">
						<input type="button" value="新增${title.typeName}" class="btn btn-primary" 
							style="float:right;margin-bottom:5px;"/>
					</a>
				</div>
			</div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
							${title.typeName}列表
                        </div>
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="pagesNewsList" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th>順序</th>
                                            <th>置頂</th>
                                            <th>日期</th>
                                            <th>中文標題</th>
                                            <th>英文標題</th>
                                            <th width="8%" >編輯</th>
                                        </tr>
                                    </thead>
                                    
                                    <tbody id="rows">
                                   	<c:forEach var="data" items="${pagesNews_list}">
                                    	<tr>
                                    		<td>${data.sort}</td>
                                    		<td>${data.showTop == true ? "是" : "否"}</td>
                                    		<td>${data.publish_date_AD}</td>
                                    		<td>${data.ch_title}</td>
                                    		<td>${data.en_title}</td>
                                    		<td>
	                                           	<input type="hidden" name="id" value="${data.id}" class="form-control" size="1">
													<a href="#">
														<img src="${pageContext.request.contextPath}/images/icons/sort.png" width="24"
															onclick="setTopOrder(this)"></a>
															
													<c:url value="/console/edit-pages.view" var="edit" scope="page">
														<c:param name="doThing"  value="getPagesNews_One"/>
														<c:param name="id"  value="${data.id}"/>
													</c:url>													
													<a href="${edit}">
														<img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"  
															></a>
													<a href="#">
														<img src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"  
															onclick="dele_pagesNews(this)"></a>
                                    		</td>
                                    	</tr>
                                   	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            
        </div>
    </div>
<div id="order-dialog" title="顯示順序"></div>    
<console:footer />  
</body>
</html>