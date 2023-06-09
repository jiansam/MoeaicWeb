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
		var id = "${pagesNewsOne.id}"; 
		var type = "${title.type}";
		
		$(document).ready(function() {
			if(type == "studyReport"){
				$("#enli, #en").hide();
			}
			
// 			setFile_ROCDate(".file_roc"); //民國年寫法
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
				//console.log("id="+id)
				getFile_list(id);
				$(".spanNote").hide();
			}else{
				setPublish_date();
			}
			checkShowTop("tabs");
			
			$('#photo_ch').change(function() {
			  var file = $('#photo_ch')[0].files[0];
			  var reader = new FileReader;
			  reader.onload = function(e) {
			    $('#photo_review_ch').attr('src', e.target.result);
			  };
			  reader.readAsDataURL(file);
			});
			
			$('#photo_en').change(function() {
				  var file = $('#photo_en')[0].files[0];
				  var reader = new FileReader;
				  reader.onload = function(e) {
				    $('#photo_review_en').attr('src', e.target.result);
				  };
				  reader.readAsDataURL(file);
			});
			
			 $('input:radio[name=image_type_ch]').change(function () {
	            if ($("input[name='image_type_ch']:checked").val() == '0') {
	               $("#rss_image_ch_td").show();
	               $("#photo_ch_td").hide();
	               $("#photo_review_ch_td").hide();
	            } else {
	               $("#rss_image_ch_td").hide();
		           $("#photo_ch_td").show();
		           $("#photo_review_ch_td").show();
	            }
	        });
			 
			 $('input:radio[name=image_type_en]').change(function () {
	            if ($("input[name='image_type_en']:checked").val() == '0') {
	               $("#rss_image_en_td").show();
	               $("#photo_en_td").hide();
	               $("#photo_review_en_td").hide();
	            } else {
	               $("#rss_image_en_td").hide();
		           $("#photo_en_td").show();
		           $("#photo_review_en_td").show();
	            }
	        });
			
		});
		
		</script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pagesNews-common.js"></script>  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/console/pagesNews-common.js?t=20230508"></script> <!--20210818修改-->
	
	<style>
	.hover-red:hover{
		color: red;
	}
	</style>
</head>


