var now = new Date();
//var year = now.getFullYear() - 1911; //民國寫法
var year = now.getFullYear(); //西元寫法
var month = now.getMonth()+1;
var date = now.getDate();
var today = year + "/" + (month<10 ? '0' : '')+month + "/" + (date<10 ? '0' : '')+date;
var t = now.getHours();
var h = now.getMinutes();


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
		contentType : "application/x-www-form-urlencoded", //傳給Servlet的格式
	  //dataType : "", //若使用text回傳字串，若使用json就可以少一個JSON.parse轉譯 
						 //[default: Intelligent Guess (xml, json, script, or html)]
		success : function(response) {

//改用EL，因為JSON遇到html語法 <p> 無法辨識處理	
//			if (doThing == "getPagesNews_One") {
//				if(response == ""){
//					return;
//				}
//				var json = JSON.parse(response)
//				setPagesNews(json);
//			}
			if(doThing == "getFile_list"){
				setFile_list(response);
			}
			else if(doThing == "removePagesNews_One"){
				//刪除一筆PagesNews後轉頁，帶入EL
				//window.location = servletURL + "?doThing=" + data.type;
				window.location = contextPath + "/console/pagesNews/" + data.type + ".jsp"; //DataTable ajax轉頁
			}
			else if(doThing == "remove_fileListOne"){
				setFile_list(response);
			}
		},
	});
}

/*  ===========================================
 *  共用submit
 *  id ：判定是從哪一個網頁進來，發動Ajax前要處理一些步驟
 */
function submitContent(form, id){
	var nextFuntion = "";
	var contentType = "application/x-www-form-urlencoded";

	//研究報告
	if(id == "pagesNews_basic"){
		//  newContent = ch_content.document.getBody().getText(); //取得html文本 
		var ch_content = CKEDITOR.instances.ch_content.getData();
		$("#ch_content").val(ch_content);
		
		var en_content = CKEDITOR.instances.en_content.getData();
		$("#en_content").val(en_content);
		nextFuntion = "setId";
		
		var publish_date = $("#basic_ch input[name='publish_date']").val();
		var publish_time = $("#basic_ch input[name='publish_time']").val();
		if(publish_date == "" || publish_time == ""){
			alert("請填入完整發佈時間");
			return;
		}
        if(publish_time.indexOf(':')<0){
              alert("請確認時間欄是否有輸入半型冒號(例如：09:00)");  //2021/08/18新增
              return;
        }		
		
		if(confirm("確定要送出資料？")==false){
			return;
		}
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
			alert("已更新完畢");
			if(method=="setId"){
				setId(response); //response = pagesNews_id
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
			
			if(id == "file-dialog"){
				setFile_one_Dialog(data);
			}
			else if (id == "order-dialog"){
				setOrder_Dialog(data);
			}
		},
	});
}

function closeDiv(id){
	$("#"+id).hide();
	if(id == "one_Div"){
		//編輯視窗關閉就轉頁
		window.location = pagesURL + "?doThing=studyReport";
	}
}

function setCKeditor(id){
	return CKEDITOR.replace(id,{height: 350});
}


/* ========================================================================================================
 * PagesNews 共用function
 */

//改用EL	
//function new_pagesNews(id){
//	var data = {
//			"id" : id,
//			"ch_title" : "",
//			"ch_content" : "",
//			"en_title" : "",
//			"en_content" : "",
//			"publish_date" : "",
//			"publish_APM" : "AM",
//			"publish_time" : "",
//			"showTop" : "0",
//			"sort" : "99",
//			"type" : type,
//			"doThing" : "newPagesNews"
//	}
//	setPagesNews(data);
//}

function setId(response){
	if(response == ""){
		return;
	}
	var json = JSON.parse(response);
	$("#one_Div input[name='id']").val(json.pagesNews_id);
	$("#one_Div #basic_ch input[name='doThing']").val(json.doThing+"_ch"); //updatePagesNews_ch
	$("#one_Div #basic_en input[name='doThing']").val(json.doThing+"_en"); //updatePagesNews_en
	$("#file_btn_ch, #file_btn_en, #file_List_ch, #file_List_en").show();
}

//改用轉頁，不直接秀在下方
//function edit_pagesNews(index){
//	var id = $(index).closest("tr").find("td:eq(5) > input[name='id']").val();
//	console.log("id="+id)
//	var data = {
//			"doThing" : "getPagesNews_One",
//			"id" : id,
//	}
//	ajax_data(pagesURL, data);
//}

