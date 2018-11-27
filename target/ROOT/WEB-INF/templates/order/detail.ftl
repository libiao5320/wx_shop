<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-ui-min.js"></script>
    <script type="text/javascript" src="../../js/js.js"></script>
    
<script type="text/javascript" >

$(function(){

  $(function(){
	var browser = {
			versions: function () {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
			iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
			iPad: u.indexOf('iPad') > -1, //是否iPad
			};
			}(),
			}
			if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
			$('#password').css("font-size","13px");
			$('#password').css("padding","12px 0");
			}
			if (browser.versions.android) {
			
			}
	$('#password').on('input',function(){
	
		if($('#password').val().length==6){
			if(isNaN($('#password').val())){//是否为非数字
				return;
			}
			$('#password').blur();//失去焦点
		   	$("#pay_bt1_p4").attr("disabled", false); 
			$(".pay_bt1").css('position','absolute');
			$("#password").attr("readonly","readonly");
			
			
		}
	});
	
	$("#password").click(function(){
		if($('#password').val().length==6){	
		$("#password").removeAttr("readonly");
		  $(this).val("");
			if($(".pay_bt1").css('position') == 'absolute'){
				$(".pay_bt1").css('position','fixed');
				
			}
}else{
			$('#password').focus();
		}
	}); 	
	
});
	});
	
function complete(XMLHttpRequest, textStatus){
  $(".sk-circle1").hide();
}
function beforeSend(XMLHttpRequest){
   $(".sk-circle1").show();
}

$(function(){
    	$('.pay_lj') .click(function(){
    	$('#password').removeAttr("readonly");
    	var orderState=$("#orderState").val();//订单状态 
    	var orderType=$("#orderType").val();//订单类型
	    	
	    	if(orderState==1){/**orderState start**/
	    	
	    		$.ajax({/**ajax start**/
					
						 url:"/getMemberById.ajax",
						 dataType:"json",
						 beforeSend:beforeSend,
					 	 complete:complete,	
			             success:function(json){
			             if(json.state){
			               			var availablePredeposit=parseInt(json.param.availablePredeposit)/100;
				                    var freezePredeposit=parseInt(json.param.freezePredeposit)/100;
				                    $(".balance").text("可用余额:"+availablePredeposit+"元");
					      			$(".zhyebalance").text((parseInt(json.param.availablePredeposit)+parseInt(json.param.freezePredeposit))/100);//账户余额
					                
					                var needPay = $(".needpay").text();//支付金额
					                var bookFinalPayment=$('#bookFinalPayment').val();//消费后付余款
					                
					                if((parseFloat(bookFinalPayment)+parseFloat(needPay))>parseFloat(availablePredeposit)){
					                     $(".navs20_d1_s1").show(); 
					                     $(".navs20_d1_s6").show(); 
					                     $(".pay_bt1_p4").hide();
					                     $(".navs37").hide();//密码框
					                     $("#pay_bt1_p3").css("display","block");
					                     $(".pay_bt1_xz").hide();//订单详情页，余额不足，隐藏钓忘记密码
					                  }else{
					                  	$(".navs20_d1_s1").show(); 
					                     $(".navs20_d1_s6").show(); 
					                     $(".pay_bt1_p4").show();
					                     $(".pay_bt1_p3").hide();
					                     $(".navs37").show();//密码框
					                  }
					                
					                $('.pay_bt').slideDown("slow");
									$('.pay_bt1').slideDown("slow");
									$('.ceng3').show();
									$('#password').css('letter-spacing',($('.mmtz_p1').width()-47)/6);
									 $('.mmtz').css('padding-left',($('.mmtz_p1').width()-60)/12);
									 $('.mmtz_p1 i').css("width",($('.mmtz_p1').width()-6)/6);				
									 $('#password').val('');
			               }else{
			               
				               if(json.message=='login'){
			               	  	redirect('/userCenter.htm');
			               	  	return;
			               	  }else if(json.message=='noPayPwd'){
		                  		 window.location.href="/paymentPsd.htm"; 
							   }else{
							   	 alert(json.message);
							   }
						   }
						 }
			       })/**ajax end**/
	    	}else if(orderState=3){
	    				$.ajax({/**ajax start**/
					
						 url:"/getMemberById.ajax",
						 dataType:"json",
						 beforeSend:beforeSend,
					 	 complete:complete,	
			             success:function(json){
			             if(json.state){
			               			var availablePredeposit=parseInt(json.param.availablePredeposit)/100;
				                    var freezePredeposit=parseInt(json.param.freezePredeposit)/100;
				                    $(".balance").text("可用余额:"+availablePredeposit+"元");
					                $(".zhyebalance").text((parseInt(json.param.availablePredeposit)+parseInt(json.param.freezePredeposit))/100);//账户余额
					                
					                var needPay = $('#bookFinalPayment').val();//消费后付余款
					                
				                  	$(".navs20_d1_s1").show(); 
				                    $(".navs20_d1_s6").show(); 
				                    $(".pay_bt1_p4").show();
				                    $(".pay_bt1_p3").hide();
				                    $(".navs37").show();//密码框
					                
					                $('.pay_bt').slideDown("slow");
									$('.pay_bt1').slideDown("slow");
									$('.ceng3').show();
									$('#password').css('letter-spacing',($('.mmtz_p1').width()-47)/6);
									 $('.mmtz').css('padding-left',($('.mmtz_p1').width()-60)/12);
									 $('.mmtz_p1 i').css("width",($('.mmtz_p1').width()-6)/6);				
									 $('#password').val('');
			               }else{
			               
				               if(json.message=='login'){
			               	  	redirect('/userCenter.htm');
			               	  	return;
			               	  }else if(json.message=='noPayPwd'){
		                  		 window.location.href="/paymentPsd.htm"; 
							   }else{
							   	 alert(json.message);
							   }
						   }
						 }
			       })/**ajax end**/
	    	} /**orderState 3 end**/
				
		       
    
    	});
	});
	
	
