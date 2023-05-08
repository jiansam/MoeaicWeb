<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "org.dasin.tools.*" %>
<%@ page import = "gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<jsp:useBean id="optionManager" scope="application" class="gov.moeaic.web.bean.OptionManager" />
<%
	Option option = new Option();
	option.setId(dTools.parseInt(request.getParameter("id")));
	option.setName(dTools.trim(request.getParameter("name")));
	option.setEname(dTools.trim(request.getParameter("ename")));
	option.setValue(dTools.trim(request.getParameter("id")));
	option.setType(dTools.trim(request.getParameter("type")));
	option.setSeq(dTools.parseInt(request.getParameter("seq")));
	
	if(option.getId() == 0){
		OptionDAO.insert(option);
	}else{
		OptionDAO.update(option);
	}
	
	optionManager.init();
	response.sendRedirect("options.jsp");
%>