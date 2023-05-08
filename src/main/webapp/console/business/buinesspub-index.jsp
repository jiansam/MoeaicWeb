<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
	
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var op_id_one = "${title.op_id_one}";  // -- 每頁必須修改處  --   
		$(document).ready(function() {
	        $('#buinessPubList').DataTable({
	            responsive: true,
	            columnDefs: [
	                         {"orderable": false, "targets": [-1]} ],
	            order : [[4, 'desc'] , [0, 'asc'] , [1, 'desc']],
	            
	            initComplete: function () {
	                this.api().columns().every( function (i) {
	                	if(i==4){
		                    var column = this;
		                    var select = $('<select><option value=""></option></select>')
		                        .appendTo( $(column.footer()).empty() )
		                        .on( 'change', function () {
		                            var val = $.fn.dataTable.util.escapeRegex(
		                                $(this).val()
		                            );
		     
		                            column
		                                .search( val ? '^'+val+'$' : '', true, false )
		                                .draw();
		                        } );
		     
		                    column.data().unique().sort().each( function ( d, j ) {
		                        select.append( '<option value="'+d+'">'+d+'</option>' )
		                    } );
	                	}
	                } );
	            }
	        });
 			setDialog("#order-dialog", 400, true);
		});
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/buinessPub.js"></script>
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
						<c:param name="doThing"  value="newBusinessPub_One"/>
						<c:param name="id"  value="0"/> 
						<c:param name="op_id_one"  value="${title.op_id_one}"/>   <!-- 每頁必須修改處    -->
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
                        <div class="panel-heading">
							${title.op_id_oneName}列表
                        </div>
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="buinessPubList" style="width:100%;">
                                    <thead>
                                        <tr>
                                            <th width="3%">順序</th>
                                            <th>日期</th>
                                            <th width="32%">中文標題</th>
                                            <th width="32%">英文標題</th>
                                            <th nowrap="nowrap">資料類型</th>
                                            <th width="8%">已設定<br>分類</th>
                                            <th width="8%" >編輯</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                    </tfoot>                                    
                                    <tbody id="rows">
                                   	<c:forEach var="data" items="${buinessPub_list}">
                                    	<tr>
                                    		<td>${data.sort}</td>
                                    		<td>${data.publish_date_AD}</td>
                                    		<td>${data.ch_title}</td>
                                    		<td>${data.en_title}</td>
                                    		<td>${data.type_str}</td>
                                    		<td>
                                    			<c:if test="${fn:length(data.ch_QA_type) > 0}">
	                                    			<img src="${pageContext.request.contextPath}/images/icons/ch.png" >
	                                    			<span style="display:none;">c</span>
                                    			</c:if>
                                    			<c:if test="${fn:length(data.en_QA_type) > 0}">
	                                    			<img src="${pageContext.request.contextPath}/images/icons/en.png" >
                                    				<span style="display:none;">e</span>
                                    			</c:if></td>                                    		
                                    		<td nowrap="nowrap">
	                                           	<input type="hidden" name="id" value="${data.id}" class="form-control" size="1">
	                                           	<input type="hidden" name="type" value="${data.type}" class="form-control" size="1">
													<a href="#">
														<img src="${pageContext.request.contextPath}/images/icons/sort.png" width="24"
															onclick="setOrder(this)"></a>
															
													<c:url value="/console/edit-pages.view" var="edit" scope="page">
														<c:param name="doThing"  value="getBusinessPub_One"/>
														<c:param name="id"  value="${data.businessPub_id}"/>
														<c:param name="op_id_one"  value="${title.op_id_one}"/>
													</c:url>													
													<a href="${edit}">
														<img src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"  
															></a>
													<c:url value="/console/edit-pages.view" var="remo" scope="page">
														<c:param name="id"  value="${data.businessPub_id}"/>
														<c:param name="doThing"  value="removeBusinessPub_One_btn"/>
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
            
            
        </div>
    </div>
<div id="order-dialog" title="顯示順序"></div>  
<console:footer />
</body>
</html>