<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<title>業務統計圖表 - 焦點消息 - 經濟部投資審議司(Department of Investment Review, MOEA)</title>
	<moeaic:libraries />
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {packages : [ 'corechart', 'controls']});
		google.charts.setOnLoadCallback(drawDashboard);
		
		function getList(){
			var json = JSON.parse('${list}');
			var array = [];
			for(var i = 0; i < json.length; i++){
				array[i] = [json[i].year, parseInt(json[i].year), json[i].amount / 1000, json[i].year + " 年 : " + (json[i].amount / 1000).toFixed(4) + " 百萬美元"];
			}
			
			return array;
		}
		
		function getData() {
			var seq = $("select[name='seq']").val();
			window.location = "${pageContext.request.contextPath}/business_category.view?lang=ch&seq=" + seq;
		}
		
		function openDiv(id) {
			if(id == "list_div"){
				$("#list_div").show();
				$("#dashboard_div").hide();
			}else{
				$("#list_div").hide();
				$("#dashboard_div").show();
			}
		}
		
		function drawDashboard() {
			var colors = ['#6285bd', 'red', 'green', 'orange'];
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'X');
			data.addColumn({type: 'number', role: 'annotation'});
			data.addColumn('number', '金額');
			data.addColumn({type: 'string', role: 'tooltip'});
			data.addRows(getList());

			var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard_div'));
			var rangeSlider = new google.visualization.ControlWrapper({
	          'controlType': 'NumberRangeFilter',
	          'containerId': 'filter_div',
	          'options': {
	            'filterColumnIndex': 1,
	            'ui' : {
	            	'label' : '年度',
	            	'format' : {
	            		'pattern' : '#'
	            	}
	            }
	          }
	        });
			
// 			var formatter = new google.visualization.NumberFormat({
// 				pattern : '#'
// 			});
// 			formatter.format(data, 0);

			var chart = new google.visualization.ChartWrapper({
				'chartType' : 'ColumnChart',
				'containerId' : 'chart_div',
				'options' : {
					hAxis : {
						title : '年度',
						maxAlternation : 1,
						titleTextStyle : {
							fontName : '微軟正黑體, Arial',
							italic : false
						},
						format : '#',
						gridlines : {
							color : 'transparent',
							count : -1
						}
					},
					vAxis : {
						title : '金額  (百萬美元 )',
						gridlines : {
							count : 8
						},
						titleTextStyle : {
							fontName : '微軟正黑體, Arial',
							italic : false
						}
					},
					legend : {
						position : 'none'
					},
					crosshair : {
						trigger : 'both'
					},
					height : 500,
					chartArea : {
						left : 90,
						right : 50
					},
					colors : [colors[$("select[name='seq']").val()]],
					backgroundColor: { fill:'transparent' },
					annotations: {
						textStyle : {
							color : 'transparent',
							fontSize : 8
						},
						stem : {
							length : 0
						}
					}
				}
			});

			dashboard.bind(rangeSlider, chart);
			dashboard.draw(data);
		}
		
		$(function() {
			$('#icon_chart_type').css('background-image', 'url(${pageContext.request.contextPath}/images/charts/icon-' + $("select[name='seq']").val() + '.png)');
			
			var control_colors = ['#6391de', '#cb396a', '#6cc49a', '#fe9f45'];
			$("<style>").prop("type", "text/css")
		    .html(".google-visualization-controls-slider-thumb {background-color : " + control_colors[$("select[name='seq']").val()] + " !important; "
		    	+ "border-color : silver !important; } "
		    	+ ".google-visualization-controls-rangefilter-thumblabel {color : " + control_colors[$("select[name='seq']").val()] + " !important; } "
		    )
		    .appendTo("head");
		});
	</script>
</head>

