 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
	<title>${title_ch} - 申辦業務 - 經濟部投資審議司(Department of Investment Review, MOEA)</title>
	
	<moeaic:libraries />
	<script type="text/javascript">
		var isShowFlow = "${isShowFlow}";
	</script>
	<script src="${pageContext.request.contextPath}/js/front-end/businessPub.js"></script>

	<style>
	.hover-red:hover{
		color: red;
	}
	
	.ui-accordion, .qs{
		padding: 5px;
	}
	
	.ui-widget{
		font-family: \5FAE\8EDF\6B63\9ED1\9AD4, Arial;
		font-size: 16px;
	}
	
	.ui-helper-reset{
		line-height: 32px;
	}
	.ipstyle {
	  height: 34px;
	  padding: 6px 12px;
	  font-size: 16px;
	  line-height: 1.42857143;
	  color: #555;
	  background-color: #fff;
	  background-image: none;
	  border: 1px solid #AAAAAA; /*#ccc*/
	  border-radius: 4px;
	}
	
	label {
		font-weight: normal;
	}
	ul.secinfor {
		display: flex;
		margin:0 10px;
	}

	ul.secinfor li.word-level a {
		display: block;
		width: 32px;
		height: 32px;
		line-height: 32px;
		margin: 0;
		padding: 0;
		font-size: 1em;
		text-align: center;
		color: #333;
		background-color: #c8c8c8;
	}

	ul.secinfor li.word-level a:hover,
	ul.secinfor li.word-level a:focus {
		display: block;
		width: 32px;
		height: 32px;
		line-height: 32px;
		margin: 0;
		padding: 0;
		font-size: 1em;
		text-align: center;
		color: #fff;
		background-color: #245091;
	}
	
	.resp-tab-content.fsmall,
    .ui-helper-reset.fsmall,
    .ui-accordion .ui-accordion-header.fsmall {
        font-size: 75%;
    }

    .resp-tab-content.fbig,
    .ui-helper-reset.fbig,
    .ui-accordion .ui-accordion-header.fbig {
        font-size: 125%;
    }
	
	.showqa{
		display: flex;
		justify-content: flex-end;
	}

    @media print {

        #divHeader,
        #divMenu,
        #divLeftMenu,
        #divFatFooter,
        #divPageBottom,
        #bp_sel,
        #bp_key,
        a[href="#top"],
        .resp-tabs-list,
        .file_div,
        .ui-corner-top:not(.ui-state-active),
        .ui-accordion-content:not(.ui-accordion-content-active) {
            display: none;
        }

        .qs+div,
        #btngroup {
            display: none !important;
        }
		
		.ui-accordion .ui-accordion-header{
			border:1px solid #eee;
		}
	
        #faq_accordion {
            max-width: 900px;
            padding: 0;
            margin-top: -80px; 
        }
		.resp-tab-content{
			border:none;
		}
        .ui-accordion .ui-accordion-content {
            padding: 25px;
        }

        @page {
            size: A4 portrait;
            margin: 0cm;
            orphans: 4;
            widows: 2;
        }
    }
	</style>
</head>

