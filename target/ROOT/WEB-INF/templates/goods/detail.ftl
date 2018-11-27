
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
     <link href="/css/swiper.min.css" rel="stylesheet" type="text/css">
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/swiper.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-min.js"></script>
    <script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
    <script type="text/javascript" src="/js/wx-share.js"></script>
    <script type="text/javascript">
    
	$(function(){
		var isCollect = true;
		var collectId = ${collectId !'null'};
		if(collectId != null){
			$("#collectId").val(collectId);
		}
		<#if isCollect == true>
			isCollect = true;
		<#else>
			isCollect = false;
		</#if>
		$("#isCollect").val(isCollect);
		
		if(isCollect ){//已收藏
			$(".top_sc").addClass("top_scs");
		}else{//未收藏
			
		}
		
		$(".top_sc").click(function(){
			isCollect = $("#isCollect").val();
			
			if(isCollect == 'true'){//已收藏
				collectId = $("#collectId").val();
				$.ajax({
					url: '/collect/uncollect.ajax' ,
					type: 'POST',
					data: {'id':collectId} ,
					dataType: 'json',
					success: function(json){
						if(json.state){
							$("#isCollect").val(false);
							$(".top_sc").removeClass("top_scs");
							$(".sccg").removeClass("sccg1");
							
							$(".sccg").html("&nbsp;&nbsp;取消成功&nbsp;&nbsp;");
							$(".sccg").fadeIn(1000);
							setTimeout(function(){
								$(".sccg").fadeOut(2000);
							},3000);
						}else{
							alert(json.message);
						}
					} ,
					error: function(){
						alert("网络忙，请稍后重试！");
					}
				});
			}else{//收藏
			
				$.ajax({
				url: '/collect/goods.ajax' ,
				type: 'POST',
				data: {'id':'${goods.goodsId !''}'},
				dataType: 'json',
				success: function(json){
					if(json.state){
						$("#isCollect").val(true);
						$("#collectId").val(json.param.id);
						$(".top_sc").addClass("top_scs");
						
						$(".sccg").html("恭喜您，收藏成功！");
						
						$(".sccg").fadeIn(1000);

						setTimeout(function(){
			
							$(".sccg").fadeOut(2000);
							
							
						},3000);
					}else{
						if(json.message == 'login'){
							$(".sccg").html("请先注册吧！");
							$(".sccg").fadeIn(1000);
							setTimeout(function(){
								$(".sccg").fadeOut(2000);
								window.location.href="/userCenter.htm";
							},3000);
							return;
						}else{
							alert(json.message);
						}
					}
				} ,
				error: function(){
					alert("网络忙，请稍后重试！");
				}
			});
			}
		});
	});
	</script>
</head>

<#if goods?? >
<body>
<div id="allmap"></div>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">服务详情</p>
        <div class="top_right top_rights">
            <a class="top_sc" href="javascript:;"></a>
            <a class="top_fx" href="javascript:;"></a>
        </div>
    </section>
