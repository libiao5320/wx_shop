<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>融耀健康</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.jlds{ color:#666; font-size:14px; float:right; font-family:"Arial";}
</style>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-min.js"></script>
<script type="text/javascript" src="/js/js.js"></script>
<script type="text/javascript">

var isAjax = true;
var pageNo = 2;

$(function(){
	<!--防止缓存数据，将初始数据初始化-->
	
	$("input[name='classId']").val(null);
	$("input[name='distanceS']").val(0);
	$("input[name='distanceE']").val(0);

	var map = new BMap.Map("allmap");
	var point =null;// new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
    var temp=null;
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var mk = new BMap.Marker(r.point);
			map.addOverlay(mk);
			map.panTo(r.point);
			
			$("input[name='x']").val(r.point.lng);
			$("input[name='y']").val(r.point.lat);
			
			ajaxload(1,1);
		}
		else {
			ajaxload(1,1);
			alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true});

	//分类
	$('.classify').click(function(){
		$(".cplb_lm").hide();
		$(".ceng").hide();
		$(".navs4_bts").show();
		isAjax = true;//使分页可以使用
		$("input[name='classId']").val($(this).attr("classId"));//获取店铺分类ID
		
		if($("input[name='classId']").val() == 0){
			$(".nav_h3_d1").html("全部分类");//换掉显示的文本
		}else{
			$(".nav_h3_d1").html($(this).text());//换掉显示的文本
		}
		pageNo = 2;
		ajaxload(0,1);
	});
	
	//点击搜索按钮
	$('.ss1').click(function(){
		$(".cplb_lm").hide();
		$(".ceng").hide();
		$(".navs4_bts").show();
		
		pageNo = 2;
		isAjax = true;//使分页可以使用
		
		ajaxload(0,1);
		
	});
	
	//附近
	$('.fujin').click(function(){
		$(".cplb_lm").hide();
		$(".ceng").hide();
		$(".navs4_bts").show();
		$(".nav_h3_d3").html($(this).find("a").text());
		
		isAjax = true;//使分页可以使用
		$("input[name='distanceS']").val($(this).attr("distanceS"));
		$("input[name='distanceE']").val($(this).attr("distanceE"));
		
		pageNo = 2;
		ajaxload(0,1);
	});
	
	//搜索框失去焦点
	$('#search').blur(function(){
		$(".navs4_bts").show();
		isAjax = true;//使分页可以使用
		pageNo = 2;
		ajaxload(0,1);
	});
	
	
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		
		if(Boolean(isAjax)){
			if(top >= (doheight-win_height)){
				ajaxload(1,pageNo);
				pageNo++;
			}
		}
	});
});

function ajaxload(number,pageNo){
	
	var classId = $("input[name='classId']").val();
	var distanceS = $("input[name='distanceS']").val();
	var distanceE = $("input[name='distanceE']").val();
	
	var lbsX = $("input[name='x']").val();
	var lbsY = $("input[name='y']").val();
	
	var search = $("#search").val();
	if(search == '' || search == null || search == 'undefined'){
		search = '';
	}

	$.ajax({
		url: '/store/list.ajax' ,
		type: 'POST',
		data: {'map[pageNo]':pageNo,'map[classId]':classId,'map[distanceS]':distanceS,'map[distanceE]':distanceE,'map[search]':search,'map[x]':lbsX,'map[y]':lbsY} ,
		dataType: 'json',
		success: function(json){
			if(json.state){
				if(json.param == '' || json.param == null || json.param == 'undefined'){
					
					isAjax = false;//使分页不可以使用
					$(".navs4_bts").hide();
					$(".nomore").css("display","block");
					return;
					
				}else{
					storelist(json.param,number);
				}
			}else{
				isAjax = false;//使分页不可以使用
				if(number == 0){
					$("#store li").remove();
				}
				$(".navs4_bts").hide();
				$(".nomore").show();
			}
			
		} ,
		error: function(){
			if(number == 0){
				$("#store li").remove();
			}
			$(".nomore").show();	
			//alert("网络忙，请稍后重试！");
		}
	});
}

