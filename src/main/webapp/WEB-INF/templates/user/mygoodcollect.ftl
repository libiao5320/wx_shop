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
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="/userCenter.htm"></a>
        <p class="top_txt top_txts"><a href="/myCollect/1/1.htm" class="sc_fw sc_bt" href="">服务（${result.countMap.goodcount! 0}）</a><a href="/myCollect/2/1.htm" class="sc_dp" href="">商家（${result.countMap.storecount! 0}）</a></p>
        <div class="top_right top_rights">
            <a class="top_dlt" href="javascript:;"></a>
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
<input type="hidden" name="img_url" value="${GOODS_IMG_URL !''}">
<input type="hidden" name="sige" value="${LOGIN_SIGN.vipRankId !'0'}">
<#if   result.countMap.goodcount?? &&   result.countMap.goodcount gt 0>



    <section class="navs41">
    	<form id="form-data">
        <ul class="navs41_ul" id="onloadAjAx">


        <#if result?? >
            <#if  result.collectList?? && result.collectList?size gt 0 >
            <#list  result.collectList as c>
            <li>
            	<div class="xzkk">
						<label class="xzkk_lb" ><i class="xzkk_i"></i><input type="checkbox"  value="${c.id !''}" name="xzkk_n" class="xzkk_c"/></label>
				</div>
				<#if c.good.goodsId??>
		                <div class="navs41_d">
		                    <a href="/goods/detail.htm?goodsId=${c.good.goodsId !''}">
		                    <div class="navs41_left">
		                      <img src="${GOODS_IMG_URL}${c.good.goodsImage!''}" alt="" title="">
		                    </div>
		                    <div class="navs41_right">
		                        <p class="navs41_right_p1">${c.good.goodsName !""}</p>
		                        <p class="navs41_right_p2"><span class="sc_right01">${c.good.dstore.fjLandmark !''}</span></p>
		                        <p class="navs41_right_p3"><span class="navs40_s1">
		                        <#if LOGIN_SIGN  ?? >
		                            <#if LOGIN_SIGN.vipRankId == 0 >
		                                <lable class="vipPrice" >￥ ${(c.good.goodsVip0Price!0)/100}</lable>
		                            <#elseif  LOGIN_SIGN.vipRankId == 1 >
		                                <lable class="vipPrice" >￥${(c.good.goodsVip1Price!0)/100}</lable>
		                            <#elseif  LOGIN_SIGN.vipRankId ==2 >
		                                <lable class="vipPrice" >	￥${(c.good.goodsVip2Price!0)/100}</lable>
		                            <#elseif  LOGIN_SIGN.vipRankId == 3 >
		                                <lable class="vipPrice" >￥ ${(c.good.goodsVip3Price!0)/100}</lable>
		                            <#elseif  LOGIN_SIGN.vipRankId == 4 >
		                                <lable class="vipPrice" >￥ ${(c.good.goodsVip4Price!0)/100}</lable>
		                            </#if>
		                        <#else>
		                            <lable class="vipPrice" >￥${(c.good.goodsVip0Price!0)/100}</lable>
		                        </#if>
		                       </span><i class="sc_mid01">${c.good.goodsVip3PriceDollar !0}</i><a class="navs40_a" href="/goods/goodsOrder/${c.good.goodsId !""}.htm">立即购买</a></p>
		                    </div>
		                    </a>
		                </div>
		        </#if>
                </li>
            </#list>
            <#else>
                <section class="navs46">
                    <p class="navs46_p1"><img title="" alt="" src="/images/zwsc.png"></p>
                    <p class="navs46_p2">暂无收藏</p>
                </section>
            </#if>
            </#if>
            <#--<li>-->
                <#--<div class="navs41_d">-->
                    <#--<div class="navs41_left">-->
                        <#--<a href=""><img src="images/img12.jpg" alt="" title=""></a>-->
                    <#--</div>-->
                    <#--<div class="navs41_right">-->
                        <#--<p class="navs41_right_p1">产品名称名称</p>-->
                        <#--<p class="navs41_right_p2"><span class="sc_right01">八一路<500m</span></p>-->
                        <#--<p class="navs41_right_p3"><span class="navs40_s1">¥123</span><i class="sc_mid01">¥159</i><a class="navs40_a" href="">立即购买</a></p>-->


                    <#--</div>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li>-->
                <#--<div class="navs41_d">-->
                    <#--<div class="navs41_left">-->
                        <#--<a href=""><img src="images/img12.jpg" alt="" title=""></a>-->
                    <#--</div>-->
                    <#--<div class="navs41_right">-->
                        <#--<p class="navs41_right_p1">产品名称名称</p>-->
                        <#--<p class="navs41_right_p2"><span class="sc_right01">八一路<500m</span></p>-->
                        <#--<p class="navs41_right_p3"><span class="navs40_s1">¥123</span><i class="sc_mid01">¥159</i><a class="navs40_a" href="">立即购买</a></p>-->


                    <#--</div>-->
                <#--</div>-->
            <#--</li>-->

        </ul>
	</form>

        <ul class="navs41_ul" id="storeList">



        </ul>


    </section>

<#else>
<section class="navs46">
    <p class="navs46_p1"><img title="" alt="" src="/images/zwsc.png"></p>
    <p class="navs46_p2">暂无收藏</p>
</section>
</#if>
</article>
<div class="xzkk_bt">
    <label class="xzkk_bt_s"><i class="xzkk_i"></i><input type="checkbox" value="" name="" id="qxzl" class="qxzl"/>全选</label> <a class="xzkk_bt_a" href="javascript:;" url="/collect/deteleCollect.ajax">删除</a>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/js.js"></script>
<script type="text/javascript" src="/js/collect.js"></script>
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
				isAjax = collectAJax(page,'/myCollect/1.htm');
				page++;
			}
		}
	});
});



</script>
</body>
</html>
