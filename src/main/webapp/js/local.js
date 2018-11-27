/*
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */


var allProvinces = null; //所有省份列表
var allCities = null; //所有城市列表
var allAreas = null; //所有区域列表
var allHot = null; //所有热门城市列表
/*
 * 获取页面函数 
 */
var getAll = function() {
		getProvinces(); //发送全部省份
		getCity(); //发送全部城市
		getArea(); //发送所有的区域
		showCity();
		showArea();
		showCityHot();

		$('.pureCity').click(function() {
			var activecity = $(this).html();
			$('.cityleft').data('activeCity', activecity);
			$(".citys").hide();
			$(".index_ceng").hide();
			
		});
		$('.areasAll').click(function() {
			var activecity = $(this).html();
			$('.cityleft').data('activeCity', activecity);
			$(".citys").hide();
			$(".index_ceng").hide();
		});
		$('.nowcity').click(function() {
			var activecity = $(this).html();
			$('.cityleft').data('activeCity', activecity);
			$(".citys").hide();
			$(".index_ceng").hide();
		});
	}
	/*
	 * 获取选择城市函数
	 */
var getActiveCity = function() {
		return $('.cityleft').data('activeCity');
	}
	/*
	 * 获取所有的省份
	 * return 所有省份数组
	 */
var getProvinces = function() {
		$.ajax({
			type: "get",
			url: '/js/queryAllProvinces.js',
			async: false,
			dataType: "json",
			success: function(data) {
				allProvinces = data.provinces;
				//$("body").data("allProvinces", allProvinces);
				//viewAllProvince(1);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//alert("获取省份信息错误"+textStatus);
			}
		});
	}
	/*
	 * 获取所有城市
	 * return 所有城市数组
	 */
var getCity = function() {
	$.ajax({
		type: "get",
		url: 'js/queryCities.js',
		async: false,
		dataType: "json",
		success: function(data) {
			allCities = data.cities;
			//$("body").data("CitysAll", data);

		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("获取所有城市信息错误："+textStatus);
		}
	});
}

/*
 * 获取所有区域列表
 * return 所有区域
 */
var getArea = function() {
		$.ajax({
			type: "get",
			url: "js/queryAllAreas.js",
			async: false,
			dataType: "json",
			success: function(data) {
				allAreas = data.areas;
				//$("body").data("allCountys", data.areas);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//alert("获取区信息错误"+textStatus);
			}
		});
	}
	/*
	 * 获取所有区域列表
	 * return 所有区域
	 */
var getHot = function() {
		$.ajax({
			type: "get",
			url: "js/queryAllAreas.js",
			async: false,
			dataType: "json",
			success: function(data) {
				allHot = data.areas;
				//$("body").data("allCountys", data.areas);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//alert("获取热门区域信息错误"+textStatus);
			}
		});
	}
	/*
	 * 显示获得的所有城市
	 */
var showCity = function() {
	var regs = /^[A-Z]$/;
	var character = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
	var height = document.body.scrollHeight / 55;

	$.each(character, function(i) {
		$('.listHref').append("<p style='height:" + height + "px'><a href='#" + character[i] + "'>" + character[i] + "</a></p>");

	});



	$.each(character, function(i) {
		$('#allCity').append("<div class='citylbs' id='" + character[i] + "'>" + character[i] + "</div>");

		$.each(allCities, function(e, city) {
			if (city.cityShortPY.substr(0, 1) == character[i]) {
				$("#" + character[i] + "").after("<div style='padding: 10px;' class='pureCity'>" + city.name + "</div>");
			}
		});

	});
}

/*
 * 显示当前城市的区
 */
var showArea = function() {
	$.each(allAreas, function(i, area) {
		var newcity = $('.nowcity').html();
		if (area.cityName == newcity) {
			$('.areas').after("<div class='areasAll' >" + area.areaName + "</div>");
		}
	});

}

/*
 * 热门城市列表
 */
var showCityHot = function() {
	$.each(allCities, function(i, area) {
		if (area.hotCity == true) {
			$('#hotcitylbs').after("<div class='areasAll' >" + area.name + "</div>");
		}

	});
}