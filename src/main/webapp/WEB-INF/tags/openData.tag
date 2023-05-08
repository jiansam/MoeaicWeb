<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print compact-letters" style="width:230px;margin-left: -25px;">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="左側選單">:::</a>
	<div id="menuTitle">政府資訊公開</div>
	<ul>
		<c:forEach var="data" items="${optionManager.openData_type}">
		<li><a href="${pageContext.request.contextPath}/openData.view#op${data.id}" title="${data.name}">${data.name}</a></li>
		</c:forEach>
	</ul>
</div>