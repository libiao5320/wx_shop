<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" language="javascript" src="http://res.mail.qq.com/mmr/static/lib/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="http://res.mail.qq.com/mmr/static/lib/js/lazyloadv3.js"></script>
<script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
function onBridgeReady(){
       var appId = $("#appId").val();
       var timeStamp = $("#timeStamp").val();
       var nonceStr = $("#nonceStr").val();
       var packageValue = $("#packageValue").val();
       var finalsign = $("#finalsign").val();
       var signType = $("#signType").val();
       var orderId = $("#orderId").val();
       var money = $("#money").val();
       
 	   wx.config({
	      debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	      appId: appId, // 必填，公众号的唯一标识
	      timestamp: timeStamp, // 必填，生成签名的时间戳
	      nonceStr: nonceStr, // 必填，生成签名的随机串
	      signature: finalsign,// 必填，签名，见附录1
	      jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	  });
	  wx.ready(function(){
	  	wx.chooseWXPay({
		    timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
		    package: packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign:finalsign, // 支付签名
		    success: function (res) {
		        // 支付成功后的回调函数
		        window.location.href='/ryvip/index.htm';
		    },
		    cancel:function(res){
		    	 window.location.href='/ryvip/index.htm';
		    },
		    fail:function(res){
		    	 window.location.href='/ryvip/index.htm';
		    }
		});
	 });
}
</script>

<script>
$(document).ready(function(){
	onBridgeReady();
})
</script>



<input type="hidden" value="${appId}" id="appId">
<input type="hidden" value="${timeStamp}" id="timeStamp">
<input type="hidden" value="${nonceStr}" id="nonceStr">
<input type="hidden" value="${packageValue}" id="packageValue">
<input type="hidden" value="${signType}" id="signType">
<input type="hidden" value="${finalsign}" id="finalsign">
<input type="hidden" value="${orderId}" id="orderId">
<input type="hidden" value="${money}" id="money">
