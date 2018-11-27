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
    <script type="text/javascript">
var isAjax = true;
var pageNo = 2;
var commentntId = ${commentntId !'null'};

$(function(){
	
	//滚动事件
	$(window).scroll(function(){
		var top = $(window).scrollTop();
		var doheight = $(document).height();
		var win_height=$(window).height();
		
		if(Boolean(isAjax)){
			if(top >= (doheight-win_height)){
				ajaxload(pageNo);
				pageNo++;
			}
		}
	});
});

function ajaxload(pageNo){

	$.ajax({
		url: '/event/commentlist.ajax' ,
		type: 'POST',
		data: {'map[pageNo]':pageNo,'map[commentntId]':commentntId,'map[isShow]':'Y'} ,
		dataType: 'json',
		success: function(json){
			if(json.state){
				if(json.param == '' || json.param == null || json.param == 'undefined'){
					
					isAjax = false;//使分页不可以使用
					$(".nomore").css("display","block");
					
					return;
					
				}else{
					evaluatelist(json.param);
				}
			}else{
				isAjax = false;//使分页不可以使用
				$(".nomore").show();
			}
			
		} ,
		error: function(){
			$(".nomore").show();	
			//alert("网络忙，请稍后重试！");
		}
	});
}

function evaluatelist(data){
	
	if(data != 'undefined' && data.length > 0){
		for(var i=0;i < data.length ;i ++){
			
			$li = $('<li  class="navs13_li">'+data[i].id+'</li>');
			
			if(data[i].member != '' && data[i].member != null && data[i].member != 'undefined'){
			$div_left = $('<div class="navs13_left"><img src="'+data[i].member.memberAvatar+'" title="" alt=""></div>');
			}else{
			$div_left = $('<div class="navs13_left"><img src="/images/cpxq_tx01.jpg" title="" alt=""></div>');
			}
			
			$li.append($div_left);//第一个div
			
			$div_right = $('<div class="navs13_right"></div>');
			
			if(data[i].member != '' && data[i].member != null && data[i].member != 'undefined' && data[i].member.memberMobile != '' && data[i].member.memberMobile != 'undefined')
				$right_p1 = $('<p class="navs13_right_p1">'+data[i].member.memberMobile.substring(0,2)+'****'+data[i].member.memberMobile.substring(7,10)+'<span>'+data[i].strCreateTime+'</span></p>');
			
			else if(data[i].member != '' && data[i].member != null && data[i].member != 'undefined' && ata[i].member.memberName != '' && ata[i].member.memberName != 'undefined')
				$right_p1 = $('<p class="navs13_right_p1">'+data[i].member.memberName+'<span>'+data[i].strCreateTime+'</span></p>');
			else 
				$right_p1 = $('<p class="navs13_right_p1">匿名用户<span>'+data[i].strCreateTime+'</span></p>');
				
			$div_right.append($right_p1);//
			
			$right_p2 = $('<p class="navs13_right_p2"></p>');
			
			if(data[i].grade != '' && data[i].grade != null && data[i].grade != 'undefined'){
				if(data[i].grade == 0)
					$i = $('<i></i><i></i><i></i><i></i><i></i>');
				else if(data[i].grade == 1)
					$i = $('<i class="navs13_i"></i><i></i><i></i><i></i><i></i>');
				else if(data[i].grade == 2)
					$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>');
				else if(data[i].grade == 3)
					$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>');
				else if(data[i].grade == 4)
					$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i>');
				else if(data[i].grade == 5)
					$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>');
				else
					$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>');
			}else{
				$i = $('<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>');
			}
			
			$right_p2.append($i);
			$div_right.append($right_p2);
			$li.append($div_right);
			
			$navs13_p1 = $('<p class="navs13_p1">'+data[i].detail+'</p>');
			$li.append($navs13_p1);
			
			$ul = $('<ul></ul>');
			
			if(data[i].imageList != '' && data[i].imageList != 'undefined' && data[i].imageList.length > 0){
				var image = data[i].imageList;
			
				for(var j = 0; j < data[i].imageList.length; j ++){
					$li2 = $('<li><a><img src="'+image[j].img+'" alt="" title=""></a></li>');
					$ul.append($li2);
				}
			}
			$li.append($ul);
			
			$("#evaluate").append($li);
		}
	}
}
    </script>
</head>
<body>
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);"></a>
        <p class="top_txt">全部评价</p>
        <div class="top_right top_rights">
        </div>
    </section>
</header>
<article>
    <section class="navs13">

        <ul class="navs13_ul" id="evaluate">
            <#if list?? && (list?size >0)>
        	<#list list as comment>
            <li class="navs13_li">${comment.id }
                <div class="navs13_left">
                	<#if comment.member??>
                    <img src="${comment.member.memberAvatar !''}" title="" alt="">
                    <#else>
                    <img src="/images/cpxq_tx01.jpg" title="" alt="">
                    </#if>
                </div>
                <div class="navs13_right">
                    <p class="navs13_right_p1">
                    	<#if comment?? && comment.member ?? && comment.member.memberMobile??>
                    	${comment.member.memberMobile?substring(0,2)}****${comment.member.memberMobile?substring(7,10)}
                    	<#elseif comment?? && comment.member ??>
                    	${comment.member.memberName !''}
                    	<#else>
                    	匿名用户
                    	</#if>
                    	<span>${comment.strCreateTime !''}</span>
                    </p>
                    <p class="navs13_right_p2">
                    	<#if comment.grade?? && comment.grade == 0>
                    	<i></i><i></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 1>
                    	<i class="navs13_i"></i><i></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 2>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i><i></i>
                    	<#elseif comment.grade?? && comment.grade == 4>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class=""></i>
                    	<#elseif comment.grade?? && comment.grade == 5>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i>
                    	<#else>
                    	<i class="navs13_i"></i><i class="navs13_i"></i><i class="navs13_i"></i><i></i><i></i>
                    	</#if>
                    </p>
                </div>
                <p class="navs13_p1">${comment.detail !''}</p>
                <#if comment?? && comment?size gt 0>
                <ul>
                	<#if comment.imageList?? && comment.imageList?size gt 0>
                	<#list comment.imageList as image>
                    <li><a><img src=${image.img !''} alt="" title=""></a></li>
                    </#list>
                    </#if>
                </ul>
                </#if>
            </li>
            </#list>
            </#if>
        </ul>
		<div class="nomore">没有更多了...</div>
    </section>
</article>


</body>
</html>
