<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ attribute name="category" %>

<%
	category = dTools.trim(category);
	if(category.length() > 0){
		category = "_" + category;
	}
%>
<div id="inside">
	<!-- SlideAD -->
	<div id="divSlideAD" class="cover-container">
<%-- 		<div id="divBigBack" style="background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/divSlideAD_main<%= category %>.jpg');height: 0;padding-top: 27.9%;background-repeat:no-repeat;background-position: center;"> --%>
		<div id="divBigBack">
			<div style="margin: 0 auto; border-width: 0px; width: 100%; max-width: 1171px;">
				<div style="background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/divSlideAD_main<%= category %>.jpg');height: 0;padding-top: 32.8%;background-repeat:no-repeat;background-position: center;"></div>
			</div>
<%-- 			<img src="${pageContext.request.contextPath}/images/divSlideAD_main<%= category %>.jpg" class="img-responsive" alt="Responsive image" style="margin: 0 auto; border-width: 0px; width: 100%; max-width: 1171px;" /> --%>
		</div>
		<div id="divSmallBack">
			<div style="background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/divSlideADsmall<%= category %>.jpg');height: 0;padding-top: 101%;"></div>
<%-- 			<img src="${pageContext.request.contextPath}/images/divSlideADsmall<%= category %>.jpg" class="img-responsive" alt="Responsive image" style="margin: 0 auto; border-width: 0px; width: 100%;" /> --%>
		</div>
	</div>
</div>