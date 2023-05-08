$(document).ready(function () {
	// 	$( "#tabs" ).tabs();
	$('#horizontalTab').easyResponsiveTabs({
		type: 'default', //Types: default, vertical, accordion           
		width: 'auto', //auto or any width like 600px
		fit: true,   // 100% fit in a container
		closed: 'false', // Start closed if in accordion view
		activate: function(event) { // Callback function if tab is switched
			var $tab = $(this);
			var $info = $('#tabInfo');
			var $name = $('span', $info);
			$name.text($tab.text());
			$info.show();
			tab_control();
		}
	});
		
	$( "#faq_accordion" ).accordion({
		heightStyle: "content",
		collapsible: true,
		active: false,
		create: function( event, ui ) {
			$('#faq_accordion h3').attr("tabindex", "0");
		},
		activate: function( event, ui ) {
			$('#faq_accordion h3').attr("tabindex", "0");
		}
	});
		
	$(".resp-tab-item").each(function(index){
		$(this).keydown(function(e){
			if(e.keyCode == 13){
				$('.tab-selected').removeClass('tab-selected');
				$(this).addClass('tab-selected');
				$(this).click();
			}
			
			if($('.tab-selected').size() == 0){
				return;
			}
			
			if(e.keyCode == 9 && !e.shiftKey && $(this).hasClass("resp-tab-active")){
				e.preventDefault();
				$('div.resp-tab-content-active a, div.resp-tab-content-active select, div.resp-tab-content-active input').first().focus();
			}
			
			if(e.keyCode == 9 && !e.shiftKey && !$(this).hasClass("resp-tab-active") && $(this).next("li").size() == 0){
				e.preventDefault();
				$('a[href="#top"]').first().focus();
			}
			
			if(e.keyCode == 9 && e.shiftKey){
				if($(this).prev("li").size() > 0 && $(this).prev("li").hasClass("resp-tab-active")){
					e.preventDefault();
					$('div.resp-tab-content-active a, div.resp-tab-content-active #faq_accordion h3').last().focus();
				}
			}
		});
		
		$(this).click(function(e){
			$('.tab-selected').removeClass('tab-selected');
			$(this).addClass('tab-selected');
		});
	});
		
	tab_control();

	if(isShowFlow != ""){
		openDiv("flow_div");
	}
});
	
function tab_control(){
	if($('.tab-selected').size() == 0){
		return;
	}
	
	$('div.resp-tab-content-active a, div.resp-tab-content-active select, div.resp-tab-content-active input').first().keydown(function(e){
		if(e.keyCode == 9 && e.shiftKey){
			e.preventDefault();
			$('.resp-tab-active').first().focus();
		}
	});
	
	$('div.resp-tab-content-active a, div.resp-tab-content-active #faq_accordion h3').last().keydown(function(e){
		if(e.keyCode == 9 && !e.shiftKey){
			if($('.resp-tab-active').first().next("li").size() > 0){
				e.preventDefault();
				$('.resp-tab-active').first().next("li").focus();
			}
		}
	});
}
	
function search(){
	$("#faq_accordion h3").show();
	$("#faq_accordion div").hide();
	$("#faq_accordion div").eq(0).show();
	
	var query = $("#QS input[name='query']").val();
	
	if(query.length != 0){
		$("#QS #faq_accordion h3:visible ").each(function() {
			var classId = $(this).attr("class").split(" ")[0];
			if($(this).text().indexOf(query) != -1 ||
				$(this).next('div').text().indexOf(query) != -1){
				$("#faq_accordion ."+classId).show();
			}else{
				$("#faq_accordion ."+classId).hide();
			}
		});
	}
}

function showQA(){
	$("#QS input[name='query']").val("");
	$("#faq_accordion h3").show();
	$("#faq_accordion div").hide();
	
	var QAtype = $("#chooseQA").val();
	if(QAtype != "0"){
		$("#faq_accordion h3").each(function(){
			var types = $(this).attr('keyword').split(",");
			var matched = false;
			for(var i = 0;i < types.length;i++){
				if(types[i] == QAtype){
					matched = true;
				}
			}
			
			if(!matched){
				$(this).hide();
				$(this).next("div").hide();				
			}
		});
	}
}

function openDiv(id) {
	if(id == "flow_div"){
		$(".flow_div").show();
		$(".file_div").hide();
	}else{
		$(".flow_div").hide();
		$(".file_div").show();
	}
}