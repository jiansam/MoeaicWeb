<%@ tag language="java" pageEncoding="UTF-8" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/sb-admin-2.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/jquery-ui-1.13.2/jquery-ui.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.13.2/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/metisMenu/metisMenu.min.js"></script>
<script src="${pageContext.request.contextPath}/js/sb-admin-2.js"></script>
<script src="${pageContext.request.contextPath}/ckeditor-4.5.11/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/json2.js"></script>
<script src="${pageContext.request.contextPath}/js/datepicker-zh-TW.js"></script>
<%-- <script src="${pageContext.request.contextPath}/js/datepickerForTW.js"></script> --%>

<script src="${pageContext.request.contextPath}/js/jquery.multiselect.min.js"></script>
<link href="${pageContext.request.contextPath}/css/jquery.multiselect.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/select2.min.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/select2.min.js"></script>

<link href="${pageContext.request.contextPath}/DataTables-1.10.12/media/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/DataTables-1.10.12/extensions/Responsive/css/responsive.bootstrap.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/DataTables-1.10.12/media/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/DataTables-1.10.12/media/js/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/DataTables-1.10.12/extensions/Responsive/js/dataTables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/DataTables-1.10.12/extensions/Responsive/js/responsive.bootstrap.min.js"></script>

<script type="text/javascript">
$.extend(true, $.fn.dataTable.defaults, {
	"language": {
		"zeroRecords" : "無符合查詢之資料",
		"info": "第 _PAGE_ 頁/ 共 _PAGES_ 頁，_TOTAL_筆資料",
		"infoEmpty": "",
		"lengthMenu": "每頁顯示 _MENU_ 筆資料",
		"paginate" : {
			"next" : "下一頁",
			"previous" : "上一頁"
		},
		"search" : "搜尋關鍵字：",
		"emptyTable" : "無符合查詢之資料"
	}
});
</script>