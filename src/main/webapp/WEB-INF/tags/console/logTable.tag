<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="list" type="java.util.ArrayList" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table table-striped table-bordered table-hover list" style="width:100%;">
    <thead>
        <tr>
<!--             <th width="12%">id</th> -->
            <th width="12%">日期</th>
            <th>標題</th>
            <th width="10%">點擊數</th>
        </tr>
    </thead>
    
    <tbody>
    	<c:forEach var="data" items="${list}">
    		<tr>
<%-- 				<td>${data.logNewsId}</td> --%>
				<td>${data.publish_dateStr}</td>
				<td>${data.title}</td>
				<td><fmt:formatNumber value="${data.logCount}" type="number" maxFractionDigits="3" /> 
				</td>
    		</tr>	
    	</c:forEach>
    </tbody>
</table>