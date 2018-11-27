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
            <a class="left_nav" href="/index.htm"></a>
            <p class="top_txt">个人中心</p>
            <div class="top_right top_rights">

                <a class="top_sx" href=""></a>
            </div>
        </section>
    </header>
    <article>
        <section class="navs16">
            <div class="navs16_left">
                <a href="/userInfo.htm"><img src="${result.memberInfo.memberAvatar}" alt="" title=""></a>
            </div>
            
            <div class="navs16_right">
            <a href="/userInfo.htm" >
                <div class="navs16_right01">
                    <p class="navs16_right_t1">${result.memberInfo.memberName}
                    	
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
                        </p>

                    <#if result.nextVip?? >
                    <p class="navs16_right_t2">
                    距离 ${result.nextVip.name }会员只需充值满 ${result.nextVip.require}元</p>
                    <#else>
                    <p class="navs16_right_t2">
                    您已经是最高等级VIP</p>
                    </#if>
                </div>
                 </a>
                <div class="navs16_right02">

                    <a class="" href="/vip/vip_index.htm">去升级充值</a>

                </div>
            </div>
           
            <div class="navs16_btm">
                <a href="/ption/index.htm" class="navs16_btm_left">
                    <span>${result.consumptionCount}</span>
                    <p>示卡消费券</p>
                </a>
                <a href="/myCollect/1/1.htm" class="navs16_btm_right">
                    <span>${result.collectCount.total}</span>
                    <p>收藏</p>
                </a>
            </div>
        </section>
        <section class="navs17">
            <a href= "/myOrder/0.htm" class="navs17_p1"><i></i>全部订单 <span>${result.differentTypeCountMap.total}</span></a>
            <ul class="navs17_ul">
                <li>
                    <a href="/myOrder/2.htm">
                    	<span>
                    	<#if result.differentTypeCountMap.unspend?? && (result.differentTypeCountMap.unspend gt 0) >
                        <i>${result.differentTypeCountMap.unspend}</i>
                        </#if>
                        </span>
                        <p>未消费</p>
                    </a>
                </li>
                <li>
                    <a href="/myOrder/1.htm">
                        <span>
                        <#if result.differentTypeCountMap.unpay?? && (result.differentTypeCountMap.unpay gt 0)>
                        <i>${result.differentTypeCountMap.unpay}</i>
                        </#if>
                        </span>
                        <p>未付款</p>
                    </a>
                </li>
                <li>
                    <a href="/myOrder/4.htm">
                        <span>
                        <#if result.differentTypeCountMap.comment?? && (result.differentTypeCountMap.comment gt 0)>
                        <i>${result.differentTypeCountMap.comment}</i>
                        </#if>
                        </span>
                        <p>待评价</p>
                    </a>
                </li>
                <li>
                    <a href="/myOrder/7.htm">
                        <span>
                        <#if result.differentTypeCountMap.recived?? && (result.differentTypeCountMap.recived gt 0)>
                        <i>${result.differentTypeCountMap.recived}</i>
                        </#if>
                        </span>
                        <p>退款</p>
                    </a>
                </li>

            </ul>
        </section>
        <section class="navs18" style="margin-bottom: 50px; ">
            <ul class="navs18_ul">
                <li><a  class="navs17_p1" href="/ryvip/index.htm"><i></i>融耀VIP卡 <span>账户余额:￥${result.memberInfo.availablePredeposit/100+result.memberInfo.freezePredeposit/100}</span></a></li>
                <li><a href="/redpackets/myredpackets.htm" class="navs17_p1"><i></i>我的红包</a></li>
                <li><a  class="navs17_p1"><i></i>我的健康档案</a></li>
            </ul>

        </section>

        <footer>
            <ul class="footer_ul">
                <li class="" id="Id1"><a href="/index.htm" onclick="dianji1();"><i></i></a><span>首页</span></li>
                <li class="" id="Id2"><a href="/goods/class.htm" onclick="dianji2();"><i></i></a><span>分类</span></li>
                <li class="" id="Id3"><a href="/techie/list.htm" onclick="dianji3();"><i></i></a><span>健康师</span></li>
                <li class="footer_li" id="Id4"><a href="/userCenter.htm" onclick="dianji4();"><i></i></a><span>个人中心</span></li>
            </ul>
        </footer>
    </article>
<script defer="true">
	function dianji1(){
		document.getElementById("Id1").className="footer_li";
		return;
	}
	function dianji2(){
		document.getElementById("Id2").className="footer_li";
		return;
	}
	function dianji3(){
		document.getElementById("Id3").className="footer_li";
		return;
	}
	function dianji4(){
		document.getElementById("Id4").className="footer_li";
		return;
	}
</script>
 </body>
</html>
