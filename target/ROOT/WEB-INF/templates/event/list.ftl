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
	<style>
		.navs23_ul li{position: relative; overflow: hidden;height: 140px;}
		.xxg_hdsj{position:absolute;z-index: 99; line-height:30px; height:30px; color:#FFF;background:rgba(0,0,0,0.5);left:0;right:0;bottom:0; padding:0 10px; text-align:right;font-size:15px;}
		.fgc{background:rgba(63,63,63,0.8);position:absolute;left:0;right:0;top:0;bottom:0;}
		.navs23_ul .lvj{
	-webkit-filter: grayscale(100%);
    -moz-filter: grayscale(100%);
    -ms-filter: grayscale(100%);
    -o-filter: grayscale(100%);
    filter: grayscale(100%);
    filter: gray}
    .lvj a{display:block;position: relative;z-index:99;}
    .navs23_p1{ position: absolute;left: 10px;top: 50%;margin-top: -40px; color:#FFF;font-size: 16px;width: 90%;overflow: hidden;line-height: 24px;
height: 24px;white-space: nowrap; text-overflow: ellipsis;z-index: 99;}
    .navs23_p2{ position: absolute;left: 10px;top: 50%;margin-top: -10px;color:#FFF;font-weight: bold;font-size: 22px;width: 90%;overflow: hidden;white-space: nowrap; text-overflow: ellipsis;z-index: 99;}
    #event li:nth-child(even) .navs23_p1{width: 50%;margin-left:-10px;text-align:right;top: inherit; bottom:40px;}
    #event li:nth-child(even) .navs23_p2{left:inherit;right:10px;width: 50%; margin-right:-15px;text-align:left;top: inherit;bottom:40px;}
	.navs23_p3{display:none;}
	.lvj .navs23_p3{display:block;z-index:100;position: absolute;left:0;top:0;bottom:0;right:0; background:rgba(0,0,0,0.5);}
	</style>
</head>
<body>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt top_txts">报名吧</p>
        <div class="top_right top_rights">
            <!--<a class="top_sc" href=""></a>-->
            <!--<a class="top_fx" href=""></a>-->
        </div>
    </section>
</header>
<article id="navs23">
    <section class="navs23">
        <nav>
            <ul class="navs23_ul" id="event">
            	<#if list??>
            	<#list list as event>
            	<#if event.strEventTime?date gt .now?date>
            	<li>
            	<#else>
                <li class="lvj">
                
                </#if>
                	<a href="/event/detail.htm?id=${event.id !''}"><img src="${GOODS_IMG_URL!''}${event.img !''}" alt="" title="">
                	<p class="navs23_p1">${event.name !''}</p>
                	<p class="navs23_p2">火热报名啦</p>
                	<p class="navs23_p3"></p>
                	<p class="xxg_hdsj">
                	
                	<#if event.strEventTime?date gt .now?date>
                	活动时间${event.strEventTime !''}
                	<#else>
                	该活动已过期
                	</#if>
                	</p>
                	</a>
                </li>
                </#list>
                </#if>
            </ul>
        </nav>
    </section>
    <div class="nomore" style="display:none;">没有更多了...</div>
</article>
<script type="text/javascript">
var pageNo = 2;
var isAjax = true;
$(function(){
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		if(Boolean(isAjax)){
			if(top >= (doheight-win_height)){
				ajaxLoad(pageNo);
				pageNo++;
			}
		}
	});
});

var ajaxLoad = function(pageNo){

	var fwId = $("input[name='wfId']").val();

	if(fwId == '' || fwId == null || fwId == 'undefined'){
		fwId = null;
	}
	
	var zcId = $("input[name='zcId']").val();

	if(zcId == '' || zcId == null || zcId == 'undefined'){
		zcId = null;
	}
	
	$.ajax({
		url: '/event/list.ajax' ,
		type: 'POST',
		data: {'map[pageNo]':pageNo} ,
		dataType: 'json',
		success: function(json){
			if(json.state){
				if(json.param == '' || json.param == null || json.param == 'undefined'){
					eventlist(null,0);
					isAjax = false;
				}else{
					eventlist(json.param,1);
				}
			}else{
				alert(json.message);
			}
		} ,
		error: function(){
			alert("网络忙，请稍后重试！");
		}
	});
}

/**
flag 0-没有数据了，1-数据正常
**/
function eventlist(data,flag){

	if(flag == 0){
		$(".nomore").css("display","block");
		return;
	}
	
	if(data != 'undefined' && data.length > 0){
	
		var myDate = new Date().Format("yyyy-MM-dd");
		var mytime = myDate.toLocaleString();     //获取当前时间
		//alert(mytime);
		
		for(var i=0;i < data.length ;i ++){
			var d1 = new Date(Date.parse(data[i].strEventTime)).Format("yyyy-MM-dd");
			if(d1 >= mytime){//未过期
				$li = $("<li><a href=\'/event/detail.htm?id="+data[i].id+"\'><img src=\'"+data[i].img+"\' alt=\'\' title=\'\'></a><p class=\'xxg_hdsj\'>活动时间"+data[i].strEventTime+"</p></li>");
			}else{
				$li = $("<li class=\'lvj\'><a href=\'/event/detail.htm?id="+data[i].id+"\'><img src=\'"+data[i].img+"\' alt=\'\' title=\'\'></a><p class=\'xxg_hdsj\'>该活动已过期</p></li>");
			}
			
			$("#event").append($li);
		}
		
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
</script>

</body>
</html>
