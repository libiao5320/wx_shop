/*
 * 筛选json参数
 */
var goodGrep = {
	"map[classId]" : null, // 类别
	"map[pageNo]" : null, // 页数
	"map[noopsycheClass]" : null, // 1智能分类,2评价最高,3销量最高
	"map[nearBy]" : false, // 附近 false true
	"map[search]" : '' // 搜索词
}
var page = 1;var isAjax = true;
$(function(){
	$.ajaxSetup({
		async : false
	});
})
/*
 * 更新筛选json参数函数 @param recodeSize //目前列表页中的列表数量 return //组合查询的json数据
 */
var flashGoodGrep = function(recodeSize, noopsyche) {
	var pageNo = parseInt(recodeSize / PAGE_SIZE)
			+ (recodeSize % PAGE_SIZE > 0 ? 1 : 0);
	pageNo += 1;
	var goodsListDiv = $("#js_goodList");
	if (pageNo == 0) {
		goodsListDiv.empty();
	}
	goodGrep["map[pageNo]"] = pageNo;
	goodGrep["map[classId]"] = $("#classId").val();
	goodGrep["map[noopsycheClass]"] = $('.smartcls').attr("noopsyche");
	goodGrep["map[nearBy]"] = $("#fujinvalue").val();
	goodGrep["map[search]"] = $('#search').val();
	return goodGrep;
}
/*
 * 商品列表实现函数 @param recodeSize 目前列表页中的列表数量
 * 
 */

var goodlist = function(y, x, recodeSize, scroll) {
	
	var falg = true;
	
	var y = y ? y : getStorage()[0];
	var x = x ? x : getStorage()[1];
	var recodeSize = recodeSize ? recodeSize : 0;
	var scroll = scroll ? scroll : 0;
	var goodsListDiv = $("#js_goodList");
	var jsonData = flashGoodGrep(recodeSize); // 获取最新数组

	jsonData["map[x]"] = x;
	jsonData["map[y]"] = y;

	if (scroll == 0)
		goodsListDiv.empty();// 如果不是scroll都必须goodsListDiv清空
	$
			.post(
					"/techie/list.ajax",
					jsonData,
					function(data) {
						var goods = data.list;
						var __html = "";						
						
						for (var i = 0; i < goods.length; i++) {
							var good = goods[i];

							var landmark = good['fjLandmark'];
							if (landmark != undefined && landmark != null) {// 截取多余的字符
								if (landmark.length >= 7)
									landmark = landmark.substr(0, 5) + '...';
							} else
								landmark = '';

							__html += '<li><a href="/techie/detail/'
									+ good['techieId']
									+ '.htm"><div class="srjk_fwlb_d"><div class="navs4_left"><p><img src="'
									+ TECHIE_IMG_URL
									+ good['img']
									+ '" alt="" title=""></div><div class="navs4_right"><p class="navs4_p1 navss11_p1">'
									+ (good['techieName'] ? good['techieName'] :'')
									+ '<span class="zy">'
									+ (good['className'] ? good['className'] :'')
									+ '</span><span class="jg"></span></p><p class="navss11_p2">'
									+ (good['unit'] ? good['unit'] :'')
									+ '</p><p class="navs4_p3 navss11_p3"><span class="hua">'
									+ good['dsTotalNum'] + '</span><span>'
									+ landmark + '</span></p></div></div><p class="srjk_fwlb_p">擅长：'+(good['goodAt'] ? good['goodAt'] :'')+'</p></a></li>'

						}
						if (goods.length == 0) {
							if (jsonData["map[pageNo]"] == 0) {
								isNull(goodsListDiv);
							} else {

								if (scroll == 0) {
									goodsListDiv.empty();// 如果不是scroll都必须goodsListDiv清空
								}
								// goodsListDiv.empty();
								isNull(goodsListDiv);
							}
							falg = false;
						} else {
							if (goods.length < PAGE_SIZE) {
							
								isNull(goodsListDiv);
								$(".nomore").show();
								falg = false;
							} else {
								scrollSign = true;
								$(".nomore").hide();
							}

							if (scroll == 0) {
								goodsListDiv.empty();// 如果不是scroll都必须goodsListDiv清空
							}
							goodsListDiv.append(__html);
							
						}
					}, "json").error(function(a, b, c) {
				alert("error" + a.status);
				alert("error" + a.readyState);
				alert("error" + b);
			});
	
	return falg;
}
/*
 * 滑动列表时异步加载列表数据
 * 
 */
