<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pages-common.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>附件下載-連結</b></h2>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                    	<div id="tabs">
							<ul class="tabLi">
								<li><a href="#chDiv">中文版</a></li>
								<li style="display: none;"><a href="#enDiv">英文版</a></li>
							</ul>
                    
                    		<div id="chDiv">
		                        <div class="panel-heading">
		                        	<form role="form" action="" method="post" >
		                        	<table class="table table-striped table-bordered" style="width:100%;border:1pa solid red;">
		                        		<tr>
								    		<td width="8%" style="vertical-align:middle;" align="center">連結位址</td>
								    		<td>
								    			<input type="text" name="page_content" style="width:100%;"
								    				class="form-control" >
								    		</td>
		                        		</tr>
		                        		
		                        		<tr>
		                        			<td align="center" colspan="2">
		                        				<input type="hidden" name="type" value="attachedFile">
		                        				<input type="hidden" name="language" value="CH">
		                        				<input type="hidden" name="id" value="">
		                        				<input type="hidden" name="doThing" value="udtContent">
		                        				<input type="button" value="送出" onclick="submitContent(this.form, '')" 
		                        					class="btn btn-primary" style="width: 100px;">
		                        			</td>
		                        		</tr>
									</table>
									</form>
									
		                        </div>
                    		
                    		
                    		</div>
                    		
	                        <div id="enDiv" style="display: none;">
								<div class="panel-heading">
									<form role="form" action="" method="post" >
								    <table class="table table-striped table-bordered" style="width:100%;border:1pa solid red;">
								    	<tr>
								    		<td width="8%" style="vertical-align:middle;" align="center">連結位址</td>
								    		<td>
								    			<input type="text" name="page_content" style="width:100%;"
								    				class="form-control" >
								    		</td>
								    	</tr>
								                        		
								    	<tr>
								        	<td align="center" colspan="2">
								           		<input type="hidden" name="type" value="attachedFile">
								           		<input type="hidden" name="language" value="EN">
								           		<input type="hidden" name="id" value="">
								           		<input type="hidden" name="doThing" value="udtContent">
								           		<input type="button" value="送出" onclick="submitContent(this.form, '')" 
								                	class="btn btn-primary" style="width:35%;">
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
    </div>
<console:footer />    
</body>
</html>