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
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">生成订单</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
    <section class="navs28">
        <div class="navs28_d" ><span><img src="/images/cpxq_zfcg.png" alt="" title=""></span>恭喜您支付成功</div>
    </section>
    <section class="navs29">
   	 <#if orderList ??>
   	 <#list orderList as list >
   	 	<#if list_index==0 >
	        <p class="navs29_p1"><span>产品名称</span><i> ${list.goodsName!''}</i>
	        </p>
	        <p class="navs29_p2">示卡消费券（${orderList ? size}张）</p>
        </#if>
         
 		<p class="navs29_p3">消费券 ${list_index+1} <span>${list.consumptionCode!''}</span></p>
        	</#list>
        </#if >
    </section>
    <section class="navs30">
        <a href="/ption/1.htm?time=${paySn !''}">
        <span>
        	</span>查看我的消费券</a>
    </section>

	<a class="fhsy fhsys" href="/index.htm"></a>
</article>


</body>
</html>
