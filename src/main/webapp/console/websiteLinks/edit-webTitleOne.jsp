<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="row" id="web_TitleOneDiv">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-body">
				<div class="table-responsive">
				
					<form role="form">
						<table class="table table-striped table-bordered table-hover" id="web_TitleOne">
							<tbody>

								<tr>
									<td width="12%" nowrap="nowrap">中文名稱</td>
									<td><input type="text" name="ch_title" value="" class="form-control"></td>
								</tr>

								<tr>
									<td>英文名稱</td>
									<td><input type="text" name="en_title" value="" class="form-control"></td>
								</tr>

								<tr>
									<td>顯示順序</td>
									<td><input type="text" name="sort" value="" class="form-control"></td>
								</tr>

								<tr>
									<td colspan="2" align="center">
										<input type="hidden" name="id" value="" class="form-control"> 
										<input type="hidden" name="doThing" value="web_TitleOne" class="form-control"> 
										<input type="button" value="送出" class="form-control btn btn-primary" 
											style="width: 15%;" onclick="submitContent(this.form, 'WebTitle')">
									</td>
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
