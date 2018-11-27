<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="../../js/test/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../js/pwd/pwd.js"></script>
<script type="text/javascript" src="../../js/jquery.min.js"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav"></a>
        <p class="top_txt">支付密码管理</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>

    <section class="navs37">
        <p class="navs37_p1">请再次输入VIP储值卡支付密码</p>
       <ul class="navs37_ul">

           <li><label><input   class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
           <li><label><input   class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
           <li><label><input   class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
           <li><label><input   class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
           <li><label><input  class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
           <li><label><input   class="pass" maxlength="1" value="" type="password" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></label></li>
       </ul>

    </section>
  

</article>
<script>

$(function(){
	//绑定监听添加事件
	pwd1=new pwd();
	pwd1.inputFocus(0);  //初始化聚焦第一个input
	//监听删除时间事件
	$(document).keydown(function(event){
	//alert(event.keyCode);
		if(event.keyCode==46){
			pwd1.inputDelete(pwd1.inputIndex()-1);
			pwd1.inputFocus(pwd1.inputIndex());
		}
		else{
			pwd1.inputFocus(pwd1.inputIndex());
			
		    if(pwd1.pwdPass(6))
		    {
		      $('').show();//slideUp();
		      $('').data(pwd, pwd1.getPwd());
		    }
		}
	});
	
});
</script>

</body>
</html>
