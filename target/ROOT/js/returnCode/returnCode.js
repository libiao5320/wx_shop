/*
 * 返回验证码函数
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */
/*
 * 返回验证码函数
 * @param  parent
 * //对象名称，请传入#id 或者.class　　{'#w'} ,{'.w'}
 * @param  target
 * //对象名称，请传入#id 或者.class　　{'#w'} ,{'.w'}   //显示反对结果
 * @param data
 * {{"url":"www.baidu.com"},{"verify":1,"char2":2,"char3":3}}
 * 多态，请在此函数中扩展data和success
 * {username:$("#username").val(), content:$("#content").val()}
 *     $('#resText').empty();   //清空resText里面的所有内容
	     var html = ''; 
	     $.each(data, function(commentIndex, comment){
	           html += '<div class="comment"><h6>' + comment['username']
	                     + ':</h6><p class="para"' + comment['content']
	                     + '</p></div>';
	     });	
 */
var returnCode=function(){
	this.getcode=function(parent,data,target){
		$(''+parent+'').click(function(){
			$.ajax({
	             type: "POST",
	             url: data[0]['url'],
	             data:data[1],
	             dataType: "json",
	             success: function(data){ 
	                 $(''+target+'').html(data);},
	             error:function(){
	            	 $(''+target+'').html('验证失败');}
	         });
		});
	}
}