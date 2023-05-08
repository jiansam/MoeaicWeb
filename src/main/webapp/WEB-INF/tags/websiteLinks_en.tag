<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print compact-letters">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="Left menu">:::</a>
	<div id="menuTitle" style="letter-spacing:0px;">Related Links</div>
	<ul>
		<c:forEach var="parent" items="${linkTitle}">
		<li><a href="${pageContext.request.contextPath}/links.view?do=${parent.id}&lang=en" title="${parent.en_title}">
		<span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span> ${parent.en_title}</a></li>
		</c:forEach>
	</ul>
</div>