<!--确认付款监听按钮 -->
$(function(){
  $(".pay_bt1_a").click(function(){
		var payPwd=$('#password').val();
		
		var orderState=$("#orderState").val();//订单状态 
		
		if(payPwd==''){
			alert('支付密码不能为空');
			return;
		}
	   $("input[name='map[payPwd]']").val(payPwd);
	   
       $.ajax({
			 url:"/order/pay.ajax",
			 data:$(this).closest("form").serialize(),
			 dataType:"json",
			 beforeSend:beforeSend,
			 complete:complete,	
             success:function(json){
             
             if(json.state){
             	 var paySn=json.param.paySn;
             	 var orderId=json.param.orderId;
             	 if(orderState==1){
             	        window.location.href="/order/success.htm?map[paySn]="+paySn+"&map[orderId]="+orderId;
             	 }else{
             	 		alert('恭喜您支付成功！');
             	 		window.location.href="/order/detail/"+orderId+".htm";
             	 }
                 
               }else if(json.message=='noPayPwd'){
                   window.location.href="/paymentPsd.htm"; 
			   }else{
			   	 alert(json.message);
			   	 return;
			   }
			 },
			 fail:function(){
			 	alert("error");
			 	return;
			 },
			 error:function(){
			 	alert("error");
			 	return;
			 }
       })
  })
})



</script>
</head>
<body>
<div class="sk-circle1" style="display:none">
      <div class="sk-circle-bg"></div>
      <div class="sk-circle">
        <div class="sk-circle1 sk-child"></div>
        <div class="sk-circle2 sk-child"></div>
        <div class="sk-circle3 sk-child"></div>
        <div class="sk-circle4 sk-child"></div>
        <div class="sk-circle5 sk-child"></div>
        <div class="sk-circle6 sk-child"></div>
        <div class="sk-circle7 sk-child"></div>
        <div class="sk-circle8 sk-child"></div>
        <div class="sk-circle9 sk-child"></div>
        <div class="sk-circle10 sk-child"></div>
        <div class="sk-circle11 sk-child"></div>
        <div class="sk-circle12 sk-child"></div>
      </div>