function setTopOrder(index){
	var id = $(index).closest("tr").find("td:eq(5) > input[name='id']").val();
	var sort = $(index).closest("tr").find("td:eq(0)").text();
	var showTop = $(index).closest("tr").find("td:eq(1)").text();
	
	var data = {
			"id" : $(index).closest("tr").find("td:eq(5) > input[name='id']").val(),
			"sort" : $(index).closest("tr").find("td:eq(0)").text(),
			"showTop" : $(index).closest("tr").find("td:eq(1)").text(),
			"doThing" : "setPagesNews_TopOrder",
			"type" : type
	}
	openDialog("order-dialog", data, contextPath + "/console/pagesNews/edit-pagesNewsOrder.jsp");
}

function setOrder_Dialog(data){
	$("#pagesNews_Order select[name='showTop']").val(data.showTop);
	$("#pagesNews_Order input[name='sort']").val(data.sort);
	$("#pagesNews_Order input[name='old_sort']").val(data.sort);
	$("#pagesNews_Order input[name='id']").val(data.id);
	$("#pagesNews_Order input[name='doThing']").val(data.doThing);
	$("#pagesNews_Order input[name='type']").val(data.type);
	checkShowTop('pagesNews_Order');
}
function checkShowTop(id){
	var showTop = $("#"+id+" select[name='showTop']").val();
	if(showTop == "是" || showTop == "1"){
		$("#"+id+" input[name='sort']").prop("disabled" , false);
	}else{
		$("#"+id+" input[name='sort']").prop("disabled" , true).val("99");
	}
}

function dele_pagesNews(index){
	if(confirm("確定要刪除資料？")==false){
		return;
	}
	var id = $(index).closest("tr").find("td:eq(5) > input[name='id']").val();
	var data = {
			"doThing" : "removePagesNews_One",
			"id" : id,
			"type" : type
	}
	ajax_data(pagesURL, data);
}

//function setPagesNews(response){
//	if(response == ""){
//		return;
//	}
//	console.log(response)
//	var data = JSON.parse(response);
//	
//	$("#one_Div input[name='id']").val(data.id); //0 or num
//	
//	$("#one_Div #basic_ch input[name='ch_title']").val(data.ch_title); //用這個無法放入ckEditor裡面，要用下行作法
//	CKEDITOR.instances.ch_content.setData(data.ch_content);
//	
//	$("#one_Div #basic_en input[name='en_title']").val(data.en_title);
//	CKEDITOR.instances.ch_content.setData(data.en_content);
//	
//	$("#one_Div input[name='publish_date']").val(data.publish_date);
//	$("#one_Div select[name='publish_APM']").val(data.publish_APM);
//	$("#one_Div input[name='publish_time']").val(data.publish_time);
//	
//	$("#one_Div select[name='showTop']").val(data.showTop);//隱藏版
//	$("#one_Div #showTopText").text(data.showTop==0?"否":"是");
//
//	$("#one_Div input[name='sort']").val(data.sort);//隱藏版
//	$("#one_Div #sortText").text(data.sort);
//	
//	$("#one_Div input[name='type']").val(data.type); //studyReport
//	
//	$("#one_Div #basic_ch input[name='doThing']").val(data.doThing+"_ch"); //new_ch or updatePagesNews_ch
//	$("#one_Div #basic_en input[name='doThing']").val(data.doThing+"_en"); //new_en or updatePagesNews_en
//	
//	if(data.id != 0){
//		getFile_list(data.id);
//	}
//	
//	$("#one_Div").show();
//}

function getFile_list(id){
	var data = {
			"doThing" : "getFile_list",
			"pagesNews_id" : id
	}
	$("#file_btn_ch, #file_btn_en, #file_List_ch, #file_List_en, #remobtn").show();
	ajax_data(pagesURL, data)
}

