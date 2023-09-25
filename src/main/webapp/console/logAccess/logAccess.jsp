<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<style type="text/css">
		div.dataTables_length label select.input-sm {
			font-size: 14px;
		}
		
		a.showCountry, a.showOneCountry{
			cursor: pointer;
		}
	</style>
	<title>經濟部投資審議司 - 點擊率管理</title>
</head>
<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>點擊率管理</b></h2>
                </div>
            </div>

            <div class="row">
				<div class="col-lg-12" style="margin-bottom:20px;">
					<div>
						<select name="divName" style="float: left;height:32px;width:120px;border-radius:5px;" onchange="showDiv()">
							<option value="total">總表</option>		
							<option value="country">國別統計</option>		
							<option value="new_ann">最新公告</option>		
							<option value="business_ann">業務統計</option>		
							<option value="studyReport">研究報告</option>		
						</select>
					</div>
					
					<div style="float: right;">
						<span>統計日期：${sDate}～${eDate}</span>
					</div>
				</div>
			</div>            

			
			<!-- /.row -->
            <div class="row" id="total">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">總表(中英文版合計)</div><!-- /.panel-heading -->
                        <div class="panel-body" >
                            <div class="table-responsive">
                                <table class="table table-hover" id="logPage_list" style="width:100%;">
                                	<thead>
                                		<tr>
                                			<th width="8%">序號</th>
                                			<th width="30%">單元</th>
                                			<th>點擊數</th>
                                		</tr>
                                	</thead>
                                    <tbody id="rows">
                                    	<tr>
                                    		<td>1</td>
                                    		<td>網站瀏覽人數(首頁點擊次數)</td>
                                    		<td><a class='showCountry' data-page='index' data-type='' >
                                    				<fmt:formatNumber value="${sum.indexSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>2</td>
                                    		<td>關於本會</td>
                                    		<td><a class='showCountry' data-page='about' data-type=''>
                                    				<fmt:formatNumber value="${sum.aboutSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>3</td>
                                    		<td>焦點消息</td>
                                    		<td><a class='showCountry' data-page='news&business_category' data-type=''>
                                    				<fmt:formatNumber value="${sum.newsSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>　3-1</td>
                                    		<td>　最新公告</td>
                                    		<td><a class='showCountry' data-page='news' data-type='news_newAn'>
                                    				<fmt:formatNumber value="${sum.newAnSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>　3-2</td>
                                    		<td>　業務統計</td>
                                    		<td><a class='showCountry' data-page='news' data-type='news_bsAn'>
                                    				<fmt:formatNumber value="${sum.bsAnSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>　3-3</td>
                                    		<td>　研究報告</td>
                                    		<td><a class='showCountry' data-page='news' data-type='news_stRt'>
                                    				<fmt:formatNumber value="${sum.stRtSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>　3-4</td>
                                    		<td>　業務統計圖表</td>
                                    		<td><a class='showCountry' data-page='business_category' data-type=''>
                                    				<fmt:formatNumber value="${sum.categorySum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>4</td>
                                    		<td>申辦業務</td>
                                    		<td><a class='showCountry' data-page='businessPub' data-type=''>
                                    				<fmt:formatNumber value="${sum.businessPubSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>5</td>
                                    		<td>相關網站</td>
                                    		<td><a class='showCountry' data-page='links' data-type=''>
                                    				<fmt:formatNumber value="${sum.linkSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    	<tr>
                                    		<td>6</td>
                                    		<td>政府資訊公開</td>
                                    		<td><a class='showCountry' data-page='openData' data-type=''>
                                    				<fmt:formatNumber value="${sum.openDataSum}" type="number" maxFractionDigits="3" /></a></td></tr>
                                    </tbody>
                                </table>
                            </div><!-- /.table-responsive -->
                        </div><!-- /.panel-body -->
                    </div><!-- /.panel -->                    
                </div><!-- /.col-lg-12 -->
            </div><!-- /.row -->           
            

            <!-- /.row -->
            <div class="row" id="country" style="display:none;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">國別統計(首頁)</div><!-- /.panel-heading -->
                        <div class="panel-body" >                    
	                    	<div class="tabs">
								<ul class="tabLi">
									<li id="chli"><a href="#chDiv">中文版</a></li>
									<li id="enli"><a href="#enDiv">英文版</a></li>
								</ul>
	                            <div id="chDiv" class="table-responsive">
		                            <console:logTable_country list="${country_ch}"/></div>
		                        <div id="enDiv" class="table-responsive">
		                        	<console:logTable_country list="${country_en}"/></div>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>	
            
            <!-- /.row -->
            <div class="row" id="new_ann" style="display:none;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">最新公告</div><!-- /.panel-heading -->
                        <div class="panel-body" >                    
	                    	<div class="tabs">
								<ul class="tabLi">
									<li id="chli"><a href="#chDiv">中文版</a></li>
									<li id="enli"><a href="#enDiv">英文版</a></li>
								</ul>
	                            <div id="chDiv" class="table-responsive">
		                            <console:logTable list="${new_ann_ch}"/></div>
		                        <div id="enDiv" class="table-responsive">
		                        	<console:logTable list="${new_ann_en}"/></div>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>	
            
            <!-- /.row -->
            <div class="row" id="studyReport" style="display:none;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">研究報告</div><!-- /.panel-heading -->
                        <div class="panel-body" >                    
	                    	<div class="tabs">
								<ul class="tabLi">
									<li id="chli"><a href="#chDiv">中文版</a></li>
								</ul>
	                            <div id="chDiv" class="table-responsive">
		                            <console:logTable list="${studyReport_ch}"/></div>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- /.row -->
            <div class="row" id="business_ann" style="display:none;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">業務統計</div><!-- /.panel-heading -->
                        <div class="panel-body" >                    
	                    	<div class="tabs">
								<ul class="tabLi">
									<li id="chli"><a href="#chDiv">中文版</a></li>
									<li id="enli"><a href="#enDiv">英文版</a></li>
								</ul>
	                            <div id="chDiv" class="table-responsive">
		                            <console:logTable list="${business_ann_ch}"/></div>
		                        <div id="enDiv" class="table-responsive">
		                        	<console:logTable list="${business_ann_en}"/></div>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>	            
            
            		            
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
<console:footer />      

	<script type="text/javascript">
		$(document).ready(function() {
		    $('#logPage_list').DataTable({
		        responsive: true,
		        paging : false,
		        lengthChange : false,
		        searching : false,
		        info : false
		    });
		    $('.list').DataTable({
		        responsive: true,
				"pageLength" : 10,
				"lengthMenu" : [[10, 25, 50, -1], [10, 25, 50, "全部"]],
				"order": [0,'desc']
		    });
		    $('.countrylist').DataTable({
		        responsive: true,
				"pageLength" : 10,
				"lengthMenu" : [[10, 25, 50, -1], [10, 25, 50, "全部"]],
				"order": [1,'desc']
		    });
			$(".tabs").tabs({
				width : $("#tabs").parent().width(),
			});
			
			showDiv();
			
			$(document).on("click",".showCountry",function() {
				var values = {
						"doThing":"countrysData",
						"page":$(this).data("page"),
						"type":$(this).data("type")
				}
				var url = "${pageContext.request.contextPath}/console/logAccess.view";
				postUrlByForm(url, values);
			})
			
			$(document).on("click",".showOneCountry",function() {
				var values = {
						"doThing":"oneIndexCountryData",
						"page":$(this).data("page"),
						"country":$(this).data("country"),
						"language":$(this).data("language")
				}
// 				console.log(values)
				var url = "${pageContext.request.contextPath}/console/logAccess.view";
				postUrlByForm(url, values);
			})
			
			function postUrlByForm(url, values){
				var $form=$("<form></form>",{"method":"post","action":url});
				var params="";
				$.each(values,function(key,value){
					params+="<input type='hidden' value='"+value+"' name='"+key+"' />";
				});
				$form.append(params);
				$('body').append($form);
				$form.submit();
			}
		});
		
		function showDiv() {
			var divName = $("select[name='divName']").val();
			$("#country,#total,#new_ann,#business_ann,#studyReport").hide();
			$("#"+divName).show();
		}
	</script>

</body>


</html>