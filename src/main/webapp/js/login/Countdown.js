/*
 * 贝多倒计时函数
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */


var countDown=function(){
	
	this.countdowntime=60;   //默认时间６０秒
	/*
	 * 倒计时组件
	 * @param obj    //倒计时按钮对象  对象名称，请传入#id 或者.class　　{'#w'} ,{'.w'}
	 */
	this.countdownfun=function(obj){
		if(countdowntime==0){
			$(''+obj+'').removeAttribute('disabled');
			$(''+obj+'').value='免费获取验证码';
			countdown=60;
			return ;
		}else{
			$(''+obj+'').setAttribute("disabled",true);
			$(''+obj+'').value="重新发送("+countdowntime+")";
			countdown--;
		}
		setTimeout(function(){countdownfun(obj)},1000);
	}
	}
