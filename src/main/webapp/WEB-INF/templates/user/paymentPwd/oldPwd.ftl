<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
        <script type="text/javascript" src="../../js/pwd/pwd.js"></script>
    <link href="../../css/style.css" rel="stylesheet" type="text/css">
       <script type="text/javascript" src="../../js/jquery.min.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">修改支付密码</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>

<form action="/oldConfrim.ajax" id="login-form" name="login-form" method="post">
    <section class="navs37">
        <p class="navs37_p1">请输入原来的支付密码</p>
<div class="zgwt" style="text-align:center;width:100%; overflow: hidden;">
		<label class="mmtz">
		<p class="mmtz_p1"><i></i><i></i><i></i><i></i><i></i><i></i></p>
			<input id='password' placeholder="" type="password" name="memberPaypwd" 
			maxlength="6" class="form-control input-lg" type="text"
			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		</label>
		</div> 

		 <input  id="submit" type="button" disabled class="bts_an" value="下一步">
    </form>
    </section>

</article>
<script>


$(function(){
var browser = {
			versions: function () {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
			iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
			iPad: u.indexOf('iPad') > -1, //是否iPad
			};
			}(),
			}
			if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
			$('#password').css("font-size","13px");
			$('#password').css("padding","12px 0");
			}
			if (browser.versions.android) {
			
			}
	$("#submit").click(function(){
		
		$.ajax({
			url:$('#login-form').attr('action'),
			data:$('#login-form').serialize(),
			method:"post",
			dataType:"json",
			success:function(json){
			
				if(json.state){
					  
					window.location.href='/paymentPsd.htm';
					
				}else{
					alert(json.message);
				}
			},error:function(){
				alert("error");
			}
		})
	})
		 $('#password').css('letter-spacing',($('.mmtz_p1').width()-47)/6);
		 $('.mmtz').css('padding-left',($('.mmtz_p1').width()-60)/12);
		 $('.mmtz_p1 i').css("width",($('.mmtz_p1').width()-6)/6);				
		 $('#password').val('');
})
</script>
<script type="text/javascript">

$(function(){
 
	$('#password').on('input',function(){
		if($('#password').val().length==6){
			if(isNaN($('#password').val())){//是否为非数字
				return;
			}
			$('#password').blur();//失去焦点
		   	$("#pay_bt1_p4").attr("disabled", false); 
			$(".pay_bt1").css('position','absolute');
			$("#password").attr("readonly","readonly");
			  $("#submit").attr("disabled", false); 
			    $("#submit").addClass('bts_ans');
		}
	});
	
	$("#password").click(function(){
		if($('#password').val().length==6){	
		$('#password').removeAttr("readonly");
		  $(this).val("");
			if($(".pay_bt1").css('position') == 'absolute'){
				$(".pay_bt1").css('position','fixed');
				//$('#password').blur();//失去焦点
			}
		}else{
			$('#password').focus();
		}
	}); 
});

</script>

</body>
</html>
