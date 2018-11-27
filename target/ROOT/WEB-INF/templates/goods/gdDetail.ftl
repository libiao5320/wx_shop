<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>融耀健康</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/css/swiper.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-min.js"></script>
<script type="text/javascript" src="/js/swiper.min.js"></script>
<script type="text/javascript" src="/js/js.js"></script>
</head>
<body>
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt">更多详情</p>
            <div class="top_right top_rights">

                <a class="top_sx" href=""></a>
            </div>
        </section>
    </header>
    <#if goods?? >
	    <article>
			<section class="navs5">
				<div class="swiper-container">
					<div class="swiper-wrapper">
					<#if imgList?? && (imgList?size gt 0)>
						<#list imgList as img>
						<div class="swiper-slide"><img src="${GOODS_IMG_URL!''}${img.img!''}" alt="" title=""></div>
						</#list>
					<#else>
						<div class="swiper-slide"><img src="${GOODS_IMG_URL!''}${goods.goodsImage!''}" alt="" title=""></div>
					</#if>        	
					</div>
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
				
				</div>
			</section>
	        <section class="navs14">
	            <h6 class="nav_h6">产品介绍</h6>
	            <p class="navs14_p">名称：${goods.goodsName!'' }</p>
	           	${goods.text!'' }
	            <h6 class="nav_h6">内容介绍</h6>
	            ${goods.contentIntroduce!''}
	            <h6 class="nav_h6">购买须知</h6>
	            <div style="margin-bottom: 45px;">
	             ${goods.buyKnow !''}
	             </div>
					   <#if goods.isBook == 1 >
				 
							 <#if LOGIN_SIGN  ?? >
			                        <#if LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId==0) >
			                            <p class="navs8_p2">VIP0：<i>
		    								￥</i><span> 
			                        	<lable class="vipPrice" > ${(goods.goodsVip0Price!0)/100}</lable>
			                        	</span>
		   	 						<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
			                        <#elseif  LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId==1) >
			                               <p class="navs8_p2">VIP1：<i>
		    								￥</i><span>
			                        	<lable class="vipPrice" >${(goods.goodsVip1Price!0)/100}</lable>
			                        	</span>
		   	 						<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
			                        <#elseif  LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId==2) >
			                            <p class="navs8_p2">VIP2：<i>
		    								￥</i><span>
			                       		<lable class="vipPrice" >	${(goods.goodsVip2Price!0)/100}</lable>
			                        </span>
		   	 						<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
		   	 						<#elseif  LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId==3) >
			                        <p class="navs8_p2">VIP3：<i>
		    								￥</i><span>
			                       		<lable class="vipPrice" >${(goods.goodsVip3Price!0)/100}</lable>
			                       	</span>
		   	 						<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
			                        <#elseif  LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId==4) >
			                        <p class="navs8_p2">VIP4：<i>
		    								￥</i><span>
				                        	<lable class="vipPrice" >${(goods.goodsVip4Price!0)/100}</lable>
					                        </span>
				   	 						<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
			                        <#else>
			                              	<p class="navs8_p2">VIP：<i>
		    								￥</i><span>
		    								<lable class="vipPrice" >${(goods.goodsVip0Price!0)/100}</lable>
					                        </span>
				   	 						<a href="/userCenter.htm"  class="pay_lj">立即购买</a>
			                        </#if>
                    <#else>
                    		<p class="navs8_p2">VIP：<i>￥</i><span>	
                    	 	<lable class="vipPrice" >${(goods.goodsVip0Price!0)/100}</lable>
		                   	</span>
				   	 		<a href="/userCenter.htm"  class="pay_lj">立即购买</a>
                    </#if>
		   	 	</p>
			   	 		
			    	<#elseif goods.isBook==2  >
		    	<p  class="navs8_p2">预付费：<i>
		    	￥</i><span>
		    	 ${(goods.bookDownPayment!0)/100}
		    	 </span>
		    	 <#if LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId>=0) >
		   	 	<a href="/goods/goodsOrder/${goods.goodsId}.htm"  class="pay_lj">立即购买</a>
		   	 	<#else>
		   	 	<a href="/userCenter.htm"  class="pay_lj">立即购买</a>
		   	 	</#if>
		   	 	</p>
   	 	 </#if>
	        </section>
	    </article>
	</#if>
<script> 
		var mySwiper = new Swiper('.swiper-container',{
		pagination: '.swiper-pagination',
		loop : true,
		loopAdditionalSlides : 1,
		paginationClickable: true,

		 centeredSlides: true,
        autoplay: 5500,
        autoplayDisableOnInteraction: false
		})
</script>
 </body>
</html>
