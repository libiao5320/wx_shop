/**
 * Created by Administrator on 2015/10/16.
 */
$(function(){
    //底部按钮点击变色
   $(".footer div").click(function(){
       $(this).addClass("current").siblings(".footer div").removeClass("current");
   });
    //性别男女选择
    $(".xingb a").click(function(){
        $(this).addClass("current").closest(".xingb a").siblings(".xingb a").removeClass("current");
    });
	//账户余额内容最小高度
	$(".zhye").css("min-height",$(window).height()-95);
    //选择时间最小高度
    $(".xz-time").css("min-height",$(window).height()-105);
  //全部订单（无）-内容最小高度
    $(".cart-no").css("height",$(window).height()-155);
	//我的消息（无）-内容最小高度
    $(".note-wu").css("height",$(window).height()-155);
    $(".position").css("height",$(window).height());
    $(".js-sswu").css("height",$(window).height()-182);

});

$(function(){
	$(".sort-con-c").css("max-height", $(window).height() - 150 );
    $(".sstk").css("max-height", $(window).height() - 150 );
	$(".area-l,.area-r").css("max-height", $(".sort-con-c").height() - 10);
	$(".shrsli").css("max-height", $(window).height() - 150);
	$(".sortrow li").click(function(){
		if($(".sortbar").css("position")=="relative"){
			$("html,body").animate({
				scrollTop: $(".idxlist").offset().top
			}, 100);
		}else if($(".sortbar").css("position")=="fixed"){
			$("html,body").animate({
				scrollTop: $(window).scrollTop()
			}, 100);
		}else{
			$("html,body").animate({
				scrollTop: $(".idxlist").offset().top
			}, 100);
		}
		var index = $(this).index();
		if($(this).attr("class").indexOf("current")==-1){
			$(this).addClass("current").siblings().removeClass("current");
			$(this).addClass("cur")
			$(".sortrow").css("z-index","4");
			$('.sortbar').addClass("fxd");
			$(".sortbg").show();
        }
        else{
			$(this).removeClass("current");
            $(".sortrow").css("z-index","1");
			//$('.sortbar').removeClass("fxd");
			$(".sortbg").hide();
        }
		$(".sort-con-c").each(function() {
			if($(this).index()==1 && index == 0){
				$(this).slideToggle(200).siblings(".sort-con-c").slideUp(100);
			 }
			if($(this).index()==2 && index == 1){
				$(this).slideToggle(200).siblings(".sort-con-c").slideUp(100);
			 }
			 if($(this).index()==3 && index == 2){
				$(this).slideToggle(200).siblings(".sort-con-c").slideUp(100);
			 }
		});
		$('.sortbar').animate({
            top: '0',left: '0',
        }, 100);
	});
	$(".sortbg").click(function(){
		$(this).hide();
		$(".sort-con-c").slideUp(200);
		$(".sort-con-c1").slideUp(200);
		$(".sort-con-c2").slideUp(200);
        $(".sort-con-c3").slideUp(200);
		$(".sortrow li").removeClass("current");
	});
	$(".shrsli li,.area-r li").click(function(){
		$(".sortbg").hide();
		$(".sort-con-c").slideUp(200);
		$(".sort-con-c1").slideUp(200);
		$(".sort-con-c2").slideUp(200);
        $(".sort-con-c3").slideUp(200);
		$(".sortrow li").removeClass("current");
	});
});
$(function(){
	var nav=$(".sortbar"); //得到导航对象
	var win=$(window); //得到窗口对象
	var sc=$(document);//得到document文档对象。
	var banheight=$(".slider").height();
	var h1=$(".header").height(),
		h2=$(".menu").height(),
		h3=banheight,
		h4=$(".nav").height()+21,
		h5=$(".idx").height()+42;
	var height=h1+h2+h3+h4+h5;
	win.scroll(function(){
	  if(sc.scrollTop()>=height){
		nav.addClass("fxd"); 
	  }else{
	   nav.removeClass("fxd");
	  }
	});
});
jQuery(document).ready(function() {
    $(function() {
        $("#elevator").click(function(){
            $("html,body").animate({scrollTop: 0}, 500);
        });
    });
	//支付订单选择支付方式
    $(".zfdd-cc li").click(function(){
        $(this).toggleClass("current").closest(".zfdd-cc li").siblings(".zfdd-cc li").removeClass("current");
    });
    /*评论图片取中间*/
    $(window).load(function(){
        var $Oimg=$('.re-list img');
        $Oimg.each(function(){
            var $thislink = $(this);
            var temHeight = $thislink.height();
            var temWidth = $thislink.width();
            if( temHeight >= temWidth )
            {
                $thislink.css({"width":"100%","height":"auto"});
            }else{
                $thislink.css({"height":"100%","width":"auto"});
            }
        })
    });
    //教室详情预定
	
	//赞
    $(".zan a").click(function(){
		$(this).toggleClass("current");
	})
	//提交订单免责声明
    $(".mzico").click(function(){
        $(this).toggleClass("current");
    });
  //订单支付账户余额勾选
	$(".or-zf-c p").click(function(){
		$(this).toggleClass("gou");
	});
});
//搜索
$(function(){
    $(".sort-con-c1").css("max-height", $(window).height() - 150 );
    $(".ss-shrs").css("max-height", $(".sort-con-c1").height());
    $(".sort-con-c2").css("max-height", $(window).height() - 150 );
    $(".ss-shrs1").css("max-height", $(".sort-con-c2").height());
    $(".sort-con-c3").css("max-height", $(window).height() - 150 );
    $(".ss-area-l,.ss-area-r").css("max-height", $(".sort-con-c3").height() - 10);
    $(".sec-c li.sec-c2").click(function(){
        $(".sortbg").show();
        $(".sort-con-c1").slideToggle(200);
    });
    $(".sec-c li.sec-c3").click(function(){
        $(".sortbg").show();
        $(".sort-con-c2").slideToggle(200);
    });
    $(".sec-c li.sec-c4").click(function(){
        $(".sortbg").show();
        $(".sort-con-c3").slideToggle(200);
    });
});
//邀请好友分享朋友圈
$(function(){
	$(".fxfx").hide();
	$(".yqhy-b a").click(function(){
		$(".fxfx").fadeIn();
	});
	$(".fxfx").click(function(){
		$(this).stop().fadeOut();
	});
});
