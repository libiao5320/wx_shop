<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
	<link href="/css/swiper.min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/swiper.min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
	<script type="text/javascript" src="/js/lbs/lbs.js"></script>
	<script type="text/javascript" src="/js/js.js"></script>
	<script type="text/javascript" src="/js/local.js"></script>
</head>
<style>
	
</style>
<body>
<header>
    <div id="allmap"></div>
    <section class="head">
        <form action="/indexSearch.htm" method="post">
        <div class="top_left">
            <a class="top_cs" id="demo" >长沙<span></span></a>
        </div>
        <div class="top_input">
            <input class="cars" name="map[goodsName]" id="seachInput" list="cars" placeholder="输入产品名称、商家、分类" />
        </div>
        <div class="top_right">
            <a class="top_sx" href=""></a>
        </div>
        </form>
    </section>
    <div class="index_ceng"></div>
    <div class="citys">
    	<div class="head_fl">
			<div class="top_input">
				<a class="ss1" href="javascript:;"></a>
				<input class="cars" list="cars" placeholder="输入产品名称、商家、分类" />
				<datalist id="cars">
					<option value="123">
				</datalist>
			</div>
		</div>
    	<div id="zwtz">
	<div class="cityleft">
		<div>
			<!--定位城市-->
			<div class="citylbs">
				定位城市
			</div>
			<div class="nowcity">长沙市</div>
		</div>
		<div>
			<!--商圈-->
			<div class="areas">
				商圈
			</div>
		</div>
		<div style="clear: left;" id="hotCity">
			<!--热门城市-->
			<div class="citylbs" id="hotcitylbs">
				热门城市
			</div>
		</div>
		<div style="clear: left;" id="allCity">
			<!--城市列表-->
		</div>
	</div>
	<div class="listHref">
		<!--字母列表-->
	</div>
</div>
    </div>
