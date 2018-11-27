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
    <script type="text/javascript" src="/js/yxPublic.js"></script>
    <script type="text/javascript" src="/js/kiting.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);" onclick="window.history.go(-1);"></a>
        <p class="top_txt">余额处理</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
        
    </section>
</header>
<article>
    <section class="navs35">
    	<input type="hidden" name="money" value="${money}">
    	<p class="" style="height:45px; line-height:45px;background:#EEE;padding-right:10px; margin-top:-15px;"><a id="yecls" style="float:right;" href="/kiting/record.htm">余额处理记录</a></p>
        <p class="navs35_p1"><span>可提现金额</span><i>${money !'0'}元</i></p>
        <p class="navs35_p1"><span>金额</span><label><input type="text" placeholder="请输入提现金额" name="amount" value="" id="money">元</label></p>
    </section>
  
    <a class="navs32_a navs32_as" id="kiting">提交</a>
</article>
</body>
</html>
