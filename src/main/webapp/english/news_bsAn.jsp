<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
	<moeaic:libraries_en />
	<title>Department of Investment Review, MOEA - Statistics</title>
	
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
					//data : { "type" : "bsAn" , "do" : "list" , "lang" : "en"}
					data: function(data) {
						data.type = "bsAn";
						data.do = "list";
						data.lang = "en";
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
							text = "Go to page " + text;	
						}else if(text == 'Previous'){
							text = "Go to previous page";
						}else if(text == 'Next'){
							text = "Go to next page";
						}
						
						$(this).attr('title', text);
					});					
				},
				"language": {
					"emptyTable": "No results."
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

		});
	</script>
</head>

<body>
	<moeaic:header_en selected="news"/>
	<moeaic:slide category="e"/>
    
    
    <div id="divContent">
    	
        <div id="divWhiteBack">
        	<div class="container">
            <moeaic:news_en />
                
            <div id="divRightContent" style="width:100%;">
		        <div id="divTitleBorder">
		        	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="Content area">:::</a>
		    		<h3 class="p" id="pageTitle">Statistics</h3>
		        </div>
		        <div class="row">
					<div style="margin-top: 4px;"><label for="date_from">
						<label for="date_to" style="font-size: large;">Date：</label></label>
						<input type="text" id="date_from" name="date_from" style="width:35%; height:32px;line-height:32px;"> ~ 
						<input type="text" id="date_to" name="date_to"  style="width:35%;height:32px;line-height:32px;"></div>
					<div style="margin-top: 4px;"><label for="keyword"  style="font-size:large;">Keyword：</label>
						<input type="text" id="keyword" name="keyword" style="width:40%;min-width:10%;height:32px;line-height:32px;">&nbsp;
						<input type="button" name="search" id="search" style="width:80px;" value="Search" title="Search">
					</div>
				</div>
				<table class="table table-striped table-bordered table-hover" id="newsList" style="width:100%;font-size:18px;">
				
					<thead>
						<tr style="color:white;background:#245091;">
						    <th style="text-align: center;">Subject</th>
						    <th style="text-align: center;" width="1%" nowrap="nowrap">Date</th>
						</tr>
					</thead>
				  
				</table> 
				<moeaic:noscript_en/>           	
            </div>
        </div>
        </div>
     </div>
     
     <moeaic:footer_en />
</body>
</html>
