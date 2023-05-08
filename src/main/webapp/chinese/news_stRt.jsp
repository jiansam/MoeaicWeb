<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<title>研究報告 - 焦點消息 - 經濟部投資審議委員會(INVESTMENT COMMISSION, MOEA)</title>
	<moeaic:libraries />
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#newsList').DataTable({
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
					data : { "type" : "stRt" , "do" : "list" , "lang" : "ch"}
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
	    			<h3 class="p" id="pageTitle">研究報告</h3>
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
