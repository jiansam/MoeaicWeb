<%@ tag language="java" pageEncoding="UTF-8"%>

<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-TW" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/jquery.bxslider.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/default.css?t=20230117" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/content.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/easy-responsive-tabs.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/jquery-ui-1.13.2/jquery-ui.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.bxslider.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.13.2/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/easy-responsive-tabs.js"></script>

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
		"search" : "搜尋：",
		"emptyTable" : "無符合查詢之資料"
	},
	lengthChange : false,
	searching : false
});
</script>