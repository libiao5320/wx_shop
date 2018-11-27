<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">



</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav"></a>
        <p class="top_txt top_txts">投诉申请</p>
        <div class="top_right top_rights">
            <a class="top_sx" href=""></a>
        </div>
    </section>
</header>
<article>

    <div class="navss8">
        <h3>投诉订单</h3>
        <p>${goodsName}</p>
    </div>
    <form id="myform" action="/order/complain.htm" method="post">
    <section class="navss8_p1">
        <label class="labels1"><input name="" type="radio" value="">消费券：${consumptionId}<span class="anbh1 anbhs1"></span></label>
    </section>
    <p class="navss8_p2">投诉原因</p>
    <section class="navss8_p1">
        <div class="navss8_p3">
            <label class="labels1"><input type="radio" name="reason" value="实际消费跟描述的完全不相符" id="RadioGroup1_0">实际消费跟描述的完全不相符<span class="anbh1"></span></label>
            <label class="labels1"><input type="radio" name="reason" value="商家虚假" id="RadioGroup1_1">商家虚假<span class="anbh1"></span></label>
            <label class="labels1"><input type="radio" name="reason" value="计划有变，没时间消费了" id="RadioGroup1_2">计划有变，没时间消费了<span class="anbh1"></span></label>
            <label class="labels1"><input type="radio" name="reason" value="其它原因" id="RadioGroup1_2">其它原因<span class="anbh1"></span></label>
        </div>
    </section>
    <section class="navss8_p4" style="display: none;">
        <p>其他原因</p>
        <textarea name="map[reason]" cols="" rows="" placeholder="在此输入详情原因"></textarea>
    </section>

    <input type="hidden" name="map[orderId]" value="${orderId}"/>
    <input type="hidden" name="map[memberId]" value="${LOGIN_SIGN.id}" />
    </form>
    <a class="navs32_a navs32_as">投诉</a>

</article>
<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/js.js" type="text/javascript"></script>
<script>
    $(function(){
                $(".navs32_as").click(function(){
					var val=$('input:radio[name="reason"]:checked').val();
		            if(val==null){
		      			alert("投诉原因至少选一项")
		                return false;
		            }
                    $('#myform').submit();
                })
                $("input[name='reason']").on("click",function(){
                    var _this = $(this);
                    $("textarea[name='map[reason]']").text(_this.val());
                    $("textarea[name='map[reason]']").val(_this.val());
                    var pas = _this.parent().siblings();
                    pas.each(function(i , o ){
                        $(o).find(".anbhs1").removeClass("anbhs1");
                    })
                    if(_this.val() !=  '其它原因')
                    {
                        $(".navss8_p4").css("display","none");
                    }
                    else
                    {
                        $(".navss8_p4").css("display","block");
                    }
                });
            }
    );

</script>
</body>
</html>