function setFile_list(response){ //把取得的某一筆眾多file放入(ch & en)
	if(response == ""){
		return;
	}
	
	$("#file_List_ch #rows, #file_List_en #rows").html("");
	var json = JSON.parse(response);
	
	for(var n=0 ; n<json.length ; n++){
		var id;
		if(json[n].file_lang == "ch"){
			id = "file_List_ch";
		}else{
			id = "file_List_en";
		}
 		$('#'+id+' #template').clone().removeAttr("id").appendTo("#"+id+" #rows").show();
 		
 		var name_1 = '<a href="' + contextPath + '/download-file.jsp?id=';
 		var name_2 = '" class="hover-red">';
 		var name_3 = '</a>';
 		var file_name = name_1 + json[n].id_str + name_2 + json[n].file_name + name_3;
 		
 		$('#'+id+' #rows tr:last > .file_name').html( file_name ); 
 		$('#'+id+' #rows tr:last > td.file_id input[name="file_id"]').val( json[n].id );
 		$('#'+id+' #rows tr:last > td.file_id input[name="pagesNews_id"]').val( json[n].pagesNews_id );
 	}
}

function remove_listOne(index){
	var id =  $(index).closest("tr").find("td:eq(1) > input[name='file_id']").val();
	var pagesNews_id =  $(index).closest("tr").find("td:eq(1) > input[name='pagesNews_id']").val();
	var data = {
			"doThing" : "remove_fileListOne",
			"id" : id,
			"pagesNews_id" : pagesNews_id
	}
	ajax_data(pagesURL, data);
}

function update_listOne(lang, index){
	var id =  $(index).closest("tr").find("td:eq(1) > input[name='file_id']").val();
	var pagesNews_id =  $(index).closest("tr").find("td:eq(1) > input[name='pagesNews_id']").val();
	var data = {
			"doThing" : "updateFile_" + lang, //newFile_ch
			"id" : id,
			"pagesNews_id" : pagesNews_id,
			"file_lang" : lang
	}
	var url = contextPath + "/console/pagesNews/fileUpload.jsp"
	openDialog("file-dialog", data, url) 
}

function new_pagesNewsFile(lang){
	var pagesNews_id = $("#one_Div #basic_ch input[name='id']").val();
	var doThing = "newFile_";
	var data = {
			"doThing" : doThing + lang, //newFile_ch
			"id" : "0",
			"pagesNews_id" : pagesNews_id,
			"file_lang" : lang
	}
	var url = contextPath + "/console/pagesNews/fileUpload.jsp"
	openDialog("file-dialog", data, url) 
	
}



function setFile_one_Dialog(data){
	$("#file_One #doThing").val(data.doThing);
	$("#file_One #pagesNews_id").val(data.pagesNews_id);
	$("#file_One #file_lang").val(data.file_lang);
	$("#file_One #id").val(data.id);
}


function setFile_ROCDate(index){
	$(index).datepicker({
		dateFormat: "yy/mm/dd",
		changeYear: true, //手動修改年
		changeMonth: true, //手動修改月			
		/*showButtonPanel:true,*/ 
		/*maxDate: "+0",*/
		onSelect: function (dateText, inst) {
			var dateFormate = inst.settings.dateFormat == null ? "yy/mm/dd" : inst.settings.dateFormat; //取出格式文字
			var reM = /m+/g;
			var reD = /d+/g;
			var year = inst.selectedYear - 1911 < 0 ? inst.selectedYear : inst.selectedYear - 1911;
			if(String(year).length < 3){
				year = "0" + year;
			}
			var month = parseInt(inst.selectedMonth,10) > 8  ? inst.selectedMonth + 1 : "0" + String(inst.selectedMonth + 1);
			var date = String(inst.selectedDay).length != 1 ? inst.selectedDay : "0" + String(inst.selectedDay);
			
			inst.input.val(year + "/" + month + "/" + date);
			$("#basic_ch input[name='publish_date']").val(year + "/" + month + "/" + date);
			$("#basic_en input[name='publish_date']").val(year + "/" + month + "/" + date);
		}
	});
}

function setPublish_date(){
	if(t > 11){
		$("select[name='publish_APM']").val("PM");
		if(t > 12){
			t = t - 12;
		}
	}
	var todayTime = (t<10 ? '0' : '')+t + ":" + (h<10 ? '0' : '')+h;
	$("input[name='publish_date']").val(today);
	$("input[name='publish_time']").val(todayTime);
}

//預約上稿時間、置頂、排序：只要有變動，就中文英文版兩邊同步
function getValue(lang, tag){
	var temp;
	if(lang == "ch"){
		temp = $("#basic_ch #"+tag).val();
		$("#basic_en #"+tag).val(temp);
		
	}else if(lang == "en"){
		temp = $("#basic_en #"+tag).val();
		$("#basic_ch #"+tag).val(temp);		
	}
}

