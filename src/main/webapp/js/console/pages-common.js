var page_content_CH;
var page_content_EN;

$(document).ready(function() {
	
	$("#tabs").tabs({
		width : $("#tabs").parent().width(), 
	});
	
	//載入網頁時，直接取出本頁的type，轉呼叫ajax來放入該bean的id、content
	var type = $("#chDiv input[name='type']").val();
	if(type==undefined || type==""){
		type = $("#enDiv input[name='type']").val();
	}
	var data = {"doThing":"seltContent", "type":type};
	//------------------------------------------------------------------
	if($('#page_content_CH').length >0 ){
		//var page_content_CH = setCKeditor("page_content_CH");
		page_content_CH = setCKeditor("page_content_CH");
//		console.log("setCKeditor")
	}
	if($('#page_content_EN').length >0 ){
		//var page_content_EN = setCKeditor("page_content_EN");
		page_content_EN = setCKeditor("page_content_EN");
	}
	selectAjax(pagesURL, data);
});

function setCKeditor(id){
	return CKEDITOR.replace(id,{height: 350});
}
function selectAjax(servletURL, data){
	$.ajax({
		url : servletURL ,
		data : data,
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded",
		success : function(response) {
			if(response==""){
				return;
			}
			var json = JSON.parse(response);
			
			if($("#chDiv #page_content_CH").val() != undefined){
				//$("#chDiv #page_content_CH").val(json.CH_context); //textarea跟ckEditor不是一樣的區塊，所以要放到ckEditor內
				//CKEDITOR.instances.page_content_CH.setData(json.CH_context);
				page_content_CH.setData(json.CH_context);
				$("#chDiv input[name='id']").val(json.CH_id);
//				console.log("setData")
			}else if(data.type == "apply_online" || data.type == "apply_search" || data.type == "attachedFile"){
				$("#chDiv input[name='page_content']").val(json.CH_context);
				$("#chDiv input[name='id']").val(json.CH_id);
			}

			if($("#enDiv #page_content_EN").val() != undefined){
				//$("#enDiv #page_content_EN").val(json.EN_context);
				//CKEDITOR.instances.page_content_EN.setData(json.EN_context);
				page_content_EN.setData(json.EN_context);
				$("#enDiv input[name='id']").val(json.EN_id);
			}else if(data.type == "apply_online" || data.type == "apply_search" || data.type == "attachedFile"){
				$("#enDiv input[name='page_content']").val(json.EN_context);
				$("#enDiv input[name='id']").val(json.EN_id);
			}
		},
	});
}

/*  共用ajax
 * 	form      :網頁上的form，要輸入到資料庫的
 * 	servletURL：要使用的Servlet路徑
 * 	method    ：成功後，要找哪一個method處理response
 */
function ajax(form, servletURL, method){
	$.ajax({
		url : servletURL ,
		data : $(form).serialize(),
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded",
		success : function(response) {
			alert("已更新完畢")
			return;
		},
	});
}

function submitContent(form, id){
	if(confirm("確定要送出資料？")==false){
		return;
	}
	var nextFuntion = "";

	//一般CkEditor編輯
	if(id == "CH"){
		//  newContent = page_content_CH.document.getBody().getText(); //取得html文本 
		var newContent = CKEDITOR.instances.page_content_CH.getData();
//		console.log(newContent)
		$("#page_content_"+id).val(newContent);
		nextFuntion = "";
	}
	else if(id == "EN"){
		var newContent = CKEDITOR.instances.page_content_EN.getData();
//		console.log(newContent)
		$("#page_content_"+id).val(newContent);
		nextFuntion = "";
	}
	
	ajax(form, pagesURL, nextFuntion);
}