/**
number:0-用户点击了条件，1-分页，2-没有数据
**/
function storelist(data,number){
	$(".cplb_lm").hide();
	$(".ceng").hide();
	$(".navs4_bts").hide();
	
	if(number == 0){//非分页请求，
		$("#store li").remove();
	}
	var distance = 0;
	var str;
	if(data != 'undefined' && data.length > 0){
		for(var i=0;i < data.length ;i ++){
			
			$li = $("<li></li>");
			$a = $("<a href=\'/store/detail.htm?id="+data[i].storeId+"&distance="+data[i].distance+"\'></a>");
			
			$div1 = $("<div class='navs4_left'></div>");
			$p1 = $("<p><img src=\'${GIU !''}"+data[i].storeLabel+"\' alt=\'\' title=\'\'/></p>");
			
			$div1.append($p1);
			$a.append($div1);
			distance = data[i].distance*1 ;
			if(distance > 0 && distance <= 500){
				str = "< 500";
			}
			if(distance > 500 && distance <= 1000){
				str = "< 1000";
			}
			if(distance > 1000 && distance <= 2000){
				str = "< 2000";
			}
			if(distance > 2000 && distance <= 3000){
				str = "< 3000";
			}
			if(distance > 3000){
				str = "> 3000";
			}
			
			$div2 = $("<div class='navs4_right'></div>");
			$p2 = $("<p class='navs4_p1 navss11_p1'>"+data[i].storeName+"</p>");
			if(data[i].evaluationStoreProbability == null || data[i].evaluationStoreProbability == '' || data[i].evaluationStoreProbability == 'undefined'){
			$p3 = $("<p class='navss11_p2'>好评率: <i>0%</i><span class=\'jlds\'>"+str+"m</span></p>");
			}else{
			$p3 = $("<p class='navss11_p2'>好评率: <i>"+data[i].evaluationStoreProbability+"%</i><span class=\'jlds\'>"+str+"m</span></p>");
			}
			$p4 = $("<p class='navs4_p3 navss11_p3'></p>");
			
			if(data[i].groupbuy == null || data[i].groupbuy == '' || data[i].groupbuy == 'undefined'){
				$i = $("<i></i>");
			}else{
				$i = $("<i>"+data[i].groupbuy.className+"</i>");
			}
			if(data[i].fjLandmark == null || data[i].fjLandmark == '' || data[i].fjLandmark == 'undefined'){
				$span = $("<span></span>");
			}else{
				if(data[i].fjLandmark.length > 10)
					$span = $("<span>"+data[i].fjLandmark.substr(0,8)+"...</span>");
				else
					$span = $("<span>"+data[i].fjLandmark+"</span>");
			}
			$p4.append($i);
			$p4.append($span);
			
			$div2.append($p2);
			$div2.append($p3);
			$div2.append($p4);
			
			$a.append($div2);
			
			$li.append($a);
			
			$("#store").append($li);
			
		}
	}
}

</script>
</head>
<body>
   	<input type="hidden" value="true" name="isAjax">
   	
   	<input type="hidden" value="0" name="classId"><!--商家分类ID-->
   	<input type="hidden" value="0" name="distanceS"><!--附近的开始值-->
   	<input type="hidden" value="0" name="distanceE"><!--附近的结束值-->
   	
   	<input type="hidden" value="0" name="x"><!--x坐标-->
   	<input type="hidden" value="0" name="y"><!--y坐标-->
   	
	<div id="allmap"></div>
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt">商家排行榜</p>
            <div class="top_right top_rights">
                <a class="top_ss" href="javascript:;"></a>
                <a class="top_sx" href="/store/list.htm"></a>
            </div>
        </section>
        <!-----------搜索中弹出层---------------->
        <section class="heads">
            <div class="top_input">
                <a class="ss1" href="javascript:;"></a>
                <input class="cars" list="cars" id="search" placeholder="输入商家名称" />
                <datalist id="cars">
                    <option value="123">
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

        <section class="navs4 navs4_cplb">
            <div class="idxlist jslist">
                <div class="sortbar">
                    <ul class="sortrow clearfix">
                        <li class="a"><a href="javascript:void(0);"><span class="nav_h3_d1">全部分类</span></a></li>
                        <li class="a"><a href="javascript:void(0);"><span class="nav_h3_d3">附近</span></a></li>
                    </ul>
                    <div class="sort-con">
                        <div class="sortbg"></div>
                        <div class="sort-con-c area clearfix">
                            <ul class="area-l">
                            	<li class="classify" classId = "0"><a href="javascript:;" ><span></span>全部</a> </li>
		                    	<!-- 分类第一层 -->
		                    	<#if gclist?? && (gclist?size > 0)>
		                    	<#list gclist as gc>
		                    	<#if gc_index == 0>
		                        <li class="current"><a href="javascript:;"><span></span>${gc.className !''}</a> </li>
		                        <#else>
		                        <li><a href="javascript:;"><span></span>${gc.className !''}</a> </li>
		                        </#if>
		                        </#list>
		                        <#else>
		                        <li class="classify" classId = "0"><a href="javascript:;"><span></span>全部</a> </li>
		                        </#if>
		                        <!--
                                <li><a href="#">全部</a></li>
                                <li class="current"><a href="#">妇幼保健</a></li>
                                <li><a href="#">美容美体</a></li>
                                <li><a href="#">理疗养生</a></li>
