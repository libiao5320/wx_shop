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
    <script type="text/javascript" src="/js/js.js"></script>
    <style>
		.navs5 .lvj{-webkit-filter: grayscale(100%);
    -moz-filter: grayscale(100%);
    -ms-filter: grayscale(100%);
    -o-filter: grayscale(100%);
    
    filter: grayscale(100%);
	
    filter: gray}
	</style>
</head>
<body>
<script>
   $(function(){
    	//我知道了
    	$("#iknow").click(function(){
    		
    		$.ajax({
				url: '/event/isKnow.ajax' ,
				type: 'POST',
				dataType: 'json',
				success: function(json){
					//alert(json.message);
				} ,
				error: function(){
				}
			});
    		
    	});
    });
    
    </script>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">活动详情</p>
        <div class="top_right top_rights">
            <!--<a class="top_sc" href=""></a>-->
            <!--<a class="top_fx" href="javascript:;"></a>-->
        </div>
    </section>
</header>
<article>
    <section class="navs5">
        <nav>
            <ul>
                <#if event.strEventTime?date gt .now?date>
            	<li>
            	<#else>
                <li class="lvj">
                </#if>
                	<a href="javascript:;"><img src="${GOODS_IMG_URL!''}${event.img!''}" alt="" title=""></a>
                </li>
            </ul>
        </nav>
    </section>
    <section class="navs6">
        <h1 class="navs_h1">${event.name !''}</h1>
        <p class="navs6_p1">${event.profile !''}</p>
        <div class="cpxq_one">
            <p class="cpxq_one_p1">￥<i>${event.entryfeeDollar !'免费'}</i></p>
            <p class="cpxq_one_p2 cpxq_one_p2s"><i>限额:${event.quota !'0'}人</i>
            <span>已报名：${event.enrollNum!'0'}</span></p>
        </div>

    </section>
    <section class="navs13">
        <h4 class="nav_h4">评价</h4>
        <ul class="navs13_ul">
        	<#if event.commentCount?? && (event.commentCount != 0)>
        	<#list event.commentList as comment>
            <li class="navs13_li">
                <div class="navs13_left">
                	<#if comment.member??>
                    <img src="${comment.member.memberAvatar !''}" title="" alt="">
                    <#else>
                    <img src="/images/cpxq_tx01.jpg" title="" alt="">
                    </#if>
                </div>
                <div class="navs13_right">
                    <p class="navs13_right_p1">
                    	<#if comment?? && comment.member ?? && comment.member.memberMobile??>
                    	${comment.member.memberMobile?substring(0,2)}****${comment.member.memberMobile?substring(7,10)}
                    	<#elseif comment?? && comment.member ??>
                    	${comment.member.memberName !''}
                    	<#else>
                    	匿名用户
                    	</#if>
                    	<span>${comment.strCreateTime !''}</span>
                    </p>
                    <p class="navs13_right_p2">
                    	<#if comment.grade?? && comment.grade == 0>
                    	<i></i><i></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 1>
                    	<i class="navs13_i"></i><i></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 2>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 4>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i>
                    	<#elseif comment.grade?? && comment.grade == 5>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
                    	<#else>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
                    	</#if>
                    </p>
                </div>
                <p class="navs13_p1">${comment.content !''}</p>
                <#if comment?? && comment?size gt 0>
                <ul>
                	<#list comment.imageList as image>
                    <li><a><img src="${image.imgPath !''}" alt="" title=""></a></li>
                    </#list>
                </ul>
                </#if>
            </li>
            </#list>
            <#else>
            	<li class="navs13_li">该活动没有评论</li>
            </#if>
        </ul>
        <a href="/event/commentlist.htm?eventId=${event.id !''}" class="navs9_p1"><p>查看全部${event.commentCount !'0'}条评价</p><span></span></a>
    </section>
    <section class="navs12 navs12s" id="navs12s">
        <h4 class="nav_h4">详细信息</h4>
        <p class="navs12_p1">活动承办方: </p>
        <p class="navs12_p2">${event.store.storeName !''}</p>
        <p class="navs12_p1">活动时间: </p>
        <p class="navs12_p2">${event.strEventTime !''}</p>
        <p class="navs12_p1">活动发起人:</p>
        <p class="navs12_p2">${event.organizerBy !'平台'}</p>
        <p class="navs12_p1">活动介绍: </p>
        <p class="navs12_p2">${event.introduce !''}</p>
        <p class="navs12_p1">活动主要安排: </p>
        <p class="navs12_p2"><#if event.text??>${event.text.text !''}</#if></p>
    </section>
    <p class="navs8_p2">报名费￥<i>${event.entryfeeDollar !'免费'}</i>
    	<#if event.strEventTime?date gt .now?date>
    	
    	<#if LOGIN_SIGN.vipRankId?? && (LOGIN_SIGN.vipRankId>=0) >
		   	 	<a href="/event/eventOrder/${event.id}.htm">马上报名</a>
		   	 	<#else>
		   	 	<a href="/userCenter.htm">马上报名</a>
		   	 	</#if>
    	
    	<#else>
    	<a href="javascript:;" style="background:#6F5F5F;">该活动已过期</a>
    	</#if>
	</p>
    <#if isKnow?? &&(isKnow == false)>
    <section class="fxtk">
        <div class="fxtks">
            <img src="/images/fxtk.png" alt="" title="">
            <div class="fxtks_d">
                <a href="">
                <p class="fxtks_p1">分享有礼</p>
                <p class="fxtks_p2">点击右上方分享入口，将活动或商品到微信朋友圈、朋友、手机QQ</p>
                <p class="fxtks_p3">即可获得20元红包！</p>
                </a>
            </div>
            <a class="fxtks_a" href="javascript:;" id="iknow">我知道了！</a>
        </div>
    </section>
    </#if>
</article>

</body>
</html>
