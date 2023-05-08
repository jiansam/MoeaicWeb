<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "org.dasin.tools.*" %>
<%@ page import = "java.util.*, java.text.*" %>
<%@ page import = "gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%
	Option option = OptionDAO.get(dTools.parseInt(request.getParameter("id")));
	if(option == null){
		option = new Option();
		option.setType(dTools.trim(request.getParameter("type")));
	}
%>
<div align="right" style="margin: 10px;<%= option.getId() == 0 ? "display:none;" : "" %>">
	<a href="delete-options.jsp?id=<%= option.getId() %>" class="btn btn-primary" style="color:white;"
		onclick="return confirm('確認刪除此資料類型？'));">刪除資料類型</a>
</div>
<form class="pure-form" action="edit-options-2.jsp">
<table class="table table-striped" style="width: 100%">
	<tr style="<%= "en_QA_type".equalsIgnoreCase(option.getType()) ? "display:none;" : "" %>">
		<td>中文名稱</td>
		<td><input type="text" name="name" class="form-control" value="<%= dTools.trim(option.getName()) %>" /></td>
	</tr>
	
	<tr style="<%= "ch_QA_type".equalsIgnoreCase(option.getType()) ? "display:none;" : "" %>">
		<td>英文名稱</td>
		<td><input type="text" name="ename" class="form-control" value="<%= dTools.trim(option.getEname()) %>" /></td>
	</tr>
	
	<tr>
		<td>顯示順序</td>
		<td><input type="text" name="seq" class="form-control" value="<%= option.getSeq() %>" /></td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="id" value="<%= option.getId() %>" />
			<input type="hidden" name="type" value="<%= option.getType() %>" />
			<input type="submit" value="送出資料" class="btn btn-primary" />
		</td>
	</tr>
</table>
</form>