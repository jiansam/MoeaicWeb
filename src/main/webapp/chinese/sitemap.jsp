<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<style>
	.sitemap_ul li{
		padding-left: 2em;
	}
	
	.sitemap_ul ul{
		list-style: square;
		list-style-position: inside;
	}
</style>
<head>
<moeaic:libraries />
<title>網站導覽 - 經濟部投資審議委員會(INVESTMENT COMMISSION, MOEA)</title>
</head>

<body>
	<moeaic:header />
    <moeaic:slide />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">網站導覽</h3>
	        	</div>
	        	
	        	<div>
	        		本網站依無障礙網頁設計原則建置，網站的主要內容分為四大區塊：<br>
					　1. 上方功能區塊、2. 中央內容區塊、3. 左方導覽區塊、4. 下方選單連結區塊<br>
					本網站的快速鍵﹝Accesskey﹞設定如下：<br>
					　Alt+U：上方選單連結區塊，此區塊列有本網站主要連結。<br>
					　Alt+C：中央內容區塊，為本頁主要內容區。<br>
					　Alt+L：左方導覽區塊。<br>
					　Alt+Z：下方選單連結區塊。<br>
	        	</div>
	        	<hr>
				<ul style="width: 100%;font-size:18px;" class="sitemap_ul">
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=ch" title="1. 關於本會">1. 關於本會</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atIo&lang=ch" title="1-1. 認識本會">1-1. 認識本會</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atMr&lang=ch" title="1-2. 認識首長">1-2. 認識首長</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atOn&lang=ch" title="1-3. 組織與職掌">1-3. 組織與職掌</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atPy&lang=ch" title="1-4. 本會政策">1-4. 本會政策</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atImPn&lang=ch" title="1-5. 施政計畫">1-5. 施政計畫</a></li>
							<li><a href="${pageContext.request.contextPath}/about.view?type=atTc&lang=ch" title="1-6. 交通位置">1-6. 交通位置</a></li>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp" title="2. 焦點消息">2. 焦點消息</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/chinese/news_newAn.jsp" title="2-1. 最新公告">2-1. 最新公告</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_bsAn.jsp" title="2-2. 業務統計">2-2. 業務統計</a></li>
							<li><a href="${pageContext.request.contextPath}/business_category.view?seq=0&lang=ch" title="2-3. 業務統計圖表">2-3. 業務統計圖表</a></li>
							<li><a href="${pageContext.request.contextPath}/chinese/news_stRt.jsp" title="2-4. 研究報告">2-4. 研究報告</a></li>
						</ul></li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="3. 申辦業務及案件查詢">3. 申辦業務及案件查詢</a>
						<ul>
							<li><a href="${urlManager.pages_map.apply_search_CH}" title="3-1. 申請案件查詢">3-1. 申請案件查詢</a>
							<div style="width:17px;height:15px;background-image: url('/images/icons/link.png');display: inline-block;"></div></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="3-2. 僑外來臺投資">3-2. 僑外來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=3" title="3-3. 陸資來臺投資">3-3. 陸資來臺投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=4" title="3-4. 國外(含港澳地區)投資">3-4. 國外(含港澳地區)投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=5" title="3-5. 對中國大陸投資">3-5. 對中國大陸投資</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=6" title="3-6. 創業家簽證">3-6. 創業家簽證</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=38" title="3-7. 經濟部推薦申請歸化國籍之高級專業人才">3-7. 經濟部推薦申請歸化國籍之高級專業人才</a></li>
							<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=7" title="3-8. 其他">3-8. 其他</a>
								<ul>
								<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=9" title="3-8-3. 大陸人士來臺">3-8-1. 大陸人士來臺</a></li>
								<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=8" title="3-8-2. 外籍人士來臺">3-8-2. 外籍人士來臺</a></li>
								<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=7" title="3-8-1. 大陸地區產業技術引進">3-8-3. 大陸地區產業技術引進</a></li>
								</ul>
							</li>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${urlManager.pages_map.apply_online_CH}" target="_blank" title="[另開新視窗]4. 線上申辦">4. 線上申辦</a>
							<div style="width:17px;height:15px;background-image: url('/images/icons/link.png');display: inline-block;"></div>
					</li>
<!-- 					<li> -->
<!-- 						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span> -->
<%-- 						<a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]5. 申請案件查詢">5. 申請案件查詢</a> --%>
<!-- 							<div style="width:17px;height:15px;background-image: url('/images/icons/link.png');display: inline-block;"></div> -->
<!-- 					</li> -->
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/openData.view" title="5. 政府資訊公開">5. 政府資訊公開</a>
						<ul>
							<li><a href="${pageContext.request.contextPath}/openData.view#op34" title="5-1. 法規及行政規則">5-1. 法規及行政規則</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op17" title="5-2. 本會組織、職掌及聯絡方式">5-2. 本會組織、職掌及聯絡方式</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op19" title="5-3. 行政指導有關文書">5-3. 行政指導有關文書</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op18" title="5-4. 施政計畫">5-4. 施政計畫</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op132" title="5-5. 出版品資訊">5-5. 出版品資訊</a></li>							
							<li><a href="${pageContext.request.contextPath}/openData.view#op20" title="5-6. 預算書、決算書及會計月報">5-6. 預算書、決算書及會計月報</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op21" title="5-7. 書面之公共工程及採購契約">5-7. 書面之公共工程及採購契約</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op22" title="5-8. 政治獻金法第七條查詢專區">5-8. 政治獻金法第七條查詢專區</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op23" title="5-9. 個人資料保護法專區">5-9. 個人資料保護法專區</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op131" title="5-10. 內部控制聲明書">5-10. 內部控制聲明書</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op24" title="5-11. 為民服務">5-11. 為民服務</a></li>
							<li><a href="${pageContext.request.contextPath}/openData.view#op25" title="5-12. 性別平等專區">5-12. 性別平等專區</a></li>
						</ul>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${urlManager.pages_map.attachedFile_CH}" target="_blank" title="[另開新視窗]6. 附件下載">6. 附件下載</a>
							<div style="width:17px;height:15px;background-image: url('/images/icons/link.png');display: inline-block;"></div>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/chinese/FAQ.jsp" title="7. 常見問答">7. 常見問答</a>  <%--2023年新增 --%>
					</li>
					<li>
						<span style="color: rgb(255, 64, 0);"><i class="fa fa-caret-right" aria-hidden="true"></i></span>
						<a href="${pageContext.request.contextPath}/links.view?do=index" title="8. 相關網站">8. 相關網站</a>  <%--2023年調整編號 --%>
					</li>
				</ul>
			</div>
        </div>
        </div>
     </div>
     
     <moeaic:footer />
</body>
</html>
