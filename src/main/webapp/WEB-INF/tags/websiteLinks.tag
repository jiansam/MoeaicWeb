<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="左側選單">:::</a>
	<div id="menuTitle">相關網站</div>
	<ul>
		<c:forEach var="parent" items="${linkTitle}">
		<li><a href="${pageContext.request.contextPath}/links.view?do=${parent.id}" title="${parent.ch_title}">${parent.ch_title}</a></li>
		</c:forEach>
	</ul>
</div>