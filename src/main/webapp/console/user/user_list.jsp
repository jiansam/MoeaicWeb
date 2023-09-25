<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		$(document).ready(function() {
		    $('#user_list').DataTable({
		        responsive: true
		    });

		});
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/userManager.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>使用者管理</b></h2>
                </div>
            </div>
            

            <div class="row">
				<div class="col-lg-12" >
					<c:url value="/console/edit-pages.view" var="add" scope="page">
						<c:param name="doThing"  value="new_User"/>
						<c:param name="id"  value="0"/>
					</c:url>	
					<a href="${add}">
						<input type="button" value="新增使用者" class="btn btn-primary" 
							style="float:right;margin-bottom:5px;"/>
					</a>
				</div>
			</div>
			            

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" 
                                	id="user_list" style="width:100%;">
                                
                                	<thead>
                                		<tr>
                                			<th>帳號</th>
                                			<th>姓名</th>
                                			<th>單位</th>
                                			<th>網站權限</th>
                                			<th>帳號停權</th>
                                			<th></th>
                                		</tr>
                                	</thead>
                                	
                                    <tbody id="rows">
                                    <c:forEach var="data" items="${userList}">
                                    	<tr>
                                    		<td>${data.account}</td>
                                    		<td>${data.name}</td>
                                    		<td>${data.department}</td>
                                    		<td>${data.admin==false?"一般使用者":"管理員"}</td>
                                    		<td>${data.disable==false?"否":"是"}</td>
                                    		<td>
												<c:url value="/console/edit-pages.view" var="edit" scope="page">
													<c:param name="doThing"  value="getUser_One"/>
													<c:param name="id"  value="${data.id}"/>
												</c:url>													
												<a href="${edit}"><img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>                                    		
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
<console:footer />      
</body>
</html>