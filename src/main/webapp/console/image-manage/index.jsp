<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%@ page import="org.dasin.tools.*" %>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script>
    $(document).ready(function() {
        $('#images-table').DataTable({
            responsive: true,
            "orderClasses" : false,
            "columnDefs": [{ "targets": -1, "orderable": false }, { "targets": -2, "orderable": false }, { "targets": -3, "orderable": false }],
            "order" : [[1, 'desc']],
        
        });
    });
    </script>
</head>
<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">圖檔管理</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">已上傳圖檔清單
                        	<a href="edit-image.jsp" style="position: absolute;top: 5px;right:30px;" class="btn btn-primary">新增圖檔</a>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table style="width:100%;" class="table table-striped table-bordered table-hover" id="images-table">
                                <thead>
                                    <tr>
                                        <th>圖片說明</th>
                                        <th>更新日期</th>
                                        <th>複製連結</th>
                                        <th>檔案預覽</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
<%
	for(Images image : ImagesDAO.list()){
		String image_url = request.getScheme() + "://" + request.getServerName() 
			//+ ":" + request.getServerPort() 
			+ request.getContextPath() + image.getFilepath();
%>
									<tr>
										<td><%= image.getDescription() %></td>
										<td><%= image.getUploadtime().split(" ")[0] %></td>
										<td><%= dTools.isEmpty(image.getFilepath()) ? "檔案上傳未成功，請檢查檔案格式及內容後重新上傳，謝謝！" : image_url %></td>
										<td>
<%
	if(!dTools.isEmpty(image.getFilepath())){
%>
											<img src="<%= image_url %>" width="120">
<%
	}
%>
										</td>
										<td>
											<a href="edit-image.jsp?id=<%= image.getId() %>"><img title="編輯圖片" 
												src="${pageContext.request.contextPath}/images/icons/edit.png" width="24" /></a>
											<a href="delete-image.jsp?id=<%= image.getId() %>" onclick="return confirm('確認刪除此圖檔？')"
												><img title="編輯圖片" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24" /></a>
										</td>
									</tr>
<%
	}
%>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
<console:footer />    
</body>
</html>