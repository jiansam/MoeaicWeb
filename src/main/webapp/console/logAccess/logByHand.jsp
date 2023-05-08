<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<script type="text/javascript">
		var servletURL = "${pageContext.request.contextPath}/console/logAccessByHand.view";
		$(document).ready(function() {
		});
		
		function ajax(form) {
			$.ajax({
				url : servletURL,
				data : $(form).serialize(),
				method : 'POST',
				cache : false, // IE不要cache （預設是true）
				async : true, // 是否採用非同步（預設是true）
				contentType : "application/x-www-form-urlencoded",
				success : function(response) {
					alert(response);
					return;
				},
			});
		}			
	</script>
	
	<title>經濟部投資審議委員會 - 手動定義log起始日</title>
</head>
<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>手動啟動_logAccess分析起始日</b></h2>
                </div>
            </div>

			<!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                    <div class="panel panel-default">
                        <div class="panel-body" >
                            <div class="table-responsive">
                            	<form id="logbyhand">
	                                <table class="table table-hover" style="width:100%;">
	                                    <tbody id="rows">
	                                    	<tr>
	                                    		<td width="15%">開始日</td>
	                                    		<td><input type="text" name="startDate" value=""></td></tr>
	                                    	<tr>
	                                    		<td width="15%">結束日</td>
	                                    		<td><input type="text" name="endDate" value=""></td></tr>
	                                    	<tr>
	                                    		<td colspan="2">Log分析，<b style="color:red;">是擷取開始日"隔天"，到結束日"前一天"</b>，若未填結束日則視為今天<br>（格式：yyyy/MM/dd）</td></tr>
	                                    	<tr>
	                                    		<td colspan="2"><input type="button" value="送出" onclick="ajax(this.form)" height="30px"></td></tr>
	                                    	
	                                    </tbody>
	                                </table>
                                </form>
                            </div><!-- /.table-responsive -->
                        </div><!-- /.panel-body -->
                    </div><!-- /.panel -->                    
                </div><!-- /.col-lg-12 -->
            </div><!-- /.row -->           
            
            		            
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
<console:footer />      
</body>
</html>