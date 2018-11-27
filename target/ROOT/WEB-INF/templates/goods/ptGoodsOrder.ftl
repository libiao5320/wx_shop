<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
 
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-ui-min.js"></script>
    <script type="text/javascript" src="../../js/js.js"></script>
    <script type="text/javascript" src="/js/yxPublic.js"></script>
   <link href="../../css/style.css" rel="stylesheet" type="text/css">


<script>
/*
 *商品应付多少钱
 *@param price  商品单价
 *@param num    商品数量
 *@param tprice 红包价格
 *return 应付费用价格
 */
var needtotalpay=function(price,num,tprice){
	         return parseFloat(parseFloat(price)*parseInt(num)-parseFloat(tprice)).toFixed(2); 
}
/*
 * 检测电话号码必须为手机号码
 * return true,false
 */
var checkPhoneNum=function(){
       var phone=$('.telPhone').val();
       if(phone && /^1[3|4|5|8]\d{9}$/.test(phone)){
         return true;
       } else{
         alert('请输入有效手机号码');
         $('.telPhone').val('');
         $('.telPhone').focus();
         return false;
       } 
}

function complete(XMLHttpRequest, textStatus){
  $(".sk-circle1").hide();
}
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
			$('.pay_bt1_a').focus();
			//$('#password').attr("readonly unselectable","on");
			$("#password").attr("readonly","readonly");
			
			
		}
	});
	
	$("#password").click(function(){
		if($('#password').val().length==6){	
		$('#password').removeAttr("readonly");
		  $(this).val("");
			if($(".pay_bt1").css('position') == 'absolute'){
				$(".pay_bt1").css('position','fixed');
				//$('#password').blur();//失去焦点
			}
		}else{
			$('#password').focus();
		}
	}); 	
	
});
	
