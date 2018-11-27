/*
 * 验证功能类
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */


var Verify=function(){
	/*
	 * 验证功能
	 * 当焦点失去的时候ｐｏｓｔ异步查询注册信息
	 * @param parent    //input的ｄｏｍ对象
	 */
	this.Verify=function(parent){
		$(''+parent+'').blur(this.returnMessage(parent,$(''+parent+'').val()));
	}
	/*
	 * 本地验证提交的信息是否合格，合格之后再提交给ｐｏｓｔ服务器
	 * ＠ｐａｒａｍ    data   　　　　 //ｄｏｍ对象内容
	 * @param   type　: email,phone,name,pwd,num
	 */
	this.localVerify＝ function(data,type=''){
        switch(type){
        case '':
        	if(data.length==0){
        		alert('不能为空');
        		
        		}//return 
        	break;
        case 'email':
        	if(!data.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
        		alert('请输入正确邮箱');
        		}
        	break;
        case 'phone':
        	if(!data.match(/^(((13[0-9]{1})|159|153)+\d{8})$/))
        		{
        		alert('请输入正确手机号码');
        		}
        	break;
        case 'name':
        	if(data.length==0){
        		alert('不能为空');
        		}
        	break;
        case 'pwd':
        	if(data.length==0){
        		alert('不能为空');
        		}
        	break;
        }
	}
	
	
	/*
	 * 返回提示信息
	 * @param parent    //input的ｄｏｍ对象
	 * @param data
	 * {{name:name,phone:15574856759,username:zhoujianlign,pwd:1534158462}}
	 * type 3： 已经存在　２可以输入　　１提交成功
	 */
	this.returnMessage=function(parent,data){
		if(this.localVerify(data)){
			var type=this.get_getData(data);
			switch(type){
			case  1:
				$(''+parent+'').insetAfter('提交成功');
				break;
			case 2:
				 $(''+parent+'').insetAfter('可以输入');
			   break;
			case 3:
				 $(''+parent+'').insetAfter('已经存在');
			    break;
			}
		}
	}
}
register.prototype=new getData();
register.prototype=new Generation();






