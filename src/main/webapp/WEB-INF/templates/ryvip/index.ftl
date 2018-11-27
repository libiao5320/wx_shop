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
    <script type="text/javascript" src="/js/jquery-ui-min.js"></script>
    <script type="text/javascript" src="/js/js.js"></script>
    <script type="text/javascript" src="/js/kiting.js"></script>
    <script type="text/javascript" src="/js/wx-share.js"></script>
    <script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<header>
    <section class="head">
        <a class="left_nav" href="/userCenter.htm"></a>
        <p class="top_txt">融耀VIP卡</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>
    <section class="navs16 navs16_ryk">
    	<a class="navs8_p1" href="/vip/querybalanceByUser.htm">账户余额：<i>¥<#if user??>${user.availablePredeposit/100+user.freezePredeposit/100}</#if></i><span></span><em>明细</em></a>
        <a class="navs8_p1" href="javascript:;">可用余额：<i>¥<#if user??>${user.availablePredeposit/100}</#if></i></a>
       
        <div class="navs16_btm">
            <a class="navs16_btm_left" href="/vip/vip_index.htm">
                <p><i class="navs16_ryk_i">+</i>充值</p>
            </a>
            <a class="navs16_btm_right handle">
                <p><i class="navs16_ryk_i">￥</i>余额处理</p>
            </a>
        </div>
    </section>
    <section class="navs31">
        <h1 class="nav_h1s">充值卡兑换余额</h1>
        <!--href="/ryvip/scanQrcode.htm"-->
        <a class="navs31_a1" onclick="scanQrcode();">扫描二维码兑换</a>
        <div class="navs31_d">
            <p class="navs31_p1">
            <label><input type="password" name="cardpassword" placeholder="请输入充值卡密码" ></label>
            <!--<a class="navs31_a2" disabled>兑换</a>-->
            <a class="navs31_a2">兑换</a>
            </p>
        </div>
    </section>
    <section class="navs18 navs18_ryk">
        <ul class="navs18_ul">
            <!--<li><a href="" class="navs17_p1 navs17_p01"><i></i>我的银行卡 <span>0</span></a></li>-->
            	<#if user??>
            			 <#if user.memberPaypwd?? >
            			 	
		            			 <#if user.memberPaypwd=="" >
		            			 	<li><a href="/paymentPsd.htm" class="navs17_p1 navs17_p02"><i></i>支付密码管理
			 				    	<span>设置支付密码</span>
			 				    <#else>
			 				    	<li><a href="/pwdManger.htm" class="navs17_p1 navs17_p02"><i></i>支付密码管理
				            	</#if>
			            <#else>	
			            			<li><a href="/paymentPsd.htm" class="navs17_p1 navs17_p02"><i></i>支付密码管理
			       					<span>设置支付密码</span>
		            	</#if>
            	</#if>
            	
            	</a></li>
        </ul>
    </section>
</article>

<script type="text/javascript">
function scanQrcode(){
	var data = {
		  		needResult:1,
		  		desc:'scanQRCode desc',
				success:function (res) {
					var url = res.resultStr;
					/*url = url.substring(url.indexOf('?')+1,url.lenght)
		         	$('input[name="cardpassword"]').val(url);*/
		         	window.location.href=url;
			    },
			    cancel:function(){
			    	window.location.reload();
			    }
			};
	sharePal(data,'scan');
}
</script>
</body>
</html>
