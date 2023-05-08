<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script type="text/javascript">
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var photoFileUrl = "${webNameOne.photoFile}";
		$(document).ready(function() {
// 			setROCDate("rocdate"); //民國年寫法
			$(".rocdate").datepicker({
				showOn: "focus",
				changeMonth: true,
				changeYear: true,
				dateFormat: "yy/mm/dd",
				//yearRange:"-5:+5",
				defaultDate: today,
			});
			isHide("${webNameOne.id}", photoFileUrl) //判斷圖片預覽、是否該隱藏   & 放入今天日期
			setCheckbox("web_NameOne", "web_titles", "${webNameOne.web_titles}"); //將checkbox的值放入
		});
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/sort-common.js"></script>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>資料管理</b></h2>
                </div>
            </div>
            
            
            <div class="row">
				<div class="col-lg-12" >
					<a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=getWebName">
						<input type="button" value="返回列表" class="btn btn-primary" onclick="return confirm('是否要放棄本次編輯？')"
							style="float:right;margin-bottom:5px;"/>
					</a>
					
					<c:url value="/console/edit-pages.view" var="remo" scope="page">
						<c:param name="webName_id"  value="${webNameOne.id}"/>
						<c:param name="doThing"  value="remove_WebNameOne"/>
					</c:url>				
					<a href="${remo}">
						<input type="button" id="remobtn" value="刪除此筆網址資料" class="btn btn-primary" 
							onclick="return confirm('確定永久刪除此筆資料？')" style="float:left;margin-bottom:5px;display:none;" />
					</a>
				</div>
			</div> 


			<div class="row" id="web_NameOneDiv">
			
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">連結資料編輯</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive">
								<form action="${pageContext.request.contextPath}/console/edit-WebNameImage.view"
									onsubmit="return checkForm();" role="form" enctype="multipart/form-data" method="post">
									<table class="table table-striped table-bordered table-hover"
										id="web_NameOne">
										<tbody>
			
											<tr id="ch_tr">
												<td width="12%">網站名稱</td>
												<td><input type="text" name="name" value="${webNameOne.name}" class="form-control"></td>
											</tr>

											<tr>
												<td>選擇分類</td>
												<td><c:forEach var="data" items="${web_titles}">
														<input id="tit${data.id}" type="checkbox" name="web_titles"
															value="${data.id}">
														<label for="tit${data.id}">${data.ch_title}&nbsp;&nbsp;</label>
													</c:forEach></td>
											</tr>
			
											<tr>
												<td>網址</td>
												<td><input type="text" name="url" value="${webNameOne.url}" class="form-control"></td>
											</tr>
			
											<tr>
												<td nowrap="nowrap">顯示於首頁</td>
												<td><select name="showIndex" class="form-control">
														<option value="0" ${webNameOne.showIndex==false?'selected="selected"':''}>否</option>
														<option value="1" ${webNameOne.showIndex==true?'selected="selected"':''}>是</option>
												</select></td>
											</tr>

											<tr>
												<td>更新日期</td>
												<td><input type="text" name="update_date" value="${webNameOne.update_date_AD}" class="form-control rocdate"> 
													<input type="hidden" name="id" value="${webNameOne.id}" class="form-control">
													<input type="hidden" name="lang" value="${webNameOne.lang}" class="form-control">
												</td>
											</tr>
			
											<tr style="border-bottom: none;">
												<td>圖檔上傳<br>(178x77)</td>
												<td>
													<input type="file" name="photoFile" style="height:auto;" accept="image/*" class="form-control"></td>
											</tr>
			
											<tr id="trPhoto">
												<td>圖片預覽</td>
												<td><img id="photo" width="120" src=""/></td>
											</tr>
			
											<tr>
												<td colspan="2" align="center">
													<input type="submit" value="送出" class="form-control btn btn-primary" style="width: 15%;"></td>
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


        </div>
    </div>
<console:footer />      
</body>
</html>