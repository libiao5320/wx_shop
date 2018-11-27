/*
 *author:Null
 *DATE:2013.5.24
 */

var PAGE_SIZE = 10;

$(function() {
	//头页登录-

	$(function() {
		$(".sort-con-c").css("max-height", $(window).height() - 150);
		$(".sstk").css("max-height", $(window).height() - 150);
		//$(".area-l,.area-r").css("max-height", $(".sort-con-c").height() - 10);
		$(".shrsli").css("max-height", $(window).height() - 150);
		$(".sortrow li").click(
				function() {
					var falg = false;
					var index = 0;
					$('.area-l li').each(function(n, value) {
						var _this = $(this);
						if (_this.hasClass('current')) {
							index = n;
							falg = true;
						}
					})
					if (!falg) {
						$('.area-l li').eq(1).addClass('current');
						$('.area-r').eq(0).show();
					}

					if ($(".sortbar").css("position") == "relative") {
						$("html,body").animate({
							scrollTop : $(".idxlist").offset().top
						}, 100);
					} else if ($(".sortbar").css("position") == "fixed") {
						$("html,body").animate({
							scrollTop : $(window).scrollTop()
						}, 100);
					} else {
						$("html,body").animate({
							scrollTop : $(".idxlist").offset().top
						}, 100);
					}
					var index = $(this).index();
					if ($(this).attr("class").indexOf("current") == -1) {
						$(this).addClass("current").siblings().removeClass(
								"current");
						$(this).addClass("cur")
						$(".sortrow").css("z-index", "4");
						$('.sortbar').addClass("fxd");
						$(".sortbg").show();
					} else {
						$(this).removeClass("current");
						$(".sortrow").css("z-index", "1");
						//$('.sortbar').removeClass("fxd");
						$(".sortbg").hide();
					}
					$(".sort-con-c").each(
							function() {
								if ($(this).index() == 1 && index == 0) {
									$(this).slideToggle(200).siblings(
											".sort-con-c").slideUp(100);
								}
								if ($(this).index() == 2 && index == 1) {
									$(this).slideToggle(200).siblings(
											".sort-con-c").slideUp(100);
								}
								if ($(this).index() == 3 && index == 2) {
									$(this).slideToggle(200).siblings(
											".sort-con-c").slideUp(100);
								}
							});
					$('.sortbar').animate({
						top : '0',
						left : '0',
					}, 100);
				});
		$(".sortbg").click(function() {
			$(this).hide();
			$(".sort-con-c").slideUp(200);
			$(".sort-con-c1").slideUp(200);
			$(".sort-con-c2").slideUp(200);
			$(".sort-con-c3").slideUp(200);
			$(".sortrow li").removeClass("current");
		});
		$(".shrsli li,.area-r li").click(function() {
			$(".sortbg").hide();
			$(".sort-con-c").slideUp(200);
			$(".sort-con-c1").slideUp(200);
			$(".sort-con-c2").slideUp(200);
			$(".sort-con-c3").slideUp(200);
			$(".sortrow li").removeClass("current");
		});

		$('.area-l li').click(function() {

			$(this).addClass('current').siblings('li').removeClass('current');
			var index = $(this).index();
			$('.area-r').eq(index - 1).show().siblings('.area-r').hide();
		});

	});

	$('.navs4_cplb').css('min-height', $(window).height() - 149);
	$('.fxtks_a').click(function() {
		$('.fxtk').hide();
	});
	/*$('.pay_lj') .click(function(){
		$('.pay_bt').slideDown("slow");
		$('.pay_bt1').slideDown("slow");
		$('.ceng3').show();
	});*/
	$('.pay_lj_yh').click(function() {
		$('.pay_bt3').slideDown("slow");
		$('.ceng3').show();
	});
	$('.ceng3').click(function() {
		$('.pay_bt').slideUp("slow");
		$('.pay_bt1').slideUp("slow");
		$('.ceng3').hide();
		$('.pay_bt3').slideUp("slow");
	});
	//生产订单红包取消
	$('.pay_bt3_no').click(function() {
		$('.pay_bt').slideUp("slow");
		$('.pay_bt1').slideUp("slow");
		$('.ceng3').hide();
		$('.pay_bt3').slideUp("slow");
	});
	$('.lb_s').click(
			function() {
				$(this).children('.hb_xz').addClass('hb_xzs').parents(
						'.pay_bt1_p2').closest('.pay_bt1_p2').siblings(
						'.pay_bt1_p2').find('.hb_xz').removeClass('hb_xzs');
				$(this).find('input').attr('checked', true);
				$(this).parents('.pay_bt1_p2').siblings('.pay_bt1_p2').find(
						'label').find('input').attr('checked', false);
			});
	$('.anbh').click(
			function() {
				$(this).addClass('anbhs').parents('.navs34_li').closest(
						'.navs34_li').siblings('.navs34_li').find('.anbh')
						.removeClass('anbhs');
			});

	$('.xz_gb').click(function() {
		$('.hb_d').hide();
		$('.hb_ds').hide();
		$('.hb_ceng').hide();

	});

	$('.classify').click(function() {
		$('.sort-con-c').hide();
		$('.sortbg').hide();

	});

	$('.classify a').click(function() {
		$('.sort-con-c').hide();
		$('.sortbg').hide();

	});

	$('.hb_ceng').click(function() {
		$('.hb_d').hide();
		$('.hb_ds').hide();
		$(this).hide();
	});
	/*$('.hb_d_a1') .click(function(){
		$('.hb_d').hide();
		$('.hb_ds').show();
	});*/
	$('.top_ss').click(function() {
		$('.head').hide();
		$('.heads').show();
		$('.heads_ceng').show();
		$('#search').focus();
	});
	$('.heads_ceng').click(function() {
		$(this).hide();
		$('.head').show();
		$('.heads').hide();
	});
	$('.navss5_a').click(function() {
		$('.tcbox').show();
	});
	$('.tcback').click(function() {
		$('.tcbox').hide();
	});
	//13_申请退款
	$('.labels2 input').each(function() {
		$(this).click(function() {
			$(this).siblings('.anbh1').addClass('anbhs1');
		});
	});

	//暂无示卡消费券
	$('.navss4').css('min-height', $(window).height() - 51);
	//1-2_申请融耀达人
	$('.labels1').each(
			function() {
				$(this).click(
						function() {
							$(this).addClass('labels1s').closest('.labels1')
									.siblings('.labels1').removeClass(
											'labels1s');
							$(this).find('.anbh1').addClass('anbhs1').closest(
									'.labels1').siblings('.labels1').find(
									'.anbh1').removeClass('anbhs1');
						});
			});

	$('.cplb_lm_left li').click(function() {
		$(this).addClass('cplb_lis').siblings('li').removeClass('cplb_lis');

	});
	$('.cplb_li01').click(
			function() {

				var index = $(this).index();
				$('.cplb_lm_right').eq(index - 1).show().siblings(
						'.cplb_lm_right').hide();
				$('.cplb_lm_left').css('min-height',
						$('.cplb_lm_right').height());
			});
	$('.cplb_lm_left li').first().click(function() {
		$('.cplb_lm_right').show();
		$('.cplb_lm_left').css('min-height', $('.cplb_lm_right').height());
	});

	$('.ss1').click(function() {

		$('.heads_ceng').hide();

	});

	/*********首页选择城市*************/
	$('.top_left').click(function() {
		$('.citys').slideToggle(300);
		$('.index_ceng').show();
	});
	$('.index_ceng').click(function() {
		$('.citys').slideUp(300);
		$('.index_ceng').hide();
	});

	/*********我的订单页面删除订单操作***********/
	$('.top_dlt').click(function() {
		var _this = $('.navs41_ul li');
		_this.toggleClass("navs41_ul_lis");
		$('.xzkk_bt').toggle();
		/**	if(!_this.hasClass('navs41_ul_lis')){
				var data = $('#form-data').serialize();
				if(confirm('你确认定要删除订单？')){
					deleteUserOrder(data);
				}else{
					
				}
			}**/
	});

	$('.xzkk_c').each(function() {
		var _this = $(this);
		if (_this.is(':checked')) {
			_this.prev().addClass('xzkk_is');
		} else {
			_this.prev().removeClass('xzkk_is');
		}
		pand();
	})

	$('body').on('click', '.xzkk_c', function() {
		$(this).prev().toggleClass('xzkk_is');
		pand();
	})

	$("#qxzl").click(function() {
		if (this.checked) {
			$(this).prev().addClass('xzkk_is');
			$(".xzkk_c").each(function() {
				this.checked = true;
				$(this).prev().addClass('xzkk_is');
			});
		} else {
			$(this).prev().removeClass('xzkk_is');
			$(".xzkk_c").each(function() {
				this.checked = false;
				$(this).prev().removeClass('xzkk_is');
			});
		}
	});

	$('.xzkk_bt_a').click(function() {
		var data = $('#form-data').serialize();
		var url = $(this).attr('url');
		deleteUserOrder(data, url);
	})

	/*********选择输了控制**********/
	function limitInput(o) {
		var value = o.value;
		var min = 1;
		var max = 100;
		if (parseInt(value) < min || parseInt(value) > max) {
			alert('输入错误');
			o.value = '';
		}
	}

});

