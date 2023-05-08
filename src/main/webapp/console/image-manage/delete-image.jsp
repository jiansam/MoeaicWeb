<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.dasin.tools.*" %>
<%@ page import="gov.moeaic.sql.dao.*" %>
<%
	ImagesDAO.delete(request, dTools.parseInt(request.getParameter("id")));

	response.sendRedirect("index.jsp");
%>