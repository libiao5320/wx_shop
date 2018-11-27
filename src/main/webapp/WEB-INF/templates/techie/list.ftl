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
<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=E4a51c429ff711c1072eb83bf39f3096&v=1.0"></script>
</head>
<body>
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt">私人服务列表</p>
            <div class="top_right top_rights">
                <a class="top_sx" href=""></a>
            </div>
        </section>

    </header>
    <article>
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
                                    <li class="cplb_lm_lis" noopsyche="dsTotalNum"><a href="javascript:;">按打赏从高到低</a></li>
                                    <!-- <li class="cplb_lm_lis" noopsyche="goodcount"><a href="javascript:;">按价格从低到高</a></li> -->
                                    <li class="cplb_lm_lis" noopsyche="gAge"><a href="javascript:;">按教龄从高到低</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        	<ul id="js_goodList" class="srjk_fwlb">
        		
            </ul>
            <div class="cplb_lm cplb_lm1">
            </div>
        </section>
    
        <div class="nomore" style="display:none;height: 50px;">没有更多了...</div>
        <p class="nomore" style="height: 50px;"></p>
        <footer>
            <ul class="footer_ul">
                <li class="" id="Id1"><a href="javascript:;" onclick="dianji1();"><i></i><span>首页</span></a></li>
                <li class="" id="Id2"><a href="/goods/class.htm" onclick="dianji2();"><i></i><span>分类</span></a></li>
                <li class="footer_li" id="Id3"><a href="/techie/list.htm" onclick="dianji3();"><i></i><span>健康师</span></a></li>
                <li class="" id="Id4"><a href="/userCenter.htm" onclick="dianji4();"><i></i><span>个人中心</span></a></li>
            </ul>
        </footer>
    </article>
    <div class="ceng"></div>
    <div class="ceng1"></div>
	<input type="hidden" id="classId" value="" />
	
   	<script type="text/javascript" src="/js/js.js"></script>
   	<script type="text/javascript" src="/js/techie/techie.js"></script>
   	<script type="text/javascript" src="/js/lbs/lbs.js"></script>
	<script type="text/javascript" defer="defer">
	function dianji1(){
		window.location.href="/index.htm";
		document.getElementById("Id1").className="footer_li";
	}
	function dianji2(){
		document.getElementById("Id2").className="footer_li"
	}
	function dianji3(){
		document.getElementById("Id3").className="footer_li"
	}
	function dianji4(){
		document.getElementById("Id4").className="footer_li"
	}
		var TECHIE_IMG_URL = '${TECHIE_IMG_URL!''}';
		var scrollSign = false;
        $(document).ready(function (){
			
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
