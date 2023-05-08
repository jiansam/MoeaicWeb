<%@ tag language="java" pageEncoding="UTF-8"%>
<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print" style="width:160px;margin-left: 0px;">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="左側選單">:::</a>
	<div id="menuTitle">焦點消息</div>
	<ul>
<%-- 		<li><a href="${pageContext.request.contextPath}/news.view?type=newsAn&do=list">最新公告</a></li> --%>
<%-- 		<li><a href="${pageContext.request.contextPath}/news.view?type=bsAn&do=list">業務統計</a></li> --%>
<%-- 		<li><a href="${pageContext.request.contextPath}/news.view?type=stRt&do=list">研究報告</a></li> --%>
		<li><a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp" title="最新公告">最新公告</a></li>
		<li><a href="${pageContext.request.contextPath}/chinese/news_bsAn.jsp" title="業務統計">業務統計</a></li>
		<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=ch" title="業務統計圖表">業務統計圖表</a></li>
		<li><a href="${pageContext.request.contextPath}/chinese/news_stRt.jsp" title="研究報告">研究報告</a></li>
	</ul>
</div>