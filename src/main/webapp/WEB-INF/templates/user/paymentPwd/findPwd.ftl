<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
     <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">找回支付密码</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>

    <section class="navs39">
        <p class="navs39_p1">短信验证找回</p>
        <p class="navs39_p2">请输入手机号<span>${moblie?replace(moblie?substring(3,7),'****')}</span>收到的短信验证码</p>
        
         <form id="login-form"  action="/codeConfrim.ajax"  method="post">
             <input name="mobile" id="mobile" type="hidden" value="${moblie}"/>
              <p class="navs31_p1"><label><input name="code"  id="code" placeholder="请输入手机验证码" type="text"></label>
              <input type="button"   class="navs31_a2" onclick="sendMessage()"  id="send" value="获取验证码">
    </section>
    
      <input  id="submit" type="button"  class="bts_an" value="下一步">
  </form>
</article>
<script>
$(function(){
	$("#submit").click(function(){
		if($("#code").val() == null){
			alert("请输入验证码!");
			return false;
		}	
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
})
</script>
<script>

$(function(){
  $("input[name='code']").bind('input propertychange',function(){
    if($(this).val() !=''){
            $("#submit").attr("disabled", false); 
			     $("#submit").addClass('bts_ans');
    }else{

     $("#submit").attr("disabled", true);
     $("#submit").removeClass("bts_ans");
     }
  })
})


var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数	

function sendMessage() {
　　  //向后台发送处理数据
     $.ajax({
     　　type: "POST", //用POST方式传输
     　　dataType: "json", //数据格式:JSON
      data:{ "mobile":$("#mobile").val()}, 
     　　url: '/sendr.ajax', //目标地址
    　　 error: function (XMLHttpRequest, textStatus, errorThrown) {},
     　　success: function (json){
				if(json.state){
					 alert(json.message);
					 buttonCss();
					 $(".oksend").attr("readonly",false);
				}else{
					alert(json.message);
				}
           }
     });
}

function buttonCss(){
    curCount = count;
　   //设置button效果，开始计时
　        $("#send").attr("disabled", "true");
　        $("#send").val(curCount + "秒后可以重发");
　        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
}

function SetRemainTime(){
	 if (curCount == 0) {                
            window.clearInterval(InterValObj);//停止计时器
            $("#send").removeAttr("disabled");//启用按钮
            $("#send").val("重新发送");
       }
        else {
	        curCount--;
	        $("#send").val(curCount +"秒可重发");
       }
}	
</script>
</body>
</html>
