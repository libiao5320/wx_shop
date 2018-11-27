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
    <script type="text/javascript" src="/js/kiting.js"></script>
    <script type="text/javascript" src="/js/yxPublic.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);" onclick="window.history.go(-1);"></a>
        <p class="top_txt top_txts">余额处理记录</p>
        <div class="top_right top_rights">
        </div>
    </section>
</header>
<article>
    <section class="navs40">
        <ul class="navs40_ul" id="ajaxLoad">
            <!--<li>
                <p class="navs40_p1"  >消费<i class="navs40_i">-60.00</i></p>
                <p class="navs40_p2">2015-11-20<span class="navs40_span"></span></p>
            </li>-->
        </ul>
    </section>
    <input type="hidden" value="2" name="page">
</article>
<script type="text/javascript">
$(function(){
	ajaxLoad(1);
	var page = 2;
	var isAjax = true;
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		if(isAjax){
			if(top >= (doheight-win_height)){
				isAjax = ajaxLoad(page);
				page++;
			}
		}
	});
});
</script>
</body>
</html>
