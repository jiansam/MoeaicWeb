<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="console" tagdir="/WEB-INF/tags/console" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	
	<console:libraries />
	<title>經濟部投資審議委員會 - 後台管理</title>
	<script type="text/javascript">
		var now = new Date();
		var month = now.getMonth()+1;
		var today = now.getFullYear()+ "/" + (month<10 ? '0' : '')+month + "/" + now.getDate();
		var pagesURL = "${pageContext.request.contextPath}/console/edit-pages.view";
		var contextPath = "${pageContext.request.contextPath}";
		var id = "${businessPubOne.id}"; 
		var op_ids = "${businessPubOne.op_ids}";
		
		$(document).ready(function() {
			//setFile_ROCDate(".file_roc"); //民國年寫法
			$(".file_roc").datepicker({
				showOn: "focus",
				changeMonth: true,
				changeYear: true,
				dateFormat: "yy/mm/dd",
				//yearRange:"-5:+5",
				defaultDate: today,
			});
		
			setDialog("#file-dialog", 500, true);
			
			$("#tabs").tabs({
	 			width : $("#tabs").parent().width(), 
	 		});
			
			if(id != "0"){
				getFile_list_BP(id);
				$(".spanNote").hide();
			}else{
				setPublish_date();
			}
			
			//常見問題分類
			$(".select2").val("0");
			$(".select2").select2({
				allowClear: true,
				placeholder: "請輸入或選擇" //提示語
			}); 
			if("${businessPubOne.type}" == "14"){
				$(".QA").show();
				setSelect2Value("basic_ch", "ch_QA_type", "${businessPubOne.ch_QA_type}");
				setSelect2Value("basic_en", "en_QA_type", "${businessPubOne.en_QA_type}");
			}else{
				$(".QA").hide();
			}
			
			
			if(op_ids == ""){
				op_ids = "${title.op_id_one}";
			}
			setMutilelected("basic_ch", "op_ids", op_ids);
			setMutilelected("basic_en", "op_ids", op_ids);
			
			
			$(".MultSelect").multiselect({
				selectedList: 10,   checkAllText: '全選',   uncheckAllText: '清空',   noneSelectedText: '請勾選項目',
				selectedText: '已選擇 # 項', height: 'auto' })
			
			
				
			var op_id_one = "${title.op_id_one}";
			if( op_id_one == "7"){
				$("#enli, #en").hide();
			}
		});
		
		
		</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/buinessPub.js"></script>
	
	<style>
	.hover-red:hover{
		color: red;
	}
	
	.select2-search__field{
		width: auto !important;
	}
	</style>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>編輯公告 - ${title.op_id_oneName}</b></h2>
                </div>
            </div>
            
            <div class="row">
				<div class="col-lg-12" >
					<a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=businessPub&op_id_one=${title.op_id_one}">
						<input type="button" value="返回列表" class="btn btn-primary" 
							style="float:right;margin-bottom:5px;"/>
					</a>
					
					<c:url value="/console/edit-pages.view" var="remo" scope="page">
						<c:param name="id"  value="${businessPubOne.id}"/>
						<c:param name="doThing"  value="removeBusinessPub_One_btn"/>
						<c:param name="op_id_one"  value="${title.op_id_one}"/>
					</c:url>				
					<a href="${remo}">
						<input type="button" id="remobtn" value="刪除此筆資料" class="btn btn-primary" 
							onclick="return confirm('確定永久刪除此筆資料？')" style="float:left;margin-bottom:5px;display:none;" />
					</a>					
										
				</div>
			</div>            

			<div class="row" id="one_Div">
				<div class="col-lg-12" >
					<div class="panel panel-default">
					
						<div class="panel-body" >
						
							<div id="tabs" >
								<ul class="tabLi">
									<li><a href="#ch">中文版</a></li>
									<li id="enli"><a href="#en">英文版</a></li>
								</ul>
								
								<!-- ===========================================================  -->
								<div class="table-responsive" id="ch">
									<form role="form" method="post" >
										<table id="basic_ch" class="table table-striped table-bordered table-hover">
											<tbody>
												<tr>
													<td>資料類型</td>
													<td>
														<select name="type" id="type" style="height:35px;border-radius:5px;margin-bottom:10px;" onchange="getValue('ch','type')">
														<c:forEach	var="data" items="${optionManager.business_type}">
															<option value="${data.id}" ${data.id == businessPubOne.type? 'selected="selected"':''}>${data.name}</option>
														</c:forEach>
														</select>
													</td>
												</tr>
												
												<tr class="QA" >
													<td>中文分類/關鍵字</td>
													<td>
														<select name="ch_QA_type" id="ch_QA_type" class="select2" multiple="multiple" style="width:100%;"> 
														<c:forEach var="data" items="${optionManager.ch_QA_type}">
															<option value="${data.id}">${data.name}</option>	
														</c:forEach>
														</select>
													</td>
												</tr>												
												
												<tr>
													<td>中文標題</td>
													<td><input type="text" name="ch_title" value="${businessPubOne.ch_title}" class="form-control"></td>
												</tr>
				
												<tr>
													<td>中文內容</td>
													<td>
														<textarea rows="3" id="ch_content" name="ch_content" style="width:100%;"
															class="form-control" >${businessPubOne.ch_content}</textarea>
													</td>
												</tr>
												
												<tr>
													<td>業務類型</td>
													<td>
														<select name="op_ids" id="op_ids" class="MultSelect" multiple="multiple" style="width:750px;" onchange="getValue('ch','op_ids')"> 
														<c:forEach var="data" items="${optionManager.business_category}">
															<option value="${data.id}">${data.name}</option>	
														</c:forEach>
														</select>
														<span id="bus_span" style="color:red;"></span>
													</td>
												</tr>
												
												<tr>
													<td nowrap="nowrap" width="15%">發佈時間</td>
													<td>
														<table>
															<tr>
																<td>
																	<input type="text" name="publish_date" value="${businessPubOne.publish_date}" class="form-control file_roc" 
																		style="height:40px !important;" size="10" id="publish_date" onchange="getValue('ch','publish_date')"></td>
																	
																<td>
																	<select name="publish_APM" class="form-control" id="publish_APM" 
																		onchange="getValue('ch','publish_APM')" style="height:40px !important;">
																			<option value="AM" ${businessPubOne.publish_APM == 'AM' ? 'selected="selected"' : ''}>AM</option>
																			<option value="PM" ${businessPubOne.publish_APM == 'PM' ? 'selected="selected"' : ''}>PM</option>
																	</select></td>
																														
																<td>
																	<input type="text" name="publish_time" id="publish_time" value="${businessPubOne.publish_time}" class="form-control" 
																		style="height:40px !important;" size="5"
																		onchange="getValue('ch','publish_time')"></td>
																
																<td><span style="color:gray;">&nbsp;&nbsp;[範例 2016/10/19 PM 02:00]</span></td>															
															</tr>
															
														</table>
													</td>
												</tr>
				
												<tr>
													<td colspan="2" style="padding-left:45%;">
														<input type="hidden" name="doThing" value="${businessPubOne.doThing}_ch" >
														<input type="hidden" name="id" value="${businessPubOne.id}" size="3">
														<input type="button" value="送出" class="form-control btn btn-primary"
															onclick="submitContent(this.form, 'pagesNews_basic')" style="width: 100px;">
														<span class="spanNote" style="color:red;">（點擊送出鈕後，下方開啟檔案上傳介面）</span></td>
												</tr>
												
											</tbody>
										</table>
									</form>
									
									<br>
									
									<hr>
									<a href="#fileList_ch" >
										<input type="button" id="file_btn_ch" value="新增檔案" class="btn btn-primary" 
											onclick="new_businessPubFile('ch');" style="display:none;float:right;margin-bottom:5px;"/>
									</a>
									<table id="file_List_ch" class="table table-hover" style="display:none;;">
										<thead>
											<tr>
												<th>檔案名稱</th>
												<th width="8%">編輯</th>
											</tr>
										</thead>
										
										<tbody id="rows">
										</tbody>
										
										<tfoot>
											<tr id="template" style="display:none;">
												<td class="file_name"></td>
												<td class="file_id">
													<a href="#file_List_ch">
														<img class="edit" onclick="updateBPfile_btn('ch',this)" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>											
													<a href="#file_List_ch">
														<img class="dele" onclick="removeBPfile_btn(this)" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"></a>											
													<input type="hidden" name="file_id" value="">
													<input type="hidden" name="businessPub_id" value="">
											</tr>
										</tfoot>
									</table>
										
								</div>
								<!-- /.table-responsive -->
					
					