</header>
<article>
    <section class="banners">
         <div class="swiper-container">
	          <div class="swiper-wrapper">
				<#if result?? >
					<#if  result.bannerlist?? && result.bannerlist?size gt 0 >
						<#list  result.bannerlist as c>
							<#if c.redpacketsId??><!--说明是红包连接-->
								<div class="swiper-slide">
									<a href="javascript:void(0);" redpacketsId="${c.redpacketsId !''}" onclick="receive($(this))">
										<#if PUBLIC_IMG_URL?? && c.img??>
	                    					<img src="${PUBLIC_IMG_URL!''}${c.img !''}" title="" onerror="onerror=null;src='/images/banner${c_index}.jpg'"/>
	                    				<#else>
			                    			<img src="/images/banner${c_index}.jpg" alt="" title="" />
			                    		</#if>
		                    		</a>
								</div>
					        <#else>
					        	<div class="swiper-slide">
					        		<a href="${c.url !'#'}">
						        		<#if (PUBLIC_IMG_URL??) && (c.img??)>
			                    			<img src="${PUBLIC_IMG_URL!''}${c.img !''}" title="" onerror="onerror=null;src='/images/banner${c_index}.jpg'"/>
			                    		<#else>
			                    			<img src="/images/banner${c_index}.jpg" alt="" title="" />
			                    		</#if>
		                    		 </a>
					        	</div>
					        </#if>
					    </#list>
					</#if>
				</#if>
				
	          </div>
	          <div class="swiper-pagination"></div>
    </div>
	
    </section>
    <section class="navs">
        <ul>
        <#if result?? >
            <#if  result.classList?? && result.classList?size gt 0 >
        <#list  result.classList as c>
            <li><a href="/queryGoodByClass/${c.classId }.htm"><img src="${PUBLIC_IMG_URL!''}${c.classImg !''}" alt="" title="" onerror="onerror=null;src='/images/nav_srfw.png'"><p>${c.className !''}</p> </a></li>
        </#list>
        	<!-- 私人服务  -->
        	<li><a href="/techie/list.htm"><img src="/images/nav_srfw.png" alt="" title=""><p>私人服务</p> </a></li>
            </#if>
        </#if>
        </ul>
    </section>
    <section class="navs1">
        <h1 class="nav_h1">私人健康师排行榜</h1>
        <ul class="techie">

     <#if result?? >
            <#if  result.techieList?? && result.techieList?size gt 0 >
            <li><a href="/techie/detail/${result.techieList[0].techieId}.htm"><p class="navs1_p"><img src="${TECHIE_IMG_URL!''}${ result.techieList[0].img}" alt="" title="" onerror="onerror=null;src='/images/jks1.jpg'"></p><p class="navs1_p1">${ result.techieList[0].trueName}</p><p class="navs1_p1 navs1_p1s">${ result.techieList[0].specialty !''}</p><p class="navs1_p2">打赏:${ result.techieList[0].dsTotalNum}</span> </p><span class="nav_ph nav_gj">冠军</span></a></li>
            <li><a href="/techie/detail/${result.techieList[1].techieId}.htm"><p class="navs1_p"><img src="${TECHIE_IMG_URL!''}${ result.techieList[1].img}" alt="" title="" onerror="onerror=null;src='/images/jks2.jpg'"></p><p class="navs1_p1">${ result.techieList[1].trueName}</p><p class="navs1_p1 navs1_p1s">${ result.techieList[1].specialty !''}</p><p class="navs1_p2">打赏:${ result.techieList[1].dsTotalNum}</span> </p><span class="nav_ph nav_yj">亚军</span></a></li>
            <li><a href="/techie/detail/${result.techieList[2].techieId}.htm"><p class="navs1_p"><img src="${TECHIE_IMG_URL!''}${ result.techieList[2].img}" alt="" title="" onerror="onerror=null;src='/images/jks3.jpg'"></p><p class="navs1_p1">${ result.techieList[2].trueName}</p><p class="navs1_p1 navs1_p1s">${ result.techieList[2].specialty !''}</p><p class="navs1_p2">打赏:${ result.techieList[2].dsTotalNum}</span> </p><span class="nav_ph nav_jj">季军</span></a></li>
            </#if>
     </#if>
        </ul>
    </section >
    <section class="navs2">
        <a href="/event/list.htm" class="">
        <#if result?? && result.enter??>
        	<img src="${result.enter.imgPath !'/images/img03.jpg'}" alt="" title="" onerror="onerror=null;src='/images/img03.jpg'">
        <#else>
        	<img src="/images/img03.jpg" alt="" title="">
        </#if>
        </a>
    </section>
    <section class="navs3">
        <h2 class="nav_h2"><span></span>发现精彩</h2>
        <div class="navs3_left">
            <p><img src="/images/img01.jpg" title="" alt=""></p>
        </div>
        <div class="navs3_mid">
            <p class="navs3_p1">耀 · 商家排行</p>
            <p class="navs3_p2">周边最热最火商家</p>
        </div>
        <div class="navs3_right">
            <a href="/store/list.htm">去看看</a>
        </div>
    </section>
    <section class="navs4" style="margin-bottom: 50px;">
        <h2 class="nav_h2"><span></span>猜你喜欢</h2>
        <ul id="guesslist">
        </ul>
        <p class="navs4_bts"><i></i>正在加载...</p>
        <a href="/goods/list.htm"  class="navs4_bt">查看全部VIP产品</a>
    </section>

    <footer>
        <ul class="footer_ul">
 	    <li class="footer_li" id="Id1"><a href="/index.htm" onclick="dianji1();"><i></i><span>首页</span></a></li>
	    <li class="" id="Id2"><a href="/goods/class.htm" onclick="dianji2();"><i></i><span>分类</span></a></li>
	    <li class="" id="Id3"><a href="/techie/list.htm" onclick="dianji3();"><i></i><span>健康师</span></a></li>
	    <li class="" id="Id4"><a href="/userCenter.htm" onclick="dianji4();"><i></i><span>个人中心</span></a></li>
        </ul>
    </footer>
    <section class="hb">
        <div class="hb_ceng"></div>
        <div class="hb_d">
        	<span class="xz_gb"></span>
            <img src="/images/hb01.png" alt="" title="">
            <p class="hb_d_p1">融耀上线千万红包派发</p>
            <p class="hb_d_p2">给你发了一个红包</p>
            <a class="hb_d_a1" onclick="receiveRed($(this))">拆红包</a>
        </div>
        <div class="hb_ds">
            <img src="/images/hb02.png" alt="" title="">
            <p class="hb_ds_p1">恭喜你获得</p>
            <p class="hb_ds_p2"><i>8.80</i>元</p>
            <p class="hb_ds_p3">红包已放入账户</p>
            <a href="/redpackets/myredpackets.htm" class="hb_ds_a2">查看领取的红包</a>
        </div>
    </section>
</article>


<script>
	$(function() {
		getAll();
	});
