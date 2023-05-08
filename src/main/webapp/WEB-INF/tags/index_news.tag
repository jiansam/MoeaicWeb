<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ tag import="java.util.*" %>
<%@ tag import="gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%@ attribute name="language" %>

<div id="divNewsWindow" class="FloatWindow">
	<img src="${pageContext.request.contextPath}/images/<%= "en".equalsIgnoreCase(language) ? "floatwindow_Title_News_en.png" : "floatwindow_Title_News.png" %>" 
		width="300" height="44" alt="<%= "en".equalsIgnoreCase(language) ? "Latest News" : "最新公告" %>" style="float: right;" class="floatwindow_Title" 
		title="<%= "en".equalsIgnoreCase(language) ? "Latest News" : "最新公告" %>"/> 
	<div style="clear: both;background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/floatwindow_Top.png');height: 0;padding-top: 1%;background-repeat:no-repeat;background-position: center;"></div>
	<div id="divNewsBody" class="FloatWindowBody">
		<div class="FloatWindowText">
<%
	ArrayList<PagesNews> news_list = PagesNewsDAO.getIndexNews("new_ann", language);
	for(int i = 0;i < news_list.size();i++){
		PagesNews news = news_list.get(i);
%>
			<div class="divMessage">
				<div class="divMessageImage" <%= news.getIsNew() ? "" : "style='display:none'" %>>
						<img src="${pageContext.request.contextPath}/images/new.gif" width="30" style="margin-top: 12px;" 
							 alt="<%= "en".equalsIgnoreCase(language) ? "New news" : "最新發表" %>"/>
				</div>
				<div class="divMessageText">
					<a href="${pageContext.request.contextPath}/news.view?do=data&id=<%= news.getId() %>&lang=<%= language %>&type=new_ann"
						title="<%= news.getPublish_date_AD() %> <%= "ch".equalsIgnoreCase(language) ? news.getCh_title() : news.getEn_title() %>">
						<%= news.getPublish_date_AD() %><br /> <%= "ch".equalsIgnoreCase(language) ? news.getCh_title() : news.getEn_title() %>
					</a>
				</div>
			</div>
<%
	}
%>
		</div>

		<a href="${pageContext.request.contextPath}/<%= "en".equalsIgnoreCase(language) ? "english" : "chinese" %>/news_newAn.jsp" style="padding-top: 10px; display: block"
			title="<%= "en".equalsIgnoreCase(language) ? "More news" : "更多最新公告" %>"><img 
			src="${pageContext.request.contextPath}/images/more.png" style="float: right;" 
			 alt="<%= "en".equalsIgnoreCase(language) ? "More news" : "更多最新公告" %>"/></a>
	</div>
	<div style="clear: both;background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/floatwindow_Bottom.png');height: 0;padding-top: 4.2%;background-repeat:no-repeat;background-position: center;"></div>
</div>