</div>
<header>
    <section class="head">
        <a class="left_nav" href="/myOrder/0.htm"></a>
        <p class="top_txt">订单详情页</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
    <section class="navs43">
        <ul>
            <li>
            	<#if order.orderType == 3>
            	<a href="/event/detail.htm?id=${order.goodsId !''}">
            	<#else>
                <a href="/goods/detail.htm?goodsId=${order.goodsId !''}">
                </#if>
                    <div class="navs4_left">
                        <p>
                            <img src="${GOODS_IMG_URL!''}${order.goodsLitpic !''}" alt="" title="" >
                        </p>
                    </div>
                    <div class="navs4_right">
                        <p class="navs4_p1"><i>${order.goodsName !''}</i></p>
                        <p class="navs4_p2">VIP价：<i>¥${order.orderAmountDollar !''}</i></p>
                        <p class="navs4_p2">预付费：<i>¥${order.bookDownPaymentDollar !''}</i></p>
                    </div>
                </a>
            </li>
        </ul>
    </section>

    <section class="navs25">

        <div class="navs20_d1"><span class="navs20_d1_s1">订单号</span><span class="navs20_d1_s6">${order.orderSn !''}</span></div>
        <div class="navs20_d1"><span class="navs20_d1_s1">下单时间</span><span class="navs20_d1_s6">${order.strAddTime !''}</span></div>
        <div class="navs20_d1"><span class="navs20_d1_s1">消费后付余款</span><span class="navs20_d1_s3">¥${(order.bookFinalPayment!0)/100}</span></div>
        <input type="hidden" id="bookFinalPayment"  value="${(order.bookFinalPayment!0)/100}" ><!-- 消费后付余款 -->
        <#if order.orderState==3 >
    	<div class="navs20_d1"><span class="navs20_d1_s1">线上付余款享VIP折上折优惠</span>
    		<a class="bts_an1 bts_qfyk" href="javascript:;"><p class="pay_lj" >去付余款</p></a>
    		
    		</div>
    	</#if>
    	
    </section>
    
    <!-- 弹窗 start -->
    <form >	 
    <input type="hidden" name="map[totalNumber]" value="${order.paySn!0}" >    <!-- 总订单号 -->
	<input type="hidden" id="orderId" value="${order.orderId!''}"  name="map[orderId]" >    <!-- 订单id -->
	<input type="hidden" id="payPwd"  name="map[payPwd]"  >    <!-- 支付密码 -->
	<input type="hidden" id="orderState" value="${order.orderState!''}"  name="map[orderState]"  >    <!-- 订单状态-->
	<input type="hidden" id="orderType" value="${order.orderType!''}"  name="map[orderType]"  >    <!-- 订单类型-->
	
    <section class="pay_bt1">
				        <!----立即付款------>
				        <div class="pay_bt1_one pay_bt1_two">
				           
				            <p class="pay_bt1_p1">确认付款</p>
				            <p class="pay_bt1_p2">VIP账户<span>账户余额:<lable class="zhyebalance">${order.pdAmount/100!''}</lable>元</span></p>
				            <p class="pay_bt1_p2"><span class="balance">可用余额:${order.pdAmount/100!''} 元</span></p>
				            <a class="pay_bt1_p3" id="pay_bt1_p3" href="/vip/vip_index.htm" ><span class="navs20_d1_s1">余额不足</span><span class="navs20_d1_s5"></span><span class="navs20_d1_s6">去充值</span></a>
				            <p class="pay_bt1_p5"><span>需付款</span><i>元</i><i class="needpay">
				         		<#if order.orderState == 3 >
				         			${order.bookFinalPayment/100!''} 
				         		<#else>
				         			${order.pdAmount/100!''} 
				         		</#if>
				            
				           </p>
				           <!----密码控制模块---->
						    <section class="navs37" >
						         <p class="navs37_p1" style="text-align:center">请输入VIP储值卡支付密码</p>
								<div class="zgwt" style="text-align:center; overflow: hidden;">
								
								<label class="mmtz">
									<p class="mmtz_p1"><i></i><i></i><i></i><i></i><i></i><i></i></p>
									<input id='password'  placeholder="" type="password" maxlength="6" class="form-control input-lg" >
								
								</label>
								</div>
						    </section>
						    <!----密码控制模块end---->
						    <p class="pay_bt1_xz"><a href="/tofindPwd.htm">忘记密码？</a></p>
				            <p class="pay_bt1_p4"><a class="pay_bt1_a " href="javascript:;">确认付款</a></p>
				        </div>
	</section>
	</form>	
    <!-- 弹窗end -->


	<!-- 未付款 -->
	<#if order.orderState == 1>
			
			<#if order.orderType==1>
		    	<p class="navs8_p2">合计：<i>￥</i><span>${order.pdAmount/100!''}</span><!--需要支付的预存款金额-->
			<#elseif order.orderType==2>
				    <p class="navs8_p2">合计：<i>￥</i><span>${order.pdAmount/100!''}</span><!--需要支付的预存款金额-->
			<#elseif order.orderType==3>
		    		<p class="navs8_p2">合计：<i>￥</i><span>${order.pdAmount/100!''}</span><!--需要支付的预存款金额-->
		    </#if>
		    <a href="javascript:;" class="pay_lj">去付款</a>
		    </p>
   
    </#if>
    
    <!-- 未消费 -->
    <#if order.orderState == 2 >
    	<section class="navs44">
        <p class="navs44_p1">示卡消费券</p>
        <span class="navs44_sp1"></span>
        <span class="navs44_sp2"></span>
        <div class="navs44_d">
            <p class="navs44_d_p1">消费券（1张）</p>
            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
            <a class="navs44_a" href="/refund/sqRefund/${order.orderId!'10'}.htm" >申请退款</a>
        </div>
        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is">未消费</i></p>
    </section>
    </#if>
    <!-- 3 待评价未付余款 -->
    <#if order.orderState ==3 >
    
    	<#if order.goods??>
    		<section class="navss12_p6 navss12_p6s">
    		<a href="#" onclick="alert('抱歉，您的余款未支付，不能马上评价哦')" ><span class="qpjb">去评价</span></a></section>
    	<#else>
    		<section class="navss12_p6 navss12_p6s"><p class="navs13_right_p2"><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i><i class=""></i></p>
    		<a href="#" onclick=alert('抱歉，您的余款未支付，不能马上评价哦') ><span class="qpjb">去评价</span></a></section>
    		
    	</#if>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>
	           		 ${order.delayTimeStr!''}
	            </nub></p>
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is">已消费未付余款</i></p>
	        <p class="navs44_d_p3">验证时间：<nub>${order.verifyConsumptionTimeStr !''}</nub></p>
	    </section>
    </#if>
    
	<!-- 4 待评价已付余款 -->
	<#if order.orderState == 4>

		<section class="navss12_p6 navss12_p6s"><a href="/order/toComment/${order.orderId}/${order.goodsName}.htm"><span class="qpjb">去评价</span></a></section>
		
	    <section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	            <a class="navs44_a" href="/order/toComplain/${order.orderId}/${order.consumptionCode}/${order.goodsName}.htm">投诉维权</a>
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="../../images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i>
	        	<i class="navs44_is">已使用</i></p>
	        <p class="navs44_d_p3">验证时间：<nub>${order.verifyConsumptionTimeStr !''}</nub></p>
	    </section>
    </#if>
    <!-- 5 已评价 -->
    <#if order.orderState == 5>
    	<#if order.goods??>
    		<section class="navss12_p6 navss12_p6s"><p class="navs13_right_p2 navs13_right_p2s">
			<#if eval.grade == 0>
    		<i></i><i></i><i></i><i></i><i></i>
    		<#elseif eval.grade == 1>
    		<i class="navs13_i"></i><i></i><i></i><i></i><i></i>
    		<#elseif eval.grade == 2>
    		<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
    		<#elseif eval.grade == 3>
    		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
    		<#elseif eval.grade == 4>
    		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i>
    		<#elseif eval.grade == 5>
    		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
    		<#else>
    		<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i>
    		</#if>
			</p><a href="#"><span class="qpjb"><nub>${eval.grade !'3'}</nub>分</span></a></section>
    	<#else>
    	<section class="navss12_p6 navss12_p6s"><p class="navs13_right_p2 navs13_right_p2s"><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i></p><a href="#"><span class="qpjb"><nub>3</nub>分</span></a></section>
	    </#if>
	    <section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	            <#--<a class="navs44_a" href="">投诉维权</a>-->
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is">已使用</i></p>
	        <p class="navs44_d_p3">验证时间：<nub>${order.strVerifyConsumptionTime !''}</nub></p>
	    </section>
    </#if>
    <!-- 6 已取消 --><!-- 和删除同理 -->
    <#if order.orderState == 6>
    	<section class="navs45">
	        <p class="navs45_p1">订单已取消<a class="bts_an1 bts_qfyk" href="/goods/detail.htm?goodsId=${order.goodsId !''}">再次购买</a></p>
	    </section>
    </#if>
    
    <!-- 7 退款中 -->
    <#if order.orderState == 7>
    	 <section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	             <a class="navs44_a" href="/refund/cancelRefund.htm?orderId=${order.orderId !''}" onclick= "if(confirm( '是否确定取消申请退款！ ')==false)return   false;" >取消退款</a>
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is navs44_itk">退款中</i></p>
	        <p class="navs44_p4">退款申请提交成功，等待融耀平台审核处理</p>
	    </section>
    </#if>
    <!-- 8 已退款 -->
    <#if order.orderState == 8>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is navs44_itk">已退款</i></p>
	        <p class="navs44_p5">退款成功</p>
	        <p class="navs44_p6">退款金额：<nub>${order.refundAmountDollar !''}</nub></p>
	        <p class="navs44_p6">退款时间：<nub>${order.refundTimeStr !''}</nub></p>
	    </section>
    </#if>
    <!-- 9 退款已拒绝 -->
    <#if order.orderState == 9>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	            <a class="navs44_a" href="refund/sqRefund.htm?orderId="+${order.orderId} >申请退款</a>
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is">退款已拒绝</i></p>
	    </section>
	    <!-- <section class="navss12_p6"><a href="/store/detail.htm?id=${order.storeId !''}">商家详情介绍</a></section> -->
    </#if>
    <!-- 10 投诉处理中 -->
    <#if order.orderState == 10>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	
	
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is navs44_itk">投诉处理中</i></p>
	        <p class="navs44_p4">投诉申请提交成功，等待融耀平台审核处理</p>
	    </section>
    </#if>
    <!-- 11 投诉已解决 -->
    <#if order.orderState == 11>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub>${order.delayTimeStr !''}</nub></p>
	
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is navs44_itk">投诉已解决</i></p>
	        <p class="navs44_d_p3">验证时间：<nub>${order.strVerifyConsumptionTime !''}</nub></p>
	        <p class="navs44_p6">您的投诉已处理</p>
	        <p class="navs44_p6">解决时间：<nub>${complain.updateTime !'-'}</nub></nub></p>
	        <#if complain.reply??>
	        	<p class="navs44_p6">投诉回复：<nub>${complain.reply !'-'}</nub></nub></p>
	        </#if>
	    </section>
    </#if>
    
   
    <!-- 12 已过期 -->
    <#if order.orderState == 12>
    	<section class="navs44">
	        <p class="navs44_p1">示卡消费券</p>
	        <span class="navs44_sp1"></span>
	        <span class="navs44_sp2"></span>
	        <div class="navs44_d">
	            <p class="navs44_d_p1">消费券（1张）</p>
	            <p class="navs44_d_p2">有效期至<nub><#if order.delayTime?? && (order.delayTime?length gt 10)>${order.delayTime?substring(0,10)}<#else>${order.delayTime !''}</#if></nub></p>
	            <a class="navs44_a" href="">申请退款</a>
	        </div>
	        <p class="navs44_p2"><span class="navs44_span1"><img src="/images/jh.png" alt="" title=""></span>券号<i class="navs44_i navs44_ihx"><#if order.consumptionCode?? && (order.consumptionCode?length >= 10)>${order.consumptionCode?substring(0,4)}&nbsp;&nbsp;${order.consumptionCode?substring(4,7)}&nbsp;&nbsp;${order.consumptionCode?substring(7,10)}</#if></i><i class="navs44_is navs44_itk">已过期</i></p>
	    </section>
    </#if>
    
    <div class="ceng3"></div>