var scroll = function(y, x) {
	var x = getStorage()[1];
	var y = getStorage()[0];
	var top = $(window).scrollTop();
	var doheight = $(document).height();
	var win_height=$(window).height();
	if(isAjax){
		if(top >= (doheight-win_height)){
			if(page == 1){
				goodlist(y, x, 0);
			}else{
				isAjax = goodlist(y, x, $("#js_goodList li").length, 1);
			}
			page++;
		}
	}
	
	/**
	if (scrollSign && $(this).scrollTop() <= 0) {
		scrollSign = false;
		goodlist(y, x, 0);
		return;
	}
	var bottonHeight = $(window).height() + $(this).scrollTop();
	var orderHeight = $("#js_goodList").height() + 30;
	if (!scrollSign && bottonHeight >= orderHeight) {
		scrollSign = true;
		return;
	}
	if (scrollSign && bottonHeight >= orderHeight) {
		scrollSign = false;
		goodlist(y, x, $("#js_goodList li").length, 1);
		// goodlist($("#js_goodList li").length,1);
		// //下拉滚动不清空goodsListDiv，第二个参数传入1时，不刷新
	}
	**/
};
/*
 * 绑定点击类目
 * 
 */
var classClick = function() {
	
	$('.area-l li').click(function() {
		$(this).addClass('current').siblings('li').removeClass('current');
		var index = $(this).index();
		$('.area-r').eq(index - 1).show().siblings('.area-r').hide();
	});
	$('.area-r li').click(function() {
		$('.sort-con-c').slideUp(500);
		$('.sortbg').hide();
	});
	$('.area-r').eq(0).show();

	$('.classify').click(function() {
		page = 1;isAjax = true;
		var id = $(this).attr("classId");
		if (id == "" || id == null || id == 'undefined') {
			return false;
		}
		$('#classId').val(id);
		$(".nav_h3_d1").html($(this).text());
		goodlist(0);

	});

	$('.classifyParent').click(function() {
		page = 1;isAjax = true;
		var id = $(this).attr("classParentId");
		if (id == "" || id == null || id == 'undefined') {
			return false;
		}

		$('#classId').val(id);
		$(".nav_h3_d1").html($(this).attr("classParentName"));
		goodlist(0);
	});

}

/*
 * 绑定search输入点击目录
 * 
 * var searchEnter=function(){ $("#search").keypress(function(event){ //绑定搜索事件
 * goodlist(0); $('.head').show(); $('.heads').hide(); $('.heads_ceng').hide();
 * }); }
 */
/*
 * 绑定search输入点击目录
 */
var searchBtn = function() {
	$("#searchBtn").on("click", function() { // 绑定搜索事件

		goodlist(0);
		$('.head').show();
		$('.heads').hide();
		$('.heads_ceng').hide();
	});
}
/*
 * 绑定附近点击事件
 */
var nearbyClick = function() {
	$('.fujin').on("click", function(event) {
		page = 1;isAjax = true;
		var tar = this;
		$(".near").text($(tar).find("a").text());
		$("#fujinvalue").val($(tar).attr("distanceE"));
		flashGoodGrep(0)

		goodlist(0);
	});

}
/*
 * 绑定智能搜索点击事件
 */
var noopsyche = function() {
	$('.cplb_lm_lis').on("click", function() {
		page = 1;isAjax = true;
		$(this).attr("noopsyche", $(this).attr("noopsyche"));
		$('.smartcls').attr("noopsyche", $(this).attr("noopsyche"));
		$('.smartcls').html($(this).text());

		goodlist(0);
	});
}

var isNull = function(e) {
		$(".nomore").show();
}

var initGoodGroupBuy = function() {

	$
			.post(
					"/techie/techieGroupBuy.ajax",
					null,
					function(data) {

						var list = data;

						for (var i = 0; i < list.length; i++) {
							var gc = list[i];
							// alert(gc["className"]);
							var __ss;
							if (gc) {

								if (gc["gc_index"] == 0) {
									__ss = '<li class="current"><a href="javascript:;"><span></span>'
											+ (gc["className"] ? gc["className"]
													: "") + '</a> </li>';
								} else {
									__ss = '<li ><a href="javascript:;"><span></span>'
											+ (gc["className"] ? gc["className"]
													: "") + '</a> </li>';

								}

								$(".area-l").append(__ss);

								var gbul = $('<ul></ul>');
								gbul.addClass("area-r");
								$(".area").append(gbul);

								// $(".area").css("height",
								// $(".area").css("height")+ 40 );
								//
								// $(".area-l").css("height",$(".area-l").css("height")+40);

								var groupbuyList = gc["groupbuyList"];

								if (groupbuyList && groupbuyList.length > 0) {
									for (var j = 0; j < groupbuyList.length; j++) {
										var gb = groupbuyList[j];
										if (gb) {
											if (j == 0) {
												var __sssAll = ' <li class="classifyParent" classParentId = "'
														+ gc["classId"]
														+ '" classParentName = "'
														+ gc["className"]
														+ '"><a href="javascript:;">全部</a> </li>';
												gbul.append(__sssAll);
											} else {
												var __sss = ' <li class="classify" classId = "'
														+ gb["classId"]
														+ '"><a href="javascript:;">'
														+ (gb["className"] ? gb["className"]
																: "")
														+ '</a> </li>';
												gbul.append(__sss);
											}
										}
									}
								}

							}

						}
						
						classClick();
					}, "json");
}