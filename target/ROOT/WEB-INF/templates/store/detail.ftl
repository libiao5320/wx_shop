<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-min.js"></script>
    <script type="text/javascript" src="/js/js.js"></script>
    <script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="/js/wx-share.js"></script>
    <script type="text/javascript">
	$(function(){
		
		var isCollect = true;
		var collectId = ${collectId !'null'};
		if(collectId != null){
			$("#collectId").val(collectId);
		}
		isCollect = ${isCollect?string('true','false') !'false'};
		$("#isCollect").val(isCollect);
		
		if(isCollect){//已收藏
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
				url: '/collect/store.ajax' ,
				type: 'POST',
				data: {'id':${store.storeId !'null'}} ,
				dataType: 'json',
				success: function(json){
					if(json.state){
						$("#isCollect").val(true);
						$("#collectId").val(json.param.id);
						$(".top_sc").addClass("top_scs");
						$(".sccg").html("&nbsp;&nbsp;恭喜您，收藏成功!&nbsp;&nbsp;");
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
<body>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">商家详情页</p>
        <div class="top_right top_rights">
            <a class="top_sc" href="javascript:;"></a>
            <a class="top_fx" href="javascript:;"></a>
        </div>
    </section>
</header>
<article>
	<input id="isCollect" type="hidden" value="">
	<input id="collectId" type="hidden" value="">
	<section class="navs4 navss12">
        <ul>
            <li>
                <a href="javascript:;">
                    <div class="navs4_left">
                        <p>
                        <img src="${STORE_IMG_URL!''}${store.storeLabel !''}" alt="" title="">
                        </p>
                    </div>
                    <div class="navs4_right">
                        <p class="navs4_p1 navss11_p1">${store.storeName !''}</p>
                        <p class="navss11_p2">好评率: <i>${store.evaluationStoreProbability !''}%</i></p>
                    </div>
                </a>
            </li>
        </ul>
    </section>
    <section class="navs6 navss12_p1">
        <div class="cpxq_one">
        	<#if store?? && (store.eventList??) && (store.eventList?size > 0)>
        	<#list store.eventList as event>
            <p class="cpxq_one_p3"><a href="/event/detail.htm?id=${event.id !''}"><i>${event.eventKey !''}</i>${event.name !''}<span></span></a></p>
            </#list>
            </#if>
        </div>
    </section>
    <section class="navs9">
        <div class="navs9_d">
            <a href="/map/storemap.htm?id=${store.storeId !''}&distance=${distance !'0'}" class="navs9_p1 navss12_p3"><i></i>${store.storeAddress !''}<span></span></a>
            <p class="navss12_p2">营业时间<span>${store.storeWorkingtime !''}</span></p>
            <a href="tel:${store.storePhone !''}" class="navs9_p1 navss12_p4"><i></i>${store.storePhone !''}<span></span></a>
        </div>
    </section>
    <section class="navs10">
        <div class="navs10_d">
            <h3 class="nav_h4 navss12_h1">服务（${store.goodsCount}）
            	<#if store.goodsCount?? && store.goodsCount gt 0>
            	<a href="/store/goodslist.htm?id=${store.storeId}&storeName=${store.storeName}">查看全部</a>
            	</#if>
            </h3>
            <ul class="navs4 navss12_ul">
            	<#if store?? && (store.goodsList??) && (store.goodsList?size > 0)>
            	<#list store.goodsList as goods>
                <li>
                    <a href="/goods/detail.htm?goodsId=${goods.goodsId !'null'}">
                        <div class="navs4_left">
                            <p>
                            <img src="${GOODS_IMG_URL!''}${goods.goodsImage !''}" alt="" title="">
                            </p>
                        </div>
                        <div class="navs4_right">
                            <p class="navs4_p1 navss11_p1">${goods.goodsName !''}</p>
                            <p class="navs4_p2 navss12_p5">${goods.goodsIntroduce !''}</p>
                            <p class="navs4_p3">¥
                            <#if vip?? && vip == 0>${goods.goodsVip0PriceDollar !'0.00'}</#if>
	                        <#if vip?? && vip == 1>${goods.goodsVip1PriceDollar !'0.00'}</#if>
	                        <#if vip?? && vip == 2>${goods.goodsVip2PriceDollar !'0.00'}</#if>
	                        <#if vip?? && vip == 3>${goods.goodsVip3PriceDollar !'0.00'}</#if>
	                        <#if vip?? && vip == 4>${goods.goodsVip4PriceDollar !'0.00'}</#if>
                            <i>${goods.goodsVip3PriceDollar !''}<!-- 原价 --></i><span>已售${goods.goodsSalenum !''}</span></p>
                        </div>
                    </a>
                </li>
                </#list>
                </#if>
            </ul>
        </div>
    </section>
    <section class="navss12_p6"><a href="/store/text/${store.storeId !'null'}.htm">商家详情介绍</a></section>
    <section class="navs13">
        <h4 class="nav_h4">评价</h4>
        <ul class="navs13_ul">
        	<#if store?? && (store.evaluateList??) && (store.evaluateList?size >0)>
        	<#list store.evaluateList as eva>
            <li class="navs13_li">
                <div class="navs13_left">
                	<#if eva.member??>
                    <img src="${eva.member.memberAvatar !''}" title="" alt="">
                    <#else>
                    <img src="/images/cpxq_tx01.jpg" title="" alt="">
                    </#if>
                </div>
                <div class="navs13_right">
                    <p class="navs13_right_p1">
                    	<#if eva?? && eva.member ?? && eva.member.memberMobile??>
                    	${eva.member.memberMobile?substring(0,2)}****${eva.member.memberMobile?substring(7,10)}
                    	<#elseif eva?? && eva.member ??>
                    	${eva.member.memberName !''}
                    	<#else>
                    	匿名用户
                    	</#if>
                    	<span>${eva.strCreateTime !''}</span>
                    </p>
                    <p class="navs13_right_p2">
                    	<#if eva.grade?? && eva.grade == 0>
                    	<i></i><i></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 1>
                    	<i class="navs13_i"></i><i></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 2>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 4>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i>
                    	<#elseif eva.grade?? && eva.grade == 5>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
                    	<#else>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
                    	</#if>
                    </p>
                </div>
                <p class="navs13_p1">${eva.detail !''}</p>
                <#if eva?? && (eva.imageList??) && (eva.imageList?size gt 0)>
                <ul>
                	<#list eva.imageList as imgs>
                    <li><a><img src="${imgs.img !''}" alt="" title=""></a></li>
                    </#list>
                </ul>
                </#if>
            </li>
            </#list>
            </#if>
        </ul>
        <#if store?? && (store.evaluateCount??) && (store.evaluateCount > 0)>
        <a href="/store/evaluatelist.htm?id=${store.storeId !''}" class="navs9_p1"><p>查看全部${store.evaluateCount !'0'}条评价</p><span></span></a>
        <#else>
        <a href="javascript:;" class="navs9_p1"><p>查看全部0条评价</p><span></span></a>
        </#if>
    </section>
    <p class="sccg">收藏成功</p>
</article>
<script type="text/javascript">
	$(function(){
		$(".top_fx").click(function(){
			shareRedPack();
		});
	});
        var shareRedPack = function(){
				var data = {
						title: '${store.storeName !''}', // 分享标题
						desc: '${store.storeDescribe !''}', // 分享描述
						link: '${shareurl !''}', // 分享链接
						imgUrl:'${store.storeLabel !''}', // 分享图标
						type: 'link', // 分享类型,music、video或link，不填默认为link
						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
						success: function (res) { 
							//alert('分享成功')
							
							$(".sccg").html("&nbsp;&nbsp;分享成功&nbsp;&nbsp;");
							$(".sccg").fadeIn(1000);
							setTimeout(function(){
								$(".sccg").fadeOut(2000);
							},3000);
							
							/**
							$.ajax({
								url: '/store/share.ajax' ,
								type: 'POST',
								data: '',
								dataType: 'json',
								success: function(json){
									if(json.message != 'login'){
										$(".sccg").html("&nbsp;&nbsp;"+json.message+"!&nbsp;&nbsp;");
										$(".sccg").fadeIn(1000);
										setTimeout(function(){
											$(".sccg").fadeOut(2000);
										},3000);
									}
								} ,
								error: function(){
									alert("网络忙，请稍后重试！");
								}
							});
							**/
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
</body>
</html>