function beforeSend(XMLHttpRequest){
   $(".sk-circle1").show();
}
$(function(){
    	$('.pay_lj') .click(function(){
    	$('#password').removeAttr("readonly");
    	var isDiyText = $('#isDiyText').val();
    	
    	if(isDiyText=='Y'){<!--test div start -->
	    	if($('.userName').val()!=''&&checkPhoneNum()==true)
	    	{
	    		 $.ajax({
				 url:"/order/createdOrder.ajax",
				 data:$(this).closest("form").serialize(),
				 dataType:"json",
				 beforeSend:beforeSend,
				 complete:complete,			 
				 success:function(json){
	               if(json.state){
	                    $("input[name='map[totalNumber]']").val(json.param.total_order_num);
                        
                        var availablePredeposit=parseInt(json.param.availablePredeposit)/100;
	                    var freezePredeposit=parseInt(json.param.freezePredeposit)/100;
	                    $(".balance").text("可用余额:"+availablePredeposit+"元");
		                $(".zhyebalance").text
		                ((parseInt(json.param.availablePredeposit)+parseInt(json.param.freezePredeposit))/100);//账户余额
	            
	                    var needPay = $(".needpay").text();
	                 
	                    if(parseFloat(needPay)>(parseFloat(json.param.availablePredeposit)/100)){
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
		                     $(".navs37").show();//密码框
		                     $(".pay_bt1_p3").hide();
		                     
		                  }
					    $('.pay_bt').slideDown("slow");
						$('.pay_bt1').slideDown("slow");
						$('.ceng3').show();
						
					  $('#password').css('letter-spacing',$('#password').width()/6);
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
			 })
	    	}
	    	else{
	    	      alert("用户名和电话不能为空");
	    	}
    	}else{
		    	$.ajax({
					 url:"/order/createdOrder.ajax",
					 data:$(this).closest("form").serialize(),
					 dataType:"json",
					 beforeSend:beforeSend,
					 complete:complete,			 
					 success:function(json){
		               if(json.state){
		                    $("input[name='map[totalNumber]']").val(json.param.total_order_num);
		                    var availablePredeposit=parseInt(json.param.availablePredeposit)/100;
		                    var freezePredeposit=parseInt(json.param.freezePredeposit)/100;

		                    $(".balance").text("可用余额:"+availablePredeposit+"元");
		                    $(".zhyebalance").text(availablePredeposit+freezePredeposit);//账户余额
		                    var needPay = $(".needpay").text();
		                    
		                    if(parseFloat(needPay)>parseFloat(availablePredeposit)){
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
				 })
	    	}<!--test div end -->

	});
	
	/*$('.ceng3') .click(function(){
		$('.pay_bt').slideUp("slow");
		$('.pay_bt1').slideUp("slow");
		$('.ceng3').hide();
	});*/
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
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">生成订单</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<#if goods?? >
		<article>
		    <section class="navs23">
		        <ul>
		            <li>
		                <a href="">
		                    <div class="navs4_left">
		                        <p>
		                            <img src="${GOODS_IMG_URL!''}${goods.goodsImage!''}" alt="" title="">
		                        </p>
		                    </div>
		                    <div class="navs4_right">
		                        <p class="navs4_p1"><i>产品名称  -${goods.goodsName!'' }</i></p>
		                        <p class="navs4_p2"><i></i></p>
		                       
		                        <#if LOGIN_SIGN ?? >
			                        <#if LOGIN_SIGN.vipRankId==0 >
			                            <p class="navs4_p3">VIP0： <i>¥</i>
			                        	<lable class="vipPrice" >  ${goods.goodsVip0Price/100!'0'}</lable>
			                        <#elseif  LOGIN_SIGN.vipRankId==1 >
			                            <p class="navs4_p3">VIP1： <i>¥</i>
			                        	<lable class="vipPrice" > ${goods.goodsVip1Price/100!'0'}</lable>
			                        <#elseif  LOGIN_SIGN.vipRankId==2 >
			                        	  <p class="navs4_p3">VIP2： <i>¥</i>
			                        	<lable class="vipPrice" > ${goods.goodsVip2Price/100!'0'}</lable>
			                        <#elseif  LOGIN_SIGN.vipRankId==3 >
			                       		<lable class="vipPrice" >  ${goods.goodsVip3Price/100!'0'}</lable>
			                        <#elseif  LOGIN_SIGN.vipRankId==4 >
			                        	 <lable class="vipPrice" > ${goods.goodsVip4Price/100!'0'}</lable>	
			                        </#if>
		                        <#else>
		                        	 	<lable class="vipPrice" > ${goods.goodsVip0Price/100!'0'}</lable>
		                        </#if>		                        
		                        <!--<span class="yff">预付费：¥${goods.bookDownPayment/100}</span></p>-->
		                    </div>
		                </a>
		            </li>
		        </ul>
		    </section>
<script>

$(function(){
     $('.telPhone').blur(function(){
           checkPhoneNum();
     });
})
 function queryMoney(sum){
	 var price = $(".price").val(); 
	 var tprice=$("#red_money").find('i').text();                 //红包价格
 	$(".sub-total").find('i').text(parseFloat(parseFloat(price)*parseInt(sum)).toFixed(2));            //总金额多少钱
     $(".total").text(needtotalpay(price,sum,tprice));
    $(".needpay").text(needtotalpay(price,sum,tprice));
    $("input[name='map[needpay]']").val(needtotalpay(price,sum,tprice));
 }
</script>
	        			 <#if LOGIN_SIGN ?? >
			                        <#if LOGIN_SIGN.vipRankId==0 >
			                        <input type="hidden" value="${goods.goodsVip0Price/100!'0'}" class="price">		
			                        <#elseif  LOGIN_SIGN.vipRankId==1 >
			                        <input type="hidden" value="${goods.goodsVip1Price/100!'0'}" class="price">		
			                        <#elseif  LOGIN_SIGN.vipRankId==2 >
			                       	<input type="hidden" value="${goods.goodsVip2Price/100!'0'}" class="price">	
			                        <#elseif  LOGIN_SIGN.vipRankId==3 >
			                       		<input type="hidden" value="${goods.goodsVip3Price/100!'0'}" class="price">	
			                        <#elseif  LOGIN_SIGN.vipRankId==4 >
			                        	<input type="hidden" value="${goods.goodsVip4Price/100!'0'}" class="price">	
			                        </#if>
		                        <#else>
		                        	 	<input type="hidden" value="${goods.goodsVip0Price/100!'0'}" class="price">	
		                        </#if>	
<form>	    
		    <section class="navs24">
		        <p class="pay_bt_p3">数量 
		        <span>
		            <a class="pay_delete">-</a>
			        	<input id="kzsl"  type="tel" placeholder="" value="1" name="map[goodsNum]" onkeyup='this.value=this.value.replace(/\D/gi,"")'>
			        <a class="pay_add">+</a>
			        </span>
		        </p>
		       <div class="navs20_d1 pay_lj_yh"><span class="navs20_d1_s1">
			      	 <#if goods.isPtRed ??>
			      	 	
						       	 	<#if ptRed?? >
						       	 		<#if  (ptRed?size > 0) >	
						  		   		红包使用</span><span class="navs20_d1_s5"></span>
						  		   		
						  		   		<span class="navs20_d1_s6">${ptRed ? size }</span>
						  		   		
						  		   		</#if>
						  		   	</#if>
						
			  		 </#if>
		  		 
		  		    </div>
		    </section>
		    
		    <section class="navs25">
		        <div class="navs20_d1"><span class="navs20_d1_s1">商品总额</span><span class="navs20_d1_s3 sub-total">¥<i>
		        			 <#if LOGIN_SIGN ?? >
			                        <#if LOGIN_SIGN.vipRankId==0 >
			                        	 ${goods.goodsVip0Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==1 >
			                        	${goods.goodsVip1Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==2 >
			                        	${goods.goodsVip2Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==3 >
			                       		 ${goods.goodsVip3Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==4 >
			                        	 ${goods.goodsVip4Price/100!'0'}	
			                        </#if>
		                        <#else>
		                        	 	${goods.goodsVip0Price/100!'0'}
		                        </#if>		                        
		        </i></span></div>
		        <div class="navs20_d1"><span class="navs20_d1_s1">优惠金额</span><span class="navs20_d1_s6" id="red_money">-¥<i>0</i></span></div>
		        <!--
		        <div class="navs20_d1"><span class="navs20_d1_s1">在线预付</span><span class="navs20_d1_s3">¥200</span></div>
		        <div class="navs20_d1"><span class="navs20_d1_s1">消费后付余款</span><span class="navs20_d1_s3">¥1600</span></div>-->
		    </section>
		    

		    <section class="navs26">
		    	<input type="hidden" id="isDiyText" value="${goods.isDiyText!'N'}" >
		    	
		    		<#if goods.isDiyText ??>
			        	 <#if goods.isDiyText=='Y' >
						        <span class="navs26_sp navs26_sps">真实姓名：</span>
						        <div class="navs26_d navs26_ds">
						            <input class="userName" type="text" name="map[mTrueName]" list="cars" placeholder="请输入姓名">
						        </div>
						        
						        <span class="navs26_sp navs26_sps">联系电话：</span>
						        <div class="navs26_d navs26_ds">
						            <input class="telPhone" type="tel" list="cars" name="map[mMobile]" placeholder="联系电话" maxlength=11 onkeyup='this.value=this.value.replace(/\D/gi,"")'>
						        </div>
						        <span class="navs26_sp navs26_sps">日期：</span>
						        <div class="navs26_d navs26_ds">
						            <input class="" type="date" list="cars" name="map[mDate]" placeholder="请输入日期">
						        </div>
						        <span class="navs26_sp navs26_sps">详细地址：</span>
						        <div class="navs26_d navs26_ds">
						            <input  class="" type="text" list="cars"  name="map[mAddress]"  placeholder="详细地址" >
						        </div>
        		        </#if>
			        </#if>
			        
			        <span class="navs26_sp">留言备注：</span>
			        <div class="navs26_d">
			            <textarea rows=""  cols="" placeholder="选填，填写特别要求给商家"></textarea>
			        </div>
   			 </section>
   			
   			<#if LOGIN_SIGN ?? >
 				<input type="hidden" name="map[memberId]" value="${LOGIN_SIGN.id}">    <!-- 用户id -->
 				<input type="hidden" name="map[vipRankId]" value="${LOGIN_SIGN.vipRankId}"> <!-- 用户vip等级 -->
		    </#if >
		    
		    <input type="hidden" name="map[rcbId]" > <!-- 红包id -->
			<input type="hidden" name="map[storeId]"  value="${goods.storeId}" >
			<input type="hidden" name="map[storeName]" value="${goods.storeName!''}">
			<input type="hidden" name="map[goodsId]"  value="${goods.goodsId}">
			<input type="hidden" name="map[goodsName]"  value="${goods.goodsName}">
			<input type="hidden" name="map[orderType]" value="${goods.isBook}">
			<input type="hidden" name="map[goodsLitpic]" value="${goods.goodsImage!''}">
			<input type="hidden" name="map[memberId]" value="1">
			
		            			 
		    					<#if LOGIN_SIGN ?? >
			                        <#if LOGIN_SIGN.vipRankId==0 >
			                        <p class="navs8_p2">VIP0：<i>￥</i><span class="total">		
			                        	 ${goods.goodsVip0Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==1 >
			                         <p class="navs8_p2">VIP1：<i>￥</i><span class="total">		
			                        	${goods.goodsVip1Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==2 >
			                         <p class="navs8_p2">VIP2：<i>￥</i><span class="total">		
			                        	${goods.goodsVip2Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==3 >
			                       		 ${goods.goodsVip3Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==4 >
			                        	 ${goods.goodsVip4Price/100!'0'}	
			                        </#if>
		                        <#else>
		                        	 	${goods.goodsVip0Price/100!'0'}
		                        </#if>	
		   </span><a href="javascript:;" class="pay_lj">提交订单</a> </p>
</form>		    
		    <section class="pay_bt1">
		    

<script>


<!--确认付款监听按钮 -->
$(function(){
  $(".pay_bt1_a").click(function(){
   
		var payPwd=$('#password').val();
		
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
                 window.location.href="/order/success.htm?map[paySn]="+paySn;
               }else if(json.message=='noPayPwd'){
                   window.location.href="/paymentPsd.htm"; 
			   }else{
			   	 alert(json.message);
			   }
			 }
       })
  })
})

<!--确认选择红包监听 -->
$(function(){
	  $(".pay_bt3_yes").attr("disabled", true); 
	  $(".lb_s").click(function(){
		var val=$('input:radio[name="hb_c"]:checked').val();
	            if(val==null){
	                $(".pay_bt3_yes").attr("disabled", true); 
	                return false;
	            }
	            else{
	            	$(".pay_bt3_yes").removeClass("pay_bt3_yess");
	                $(".pay_bt3_yes").attr("disabled", false);
	                 $(".pay_bt3_yes").click(function(){
      
  		var type = $('input[type="radio"]:checked');
  		if(type != null && type != '' && type != 'undefined'){
  			
  			
  			var redMoney = type.parents("p").find(".red_money").text();//红包金额、
  			redMoney = convert(redMoney);
  			var price = $(".price").val();//产品单价
  			price = convert(price);
  			var num= $('.pay_delete').next();     //商品数量dom对象
  			var number= num.val();   
  			     
  			if(redMoney>price){
  				redMoney=price;	
  			}
  			
  			$('input[name="map[rcbId]"]').val(type.val());
  			
  			  var totalMoney = $('.sub-total').find('i').text();
  			  
                 
	          $("#red_money").find('i').text(parseFloat(redMoney).toFixed(2));
	          $(".total").text(parseFloat(needtotalpay(price,number,redMoney)).toFixed(2));
			  $('.needpay').text(parseFloat(needtotalpay(price,number,redMoney)).toFixed(2));
		  
		  	/*var vals=$(".pay_bt3_yes":disabled').val();
	            if(vals==null){
	                $('.ceng3').hide();
  			  		$('.pay_bt3').hide();
	                return false;
	            }
	            else{
	            	
	            }
	            */
				
		  
  			  $('.ceng3').hide();
  			  $('.pay_bt3').hide();
  		}
  });
	                
	            }
		});
	
	
		
               
	            
 
  
})
</script>		        
		       <form>	 
			        <input type="hidden" name="map[totalNumber]">    <!-- 总订单号 -->
			        
			        <input type="hidden"  name="map[orderId]" >    <!-- 订单id -->
			        <input type="hidden" id="payPwd"  name="map[payPwd]" >    <!-- 支付密码 -->
		          <#if LOGIN_SIGN ?? >
	                        <#if LOGIN_SIGN.vipRankId==0 >
	                        <input type="hidden" name="map[needpay]" value="${goods.goodsVip0Price/100!'0'}"><!-- 应付金额 -->
	                        <#elseif  LOGIN_SIGN.vipRankId==1 >
	                        <input type="hidden" name="map[needpay]" value="${goods.goodsVip1Price/100!'0'}"><!-- 应付金额 -->
	                        <#elseif  LOGIN_SIGN.vipRankId==2 >
	                        <input type="hidden" name="map[needpay]" value="${goods.goodsVip2Price/100!'0'}"><!-- 应付金额 -->
	                        <#elseif  LOGIN_SIGN.vipRankId==3 >
	                       	<input type="hidden" name="map[needpay]" value="${goods.goodsVip3Price/100!'0'}"><!-- 应付金额 -->
	                        <#elseif  LOGIN_SIGN.vipRankId==4 >
	                       	<input type="hidden" name="map[needpay]" value="${goods.goodsVip4Price/100!'0'}"><!-- 应付金额 -->
	                       </#if>
		                  <#else>
	                       <input type="hidden" name="map[needpay]" value="${goods.goodsVip0Price/100!'0'}"><!-- 应付金额 -->
		            </#if>	
		        
		         
		        <div class="pay_bt1_one pay_bt1_two">
		            
		            <p class="pay_bt1_p1">确认付款</p>
		            <p class="pay_bt1_p2">VIP账户<span>账户余额:<lable class="zhyebalance">0</lable>元</span></p>
		            <p class="pay_bt1_p2"><span class="balance">可用余额: 0元</span></p>
		            
		           <a class="pay_bt1_p3" id="pay_bt1_p3" href="/vip/vip_index.htm" ><span class="navs20_d1_s1">余额不足</span><span class="navs20_d1_s5"></span><span class="navs20_d1_s6">去充值</span></a>
		            <p class="pay_bt1_p5"><span>需付款</span><i>元</i><i class="needpay">
		            			<#if LOGIN_SIGN ?? >
			                        <#if LOGIN_SIGN.vipRankId==0 >
			                        	 ${goods.goodsVip0Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==1 >
			                        	${goods.goodsVip1Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==2 >
			                        	${goods.goodsVip2Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==3 >
			                       		 ${goods.goodsVip3Price/100!'0'}
			                        <#elseif  LOGIN_SIGN.vipRankId==4 >
			                        	 ${goods.goodsVip4Price/100!'0'}	
			                        </#if>
		                        <#else>
		                        	 	${goods.goodsVip0Price/100!'0'}
		                        </#if>
		                        
		            </i></p>
		            
	<!----密码控制模块---->
    <section class="navs37"  style="margin-top: 0;padding-top: 17px;">
        <p class="navs37_p1" style="text-align:center">请输入VIP储值卡支付密码</p>
		<div class="zgwt" style="text-align:center; overflow: hidden;">
		<label class="mmtz">
		<p class="mmtz_p1"><i></i><i></i><i></i><i></i><i></i><i></i></p>
		<input id='password' placeholder="" type="password" maxlength="6" class="form-control input-lg" 
			onkeyup="this.value=this.value.replace(/\D/g,'')" 
			onafterpaste="this.value=this.value.replace(/\D/g,'')"  style="-webkit-appearance: none;" >
			
			
		</label>
		</div>
    </section>
	<p class="pay_bt1_xz"><a href="/tofindPwd.htm">忘记密码？</a></p>
	<p class="pay_bt1_p4" id="pay_bt1_p4"><a class="pay_bt1_a" href="javascript:;"  id="payment">确认付款</a></p>
		        </div>
		        
		        </form>
		        
		    </section>
		    
			<section class="pay_bt3">
		        <div class="pay_bt1_one">
		            <p class="pay_bt1_p1">选择红包</p>
		            <div class="pay_bt3_d">
		              	            
			               <#if ptRed ??>
			               		<#list ptRed as list>
					                <p class="pay_bt1_p2"><i>${list.name!''}</i><i class="red_money">${list.money/100!''}</i>元<label class="lb_s"><span class="hb_xz "></span>
					                <input type="radio" value="${list.id!''}" name="hb_c"  ></label>
					                </p>
			                	</#list>
							</#if>
		            </div>
		            <!-- 确认红包按钮  -->
		            <p  class="pay_bt3_s"><a class="pay_bt3_a1">
		            <span class="pay_bt3_yes pay_bt3_yess">确认</span></a>
		            
		            <a class="pay_bt3_a1" href="javascript:;">
		            <span class="pay_bt3_no">取消</span></a></p>
		        </div>
		    </section>
		    <div class="ceng3"></div>
		</article>
</#if>

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
    border: 1px solid #3C763D;
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
				ont-size: 28px;
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