-->
                            </ul>
                            <!-- 分类第二层 -->
                            <#if gclist?? && (gclist?size >= 0)>
			                <#list gclist as gc>
			                <#if (gc_index == 0) >
			                <#else>
			                </#if>
			                    <ul class="area-r">
		                    	<#list gc.groupbuyList as gb>
			                    	<#if (gb_index == 0)>
			                        <li class="classify" classId = "${gb.classId !''}"><a href="javascript:;">${gb.className !''}</a> </li>
			                        <#else>
			                        <li class='classify' classId = '${gb.classId !''}' ><a href="javascript:;" >${gb.className !''}</a> </li>
			                        </#if>
		                        </#list>
			                    </ul>
		                	</#list>
		                </#if>
                            
                        </div>
                        <div class="sort-con-c">
                            <div class="shrs">
                            	<ul class="shrsli">
									<li class="fujin" distanceS="0" distanceE="0"><a href="javascript:;">全部</a></li>
									<li class="fujin" distanceS="0" distanceE="500"><a href="javascript:;">500m</a></li>
									<li class="fujin" distanceS="0" distanceE="1000"><a href="javascript:;">1km</a></li>
									<li class="fujin" distanceS="0" distanceE="2000"><a href="javascript:;">2km</a></li>
									<li class="fujin" distanceS="0" distanceE="3000"><a href="javascript:;">3km</a></li>
				                </ul>
                            </div>
                        </div>
                        <div class="sort-con-c jxyt clearfix">
                            <div class="shrs">
                                <ul class="shrsli">
                                    <li><a href="#">智能分类</a></li>
                                    <li><a href="#">智能分类</a></li>
                                    <li><a href="#">智能分类</a></li>
                                    <li><a href="#">智能分类</a></li>
                                    <li><a href="#">智能分类</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
            <p class="navs4_bts"><i></i>正在加载...</p>
            <ul id="store">
            	<#if list??>
            	<#list list as store>
                <li>
                    <a href="/store/detail.htm?id=${store.storeId !''}">
                        <div class="navs4_left">
                            <p>{STORE_IMG_URL!''}${store.storeLabel !''}
                            <img src="${STORE_IMG_URL!''}${store.storeLabel !''}" alt="" title="">
                            </p>
                        </div>
                        <div class="navs4_right">
                            <p class="navs4_p1 navss11_p1">${store.storeName !''}</p>
                            <p class="navss11_p2">好评率: <i>${store.evaluationStoreProbability !'98'}%</i></p>
                            <p class="navs4_p3 navss11_p3"><#if store.groupbuy??><i>${store.groupbuy.className !''}</i></#if><span>${store.fjLandmark !''}</span></p>
                        </div>
                    </a>
                </li>
                </#list>
                </#if>
            </ul>
            
	        <div class="nomore">
		        <p class="zwsp">没有更多数据</p>
		        <p class="zwsps"></p>
	        </div>
        </section>
        
    </article>
    
    <div class="ceng"></div>
    <div class="ceng1"></div>
 </body>
</html>
