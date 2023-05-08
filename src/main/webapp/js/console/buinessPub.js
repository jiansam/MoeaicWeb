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
			if(doThing == "getFile_list_BP"){
				setFile_list_BP(response);
			}
			else if(doThing == "removeBPfile_btn"){
				setFile_list_BP(response);
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
		// ckEditor
//		//  newContent = ch_content.document.getBody().getText(); //取得html文本 
//		var ch_content = CKEDITOR.instances.ch_content.getData();
//		$("#ch_content").val(ch_content);
//		
//		var en_content = CKEDITOR.instances.en_content.getData();
//		$("#en_content").val(en_content);
		
		
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
		var op_ids = $("#basic_ch #op_ids").multiselect("getChecked").map(function(){
							return this.value;
					 }).get();
		//console.log("op_ids="+op_ids)
		if(op_ids == "" || publish_time == "undefind"){
			$("#bus_span").text("請至少選擇一種業務類型");
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
			alert("修改完成");
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


function setId(response){
	if(response == ""){
		return;
	}
	var json = JSON.parse(response);
	$("#one_Div input[name='id']").val(json.businessPub_id);
	$("#one_Div #basic_ch input[name='doThing']").val(json.doThing+"_ch"); //updatePagesNews_ch
	$("#one_Div #basic_en input[name='doThing']").val(json.doThing+"_en"); //updatePagesNews_en
	$("#file_btn_ch, #file_btn_en, #file_List_ch, #file_List_en").show();
}


function setOrder(index){
	var data = {
			"id" : $(index).closest("tr").find("td:eq(6) > input[name='id']").val(),
			"type" : $(index).closest("tr").find("td:eq(6) > input[name='type']").val(),
			"sort" : $(index).closest("tr").find("td:eq(0)").text(),
			"op_id_one" : op_id_one
	}
	openDialog("order-dialog", data, contextPath + "/console/business/buinesspub-Order.jsp");
}

function setOrder_Dialog(data){
	$("#businessPub_Order input[name='sort']").val(data.sort);
	$("#businessPub_Order input[name='old_sort']").val(data.sort);
	$("#businessPub_Order input[name='id']").val(data.id);
	$("#businessPub_Order input[name='op_id_one']").val(data.op_id_one);
	$("#businessPub_Order input[name='type']").val(data.type);
}

function getFile_list_BP(id){
	var data = {
			"doThing" : "getFile_list_BP",
			"businessPub_id" : id
	}
	$("#file_btn_ch, #file_btn_en, #file_List_ch, #file_List_en, #remobtn").show();
	ajax_data(pagesURL, data)
}

function setFile_list_BP(response){ //把取得的某一筆眾多file放入(ch & en)
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
 		
 		var name_1 = '<a href="' + contextPath + '/download-file.jsp?do=BP&id=';
 		var name_2 = '" class="hover-red">';
 		var name_3 = '</a>';
 		var file_name = name_1 + json[n].id_str + name_2 + json[n].file_name + name_3;
 		
 		$('#'+id+' #rows tr:last > .file_name').html( file_name ); 
 		$('#'+id+' #rows tr:last > td.file_id input[name="file_id"]').val( json[n].id );
 		$('#'+id+' #rows tr:last > td.file_id input[name="businessPub_id"]').val( json[n].businessPub_id );
 	}
}

function removeBPfile_btn(index){
	var id =  $(index).closest("tr").find("td:eq(1) > input[name='file_id']").val();
	var businessPub_id =  $(index).closest("tr").find("td:eq(1) > input[name='businessPub_id']").val();
	var data = {
			"doThing" : "removeBPfile_btn",
			"id" : id,
			"businessPub_id" : businessPub_id
	}
	ajax_data(pagesURL, data);
}

function updateBPfile_btn(lang, index){
	var id =  $(index).closest("tr").find("td:eq(1) > input[name='file_id']").val();
	var businessPub_id =  $(index).closest("tr").find("td:eq(1) > input[name='businessPub_id']").val();
	var doThing = "updateBPFile_";
	var data = {
			"doThing" : doThing + lang, //newBPFile_ch
			"id" : id,
			"businessPub_id" : businessPub_id,
			"file_lang" : lang
	}
	var url = contextPath + "/console/business/buinesspub-fileUpload.jsp"
	openDialog("file-dialog", data, url)
}

function new_businessPubFile(lang){
	var businessPub_id = $("#one_Div #basic_ch input[name='id']").val();
	var doThing = "newBPFile_";
	var data = {
			"doThing" : doThing + lang, //newBPFile_ch
			"id" : "0",
			"businessPub_id" : businessPub_id,
			"file_lang" : lang
	}
	var url = contextPath + "/console/business/buinesspub-fileUpload.jsp"
	openDialog("file-dialog", data, url)
}



function setFile_one_Dialog(data){
	$("#file_One #doThing").val(data.doThing);
	$("#file_One #businessPub_id").val(data.businessPub_id);
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

//預約上稿時間：只要有變動，就中文英文版兩邊同步
function getValue(lang, tag){
	var temp;
	var id;
	if(tag == "op_ids"){
		$("#bus_span").text("");
		if(lang == "ch"){
			temp = $("#basic_ch #"+tag).multiselect("getChecked").map(function(){
		        		return this.value;
		    		}).get();
			id = "basic_en";
			
		}else if(lang == "en"){
			temp = $("#basic_en #"+tag).multiselect("getChecked").map(function(){
						return this.value;
					}).get();
			id = "basic_ch";
		}
		
		$("#"+id +" #"+tag).multiselect("destroy");
		setMutilelected(id, tag, temp);
		$("#"+id +" #"+tag).multiselect({
			selectedList: 10,   checkAllText: '全選',   uncheckAllText: '清空',   noneSelectedText: '請勾選項目',
			selectedText: '已選擇 # 項', height: 'auto' })
		
	}else{
		
		if(lang == "ch"){
			temp = $("#basic_ch #"+tag).val();
			$("#basic_en #"+tag).val(temp);
			
		}else if(lang == "en"){
			temp = $("#basic_en #"+tag).val();
			$("#basic_ch #"+tag).val(temp);		
		}
		
		//常見問答分類
		if(tag == "type" && temp == "14"){
			$(".QA").show();
		}else if(tag == "type" && temp != "14"){
			//$("#ch_QA_type, #en_QA_type").select2("val", "0");
//			$("#ch_QA_type, #en_QA_type").val("0");
//			$("#ch_QA_type, #en_QA_type").select2({
//				allowClear: true,
//				placeholder: "請輸入或選擇" //提示語
//			});
			$(".select2").val("0");
			$(".select2").select2({
				allowClear: true,
				placeholder: "請輸入或選擇" //提示語
			});
			$(".QA").hide();
		}
	}
	
}

function setMutilelected(id,name,value){
	if(value.length != 0){
		var arr;
		if(value.indexOf(",") != -1){
			arr = value.split(",");
		}else {
			arr = [value];
		}
		for(var i=0;i<arr.length;i++){
			$("#"+id +" #"+name).find("option[value='"+arr[i]+"']").prop("selected",true);
		}
	}
}

function setSelect2Value(id, name, value){
	if(value.length != 0){
		var ary;
		if(value.indexOf(",") != -1){
			ary = value.split(",");
		}else {
			ary = [value];
		}
		$("#"+id+" #"+name).val(ary).trigger('change'); //要加上trigger('change')，不然span不會放入選中的資料顯示出來
	  //$("#"+id+" #"+name).val(ary);                   //適用複選，但是少了trigger
	  //$("#"+id+" #"+name).select2("val",ary);         //只能用於單選，複選會只選到第一個（4.1會失效）
	}
}