</script>
<script  type="text/javascript" defer="defer">
	function dianji1(){
		document.getElementById("Id1").className="footer_li"
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
    //验证红包是否有效
    function receive(data){
    	$('.hb_ceng').show();
        var redpacketsId = data.attr("redpacketsId");

        if(redpacketsId == '' || redpacketsId == null ){
            alert("ID无效");
            return;
        }
        
        $.post("/redpackets/checkRed.ajax",{'id':redpacketsId},function(data){
            if(data.state){//红包可以正常领取
                    receiveAuth(redpacketsId);//检测用户是否有领取红包的权限
                }else{
                    $(".hb_d_p1").html(data.message);
                    $(".hb_d_p2").html("");
                    $(".hb_d_a1").html("查看我的红包");
                    $(".hb_d_a1").attr("onclick","redirect('/redpackets/myredpackets.htm')");
                    $('.hb_d').show();
                    $('.hb_ceng').show();
                }

        },"json").error(function(a, b,c){
        })
    }

    //领取权限
    function receiveAuth(id){
        $.ajax({
            url: '/redpackets/checkReceive.ajax' ,
            type: 'POST',
            data: {'id':id} ,
            dataType: 'json',
            success: function(json){
                if(json.state){//有权限领取红包
                    $(".hb_d_p1").html(json.param.name);
                    $(".hb_d_a1").attr("redpacketsId",id);
                    $('.hb_d').show();
                    $('.hb_ceng').show();
                }else{//领取过
                	if(json.message == 'login'){//未登录
						redirect("/userCenter.htm");
						return;
					}
                
                    $(".hb_ds_p1").html(json.message);
                    $(".hb_ds_p2 i").html(json.param.moneyDollar);
                    $('.hb_ds').show();
                }

            } ,
            error: function(){
            }
        });
    }

    //领取红包
    function receiveRed(data){
        $.ajax({
            url: '/redpackets/receive.ajax' ,
            type: 'POST',
            data: {'id':data.attr("redpacketsId")} ,
            dataType: 'json',
            success: function(json){
                if(json.state){//領取成功
                    $(".hb_ds_p1").html(json.param.name);
                    $(".hb_ds_p2 i").html(json.param.moneyDollar);
                    $('.hb_ds').show();
                }else{
                    $(".hb_d_p1").html(json.message);
                    $(".hb_d_p2").html("");
                    $(".hb_d_a1").html("查看我的红包");
                    $(".hb_d_a1").attr("onclick","redirect('/redpackets/myredpackets.htm')");
                    $('.hb_d').show();
                    $('.hb_ceng').show();
                }

            } ,
            error: function(){
            }
        });
    }


    function guessYouLove(x,y){

        $.ajax({
            url: '/guessYouLove.ajax' ,
            type: 'POST',
            data: {'map[x]':x,'map[y]':y} ,
            dataType: 'json',
            success: function(data){
				$(".navs4_bts").hide();
                var result = data.result;
                if( result  && result.length > 0)
                {
                    for( var i= 0 ; i < result.length ; i++)
                    {
                        var good = result[i];
                        var vipPrice = 0;

						//vip3为基础价格
                        switch (<#if LOGIN_SIGN??>${LOGIN_SIGN.vipRankId ! '3'}<#else>3</#if>)
                        {
                            case 0:
                                vipPrice = good["goodsVip0Price"];
                                break;
                            case 1:
                                vipPrice = good["goodsVip1Price"];
                                break;
                            case 2:
                                vipPrice = good["goodsVip2Price"];
                                break;
                            case 3:
                                vipPrice = good["goodsVip3Price"];
                                break;
                            case 4:
                                vipPrice = good["goodsVip4Price"];
                                break;
                        }
						var distance = good["distance"];
						if(distance != null && distance != 0 && distance != undefined){
							distance = (distance/1000).toFixed(2);
						}
                        var _html = '<li><a href="/goods/detail.htm?goodsId='+good["goodsId"]+'&distance='+good["distance"]+'"> <div class="navs4_left"><p><img src="${GOODS_IMG_URL !""}'+good["goodsImage"]+'" alt="" title=""></p> </div> <div class="navs4_right"> <p class="navs4_p1"><i>'+good["goodsName"]+'</i><span><'+distance+'km</span></p> <p class="navs4_p2">'+good["goodsIntroduce"]+'</p> <p class="navs4_p3">¥'+(good["goodsVip0Price"]/100)+'<i>¥'+(good["goodsVip3Price"]/100)+'</i><span>已抢购'+(good["goodsSalenum"]?good["goodsSalenum"]:0)+'</span></p> </div> </a> </li>';
                        $("#guesslist").append(_html);
                    }

                }

            } ,
            error: function(r,b,c){
            }
        });

    }

    (function(){
        $("#seachInput").keypress(function(event){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13'){
                $("form").submit();
            }
        });
        lbs(guessYouLove);
    })();

</script>
<script> 
		var mySwiper = new Swiper('.swiper-container',{
		pagination: '.swiper-pagination',
		loop : true,
		loopAdditionalSlides : 1,
		paginationClickable: true,

		 centeredSlides: true,
        autoplay: 5500,
        autoplayDisableOnInteraction: false
		})
</script>

</body>
</html>
