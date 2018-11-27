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
        <p class="top_txt top_txts"><a href="/myCollect/1/1.htm" class="sc_fw" href="">服务（${result.countMap.goodcount! 0}）</a><a href="/myCollect/2/1.htm" class="sc_dp sc_bt" href="">商家（${result.countMap.storecount! 0}）</a></p>
        <div class="top_right top_rights">
            <a class="top_dlt" href="javascript:;"></a>
            <a class="top_sx" href="#"></a>
        </div>
    </section>
</header>
<article>
	<input type="hidden" name="img_url" value="${STORE_IMG_URL !''}">
    <#if  result.countMap.storecount?? &&  result.countMap.storecount gt 0>
    <section class="navs41">
    	<form id="form-data">
        <ul class="navs41_ul"  id="onloadAjAx">
        <#if result?? >
        <#if  result.collectList?? && result.collectList?size gt 0 >
        <#list  result.collectList as c>
            <li>
            	<div class="xzkk">
						<label class="xzkk_lb" ><i class="xzkk_i"></i><input type="checkbox"  value="${c.id !''}" name="xzkk_n" class="xzkk_c"/></label>
				</div>
				<#if  c.store.storeId??>
	                <div class="navs41_d">
	                	<a href="/store/detail.htm?id=${c.store.storeId}">
		                    <div class="navs41_left">
		                        <img src="${STORE_IMG_URL !''}${c.store.storeLabel !''}" alt="" title="">
		                    </div>
		                    <div class="navs41_right">
		                        <p class="navs4_p1"><i>${c.store.storeName !''}</i><span></span></p>
		                        <p class="navs41_right_p2"><a class="sc_right03">${c.store.fjLandmark !''}</a><a href="/store/detail.htm?id=${c.store.storeId}"> <#if c.store.storeDescribe?? && ( c.store.storeDescribe?length gt 20)>${c.store.storeDescribe?substring(0,19) !''}...<#else>${c.store.storeDescribe !''}</#if></a></p>
		                        <p class="navs41_right_p7 navs13_right_p2">
		                        	<#if c.store.storeServicecredit??>
		                        	<#if c.store.storeServicecredit gte 1 && c.store.storeServicecredit lt 2>
		                        		<i class="navs13_i"></i><i class=""></i><i class=""></i><i class=""></i><i class=""></i>
		                    		<#elseif c.store.storeServicecredit gte 2 && c.store.storeServicecredit lt 3>
		                        		<i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i><i class=""></i>
		                    		<#elseif c.store.storeServicecredit gte 3 && c.store.storeServicecredit lt 4>
		                        		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i>
		                    		<#elseif c.store.storeServicecredit gte 4 && c.store.storeServicecredit lt 5>
		                        		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i>
		                        	<#elseif c.store.storeServicecredit gte 5>
		                        		 <i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
		                        	</#if>
		                        	<#else>
		                        		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i>
		                        	</#if>
		                        	
		                       		<span class="sc_right02">${c.store.storeServicecredit !'3'}分</span>
		                        </p>
		                    </div>
	                    </a>
	                </div>
                </#if>
            </li>
        </#list>
         </#if>
        </#if>
        </ul>
        </form>
    </section>
    <#else>
    <section class="navs46">
        <p class="navs46_p1"><img title="" alt="" src="/images/zwsc.png"></p>
        <p class="navs46_p2">暂无收藏</p>
    </section>
    </#if>
</article>
<div class="xzkk_bt">
    	<label class="xzkk_bt_s"><i class="xzkk_i"></i><input type="checkbox" value="" name="" id="qxzl" class="qxzl"/>全选</label> <a class="xzkk_bt_a" href="javascript:;"  url="/collect/deteleCollect.ajax">删除</a>
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
				isAjax = collectAJax(page,'/myCollect/2.htm');
				page++;
			}
		}
	});
});
<script>
</body>
</html>
