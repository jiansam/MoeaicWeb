<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/business-excel.js"></script>
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		//console.log("${result['0']}")
		//console.log("${order_name}")
		$(document).ready(function() {
 			setDialog("#file-dialog", 400, true);
 			//ajax_data(pagesURL, {"doThing":"getShowIndex"});
		});
	</script>
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
					<input type="button" value="上傳統計資料" class="btn btn-primary" 
						onclick="uploadFile('file-dialog')" style="float:right;margin-bottom:5px;"/>
				</div>
			</div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
							統計資料
                        </div>
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-hover" style="width:100%;table-layout:fixed;" id="list">
                                    <thead>
                                        <tr>
                                            <th>業務類型</th>
                                            <th>開始年</th>
                                            <th>資料更新至</th>
                                            <th style="display:none;">首頁輪撥</th>
                                        </tr>
                                    </thead>
                                    
                                    <tbody>
	                                   	<c:forEach var="num" items="${order_name}">
                                    	<tr>
                                    		<td>${result[num].type}</td>
                                    		<td>${result[num].first_year}</td>
                                    		<td>${result[num].last_year}</td>
                                    		<td style="display: none;">
                                    			<input type="checkbox" name="showIndex" value="${result[num].type}"
                                    				style="width:18px;height:18px;" onclick="getShowIndex()" ></td>
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
<div id="file-dialog" title="檔案上傳"></div>    
<console:footer />  
</body>
</html>