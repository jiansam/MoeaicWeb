var now = new Date();
//var year = now.getFullYear() - 1911; //民國寫法
var year = now.getFullYear(); //西元寫法
var month = now.getMonth()+1;
var date = now.getDate();
var today = year + "/" + (month<10 ? '0' : '')+month + "/" + (date<10 ? '0' : '')+date;

/*  ===============================================
 *  共用ajax ：初始取出資料
 *  data   ：是每個不同類型的資料取出
 *  doThing：根據不同的doThing去不同的method處理回傳的資料
 */
function ajax_data(servletURL, data){
	var doThing = data.doThing;
	$.ajax({
		url : servletURL ,
		data : data,
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded",
		success : function(response) {
			if(doThing == "getWebTitle"){
				setWeb_TitleList(response);
			}
			else if(doThing == "remove_WebOrderOne" || doThing == "remove_WebNameOne"){
				//刪除一筆WebOrder or WebName 後轉頁，帶入EL
				window.location = servletURL + "?doThing=getWebName";
			}
			else if (doThing == "removeWeb_TitleOne") {
//				console.log("OVER")
				setWeb_TitleList(response);
			}
		},
	});
}

/*  ===========================================
 *  共用submit
 *  id ：判定是從哪一個網頁進來，發動Ajax前要處理一些步驟
 */
function submitContent(form, id){
	if(confirm("確定要送出資料？")==false){
		return;
	}
	var nextFuntion = "";
	var contentType = "application/x-www-form-urlencoded";

	//相關網站-分類管理
	if(id == "WebTitle"){
		nextFuntion = "getWeb_TitleList";
	}
	
	ajax_form(form, pagesURL, nextFuntion, contentType);
}


/*  ===========================================
 *  共用ajax
 * 	form      :網頁上的form，要輸入到資料庫的
 * 	servletURL：要使用的Servlet路徑
 * 	method    ：成功後，要找哪一個method處理response
 */
function ajax_form(form, servletURL, method, contentType){
	$.ajax({
		url : servletURL ,
		data : $(form).serialize(),
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : contentType,
		success : function(response) {
			if(method=="getWeb_TitleList"){
				$("#dialog-webTitle").dialog("close");
				setWeb_TitleList(response);
			}
		},
	});
}

/* 共用Dialog
 * 先用setDialog設定顯示模式，再用openDialog傳進資料打開畫面
 */
function setDialog(id, width, isModal){
	$(id).dialog({
		autoOpen : false,
		show : { effect : "blind", duration : 300},
		hide : { effect : "explode", duration : 300},
		width : width,
		beforeClose : function(){
			$(id).html("");
		},
		modal : isModal
	});
}

function openDialog(id, data, url){
	
	$.ajax({
		url : url, //contextPath + "/console/websiteLinks/edit-webNameOne.jsp"
		data: data,
		cache: false,
		type: "POST",
		error : function(xhr) {
			$("#"+id).html("資料讀取失敗，請稍後重試！");
			$("#"+id).dialog("open");
		},
		success : function(response) {
			$("#"+id).html(response);
			$("#"+id).dialog("open");
			
			if(id == "dialog-webOrder"){
				setWeb_Order(data);
			}
			else if(id == "dialog-webTitle"){
				setWeb_TitleOne(data);
			}
		},
	});
}

function closeDiv(id){
	$("#"+id).hide();
}

/* ========================================================================================================
 * 相關網站-分類管理 Web_Title
 */

function new_title(id){
	var data = {
				"id" : id,
				"ch_title" : "",
				"en_title" : "",
				"sort" : "99",
				}
	var url = contextPath + "/console/websiteLinks/edit-webTitleOne.jsp";
	openDialog("dialog-webTitle", data, url);
}

function setWeb_TitleOne(data){
	$("#web_TitleOneDiv").show();
	$("#web_TitleOne input[name='ch_title']").val(data.ch_title);
	$("#web_TitleOne input[name='en_title']").val(data.en_title);
	$("#web_TitleOne input[name='sort']").val(data.sort);
	$("#web_TitleOne input[name='id']").val(data.id);
}

