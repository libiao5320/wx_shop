<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="no"/>
    <meta name="format-detection" content="telephone=no" />
    <title>融耀健康</title>
    <link href="../../../css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../../js/jquery.min.js"></script>
    <script type="text/javascript" src="../../../js/js.js"></script>
    <style>
        .navs37_p2{ margin: 0 10px;}
        .navs37_p2 label{display: block; width: 100%;}
        .navs37_p2 input{ width: 100%; padding:10px 5px; font-size: 15px; box-sizing: border-box;}
        .navs37s{
            margin: 20px 0px;
            background: #EEE none repeat scroll 0% 0%;
            display: none;
        }
    </style>
    
<script type="text/javascript">
        $(function(){
        	var browser = {
			versions: function () {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
			iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
			iPad: u.indexOf('iPad') > -1, //是否iPad
			};
			}(),
			}
			if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
			$('#sccs').css("font-size","13px");
			$('#sccs').css("padding","12px 0");
			}
			if (browser.versions.android) {
			
			}
         getpass();
         
         $('#sccs').css('letter-spacing',($('.mmtz_p1').width()-47)/6);
		 $('.mmtz').css('padding-left',($('.mmtz_p1').width()-60)/12);
		 $('.mmtz_p1 i').css("width",($('.mmtz_p1').width()-6)/6);				
		 $('#sccs').val('');
        });
		
        var getpass=function(){
            var password1=null;

            var  wd = $('#sccs').width();

            $('#sccs').on('input',function(){

                if($('#sccs').val().length==6){
                
	                if(isNaN($('#sccs').val())){//是否为非数字
						return;
					}

                    if(password1==null){
                        password1=$('#sccs').val();
                        $('#sccs').val('');
                        $('#sccs').focus();
                        $('.navs37_p1').html('请再次输入支付密码');
                    }
                    else if(password1!=null){
                        if(password1==$('#sccs').val()){
                            $('#sccs').blur();
                            var url = document.referrer;
                            if((url.indexOf("/goodsOrder")>0) || (url.indexOf("/eventOrder")>0)){
                            	$.post('/setPayPsd.ajax',{'parm1':password1,'url':url},function(json){
	                            	if(json.state){
	                            		alert("设置密码成功");
	                            		if(json.message != null && json.message !='' && json.message !='undefined'){
	                            			window.location.replace(json.message);
	                            		}
	                            		else{
	                            			window.location.href='/ryvip/index.htm';
	                            		}
	                            	}else{
	                            		alert("设置密码成功");
	                            	}
	                            },'json');
                            }else{
	                            //在这个地方进行输入成功操作
	                            redirect('/setPayPsd.htm?parm1='+password1);
	                            alert("设置密码成功");
	                            return password1;
                            }
                            
                        }
                        else{
                            alert("两次密码不一致");
                            password1=null;
                            $('#sccs').val('');
                            $('.navs37_p1').html('请设置支付密码');
                        }
                    }
                };
            });
        }

    </script>
</head>
<body class="cpxq_qb">
<header>
    <section class="head">
        <a class="left_nav" href="javascript:history.go(-1);" onclick="window.history.go(-1);"></a>
        <p class="top_txt">设置支付密码</p>
        <div class="top_right top_rights">

        </div>
    </section>
</header>
<article>
	
    <section class="navs37">
        <p class="navs37_p1">请设置支付密码</p>
       
        <div class="zgwt" style="text-align:center;width:100%; overflow: hidden;">
        <label class="mmtz">
		<p class="mmtz_p1"><i></i><i></i><i></i><i></i><i></i><i></i></p>
        	<input id="sccs" class="sccs" type="password" 
        		value="" name="" autocomplete="off"
        		onkeyup="this.value=this.value.replace(/\D/g,'')" 
				onafterpaste="this.value=this.value.replace(/\D/g,'')" >
				</label>
				</div>
				
    </section>

</article>


</body>
</html>