/**
 * 当用户未登录的时候，直接重定向到用户中心进行登陆;
 */
function redirect(url) {
	window.location.href = url;
}

/**订单*****-------------------------*/
$(function() {
	/***查看input里的值，看是为空，不为空则自动计算价格**/
	var default_value = $('input[name="map[goodsNum]"]').val();
	if (default_value != null && default_value != ''
			&& default_value != 'undefined') {
		queryMoney(default_value);
	}

	/**js监控input输入改变事件**/
	$('input[name="map[goodsNum]"]').bind('input propertychange', function() {
		var value = $(this).val();
		if (value == null || value == '' || value == 'undefined') {
			value = 1;
		}

		if (!changeValue(value)) {
			$(this).val('1');
			value = 1;
		}
		queryMoney(value);
	});

	/**js监控input失去焦点事件**/
	$('input[name="map[goodsNum]"]').on('blur', function() {
		var sum = $(this).val();
		if (sum != null && sum != '' && sum != 'undefined') {
			if (!changeValue(sum)) {
				sum = 1;
				$(this).val('1');
			}
		} else {
			sum = 1;
			$(this).val('1');
		}
		queryMoney(sum);
	})

	/**pay_add*/
	$(".pay_add").click(function() {
		var num = $(this).prev();
		var sum = parseInt(num.val()) + 1;
		num.val(sum);
		if (!changeValue(sum)) {
			sum = 1;
			$(this).prev().val('1');
		}
		queryMoney(sum);
	})

	$(".pay_delete").click(function() {
		var num = $(this).next();
		var sum = parseInt(num.val()) - 1;
		num.val(sum);
		if (!changeValue(sum)) {
			sum = 1;
			$(this).next().val('1');
		}
		queryMoney(sum);
	})
})

var changeValue = function(value) {
	var min = 1;
	var max = 100;
	if (parseInt(value) > max) {
		alert('最多只能添加100件商品');
		return false;
	} else if (parseInt(value) < min) {
		return false;
	} else {
		return true;
	}
}

var deleteUserOrder = function(data, url) {
	if (data != null && data != '' && data != 'undefined') {
		$.ajax({
			url : url,
			data : data,
			type : 'post',
			dataType : 'json',
			success : function(json) {
				alert(json.message);
				if (json.state) {
					window.location.reload();
				}
			},
			error : function() {

			}
		})
	}
}

/*****/
var pand = function() {
	var falg = true;
	$('.xzkk_c').each(function() {
		if (!$(this).is(':checked')) {
			falg = false;
		}
	})
	if (falg) {
		$('.qxzl').prev().addClass('xzkk_is');
		if (!$('.qxzl').is(':checked')) {
			$('.qxzl').click();
			$('.qxzl').prev().addClass('xzkk_is');
		}
	} else {
		if ($('.qxzl').is(':checked')) {
			$('.qxzl').attr("checked", false);
			$('.qxzl').prev().removeClass('xzkk_is');
		}
	}
}