function setWeb_TitleList(response){
	if(response == ""){
		return;
	}
	$("#web_TitleList #rows").html("");
	var json = JSON.parse(response);
	
	for(var n=0 ; n<json.length ; n++){
 		$('#web_TitleList #template').clone().removeAttr("id").appendTo("#web_TitleList #rows").show();
 		$('#web_TitleList #rows tr:last > .sort').text( json[n].sort); 
 		$('#web_TitleList #rows tr:last > .ch_title').text( json[n].ch_title );
 		$('#web_TitleList #rows tr:last > .en_title').text( json[n].en_title );
 		$('#web_TitleList #rows tr:last > td.id input[name="id"]').val( json[n].id );
 		
 	}
	
	//編輯一筆分類
	$("#web_TitleList .edit").each(function(index){
		$(this).on('click',function(){
			var sort = $(this).closest("tr").find("td:eq(0)").text();
			var ch_title = $(this).closest("tr").find("td:eq(1)").text();
			var en_title = $(this).closest("tr").find("td:eq(2)").text();
			var id = $(this).closest("tr").find("td:eq(3) > input[name='id']").val();
			
			var data = {
					"id" : id,
					"ch_title" : ch_title,
					"en_title" : en_title,
					"sort" : sort,
					}
			var url = contextPath + "/console/websiteLinks/edit-webTitleOne.jsp";
			openDialog("dialog-webTitle", data, url);
		});
	});
	
	//刪除一筆分類
	$("#web_TitleList .dele").each(function(index){
		$(this).on('click',function(){
			var id = $(this).closest("tr").find("td:eq(3) > input[name='id']").val();
			$.ajax({
				url : pagesURL ,
				data : {
						"doThing" : "isParentId_used",
						"id" : id
						},
				method : 'POST',
				cache : false, //IE不要cache （預設是true）
				async : true, //是否採用非同步（預設是true）
				contentType : "application/x-www-form-urlencoded",
				success : function(response) {
					if(response == "true"){
						if(alert("此分類項下有資料，請調整後再刪除")==false){
							return;
						}
					}else{
						if(confirm("確定要刪除此筆分類？")==false){
//							console.log("不刪")
							return;
						}else {
							var data = { "doThing" : "removeWeb_TitleOne", "id" : id };
//							console.log(data)
							ajax_data(pagesURL, data);
						}
					}
				}
			});
			
		});
	});
}

/* ========================================================================================================
 * 相關網站-資料管理 Web_Name
 */

//function new_name(id){
//	var data = {
//			"id" : id,
//			"ch_name" : "",
//			"en_name" : "",
//			"web_titles" : "",
//			"url" : "",
//			"showIndex" : "false",
//			"update_date" : today,
//			"photoFile" : ""
//			};
////	var url = contextPath + "/console/websiteLinks/edit-webNameOne.jsp";
////	openDialog("dialog-webName", data, url);
//	setWeb_NameOne(data);
//}

//function setWeb_NameOne(data){
//	$("#web_NameOneDiv").show();
//	$("#web_NameOne input[name='ch_name']").val(data.ch_name);
//	$("#web_NameOne input[name='en_name']").val(data.en_name);
//	setCheckbox("web_NameOne", "web_titles",data.web_titles);
//	$("#web_NameOne input[name='url']").val(data.url);
//	$("#web_NameOne input[name='update_date']").val(data.update_date);
//	$("#web_NameOne input[name='id']").val(data.id);
//	
//	if(data.showIndex == "true"){
//		$("#web_NameOne select[name='showIndex']").val("1");
//	}else{
//		$("#web_NameOne select[name='showIndex']").val("0");
//	}
//	
//	if(data.photoFile == ""){
//		$("#web_NameOne #photo").hide();
//	}else{
//		$("#web_NameOne #photo").prop("src" , contextPath + data.photoFile).show();
//	}
//	
//	if(data.id == 0){
//		$("#web_NameOne #trPhoto").hide();
//	}else{
//		$("#web_NameOne #trPhoto").show();
//	}
//	$("#web_NameOneDiv").show();
//}


