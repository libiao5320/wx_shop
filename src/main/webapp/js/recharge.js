/**
 * 杨鑫 充值页面
 * 
 * */


$(function(){
/*	*//**页面加载时得到用户充值价格*//*
	var money = 
	vipRank(money);
	*/
	$('input[name="money"]').val('');
	/**根据选择的价格判断vip等级*/
	$('.navs32_li').click(function(){
		var money = $(this).find('.navs32_p1 nub').text();
		var vip = $(this).find('.navs32_p2 nub').text();
		var greade = $(this).find('input').val();
		$(this).find('div').css('border','1px solid #f35353').parent().siblings().find('div').css('border','1px solid #ded4d4');
		$('input[name="greade"]').val(greade);
		$('input[name="money"]').val(money);
		$('.navs32_p3').find('i').text(vip);
	});
	
	/**根据输入的价格判断vip等级*/
	$("input[name='money']").bind('input propertychange',function(){
		var money = $(this).val();
		if(check(money)){
			if(checkFloat(money,$(this))){
				money = $(this).val();
				vipRank(money);
			}else{
				$(this).val('');
			}
		}else{
			$(this).val('');
		}
	});
	
	$('#at_pay').click(function(){
		var money = $(this).prev().find('input').val();
		var greade = $('input[name="greade"]').val();
		if(money != null && money != '' && money != 'undefined'){
			wxpay();
		}else{
			alert('请选择或输入金额！');
		}
	});
	
	$('.ceng3') .click(function(){
		$('.pay_bt').slideUp("slow");
		$('.pay_bt1').slideUp("slow");
		$('.ceng3').hide();
		$('input[name="password"]').val('');
	});
	
	/**调用微信支付接口*/
	$('.pay_lj_1').click(function(){
		var sn = $('input[name="sn"]').val();
		//
		var rs = $('input[name="pay_c"]');
		if(rs.is(':checked')){
			window.location.href='/bd/wxpay/'+sn+'.htm';//正式
			//analogsPay(sn); test
		}else{
			alert('请选择一种支付方式！')
		}
	})
});


var wxpay = function(){
	var money = $('input[name="money"]').val();
	var greade = $('input[name="greade"]').val();
	var url = $('input[name="url"]').val();
	$.post('/vip/save_logs.ajax',{'money':money,'greade':greade},function(json){
		if(json.state){
			if('wxpay' == json.param){
				window.location.href='/vip/pay/'+json.message+'.htm?url='+url;
			}
		}
	},'json');
}

/**模拟支付*/
var analogsPay = function(sn){
	var url = $('input[name="http_url"]').val();
	$.post('/analog_pay/'+sn+'.ajax',function(json){
		if(json.state){
			alert(json.message);
			if(url != null && url !='' && url !='undefined'){
				window.location.replace(url);
			}else{
				window.location.href='/ryvip/index.htm';
			}
			//window.location.replace(document.referrer);
		}else{
			alert(json.message);
		}
	},'json');
}


/**根据价格判断vip等级*/
var vipRank = function(money){
	$.post('/vip/judge_vip.ajax',{"money":money},function(json){
		if(json.state){
			$('.navs32_p3').find('i').text(json.message);
			$('input[name="greade"]').val(json.param);
		}else{
			$('.navs32_p3').find('i').text('VIP0');
		}
	},'json');
}


/**滚动加载余额明细*/
var ajaxLoad = function(page){
	$.post('/vip/querybalanceByUser.ajax',{'page':page},function(json){
		if(json.state){
			$.each(json.param,function(n,value){
				/*alert(value.add_time+'==========='+new Date(value.add_time.replace(/-/g, "/")).getTime())*/
				var li = document.createElement('li');
				var p = document.createElement('p');
				var i =  document.createElement('i');
				var span = document.createElement('span');
				var nub = document.createElement('nub');
				var p1 = p.cloneNode(true);
				
				i.setAttribute('class','navs40_i');
				
				if(value.method == 'WXPAY' || value.method=='CARD'){
					p.innerHTML='充值';
					i.innerHTML='+'+moneyFormat(value.amount);
				}else if(value.method == 'GST'){
					p.innerHTML='消费';
					i.innerHTML='-'+moneyFormat(value.amount);
				}else if(value.method == 'REFUND'){
					p.innerHTML='退款';
					i.innerHTML='+'+moneyFormat(value.amount);
				}else if(value.method == 'REWARD'){
					p.innerHTML='打赏';
					i.innerHTML='+'+moneyFormat(value.amount);
				}else{
					p.innerHTML='提现';
					i.innerHTML='-'+moneyFormat(value.amount);
				}
				p.setAttribute('class','navs40_p1');
				p.appendChild(i);
				li.appendChild(p);
				nub.innerHTML=moneyFormat(value.current_balance);
				
				span.setAttribute('class','navs40_span');
				span.innerHTML='余额：';
				span.appendChild(nub);

				p1.setAttribute('class','navs40_p2');
				p1.innerHTML = value.add_time;
				p1.appendChild(span);
				
				li.appendChild(p1);
				$('#ajaxLoad').append(li);
			});
		}else{
			alert(json.message);
		}
	},'json');
}