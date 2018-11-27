/**
 * 贝多产品列表页
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */

var productList=function(){
	var productName=null;                        //当前产品列表名称
	var allProductListName=null;                 //所有产品列表名
	var classification=null;　　　　                                     //智能分类列表
	var nearby=null;                             //附近列表
	var productInformationList=null;             //产品信息列表
	
	
	/*
	 * 界面标题显示方法
	 * param productName
	 * {妇幼保健}
	 */
	var show_productName=function(productName){
		$('.head .left_nav').html('');
		$('.head .left_nav').append('   <p class="top_txt">'+productName+'</p>');
	}

	/*
	 * 产品分类名列表显示方法
	 * param allProductListName
	 * ｛
	 * ｛name：美容美体，｛ｎａｍｅ：美容美体１，ｎａｍｅ：美容美体２，ｎａｍｅ：美容美体３｝｝,
	 * ｛ｎａｍｅ：妇幼保健，｛ｎａｍｅ：妇幼保健１，ｎａｍｅ：妇幼保健２，ｎａｍｅ：妇幼保健３｝｝,
	 * ｛ｎａｍｅ：理疗养生，｛ｎａｍｅ：理疗养生１，ｎａｍｅ：理疗养生２，ｎａｍｅ，理疗养生３｝｝,
	 * ｝
	 */
	var show_allProductList=function(allProductListName){
		//实现下拉列表显示文字
		$('.nav_h3_d.nav_h3_ds.nav_h3_d1').html('');
		$('.nav_h3_d.nav_h3_ds.nav_h3_d1').append(' <span>'+productName+'</span>');
		//显示下拉列表
		$('.cplb_lm.cplb_lm1').html('');
		$('.cplb_lm.cplb_lm1').append('<div class="cplb_lm_left"><ul><li class=""><a><span></span>全部分类</a> </li></ul></div>');
		var i=0;
		for(product in allProductList ){
			$('.cplb_lm_left ul').append(' <li class="cplb_li01"><a><span></span>'+product['name']+'</a> </li>');
			$('.cplb_lm.cplb_lm1').append('<div class="cplb_lm_right</ul></div>"><ul>');
			for(child_product in product[1]){
				$('.cplb_lm_right ul').append('<li class="cplb_li01"><a><span></span>'+child_product['name']+'</a> </li>');
			}
		    i=i+1;
		}
	}
	
	/*
	 * 界面标题
	 * param classification
	 * 
	 */
	var show_classification=function(classification){

		
	}
	/*
	 * 界面标题
	 * param nearby
	 * 
	 */
	var show_nearby=function(nearby){
		
	}
	/*
	 * 界面标题
	 * param productInformationList
	 * {
	 * {url:http://www.baidu.com,src:http:ww.baidu.com1.png,name:产品名称,Evaluation:98,street:八一路，Distance：５００ｍ，introduce：特价商品，price：１５９，cost：２００，sale：４５}
	 * }
	 */
	var show_productInformationList=function(productInformationList){
		$('.navs4.navs4_cplb ul').html('');
		for(product in productInformationList){
					$('.navs4.navs4_cplb ul').append(' <li><a href="'+product['url']+'"><div class="navs4_left"><p><img src="'+product['src']+'" alt="" title=""></p> </div><div class="navs4_right"><p class="navs4_p1"><i>'+product['name']+'</i></p><p class="navs4_p2"><i>好评率: '+product['Evaluation']+'%</i><span>'+guess['street']+'<'+guess['Distance']+'</span></p><p class="navs4_p3">¥'+guess['price']+'<i>¥'+guess['cost']+'</i><span>已抢购'+guess[' sale']+'</span></p></div></a> </li>');
	}
}
}
		