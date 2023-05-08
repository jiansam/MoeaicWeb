<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var data = {"doThing" : "getWebTitle"};
		$(document).ready(function() {
			ajax_data(pagesURL, data);
			setDialog("#dialog-webTitle", 700, true);
		});
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/sort-common.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>分類管理</b></h2>
                </div>
            </div>
            
            
            <div class="row">
				<div class="col-lg-12" >
					<a href="#web_TitleOne" >
						<input type="button" value="新增分類名稱" class="btn btn-primary" 
							onclick="new_title(0);" style="float:right;margin-bottom:5px;"/></a>
				</div>
			</div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
							分類名稱列表
                        </div>
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="web_TitleList">
                                    <thead>
                                        <tr>
                                            <th width="15%" >順序</th>
                                            <th width="35%">中文名稱</th>
                                            <th >英文名稱</th>
                                            <th width="5%" >編輯</th>
                                        </tr>
                                    </thead>
                                    <tbody id="rows">
                                    </tbody>
                                    
                                    <tfoot>
                                        <tr style="display:none;" id="template">
                                            <td class="sort"></td>
                                            <td class="ch_title"></td>
                                            <td class="en_title"></td>
                                            <td class="id" nowrap="nowrap">
                                            	<input type="hidden" name="id" value="" class="form-control">
												<a href="#web_TitleOne">
													<img class="edit" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>
												<a href="#web_TitleList">
													<img class="dele" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"></a>
											</td>
                                        </tr>                                    
                                    </tfoot>
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
            
          <br>
        </div>
    </div>
<console:footer /> 
<div id="dialog-webTitle" title="編輯分類名稱"></div>      
</body>
</html>