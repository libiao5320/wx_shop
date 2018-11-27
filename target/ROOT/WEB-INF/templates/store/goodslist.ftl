<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">${storeName !''}</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
    <section class="navss13">
        <ul class="navs4 navss12_ul" id="js_goodList">
        	<#if list?? && (list?size > 0)>
        	<#list list as goods>
            <li>
                <a href="/goods/detail.htm?goodsId=${goods.goodsId !''}">
                    <div class="navs4_left">
                        <p>
                        <img src="${GOODS_IMG_URL!''}${goods.goodsImage !''}" alt="" title="">
                        </p>
                    </div>
                    <div class="navs4_right">
                        <p class="navs4_p1 navss11_p1">${goods.goodsName !''}</p>
                        <p class="navs4_p2 navss12_p5">${goods.goodsIntroduce !''}</p>
                        <p class="navs4_p3">
                        ¥<#if vip?? && vip == 0>${goods.goodsVip0PriceDollar !'0.00'}</#if>
                        <#if vip?? && vip == 1>${goods.goodsVip1PriceDollar !'0.00'}</#if>
                        <#if vip?? && vip == 2>${goods.goodsVip2PriceDollar !'0.00'}</#if>
                        <#if vip?? && vip == 3>${goods.goodsVip3PriceDollar !'0.00'}</#if>
                        <#if vip?? && vip == 4>${goods.goodsVip4PriceDollar !'0.00'}</#if>
                        
                        <i>${goods.goodsVip3PriceDollar !''}</i><span>已售${goods.goodsSalenum !''}</span></p>
                    </div>
                </a>
            </li>
            </#list>
            </#if>
        </ul>
    </section>
</article>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/js.js"></script>
<script type="text/javascript" src="js/goods.js"></script>
<script type="text/javascript">
    var scrollSign = false;
    $(document).ready(function (){
   

        var scroll = function () {

            if (scrollSign && $(this).scrollTop() <= 0) {
                scrollSign = false;
                goodlist(0);
                return;
            }

            var bottonHeight = $(window).height()+$(this).scrollTop();
            var orderHeight = $("#js_orderListDIV").height()+30;
            if(scrollSign && bottonHeight >= orderHeight) {
                scrollSign = false;
                goodlist($("#js_orderListDIV div").length);
            }
        };
        $(window).scroll(scroll);
    });

</script>
</body>
</html>
