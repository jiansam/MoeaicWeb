<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="java.util.*" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ tag import="gov.moeaic.sql.bean.*" %>
<%@ attribute name="op_ids" required="true" type="Integer" %>
<%@ attribute name="language" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	ArrayList<BusinessPub> bp_list = ((HashMap<Integer, ArrayList<BusinessPub>>)request.getAttribute("bpmap")).get(op_ids);
	ArrayList<Integer> flow_chart_list = (ArrayList<Integer>) request.getAttribute("fclist");
%>
	<ul style="<%= String.valueOf(op_ids).equalsIgnoreCase(request.getParameter("op_id_one")) ? "" : "display:none;" 
		%>list-style-type:disc;list-style-position: inside;" class="bp_submenu">
<%
	if(flow_chart_list.contains(op_ids)){
%>
		<li><a href="${pageContext.request.contextPath}/businessPub.view?type=flow&lang=<%= language %>&op_id_one=<%= op_ids %>"
			title="<%= "en".equalsIgnoreCase(language) ? "Flow Chart" : "流程圖" %>"><%= "en".equalsIgnoreCase(language) ? "Flow Chart" : "流程圖" %></a></li>
<%
	}

	int count = 0;
	for(BusinessPub bp : bp_list == null ? new ArrayList<BusinessPub>() : bp_list){
		String title = "en".equalsIgnoreCase(language) ? bp.getEn_title() : bp.getCh_title();
		if(dTools.isEmpty(title)){
			continue;
		}
%>
		<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=<%= language %>&op_id_one=<%= op_ids %>&tab=<%= count ++ %>"
			title="<%= title %>"><%= title %></a></li>
<%
	}
%>
	</ul>
