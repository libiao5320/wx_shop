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
        <p class="top_txt">支付密码管理</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>
<style>
 #password{
	 font-size: 28px;
			         letter-spacing: 16%;
			         border-radius: 3px;
			         border-color: #3C763D;
			         border-radius: 3px;
			         padding: 10px;
			        height: auto;
			        box-shadow: none;
			        width:96%;
			        font-size: 13px;
			        box-sizing: border-box;
          }
</style>
    <section class="navs37">
         <p class="navs37_p1">请再次输入VIP储值卡支付密码</p>
 <div class="zgwt" style="text-align:center;width:100%; overflow: hidden;">
		<label>
			<input id='password' placeholder="" type="password" maxlength="6" 
			class="form-control input-lg"
			onkeyup="this.value=this.value.replace(/\D/g,'')" 
			onafterpaste="this.value=this.value.replace(/\D/g,'')" >
		 </label>
		</div>
    </section>
	
  	<form action="/setPayPsd.htm" id="setPayPsd"  method="post">
		<input type="hidden" value="${memberPaypwd}" id="first" name="memberPaypwd">
		 <input  id="submit" type="submit" disabled class="bts_an" value="完成">
    </form>
</article>



<script type="text/javascript">
$(function(){
    $('#password').css('letter-spacing',$('#password').width()/6);
	$('#password').on('input',function(){
		if($('#password').val().length==6){
			$('#password').blur();
		}
	});
	
});
</script>
</body>
</html>
