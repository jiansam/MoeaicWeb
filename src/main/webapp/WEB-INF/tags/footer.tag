<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divFatFooter">
	<a id="Z" href="#Z" accesskey="Z" class="sr-only z-focusable" style="float:left;" title="下方選單">:::</a>
	<div class="container">
		<div id="divFatFooterTool">
			<div class="cell">
				<dl>
					<dt>關於本司</dt>
					<dd>
						<ul>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=ch" title="認識本司">認識本司</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atMr&lang=ch" title="認識召集人及司長">認識召集人及司長</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atOn&lang=ch" title="組織與職掌">組織與職掌</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atPy&lang=ch" title="本司政策">本司政策</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atImPn&lang=ch" title="施政計畫">施政計畫</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atTc&lang=ch" title="交通位置">交通位置</a></li>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="cell">
				<dl>
					<dt>焦點消息</dt>
					<dd>
						<ul>
							<li><a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp" title="最新公告">最新公告</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_bsAn.jsp" title="業務統計">業務統計</a></li>
							<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=ch" title="業務統計圖表">業務統計圖表</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_stRt.jsp" title="研究報告">研究報告</a></li>
						</ul>
					</dd>
					<dt><a href="${urlManager.pages_map.apply_online_CH}" target="_blank" title="[另開新視窗]線上申辦">線上申辦</a></dt>
<%-- 					<dt><a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]申請案件查詢">申請案件查詢</a></dt> --%>
					<dt><a href="${urlManager.pages_map.attachedFile_CH}" target="_blank" title="[另開新視窗]附件下載">附件下載</a></dt>
					<dt><a href="${pageContext.request.contextPath}/chinese/FAQ.jsp">常見問答</a></dt>   <%--2023年新增 --%>
					<dt><a href="${pageContext.request.contextPath}/links.view?do=index">相關網站</a></dt>
				</dl>
			</div>
			<div class="cell">
				<dl>
					<dt>申辦業務及案件查詢</dt>
					<dd>
						<ul>
							<li><a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]申請案件查詢">申請案件查詢</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="僑外來臺投資">僑外來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=3" title="陸資來臺投資">陸資來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=4" title="國外(含港澳地區)投資">國外(含港澳地區)投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=5" title="對中國大陸投資">對中國大陸投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=6" title="創業家簽證">創業家簽證</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=38" title="經濟部推薦申請歸化國籍之高級專業人才">經濟部推薦申請歸化國籍之高級專業人才</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=7" title="其他">其他</a></li>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="cell">
				<dl>
					<dt>政府資訊公開</dt>
					<dd>
						<ul>
							<c:forEach var="data" items="${optionManager.openData_type}">
							<li><a href="${pageContext.request.contextPath}/openData.view#op${data.id}" title="${data.name}">${data.name}</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
<div id="divPageBottom">
	<div class="container">
		<div class="information">
			<span style="white-space: nowrap;">服務信箱：<a href="#" style="color:white;text-decoration:underline;" target="_black" title="服務信箱：icserve@moea.gov.tw">icserve@moea.gov.tw</a></span>&nbsp;&nbsp; 
			<span>地址：100215 台北市中正區羅斯福路1段7號8樓</span>&nbsp;&nbsp; 
			<span style="white-space: nowrap;">電話：02-3343-5700</span> <br /> 
			<span>2016 © 版權所有 經濟部投資審議司</span><br>
			<span>建議使用 Microsoft Edge/Firefox 30/Chrome 27，最佳瀏覽解析度為1024x768 以上</span> 
			<span style="white-space: nowrap;">｜ <a href="${pageContext.request.contextPath}/about.view?type=pvay&lang=CH" style="color:white;text-decoration:underline;" title="隱私權及安全政策">隱私權及安全政策</a></span>
		</div>
		<div class="information_logo">
			<div class="information_image">
				<a href="https://accessibility.moda.gov.tw/Applications/Detail?category=20201130172438" target="_blank" title="無障礙網站[另開新視窗]">
				    <img src="${pageContext.request.contextPath}/images/a1.png" border="0" height="43" alt="通過A檢測等級無障礙網頁檢測" />
				</a>
			</div>
			<div class="information_image">
				<a href="http://www.gov.tw/" target="_blank" title="[另開新視窗]我的E政府">
					<img src="${pageContext.request.contextPath}/images/e_gov.gif" alt="[另開新視窗]我的E政府" />
				</a>
			</div>
		</div>
	</div>
</div>
