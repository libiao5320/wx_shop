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
    <script type="text/javascript" src="/js/recharge.js"></script>
    <script type="text/javascript" src="/js/yxPublic.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);" onclick="window.history.go(-1);"></a>
        <p class="top_txt top_txts">余额明细</p>
        <div class="top_right top_rights">
        </div>
    </section>
</header>
<article>
    <section class="navs40">
        <ul class="navs40_ul" id="ajaxLoad">
        </ul>
    </section>
    <input type="hidden" value="2" name="page">
</article>
<script type="text/javascript">
var page = 2;
var isAjax = true;
$(function(){
	ajaxLoad(1);
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		if(isAjax){
			if(top >= (doheight-win_height)){
				ajaxLoad(page);
				page++;
			}
		}
	});
});
</script>
</body>
</html>
