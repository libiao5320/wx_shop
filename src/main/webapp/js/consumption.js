$.ajaxSetup({async:false});

$(function(){
	$('#ajaxLoadDetail section').bind('click',function(){
		var url = $(this).attr('url');
		window.location.href=url;
	});
})


/**加载消费券信息*/
var onloadAJAX = function(page){
	var img_url = $('input[name="img_url"]').val();
	var falg = true;
	$.post('/ption/onload.ajax',{'page':page},function(json){
		if(json.state){
			if(json.param != null && json.param !='' && json.param !='undefined'){
				$.each(json.param,function(n,value){
					var section = document.createElement('section');
					var a = document.createElement('a');
					var img = document.createElement('img');
					var div = document.createElement('div');
					var h3 = document.createElement('h3');
					var p = document.createElement('p');
					
					section.setAttribute('class','navss1');
					
					a.setAttribute('class','navss1_p1');
					a.setAttribute('href','/ption/'+value.goodsId+'.htm?time='+value.paySn);
					img.src = img_url+value.goods_img;
					a.appendChild(img);
					
					div.setAttribute('class','navss1_p2');
					h3.innerHTML=value.goods_name;
					div.appendChild(h3);
					
					p.innerHTML='有效期：'+ value.tion_time.substring(0,value.tion_time.indexOf(' '));
					div.appendChild(p);
					a.appendChild(div);
					
					section.appendChild(a);
					document.getElementById('ajaxLoad').appendChild(section);
				});
			}else{
				falg = false;
			}
		}
	},'json');
	return falg;
}


var onloadAJAXDetial = function(page){
	var time  = $('input[name="time"]').val();
	var goodsId = $('input[name="goodsId"]').val();
	var img_url = $('input[name="img_url"]').val();
	var orderType = $('input[name="orderType"]').val();
	var falg = true;
	$.post('/ption/'+goodsId+'.ajax',{'page':page,'time':time,'orderType':orderType},function(json){
		if(json.param != null && json.param !='' && json.param !='undefined'){
			$.each(json.param,function(n,value){
				var $section = $('.navss3:last');
				var $section_1 = $section.clone(true);
				$section_1.attr('url','/order/detail/'+value.id+'.htm')
				$section_1.find('.navss1_p2').find('h3').text(value.goods_name).next().text('有效期：'+value.tion_time);
				var no = value.no.substring(0,4)+"&nbsp;&nbsp;"+value.no.substring(4,7)+"&nbsp;&nbsp;"+value.no.substring(7,10);
				$section_1.find('.navss3_p1').find('span').html(no);
				//$section_1.find('.navss3_p2').find('img').attr('src',img_url+value.goods_img);
				$section_1.find('.navss3_p2').find('div').find('img').attr('src','/uploads/ticket/'+value.qr_code);
				$('#ajaxLoadDetail').append($section_1);
			});
		}else{
			falg = false;
		}
	},'json');
	
	return falg;
}