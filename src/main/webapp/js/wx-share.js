$.ajaxSetup({async:false});

/**微信JSSDK功能*/
var sharePal = function(datas,type ,fn){
	var data = getWxData();
	var appid = data.appid;
	var noncestr = data.noncestr;
	var timestamp = data.timestamp;
	var	sign = data.sign;
	
	/**通过config接口注入权限验证配置*/
	wx.config({
		debug:false,
		appId: appid, // 必填，公众号的唯一标识
        timestamp: timestamp, // 必填，生成签名的时间戳
        nonceStr: noncestr, // 必填，生成签名的随机串
        signature: sign,// 必填，签名，见附录1
        jsApiList: 
        	[
        	 'checkJsApi','hideOptionMenu','showMenuItems','onMenuShareAppMessage','onMenuShareTimeline','scanQRCode','chooseImage','uploadImage'
        	 ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	/**通过error接口处理失败验证*/
	wx.error(function(res){
		//alert(res);
	});
	
	/**通过ready接口处理成功验证*/
	wx.ready(function(){
		 switch(type){
		 	case 'share':shareFired(datas);break;
		 	case 'scan': scanqrcode(datas);break;
			 case 'uploadImg':uploadImg(fn);break;
		 }
	});
}


/**分享到朋友*/
var  shareFired = function(data){
	wx.hideOptionMenu();
	wx.onMenuShareAppMessage(data);
	wx.onMenuShareTimeline(data)
	wx.showMenuItems({
	    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
	});
	var section = document.createElement('section');
	var div = document.createElement('div');
	var p = document.createElement('p');
	var img = document.createElement('img');
	var a = document.createElement('a');
	var div_1 = div.cloneNode(true);
	var p_1 = p.cloneNode(true);
	var　p_2 = p.cloneNode(true);
	
	section.setAttribute('class','fxtk');
	div.setAttribute('class','fxtks');
	img.setAttribute('src','/images/fxtk.png');
	div_1.setAttribute('class','fxtks_d');
	p.setAttribute('class','fxtks_p1');
	p.innerHTML='点击右上方分享入口，分享到朋友';
	
	p_1.setAttribute('class','fxtks_p2');
	/*p_1.innerHTML='';*/
	
	p_2.setAttribute('class','fxtks_p3');
	
	a.setAttribute('class','fxtks_a');
	a.innerHTML='我知道了！';
	a.onclick = fxdisplay;
	div_1.appendChild(p);
	div_1.appendChild(p_1);
	div_1.appendChild(p_2);
	div.appendChild(img);
	div.appendChild(div_1);
	div.appendChild(a);
	section.appendChild(div);
	document.body.appendChild(section);
}


/**微信扫一扫*/
var scanqrcode = function(data){
	wx.scanQRCode(data);
}

var uploadImg = function(fn){
	var obj = {};

	obj["localId"] = [];
	obj["serverId"] = [];

	wx.chooseImage({
		count: 1, // 默认9
		sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有
		sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		success: function (res) {
			obj.localId = res.localIds;



			fn(obj.localId);
			if(res.localIds.length == 1) {

				wx.uploadImage({
					localId: obj.localId[0],
					isShowProgressTips: 1,
					success: function (res) {
						obj.serverId.push(res.serverId);
		                        //
                        //
						//var sI = obj.serverId;
					},
					fail: function(res) {
						alert(JSON.stringify(res));
					}


				});
			}
		},
		fail :function(){

			alert("ssss");
		}
	});
}

/**获取微信appid，noncestr，timestamp，sign等参数*/
var getWxData = function(){
	var data = '';
	$.ajax({
		url:'/getWxData.ajax',
		type:'post',
		dataType:'json',
		async:false,
		success:function(json){
			if(json.state){
				data = json.param;
			}
		}
	});
	return data;
}


/***/
var fxdisplay = function(){
	$('.fxtk').css('display','none');
}