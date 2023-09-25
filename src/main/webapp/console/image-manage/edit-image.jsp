<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.dasin.tools.*" %>
<%@ page import="java.io.*" %>
<%@ page import="gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	
	<link href="${pageContext.request.contextPath}/jquery-loadmask-0.4/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-loadmask-0.4/jquery.loadmask.js"></script>
	<script type="text/javascript">
		function confirm_upload(){
			if(confirm('確認送出圖檔資料？')){
				$('#upload_div').mask('上傳處理中，請稍候...');
				return true;
			}
			
			return false;
		}
	</script>
</head>
<%
	Images image = ImagesDAO.get(dTools.parseInt(request.getParameter("id")));
%>
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
                <div class="col-lg-10 col-lg-offset-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">編輯圖檔資料
                        	<a href="index.jsp" style="position: absolute;top: 5px;right:30px;" class="btn btn-primary">返回總表</a>
                        </div>
                        
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12" id="upload_div">
                                    <form role="form" action="edit-image-2.jsp" method="post" enctype="multipart/form-data"
                                    	onsubmit="return confirm_upload();">
                                        <div class="form-group">
                                            <label>圖片說明：</label>
                                            <input class="form-control" placeholder="請輸入圖片說明" name="description" 
                                            	value="<%= dTools.trim(image.getDescription()) %>"/>
                                            <input type="hidden" name="id" value="<%= image.getId() %>" />
                                        </div>
                                        
<%
	String filepath = dTools.trim(image.getFilepath());
	if(filepath.length() > 1 &&
		new File(application.getRealPath(filepath)).exists()){
%>
                                        <div class="form-group">
                                        	<label>檔案預覽：</label>
                                            <img src="<%= request.getContextPath() + image.getFilepath() %>" width="120" />
                                        </div>
                                        
                                        <div class="form-group">
                                        	<label>連結路徑：</label>
<%--                                             http://<%= request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + image.getFilepath() %> --%>
                                            http://<%= request.getServerName() + request.getContextPath() + image.getFilepath() %>
                                        </div>
<%
	}
%>
                                        <div class="form-group">
                                            <label>上傳檔案：</label>
                                            <input type="file" name="imagefile" accept="image/*" />
                                        </div>

                                        <div align="center">
                                        	<button type="submit" class="btn btn-primary">送出圖檔資料</button>
                                        </div>
                                    </form>
                                </div>
                                
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
<console:footer />    
</body>
</html>