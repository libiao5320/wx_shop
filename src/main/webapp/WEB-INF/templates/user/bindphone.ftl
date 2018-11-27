<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=E4a51c429ff711c1072eb83bf39f3096"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">绑定手机号</p>
        <div class="top_right top_rights">
            <a class="top_sx"></a>
        </div>
    </section>
</header>
<article>
<div id="allmap"></div>
    <form id="form1" action="register.htm" method="post">
    <input type="hidden" id="areaName" name="map[areaName]" value=""/>
    <input type="hidden" id="cityName" name="map[cityName]" value=""/>
    <input type="hidden" id="provinceName" name="map[provinceName]" value=""/>
    <input type="hidden" id="areaInfo" name="map[areaInfo]" value=""/>
    <section class="navs39 navs39s">
        <p class="navs39_p1s"><i></i>验证手机号码后，即可完成绑定，获取代金券！</p>
        <p class="navs39_p3"><input type="tel" name="map[memberMobile]" placeholder="请输入手机号码" value=""></p>
        <p class="navs31_p1"><label><input placeholder="验证码" name="validCode" type="text"></label><a class="navs31_a2" href="javascript:void(0);" id="getValidCodeBtn" >获取验证码</a></p>
        <p class="navs39_p3"><input type="text" name="map[recommendCode]" placeholder="推荐人ID,以'J'开头(可选)" value=""></p>
        <p class="navs39_p4"><label><input type="checkbox" id="agree" checked = "checked">我已阅读</label><a class="bdxy" href="/protocol.htm">《融耀健康协议》</a></p>
    </section>
    <a class="bts_an bts_ans" id="regBtn" href="javascript:void(0);" >下一步</a>
    </form>
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
                    <a href="/redpackets/myredpackets.htm" class="lqhb_a1">查看我的红包</a>
                </div>
            </div>
        </div>
    </div>
</article>


<script src="/js/jquery.min.js"></script>
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

    (function(){

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

        $('#getValidCodeBtn').unbind("click");

    	$.post("/sendRegValidCode.htm" , {phone:phone},function(data){
            if(data.state){
            	times();
            }
            alert(data.message);

    	},"json");
	}

    var times = function(){
    
    	var count = 60 ;

    	var time = setInterval(function(){
        	count-=1;
            if(count == 0) {
                clearInterval(time);
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
    var registerFn = function(){

        var validCode = $("input[name='validCode']").val();
        var phone = $("input[name='map[memberMobile]']").val();
        var agree = $("#agree");

        if(!(agree.get(0).checked)) {
            alert("请先阅读融耀健康协议");
            return false;
        }

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

        var myreg =/^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1})+\d{8})$/;
        if(!myreg.test(phone))
        {
            alert("请输入有效手机号码");
            return false;
        }

        if(validCode == "")
        {
            //createMsgMainWin("手机号码不能为空");
            alert("请输入短信验证码");
            return false;
        }



        $("#regBtn").unbind("click");
        $.post("/authRegValidCode.htm",{inputCode:validCode},function(data){

            if(data == '1'){
                $.ajax({
				    url: $("#form1").attr("action") ,
				    type: 'POST',
				    data: $("form").serialize() ,
				    dataType: 'json',
				    success: function(data){
				    	if(data.state){
				    		if(data.message == 'red'){//领取成功
					    		$('.lqhb_p4').html(data.param.moneyDollar+"<i>￥</i>");
					    		$('.lqhb').show();
			               		$('.lqhb_c').show();
		               		}else{//领取失败
		               			window.location.href="/userCenter.htm";
		               			return ;
		               		}
				    	}else{
				    		window.location.href="/userCenter.htm";
				    	}
				    } ,
				    error: function(){
				    }
				});
			}
            else
                alert("验证码不一致");
            $("#regBtn").on("click",function(){registerFn();});
        },"json");

    }

    $("#getValidCodeBtn").on("click",function(){
        getValidCode();
    });

    $("#regBtn").on("click",function(){
        registerFn();
    });
    
    $(".lqhb_c").on("click",function(){
        window.location.href="/userCenter.htm";
    });
})();
</script>
</body>
</html>
