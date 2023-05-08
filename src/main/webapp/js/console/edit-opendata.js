$(function() {	
	$("#options-dialog").dialog({
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
});

function edit_options(id, type){
	$.ajax({
		url : 'edit-options.jsp',
		type: "POST",
		error : function(xhr) {
			$("#options-dialog").html("資料讀取失敗，請稍後重試！");
			$("#options-dialog").dialog("open");
		},
		success : function(response) {
			$("#options-dialog").html(response);
			$("#options-dialog").dialog("open");
		},
		data: {
			id: id,
			type: type
		},
		cache: false
	});
}

$(function() {
	$("#tabs").tabs({
	});
});