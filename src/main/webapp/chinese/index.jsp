<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-TW">

<head>
<moeaic:libraries />
<script>
	$(document).ready(function() {
		var LogoSlider = $('.slider1').bxSlider({
			slideWidth : 178,
			minSlides : 1,
			maxSlides : 5,
			slideMargin : 20,
			pause : 8000,
			speed : 1000
		});

		$('#goPrev').click(function() {
			LogoSlider.goToNextSlide();
			return false;
		});

		$('#goNext').click(function() {
			LogoSlider.goToPrevSlide();
			return false;
		});
		
		$('#stats_news').focusin(function() {
			$('.cycle-slideshow').cycle('pause');
		});
		
		$('#stats_news').focusout(function() {
			$('.cycle-slideshow').cycle('resume');
		});
	});
</script>
<title>經濟部投資審議委員會</title>
</head>

<body>
	<moeaic:header />
    <h2 style="display:none;">首頁</h2>
	
    <div id="inside">
        <!-- SlideAD -->
        <div id="divSlideAD" class="cover-container">
            <div id="divBigBack">
            	<div style="margin: 0 auto; border-width: 0px; width: 100%; max-width: 1171px;">
            		<div style="background-size: 100%;background-image: url('${pageContext.request.contextPath}/images/divSlideAD.jpg');height: 0;padding-top: 112.5%;background-repeat:no-repeat;background-position: center;"></div>
<%--                 <img src="${pageContext.request.contextPath}/images/divSlideAD.jpg" class="img-responsive" alt="Responsive image" style="margin:0 auto;border-width:0px;width:100%;max-width: 1171px;"/> --%>
				</div>
            </div>
            <div id="divSmallBack">
                <img src="${pageContext.request.contextPath}/images/divSlideADsmall_index.jpg" class="img-responsive" alt="Responsive image" style="margin:0 auto;border-width:0px;width:100%;"/>
            </div>
        </div>  
        <!-- FloatWidget -->
        <div class="container">
        	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;" title="中央內容區塊">:::</a>
            <div id="divFloat">
				<div id="divFloatLeft" style="overflow:hidden;">
					<div class="row row-centered">
						<div class="col-sm-4 col-md-4 col-centered">
							<div class="row row-centered">
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=1" title="僑外來臺投資"><img src="${pageContext.request.contextPath}/images/divButton01.png" alt="僑外來臺投資" /></a>
								</div>
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=3" title="陸資來臺投資"><img src="${pageContext.request.contextPath}/images/divButton02.png" alt="陸資來臺投資" /></a>
								</div>
							</div>
						</div>
						<div class="col-sm-4 col-md-4 col-centered">
							<div class="row row-centered">
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=4" title="國外(含港澳地區)投資"><img src="${pageContext.request.contextPath}/images/divButton03.png" alt="國外(含港澳地區)投資" /></a>
								</div>
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=5" title="對中國大陸投資"><img src="${pageContext.request.contextPath}/images/divButton04.png" alt="對中國大陸投資" /></a>
								</div>
							</div>
						</div>
						<div class="col-sm-4 col-md-4 col-centered">
							<div class="row row-centered">
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=6" title="創業家簽證"><img src="${pageContext.request.contextPath}/images/divButton05.png" alt="創業家簽證" /></a>
								</div>
								<div class="col-xs-6 col-sm-12 buttonAD col-centered">
									<a href="${pageContext.request.contextPath}/businessPub.view?lang=ch&op_id_one=9" title="其他"><img src="${pageContext.request.contextPath}/images/divButton06.png" alt="其他" /></a>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div id="divFloatRight" class="row-centered">
					<div id="divNews">
						<moeaic:index_stats language="ch" />
						<moeaic:index_news language="ch" />
					</div>
				</div>
			</div>
        </div>
    </div>  

	<!-- footer -->
	<div id="divFootLayer">
		<moeaic:index_links language="ch" />
		
		<moeaic:footer />
	</div>
</body>
</html>
