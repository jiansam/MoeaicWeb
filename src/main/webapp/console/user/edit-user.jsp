<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議司 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var doThing = "${doThing}"
		var account = "${userOne.account}";
		var login_admin = "${user.admin}";
		console.log(login_admin)
		var login_account = "${user.account}";
		var isNeedChg = "${isNeedChg}";
		$(document).ready(function() {
			if(doThing == "updateUser"){
				$("#user_One #account").text(account); //修改：帳號文字顯示
				
			}else if (doThing == "insertUser") {
				$("#user_One #password, #user_One #password_tr").show();  //新增：密碼欄出現
				$("#user_One #psdBtn").hide();  //新增：密碼button隱藏
			}
			
			if(login_admin == "true"){
				$("#returnBtn, #admin, #disable").show();
			}
			setDialog("#user-dialog", 700, true);
			
			//console.log("isNeedChg ? " + isNeedChg)
			setTimeout(
					function(){
							    if(isNeedChg == "need"){
							    	alert("您已經六個月未變更密碼，煩請進行密碼變更，以保護您的資料安全。");
							    	edit_PSW();
							    }
					}, 800);			
		});
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/userManager.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>使用者管理</b></h2>
                </div>
            </div>
            

            <div class="row" id="returnBtn" style="display:none;">
				<div class="col-lg-12" >
					<a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=userManage">
						<input type="button" value="返回列表" class="btn btn-primary" 
							style="float:right;margin-bottom:5px;"/>
					</a>
				</div>
			</div>
			            

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body" >
                            <div class="table-responsive">
                            	<form action="" method="post">
	                                <table class="table table-hover" id="user_One" >
	                                
	                                    <tbody id="rows">
	                                    	<tr>
	                                    		<td width="15%">帳號</td>
	                                    		<td width="60%" id="account">
	                                    			<input name="account" value="${userOne.account}" class="form-control"></td>
	                                    		<td></td></tr>
	
	                                    	<tr style="display:none;" id="password">
	                                    		<td >密碼</td>
	                                    		<td><input name="password" value="" class="form-control" onblur="checkPSW('user_One')"></td>
	                                    		<td><span id="msg">[大小寫英文+數字+符號，不得少於12字元]</span></td></tr>
										
	                                    	<tr>
	                                    		<td>姓名</td>
	                                    		<td><input name="name" value="${userOne.name}" class="form-control"></td>
	                                    		<td></td></tr>
	                                    		
	                                    	<tr>
	                                    		<td>單位</td>
	                                    		<td><input name="department" value="${userOne.department}" class="form-control"></td>
	                                    		<td></td></tr>
	                                    		                             		                                    
	                                    	<tr>
	                                    		<td>電話</td>
	                                    		<td><input name="tel" value="${userOne.tel}" class="form-control"></td>
	                                    		<td></td></tr>                                    		                             		                                    
	
	                                    	<tr>
	                                    		<td>信箱</td>
	                                    		<td><input name="email" value="${userOne.email}" class="form-control"></td>
	                                    		<td></td></tr>
										
										<c:if test="${user.admin == true}">
	                                    	<tr style="display:none;" id="admin">
	                                    		<td>網站權限</td>
	                                    		<td>
	                                    			<select name="admin" class="form-control">
	                                    				<option value="0" ${userOne.admin==false?'selected="selected"':''}>一般使用者</option>
	                                    				<option value="1" ${userOne.admin==true?'selected="selected"':''}>管理員</option>
	                                    			</select>
	                                    		</td>
	                                    		<td></td></tr>
	
	                                    	<tr style="display:none;" id="disable">
	                                    		<td>帳號停權</td>
	                                    		<td>
	                                    			<select name="disable" class="form-control">
	                                    				<option value="0" ${userOne.disable==false?'selected="selected"':''}>否</option>
	                                    				<option value="1" ${userOne.disable==true?'selected="selected"':''}>是</option>
	                                    			</select>
	                                    		</td>
	                                    		<td></td></tr>
										</c:if>
										
	                                    	<tr>
	                                    		<td colspan="3" align="center">
	                                    			<input type="hidden" name="doThing" value="${doThing}">
	                                    			<input type="hidden" name="id" value="${userOne.id}">
	                                    			<input type="hidden" name="login_user" value="${user.account}">
	                                    			<br>
	                                    			<input type="button" value="送出資料" class="btn btn-primary" 
	                                    				onclick="submitContent(this.form, 'user_one')">
	                                    			<input type="button" value="修改密碼" class="btn btn-primary" 
	                                    				id="psdBtn" onclick="edit_PSW()">
	                                    		</td></tr>
	                                    </tbody>
	                                    
	                                </table>
								</form>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->                    
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
        </div>
    </div>
    
    <div id="user-dialog" title="修改密碼"></div>   
    <console:footer />  
</body>
</html>