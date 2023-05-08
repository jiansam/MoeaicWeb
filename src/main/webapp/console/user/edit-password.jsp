<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" >
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-body">
				<div class="table-responsive">
					<form action="" method="post">
						<table class="table table-striped table-bordered table-hover" id="update_userPSW">
							<tbody>

<!-- 								<tr> -->
<!-- 									<td width="20%" >舊密碼</td> -->
<!-- 									<td><input type="password" name="old_password" value="" class="form-control"> -->
<!-- 									</td> -->
<!-- 								</tr> -->

								<tr>
									<td width="20%">新密碼</td>
									<td><input type="password" name="password" value="" class="form-control" onkeyup="checkPSW('update_userPSW')"
										><span id="msg" style="color:gray;">[英文(有大小寫之分)+數字+符號，不得少於12字元]</span></td>
								</tr>
								
								<tr>
									<td nowrap="nowrap">再輸入一次</td>
									<td><input type="password" name="password_re" value="" class="form-control" onkeyup="checkTwoPSW('update_userPSW')"><span id="msg2"></span></td>
								</tr>								

								<tr>
									<td colspan="2" align="right">
										<input type="radio" id="chg_type" onclick="change_type()"><label for="chg_type">顯示密碼</label></td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="hidden" name="doThing" value="" class="form-control">
										<input type="hidden" name="id" value="" class="form-control">
										<input type="hidden" name="login_user" value="${user.account}">
										<input type="button" value="送出" class="form-control btn btn-primary"
											onclick="submitContent(form, 'update_userPSW')"></td>
								</tr>
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
