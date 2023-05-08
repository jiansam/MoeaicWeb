var now = new Date();
var year = now.getFullYear() - 1911;
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
				window.location = servletURL + "?doThing=studyReport";
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
	if(id == "user_one"){
		if(confirm("確定要送出資料？")==false){
			return;
		}
//		console.log("user_one")
		var doThing = $("#user_One input[name='doThing']").val();
		if(doThing == "updateUser"){
			nextFuntion = "updateUser_ok";
		}
		else if (doThing == "insertUser") {
			nextFuntion = "insertUser_ok";
		}
	}
	//變更密碼
	else if (id == "update_userPSW") {
		var value = $("#"+id+" input[name='password']").val();
		var regx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{12,}$/g; //正則表示法 不加引號，g全域比對
		if(regx.test(value)==false){
			alert("密碼強度不足，請參照密碼設定說明重新設定，謝謝。\n英文(至少要有一大寫一小寫)+數字+符號，不得少於12字元");
			return;
		}
		if(confirm("確定要送出資料？")==false){
			return;
		}
		nextFuntion = "update_userPSW_ok";
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
			if(method == "insertUser_ok"){
				setId(response); //response = user_id
				alert("使用者新增完成");
			}
			else if(method == "updateUser_ok"){
				alert("資料修改成功");
				return;
			}
			else if(method == "update_userPSW_ok"){
				setPSW(response); //回傳新密碼
				alert("密碼修改完成");
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
			
			if(id == "user-dialog"){
				setPSW_Dialog(data);
			}
		},
	});
}

function validate(value,pattern){
	/*寫法一 //正則表示法 不加引號
		var pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/; 
		var reg = new RegExp(pattern, "g"); //g全域比對 */
	//寫法二
		var reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/g //g全域比對
		
		return reg.test(value);
}

/* ========================================================================================================
 * user function
 */

function checkPSW(id){
	var value = $("#"+id+" input[name='password']").val();
//	console.log(value)
//	var regx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/g; //正則表示法 不加引號，g全域比對
//	var regx = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/g; //正則表示法 不加引號，g全域比對
	var regx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{12,}$/g; //正則表示法 不加引號，g全域比對，注意全域比對test第二次前需重置lastIndex()=0
	
	var err="";
	if(regx.test(value)==false){
		if( (/^(?=.*[a-z]).+$/g).test(value) == false){  //  /^ (?=.*[a-z]) .+ $/g
			err = "必須有一個小寫英文";
		}
		if( (/^(?=.*[A-Z]).+$/g).test(value) == false){  //  /^ (?=.*[A-Z]) .+ $/g
			if(err.length>0){
				err = err + ",大寫英文";
			}else{
				err = "必須有一個大寫英文";
			}
		}
		
//		if( (/^(?=.*[a-zA-Z]).+$/g).test(value) == false){  //  /^ (?=.*[a-zA-Z]) .+ $/g
//		err = "必須有一個英文";
//		}		
		if( (/^(?=.*[0-9]).+$/g).test(value) == false){  //  /^(?=.*[0-9]).+$/g
			if(err.length>0){
				err = err + ",0~9的數字";
			}else{
				err = "必須有一個0~9的數字";
			}
		}	
		if( (/^(?=.*[!@#$%^&*()]).+$/g).test(value) == false){  //  /^(?=.*[!@#$%^&*()]).+$/g
			if(err.length!=0){
				err = err + ",特殊符號";
			}else{
				err = "必須有一個特殊符號";
			}
		}
		if( (/^.{12,}$/g).test(value) == false ){  //  /^.{6,}$/g
			if(err.length>0){
				err = err + ",不得少於十二個字元";
			}else{
				err = "不得少於十二個字元";
			}
		}	
	}
	
	if(err.length == 0){
		$("#"+id+" #msg").css("color","gray").text("[英文(有大小寫之分)+數字+符號，不得少於6字元]");
	}else{
		$("#"+id+" #msg").css("color","red").text(err);
	}
}

function checkTwoPSW(id){
	var password = $("#update_userPSW input[name='password']").val();
	var password_re = $("#update_userPSW input[name='password_re']").val();
	if(password_re == ""){
		$("#"+id+" #msg2").css("color","red").text("請再輸入一次密碼");
		return;
	}else{
		if(password == password_re){
			$("#"+id+" #msg2").css("color","gray").text("");
		}else{
			$("#"+id+" #msg2").css("color","red").text("兩次密碼不相同");
			return;
		}
	}
}


function setId(response){
	if(response == ""){
		return;
	}
	var json = JSON.parse(response);
	$("#user_One input[name='doThing']").val(json.doThing);
	$("#user_One input[name='id']").val(json.id);
	$("#user_One #password, #user_One #password_tr").hide(); //修改：密碼欄隱藏
	$("#user_One #psdBtn").show(); //修改：密碼button出現
}

function edit_PSW(){
	var id = $("#user_One input[name='id']").val();
	var password = $("#user_One input[name='password']").val();
	var data = {
			"id" : id,
			"password" : password,
			"doThing" : "update_userPSW"
	}
	openDialog("user-dialog", data, contextPath + "/console/user/edit-password.jsp");
}

function setPSW_Dialog(data){
//	$("#update_userPSW input[name='old_password']").val(data.password);
	$("#update_userPSW input[name='id']").val(data.id);
	$("#update_userPSW input[name='doThing']").val(data.doThing);
}

function change_type(){
	var ischecked =  $("#update_userPSW #chg_type").prop("checked");
	if(ischecked == true){
		$("#update_userPSW input[name='old_password']").prop("type" , "text");
		$("#update_userPSW input[name='password']").prop("type" , "text");
		$("#update_userPSW input[name='password_re']").prop("type" , "text");
	}else {
		$("#update_userPSW input[name='old_password']").prop("type" , "password");
		$("#update_userPSW input[name='password']").prop("type" , "password");
		$("#update_userPSW input[name='password_re']").prop("type" , "password");
	}
}

function setPSW(psw){
	$("#user_One input[name='password']").val(psw);
	$("#user-dialog").dialog("close");
}
