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
    <section class="navs34">
    	<input type="hidden" name="sn" value="${vipList.sn !''}">
        <p class="navs34_p1"><span>本次充值金额</span><i>${vipList.money !'0.00'}元</i></p>
        <p class="navs34_p2">选择充值方式</p>
        <ul class="navs34_ul">
            <li class="navs34_li">
                <a><i class="wxzf"></i>微信支付<label class="labels"><input type="radio" name="pay_c" value="WXPAY"><span class="anbh"></span></label></a>
            </li>
            <!--<li class="navs34_li">
                <a><i class="wxzf"></i>微信支付<label class="labels"><input type="radio" name="pay_c"><span class="anbh"></span></label></a>
            </li>-->
        </ul>
    </section>
    <a class="navs32_a navs32_as pay_lj_1">立即充值</a>
</article>

<!--<section class="pay_bt1">
	<input type="hidden" name="isNot" value="N">       
    <div class="pay_bt1_three">
        <p class="pay_bt1_p1">输入支付密码</p>
        <p class="pay_bt1_p6"><label><input type="password" name="password" placeholder="输入支付密码" style="width:84%"></label><a style="width:61px;" id="pay">立即充值</a></p>
        <p class="pay_bt1_p7"><a href="">忘记密码？</a></p>
    </div>
</section>-->
<input type="hidden" name="http_url" value="${url !''}">
<div class="ceng3"></div>
</body>
</html>
