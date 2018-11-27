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

	var y = y ? y : getStorage()[0];
	var x = x ? x : getStorage()[1];
	var recodeSize = recodeSize ? recodeSize : 0;
	var scroll = scroll ? scroll : 0;
	var goodsListDiv = $("#js_goodList");
	var vip = $("#vipRank").val();
	var jsonData = flashGoodGrep(recodeSize); // 获取最新数组

	jsonData["map[x]"] = x;
	jsonData["map[y]"] = y;

	if (scroll == 0)
		goodsListDiv.empty();// 如果不是scroll都必须goodsListDiv清空
	$
			.post(
					"/goods/list.ajax",
					jsonData,
					function(data) {
						var goods = data.list;
						// alert(JSON.stringify(goods));
						var goods_url = $('input[name="goods_url"]').val();
						var __html = "";
						var vipPrice;

						for (var i = 0; i < goods.length; i++) {
							var good = goods[i];

							vipPrice = good["goods_vip0_price"];
							/**
							 * 
							 * 
							 * switch (vip) { case "0": vipPrice =
							 * good["goods_vip0_price"]; break; case "1":
							 * vipPrice = good["goods_vip1_price"]; break; case
							 * "2": vipPrice = good["goods_vip2_price"]; break;
							 * case "3": vipPrice = good["goods_vip3_price"];
							 * break; case "4": vipPrice =
							 * good["goods_vip4_price"]; break; }
							 */
							// 好评率
							var goodEval = (parseInt(good['goodCount']) + parseInt(good['badCount'])) > 0 ? ((parseInt(good['goodCount']) + 50) / (parseInt(good['goodCount'])
									+ parseInt(good['badCount']) + 50)) * 100
									: 0;

							var goodPrice = parseInt(good["goods_price"]) / 100;
							vipPrice = vipPrice / 100;
							var distanceI;
							var distance;
							var goodsVip3PriceDollar = 0;
							if (good["goods_vip3_price"] != 'undefined'
									&& good["goods_vip3_price"] != null
									&& good["goods_vip3_price"] != '') {
								goodsVip3PriceDollar = good["goods_vip3_price"] / 100;
							}
							if (good["distance"] != 'undefined'
									&& good["distance"] != null
									&& good["distance"] != '') {

								distanceI = parseInt(good["distance"]);

								if (distanceI >= 1000) {
									distance = (parseFloat(good["distance"]) / 1000)
											.toFixed(2)
											+ "km";
								} else {
									distance = parseFloat(good["distance"])
											.toFixed(0)
											+ "m";
								}
							} else {
								distance = "0m";
							}
							
							var landmark = good['landmark'];
							if(landmark != undefined && landmark != null){//截取多余的字符
								if(landmark.length >= 7)
									landmark = landmark.substr(0,5)+'...';
							}else
								landmark = '';
							
							__html += ' <li title="'
									+ good["goods_name"]
									+ '"> <a href="/goods/detail.htm?goodsId='
									+ good["goods_id"]
									+ '"> <div class="navs4_left"> <p> <img src="'
									+ goods_url
									+ good["goods_image"]
									+ '" alt="" title=""> </p> </div> <div class="navs4_right"> <p class="navs4_p1"><i>'
									+ (good["goods_name"] ? good['goods_name'] : '')
									+ '</i></p> <p class="navs4_p2"><i>好评率: '
									+ goodEval.toFixed(0)
									+ '%</i><span>'
									+ landmark
									+ '<'
									+ distance
									+ '</span></p><p class="navs4_p3">￥'
									+ vipPrice
									+ '<i class="sc_mid01">￥'
									+ goodsVip3PriceDollar
									+ '</i><span>已抢购'
									+ (good["goods_salenum"] ? good["goods_salenum"]
											: 0)
									+ '</span></p> </div> </a> </li>'
								
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
						} else {
							if (goods.length < PAGE_SIZE) {
								isNull(goodsListDiv);
							
							} else {
								scrollSign = true;
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
}
/*
 * 滑动列表时异步加载列表数据
 * 
 */
var scroll = function(y, x) {
	var x = getStorage()[1];
	var y = getStorage()[0];
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
		var id = $(this).attr("classId");
		if (id == "" || id == null || id == 'undefined') {
			return false;
		}
		$('#classId').val(id);
		$(".nav_h3_d1").html($(this).text());
		goodlist(0);

	});

	$('.classifyParent').click(function() {
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

		$(this).attr("noopsyche", $(this).attr("noopsyche"));
		$('.smartcls').attr("noopsyche", $(this).attr("noopsyche"));
		$('.smartcls').html($(this).text());

		goodlist(0);
	});
}

var isNull = function(e) {

	if (!e.find(".zwsp")) {
		var _html = '<p class="zwsp">亲早来啊，商品还没有上架呢</p><p class="zwsps"></p>';
		e.append(_html);
	}

}

var initGoodGroupBuy = function() {

	$
			.post(
					"/goods/goodGroupBuy.ajax",
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
						
						/****************私人服务****************/
						var _srfw = '<li ><a href="javascript:;"><span></span>私人服务</a> </li>';
						$(".area-l").append(_srfw);
						$(".area").append(gbul);
						
						var srfwul = $('<ul></ul>');
						srfwul.addClass("area-r");
						$(".area").append(srfwul);
						
						var srfwli = ' <li><a href="/techie/list.htm">全部</a> </li>';
						srfwul.append(srfwli);
						/****************私人服务****************/
						
						classClick();
					}, "json");
}