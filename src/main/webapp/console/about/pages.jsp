<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var type = "${pagesOne_ch.type}"
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pages.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>${title}</b></h2>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    	<div id="tabs">
							<ul class="tabLi">
								<li id="chli"><a href="#chDiv">中文版</a></li>
								<li id="enli"><a href="#enDiv">英文版</a></li>
							</ul>
                    
                    		<div id="chDiv">
		                        <div class="panel-heading">
		                        	<form role="form" action="" method="post" class="pure-form" >
		                        	<table class="pure-table pure-table-striped" style="width:100%;border:1pa solid red;">
		                        		<tr>
		                        			<td>
												<textarea rows="10" id="page_content_CH" name="page_content" style="width:100%;"
													class="form-control" >${pagesOne_ch.page_content}</textarea>
		                        			</td>
		                        		</tr>
		                        		
		                        		<tr>
		                        			<td align="center">
		                        				<br>
		                        				<input type="hidden" name="type" value="${pagesOne_ch.type}">
		                        				<input type="hidden" name="language" value="CH">
		                        				<input type="hidden" name="id" value="${pagesOne_ch.id}">
		                        				<input type="hidden" name="doThing" value="udtContent">
		                        				<input type="button" value="送出" onclick="submitContent(this.form, 'CH')" 
		                        					class="btn btn-primary" style="width: 100px;">
		                        			</td>
		                        		</tr>
									</table>
									</form>
									
		                        </div>
                    		
                    		
                    		</div>
                    		
	                        <div id="enDiv">
								<div class="panel-heading">
									<form role="form" action="" method="post" class="pure-form" >
								    <table class="pure-table pure-table-striped" style="width:100%;border:1pa solid red;">
								    	<tr>
								        	<td>
												<textarea rows="10" id="page_content_EN" name="page_content" style="width:100%;"
														class="form-control">${pagesOne_en.page_content}</textarea>
								       		</td>
								    	</tr>
								                        		
								    	<tr>
								        	<td align="center">
								            	<br>
								           		<input type="hidden" name="type" value="${pagesOne_en.type}">
								           		<input type="hidden" name="language" value="EN">
								           		<input type="hidden" name="id" value="${pagesOne_en.id}">
								           		<input type="hidden" name="doThing" value="udtContent">
								           		<input type="button" value="送出" onclick="submitContent(this.form, 'EN')" 
								                	class="btn btn-primary" style="width: 100px;">
								       		</td>
								       	</tr>
									</table>
									</form>
								
								</div>
	                        </div>
	                        
                        </div>
                        
                        
                        
                    </div>
                </div>
            </div>
            
            
            
            
        </div>
        <div style="clear:both;"></div>
    </div>
<console:footer />    
</body>
</html>