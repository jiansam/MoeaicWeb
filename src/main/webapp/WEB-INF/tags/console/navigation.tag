<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <!-- Navigation -->
 <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
     <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
             <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="#">經濟部投資審議司 - 後台管理</a>
     </div>
     <!-- /.navbar-header -->

     <ul class="nav navbar-top-links navbar-right">
         <li class="dropdown">
             <a title="登出" href="${pageContext.request.contextPath}/console/logout.jsp">
                 <i class="fa fa-sign-out fa-fw"></i>登出 
             </a>
             <!-- /.dropdown-user -->
         </li>
         <!-- /.dropdown -->
     </ul>
     <!-- /.navbar-top-links -->

     <div class="navbar-default sidebar" role="navigation">
         <div class="sidebar-nav navbar-collapse">
             <ul class="nav" id="side-menu">
                 <li>
                     <a href="#"><i class="fa fa-dashboard fa-fw"></i> 關於本司<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_intro">認識本司</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_menager">認識召集人及司長</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_organization">組織與職掌</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_policy">本司政策</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_imple_plan">施政計畫</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_traffic">交通位置</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_responsibility">Responsibility</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=about_fuDir">Future Directions</a>
                         </li>
                     </ul>
                 </li>
                 
                 <li>
                     <a href="${pageContext.request.contextPath}/console/pagesNews/new_ann.jsp"><i class="fa fa-newspaper-o fa-fw"></i> 最新公告</a>
                 </li>
                 
                 <li>
                     <a href="#"><i class="fa fa-newspaper-o fa-fw"></i> 業務統計<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="${pageContext.request.contextPath}/console/pagesNews/business_ann.jsp">公告管理</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=excelUpload">統計資料</a>
                         </li>
                     </ul>
					 <!-- /.nav-second-level -->
                 </li>
                 
                 <li>
                     <a href="${pageContext.request.contextPath}/console/pagesNews/studyReport.jsp"><i class="fa fa-newspaper-o fa-fw"></i> 研究報告</a>
                 </li>
                 
                 <li>
                     <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 申辦業務<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="#"> 公告管理<span class="fa arrow"></span></a>
                             <ul class="nav nav-third-level">
                             	<c:forEach var="data" items="${optionManager.business_category}">
	                             <li><a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=businessPub&op_id_one=${data.id}">${data.name}</a></li>
	                             </c:forEach>
                             </ul>
                         </li>
                                            
                         <li>
                             <a href="#"> 流程圖管理<span class="fa arrow"></span></a>
                             <ul class="nav nav-third-level">
                             	<c:forEach var="data" items="${optionManager.business_category}">
	                             <li><a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=flowcChart&type=${data.id}">${data.name}</a></li>
	                             </c:forEach>
                             </ul>
                         </li>
                         
                         <li>
                             <a href="${pageContext.request.contextPath}/console/business/options.jsp">選項管理</a>
                         </li>
                     </ul>
					<!-- /.nav-second-level -->
                 </li>
                 
                 <li>
                     <a href="${pageContext.request.contextPath}/console/about/apply_online.jsp"><i class="fa fa-download fa-fw"></i> 線上申辦</a>
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/about/apply_search.jsp"><i class="fa fa-download fa-fw"></i> 申請案件查詢</a>
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/about/attachedFile.jsp"><i class="fa fa-download fa-fw"></i> 附件下載</a>
                 </li>
                 
                 <li>
                     <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 政府資訊公開<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="#"> 公告管理<span class="fa arrow"></span></a>
                             <ul class="nav nav-third-level">
                             	<c:forEach var="data" items="${optionManager.openData_type}">
	                             <li><a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=openData_index&op_id_one=${data.id}">${data.name}</a></li>
	                             </c:forEach>
                             </ul>                             
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/opendata/options.jsp">選項管理</a>
                         </li>
                     </ul>
					<!-- /.nav-second-level -->
                 </li>
                 
				<li>
                     <a href="#"><i class="fa fa-table fa-fw"></i> 相關網站<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=getWebName">資料管理</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/websiteLinks/edit-webTitle.jsp">分類管理</a>
                         </li>
                     </ul>
                     <!-- /.nav-second-level -->
                 </li>
                 <li>
                     <a href="#"><i class="fa fa-edit fa-fw"></i> 聯絡我們<span class="fa arrow"></span></a>
                     <ul class="nav nav-second-level">
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=contact_info">聯絡資訊</a>
                         </li>
                         <li>
                             <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=contact_apply">陳情管道</a>
                         </li>
                     </ul>
                     <!-- /.nav-second-level -->
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=pagesIndex&type=privacy"><i class="fa fa-lock fa-fw"></i> 隱私權及安全政策</a>
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/image-manage"><i class="fa fa-file-image-o fa-fw"></i> 圖檔管理</a>
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/edit-pages.view?doThing=userManage"><i class="fa fa-user fa-fw"></i> 使用者管理</a>
                 </li>
                 <li>
                     <a href="${pageContext.request.contextPath}/console/logAccess.view"><i class="fa fa-calculator fa-fw"></i> 點擊率管理</a>
                 </li>
                 <li style="display: none;">
                     <a href="${pageContext.request.contextPath}/console/logAccess/logByHand.jsp"><i class="fa fa-calculator fa-fw"></i> 手動啟動LogAccess</a>
                 </li>
             </ul>
         </div>
         <!-- /.sidebar-collapse -->
     </div>
     <!-- /.navbar-static-side -->
 </nav>