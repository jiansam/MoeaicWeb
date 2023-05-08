<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="gov.moeaic.sql.dao.*, gov.moeaic.sql.bean.*, gov.moeaic.sql.controller.*" %>
<%@ tag import="org.dasin.tools.*" %>
<%@ tag import="java.util.*" %>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setAttribute("bpmap", BusinessPubDAO.mapBussinessPub("en"));
	request.setAttribute("fclist", PagesServlet.list_flow_chart("en"));
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

<div id="divLeftMenu" class="hidden-sm hidden-xs hidden-print compact-letters" style="width:220px;margin-left: -20px;">
	<a id="L" href="#L" accesskey="L" class="sr-only u-focusable" style="float:left;line-height:40px;" title="Left menu">:::</a>
	<div id="menuTitle" style="letter-spacing:0px;">Services</div>
	<div>
		<table style="width:90%;margin: 0 auto;">
			<tr>
				<td width="5%" valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td>
					<a href="javascript:void(0);" onfocus="showli(this);" title="Overseas Chinese & Foreign Investment">Overseas Chinese & Foreign Investment</a>
					<moeaic:businessPubSubmenu op_ids="1" language="en" />
				</td>
			</tr>

			<tr>
				<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td>
					<a href="javascript:void(0);" onfocus="showli(this);" title="Investment From Mainland China">Investment From Mainland China</a>
					<moeaic:businessPubSubmenu op_ids="3" language="en" />
				</td>
			</tr>		

			<tr>
				<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td>
					<a href="javascript:void(0);" onfocus="showli(this);" title="Outward Investment">Outward Investment</a>
					<moeaic:businessPubSubmenu op_ids="4" language="en" />
				</td>
			</tr>
			
			<tr>
				<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td>
					<a href="javascript:void(0);" onfocus="showli(this);" title="Investment To Mainland China">Investment To Mainland China</a>
					<moeaic:businessPubSubmenu op_ids="5" language="en" />
				</td>
			</tr>
			
			<tr>
				<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td>
					<a href="javascript:void(0);" onfocus="showli(this);" title="Entrepreneur Visa">Entrepreneur Visa</a>
					<moeaic:businessPubSubmenu op_ids="6" language="en" />
				</td>
			</tr>
			
			<tr>
				<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
				<td><a href="javascript:void(0);" title="Other">Other</a></td>
			</tr>
			
			<tr>
				<td></td>
				<td>
					<table style="width:95%;margin: 0 auto;">
						<tr>
							<td valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
							<td>
								<a href="javascript:void(0);" onfocus="showli(this);" title="Entry Permit for People of Mainland China Area">Entry Permit for People of Mainland China Area</a>
								<moeaic:businessPubSubmenu op_ids="9" language="en" />
							</td>
						</tr>	
						<tr>
							<td width="5%" valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
							<td>
								<a href="javascript:void(0);" onfocus="showli(this);" title="Entry Permit for Foreigners">Entry Permit for Foreigners</a>
								<moeaic:businessPubSubmenu op_ids="8" language="en" />
							</td>
						</tr>
						
																	
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>