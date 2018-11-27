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
    <!--<script type="text/javascript" src="/js/js.js"></script>-->
    <script type="text/javascript" src="/js/yxPublic.js"></script>
    <script type="text/javascript" src="/js/recharge.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" onclick="window.history.go(-1);"></a>
        <p class="top_txt">充值</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
    <section class="navs32">
        <ul class="navs32_ul">
           <#if vipList.vipCard ?? && (vipList.vipCard?size>0)>
        	<#list vipList.vipCard as vip>
	            <li class="navs32_li">
	                <div class="navs32_d1">
	                	<input type="hidden" value="${vip.greade !'0'}">
	                    <p class="navs32_p1"><nub>${vip.require !''}</nub>元</p>
	                    <p class="navs32_p2"><nub>${vip.name !'VIP0'}</nub></p>
	                </div>
	            </li>
            </#list>
           </#if>
        </ul>
        <input type="hidden" value="0" name="greade">
        <p class="navs32_p3"><label><input type="text" name="money" value="" placeholder="输入金额"></label><i>VIP0</i></p>
        <a class="navs32_a navs32_as" id="at_pay">立即充值</a>
    </section>
    <section class="navs33">
        <p class="navs33_p2">付成功后，金额会直接充值到您的账户中。</p>
        <p class="navs33_p2">注意：余额可用户购买VIP商城所以产品，并享受VIP价。</p>
        <p class="navs33_p1">融耀健康提供三个等级的通用储值额度供您选择：</p>
        <p class="navs33_p2">充值1-499元，享受VIP0级的价格优惠；</p>
        <p class="navs33_p2">充值500-1999元，享受VIP1级的价格优惠；</p>
        <p class="navs33_p1">充值2000元以上，享受VIP2级的价格优惠；</p>
    </section>
    <input type="hidden" value="" name="url">
</article>
<script type="text/javascript">
	$(function(){
		var url = document.referrer;
		$('input[name="url"]').val(encodeURIComponent(url));
	})
</script>
</body>
</html>
