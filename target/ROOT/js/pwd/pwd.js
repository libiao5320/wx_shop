/*
 *密码输入功能函数
 *作者：周降临
 *邮箱：zhoutq@outlook.com 
 */
 
var pwd=function(){
	var me=this;
	/*
	 * 循环所有input,返回现在焦点所要指向的input顺序lengthval()!=''
	 */
	this.inputIndex=function(){
		var i=0;
		while($('input').eq(i).val()!=''&&i!=5){
		   i++;
		}
		return i;
	}
	
	/*
	 * 根据input顺序，给input焦点
	 * @param index 当前顺序
	 */
	this.inputFocus=function(index){
		$('input').eq(index).focus();
	}
	
	/*
	 * 根据input顺序，失去input焦点
	 * @param index 当前顺序
	 */
	this.inputBlur=function(index){
		$('input').eq(index).blur();
	}
	
	/*
	 * 判断input是否为空
	 */
	this.inputIsNo=function(){
		if($("input").val().length==0)
		{
			return true;
			}
		else{
			return false;
		}
	}
	/*
	 * 删除input中的值
	 * @param index 当前顺序
	 */
	this.inputDelete=function(index){
		$('input').eq(index).val('');
	}
	/*
	 * 删除所有input中的值
	 */
	this.inputDeleteAll=function(){
		$('input').val('');
	}
    /*
     * 判断密码是否达到6位数字
     * @param index
     */
	this.pwdPass=function(index){
		var flag=false;
		for(var i=1;i<index;i++){
			if($('input').eq(i).val()!=''){
				flag=true;
			}
			else{
				flag=false;
			}
		}
	    if(flag==true){
	    	return true;
	    }
	}
	/*
	 * 获取6位密码函数
	 */
	this.getPwd=function(){
		var passwd='';
		var i=0;
		while($('input').eq(i).val()!=''&&i!=6){
			passwd=passwd+$('input').eq(i).val();
			i++;
		}
		return passwd;
	}
}

/*
 * 实用列子

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
		      $('').show();
		    }
		}
	});
	
});
 */
