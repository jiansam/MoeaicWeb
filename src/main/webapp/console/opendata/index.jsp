<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var op_id_one = "${title.op_id_one}";   
		$(document).ready(function() {
			//console.log("AAA")
	        $('#openData_List').DataTable({
	            responsive: true,
	            columnDefs: [{"orderable": false, "targets": [-1]}],
	            //order : [[1, 'desc']]
	        });
			setDialog("#order-dialog", 400, true);
		});	
	</script>
	<script src="${pageContext.request.contextPath}/js/console/opendata.js"></script>
</head>
<body>
<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>${title.op_id_oneName}</b></h2>
                </div>
            </div>
            
            <div class="row">
				<div class="col-lg-12" >
					<c:url value="/console/edit-pages.view" var="add" scope="page">
						<c:param name="doThing"  value="newOpenData_One"/>
						<c:param name="id"  value="0"/> 
						<c:param name="op_id_one"  value="${title.op_id_one}"/>
					</c:url>				
					<a href="${add}">
						<input type="button" value="新增公告項目" class="btn btn-primary" 
							style="float:right;margin-bottom:5px;"/>
					</a>
				</div>
			</div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">公告列表</div>
                        
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="openData_List" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th width="7%">順序</th>
                                            <th width="7%">置頂</th>
                                            <th width="9%">更新日期</th>
                                            <th>標題</th>
                                            <th width="1%" nowrap="nowrap">編輯</th>
                                        </tr>
                                    </thead>
                                    <tbody id="rows">
                                    <c:forEach var="data" items="${openData_List}">
										<tr>
											<td>${data.seq}</td>
											<td>${data.ontop == true ? '是' : '否'}</td>
											<td>${data.publish_date_AD}</td>
											<td>${data.ch_title}</td>
											<td nowrap="nowrap">
												<input type="hidden" name="id" value="${data.id}" class="form-control" size="1">
												<a href="#">
													<img src="${pageContext.request.contextPath}/images/icons/sort.png" width="24"
														onclick="setOrder(this)"></a>
														
												<c:url value="/console/edit-pages.view" var="edit" scope="page">
													<c:param name="doThing"  value="getOpenData_One"/>
													<c:param name="id"  value="${data.id}"/>
													<c:param name="op_id_one"  value="${title.op_id_one}"/>
												</c:url>													
												<a href="${edit}">
													<img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"  
														></a>
												<c:url value="/console/edit-pages.view" var="remo" scope="page">
													<c:param name="id"  value="${data.id}"/>
													<c:param name="doThing"  value="removeOpenData_One"/>
													<c:param name="op_id_one"  value="${title.op_id_one}"/>
												</c:url>												
												<a href="${remo}">
													<img src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24" ></a>
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
            <!-- /.row -->
        </div>
    </div>
    <div id="order-dialog" title="顯示順序"></div>    
<console:footer />    
</body>
</html>