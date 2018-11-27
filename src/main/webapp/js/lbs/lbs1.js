    /*
     * 百度接口当前地址获取，并提交给服务器
     * 作者：周降临
     * 邮箱：zhoutq@outlook.com
     * var cityName  城市地址
	 * 获取你浏览器的百度经纬度坐标表值
	 * var point 坐标对象  
	 * 使用point.point.lng和point.point.lat 使用两个坐标值
	 * 请包含如下地址
	 *  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=MiGYHilOOck4mf7S6OAbUxQz"></script>
	 */
var lbs=function(callback){
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
			callback(r.point.lng,r.point.lat);
		}
		else {
			alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true})
}

/*
 * 浏览器原生navigator.geolocation获取地址函数
 */
var localLbs=function(successCallback){
	navigator.geolocation.getCurrentPosition(successCallback)
}
/*
 * 打印本地地址回调函数
 */
var successCallback=function(position){
alert(	"本地纬度："+position.coords.latitude+"；经度："+position.coords.longitude);
}

/*
 * 原生经纬度地址转换为百度地址函数
 * @param points  原生地址百度地图BMap.Point对象数组
 * return data;
 */
var localToLbs=function(point){
	    //var points = [new BMap.Point(point.lng,point.lat)];
	    var bm = new BMap.Map("allmap");
	    bm.centerAndZoom(null, 15);

	    //坐标转换完之后的回调函数
	    translateCallback = function (data){
	      if(data.status === 0) {
	        for (var i = 0; i < data.points.length; i++) {
	            bm.addOverlay(new BMap.Marker(data.points[i]));
	            alert(data.points[i].lng+''+data.points[i].lat);
	            //bm.setCenter(data.points[i]);
	        }
	        return data;
	      }
	    }
	   
        var convertor = new BMap.Convertor();
        convertor.translate(points, 1, 5, translateCallback);
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

	