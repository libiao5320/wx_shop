    /*
     * 百度接口当前地址获取，并提交给服务器
     * 作者：周降临
     * 邮箱：zhoutq@outlook.com
     * var cityName  城市地址
	 * 获取你浏览器的百度经纬度坐标表值
	 * var point 坐标对象  
	 * 使用point.point.lng和point.point.lat 使用两个坐标值
	 * 请包含如下地址
	 * <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
	 */



var lbs=function(callback){
  try{

	try {

		//$.getScript({
		//	url: "http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096",
		//	dataType: "script",
		//	success: function(){
		//
        //
		//	}
		//});


		var map = new BMap.Map("allmap");

		var point = null;// new BMap.Point(116.331398,39.897445);

		map.centerAndZoom(point, 12);

		var temp = null;

		var geolocation = new BMap.Geolocation();

		geolocation.getCurrentPosition(function (r) {


			if (this.getStatus() == BMAP_STATUS_SUCCESS) {
				var mk = new BMap.Marker(r.point);
				map.addOverlay(mk);
				map.panTo(r.point);

				setStorage(r.point.lng, r.point.lat);
				callback(r.point.lng, r.point.lat);
			}
			else {
				alert('failed' + this.getStatus());
			}
		}, {enableHighAccuracy: true});

	}catch(e)
	{


		callback(null,null);
	}
  }
  catch(e){
  }
}
/*
 * 浏览器原生navigator.geolocation获取地址函数
 */
var localLbs=function(){
	  if (navigator.geolocation){
			navigator.geolocation.watchPosition(successCallback);
	  }
	  else{
		  alert('SORRY');
	  }
	}
var localpoint=null;
/*
 * 打印本地地址回调函数
 */
var successCallback=function(points){
     var point = new BMap.Point(points.coords.latitude,points.coords.longitude);
     localpoint= localToLbs(point);
	 return localpoint;
}
/*
 * 原生经纬度地址转换为百度地址函数
 * @param points  原生地址百度地图BMap.Point对象数组
 * return data;
 */
var localToLbs=function(points){

    var x = points.lat;
    var y = points.lng;
    var ggPoint = new BMap.Point(x,y);

    //地图初始化
    var bm = new BMap.Map("allmap");
    bm.centerAndZoom(ggPoint, 15);
    bm.addControl(new BMap.NavigationControl());

    //添加gps marker和label
    var markergg = new BMap.Marker(ggPoint);
    bm.addOverlay(markergg); //添加GPS marker
    var labelgg = new BMap.Label("未转换的GPS坐标（错误）",{offset:new BMap.Size(20,-10)});
    markergg.setLabel(labelgg); //添加GPS label

    //坐标转换完之后的回调函数
    translateCallback = function (data){
      if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);
        bm.addOverlay(marker);
        var label = new BMap.Label("转换后的百度坐标（正确）",{offset:new BMap.Size(20,-10)});
        marker.setLabel(label); //添加百度label
        bm.setCenter(data.points[0]);
         //alert(data.points[0].lng+''+data.points[0].lat);
         setStorage(data.points[0].lng,data.points[0].lat);
        return data.points[0];
      }
    }
        var convertor = new BMap.Convertor();
        var pointArr = [];
        pointArr.push(ggPoint);
        convertor.translate(pointArr, 1, 5, translateCallback)
}
/*
 * 生成百度地址转换所需的BMap.Point对象数组
 * @param lng 精度
 * @param lat 维度
 * @param Pointarray  pointarray数组
 * return points对象
 */
var pointArray=function(lng,lat,Pointarray){
	 //var Pointarray = [];
	 var gPoint = new BMap.Point(lng,lat);
	 Pointarray.push(gPoint);
	 return Pointarray;
}
/*
 * 经纬度坐标本地持久化函数
 * @param lng  精度
 * @param lat  纬度
 */
var setStorage=function(lng,lat){
	window.localStorage.setItem('lng', lng);   
	window.localStorage.setItem('lat', lat); 
}
/*
 * 从至持久化对象里获得经纬度函数
 * return arr[lng,lat]
 */
var getStorage=function(){
	if(isStorage()){
		var arr=[];
		arr[0]=window.localStorage.getItem('lng');
		arr[1]=window.localStorage.getItem('lat');
		//alert(arr[0]);
		return arr;
	}
	else{
		localLbs();
		getStorage();
	}
}
/*
 * 是否存在写过的持久的经纬度
 * return 存在就返回正确
 */
var isStorage=function(){
	if(window.localStorage.getItem('lng')){ //存在就返回正确
		return true;
	}
	else {
		return false;
	}
}
/*
 * 删除本地持久化经纬度函数
 */
var delStorage=function(){
	window.localStorage.removeItem('lng');
	window.localStorage.removeItem('lat');
}

	