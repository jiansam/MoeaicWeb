<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="gov.moeaic.sql.dao.*" %>
<%
	HashMap<String, String> parameters = ImagesDAO.upload(request);

	response.sendRedirect("edit-image.jsp?id=" + parameters.get("id"));
%>