<body>
	<div id="wrapper">
		<console:navigation />
		
        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="page-header"><b>編輯 ${title.typeName}</b></h2>
                </div>
            </div>
            
            <div class="row">
				<div class="col-lg-12" >
				<c:choose>
					<c:when test="${title.type eq 'new_ann'}">
						<a href="${pageContext.request.contextPath}/console/pagesNews/${title.type}.jsp">
							<input type="button" value="返回列表" class="btn btn-primary" 
								style="float:right;margin-bottom:5px;"/>
						</a>						
					</c:when>
					
					<c:when test="${title.type eq 'business_ann'}">
						<a href="${pageContext.request.contextPath}/console/pagesNews/${title.type}.jsp">
							<input type="button" value="返回列表" class="btn btn-primary" 
								style="float:right;margin-bottom:5px;"/>
						</a>						
					</c:when>					
					
					<c:when test="${title.type eq 'studyReport'}">
						<a href="${pageContext.request.contextPath}/console/pagesNews/${title.type}.jsp">
							<input type="button" value="返回列表" class="btn btn-primary" 
								style="float:right;margin-bottom:5px;"/>
						</a>						
					</c:when>	
										
				</c:choose>
				
					
					<c:url value="/console/edit-pages.view" var="remo" scope="page">
						<c:param name="id"  value="${pagesNewsOne.id}"/>
						<c:param name="doThing"  value="removePagesNews_One_btn"/>
						<c:param name="type"  value="${pagesNewsOne.type}"/>
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
									<form id="from_ch" role="form" method="post" enctype="multipart/form-data" >
										<table id="basic_ch" class="table table-striped table-bordered table-hover">
											<tbody>
				
												<tr>
													<td>中文標題</td>
													<td><input type="text" name="ch_title" value="${pagesNewsOne.ch_title}" class="form-control"></td>
												</tr>
				
												<tr>
													<td>中文內容</td>
													<td>
														<textarea rows="10" id="ch_content" name="ch_content" style="width:100%;"
															class="form-control" >${pagesNewsOne.ch_content}</textarea>
														<script type="text/javascript">
															var page_content_CH = setCKeditor("ch_content");
														</script>
													</td>
												</tr>
												
												<tr>
													<td nowrap="nowrap" width="15%">發佈時間</td>
													<td>
														<table>
															<tr>
																<td>
																	<input type="text" name="publish_date" value="${pagesNewsOne.publish_date}" class="form-control file_roc" 
																		style="height:40px !important;" size="10" id="publish_date" onchange="getValue('ch','publish_date')"></td>
																	
																<td>
																	<select name="publish_APM" class="form-control" id="publish_APM" 
																		onchange="getValue('ch','publish_APM')" style="height:40px !important;">
																			<option value="AM" ${pagesNewsOne.publish_APM == 'AM' ? 'selected="selected"' : ''}>AM</option>
																			<option value="PM" ${pagesNewsOne.publish_APM == 'PM' ? 'selected="selected"' : ''}>PM</option>
																	</select></td>
																														
																<td>
																	<input type="text" name="publish_time" id="publish_time" value="${pagesNewsOne.publish_time}" class="form-control" 
																		style="height:40px !important;" size="5"
																		onchange="getValue('ch','publish_time')"></td>
																
																<td><span style="color:gray;">&nbsp;&nbsp;[範例 2016/10/19 PM 02:00]</span></td>															
															</tr>
															
														</table>
													</td>
												</tr>
				
												<tr>
													<td nowrap="nowrap">置頂</td>
													<td id="showTopText">
														<select name="showTop" class="form-control" id="showTop" onclick="checkShowTop('tabs')"
															onchange="getValue('ch','showTop')" style="height: 40px !important;width:150px;">
															<option value="0" ${pagesNewsOne.showTop==0?'selected="selected"':''}>否</option>
															<option value="1" ${pagesNewsOne.showTop==1?'selected="selected"':''}>是</option>
														</select>
													</td>
												</tr>
	
												<tr>
													<td>排序</td>
													<td id="sortText">
														<input type="text" name="sort" value="${pagesNewsOne.sort}" id="sort" class="form-control" 
															onchange="getValue('ch','sort')" style="width:150px;"> 
													</td> 
												</tr>
												
												<tr>
													<td>經濟部RSS介接圖片 格式 ${pagesNewsOne.image_type_ch }</td>
													<td>
														<input type="radio" name="image_type_ch"   value="0"  ${ pagesNewsOne.image_type_ch == "0" ? 'checked="checked"' : '' } /> 預設
														<input type="radio" name="image_type_ch"   value="1"  ${ pagesNewsOne.image_type_ch == "1" ? 'checked="checked"' : '' }/> 上傳
													</td> 
												</tr>
												
												<c:choose>
													<c:when test="${title.type eq 'new_ann'}">
														<tr id="rss_image_ch_td"   ${ pagesNewsOne.image_type_ch == "1" ? 'style="display: none;"' : '' }>
															<td>經濟部RSS介接圖片</td>
															<td>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/10.jpg" 
																${pagesNewsOne.rss_image_ch == "/images/news/default/10.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/10.jpg" height="150">
															</label> 
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/003.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/003.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/003.jpg" height="150">
															</label>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/04.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/04.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/04.jpg" height="150">
															</label>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/005.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/005.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/005.jpg" height="150">
															</label><br/>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/05.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/05.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/05.jpg" height="150">
															</label>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/01.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/01.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/01.jpg" height="150">
															</label>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/013.jpg"
																${pagesNewsOne.rss_image_ch == "/images/news/default/013.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/013.jpg" height="150">
															</label>
															<label>
																<input type="radio" name="rss_image_ch" value="/images/news/default/014.jpg"
																${pagesNewsOne.rss_image == "/images/news/default/014.jpg"? 'checked="checked"':''}>
																<img src="${pageContext.request.contextPath }/images/news/default/014.jpg" height="150">
															</label>
															</td> 
														</tr>
														<tr id="photo_ch_td" ${ pagesNewsOne.image_type_ch == "0" ? 'style="display: none;"' : '' } >
														
															<td>經濟部RSS介接圖片</td>
															<td>
																<input id="photo_ch" type="file" name="photo_ch" style="height:auto;" accept="image/*" class="form-control"></td>
														</tr>
						
														<tr id="photo_review_ch_td" ${ pagesNewsOne.image_type_ch == "0" ? 'style="display: none;"' : '' }>
															<td>圖片預覽</td>
															<td>
															     <img id="photo_review_ch" width="150" src="${pagesNewsOne.photo_review_ch}"/>
															</td>
														</tr>
														<tr>
															<td>介接圖片中文說明<br/>(請使用半形文字)</td>
															<td>
																<input type="text" name="rss_text_ch" value="${pagesNewsOne.rss_text_ch}" style="width: 50%;">
																<input type="button" value="同標題文字" onclick="$('input[name=rss_text_ch]').val($('input[name=ch_title]').val())">
															</td>
														</tr>
														
													</c:when>
												</c:choose>
												
											
												
												<tr>
													<td colspan="2" style="padding-left:45%;">
														<input type="hidden" name="photo_ch_temp" value="${pagesNewsOne.photo_ch}" >
														<input type="hidden" name="type" value="${pagesNewsOne.type}" >
														<input  type="hidden" name="doThing" value="${pagesNewsOne.doThing}_ch" >
														<input type="hidden" name="id" value="${pagesNewsOne.id}" >
														<input type="button" value="送出" class="form-control btn btn-primary"
															onclick="submitContent( this.form , 'pagesNews_basic' , '${pagesNewsOne.doThing}_ch')" style="width:100px;">
														<span class="spanNote" style="color:red;">（點擊送出鈕後，下方開啟檔案上傳介面）</span></td>
												</tr>
												
											</tbody>
										</table>
									</form>
									
									<br>
									
									<hr>
									<a href="#fileList_ch" >
										<input type="button" id="file_btn_ch" value="新增檔案" class="btn btn-primary" 
											onclick="new_pagesNewsFile('ch');" style="display:none;float:right;margin-bottom:5px;"/>
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
														<img class="edit" onclick="update_listOne('ch',this)" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>											
													<a href="#file_List_ch">
														<img class="dele" onclick="remove_listOne(this)" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"></a>											
													<input type="hidden" name="file_id" value="">
													<input type="hidden" name="pagesNews_id" value="">
											</tr>
										</tfoot>
									</table>
										
								</div>
								<!-- /.table-responsive -->
					
					
								<!-- ===========================================================  -->
								<div class="table-responsive" id="en">
								
									<form role="form" method="post" >
										<table id="basic_en" class="table table-striped table-bordered table-hover">
											<tbody>
				
												<tr>
													<td>英文標題</td>
													<td><input type="text" name="en_title" value="${pagesNewsOne.en_title}" class="form-control"></td>
												</tr>
				
												<tr>
													<td>英文內容</td>
													<td>
														<textarea rows="10" id="en_content" name="en_content" style="width:100%;"
															class="form-control" >${pagesNewsOne.en_content}</textarea>
														<script type="text/javascript">
															var page_content_EN = setCKeditor("en_content");
														</script>
													</td>
												</tr>
												
												<tr>
													<td nowrap="nowrap" width="15%">發佈時間</td>
													<td>
														<table>
															<tr>
																<td>
																	<input type="text" name="publish_date" value="${pagesNewsOne.publish_date}" class="form-control file_roc" 
																		style="height:40px !important;" size="10" id="publish_date" onchange="getValue('en','publish_date')"></td>
																	
																<td>
																	<select name="publish_APM" class="form-control" id="publish_APM" 
																		onchange="getValue('en','publish_APM')" style="height:40px !important;">
																			<option value="AM" ${pagesNewsOne.publish_APM=='AM'?'selected="selected"':''}>AM</option>
																			<option value="PM" ${pagesNewsOne.publish_APM=='PM'?'selected="selected"':''}>PM</option>
																	</select></td>
																														
																<td>
																	<input type="text" name="publish_time" id="publish_time" value="${pagesNewsOne.publish_time}" class="form-control" 
																		style="height:40px !important;" size="5"
																		onchange="getValue('en','publish_time')"></td>
																														
																<td><span style="color:gray;">&nbsp;&nbsp;[範例 2016/10/19 PM 02:00]</span></td>															
															</tr>
														</table>
													</td>
												</tr>
				
												<tr>
													<td nowrap="nowrap">置頂</td>
													<td id="showTopText">
														<select name="showTop" class="form-control" id="showTop" onclick="checkShowTop('tabs')"
															onchange="getValue('en','showTop')" style="height: 40px !important;width:150px;">
															<option value="0" ${pagesNewsOne.showTop==0?'selected="selected"':''}>否</option>
															<option value="1" ${pagesNewsOne.showTop==1?'selected="selected"':''}>是</option>
														</select>
													</td>
												</tr>
	
												<tr>
													<td>排序</td>
													<td id="sortText">
														<input type="text" name="sort" value="${pagesNewsOne.sort}" id="sort" class="form-control" 
															onchange="getValue('en','sort')" style="width:150px;"> 
													</td> 
												</tr>
												<tr>
													<td>經濟部RSS介接圖片 格式</td>
													<td>
															<input type="radio" name="image_type_en"   value="0"  ${ pagesNewsOne.image_type_en == "0" ? 'checked="checked"' : '' } /> 預設
															<input type="radio" name="image_type_en"   value="1"  ${ pagesNewsOne.image_type_en == "1" ? 'checked="checked"' : '' }/> 上傳
													</td> 
												</tr>
										<c:choose>
											<c:when test="${title.type eq 'new_ann'}">
												<tr id="rss_image_en_td"  ${ pagesNewsOne.image_type_en == "1" ? 'style="display: none;"' : '' }>
													<td>經濟部RSS介接圖片</td>
													<td>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/10.jpg" 
														${pagesNewsOne.rss_image == "/images/news/default/10.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/10.jpg" height="150">
													</label> 
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/003.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/003.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/003.jpg" height="150">
													</label>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/04.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/04.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/04.jpg" height="150">
													</label>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/005.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/005.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/005.jpg" height="150">
													</label><br/>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/05.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/05.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/05.jpg" height="150">
													</label>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/01.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/01.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/01.jpg" height="150">
													</label>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/013.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/013.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/013.jpg" height="150">
													</label>
													<label>
														<input type="radio" name="rss_image" value="/images/news/default/014.jpg"
														${pagesNewsOne.rss_image == "/images/news/default/014.jpg"? 'checked="checked"':''}>
														<img src="${pageContext.request.contextPath }/images/news/default/014.jpg" height="150">
													</label>
													</td> 
												</tr>
												
												<tr id="photo_en_td"  ${ pagesNewsOne.image_type_en == "0" ? 'style="display: none;"' : '' }>
													<td>圖檔上傳</td>
													<td>
														<input type="file" id="photo_en" name="photo_en" style="height:auto;" accept="image/*" class="form-control"></td>
												</tr>
				
												<tr id="photo_review_en_td"  ${ pagesNewsOne.image_type_en == "0" ? 'style="display: none;"' : '' }>
													<td>圖片預覽</td>
													<td>
														<img id="photo_review_en" width="120" src=" ${pagesNewsOne.photo_review_en}"/>
													</td>
												</tr>
												<tr>
													<td>介接圖片英文說明<br/>(請使用半形文字)</td>
													<td>
														<input type="text" name="rss_text" value="${pagesNewsOne.rss_text}" style="width: 50%;">
														<input type="button" value="同標題文字" onclick="$('input[name=rss_text]').val($('input[name=en_title]').val())">
													</td>
												</tr>
												
											</c:when>
										</c:choose>
				
												<tr>
													<td colspan="2" style="padding-left:45%;">
														<input type="hidden" name="photo_en_temp" value="${pagesNewsOne.photo_en}" >
														<input type="hidden" name="type" value="${pagesNewsOne.type}" >
														<input type="hidden" name="doThing" value="${pagesNewsOne.doThing}_en" >
														<input type="hidden" name="id" value="${pagesNewsOne.id}" >
														<input type="button" value="送出" class="form-control btn btn-primary"
															onclick="submitContent(this.form, 'pagesNews_basic', '${pagesNewsOne.doThing}_en')" style="width: 100px;">
														<span class="spanNote" style="color:red;">（點擊送出鈕後，下方開啟檔案上傳介面）</span></td>
												</tr>
											</tbody>
										</table>
									</form>
									
									
									<hr>
									<a href="#fileList_en" >
										<input type="button" id="file_btn_en" value="新增檔案" class="btn btn-primary" 
											onclick="new_pagesNewsFile('en');" style="display:none;float:right;margin-bottom:5px;"/>
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
														<img class="edit" onclick="update_listOne('en',this)" src="${pageContext.request.contextPath}/images/icons/edit.png" width="24"></a>											
													<a href="#file_List_en">
														<img class="dele" onclick="remove_listOne(this)" src="${pageContext.request.contextPath}/images/icons/action_delete.gif" width="24"></a>											
													<input type="hidden" name="file_id" value="">
													<input type="hidden" name="pagesNews_id" value="">
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