<!-- ==========================================================================================================  -->
								<div class="table-responsive" id="en">
								
									<form role="form" method="post" >
										<table id="basic_en" class="table table-striped table-bordered table-hover">
											<tbody>
												<tr>
													<td>資料類型</td>
													<td>
														<select name="type" id="type" style="height:35px;border-radius:5px;margin-bottom:10px;" onchange="getValue('en','type')">
														<c:forEach	var="data" items="${optionManager.business_type}">
															<option value="${data.id}" ${data.id == businessPubOne.type? 'selected="selected"':''}>${data.name}</option>
														</c:forEach>
														</select>
													</td>
												</tr>	

												<tr class="QA">
													<td>英文分類/關鍵字</td>
													<td>
														<select name="en_QA_type" id="en_QA_type" class="select2" multiple="multiple" style="width:100%;"> 
														<c:forEach var="data" items="${optionManager.en_QA_type}">
															<option value="${data.id}">${data.ename}</option>	
														</c:forEach>
														</select>
													</td>
												</tr>	
															
												<tr>
													<td>英文標題</td>
													<td><input type="text" name="en_title" value="${businessPubOne.en_title}" class="form-control"></td>
												</tr>
				
												<tr>
													<td>英文內容</td>
													<td>
														<textarea rows="3" id="en_content" name="en_content" style="width:100%;"
															class="form-control" >${businessPubOne.en_content}</textarea>
													</td>
												</tr>
												
												<tr>
													<td>業務類型</td>
													<td>
														<select name="op_ids" id="op_ids" class="MultSelect" multiple="multiple" style="width:750px;" onchange="getValue('en','op_ids')"> 
														<c:forEach var="data" items="${optionManager.business_category}">
															<option value="${data.id}">${data.name}</option>	
														</c:forEach>
														</select>
													</td>
												</tr>												
												
												<tr>
													<td nowrap="nowrap" width="15%">發佈時間</td>
													<td>
														<table>
															<tr>
																<td>
																	<input type="text" name="publish_date" value="${businessPubOne.publish_date}" class="form-control file_roc" 
																		style="height:40px !important;" size="10" id="publish_date" onchange="getValue('en','publish_date')"></td>
																	
																<td>
																	<select name="publish_APM" class="form-control" id="publish_APM" 
																		onchange="getValue('en','publish_APM')" style="height:40px !important;">
																			<option value="AM" ${businessPubOne.publish_APM=='AM'?'selected="selected"':''}>AM</option>
																			<option value="PM" ${businessPubOne.publish_APM=='PM'?'selected="selected"':''}>PM</option>
																	</select></td>
																														
																<td>
																	<input type="text" name="publish_time" id="publish_time" value="${businessPubOne.publish_time}" class="form-control" 
																		style="height:40px !important;" size="5"
																		onchange="getValue('en','publish_time')"></td>
																														
																<td><span style="color:gray;">&nbsp;&nbsp;[範例 2016/10/19 PM 02:00]</span></td>															
															</tr>
														</table>
													</td>
												</tr>
				
												<tr>
													<td colspan="2" style="padding-left:45%;">
														<input type="hidden" name="doThing" value="${businessPubOne.doThing}_en" >
														<input type="hidden" name="id" value="${businessPubOne.id}" >
														<input type="button" value="送出" class="form-control btn btn-primary"
															onclick="submitContent(this.form, 'pagesNews_basic')" style="width: 100px;">
														<span class="spanNote" style="color:red;">（點擊送出鈕後，下方開啟檔案上傳介面）</span></td>
												</tr>
											</tbody>
										</table>
									</form>
									
									<br>
									<hr>
									<a href="#fileList_en" >
										<input type="button" id="file_btn_en" value="新增檔案" class="btn btn-primary" 
											onclick="new_businessPubFile('en');" style="display:none;float:right;margin-bottom:5px;"/>
									</a>
									<table id="file_List_en" class="table table-hover" style="display:none;">
										<thead>
											<tr>
												<th>檔案名稱</th>
												<th width="8%">編輯</th>
											</tr>
										</thead>
										
										<tbody id="rows">
										
										</tbody>
										
										<tfoot style="display:none;" >
											<tr id="template">
												<td class="file_name"></td>
												<td class="file_id">
													<a href="#file_List_en">
														<img class="edit" onclick="updateBPfile_btn('en',this)" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>											
													<a href="#file_List_en">
														<img class="dele" onclick="removeBPfile_btn(this)" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"></a>											
													<input type="hidden" name="file_id" value="">
													<input type="hidden" name="businessPub_id" value="">
											</tr>
										</tfoot>
									</table>
										
								</div>
								<!-- /.table-responsive -->
								
								
							</div>
							
							
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
   
<div id="file-dialog" title="檔案上傳"></div>    
<console:footer />
</body>
</html>