<body>
	<moeaic:header selected="business" />
	<moeaic:slide category="h"/>

	<div id="divContent">
		<div id="divWhiteBack">
			<div class="container">
				<moeaic:businessPub />

				<div id="divRightContent" style="width: 100%;">
					<div id="divTitleBorder">
						<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;margin-top: 130px;" title="中央內容區塊">:::</a>
						<h3 class="p" id="pageTitle">${title_ch}</h3>
					</div>
					<div style="display: flex;justify-content: flex-end;" id="btngroup">
						<div class="showqa" style="display:none">
							<a href="javascript:window.print();" title="友善列印" class="printbtn" style="display:none">
								<i class="fa fa-print" aria-hidden="true"></i>友善列印
							</a>
							<ul class="secinfor" style="display:none">
								<li class="word-level" onclick="changeSize('small');" style="cursor: pointer;"><a href="javascript:;"
										title="小字級">小</a></li>
								<li class="word-level" onclick="changeSize('');" style="cursor: pointer;"><a href="javascript:;"
										title="中字級">中</a>
								</li>
								<li class="word-level" onclick="changeSize('big');" style="cursor: pointer;"><a href="javascript:;"
										title="大字級">大</a>
								</li>
							</ul>
						</div>
						<c:if test="${not empty flow_chart}">
							<div id="choicediv" style="line-height:22px!important;padding-bottom:14px;text-align:right;">
								<a href="javascript:void(0)" onclick="openDiv('file_div')" onkeypress="openDiv('file_div')" title="開啟申請文件">
									<img alt="申請文件" src="${pageContext.request.contextPath}/images/icons/file.jpg" width="32"></a>
								<a href="javascript:void(0)" onclick="openDiv('flow_div')" onkeypress="openDiv('flow_div')" title="開啟流程圖">
									<img alt="流程圖" src="${pageContext.request.contextPath}/images/icons/flow.jpg" width="32"></a>
							</div>
						</c:if>
					</div>
					<div class="flow_div" style="display:none">${flow_chart}</div>
					<div class="file_div" style="color:gray;line-height:16px!important;padding-bottom:5px;">請以tab鍵切換每一頁籤，再按下Enter鍵進入該頁籤頁面。</div>
					<div class="file_div" id="horizontalTab" >
						<ul class="resp-tabs-list">
							<c:forEach var="name" items="${list_name}">
								<li style="font-size:18px;" tabindex='0'>${name}</li>
							</c:forEach>
						</ul>

						<div class="resp-tabs-container">
							<c:forEach var="beans" items="${lists}">
							
								<c:choose>
								
								<c:when test="${beans[0].type eq '14'}">
								<div id="QS" class="qs">
									
									<div class="qs" style="font-size:18px;text-align:left;display:inline-block;width:100%;border-radius:50px;">
										
										<c:if test="${not empty QA_type}">
											<div id="bp_sel">
												<label for="chooseQA">分　類:</label>
												<select id="chooseQA" style="height:36px;border-radius:5px;width:186px;margin-bottom:5px;">
													<c:forEach var="option" items="${QA_type}">
														<option value="${option.id}">${option.name}</option>
													</c:forEach>
												</select>
												<input type="image" alt="選擇分類" src="${pageContext.request.contextPath}/images/searchbutton.png" title="選擇分類"
													style="height: 36px;vertical-align:bottom;margin-bottom:5px;" onclick="showQA()" onkeypress="showQA()" />
											</div>
										</c:if>
										
										<div id="bp_key">
											<label for="query">關鍵字:</label>
											<input type="text" name="query" id="query" title="關鍵字搜尋" onchange="search()" class="ipstyle" style="width:186px;margin-bottom:5px;">
											<input type="image" alt="關鍵字搜尋按鈕" src="${pageContext.request.contextPath}/images/searchbutton.png" title="搜尋"
												style="height:36px;vertical-align:bottom;margin-bottom:5px;" onclick="search()" onkeypress="search()" />
										</div>

									</div>
									<div style="color:gray;margin-left:5px;line-height:16px!important;">請以方向鍵上下切換每一問題，再按下Enter鍵進入展開內容或關閉內容。</div>
									<div id="faq_accordion" style="font-size:18px;">
										<c:forEach var="data" items="${beans}">
											<h3 class="Q${data.id}" keyword="${data.ch_QA_type}">${data.ch_title}</h3>
											<div class="Q${data.id}" keyword="${data.ch_QA_type}">${data.ch_content}</div>
										</c:forEach>
									</div>
								</div>									
								</c:when>
								
								<c:otherwise>
								<div>
									<table style="width: 100%;font-size:18px;word-wrap: break-word;word-break: break-all;">
									

										<thead>
											<tr>
												<th id="hd0${beans[0].type}" width="1%" nowrap="nowrap" valign="top"></th>
												<th id="hd1${beans[0].type}" colspan="1">用途說明</th>
												<th id="hd2${beans[0].type}" style="width: 20%;text-align: left">檔案</th>
											</tr>
										</thead>
									
										<tbody>
											<c:forEach var="data" items="${beans}">
											<tr>
												<td headers="hd0${beans[0].type}" width="1%" nowrap="nowrap" valign="top"><span style="color:#FF4000;"><i class="fa fa-caret-right" aria-hidden="true"></i></span></td>
												<td headers="hd1${beans[0].type}" valign="top">${data.ch_title}<br><span style="color:#084B8A;">${data.ch_content}</span></td>
												
												<td headers="hd2${beans[0].type}" align="left" valign="top" style="padding-left: 5px;">      
																			   
																			   
													<c:forEach var="file" items="${files[data.businessPub_id_str]}">
														<div style="display: inline-block;width: 20px;height:20px;vertical-align:middle;background-image: url('/images/icon_download_s.png')"></div>
														<a href="${pageContext.request.contextPath}/download-file.jsp?do=BP&id=${file.id_encrypt}" class="hover-red" rel="下載" title="[另開新視窗下載]${data.ch_title}${file.file_name_str}">${file.file_name}</a><br>
											 		</c:forEach>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								</c:otherwise>
								
								</c:choose>
							</c:forEach>
						</div>

					</div>

					<br>
					
				    <div style="width:100%;float:right;margin-bottom:20px;">
				    	<a href="#top" tabindex="0"><img alt="回到最上方" title="回到最上方" border="0" src="/images/upload/1b41b423-7181-4801-88a7-44ddcdb33f72.png" style="float: right;" width="32" /></a>
				    </div>					
					
				</div>
			</div>
		</div>
	</div>

	<moeaic:footer />
</body>
<script type="text/javascript">
    function changeSize(e) {
        var element = document.querySelectorAll(".ui-helper-reset,.ui-accordion .ui-accordion-header,.resp-tab-content");
        for (var i = 0; i < element.length; ++i) {
            element[i].classList.add('fsmall');
            if (e == "small") {
                element[i].classList.remove('fbig');
                element[i].classList.add('fsmall');
            } else if (e == "big") {
                element[i].classList.remove('fsmall');
                element[i].classList.add('fbig');
            } else {
                element[i].classList.remove('fsmall');
                element[i].classList.remove('fbig');
            }
        }
        return false;
    }
	$(function(){
		if($('.resp-tab-item.resp-tab-active').text() == "常見問答集"){
			$('.showqa,.secinfor').show();
		}
		$('.resp-tab-item,.resp-accordion').on('click',function(){
			let text = $(this).text();
			if(text == "常見問答集"){
				$('.showqa,.secinfor').fadeIn();
			}else{
				$('.showqa,.secinfor').fadeOut();
				if(!$('.ui-widget-content').is(":hidden")){
					$('.printbtn').fadeOut();
				}
			}
		});
		$('.ui-accordion-header').on('click',function(){
			setTimeout(() => {
				let active = $('.ui-widget-content').hasClass('ui-accordion-content-active');
				if(active){
					$('.printbtn').fadeIn();
				}else{
					$('.printbtn').fadeOut();
				}
			}, "600");
			
		});
	});
</script>
</html>