</header>
<article>
	<input id="isCollect" type="hidden" value="">
	<input id="collectId" type="hidden" value="">
	<input id="lbsX" type="hidden" value="0">
	<input id="lbsY" type="hidden" value="0">
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
    <section class="navs6">
        <h1 class="navs_h1">${goods.goodsName!'' }</h1>
        <p class="navs6_p1">${goods.goodsIntroduce!'' }</p>
        <div class="cpxq_one">
            <p class="cpxq_one_p1">￥
            <i>			
	        <lable class="vipPrice" >${goods.goodsVip0PriceDollar !'0'}</lable>
	        </i>
            <span>查看vip价</span>
            <b style="background-color:#c0c0c0;display:none;">
            <i style="font-size:12px;">v1:￥${goods.goodsVip1PriceDollar !'0'}</i>
            <i style="font-size:12px;">v2:￥${goods.goodsVip2PriceDollar !'0'}</i>
            </b>
           </p>
            <p class="cpxq_one_p2"><i>￥
            			${goods.goodsVip3PriceDollar !'0'}<!--原价-->
		                        </i><span>已销售：${goods.goodsSalenum !0}</span></p>
           <!-- <p class="cpxq_one_p3"><a href=""><i>减</i>名店限时抢购，每人${goods.bookDownPayment}份<span></span></a></p>-->
            <p class="cpxq_one_p4">
               <#if goods.serviceShRefund  == 1 >
        	  			<span>随时退款</span>
		 	   </#if>
            	<#if goods.serviceShComplaint == 1>
						<span>售后投诉</span>
				</#if>
            </p>
            
        </div>
    </section>

    <section class="navs8">
        <a class="navs8_p1" id="navs8_p1" href="#navs13">好评率：<i>${goods.evaluationGoodProbability!'0'}%</i><span></span><em>
         <#assign sum = (goods.goodEvaluationCount+goods.badEvaluationCount) />${sum !'0'}人评价</em></a>
    </section>
    <section class="navs9">
        <div class="navs9_d">
            <div class="navs9_d1">
            	<a href="/store/detail.htm?id=${goods.storeId !'0'}&distance=${distance !'0'}">
                <div class="navs9_d1_left">
                    <p>商家名称</p>
                    <span>${goods.dstore.storeName!''}</span>
                </div>
                </a>
                <div class="navs9_d1_right"><a href="tel:${goods.dstore.storePhone}"></a></div>
            </div>
            <a href="javascript:;" id="storeId" class="navs9_p1"><i></i>${goods.dstore.areaInfo!''}${goods.dstore.storeAddress!''}<span></span></a>
            <#--<a href="" class="navs9_p1"><p>查看全部分店（3）</p><span></span></a>-->
        </div>
        
    </section>
   
    <section class="navs11">
        <h4 class="nav_h4">内容介绍</h4>
        <div class="navs11_d">
       	 ${goods.contentIntroduce!'' }

        </div>
        <a href="/goods/gdDetail.htm?goodsId=${goods.goodsId!'1'}" class="navs9_p1 navs11_p1"><i></i>查看图文详情<span></span></a>

    </section>
    <section class="navs11">
        <h4 class="nav_h4">融耀健康会员权益</h4>
        <div class="navs11_d">
			${goods.memberInterest!'' }
        </div>

    </section>
    <section class="navs12">
        <h4 class="nav_h4">购买须知</h4>
        ${goods.buyKnow!'' }
    </section>
    <section class="navs13" id="navs13" name="navs13" style="margin-bottom: 80px;">
        <h4 class="nav_h4">评价</h4>
        
        <ul class="navs13_ul">
            <#if evalList ?? && (evalList?size gt 0)>
            <#list evalList as evaluate >
            
            <li class="navs13_li">
				<#if (evaluate.isAnonymous != 'Y') && (evaluate.member??)>
                <div class="navs13_left">
					<img src="${evaluate.member.memberAvatar}" title="" alt=""/>
                </div>

				<#else >
                    <div class="navs13_left">
         	 			<img src="../../images/cpxq_tx02.jpg " title="" alt=""/>
					</div>

				</#if>
                <div class="navs13_right">

                    <p class="navs13_right_p1">
                    	<#if (evaluate.isAnonymous != 'Y') && (evaluate.member??)>
                    	 ${evaluate.member.memberName !''}
                    	<#else > 
                    	匿名用户
                    	</#if><span>${evaluate.createTime!'' } </span></p>

                    <section class="navss12_p6 navss12_p6s"><p class="navs13_right_p2 navs13_right_p2s">
						<#if evaluate.grade == 0>
                            <i></i><i></i><i></i><i></i><i></i>
						<#elseif evaluate.grade == 1>
                            <i class="navs13_i"></i><i></i><i></i><i></i><i></i>
						<#elseif evaluate.grade == 2>
                            <i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
						<#elseif evaluate.grade == 3>
                            <i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
						<#elseif evaluate.grade == 4>
                            <i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i>
						<#elseif evaluate.grade == 5>
                            <i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
						<#else>
                            <i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i>
						</#if>
                    </p></section>

                </div>
                <p class="navs13_p1" >评价内容 ： ${evaluate.detail!'' }</p>
                <#if evaluate.imageList ?? >
	                 <#list evaluate.imageList as imglist >
	                 <ul>
	                 	<li><a><img src="${imglist.img!''}" alt="" title=""></a></li>
	                 </ul>
	                 </#list>
	                 
                 </#if>
               
            </li>
             </#list>
             <#else>
             <li class="navs13_li">
             	暂无评论
             </li> 		 
            </#if>
        </ul>

        <#if goods.devaluate?? && goods.devaluate?size gt 0 && (goods.goodEvaluationCount+goods.badEvaluationCount) gt goods.devaluate?size >
        <a href="" class="navs9_p1"> <p>查看全部${goods.goodEvaluationCount+goods.badEvaluationCount}条评价 </p><span></span></a>
        </#if>
    </section>
    
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

    <section class="pay_bt">
        <p class="pay_bt_p1">服务名称  ${goods.goodsName!''}</p>
        <p class="pay_bt_p2">¥${(goods.goodsPrice!0)/100}</p>
        <p class="pay_bt_p3">购买数量 <span><a class="pay_delete">-</a><input type="text" placeholder="1" value="1"/><a class="pay_add">+</a></span></p>
    </section>
 	<#if goodsSet?? && goodsSet.technicianId??>
 	<a class="ljzx" href="http://api.royao.cc/AppAPI/chat/html/chat.html?doctorId=${goodsSet.technicianId !''}"></a>
 	<#else>
 	<a class="ljzx" href="http://w.royao.cc/techie/detail/80.htm"></a>
 	</#if>
    <div class="ceng3"></div>
	<p class="sccg">收藏成功</p>
