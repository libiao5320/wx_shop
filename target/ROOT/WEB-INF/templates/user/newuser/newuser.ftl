<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="no"/>
<meta name="format-detection" content="telephone=no" />
<title>注册送红包</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
<script src="/js/jquery.min.js"></script>
</head>
<body >
    <article id="fx">
        <section class="fx_one">
            <p><img src="/images/fx_bg02.jpg" alt="" title=""></p>
            <div class="fx_ones">
            <form id="form1" action="/registerred/register.ajax" method="post">
        	    <input type="hidden" id="recommendCode" name="map[recommendCode]" value="${recommendCode !''}"/>
        	    <input type="hidden" id="areaName" name="map[areaName]" value=""/>
			    <input type="hidden" id="cityName" name="map[cityName]" value=""/>
			    <input type="hidden" id="provinceName" name="map[provinceName]" value=""/>
			    <input type="hidden" id="areaInfo" name="map[areaInfo]" value=""/>
                <div class="fx_one_d new_one">
                    <p class="fx_one_p1"><img src="/images/fx_img11.png" alt="" title=""></p>
                    <p class="fx_one_p2">融耀健康，做好自己！</p>
                    <p class="fx_one_p3"><input type="tel" value="" name="map[memberMobile]" placeholder="请输入手机号"></p>
                    <a class="fx_one_a1" href="javascript:void(0);" id="getValidCode">获取验证码</a>
                    <p class="fx_one_p4"><img src="/images/fx_img01.png" alt="" title=""></p>

                </div>
                <div class="fx_one_d fx_one_ds new_two" style="display:none;">
                    <p class="fx_one_p1s">领取红包手机号：<span id="mobile">15209559890</span></p>
                    <p class="fx_one_p2s">验证码发送成功</p>
                    <p class="fx_one_p3">
                        <label class="fx_one_la"><input type="text" value="" name="map[validCode]" placeholder="输入验证码"></label>
                        <a hidden="" href="javascript:void(0);" id="getValidCodeBtn">重新发送</a>
                    </p>
                    <a class="fx_one_a1" id="qrlw">确认领取</a>
                    <p class="fx_one_p4"><img src="/images/fx_img01.png" alt="" title=""></p>

                </div>
            </form>
                <div class="fx_one_dd">
                    <p class="fx_one_p1"><img src="/images/fx_img07.png" alt="" title=""></p>
                    <div class="fx_one_dds">
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包，限。</span></p>
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包，限量发放10000个。</span></p>
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包，限量发放10000个。</span></p>
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包</span></p>
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包，限量发放10000个。</span></p>
                        <p class="fx_one_p5"><i></i><span>手机号首次注册，即可领取50元红包</span></p>
                    </div>

                </div>
            </div>
        </section>
        <div class="lqhb">
            <div class="lqhb_c">
            </div>
            <div class="lqhb_d">
                <p class="lqhb_p1"><img src="/images/fx_img02.png" alt="" title=""></p>
                <div class="lqhb_ds">
                    <p class="lqhb_p2"><img src="/images/fx_img03.png" alt="" title=""></p>
                    <div class="lqhb_dss">
                        <p class="lqhb_p3">现金红包</p>
                        <p class="lqhb_p4">50<i>￥</i></p>
                        <a href="http://mp.weixin.qq.com/s?__biz=MzAwODY5NDUyNw==&mid=401472584&idx=1&sn=70bca960f63a041e82baa9e2f0f37c0a#rd&ADUIN=1220356497&ADSESSION=1456719044&ADTAG=CLIENT.QQ.5457_.0&ADPUBNO=26551" class="lqhb_a1">立即使用</a>
                    </div>
                </div>
            </div>
        </div>
    </article>
<script type="text/javascript">
(function(){
var geoc = new BMap.Geocoder(); 
var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var pt = r.point;
			geoc.getLocation(pt, function(rs){
				var addComp = rs.addressComponents;
				$("#areaName").val(addComp.district);//区
				$("#cityName").val(addComp.city);//市
				$("#provinceName").val(addComp.province);//省
				$("#areaInfo").val(addComp.province+ addComp.city + addComp.district + addComp.street + addComp.streetNumber);//详细地址
			});    
		}
		else {
			
			//alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true});

})();
</script>

<script>
  $(document).ready(function(){
    
    $('#qrlw').click(function(){
    	var validCode = $("input[name='map[validCode]']").val();
    	if(validCode == ""){
    		alert('验证码不能为空');
    		return;
    	}
    	
    	var phone = $("input[name='map[memberMobile]']").val();
    	if(phone == "")
    	{
            alert("手机号码不能为空");
            return false;
    	}
        if(phone.length!=11)
        {
            alert("请输入有效手机号码");
            return false;
        }

    	var myreg = /^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1})+\d{8})$/;
        if(!myreg.test(phone))
        {
            alert("请输入有效手机号码");
            return false;
        }
    	
    	$.ajax({
		    url: $("#form1").attr("action") ,
		    type: 'POST',
		    data: $("form").serialize() ,
		    dataType: 'json',
		    success: function(data){
		    	if(data.state){
		    		if(data.message == 'success'){//领取成功
			    		$('.lqhb_p4').html(data.param.moneyDollar+"<i>￥</i>");
			    		$('.lqhb').show();
	               		$('.lqhb_c').show();
               		}else{//领取失败
               			window.location.href="/registerred/receivefail.htm";
               			return ;
               		}
		    	}else{
		    		alert(data.message);
		    	}
		     } ,
		    error: function(){
		    }
		});
    });
    $('.lqhb_c').click(function(){
        $('.lqhb').hide();
        $(this).hide();
    });
    
    
    var getValidCode  = function(){
    	var phone = $("input[name='map[memberMobile]']").val();
    	if(phone == "")
    	{
            alert("手机号码不能为空");
            return false;
    	}
        if(phone.length!=11)
        {
            alert("请输入有效手机号码");
            return false;
        }

    	var myreg = /^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1})+\d{8})$/;
        if(!myreg.test(phone))
        {
            alert("请输入有效手机号码");
            return false;
        }

		$.ajax({
		    url: '/registerred/sendRegValidCode.ajax' ,
		    type: 'POST',
		    data: {phone:phone} ,
		    dataType: 'json',
		    success: function(data){
		    	if(data.state){
		    		time();
		    		$("#mobile").html(phone);
		    		$(".new_one").hide();
		    		$(".new_two").show();
		    		alert("注册码已经发送,请注意查收");
		    	}else{
		    		alert(data.message);
		    	}
		     } ,
		    error: function(){
		    }
		});
	}

	var time = function(){
		
		$('#getValidCodeBtn').unbind("click");

    	var count = 60 ;
		var times = setInterval(function(){
		    count-=1;
		    if(count == 0) {
		    
		        clearInterval(times);
		        $('#getValidCodeBtn').removeAttr("disabled");
		        $("#getValidCodeBtn").text("获取验证码");
		        $('#getValidCodeBtn').on("click",function(){
		            getValidCode();
		        })
		    }
		    else {
		
		        $("#getValidCodeBtn").text(count+"秒可获取");
		    }
		},1000);
	}

    $("#getValidCode").on("click",function(){
        getValidCode();
    });
    $("#getValidCodeBtn").on("click",function(){
        getValidCode();
    });

  });
</script>
 </body>
</html>
