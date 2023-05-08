<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="gov.moeaic.sql.dao.*, gov.moeaic.sql.bean.*, gov.moeaic.sql.controller.*" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ tag import="java.util.*" %>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setAttribute("bpmap", BusinessPubDAO.mapBussinessPub("ch"));
	request.setAttribute("fclist", PagesServlet.list_flow_chart("ch"));
%>
<script type="text/javascript">
function showli(anchor) {
	$(".bp_submenu").hide();
	$(anchor).next("ul").show();
}

<%
	if(!dTools.isEmpty(request.getParameter("tab"))){
%>
$(document).ready(function () {
		$(".resp-tab-item").eq(<%= dTools.parseInt(request.getParameter("tab")) %>).click();
		location.hash = "#horizontalTab";
});
<%		
	}
%>
</script>

<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print compact-letters" style="width:200px;margin-left: -40px;">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="左側選單">:::</a>
	<div id="menuTitle" style="letter-spacing: 2px;">申辦業務及案件查詢</div>
	<ul>
		<li><a href="${urlManager.pages_map.apply_search_CH}" target="_blank" title="[另開新視窗]申請案件查詢">申請案件查詢</a></li>
		
		<li><a href="javascript:void(0);" onfocus="showli(this);" title="僑外來臺投資">僑外來臺投資</a>
			<moeaic:businessPubSubmenu op_ids="1" language="ch" />
		</li>

		<li><a href="javascript:void(0);" onfocus="showli(this);" title="陸資來臺投資">陸資來臺投資</a>
			<moeaic:businessPubSubmenu op_ids="3" language="ch" />
		</li>
		
		<li><a href="javascript:void(0);" onfocus="showli(this);" title="國外(含港澳地區)投資">國外(含港澳地區)投資</a>
			<moeaic:businessPubSubmenu op_ids="4" language="ch" />
		</li>
		
		<li><a href="javascript:void(0);" onfocus="showli(this);" title="對中國大陸投資">對中國大陸投資</a>
			<moeaic:businessPubSubmenu op_ids="5" language="ch" />
		</li>
		
		<li><a href="javascript:void(0);" onfocus="showli(this);" title="創業家簽證">創業家簽證</a>
			<moeaic:businessPubSubmenu op_ids="6" language="ch" />
		</li>

		<li><a href="javascript:void(0);" onfocus="showli(this);" title="經濟部推薦申請歸化國籍之高級專業人才">經濟部推薦申請歸化國籍之高級專業人才</a>
			<moeaic:businessPubSubmenu op_ids="38" language="ch" />
		</li>

		<li><a href="javascript:void(0);" title="其他">其他</a>
			<ul>
				<li><a href="javascript:void(0);" onfocus="showli(this);" title="大陸人士來臺" class="submenutitle"
					><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true" title="大陸人士來臺"></i></span>大陸人士來臺</a>
					<moeaic:businessPubSubmenu op_ids="9" language="ch" />
				</li>
				
				<li><a href="javascript:void(0);" onfocus="showli(this);" title="外籍人士來臺" class="submenutitle"
					><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true" title="外籍人士來臺"></i></span>外籍人士來臺</a>
					<moeaic:businessPubSubmenu op_ids="8" language="ch" />
				</li>
				
				<li><a href="javascript:void(0);" onfocus="showli(this);" title="大陸地區產業技術引進" class="submenutitle"
					><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span>大陸地區產業技術引進</a>
					<moeaic:businessPubSubmenu op_ids="7" language="ch" />
				</li>
				

			</ul>
		</li>
	
<%-- 		<c:forEach var="data" items="${optionManager.business_category}"> --%>
<%-- 			<li><a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=${data.id}">${data.name}</a></li> --%>
<%-- 		</c:forEach> --%>
	</ul>
</div>