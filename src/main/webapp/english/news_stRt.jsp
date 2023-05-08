<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
	<moeaic:libraries_en />
	<title>INVESTMENT COMMISSION, MOEA - Publications</title>
	
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
					data : { "type" : "stRt" , "do" : "list" , "lang" : "en"}
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
				}
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
		    		<h3 class="p" id="pageTitle">Publications</h3>
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
