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
        <p class="top_txt">全部评价</p>
        <div class="top_right top_rights">
        </div>
    </section>
</header>
<article>
    <section class="navs13">

        <ul class="navs13_ul">
            <#if list?? && (list?size >0)>
        	<#list list as eva>
            <li class="navs13_li">
                <div class="navs13_left">
                	<#if eva.member??>
                    <img src="${eva.member.memberAvatar !''}" title="" alt="">
                    <#else>
                    <img src="/images/cpxq_tx01.jpg" title="" alt="">
                    </#if>
                </div>
                <div class="navs13_right">
                    <p class="navs13_right_p1">
                    	<#if eva?? && eva.member ?? && eva.member.memberMobile??>
                    	${eva.member.memberMobile?substring(0,2)}****${eva.member.memberMobile?substring(7,10)}
                    	<#elseif eva?? && eva.member ??>
                    	${eva.member.memberName !''}
                    	<#else>
                    	匿名用户
                    	</#if>
                    	<span>${eva.createTimeStr !''}</span>
                    </p>
                    <p class="navs13_right_p2">
                    	<#if eva.grade?? && eva.grade == 0>
                    	<i></i><i></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 1>
                    	<i class="navs13_i"></i><i></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 2>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
                    	<#elseif eva.grade?? && eva.grade == 4>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i>
                    	<#elseif eva.grade?? && eva.grade == 5>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
                    	<#else>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
                    	</#if>
                    </p>
                </div>
                <p class="navs13_p1">${eva.detail !''}</p>
                <#if eva?? && (eva?size gt 0) >
                <ul>
                	<#list eva.evaluateImageList as image>
                    <li><a><img src="${image.img !''}" alt="" title=""></a></li>
                    </#list>
                </ul>
                </#if>
            </li>
            </#list>
            </#if>
        </ul>

    </section>
</article>


</body>
</html>
