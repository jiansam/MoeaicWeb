<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery-File-Upload-9.12.6/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery-File-Upload-9.12.6/js/jquery.fileupload.js"></script>
<script type="text/javascript">
$(function() {
	$('#fileupload').fileupload({
		url: contextPath + '/console/edit-pagesNewsFile.view', //submit要送去處理的地方
		dataType : 'text', //回傳的資料型態
		done : function(e, data) {		//success的function
			setFile_list(data.result);
		},
		sequentialUploads: true
	})
	.prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled')
	.bind('fileuploadsubmit', function (e, data) {  
		data.formData = {				//submit時帶走的資料
			doThing: $('#doThing').val(), //newODFile_ch
			master_id: $('#master_id').val(),
			file_lang: $('#file_lang').val(),
			id: $('#id').val()
		}
	})
	.bind('fileuploadstop', function (e) {
		$('#progress .bar').css('width', '0%').text('');
		$("#file-dialog").dialog("close");
	})
	.bind('fileuploadprogressall', function (e, data) {
		var progress = parseInt(data.loaded / data.total * 100, 10);
		$('#progress .bar').css('width', progress + '%').text(progress + '%');
	});
});
</script>


<div class="row" >
	<div class="col-lg-12">
		<div class="panel panel-default">

			<div class="panel-body">
				<div class="table-responsive">

					<form action="" role="form" method="post" >
						<table id="file_One" class="table table-striped table-bordered table-hover">
							<tbody>
								
								<tr>
									<td nowrap="nowrap">檔案上傳</td>
									<td>
										<input id="fileupload" type="file" name="file" multiple="multiple" style="width:100%;">
										<input type="hidden" id="doThing" name="doThing" value="">
										<input type="hidden" id="master_id" name="master_id" value="">
										<input type="hidden" id="file_lang" name="file_lang" value="">
										<input type="hidden" id="id" name="id" value="">
										</td>
								</tr>

							</tbody>
						</table>
						
						<div id="progress" style="width: 100%;margin-top: 10px;">
							<div class="bar" style="width: 0%;text-align: center;background-image: url('${pageContext.request.contextPath}/images/console/progressbar.gif');"></div>
						</div>		
													
					</form>			
					
					
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
