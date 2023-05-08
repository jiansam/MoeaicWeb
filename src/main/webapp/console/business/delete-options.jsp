<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "org.dasin.tools.*" %>
<%@ page import = "gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<jsp:useBean id="optionManager" scope="application" class="gov.moeaic.web.bean.OptionManager" />
<%
	OptionDAO.delete(dTools.parseInt(request.getParameter("id")));

	optionManager.init();
	response.sendRedirect("options.jsp");
%>