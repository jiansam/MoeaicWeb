<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row" id="web_NameOneDiv">
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-body">
				<div class="table-responsive">
					<form action="${pageContext.request.contextPath}/console/edit-pages.view" role="form" method="post"
						onsubmit="return confirm('確定要送出資料？')">
						<table class="table table-striped table-bordered table-hover" id="pagesNews_Order">
							<tbody>

								<tr>
									<td width="35%" >是否置頂</td>
									<td>
										<select name="showTop" class="form-control" onchange="checkShowTop('pagesNews_Order')" style="height: 40px !important;">
											<option value="否">否</option>
											<option value="是">是</option>
										</select>
									</td>
								</tr>

								
								<tr>
									<td width="35%" nowrap="nowrap">自動接續排列</td>
									<td>
										<select name="autoSort" class="form-control" style="height: 40px !important;">
											<option value="0">不調整排序</option>
											<option value="1">自動接續排序</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td width="35%" >置頂順序</td>
									<td><input type="text" name="sort" value="" class="form-control" disabled="disabled"></td>
								</tr>								

								<tr>
									<td colspan="2" align="center">
										<input type="hidden" name="doThing" value="" class="form-control">
										<input type="hidden" name="type" value="" class="form-control">
										<input type="hidden" name="old_sort" value="" class="form-control">
										<input type="hidden" name="id" value="" class="form-control">
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
