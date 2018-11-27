$(function(){
	$.ajaxSetup({async:false});
	
	/**根据输入的金额判断是否超过可提现金额*/
	$("input[name='amount']").bind('input propertychange',function(){
		var money = $('input[name="money"]').val();
		var amount = $(this).val();
		if(amount != null && amount !='' && amount !='undefined'){
			if(check(amount)){
				if(checkFloat(amount,$(this))){
					money = convert(money);
					amount = convert(amount);
					if(amount > money){
						alert("提现金额超出本次可提现金额，请重新输入金额");
							$(this).val('');
					}
				}else{
					$(this).val('');
				}
			}else{
				$(this).val('');
			}
		}
	});
	
	
	$('.handle').click(function(){
		$.post('/kiting/index.ajax',function(json){
			if(json.state){
				window.location.href='/kiting/kiting/'+json.param+'.htm';
			}else{
				alert(json.message);
			}
		},'json');
	});
	
	$('#kiting').click(function(){
		var amount = $('input[name="amount"]').val();
		if(amount != null && amount !='' && amount != 'undefined'){
			$.post('/kiting/save_kiting/'+amount+'.ajax',function(json){
				if(json.state){
					alert("申请已提交，请等待审核");
					//window.location.href="/kiting/handle.htm";
					window.location.href="/ryvip/index.htm";
				}else{
					alert(json.message);
				}
			},'json');
		}else{
			alert('提现金额不能为空，请重新输入。。');
		}
	});
	
	
	
	$('input[name="cardpassword"]').bind('input propertychange',function(){
		var val = $(this).val();
		if(val != null && val != '' && val != 'undefined'){
			$(this).parent().next().css('background','#f35353');
		}else{
			$(this).parent().next().css('background','gray');
		}
	});
	
	
	$('.navs31_a2').click(function(){
		var val = $(this).prev().find('input').val();
		if(val != null && val != '' && val !='undefined'){
			$.post('/ryvip/cardpay.ajax',{'card':val},function(json){
				if(json.state){
					if(confirm(json.message)){
						$('input[name="cardpassword"]').val('');
						window.location.reload();
					}
				}else{
					alert(json.message);
				}
			},'json');
		}
	});
})

var ajaxLoad = function(page){
	$.post('/kiting/record.ajax',{'page':page},function(json){
		if(json.state){
			$.each(json.param,function(n,value){
				var li = document.createElement('li');
				var p = document.createElement('p');
				var i =  document.createElement('i');
				var span = document.createElement('span');
				var nub = document.createElement('nub');
				var p1 = p.cloneNode(true);
				i.setAttribute('class','navs40_i');
				i.innerHTML=new Date(value.createdTime).Format('yyyy-MM-dd hh:mm:ss');
				p.setAttribute('class','navs40_p1');
				p.innerHTML="提现";
				p.appendChild(i);
				li.appendChild(p);
				
				nub.innerHTML=moneyFormat(value.money);
				
				span.setAttribute('class','navs40_span');
				span.innerHTML='-';
				span.appendChild(nub);

				p1.setAttribute('class','navs40_p2');
				if('AUDIT'==value.status){
					p1.innerHTML = '审核中';
				}else if('REJECT'==value.status){
					p1.innerHTML = '提现被拒绝';
				}else if('FAIL' == value.status){
					p1.innerHTML = '提现失败';
				}else{
					p1.innerHTML = '处理成功';
				}
				p1.appendChild(span);
				
				li.appendChild(p1);
				$('#ajaxLoad').append(li);
			});
			return true;
		}else{
			return false;
		}
	},'json');
}

