var page_content_CH;
var page_content_EN;

$(document).ready(function() {
	if(type != "7"){
		$("#enli, #enDiv").show();
	}
	
	
	$("#tabs").tabs({
		width : $("#tabs").parent().width(),
	});
	// ------------------------------------------------------------------
	if ($('#page_content_CH').length > 0) {
		// var page_content_CH = setCKeditor("page_content_CH");
		page_content_CH = setCKeditor("page_content_CH");
		//console.log("setCKeditor")
	}
	if ($('#page_content_EN').length > 0) {
		// var page_content_EN = setCKeditor("page_content_EN");
		page_content_EN = setCKeditor("page_content_EN");
	}
});

function setCKeditor(id) {
	return CKEDITOR.replace(id, {
		height : 350
	});
}

/*
 * 共用ajax form :網頁上的form，要輸入到資料庫的 servletURL：要使用的Servlet路徑 method
 * ：成功後，要找哪一個method處理response
 */
function ajax(form, servletURL, method) {
	$.ajax({
		url : servletURL,
		data : $(form).serialize(),
		method : 'POST',
		cache : false, // IE不要cache （預設是true）
		async : true, // 是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded",
		success : function(response) {
			alert("已更新完畢")
			return;
		},
	});
}

function submitContent(form, id) {
	if (confirm("確定要送出資料？") == false) {
		return;
	}
	var nextFuntion = "";

	// 一般CkEditor編輯
	if (id == "CH") {
		// newContent = page_content_CH.document.getBody().getText(); //取得html文本
		var newContent = CKEDITOR.instances.page_content_CH.getData();
		console.log(newContent)
		$("#page_content_" + id).val(newContent);
		nextFuntion = "";
	} else if (id == "EN") {
		var newContent = CKEDITOR.instances.page_content_EN.getData();
		 //console.log(newContent)
		$("#page_content_" + id).val(newContent);
		nextFuntion = "";
	}

	ajax(form, pagesURL, nextFuntion);
}