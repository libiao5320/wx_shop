<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>融耀健康</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css">
<script src="../../js/jquery.min.js" type="text/javascript"></script>
<script src="../../js/js.js" type="text/javascript"></script>

<script>
	
$(function(){
	
	
		$(".navs32_a").attr("disabled",true); 

  $(".navs32_as").click(function(){
  
		   var val=$('input:radio[name="reason"]:checked').val();
            if(val==null){
      			alert("退款原因至少选一项")
                return false;
            }
           
       $('#myform').submit();
   })
   
   
   $("input[name='reason']").on("click",function(){
   		$(".navs32_a").attr("disabled",false); 
   		$(".navs32_a").removeClass("no_click");
   		var _this = $(this);
		$("textarea[name='map[reason]']").text(_this.val());
   		$("textarea[name='map[reason]']").val(_this.val());
   		
   		var pas = _this.parent().siblings();
   		
   		//alert($("textarea[name='map[reason]']").text());
   		
   		pas.each(function(i , o ){
   			$(o).find(".anbhs1").removeClass("anbhs1");
   		})
   		
   		if(_this.val() !=  '其它原因')
   		{
			$("textarea[name='map[reason]']").css("display","none");
   		}
   		else
   		{
   			$("textarea[name='map[reason]']").css("display","block");
   		}

   		
		   
   });
}

)	

	</script>    

</head>
<body class="cpxq_qb">
    <header>
        <section class="head">
            <a class="left_nav"></a>
            <p class="top_txt top_txts">申请退款</p>
            <div class="top_right top_rights">
                <a class="top_sx" href=""></a>
            </div>
        </section>
    </header>

    <article>
    	<div class="navss8">
        	<h3>退款订单</h3>
            <p>${order.goodsName!''}</p>
        </div>
        <section class="navss8_p1">
        	<label class="labels1"><input name="" type="radio" value="">消费券：${order.consumptionCode!''}<span class="anbh1 anbhs1"></span></label>
        </section>
        <p class="navss8_p2">退还内容</p>
       	<form name="myform" id="myform" method ="post"  action ="/refund/submitRefund.htm">
       		
       	<input type="hidden" name="map[orderId]" value="${order.orderId!''}"><!-- 订单id -->
        <section class="navss8_p5">
        	<p>退款金额<span>¥${order.pdAmount/100!''}</span></p>
            <p>优惠不可退<span><!--新用户立即30元--></span></p>
        </section>
        <p class="navss8_p2">退款方式</p>
        <section class="navss8_p1">
        	<label class="labels1"><input name="" type="radio" value="">原路退回<i>3-10个工作日内退款到原支付方，暂不收手续费</i><span class="anbh1 anbhs1"></span></label>
        </section>
        <p class="navss8_p2">退款原因<span>（至少选一项）</span></p>
        <section class="navss8_p1">
        	<div class="navss8_p3 navss8_p5">
                <label class="labels2"><input type="radio" name="reason" value="预约不上" id="CheckboxGroup1_0">预约不上<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="买多了/买错了" id="CheckboxGroup1_1">买多了/买错了<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="服计划有变，没时间消费了" id="CheckboxGroup1_2">计划有变，没时间消费了<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="评价不好" id="CheckboxGroup1_3">评价不好<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="去过了，不太满意" id="CheckboxGroup1_4">去过了，不太满意<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="后悔了，不想要了" id="CheckboxGroup1_5">后悔了，不想要了<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="商家营业但不接待" id="CheckboxGroup1_6">商家营业但不接待<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="商家停业/装修/转让" id="CheckboxGroup1_7">商家停业/装修/转让<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="商家说可以直接以网购价到店消费" id="CheckboxGroup1_8">商家说可以直接以网购价到店消费<span class="anbh1"></span></label>
                <label class="labels2"><input type="radio" name="reason" value="其它原因" id="CheckboxGroup1_9">其它原因<span class="anbh1"></span></label>
          </div>
          
        </section>
        
        <section class="navss8_p4">
        	<p>其他原因</p>
            <textarea name="map[reason]"  cols="" rows="" placeholder="在此输入详情原因"> </textarea>
        </section>
        
        <a class="navs32_a navs32_as no_click" >申请退款</a>
        </form>	
        
    </article>
 </body>
 
</html>