function setCheckbox(tag,name,valueStr){
	if(valueStr.length!=0){
		var ary=valueStr.split(",");
	    $("#"+tag+" input[name='"+name+"']").each(function(){
			    var v= $(this).val();
			    if($.inArray(v,ary)!=-1){
			    	$(this).prop("checked",true);
		     	}
			    else{
			    	$(this).prop("checked",false);
		     	}
		    });
	}
}
function checkForm(){
	var titles = $('input:checkbox:checked[name="web_titles"]').map(function() {
		 return $(this).val(); }).get().join(",");
	if(titles == ""){
		alert("請至少選一個分類");
		return false;
	}
	
	var name = $("#web_NameOne input[name='name']").val();
	var url = $("#web_NameOne input[name='url']").val();
	if(name==""){
		alert("請至少填入名稱");
		return false;
	}
	
	var showIndex = $("#web_NameOne select[name='showIndex']").val();
	var photoFile = $("#web_NameOne input[name='photoFile']").val();
	if(showIndex == "1"){
		if(photoFile == "" && photoFileUrl == ""){
			alert("若預計顯示於首頁，請上傳圖檔。");
			return false;
		}
	}
	return confirm('確定要送出資料？');
}

function list_sort(index){
	var thisTR = $(index).closest("tr");
	var data = {
			"doThing" : "update_webOrderOne",
			"sort" : $(thisTR).find("td:eq(0)").text(),
			"webOrder_id" : $(thisTR).find("td:eq(4) > input[name='webOrder_id']").val(),
	};
	var url = contextPath + "/console/websiteLinks/edit-webOrder.jsp";
	openDialog("dialog-webOrder", data, url);
}
function setWeb_Order(data){
	$("#web_OrderOne input[name='doThing']").val(data.doThing);
	$("#web_OrderOne input[name='sort']").val(data.sort);
	$("#web_OrderOne input[name='id']").val(data.webOrder_id);
}


function list_edit(index){
	var thisTR = $(index).closest("tr");
	var data = {
			"id" : $(thisTR).find("td:eq(4) > input[name='webName_id']").val(),
			"ch_name" : $(thisTR).find("td:eq(3)").text(),
			"en_name" : $(thisTR).find("td:eq(4) > input[name='en_name']").val(),
			"web_titles" : $(thisTR).find("td:eq(4) > input[name='web_titles']").val(),
			"url" : $(thisTR).find("td:eq(4) > input[name='url']").val(),
			"showIndex" : $(thisTR).find("td:eq(4) > input[name='showIndex']").val(),
			"update_date" : $(thisTR).find("td:eq(1)").text(),
			"photoFile" : $(thisTR).find("td:eq(4) > input[name='photoFile']").val()
			};


	setWeb_NameOne(data);
}

function list_dele(index){
	if(confirm('確定要刪除資料？') == false){
		return;
	}
	var thisTR = $(index).closest("tr");
	var data = {
			"doThing" : "remove_WebOrderOne", //remove_WebNameOne（完整刪除） or remove_WebOrderOne（只刪該order下排序）
			"webOrder_id" : $(thisTR).find("td:eq(4) > input[name='webOrder_id']").val(),
			"webName_id" : $(thisTR).find("td:eq(4) > input[name='webName_id']").val(),
			"webTitle_id" : $(thisTR).find("td:eq(4) > input[name='webTitle_id']").val(),
			"web_titles" : $(thisTR).find("td:eq(4) > input[name='web_titles']").val(),
	};
	ajax_data(pagesURL, data);
}

function isHide(id, photoFile){
	$("#web_NameOne input[name='update_date']").val(today);
	
	if(id == "0"){
		$("#web_NameOne #trPhoto").hide();
	}
	else {
		$("#remobtn").show();
//		console.log("photoFile="+photoFile)
//		console.log(photoFile=="")
		if(photoFile==""){
			$("#web_NameOne #trPhoto").hide();
		}else{
			$("#web_NameOne #trPhoto").show();
			$("#web_NameOne #photo").prop("src" , contextPath + photoFile);
		}
	}
}