</article>
<script type="text/javascript">
	$(function(){
	
		var map = new BMap.Map("allmap");
		var point =null;// new BMap.Point(116.331398,39.897445);
		map.centerAndZoom(point,12);
	    var temp=null;
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				var mk = new BMap.Marker(r.point);
				map.addOverlay(mk);
				map.panTo(r.point);
				
				$("#lbsX").val(r.point.lng);
				$("#lbsY").val(r.point.lat);
				
			}
			else {
				ajaxload(1,1);
				alert('failed'+this.getStatus());
			}        
		},{enableHighAccuracy: true});
	
		$("#storeId").click(function(){
		
			var lbsX = $("#lbsX").val();
			var lbsY = $("#lbsY").val();
			
			if(lbsX == 0 || lbsX == '' || lbsX == null || lbsY == 0 || lbsY == '' || lbsY == null){
				$(".sccg").html("正在定位，请稍后...");
				$(".sccg").fadeIn(1000);
				setTimeout(function(){
					$(".sccg").fadeOut(2000);
				},3000);
				return;
			}
		
			window.location.href="/map/goodsmap.htm?id=${goods.dstore.storeId!''}&lbsX="+lbsX+"&lbsY="+lbsY;
		});
		
		$(".top_fx").click(function(){
			shareRedPack();
		});
		
		$('.cpxq_one_p1').click(function(){
			var _this = $(this).find('span').next();
			if(_this.is(':hidden')){
				_this.show();
			}else{
				_this.hide();
			}
		});
	});
    var shareRedPack = function(){
			var data = {
					title: '${goods.goodsName!''}', // 分享标题
					desc: '${goods.goodsIntroduce !''}', // 分享描述
					link: '${shareurl !''}', // 分享链接
					imgUrl:'${http_img!''}${goods.goodsImage !''}', // 分享图标
					type: 'link', // 分享类型,music、video或link，不填默认为link
					//dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					success: function (res) { 
						//alert('分享成功')
						
						$(".sccg").html("分享成功");
						$(".sccg").fadeIn(1000);
						setTimeout(function(){
							$(".sccg").fadeOut(2000);
						},3000);
						/***
						$.ajax({
							url: '/goods/share.ajax' ,
							type: 'POST',
							data: '',
							dataType: 'json',
							success: function(json){
								if(json.message != 'login'){
									
									$(".sccg").html(json.message);
									$(".sccg").fadeIn(1000);
									
									setTimeout(function(){
										$(".sccg").fadeOut(2000);
										$(".sccg").removeClass("sccg1");
									},3000);
								}
							} ,
							error: function(){
								alert("网络忙，请稍后重试！");
							}
						});
						***/
						$('.fxfx').hide();
					},
					cancel: function () { 
						//alert('取消分享');
						$('.fxfx').hide();
					},fail:function(){
						//alert('分享失败');
						$('.fxfx').hide();
					}
			};
            sharePal(data,'share');
    }

</script>
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
</#if>

</html>