</article>

<style type="text/css">

  #password{
	 font-size: 28px;
			         border-radius: 3px;
			         border-color: #3C763D;
			         border-radius: 3px;
			         padding: 10px;
			        height: auto;
			        box-shadow: none;
			       width:60%;
			        font-size: 13px;
			        box-sizing: border-box;
          }
 	.navs37{position: relative;}
 </style> 
 
 <style type="text/css">
 .sk-circle1{ position:fixed; top:0; left:0; width:100%; height:100%; z-index:999;}
  .sk-circle-bg{ position:fixed; top:0; left:0; width:100%; height:100%; z-index:1000; background:#999;
  filter:alpha(opacity=50); -moz-opacity:0.5; opacity:0.5;}
.sk-circle {
	
  margin: 40px auto;
  width: 40px;
  height: 40px;
  position:absolute; left:50%; margin-left:-20px; top:50%; margin-top:-20px; z-index:1001;}
  .sk-circle .sk-child {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0; }
  .sk-circle .sk-child:before {
    content: '';
    display: block;
    margin: 0 auto;
    width: 15%;
    height: 15%;
    background-color: #333;
    border-radius: 100%;
    -webkit-animation: sk-circleBounceDelay 1.2s infinite ease-in-out both;
            animation: sk-circleBounceDelay 1.2s infinite ease-in-out both; }
  .sk-circle .sk-circle2 {
    -webkit-transform: rotate(30deg);
        -ms-transform: rotate(30deg);
            transform: rotate(30deg); }
  .sk-circle .sk-circle3 {
    -webkit-transform: rotate(60deg);
        -ms-transform: rotate(60deg);
            transform: rotate(60deg); }
  .sk-circle .sk-circle4 {
    -webkit-transform: rotate(90deg);
        -ms-transform: rotate(90deg);
            transform: rotate(90deg); }
  .sk-circle .sk-circle5 {
    -webkit-transform: rotate(120deg);
        -ms-transform: rotate(120deg);
            transform: rotate(120deg); }
  .sk-circle .sk-circle6 {
    -webkit-transform: rotate(150deg);
        -ms-transform: rotate(150deg);
            transform: rotate(150deg); }
  .sk-circle .sk-circle7 {
    -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
            transform: rotate(180deg); }
  .sk-circle .sk-circle8 {
    -webkit-transform: rotate(210deg);
        -ms-transform: rotate(210deg);
            transform: rotate(210deg); }
  .sk-circle .sk-circle9 {
    -webkit-transform: rotate(240deg);
        -ms-transform: rotate(240deg);
            transform: rotate(240deg); }
  .sk-circle .sk-circle10 {
    -webkit-transform: rotate(270deg);
        -ms-transform: rotate(270deg);
            transform: rotate(270deg); }
  .sk-circle .sk-circle11 {
    -webkit-transform: rotate(300deg);
        -ms-transform: rotate(300deg);
            transform: rotate(300deg); }
  .sk-circle .sk-circle12 {
    -webkit-transform: rotate(330deg);
        -ms-transform: rotate(330deg);
            transform: rotate(330deg); }
  .sk-circle .sk-circle2:before {
    -webkit-animation-delay: -1.1s;
            animation-delay: -1.1s; }
  .sk-circle .sk-circle3:before {
    -webkit-animation-delay: -1s;
            animation-delay: -1s; }
  .sk-circle .sk-circle4:before {
    -webkit-animation-delay: -0.9s;
            animation-delay: -0.9s; }
  .sk-circle .sk-circle5:before {
    -webkit-animation-delay: -0.8s;
            animation-delay: -0.8s; }
  .sk-circle .sk-circle6:before {
    -webkit-animation-delay: -0.7s;
            animation-delay: -0.7s; }
  .sk-circle .sk-circle7:before {
    -webkit-animation-delay: -0.6s;
            animation-delay: -0.6s; }
  .sk-circle .sk-circle8:before {
    -webkit-animation-delay: -0.5s;
            animation-delay: -0.5s; }
  .sk-circle .sk-circle9:before {
    -webkit-animation-delay: -0.4s;
            animation-delay: -0.4s; }
  .sk-circle .sk-circle10:before {
    -webkit-animation-delay: -0.3s;
            animation-delay: -0.3s; }
  .sk-circle .sk-circle11:before {
    -webkit-animation-delay: -0.2s;
            animation-delay: -0.2s; }
  .sk-circle .sk-circle12:before {
    -webkit-animation-delay: -0.1s;
            animation-delay: -0.1s; }

@-webkit-keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }

@keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }
  #password{
	 font-size: 28px;
	 border-radius: 3px;
	 border-color: #3C763D;
	 border-radius: 3px;
	 padding: 10px;
	 height: auto;
	 box-shadow: none;
	 width:96%;
	 font-size: 13px;
	 box-sizing: border-box;
          }
 	.navs37{position: relative;}
 </style>  
   
</body>
</html>
