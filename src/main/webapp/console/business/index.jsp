<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ page import = "gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<console:libraries />
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
	</script>
	<script src="${pageContext.request.contextPath}/js/console/edit-options.js"></script>
	<title>經濟部投資審議司 - 後台管理</title>
</head>
<body>
<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>資料類型管理</b></h2>
                </div>
            </div>
            
            <div class="row">
				<div class="col-lg-12" >
					<input type="button" value="新增資料類型" class="btn btn-primary" 
						onclick="edit_options(0, 'business_type');" style="float:right;margin-bottom:5px;"/>
				</div>
			</div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">資料類型列表</div>
                        
                        <!-- /.panel-heading --> 
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="web_TitleList">
                                    <thead>
                                        <tr>
                                            <th width="5%">順序</th>
                                            <th>中文名稱</th>
                                            <th>英文名稱</th>
                                            <th width="5%" >編輯</th>
                                        </tr>
                                    </thead>
                                    <tbody id="rows">
<%
									for(Option option : OptionDAO.listByType("business_type")){
%>
										<tr>
											<td><%= option.getSeq() %></td>
											<td><%= option.getName() %></td>
											<td><%= option.getEname() %></td>
											<td>
												<a href="javascript:void(0);" onclick="edit_options(<%= option.getId() %>, 'business_type');">
													<img class="edit" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24">
												</a>
												<a href="delete-options.jsp?id=<%= option.getId() %>" onclick="doRemove('<%= option.getId() %>')">
													<img class="dele" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24">
												</a>
											</td>
										</tr>
<%
									}
%>
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
    
    <div id="options-dialog" title="編輯資料類型"></div>
<console:footer />    
</body>
</html>