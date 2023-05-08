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
			if(doThing == "getShowIndex"){
//				console.log("getvalue=" + response)
				setCheckboxValue("list", "showIndex", response)
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


/* ===========================================================================
 */
function uploadFile(id) {
	$.ajax({
		url : contextPath + "/console/pagesNews/excelUpload.jsp",
		cache: false,
		type: "POST",
		error : function(xhr) {
			$("#"+id).html("資料讀取失敗，請稍後重試！");
			$("#"+id).dialog("open");
		},
		success : function(response) {
			$("#"+id).html(response);
			$("#"+id).dialog("open");
		},
	});
}


function getShowIndex(){
	var showIndex = $('input:checkbox:checked[name="showIndex"]').map(
					 function() {
						 	return $(this).val(); 
						 	}
					 ).get().join(",");
//	console.log("value="+showIndex)
	var data = { 
			"doThing" : "updateShowIndex",
			"showIndex" : showIndex
		};
	
	ajax_data(pagesURL, data);
}

function setCheckboxValue(id, name, value){
	//console.log("value = " + value)
	if(value.length != 0){
		var arr;
		if(value.indexOf(",") != -1){
			arr = value.split(",");
		}else {
			arr = [value]; //因為要用inArr 所以直接 = value ，使用inArry但是value不是Array所以不能比對
		}
		//console.log("arr = " + arr)
		$("#"+id+" input[name='"+name+"']").each(function(){
			var v= $(this).val();
			//console.log("this = " + v)
			//console.log(v +" "+$.inArray(v,arr))
			if($.inArray(v,arr)!=-1){
				$(this).prop("checked",true);
			}else{
				$(this).prop("checked",false);
			}
		});
	}
}