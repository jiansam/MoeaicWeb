<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="moeaic" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
<moeaic:libraries_en />

<script>
$(document).ready(function(){
	var LogoSlider = $('.slider1').bxSlider({
            slideWidth: 178,
            minSlides: 1,
            maxSlides: 5,
            slideMargin: 20,
            pause: 8000,
            speed: 1000
	});
	
	
    $('#goPrev').click(function(){
      LogoSlider.goToNextSlide();
      return false;
    });

    $('#goNext').click(function(){
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
<title>Department of Investment Review, MOEA</title>
</head>

<body>
	<moeaic:header_en />
	<h2 style="display:none;">index</h2>
	
    <div id="inside">
        <!-- SlideAD -->
        <div id="divSlideAD" class="cover-container">
            <div id="divBigBack">
                <img src="${pageContext.request.contextPath}/images/divSlideAD.jpg" class="img-responsive" alt="Responsive image" style="margin:0 auto;border-width:0px;width:100%;max-width: 1171px;"/>
            </div>
            <div id="divSmallBack">
                <img src="${pageContext.request.contextPath}/images/divSlideADsmall_index.jpg" class="img-responsive" alt="Responsive image" style="margin:0 auto;border-width:0px;width:100%;"/>
            </div>
        </div>  
        <!-- FloatWidget -->
        <div class="container">
        	<a id="C" href="#C" accesskey="C" class="sr-only z-focusable" style="float:left;" title="Content area">:::</a>
            <div id="divFloat">
                <div id="divFloatLeft" style="overflow:hidden;">
                    <div class="row row-centered">
                      <div class="col-sm-4 col-md-4 col-centered">
                        <div class="row row-centered">
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=1" title="Overseas Chinese & Foreign Investment"
                            	><img src="${pageContext.request.contextPath}/images/divButton01_en.png" alt="Overseas Chinese & Foreign Investment"/></a></div>
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=3" title="Investment From Mainland China"
                            	><img src="${pageContext.request.contextPath}/images/divButton02_en.png" alt="Investment From Mainland China"/></a></div>
                        </div>
                      </div>
                      <div class="col-sm-4 col-md-4 col-centered">
                        <div class="row row-centered">
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=4" title="Outward Investment"
                            	><img src="${pageContext.request.contextPath}/images/divButton03_en.png" alt="Outward Investment"/></a></div>
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=5" title="Investment To Mainland China"
                            	><img src="${pageContext.request.contextPath}/images/divButton04_en.png" alt="Investment To Mainland China"/></a></div>
                        </div>
                        </div>
                      <div class="col-sm-4 col-md-4 col-centered">
                        <div class="row row-centered">
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=6" title="Entrepreneur Visa"
                            	><img src="${pageContext.request.contextPath}/images/divButton05_en.png" alt="Entrepreneur Visa"/></a></div>
                            <div class="col-xs-6 col-sm-12 buttonAD col-centered"><a href="${pageContext.request.contextPath}/businessPub.view?lang=en&op_id_one=9" title="Other"
                            	><img src="${pageContext.request.contextPath}/images/divButton06_en.png" alt="Other"/></a></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  
				<div id="divFloatRight" class="row-centered">
					<div id="divNews">
						<moeaic:index_stats language="en" />
						<moeaic:index_news language="en" />
					</div>
				</div>
			</div>
        </div>
    </div>  
    <!-- footer -->
    <div id="divFootLayer">
    	<moeaic:index_links language="en" />
        
		<moeaic:footer_en />        
    </div>
</body>
</html>
