<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<title>最新公告 - 焦點消息 - 經濟部投資審議司(INVESTMENT COMMISSION, MOEA)</title>
	<moeaic:libraries />
	
	<script type="text/javascript">
		$(document).ready(function() {
			var table = $('#newsList').DataTable({
				//"responsive" : true,
				"columnDefs" : [ 
				   { "targets" : 0, 
					 "orderable" : false , 
					 }
				   ],
				"orderClasses" : false,
				"order" : [],
				
				// DataTable ajax
				"ajax" : {
					url : "${pageContext.request.contextPath}/news.view",
					cache : false,
					//data : { "type" : "newsAn" , "do" : "list" , "lang" : "ch"}
					data: function(data) {
						data.type = "newsAn";
						data.do = "list";
						data.lang = "ch";
						data.keyword = $('#keyword').val();
						data.date_from = $('#date_from').val();
						data.date_to = $('#date_to').val();
					}
				},
				"drawCallback" : function( settings ) {
					$("table#newsList thead th").each(function(index){
						$(this).removeAttr("rowspan");
						$(this).removeAttr("colspan");
					});
					$('.paginate_button').each(function(){
						var text = $(this).text();
						if(/\d+/.test(text)){
							text = "第" + text + "頁";	
						}
						
						$(this).attr('title', text);
					});					
				}
			});
			
			$('#date_from').datepicker({
				changeYear: true,
				changeMonth: true,
				dateFormat: 'yy/mm/dd',
			});
			
			$('#date_to').datepicker({
				changeYear: true,
				changeMonth: true,
				dateFormat: 'yy/mm/dd',
			});
			
			$('#search').click(function() {
				table.ajax.reload();
				table.draw();
			});
			/*
			$('#date_from, #date_to, #keyword').keyup( function() {
				table.ajax.reload();
				table.draw();
			});
			*/
		
		});
	</script>
</head>

<body>
	<moeaic:header selected="news" />
    <moeaic:slide category="e"/>
    <div id="divContent">
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:news />
                
            <div id="divRightContent" style="width:100%;">
	            <div id="divTitleBorder">
	            	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
	    			<h3 class="p" id="pageTitle">最新公告</h3>
	        	</div>
				
				<div class="row">
					<div style="margin-top: 4px;"><label for="date_from">
						<label for="date_to" style="font-size: large;">發布日期：</label></label>
						<input type="text" id="date_from" name="date_from" style="width:30%; height:32px;line-height:32px;"> ~ 
						<input type="text" id="date_to" name="date_to"  style="width:30%;height:32px;line-height:32px;"></div>
					<div style="margin-top: 4px;"><label for="keyword"  style="font-size:large;">關鍵字：</label>
						<input type="text" id="keyword" name="keyword" style="width:40%;min-width:10%;height:32px;line-height:32px;">&nbsp;
						<input type="button" name="search" id="search" style="width:60px;" value="查詢" title="查詢">
					</div>
				</div>

				<table class="table table-striped table-bordered table-hover" id="newsList" style="width:100%;font-size:18px;">
					<thead>
						<tr style="color:white;background:#245091;">
						    <th style="text-align: center;">中文標題</th>
						    <th style="text-align: center;" width="1%" nowrap="nowrap">日期</th>
						</tr>
					</thead>
				</table> 
				<moeaic:noscript/>
				<br>           	
            </div>
        </div>
        </div>
     </div>
     
     <moeaic:footer />
</body>
</html>
