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
    <style type="text/css">
        .head_fl{position: relative;}
        .head_fl .top_input{ left: 10px;right: 10px;}
        .head_fl .cars {  position: absolute;  right: 0px;  left: 0px;  padding-left: 40px;  }
        .navs53_left{ float: left; width: 80px; position: fixed;left: 0;top: 50px; bottom:49px; overflow-x:hidden;overflow-y: auto; background: #eeeeee; }
        .navs53_lu1 li{ line-height: 45px; height: 45px; text-align: center; border-bottom: 1px solid #c8c8c8;overflow: hidden;}
        .navs53_lu1 a{ color: #333; font-size: 15px;}
        .navs53_lu1 .navs53_li1{ background: #FFF;}
        .navs53_right{ position: fixed;left:80px;top: 50px; bottom:49px; right: 0; overflow-x:hidden;overflow-y: auto; background: #FFF;border-top: 1px solid #c8c8c8;  }

        .navs53_right_p1{margin: 3px;}
        .navs53_right_p2{ height: 45px; line-height: 45px; font-size: 16px; color: #333333; overflow: hidden;}
        .navs53_right_p2 span{margin-left: 10px; width: 10px; height: 10px; display: inline-block; vertical-align: middle; background: #f15353; border-radius: 50%; margin-right:5px;}
        .navs53_qk{ border: 1px solid #c8c8c8; height: 30px; line-height: 30px; margin-top: 8px; padding: 0 10px; border-radius: 5px; font-size: 15px; color: #666;display: inline-block; float: right; margin-right: 10px;}
        .navs53_right_p4{ color: #454553; font-size: 15px; text-align: center; line-height: 24px; height: 24px;}
        .navs53_lu2 li{ float: left; width: 33.3%; margin-bottom: 5px;}
        .navs53_lu2{ margin: 0 2px; overflow: hidden;}
        .navs53_lm{ display: block; margin: 2px;}
        .zwsp{ text-align: center; font-size: 16px; color: #666; margin-top: 50%;margin-bottom: 5px;}
        .zwsps{ display:block; width: 25px; height: 25px; background: transparent url(/images/zsmy.png) no-repeat scroll 0% 50% / 25px auto; margin: 0 auto;}
    </style>
    <script>
    	$(function(){
    		$('.navs53_lu1 li').each(function(){
	            $(this).click(function(){	            	
	                $(this).addClass('navs53_li1').siblings('.navs53_lu1 li').removeClass('navs53_li1');
	                var index = $(this).index();
	                if (index == "0") { 
						$('.navs53_right_p1').show();
						$('.navs53_right_p2').show();
					} 
					else { 
						$('.navs53_right_p1').hide();
						$('.navs53_right_p2').hide();
					} 
						               
            });
            }); 
            
        });
    </script>
</head>
<body class="cpxq_qb">
<input type="hidden" value="1" name="pageNo">
<header>
    <div class="head_fl">
    		<input type="hidden" name="public_url" value="${PUBLIC_IMG_URL!''}">
        <div class="top_input">
        	<form action="/indexSearch.htm" method="post">
	            <a class="ss1" href="javascript:;" onclick="query($(this))"></a>
	            <input class="cars" name="map[goodsName]" id="seachInput" list="cars" placeholder="输入产品名称、商家、分类" />
	            <!--
	            <datalist id="cars">
	                <option value="222">
	            </datalist>
	            -->
            </form>
        </div>
    </div>
</header>
<article>
    <section class="navs53">
        <div class="navs53_left">
            <ul class="navs53_lu1" >
            	<li class="navs53_li1"><a href="JavaScript:;" onclick="queryMostClass($(this))">推荐分类</a></li>
            	<#if  result?? && result?size gt 0 >  
            		<#list result as a>
                		<li> <a href="JavaScript:;"  id="aa" className="${a.className !''}"  classId="${a.classId !''}" onclick="guessYouLove(${a.classId !''},1,'${a.className !''}')">${a.className}</a></li>
                	</#list>
             	</#if>
	            <!-- 私人服务  -->
	            <li><a href="/techie/list.htm" >私人服务</a></li>
            </ul>
        </div>
        <div class="navs53_right" id="cc">
            <p class="navs53_right_p1"><img src="/images/banner1.jpg" alt="" title=""></p>
            <p class="navs53_right_p2" ><span  id="redDot"></span><i id="mulu">常用分类</i><a class="navs53_qk"  onclick="clearAll()"  href="JavaScript:;" >清空</a> </p>
            <ul id="goodClassOne" class="navs53_lu2">
            </ul>
            <div class="nomore" style="display: none" ><p ><span>没有更多了...</span><p/></div>
        </div>
    </section>
</article>   
<div class="ceng"></div>
<div class="ceng1"></div>
<footer>
	<ul class="footer_ul">
	    <li class="" id="Id1"><a href="/index.htm" onclick="dianji1();"><i></i></a><span>首页</span></li>
	    <li class="footer_li" id="Id2"><a href="/goods/class.htm" onclick="dianji2();"><i></i></a><span>分类</span></li>
	    <li class="" id="Id3"><a href="/techie/list.htm" onclick="dianji3();"><i></i></a><span>健康师</span></li>
	    <li class="" id="Id4"><a href="/userCenter.htm" onclick="dianji4();"><i></i></a><span>个人中心</span></li>
	</ul>
</footer>
<script defer="true">
	function dianji1(){
		document.getElementById("Id1").className="footer_li";
		return;
	}
	function dianji2(){
		document.getElementById("Id2").className="footer_li";
		return;
	}
	function dianji3(){
		document.getElementById("Id3").className="footer_li";
		return;
	}
	function dianji4(){
		document.getElementById("Id4").className="footer_li";
		return;
	}
	var pageNo = 2;
	//当点击产品分类一级目录中调用
    function guessYouLove(data,pageNo,className){
		$("#goodClassOne").empty();
		var classId=data;
		document.getElementById("mulu").innerHTML='';
		//document.getElementById('span').color='white';
		 var public_url = $('input[name="public_url"]').val();

		$.ajax({
		    url: '/goods/class.ajax',
		    type: 'POST',
		    data: {'map[pageNo]':pageNo,'map[parentId]':classId} ,
		    dataType: 'json',
		    success: function(json){
				$("#goodClassOne").append('');
		        var result = json.list;
	            if( result  && result.length > 0){       
	            	var _html ="";
	                for( var i= 0 ; i < result.length ; i++){
	                    var goodClass = result[i];
	                    _html+='<li id="bb"><a class="navs53_lm"   href="/goods/list.htm?classIds='+goodClass ["classId"]+'"><p class="navs53_right_p3"><img src="'+public_url+goodClass ["classImg"]+'" alt="" title=""></p><p class="navs53_right_p4">'+goodClass ["className"]+'</p></a></li>';
	                }
					$("#goodClassOne").append(_html);
	            }else{
	            	var _html ="";
	            	_html+='<p class="zwsp">暂无结果</p><p class="zwsps"></p>';
	            	$("#goodClassOne").html(_html)
	            }
	    	} ,
		    error: function(){
		    }
		});
    }
	//分页时加载二级目录
	function guessYouLove2(data,pageNo,className){
		var classId=data;
		document.getElementById("mulu").innerHTML='';
		var public_url = $('input[name="public_url"]').val();
		$.ajax({
			url: '/goods/class.ajax',
			type: 'POST',
			data: {'map[pageNo]':pageNo,'map[parentId]':classId} ,
			dataType: 'json',
			success: function(json){
				$("#goodClassOne").append('');
		    	var result = json.list;
		        if( result  && result.length > 0)
		        {       
		        	var _html ="";
		            for( var i= 0 ; i < result.length ; i++)
		            {
		                var goodClass = result[i];                                                                                                 
		                _html+='<li id="bb"><a class="navs53_lm"  href="/goods/list.htm?classIds='+goodClass ["classId"]+'"><p class="navs53_right_p3"><img src="'+public_url+goodClass ["classImg"]+'" alt="" title=""></p><p class="navs53_right_p4">'+goodClass ["className"]+'</p></a></li>';
		            }
					$("#goodClassOne").append(_html);
		        }else{
		        	$(".nomore").css("display","block");
		        	return ;
		        	//var _html ="";
		        	//_html+='<p class="zwsp">暂无结果</p><p class="zwsps"></p>';
		            //$("#goodClassOne").html(_html)
		        }
		    } ,
	        error: function(){
	        }
    	});
	}
	//查常用分类
	function queryMostClass(data){
		$("#goodClassOne").empty();
		var classId = data.attr("classId");
		var public_url = $('input[name="public_url"]').val();
		$.ajax({
	        url: '/goods/queryMostClass.ajax',
	        type: 'POST',
	        dataType: 'json',
	        success: function(data){
				$("#goodClassOne").append('');
		        var result = data.list;
		        if( result  && result.length > 0)
		        {       
		        	var _html ="";
		            for( var i= 0 ; i < result.length ; i++)
		            {
		                var goodClass = result[i];
		                _html+='<li><a class="navs53_lm" href="/goods/list.htm?classIds='+goodClass ["gc_id_2"]+'"><p class="navs53_right_p3"><img src="'+public_url+goodClass ["class_img"]+'" alt="" title=""></p><p class="navs53_right_p4">'+goodClass ["class_name"]+'</p></a></li>';
		            }
					$("#goodClassOne").html(_html)
	            }else{
		        	var _html ="";
		        	_html+='<p class="zwsp">暂无结果</p><p class="zwsps"></p>';
		        	$("#goodClassOne").html(_html)
	            }
	        } ,
	        error: function(){
	        }
        });
    }
	//清空按钮
	function clearAll(){
		var _html ="";
		$("#goodClassOne").html(_html)
	}
	//搜索按钮
	function query(){
		var seachInput = $('#seachInput').val();
		if(seachInput != '' && seachInput != null )
		{
			$("form").submit();
		}
	}
	(function(win){
		//搜索
		$("#seachInput").keypress(function(event){
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13'){
                $("form").submit();
            }
        });
        <!--初始化进入页面时显示用户常用分类-->
        queryMostClass($(this));
        //分页
        var scrollSign = false;
        $(win).scroll(
        	function(){
       			return (function(scrollObj){
       				var recodeSize = $(scrollObj).find("li").length;
     				var pageNo =  parseInt( recodeSize / PAGE_SIZE ) + (recodeSize % PAGE_SIZE > 0 ? 1:0 );  		
     				pageNo += 1;
			   		if (scrollSign && $(scrollObj).scrollTop() <= 0){
        				scrollSign = false;
      					guessYouLove2($("#aa").attr("classId"),pageNo,$("#aa").attr("className"))
        				return;
    				}
				    var bottonHeight = $(window).height()+$(scrollObj).scrollTop();
				    var orderHeight = $(scrollObj).height()+30;
 					if(!scrollSign && bottonHeight >= orderHeight){
				        scrollSign = true;
				        return ;
    				}
				    if( scrollSign &&  bottonHeight >= orderHeight)
				    {
				        scrollSign = false;
				        guessYouLove2($("#aa").attr("classId"),pageNo,$("#aa").attr("className"))
    				}
	  	 		})($(".navs53_right").get(0));	
	  	 	}
        );
 	})($(".navs53_right").get(0));
</script>
</body>
</html>
