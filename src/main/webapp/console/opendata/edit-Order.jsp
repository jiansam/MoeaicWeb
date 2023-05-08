<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" id="businessPubDiv">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-body">
				<div class="table-responsive">
					<form action="${pageContext.request.contextPath}/console/edit-pages.view" role="form" method="post"
						onsubmit="return confirm('確定要送出資料？')">
						<table class="table table-striped table-bordered table-hover" id="openData_Order">
							<tbody>
							
								<tr>
									<td width="35%" nowrap="nowrap">是否置頂</td>
									<td>
										<select name="ontop" class="form-control" onchange="checkShowTop('openData_Order')" style="height: 40px !important;">
											<option value="否">否</option>
											<option value="是">是</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td width="35%" nowrap="nowrap">排序</td>
									<td><input type="text" name="seq" value="" class="form-control"></td>
								</tr>

								<tr>
									<td colspan="2" align="center">
										<input type="hidden" name="doThing" value="setOpenData_Order" class="form-control">
										<input type="hidden" name="id" value="" class="form-control">
										<input type="hidden" name="op_id_one" value="" class="form-control">
										<input type="submit" value="送出" class="form-control btn btn-primary"></td>
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
