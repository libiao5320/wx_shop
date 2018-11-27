/*
 * 微信获取地址
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 * 载入了jshttp:
 * <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
 * <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
 */


function initWXConfig(appId, timestamp, nonceStr, signature) {
	var  x="0123456789qwertyuioplkjhgfdsazxcvbnm";
	var  tmp="";
	var timestamp = new Date().getTime();
	for(var  i=0;i<10;i++)  {
	tmp  +=  x.charAt(Math.ceil(Math.random()*100000000)%x.length);
	}
	
	
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : appId, // 必填，公众号的唯一标识
		timestamp : Math.floor(new Date().getTime() / 1000), // 必填，生成签名的时间戳
		nonceStr : tmp, // 必填，生成签名的随机串
		signature : signature, // 必填，签名，见附录1
		jsApiList : ['onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone', 'chooseImage', 'previewImage', 'uploadImage', 'startRecord', 'stopRecord', 'onVoiceRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice', 'onVoicePlayEnd', 'uploadVoice', 'hideOptionMenu', 'showOptionMenu', 'hideMenuItems', 'showMenuItems', 'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'closeWindow'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	wx.ready(function() {
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		        alert(latitude'+'longitude);   //输出经纬度地址
		    }
		});
	});
}






