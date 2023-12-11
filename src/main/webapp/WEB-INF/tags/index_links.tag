<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ tag import="java.util.*" %>
<%@ tag import="gov.moeaic.sql.bean.*, gov.moeaic.sql.dao.*" %>
<%@ attribute name="language" %>

<div id="divLink">
	<div class="container">
<!-- 		<div id="divLinkYoutube" class="col-md-4 col-xs-12"> -->
<!-- 			<div class="divLinkTitle"> -->
<%-- 				<span style="letter-spacing: 10px;"><%= "en".equalsIgnoreCase(language) ? "Video" : "相關影片" %></span> --%>
<!-- 				<iframe width="95%" height="200" title='投審會60周年影片' src="https://www.youtube.com/embed/1SvupsvMvfQ" frameborder="0" allowfullscreen></iframe> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
<!-- 		<div id="divLinkWeb" class="col-md-8 col-xs-12"> -->
			<div class="divLinkTitle">
				<span style="letter-spacing: 10px;"><%= "en".equalsIgnoreCase(language) ? "Related Websites" : "相關網站" %></span>
			</div>

			<div id="outerCountainer" class="divLinkPhoto">
				<div class="bx-wrapper" style="box-shadow: none;border: none;">
					<div class="bx-viewport" aria-live="polite" style="width: 100%; position: relative; height: 169px;">
						<div class="slider1">
<%
	ArrayList<WebName> links = WebNameDAO.getIndexURL(language);
	for(int i = 0;i < links.size();i++){
		WebName link = links.get(i);
%>
							<div class="outer-links-div bx-clone" style="float: left; list-style: none; position: relative; width: 178px; margin-right: 20px;" aria-hidden="true">
								<a href="<%= link.getUrl() %>" target="_blank"
									title="<%= ("en".equalsIgnoreCase(language) ? " [Open in a new window]" : " [另開新視窗]") + link.getName() %>"
									><img src="<%= link.getPhotoFile() %>" width="178" height="77" alt="<%= link.getName() %>" /></a><br />
<%
		if(++i < links.size()){
			link = links.get(i);
%>
								<a href="<%= link.getUrl() %>" target="_blank"
									title="<%= ("en".equalsIgnoreCase(language) ? " [Open in a new window]" : " [另開新視窗]") + link.getName() %>"
									><img src="<%= link.getPhotoFile() %>" width="178" height="77" alt="<%= link.getName() %>" /></a>
<%
		}
%>
							</div>
<%
	}
%>
						</div>
					</div>
	
					<div class="bx-controls bx-has-controls-direction">
						<div class="bx-controls-direction">
							<a id="goPrev" href="#" alt=" " style="background: url('${pageContext.request.contextPath}/images/arrow_left.png') no-repeat; width: 40px; height: 54px; left: -45px; top: 45%;"> Prev </a> 
							<a id="goNext" href="#" alt=" " style="background: url('${pageContext.request.contextPath}/images/arrow_right.png') no-repeat; width: 40px; height: 54px; right: -45px; top: 45%;background-position: right top;"> Next </a>
						</div>
					</div>
				</div>
			</div>
<!-- 		</div>End of divLinkWeb -->
	</div>
</div>