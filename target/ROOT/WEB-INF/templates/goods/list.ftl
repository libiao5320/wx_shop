<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>融耀健康</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=E4a51c429ff711c1072eb83bf39f3096&v=1.0"></script>
</head>
<body>
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt">商品分类</p>
            <div class="top_right top_rights">
                <a class="top_ss" href="javascript:;"></a>
                <a class="top_sx" href=""></a>
            </div>
        </section>


        <!-----------搜索中弹出层---------------->
        <section class="heads">
            <div class="top_input">
                <a id="searchBtn" class="ss1" href="javascript:;"></a>
                <input class="cars" list="cars" id="search" value="${search !''}" placeholder="输入产品名称" />
                <datalist id="cars">

                </datalist>
            </div>
            <div class="top_right">
                <a class="top_sx" href=""></a>
            </div>
            <div class="heads_ceng"></div>
        </section>
        <!-----------搜索中弹出层结束---------------->
    </header>
    <article>
		<input type="hidden" name="goods_url" value="${GOODS_IMG_URL !''}">
        <section class="navs4 navs4_cplb">
        	<div class="idxlist jslist">
                <div class="sortbar">
                    <ul class="sortrow clearfix sortrowgoods">
                        <li class="a"><a href="javascript:void(0);"><span class="nav_h3_d1">全部分类</span></a></li>
                        <li class="a"><a href="javascript:void(0);"><span class="nav_h3_d3 near">附近</span></a></li>
                        <li class="a"><a href="javascript:void(0);"><span class="nav_h3_d3 smartcls">智能分类</span></a></li>
                    </ul>
                    <div class="sort-con">
                        <div class="sortbg"></div>
                        <div class="sort-con-c area clearfix">
                            <ul class="area-l" >
		                        <li class="classify" classId = "0"><a href="javascript:;"><span></span>全部</a> </li>
                            </ul>
                        </div>
                        <div class="sort-con-c">
                            <div class="shrs">
                            	<ul class="shrsli">
									<li class="fujin" distanceS="0" distanceE="0"><a href="javascript:;">全部</a></li>
									<li class="fujin" distanceS="0" distanceE="500"><a href="javascript:;">500m</a></li>
									<li class="fujin" distanceS="0" distanceE="1000"><a href="javascript:;">1km</a></li>
									<li class="fujin" distanceS="0" distanceE="2000"><a href="javascript:;">2km</a></li>
									<li class="fujin" distanceS="0" distanceE="3000"><a href="javascript:;">3km</a></li>
                                    <input type="hidden" id="fujinvalue" />
				                </ul>
                            </div>
                        </div>
                        <div class="sort-con-c jxyt clearfix">
                            <div class="shrs">
                                <ul class="shrsli">
                                    <li class="cplb_lm_lis" noopsyche=""><a href="#">智能分类</a></li>
                                    <li class="cplb_lm_lis" noopsyche="goodcount"><a href="javascript:;">评价最高</a></li>
                                    <li class="cplb_lm_lis" noopsyche="salnum"><a href="javascript:;">销量最高</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        	<ul id="js_goodList">
        		<li class="classifyParent" classParentId = "" classParentName = ""><a href="javascript:;">全部</a> </li>
            </ul>
            <div class="cplb_lm cplb_lm1">
            </div>
            <div class="nomore">
		        <p class="zwsp">没有更多数据</p>
		        <p class="zwsps"></p>
	        </div>
        </section>
    </article>
    <div class="ceng"></div>
    <div class="ceng1"></div>
    <div class="ceng2"></div>
    <div id="allmap"></div>

    <input type="hidden" id="classId" value="${class !''}" />
    <input type="hidden" id="vipRank" value="${LOGIN_SIGN.vipRankId ! 0}" />

    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/js.js"></script>
    <script type="text/javascript" src="/js/goods/js/goods.js"></script>
    <script type="text/javascript" src="/js/lbs/lbs.js"></script>
    <script type="text/javascript" defer="defer" >
        var scrollSign = false;
        $(document).ready(function (){

			<#if groupbuy ?? >//说明是从分类跳转过来的
            var classId = ${groupbuy.classId !''};
            var className = '${groupbuy.className !''}';
            
            $("#classId").val(classId);
            $(".nav_h3_d1").parent().parent().addClass('current').siblings('li').removeClass('current');
            $(".nav_h3_d1").html(className);
            </#if>

            lbs(goodlist);

            $(window).scroll(scroll);  //下拉搜索

            initGoodGroupBuy();
            
            classClick();   //分类搜索
            
            searchBtn(); //搜索框搜索

            nearbyClick();  //附近搜索
            
            noopsyche();   //智能排序搜索
            
        });
    </script>
 </body>
</html>