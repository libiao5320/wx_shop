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
            <p class="top_txt">私人健康师主页</p>
            <div class="top_right top_rights">
                <a class="top_sx" href=""></a>
            </div>
        </section>

    </header>
    <article>
		<#if techie??>
        <section class="navs48">
            <div class="navs10_d">
                <ul>
                    <li class="navs10_d_li">
                        <div class="navs10_left">
                            <a href="">
                                <img src="${TECHIE_IMG_URL!''}${techie.img !''}" title="" alt="">
                            </a>
                            <span class="navs10_left_xtb"></span>
                        </div>
                        <div class="navs10_right">
                            <div class="navs10_right_01">
                                <p class="navs10_p1">${techie.techieName !''} <span class="navs48_s1">${techie.rzPosition !''}/<#if techie.techieClass??>${techie.techieClass.className !''}<#else>-</#if>      </span></p>
                                <p class="navs10_p2">${techie.unit !''}</p>
                            </div>
                            <div class="navs10_right_02">
                                <span class="navs48_s2"></span>
                                <span class="navs48_s3">${techie.dsTotalNum !''}</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
        <section class="navs49">
            <div class="navs9_d1">
                <div class="navs9_d1_left">
                    <p>私人医生免费咨询</p>
                    <span>通过文字、图片、短语音咨询</span>
                </div>
                <div class="navs9_d1_right">
                    <a href="http://api.royao.cc/AppAPI/chat/html/chat.html?doctorId=${techie.techieId !''}">
                        <span class="navs49_s1"><img src="/images/srjk_mfzx.png" alt="" title=""></span>
                        <span class="navs49_s2">免费咨询</span>
                    </a>
                </div>
            </div>
            <div class="navs9_d1">
                <div class="navs9_d1_left">
                    <p>私人医生助理服务</p>
                    <span>7*24小时在线为您服务</span>
                </div>
                <div class="navs9_d1_right">
                	<!--doctorId由于没有设计数据库，暂时搞个死的-->
                    <a href="http://api.royao.cc/AppAPI/chat/html/chat.html?doctorId=${techie.techieId !''}">
                        <span class="navs49_s1"><img src="/images/srjk_mfzx1.png" alt="" title=""></span>
                        <span class="navs49_s3">免费咨询</span>
                    </a>
                </div>
            </div>
            <#if techie?? && (techie.techieGoodsList??) && (techie.techieGoodsList?size > 0)>
            <#list techie.techieGoodsList as techieGoodsList>
            <#if techieGoodsList?? && (techieGoodsList?size > 0)>
			<#list techieGoodsList.goodsList as goods>
            <div class="navs9_d1">
                <div class="navs9_d1_left">
                    <p>${goods.goodsName !''}</p>
                    <span>${goods.goodsIntroduce !''}</span>
                </div>
                <div class="navs9_d1_right">
                    <a href="/goods/detail.htm?goodsId=${goods.goodsId !''}">
                        <span class="navs49_s1"><img src="/images/srjk_ks1.png" alt="" title=""></span>
                        <span class="navs49_s3">￥${goods.goodsVip0PriceDollar !'0.00'}</span>
                    </a>
                </div>
            </div>
            </#list>
            </#if>
            </#list>
            </#if>
        </section>
        <section class="navs_50">
            <h1 class="navs_50_h1">擅长</h1>
            <p class="navs_50_p1">${techie.goodAt !''}</p>
            <h1 class="navs_50_h1"><#if techie.store??>${techie.store.storeName !''}</#if></h1>
            <#if techie.store??>
            <a href="/map/storemap.htm?id=${techie.store.storeId !''}" class="navs9_p1"><i></i>${techie.store.storeAddress !''}<span></span></a>
            </#if>
            <!-- <p class="navs_50_p2">打赏<span class="das"></span></p> -->
        </section>
        <section class="navs_51">
            <a href="/techie/evaluatelist.htm?id=${techie.techieId !''}" class="navs9_p1"><i></i>用户评价（<nub>${techie.countEva !'0'}</nub>）<span></span></a>
            <ul class="navs_51_ul">
            	<#if techie?? && (techie.techieEvaList??) && (techie.techieEvaList?size > 0)>
            	<#list techie.techieEvaList as eva>
                <li class="navs_51_li">
                    <p class="navs_51_p1">
                    	<#if eva?? && eva.member ?? && eva.member.memberMobile??>
                    	${eva.member.memberMobile?substring(0,2)}****${eva.member.memberMobile?substring(7,10)}
                    	<#elseif eva?? && eva.member ??>
                    	${eva.member.memberName !''}
                    	<#else>
                    	匿名用户
                    	</#if>
                    	<span><#if eva.grade lt 3>不满意<#else>满意</#if></span><i>${eva.createTimeStr !''}</i></p>
                    <p CLASS="navs_51_p2">${eva.detail !''}</p>
                   <!-- <p CLASS="navs_51_p3"><a class="navs_51_a1">回复很及时</a><a class="navs_51_a1">态度非常好</a> </p>-->
                </li>
                </#list>
                </#if>
            </ul>
        </section>
    </article>
	<#else>
		健康师信息获取失败
	</#if>
 </body>
</html>
