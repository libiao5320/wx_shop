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
<script type="text/javascript" src="/js/consumption.js"></script>
<script type="text/javascript" src="/js/yxPublic.js"></script>
</head>
<body class="cpxq_qb">
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt top_txts">示卡消费券</p>
            <div class="top_right top_rights">
                <a class="top_sx" href=""></a>
            </div>
            <input type="hidden" name="img_url" val="${GOODS_IMG_URL !''}">
        </section>
    </header>
    <article id="ajaxLoad">
		<#if result ?? && (result?size>0)>
        	<#list result as res>
		        <section class="navss1">
		            <a href="/ption/${res.goodsId}.htm?orderType=${res.orderType !''}" class="navss1_p1">
			            <img src="${GOODS_IMG_URL!''}${res.goods_img !''}">
			            <div class="navss1_p2">
			            	<h3>${res.goods_name !''}</h3>
			            	<p>有效期：${res.time !''}</p>
			            	<span class="vip_nub">${res.storeId}</span>
			            </div>
			            
		            </a>
		        
		        </section>
        	</#list>
        </#if>
    </article>
	
	<article style="display:none">
        <section class="navss4">
            <div class="navss4_p1">
                <div class="navss4_p2"><img src="/images/skxfq_2.png"></div>暂无示卡消费券
            </div>
        </section>
    </article>
<script type="text/javascript">
$(function(){
	var isAjax = true;
	var page = 2;
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		if(isAjax){
			if(top >= (doheight-win_height)){
				isAjax = onloadAJAX(page);
				page++;
			}
		}
	});
});
</script>
 </body>
</html>
