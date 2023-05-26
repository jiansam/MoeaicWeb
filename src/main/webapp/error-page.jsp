<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head> 
	<moeaic:libraries />
	<title>找不到網頁</title>
	<style type="text/css">
		@media screen and (min-width: 992px) {
			#divRightContent {
				padding-left:40px;
				min-height: 300px;
			}
			
			#pageTitle {
				padding-left:180px;
			}
		}
	</style>
</head>

<body>
	<moeaic:header/>
    <moeaic:slide />

	<div id="divContent">
		<div id="divWhiteBack">
			<div class="container">

				<div id="divRightContent" style="width: 100%; font-size: 18px;">
					<div id="divTitleBorder">
						<div id="pageTitle"></div>
					</div>
					
					<div style="text-align:center;">
						<h3 style="color:#245091;"><b>找不到您要的網頁。</b></h3>
							<span style="font-size:18px !important;">網頁出現暫時性的錯誤，建議您重新載入頁面，</span>
							<br>
							<span style="font-size:18px !important;">若仍無法正確顯示，請與提供網路服務的管理者聯繫。</span>
							<br>
				
							<br>
						<h3 style="color:#245091;"><b>Web page not found.</b></h3>
							<span style="font-size:18px !important;">The web page does not exist or has been removed.</span>
							<br>
							<span style="font-size:18px !important;">You can refresh this page or browsing other pages,</span>
							<br>
							<span style="font-size:18px !important;">or let us know what you were doing when this error occurred.</span>
							<br>					
					</div>
				</div>
			</div>
		</div>
	</div>

<moeaic:footer />
</body>
</html>
