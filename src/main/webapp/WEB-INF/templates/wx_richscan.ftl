<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" language="javascript" src="http://res.mail.qq.com/mmr/static/lib/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="http://res.mail.qq.com/mmr/static/lib/js/lazyloadv3.js"></script>
<script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">
function loadConfig(){
	var appid = $('#appid').val();
	var timestamp = $('#timestamp').val();
	var nonceStr = $('#nonceStr').val();
	var signature = $('#signature').val();
	var wxOpenid = $('#wxOpenid').val();
	 wx.config({
	      debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	      appId: appid, // 必填，公众号的唯一标识
	      timestamp: timestamp, // 必填，生成签名的时间戳
	      nonceStr: nonceStr, // 必填，生成签名的随机串
	      signature: signature,// 必填，签名，见附录1
	      jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	  });
	  wx.error(function(res){
	  	
	  });
	  wx.ready(function(){
		  	 wx.scanQRCode({
		  		needResult:1,
		  		desc:'scanQRCode desc',
				success:function (res) {
		          //扫码后获取结果参数:http://xxx.com/c/?6123，截取到url中的防伪码后，赋值给Input
		          var url = res.resultStr;
		          $.get(url+'?wxOpenid='+wxOpenid,function(json){
		          	if(json.state){
		          		alert(json.message);
		          		window.location.href="/ryvip/index.htm";
		          	}else{
		          		alert(json.message);
		          		window.location.replace(document.referrer);
		          	}
		          },'json');
			    }
	  		});
	  });
  }
  
 $(document).ready(function(){
  	loadConfig();
});
</script>
<input type="hidden" id="wxOpenid" value="${wxOpenid}">
<input type="hidden" id="appid" value="${appid}"/>
<input type="hidden" id="timestamp" value="${timestamp}"/>
<input type="hidden" id="nonceStr" value="${nonceStr}"/>
<input type="hidden" id="signature" value="${signature}"/>