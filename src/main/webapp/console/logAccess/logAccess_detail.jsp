<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<script type="text/javascript">
		var servletURL = "${pageContext.request.contextPath}/console/logAccess.view";
		$(document).ready(function() {
		    $('#ListTable').DataTable({
				"pageLength" : 100,
				"lengthMenu" : [[100, 250, 500, -1], [100, 250, 500, "全部"]],
				"order": [0,'asc']
		    });
		});
		
	</script>
	
	<title>經濟部投資審議司 - 點擊率管理</title>
</head>
<body>
	<div id="wrapper">
		<console:navigation />
        <div id="page-wrapper">
			<div class="row">
			    <div class="col-lg-12">
			        <h2 class="page-header"><b>點擊率管理</b></h2>
			    </div>
			</div>
			
            <div class="row" >
                <div class="col-lg-12">
					<table id="ListTable" style="width:100%;" class="table table-hover">
						<thead>
							<tr>
								<th width="12%">序號</th>
								<th width="20%">IP位址</th>
								<th>點擊數</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="data" items="${list}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${data.logIP}</td>
								<td>${data.logCount}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table><br/>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
<console:footer />      
</body>
</html>