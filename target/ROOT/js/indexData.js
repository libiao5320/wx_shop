/**
 * 贝多首页数据
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */

var indexData=function(){
var allData=null;                          //所有信息
var lbsList=[]; 						   //地址数据列表
var barnnerList=[];　	　　　　　                            //轮转图列表
var regimenList=[];                        //养生类目列表
var healthPersonList=[];                   //健康师列表
var activeList=[];                         //活动列表
var merchantList=[];                       //商家列表
var guessList=[];                          //猜你喜欢列表
var winner=['冠军','亚军','季军'];            //健康师排名列表
/*
 * 显示首页地址列表
 * ＠param 　　lbsList　数组
 * ｛｛name:长沙｝，｛name:北京｝，｛name:上海｝
 * */
var show_lbsList=function(lbsList){
	$('#top_left').html('');
	for(lbs in lbsList){
		 $('#top_left').append('<a>'+lbs['name']+'</a>');
	}
}
/*
 * 显示首页轮转图列表
 * ＠param　barnnerList
 * ｛｛url:http://www.beiduo.cc/123,src：http://www.beiduo.cc/logo.png｝｝
 * */
var show_barnnerList=function(barnnerList){
	$('.nav_ul').html('');
	for(barnner in barnnerList){
		$('.nav_ul').append('<li><a href='+barnner['url']+' <img src='+barnner['src']+' alt="" title=""></a></li>');
	}
}

/*
 * 显示首页养生类目列表
 * ＠param　regimenList
 * ｛
 * ｛url:http://www.beiduo.cc/123  ,  src：http://www.beiduo.cc/logo.png,name：妇幼保健｝，
 * ｛url:http://www.beiduo.cc/12４４  ,  src：http://www.beiduo.cc/logo１.png,name：美容美体｝
 * ｝
 * */
var show_regimenList=function(regimenList){  
	$('.navs  ul').html('');
	for(regimen in regimenList){
		$('.navs ul').append(' <li><a href='+regimen['url']+'><img src='+regimen['src']+' alt="" title=""><p>'+regimen['name']+'</p> </a></li>');
	}
}

/*
 * 显示首页健康师列表
 * ＠param　healthPersonList
 * ｛{url:http://www.baidu.com;}｝
 * */
var show_healthPersonList=function(healthPersonList){  
	$('.navs1 ul').html('');
	for(var i= 0,len=healthPersonList.length;i<len;i++){

	}
	$('.navs１ ul').append(' <li><a href=' + healthPersonList[i]['url'] + '><p class="navs1_p"><img src=' + healthPersonList[i]['src'] + ' alt="" title=""></p><p class="navs1_p1">' + healthPersonList[i]['name']+'</p><p class="navs1_p2">打赏:259</span> </p><span class="nav_ph nav_gj">' + winner[i] + '</span></a></li>'
	)
	;
}

/*
 * 显示首页活动列表
 * ＠param　activeList
 * ｛{url:http://www.baidu.com,src:http://www.baidu.com/logo.png}｝
 * */
var show_activeList=function(activeList){
	$('.navs2').html('');
	$('.navs2').append('<a class="" href='+activeList['url']+'><img src='+activeList['src']+' alt="" title=""></a>');
}

/*
 * 显示商家排行列表
 * ＠param　merchantList
 * ｛src:http://www.baidu.com/logo.png,ｕｒｌ：http://www.baidu.com｝
 * */
var show_merchantList=function(merchantList){  
	$('.navs3 .navs3_left').html('');
	$('.navs3 .navs3_right').html('');
	$('.navs3 .navs3_left').append('	<p><img src='+merchantList['src']+' title="" alt=""></p>');
	$('.navs3 .navs3_right').append('	  <a href='+merchantList['url']+'>去看看</a>');
}

/*
 * 显示猜你喜欢列表
 * ＠param　guessList
 * {url:http://www.baidu.com,src:http:ww.baidu.com1.png,name:产品名称,Evaluation:98,street:八一路，Distance：５００ｍ，introduce：特价商品，price：１５９，cost：２００，sale：４５}
 * */
var show_guessList=function(guessList){  
	$('.navs4 ul').html('');
	for(guess　in guessList){
		$('.navs4 ul').append('<li><a href='+guess['url']+'><div class="navs4_left"><p><img src='+guess['src']+' alt="" title=""> </p></div><div class="navs4_right"><p class="navs4_p1"><i>'+guess['street']+'</i><span><'+guess['Distance']+'</span></p><p class="navs4_p2">'+guess['introduce']+'</p><p class="navs4_p3">¥'+guess['price']+'<i>¥'+guess['cost']+'</i><span>已抢购'+guess[' sale']+'</span></p> </div></a></li>');
	}

}


$(function(){
	indexData.prototype=new Generation();
	indexData.prototype=new getData();
	indexData=new indexData();
	indexData.allData=indexData.get_getData();
	
});



}