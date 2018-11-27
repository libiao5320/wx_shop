
/*
 *页面排版公共类、
 *fun TypeConstruct
 *fun appendConstruct
 *
 *作者：周降临
 *邮箱：zhoutq@outlook.com 
 *
 */
var Generation=function(){
	/*
	 * 总生成排版函数
	 * @param　parent   　     //对象名称，请传入#id 或者.class　　{'#w'} ,{'.w'}
	 * @param　data                //
	 * 轮转data{｛url:http://www.beiduo.cc/123,src：http://www.beiduo.cc/logo.png｝}
	 * 列表data{ {url:http://www.baidu.com,src:http:ww.baidu.com1.png,name:产品名称,Evaluation:98,street:八一路，Distance：５００ｍ，introduce：特价商品，price：１５９，cost：２００，sale：４５}}
	 * 链接data{{ｕｒｌ：http://www.baidu.com，ｎａｍｅ：去充值}}
	 * 链接图片data {{ｕｒｌ：http://www.baidu.com，ｎａｍｅ：去充值，ｓｒｃ：ｈｔｔｐ：／／ｗｗｗ．ｂａｉｄｕ．ｃｏｍ/11.png}}
	 * 数字data{{1}，｛个人中心｝}
	 * @param    type               //1 轮转图　２产品列表　３链接　４链接图片   5数字/文字　６图片  7商家列表    8评价列表   9消费余额明细列表     10订单列表
	 */
	this.TypeConstruct=function(parent,data, type){
		$(''+parent+'').html('');        //清空父类ｄｏｍ下面所有内容
				switch(type){
				case 1:
						for(da in data){
							$(''+parent+'').append('<li><a href='+da['url']+' ><img src='+da['src']+' alt="" title=""></a></li>');
						}
					break;
				case 2:
					for(da in data){
						$(''+parent+'').append('<li><a href='+da['url']+'><div class="navs4_left"><p><img src='+da['src']+' alt="" title=""> </p></div><div class="navs4_right"><p class="navs4_p1"><i>'+da['street']+'</i><span><'+da['Distance']+'</span></p><p class="navs4_p2">'+da['introduce']+'</p><p class="navs4_p3">¥'+da['price']+'<i>¥'+da['cost']+'</i><span>已抢购'+da[' sale']+'</span></p> </div></a></li>');			
						}
					break;
				case 3:
					for(da in data){
						$(''+parent+'').append('<li><a href='+da['url']+' ><img src='+da['src']+' alt="" title=“”></a></li>');
					}
					break;
				case 4:
					for(da in data){
						$(''+parent+'').append('  <a href="'+da['url']+'"><img src="'+da['src']+'" alt="" title=""></a>');
					}
					break;
				case 5:
					for(da  in data){
					 	$(''+parent+'').append('' +da[0]+'');
					}
					break;
				case 6:
			         for(da in data){
			        	   $(''+parent+'').append('<img src="' +da[0]+'" alt="" title="">');
			         }
					break;
				case 7:
					for(da in data){
						$(''+parent+'').append('<li><a href="'+da['url']+'"><div class="navs4_left"><p><img src="'+da['img']+'" alt="" title=""></p></div><div class="navs4_right"><p class="navs4_p1 navss11_p1">'+da['name']+'</p><p class="navss11_p2">好评率: <i>'+da['evaluate']+'%</i></p><p class="navs4_p3 navss11_p3"><i>'+da['class']+'</i><span>'+da['evaluate']+'</span></p></div></a></li>');
					}
					break;
				case 8:
					for(da in data){
					var liHtml=null;
					var liNav=null;
					if(da['evaluateImg'])
					{
					     for(temp in da['evaluateImg'])
					    	 {
					    	     liHtml=+'<li><a><img src="'+temp['img']+'" alt="" title=""></a></li>';
					    	 }
					}
					if(da['evaluate'])
					{
						for(var i=0;i<da['evaluate'];i++)
							{
							    liNav=+'<i class="navs13_i"></i>';
							}
					}
						$(''+parent+"").append('<li class="navs13_li"><div class="navs13_left"><img src="'+da['useImg']+'" title="" alt=""></div><div class="navs13_right"><p class="navs13_right_p1">'+da['phone']+' <span>'+da['Date']+' </span></p><p class="navs13_right_p2">"'+liNav+'"</p></div><p class="navs13_p1">'+da['introduce']+'</p><ul>"'+liHtml+'"</ul></li>');
					}
					break;
				case 9:
					for(da in data)
						{
						if(da['paytype']=='xf')
							{
							$(''+parent+'').append('<li><p class="navs40_p1">消费<i class="navs40_i">'+da['num']+'</i></p><p class="navs40_p2">2015-11-20<span class="navs40_span">余额: <nub>0</nub></span></p></li>');
							}
						else if(da['paytpe']=='wxpay')
							$(''+parent+'').append('<li><p class="navs40_p1">充值<i class="navs40_i navs40_is">'+da['num']+'</i></p><p class="navs40_p2">'+da['Date']+'<span class="navs40_span">余额: <nub>'+da['balance']+'</nub></span></p></li>');
						}
					break;
				}
	}
	/*
	 * 从后添加排版函数       appendChild
	 * @param　parent   　     //对象名称，请传入#id 或者.class　　{'#w'} ,{'.w'}
	 * @param　data                //
	 * 轮转data{｛url:http://www.beiduo.cc/123,src：http://www.beiduo.cc/logo.png｝}
	 * 列表data{ {url:http://www.baidu.com,src:http:ww.baidu.com1.png,name:产品名称,Evaluation:98,street:八一路，Distance：５００ｍ，introduce：特价商品，price：１５９，cost：２００，sale：４５}}
	 * 链接data{{ｕｒｌ：http://www.baidu.com，ｎａｍｅ：去充值}}
	 * 链接图片data {{ｕｒｌ：http://www.baidu.com，ｎａｍｅ：去充值，ｓｒｃ：ｈｔｔｐ：／／ｗｗｗ．ｂａｉｄｕ．ｃｏｍ/11.png}}
	 * 数字data{{1}，｛个人中心｝}
	 * @param    type                //1 轮转图　２产品列表　３链接　４链接图片   5数字/文字　６图片  7商家列表    8评价列表   9订单列表
	 */
	
	this.appendConstruct=function(parent,data, type){
		
		switch(type){
		case 1:
				for(da in data){
					$(''+parent+'').append('<li><a href='+da['url']+' ><img src='+da['src']+' alt="" title=""></a></li>');
				}
			break;
		case 2:
			for(da in data){
				$(''+parent+'').append('<li><a href='+da['url']+'><div class="navs4_left"><p><img src='+da['src']+' alt="" title=""> </p></div><div class="navs4_right"><p class="navs4_p1"><i>'+da['street']+'</i><span><'+da['Distance']+'</span></p><p class="navs4_p2">'+da['introduce']+'</p><p class="navs4_p3">¥'+da['price']+'<i>¥'+da['cost']+'</i><span>已抢购'+da[' sale']+'</span></p> </div></a></li>');			
				}
			break;
		case 3:
			for(da in data){
				$(''+parent+'').append('<li><a href='+da['url']+' ><img src='+da['src']+' alt="" title=“”></a></li>');
			}
			break;
		case 4:
			for(da in data){
				$(''+parent+'').append('  <a href="'+da['url']+'"><img src="'+da['src']+'" alt="" title=""></a>');
			}
			break;
		case 5:
			for(da  in data){
			 	$(''+parent+'').append('' +da[0]+'');
			}
			break;
		case 6:
	         for(da in data){
	        	   $(''+parent+'').append('<img src="' +da[0]+'" alt="" title="">');
	         }
			break;
		case 7:
			for(da in data){
				$(''+parent+'').append('<li><a href="'+da['url']+'"><div class="navs4_left"><p><img src="'+da['img']+'" alt="" title=""></p></div><div class="navs4_right"><p class="navs4_p1 navss11_p1">'+da['name']+'</p><p class="navss11_p2">好评率: <i>'+da['evaluate']+'%</i></p><p class="navs4_p3 navss11_p3"><i>'+da['class']+'</i><span>'+da['evaluate']+'</span></p></div></a></li>');
			}
			break;
		case 8:
			for(da in data){
				var liHtml=null;
				if(da['evaluateImg'])
				{
				     for(temp in da['evaluateImg'])
				    	 {
				    	     liHtml=+'<li><a><img src="'+temp['img']+'" alt="" title=""></a></li>';
				    	 }
				}
				if(da['evaluate'])
				{
					for(var i=0;i<da['evaluate'];i++)
						{
						    liNav=+'<i class="navs13_i"></i>';
						}
				}
				$(''+parent+"").append('<li class="navs13_li"><div class="navs13_left"><img src="'+da['useImg']+'" title="" alt=""></div><div class="navs13_right"><p class="navs13_right_p1">'+da['phone']+' <span>'+da['Date']+' </span></p><p class="navs13_right_p2">"'+liNav+'"</p></div><p class="navs13_p1">'+da['introduce']+'</p><ul>"'+liHtml+'"</ul></li>');
			}
			break;
		case 9:
			for(da in data)
				{
				if(da['paytype']=='xf')
					{
					$(''+parent+'').append('<li><p class="navs40_p1">消费<i class="navs40_i">'+da['num']+'</i></p><p class="navs40_p2">2015-11-20<span class="navs40_span">余额: <nub>0</nub></span></p></li>');
					}
				else if(da['paytpe']=='wxpay')
					$(''+parent+'').append('<li><p class="navs40_p1">充值<i class="navs40_i navs40_is">'+da['num']+'</i></p><p class="navs40_p2">'+da['Date']+'<span class="navs40_span">余额: <nub>'+da['balance']+'</nub></span></p></li>');
				}
			break;
			
		}

	}
}



