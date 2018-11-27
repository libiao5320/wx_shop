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
      
   WeixinJSBridge.invoke(
       'getBrandWCPayRequest', {
           "appId" : appId,     //公众号名称，由商户传入     
           "timeStamp" :timeStamp,         //时间戳，自1970年以来的秒数     
           "nonceStr" : nonceStr, //随机串     
           "package" : packageValue,     
           "signType" :"MD5",         //微信签名方式:     
           "paySign" : finalsign //微信签名 
       },
       function(res){     
	            if(res.err_msg == "get_brand_wcpay_request:ok"){  
	               window.location.href='/ryvip/index.htm';
	            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
	               alert("用户取消支付!");
	               window.location.href='/ryvip/index.htm';  
	            }else{  
	               alert("支付失败!");
	               window.location.href='/ryvip/index.htm';
	            } 
       }
   ); 

}

        function callpay()
        {
            if (typeof WeixinJSBridge == "undefined"){
                if( document.addEventListener ){
                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                }else if (document.attachEvent){
                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                }
            }else{
                onBridgeReady();
            }
        }
  </script>

<script>
$(document).ready(function(){
	callpay()
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
