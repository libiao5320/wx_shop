<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">个人信息</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>
    <section class="navs20">
        <div class="navs20_d1">
            <span class="navs20_d1_s1">头像</span>
            <span class="navs20_d1_s2"><img src="${result.memberInfo.memberAvatar}" alt="" title=""> </span>
        </div>
        <div class="navs20_d1">
            <span class="navs20_d1_s1">昵称</span>
            <span class="navs20_d1_s3">${result.memberInfo.memberName} </span>
        </div>
        <div class="navs20_d1">
        	<a href="/user_share.htm?img=${result.memberInfo.memberAvatar}&codes=<#if result.extend??>${result.extend.recommendCode !''}<#else>D${result.memberInfo.memberId !''}</#if>">
            <span class="navs20_d1_s1">我的二维码分享名片</span>
            <span class="navs20_d1_s5"></span>
            <span class="navs20_d1_s4"></span>
            </a>
        </div>
        <div class="navs20_d1">
            <span class="navs20_d1_s1">我的邀请码</span>
            <span class="navs20_d1_s3"><#if result.extend??>${result.extend.recommendCode !'-'} <#else>-</#if></span>
        </div>
    </section>
    <section class="navs21">
        <div class="navs20_d1">
            <span class="navs20_d1_s1">会员等级</span>
            <span class="navs20_d1_s5"></span>
            <a href="/vip/vip_index.htm" > <span class="navs20_d1_s6">去充值升级</span> </a>
            <#if result.memberInfo.dvip??>
                        	<#if result.memberInfo.dvip.greade == 0>
                        	<span class="vip">
                        	<#elseif result.memberInfo.dvip.greade == 1>
                        	<span class="vip vips">
                        	<#elseif result.memberInfo.dvip.greade == 2>
                        	<span class="vip vipss">
                        	<#else>
                        	<span class="vip">
                        	</#if>
                    ${result.memberInfo.dvip.greade}</span>
                        <#else >
                        <span class="vip">
                        0
                        </span>
                            </#if >
        </div>
        <div class="qysm">
            <p class="qysm_p1">权益说明：</p>
           <!-- <p class="qysm_p2">优惠折上折</p>
            <ul class="navs19_ul">
                <#if result??>
                     <#if result.vipList??&& result.vipList?size gt 0>
                        <#list  result.vipList as c>
                            <li>
                                <a href="">
                                    <span class="vip vips">${c.greade}</span>${c.remark}
                                </a>
                            </li>
                        </#list>
                    </#if>
                </#if>

            </ul>-->
            <ul class="grqy">
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx01.png" alt="" title=""></p>
	            			<p class="grqy_p2">通用储值优惠</p>
	            			<p class="grqy_p3">一卡通用 自由实惠</p>
	            		</div>
            		</a>
            	</li>
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx02.png" alt="" title=""></p>
	            			<p class="grqy_p2">私人健康服务</p>
	            			<p class="grqy_p3">随时随地 免费咨询</p>
	            		</div>
            		</a>
            	</li>
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx03.png" alt="" title=""></p>
	            			<p class="grqy_p2">品牌健康活动</p>
	            			<p class="grqy_p3">精英社群 健康领地</p>
	            		</div>
            		</a>
            	</li>
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx04.png" alt="" title=""></p>
	            			<p class="grqy_p2">线下服务优待</p>
	            			<p class="grqy_p3">商户会员 同等服务</p>
	            		</div>
            		</a>
            	</li>
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx05.png" alt="" title=""></p>
	            			<p class="grqy_p2">融耀达人</p>
	            			<p class="grqy_p3">会员大咖  回报更多</p>
	            		</div>
            		</a>
            	</li>
            	<li>
            		<a herf="">
	            		<div class="grqy_d">
	            			<p class="grqy_p1"><img src="../images/grxx06.png" alt="" title=""></p>
	            			<p class="grqy_p2">更多特权</p>
	            			<p class="grqy_p3">敬请期待</p>
	            		</div>
            		</a>
            	</li>
            </ul>
        </div>

    </section>

</article>

</body>
</html>
