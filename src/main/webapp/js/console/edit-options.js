$(function() {	
	$("#options-dialog, #QA-dialog").dialog({
		autoOpen : false,
		show : {
			effect : "blind",
			duration : 300
		},
		hide : {
			effect : "explode",
			duration : 300
		},
		width : 640,
		beforeClose : function(){
			$(this).html("");
		},
		modal : true
	});
	
    $('#web_TitleList_CH, #web_TitleList_EN').DataTable({
        responsive: true,
        columnDefs: [
                     {"orderable": false, "targets": [-1]} ],
        order : [[0, 'asc']],
        "pageLength" : 10,
    	"lengthMenu" : [[10, 25, 50, -1], [10, 25, 50, "全部"]]
    });
});

function edit_options(id, type){
	var dialog;
	if(type=="business_type" || type=="opendata_type"){
		dialog = "#options-dialog";
	}else{
		dialog = "#QA-dialog";
	}
	$.ajax({
		url : 'edit-options.jsp',
		type: "POST",
		error : function(xhr) {
			$(dialog).html("資料讀取失敗，請稍後重試！");
			$(dialog).dialog("open");
		},
		success : function(response) {
			$(dialog).html(response);
			$(dialog).dialog("open");
		},
		data: {
			id: id,
			type: type
		},
		cache: false
	});
}

function doRemove(id , type){
	var doThing = "isParentId_used_" + type; // isParentId_used_OD政府資訊公開 、 isParentId_used_PB申辦業務（共用method）
	$.ajax({
		url : pagesURL ,
		data : {
			"doThing" : doThing,
			"id" : id
		},
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded",
		success : function(response) {
			if(response == "true"){
				alert("此分類項下有資料，請調整後再刪除")
			}else{
				if(confirm("確認刪除這個資料類型？")==false){
					
				}else{
					window.location.href = "delete-options.jsp?id=" + id;
				}
			}
		}
	})	
}