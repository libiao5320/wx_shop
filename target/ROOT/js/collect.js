/**杨鑫start**/
$.ajaxSetup({async:false});
var collectAJax = function(page,url){
	var img_url = $('input[name="img_url"]').val();
	var _value = $('input[name="sige"]').val();
	var _this = $('#onloadAjAx li:last');
	var falg = true;
	$.ajax({
		url : url,
		data : {'pageNo':page},
		type : 'post',
		dataType : 'json',
		success : function(json){
			if(json.state){
				var data = json.param;
				if(data != null && data != '' && data != 'undefined'){
					var _li = _this.clone(true);
					var eachData = data.collectList;
					$.each(eachData,function(n,value){
						if(_value != null && _value !='' && _value != 'undefined'){
							_li.find('div .xzkk_lb input').val(value.id);
							_li.find('.navs41_d').children('a').attr('href','/goods/detail.htm?goodsId ='+value.good.goodsId);
							_li.find('.navs41_d').div('navs41_left').find('img').attr('src',img_url+value.good.goodsImage);
							var _right = _li.find('.navs41_d').find('navs41_right');
							_right.find('.navs41_right_p1').text(value.good.goodsName);
							_right.find('.navs41_right_p2').find('span').text(value.good.goodsName);
							var price = '';
							switch(_value){
								case '0' : price = parseFloat(value.good.goodsVip0Price/100).toFixed(2);break;
								case '1' : price = parseFloat(value.good.goodsVip1Price/100).toFixed(2);break;
								case '2' : price = parseFloat(value.good.goodsVip2Price/100).toFixed(2);break;
								case '3' : price = parseFloat(value.good.goodsVip3Price/100).toFixed(2);break;
								case '4' : price = parseFloat(value.good.goodsVip4Price/100).toFixed(2);break;
							}
							_right.find('.navs41_right_p3').find('span').find('lable').text(price);
							_right.find('.navs41_right_p3').find('.sc_mid01').text(value.good.goodsVip3PriceDollar);
							_right.find('.navs41_right_p3').find('.navs40_a').attr('href','/goods/goodsOrder/'+value.good.goodsId+'.htm');
						}else{
							_li.find('div .xzkk_lb input').val(value.id);
							var _div = _li.find('.navs41_d');
							var _left = _div.find('.navs41_left');
							var _right = _div.find('.navs41_right');
							_div.children('a').attr('href','/store/detail.htm?id='+value.store.storeId);
							_left.find('img').attr('src',img_url+value.store.storeLabel);
							_right.find('.navs4_p1').find('i').text(value.store.storeName);
							_right.find('.navs41_right_p2').find('a:eq(0)').text(value.store.fjLandmark).siblings().text(value.store.storeDescribe).attr('href','/store/detail.htm?id='+value.store.storeId);
							var length = value.store.storeServicecredit;
							if(length != null && length != '' && length != 'undefined'){
								length = Number(length);
							}
							var i = 0;
							while(i < 5){
								if(i < length){
									_right.find('.navs41_right_p7').append('<i class="navs13_i"></i>');
								}else{
									_right.find('.navs41_right_p7').append('<i class="navs13_i"></i>');
								}
								i++;
							}
							/*for(var i =0;i < length;i++){
								_right.find('.navs41_right_p7').append('<i class="navs13_i"></i>');
							}
							for(var j = 0;j<(5-length);j++){
								_right.find('.navs41_right_p7').append('<i class=""></i>')
							}*/
							_right.find('.navs41_right_p7').find('span').text(value.store.storeServicecredit+'分');
						}
						$('#onloadAjAx').append(_li);
					})
				}else{
					falg = false;
				}
			}
		},error : function(){
			
		}
	})
	return falg;
}
/**end**/