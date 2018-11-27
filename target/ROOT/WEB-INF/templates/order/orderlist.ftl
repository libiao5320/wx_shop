<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>融耀健康</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">

</head>
<body class="cpxq_qb">
    <header>
        <section class="head">
            <a class="left_nav" href="/userCenter.htm"></a>
            <p class="top_txt top_txts">我的订单</p>
            <div class="top_right top_rights">
                <a class="top_dlt" href="javascript:;"></a>
                <a class="top_sx" href="javascript:;" onclick="window.location.reload();"></a>
            </div>
        </section>
    </header>
    <article>
		<input type="hidden" name="http_img" value="${GOODS_IMG_URL !''}">
        <section class="navs41">
        <form id="form-data">
            <ul class="navs41_ul" id="js_orderList">
  <!-- 
订单状态：
1 未付款
2 未消费
3 待评价未付余款
4 待评价已付余款
5 已评价
6 已取消
7 退款中
8 已退款
9 退款已拒绝
10 投诉处理中
11 投诉已解决
12 已过期  
  -->
<#if  result?? && result?size gt 0 >   
         <#list  result as c>         
                <li>
					<div class="xzkk">
						<label class="xzkk_lb" >
						<#if c.orderState == 1 || c.orderState == 5 || c.orderState == 8 || c.orderState == 6 || c.orderState == 11>
							<i class="xzkk_i"></i><input type="checkbox"  value="${c.orderId !''}" name="xzkk_n" class="xzkk_c"/>
						</#if>
						</label>
					</div>
                    <div class="navs41_d">
                        <a href="/order/detail/${c.orderId !''}.htm">
                        <div class="navs41_left">
                          <img src="${GOODS_IMG_URL !''}${c.goodsLitpic !''}" alt="" title="">
                        </div>
                        <div class="navs41_right">
                        	<!--<span class="detail" orderId="${c.orderId !''}">-->
                            <p class="navs41_right_p1">${c.goodsName !''}</p>
                            <p class="navs41_right_p2">¥${c.orderAmount/100 !''}</p>
                            <#if c.orderState == 1>
                            <p class="navs41_right_p3">
		                        <span class="navs40_s1">待付款</span>
		                        <a class="navs40_a" href="/order/detail/${c.orderId !''}.htm">去付款</a>
                            </p>
                            </#if>
                            <#if c.orderState == 2>
	                            <p class="navs41_right_p3">
			                        <span class="navs40_s1">未消费</span>
			                        <a class="navs40_a" href="/refund/sqRefund/${c.orderId !''}.htm">申请退款</a>
	                            </p>
                            </#if>
                            
                            <#if c.orderState == 3>
	                            <p class="navs41_right_p3">
			                        <span class="navs40_s1">待评价未付余款</span>
	                            </p>
                            </#if>
                            <#if c.orderState == 4>
                            <p class="navs41_right_p3 navs41_right_p3b">
		                        <span class="navs40_s1">待评价</span>
		                        <a class="navs40_a" href="/order/toComment/${c.orderId}/${c.goodsName}.htm">评价</a>
		                       <!-- <a class="navs40_a" href="/order/toComplain/${c.orderId}/${c.consumptionCode}/${c.goodsName}.htm">投诉</a>-->
                            </p>
                            </#if> 
                            
                            <#if c.orderState == 5>
                            <p class="navs41_right_p3 navs41_right_p3b">
		                        <span class="navs40_s1">已评价</span>
                            </p>
                            </#if> 
                            
                            <#if c.orderState == 6>
                            <p class="navs41_right_p3 navs41_right_p3a">
		                        <span class="navs40_s1">已取消</span>
		                        <!-- 跳转到产品页 -->
		                        <a class="navs40_a" href="/goods/detail.htm?goodsId=${c.goodsId !''}">再次购买</a>
                            </p>
                            </#if>  
                             
                         <!--  <p class="navs41_right_p3 navs41_right_p3c">
                           </span>
                            <span class="navs40_s1">-->
                           <#if c.orderState == 7>
	                           <p class="navs41_right_p3 navs41_right_p3b">
			                        <span class="navs40_s1">退款中</span>
			                        <a class="navs40_a" href="/order/detail/${c.orderId !''}.htm">取消退款</a>
	                           </p>
                          </#if>
                          
                          <#if c.orderState == 8>
                          	<p class="navs41_right_p3 navs41_right_p3b">
		                        <span class="navs40_s1">已退款</span>
                            </p>
                          </#if> 
                          <#if c.orderState == 9>
	                            <p class="navs41_right_p3 navs41_right_p3b">
			                        <span class="navs40_s1">退款已拒绝</span>
	                            </p>
                          </#if> 
                          <#if c.orderState == 10>
                          		<p class="navs41_right_p3 navs41_right_p3b">
			                        <span class="navs40_s1">投诉处理中</span>
	                            </p>
                          </#if> 	
                          <#if c.orderState == 11>
                                <p class="navs41_right_p3 navs41_right_p3b">
			                        <span class="navs40_s1">投诉已解决</span>
	                            </p>
                          </#if>  
                   
                          <!--   </span>
                          </p>-->
                                                                                       
                                                     
                        </div>
                        </a>
                    </div>
                </li>       
          </#list>    
