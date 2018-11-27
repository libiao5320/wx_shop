<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <script src="/js/jquery.min.js" type="text/javascript"></script>
    <script src="/js/js.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="/js/wx-share.js" type="text/javascript"></script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt top_txts">去评价</p>
        <div class="top_right top_rights">
            <a class="top_sx" href="#"></a>
        </div>
    </section>
</header>
<article>

    <form action="/order/comment.htm" method="post">
    <p class="navss10_p1">${order.goodsName}</p>
    <section class="navss10">
        <div class="navss10_p2">
            <p class="navs13_right_p2"><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i><i class=""></i></p>
            <span id="commentlevel">一般</span>
        </div>
        <textarea name="map[detail]"  cols="" rows="" placeholder="对这次消费满意吗？给小伙伴们分享一下吧！"></textarea>
        <p class="navss10_p3">500字</p>
    </section>
   <!-- <section class="navss10_p4">
        <div class="navss10_p5">
            <ul class="navss10_p6 clearfix">
                <#--<li><a><img src="images/img04.png"></a></li>-->
                <li class="navss10_li"><a><button class="chooseFile"></button> <img src="/images/qpj.png" ></a></li>
            </ul>
        </div>
    </section>-->
    <p class="navss10_p7"><label class="labels2"><input name="isAnonymous" type="checkbox" value="N" />
        <input type="hidden" name='map[isAnonymous]' value="N"/>
        匿名评价<span class="anbh1"></span></label></p>
    <a class="navs32_a navs32_as"  >提交评价</a>

    <input type="hidden" name="map[grade]" value="3" />
    <input type="hidden" name="map[orderId]" value="${order.orderId}" />
    <input type="hidden" name="map[memberId]" value="${LOGIN_SIGN.id}" />
    <input type="hidden" name="map[img]" value="" />

    </form>
</article>
<script>

    (function(){
            var iss = $(".navs13_right_p2").find("i");



            iss.each(function(i,o){
                $(o).on("click",function(){
                    var index =  i;
                    index++;
                    iss.removeClass("navs13_i");
                    var ass = $(".navs13_right_p2").find("i:lt("+index+")");
                    $(".navs13_right_p2").find("i:lt("+index+")").addClass("navs13_i");
                    $("input[name='map[grade]']").val(index);


                    switch (index)
                    {
                        case 1:
                        $("#commentlevel").text("很差");
                            break;
                        case 2:
                            $("#commentlevel").text("比较差");
                            break;
                        case 3:
                            $("#commentlevel").text("一般");
                            break;
                        case 4:
                            $("#commentlevel").text("很好");
                            break;
                        case 5:
                            $("#commentlevel").text("棒极了");
                            break;
                    }

//                    alert("bbb");

                })
            })


        var submit = function(){

            if($('textarea[name="map[detail]"]').val() == "" )
            {
                alert("请输入评价内容");
                return false;
            }

//                return false;

            $("form").submit();
        }
        $(".navs32_as").on("click",submit);

        var uploadImg = function(imgList){
            for(var i = 0 ; i < imgList.length ; i++)
            {
                var img =  $('<li><a><img src="'+imgList[i]+'"></a></li>');
                img.insertBefore($(".navss10_p6").find("li:eq(0)"));

                var s =  "[" + imgList[i] +"]";

                $("input[name='map[img]']").val( $("input[name='map[img]']").val()+s);


            }
        }

        $(".chooseFile").on("click",function(){
            sharePal(null,"uploadImg",uploadImg);
            return false;
        });


        $("input[name='isAnonymous']").click(function(){
            var _this = $(this);
            if( _this.get(0).checked == true )
            {
                _this.next().val("Y");
            }
            else{
                _this.next().val("N");
            }
        });
    })();
</script>
</body>



</html>
