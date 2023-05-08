<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="list" type="java.util.ArrayList" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table table-striped table-bordered table-hover countrylist" style="width:100%;">
    <thead>
        <tr>
            <th width="20%">國家</th>
            <th >點擊數</th>
        </tr>
    </thead>
    
    <tbody>
    	<c:forEach var="data" items="${list}">
    		<tr>
				<td>${data.ipCountry_ch}</td>
				<td><a class='showOneCountry' data-page='index' data-country='${data.ipCountry}' data-language='${data.language}'>
						<fmt:formatNumber value="${data.logCount}" type="number" maxFractionDigits="3" />
					</a> 
				</td>
    		</tr>	
    	</c:forEach>
    </tbody>
</table>