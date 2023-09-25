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
				"pageLength" : 10,
				"lengthMenu" : [[10, 25, 50, -1], [10, 25, 50, "全部"]],
				"order": [0,'asc']
		    });
		    
		    $(document).on("click",".showOneCountry",function() {
		    //$(".showOneCountry").on("click", function(){
				var values = {
						"doThing":"oneCountryData",
						"page":$(this).data("page"),
						"type":$(this).data("type"),
						"country":$(this).data("country")
				}
// 				console.log(values)
				var url = "${pageContext.request.contextPath}/console/logAccess.view";
				postUrlByForm(url, values);
			})
			
			function postUrlByForm(url, values){
				var $form=$("<form></form>",{"method":"post","action":url});
				var params="";
				$.each(values,function(key,value){
					params+="<input type='hidden' value='"+value+"' name='"+key+"' />";
				});
				$form.append(params);
				$('body').append($form);
				$form.submit();
			}	
		});
		
	</script>
	
	<style>
		.showOneCountry{
			cursor:pointer;
		}
	</style>
	<title>經濟部投資審議司 - 點擊率管理</title>
</head>
<body>
	<div id="wrapper">
		<console:navigation />
        <div id="page-wrapper">
			<div class="row">
			    <div class="col-lg-12">
			        <h2 class="page-header"><b>點擊率管理-${pageName}</b></h2>
			    </div>
			</div>
			
            <div class="row">
                <div class="col-lg-12">
					<table id="ListTable" style="width:100%;" class="table table-hover">
						<thead>
							<tr>
								<th width="12%">序號</th>
								<th width="20%">國家</th>
								<th>點擊數</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="data" items="${list}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${data.ipCountry_ch}</td>
								<td><a class='showOneCountry' data-page='${data.logPage}' data-type='${data.logPage_type}' data-country="${data.ipCountry}">
										${data.logCount}</a></td>
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