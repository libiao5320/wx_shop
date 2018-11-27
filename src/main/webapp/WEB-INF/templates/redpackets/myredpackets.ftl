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
</head>
<body class="cpxq_qb">
    <header>
        <section class="head">
            <a class="left_nav" href="javascript:history.go(-1);"></a>
            <p class="top_txt top_txts">我的红包</p>

        </section>
    </header>
    <article>
        <section class="navs47">
            <ul class="navs47_ul">
            	<#if list?? && (list?size > 0)>
            	<#list list as red>
            	<#if (red.status == 'expired') || (red.status == 'unuse')>
                <li>
                    <p class="navs47_p1"><img src="/images/gwq01.png" alt="" title=""></p>
                    <p class="navs47_p2">${red.name}</p>
                    <p class="navs47_p3">￥<span>${red.moneyDollar}</span></p>
                    <#if (red.status == 'unuse')>
                    <p class="navs47_p4">可用</p>
                    <p class="navs47_p5">有效期：${red.strValidTime !''}</p>
                    <a class="navs47_a1" href="javascript:void(0);" onclick="shareRedPack();"><img src="/images/fxyl.png" alt="" title=""></a>
                    <#else>
                    <p class="navs47_p4">已过期</p>
                    <p class="navs47_p5"></p>
                    </#if>
                </li>
                </#if>
                </#list>
                </#if>
            </ul>
        </section>
    </article>
    <section class="hb">
    	<div class="hb_ceng"></div>
    	 <div class="hb_ds">
    	 	<span class="xz_gb"></span>
            <img src="/images/hb02.png" alt="" title="">
            <p class="hb_ds_p1">恭喜你获得</p>
            <p class="hb_ds_p2"><i>8.80</i>元</p>
            <p class="hb_ds_p3">红包已放入账户</p>
            <a href="/redpackets/myredpackets.htm" class="hb_ds_a2">立即查看</a>
        </div>
    </section>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-min.js"></script>
    <script type="text/javascript" src="/js/js.js"></script>
    <script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="/js/wx-share.js"></script>
    <script type="text/javascript">

        var shareRedPack = function(){
				var data = {
						title: '分享送红包', // 分享标题
						desc: '分享送红包', // 分享描述
						link: '${sharlink !''}', // 分享链接
						imgUrl:'', // 分享图标
						type: 'link', // 分享类型,music、video或link，不填默认为link
						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
						success: function (res) { 
							alert('分享成功')
							$('.fxtk').hide();
							shareSuccessRed();
							//window.location.reload();
						},
						cancel: function () { 
							alert('取消分享');
							$('.fxtk').hide();
							window.location.reload();
						},fail:function(){
							alert('分享失败');
							$('.fxtk').hide();
							window.location.reload();
						}
				};
                sharePal(data,'share');
        }
		
		//分享成功红包
		var shareSuccessRed = function(){
			$.ajax({
				url: '/redpackets/shareSuccessRed.ajax' ,
				type: 'POST',
				data: '',
				dataType: 'json',
				success: function(data){
					if(data.state){
						$(".hb_ds_p2 i").html(data.param.moneyDollar);
						$(".hb_ceng").show();
						$(".hb_ds").show();
					}
				} ,
			    error: function(){
			    }
		 	});
		}
    </script>
 </body>
</html>