<body>
	<moeaic:header selected="news" />
    <moeaic:slide category="e" />
    
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:news />
			
			<div id="divRightContent" style="width:100%;">
				<div id="divTitleBorder">
					<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">業務統計圖表</h3>
	        	</div>
	        	
        		<div style="margin-bottom:20px;">
        			<div style="float:left;">
        				<label for="seq">圖表分類：</label>
						<select id="seq" name="seq" style="height:40px;border-radius:7px;margin-bottom: 10px;" >
							<c:forEach var="data" items="${BSCT_type}">
							<option value="${data.seq}" ${data.seq == seq ? 'selected="selected"':''}>${data.type}</option>
							</c:forEach>
						</select>
						<input type="button" value="送出" title="送出" onclick="getData()" 
							style="height:40px;border-radius:5px;margin-bottom: 10px;width:50px;">

					</div>
					
					<div id="choicediv" style="float:right;">
						<a href="#choicediv" onclick="openDiv('list_div')" onkeypress="openDiv('list_div')" title="開啟「${typeforTitle}」歷年投資金額統計表">
							<img alt="歷年投資金額統計表" src="${pageContext.request.contextPath}/images/icons/bus_icon.png"></a>
						<a href="#choicediv" onclick="openDiv('dashboard_div')" onkeypress="openDiv('dashboard_div')" title="開啟「${typeforTitle}」歷年投資金額統計圖">
							<img alt="歷年投資金額統計圖" src="${pageContext.request.contextPath}/images/icon_stat.png"></a>
						<a href="${pageContext.request.contextPath}/download-excel.jsp?do=dwn&seq=${seq}&lang=ch" target="_blank"
							title="[開啟新分頁下載]「${typeforTitle}」歷年投資金額統計表.csv"><img alt="歷年投資金額統計表" src="${pageContext.request.contextPath}/images/icon_download.png" width="27" height="27"></a>
					</div>
				</div>
				
				
				<br><br>
				<div style="text-align:center;font-size:18px;">
					<div id="icon_chart_type" style="height: 48px;width: 48px;display:inline-block;background-size: contain;background-repeat: no-repeat;background-position: center;margin-bottom: -15px;"></div>
					${type}
				</div>
				
	  			<moeaic:noscript/>
	  				
				<div id="dashboard_div" style="margin-top: 20px;">
					<div id="filter_div" style="text-align:center;margin:0 auto;"></div>
					<div id="chart_div"></div>
					
	  				<table class="table table-striped table-bordered table-hover" style="font-size:16px;">
						<thead>
		  					<tr>
		  						<th style="text-align: center;">年度</th>
		  						<th style="text-align: center;">金額  (美金千元 )</th>
		  					</tr>
						</thead>
						<tbody>
		  				<c:forEach var="data" items="${excel}" varStatus="index">
	 						<c:choose>
	  						<c:when test="${!index.last}">
		  					<tr>
		  						<td style="text-align: center;line-height:10px;padding:8px;">${data.year}</td>
		  						<td style="text-align: center;line-height:10px;padding:8px;"><fmt:formatNumber value="${data.amount}" type="currency"  maxFractionDigits="3" currencySymbol="" /></td>
		  					</tr>
	  						</c:when>
	
							<c:otherwise>
		  					<tr>
		  						<td style="text-align: center;">${data.year}${end_month_range}</td>
		  						<td style="text-align: center;"><fmt:formatNumber value="${data.amount}" type="currency"  maxFractionDigits="3" currencySymbol="" /></td>
		  					</tr>
							</c:otherwise>
	  					</c:choose>
		  				</c:forEach>
	  					</tbody>
	  				</table>
					<div style="width:100%;float:right;margin-bottom:20px;">
				    	<a href="#top" tabindex="0"><img alt="回到最上方" title="回到最上方" border="0" src="/images/upload/1b41b423-7181-4801-88a7-44ddcdb33f72.png" style="float: right;" width="32" /></a>
				    </div>	  
				</div>





				<div id="list_div" style="vertical-align:center;display:none;">
	  				<table class="table table-striped table-bordered table-hover" style="font-size:18px;">
						<thead>
		  					<tr>
		  						<th style="text-align: center;">年度</th>
		  						<th style="text-align: center;">金額  (美金千元 )</th>
		  					</tr>
						</thead>
						
						<tbody>
		  				<c:forEach var="data" items="${excel}" varStatus="index">
	
	 						<c:choose>
	  						<c:when test="${!index.last}">
		  					<tr>
		  						<td style="text-align: center;">${data.year}</td>
		  						<td style="text-align: center;"><fmt:formatNumber value="${data.amount}" type="currency"  maxFractionDigits="3" currencySymbol="" /></td>
		  					</tr>
	  						</c:when>
	
							<c:otherwise>
		  					<tr>
		  						<td style="text-align: center;">${data.year}${end_month_range}</td>
		  						<td style="text-align: center;"><fmt:formatNumber value="${data.amount}" type="currency"  maxFractionDigits="3" currencySymbol="" /></td>
		  					</tr>
							</c:otherwise>
	  					</c:choose>
		  				</c:forEach>
	  					</tbody>
	  				</table>
					<div style="width:100%;float:right;margin-bottom:20px;">
				    	<a href="#top" tabindex="0"><img alt="回到最上方" title="回到最上方" border="0" src="/images/upload/1b41b423-7181-4801-88a7-44ddcdb33f72.png" style="float: right;" width="32" /></a>
				    </div>	  				
  				</div>
	  			
				<br>
			</div>
			
			
			
        </div>
        </div>
     </div>
     <moeaic:footer />
</body>
</html>