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
        </section>
    </header>
    <input type="hidden" name="time" value="${time !''}">
    <input type="hidden" name="goodsId" value="${goods_id !''}">
    <input type="hidden" name="orderType" value="${order_type !''}">
    <article id="ajaxLoadDetail">
    <#if result ?? && (result?size>0)>
    	<#list result as res>
	        <section class="navss3" url="/order/detail/${res.id}.htm">
	        	<div class="navss1_p2"><h3>${res.goods_name !''}</h3><p>有效期：${res.tion_time !''}</p></div>
	            <p class="navss3_p1">券号：<span><#if res.no?? && (res.no?length >= 10)>${res.no?substring(0,4)}&nbsp;&nbsp;${res.no?substring(4,7)}&nbsp;&nbsp;${res.no?substring(7,10)}</#if></span></p>
	           	<div class="navss3_p2 navss3_p2s">
	           		<img src="/images/skxq_1.jpg" alt="" title="">
	           		<div class="navss3_p2_d">
	           			<img src="/uploads/ticket/${res.qr_code !''}" alt="" title="">
	           		</div>
	           	</div>
	        </section>
        </#list>
    </#if>
    </article>
    <input type="hidden" name="page" value="2">
<script type="text/javascript">
$(function(){
	var isAjax = true;
	var page =2;
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		if(isAjax){
			if(top >= (doheight-win_height)){
				isAjax = onloadAJAXDetial(page);
				page++;
			}
		}
	});
});
</script>
 </body>
</html>
