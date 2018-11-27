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
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#golist {display: none;}
		@media (max-device-width: 780px){#golist{display: block !important;}}
		.maps_d{ overflow: hidden; margin-bottom: 10px;}
		.maps_d1{padding: 10px;}
		.maps_h1{ font-size: 20px; color: #000000; margin-bottom: 5px;}
		.maps_p1{ font-size: 15px; color: #666666;}
		.maps_h1 span{ font-size:14px; color:#666; float:right;}
		article{position: fixed;left:0;right:0;bottom:1px;}
	</style>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
       <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">商家地图</p>
    </section>
</header>
<div id="allmap"></div>
<article>
    <section class="maps">
    	<div class="maps_d"><div id="allmap"></div></div>
        <div class="maps_d1">
            <h1 class="maps_h1">${store.storeName !''}<span><#if distance??>${distance !''}km</#if></span></h1>
            <p class="maps_p1">${store.storeAddress !''}</p>
        </div>
    </section>
</article>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(${store.lbsX !''},${store.lbsY !''});
	map.centerAndZoom(point, 15);
	map.addControl(new BMap.ZoomControl());       

	var icon = new BMap.Icon("/images/place.png", new BMap.Size(35,35));
	var marker = new BMap.Marker(point,{icon:icon});  //创建标注
	map.addOverlay(marker);    // 将标注添加到地图中
	var opts = {
		width : 200,    // 信息窗口宽度
		height: 60,     // 信息窗口高度
		title : "${store.storeName !''}", // 信息窗口标题
		enableAutoPan : true //自动平移
	}
	//var infoWindow = new BMap.InfoWindow("地址：${store.storeAddress !''}", opts);  // 创建信息窗口对象
	         
	//map.openInfoWindow(infoWindow,point); //开启信息窗口
	
	
</script>

</body>
</html>