</#if>                
            </ul>
        </form>
        </section>
    </article>
    <div class="xzkk_bt">
    	<label class="xzkk_bt_s"><i class="xzkk_i"></i><input type="checkbox" value="" name="" id="qxzl" class="qxzl"/>全选</label> <a class="xzkk_bt_a" href="javascript:;" url="/userDeleteOrder.ajax">删除</a>
    </div>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/js.js"></script>
    <script type="text/javascript">
        $(function(){
        	$.ajaxSetup({async:false});
          	var falg = true;
          	var pageNo = 2;
          	var status = '${status}';
          	$(window).scroll(function(){
          		var top = $(window).scrollTop();
				var doheight = $(document).height();
				var win_height=$(window).height();
				if(falg){
					if(top >= (doheight-win_height)){
						/**得到ul里面的最后一个li元素*/
						var _this = $('#js_orderList li:last');
						
						var url = $('input[name="http_img"]').val();
						
						$.post('/myOrder.ajax',{'pageNo':pageNo,'status':status},function(result){
							if(result != null && result != '' && result !='undefined'){
								$.each(result,function(n,value){
									var _li = _this.clone(true);
									_li.find('div .xzkk_lb input').val(value.orderId);
									var _div = _li.find('.navs41_d');
									_div.find('a').attr('href','/order/detail/'+value.orderId+'.htm');
									_div.find('.navs41_left img').attr('src',url+value.goodsLitpic);
									var _right = _div.find('.navs41_right');
									_right.find('.navs41_right_p1').text(value.goodsName);
									_right.find('.navs41_right_p2').text('¥'+value.orderAmountDollar);
									var _html = _right.find('.navs41_right_p3');
									_right.find('.navs41_right_p3').empty();
									_right.find('.navs41_right_p3').append('<span class="navs40_s1"></span>');
									var _ste = _right.find('.navs41_right_p3').find('.navs40_s1');
									var state = value.orderState;
									switch(state){
										case 1:
			                               	_ste.text('待付款');
			                           		_html.append('<a class="navs40_a" href="/order/detail/'+value.orderId+'.htm">去付款</a>');
			                                break;
			                            case 2:
			                                 _ste.text('未消费');
			                                _html.append('<a class="navs40_a" href="/refund/sqRefund/'+value.orderId+'.htm">申请退款</a>');
			                                break;
			                            case 3:
			                                 _ste.text('待评价未付余款');
			                                break;
			                            case 4:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                _ste.text('待评价已付余款');
			                                _html.append('<a class="navs40_a" href="/order/toComment/'+value.orderId+'/'+value.goodsName+'.htm">评价</a>');
			                                /**&nbsp;&nbsp;<a class="navs40_a" href="/order/toComplain/'+value.orderId+'/'+value.consumptionCode+'/'+value.goodsName+'.htm">投诉</a>*/
			                                break;
			                            case 5:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text('已评价');
			                                break;
			                            case 6:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3a');
			                                 _ste.text("已取消");
											_html.append('<a class="navs40_a" href="/goods/detail.htm?goodsId="+value.orderId>再次购买</a>');
			                                break;
			                            case 7:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text("退款中");
			                                _html.append('<a class="navs40_a" href="/order/detail/'+value.orderId+'.htm">取消退款</a>');
			                                break;
			                            case 8:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text("已退款");
			                                break;
			                            case 9:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text("退款已拒绝");
			                                break;
			                            case 10:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text("投诉处理中");
			                                break;
			                            case 11:
			                            	_right.find('.navs41_right_p3').addClass('navs41_right_p3b');
			                                 _ste.text("投诉已解决");
			                                break;
									}
									$('#js_orderList').append(_li);
								})
								pageNo++;
							}else{
								falg = false;
							}
						},'json')
					}
				}
          	})
        })
    </script>
 </body>
</html>
