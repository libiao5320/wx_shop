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
<body>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">商家详情</p>
        <div class="top_right top_rights">
        </div>
    </section>
</header>
<article>
	<#if json??>
	${json.text !''};
	</#if>
</article>
</body>
</html>
