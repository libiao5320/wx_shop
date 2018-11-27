/**数值转换，把string数字转换成number*/
var convert = function(val){
	if(check(val)){
		if(typeof(val) == 'string'){
			val = parseFloat(val);
		}
		return val;
	}else{
		return null;
	}
}


/**把价格分转换成元保留2位小数点*/
var moneyFormat = function(money){
	money = convert(money);
	var doubleMoney = parseFloat(money/100).toFixed(2);
	
	return doubleMoney;
}


/**时间格式化*/
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 判断是否为金额
 */
var check = function(v)
{
	var a=/^([0-9])|([1-9]\d+)\.\d?$/;
	if(!a.test(v))
	{
	return false;
	}
	else
	{
	return true;
	}
}

/**
 * 判断是否为金额
 */
var checkMoney = function(v)
{
	var a = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if(!a.test(v))
	{
	return false;
	}
	else
	{
	return true;
	}
}

var checkFloat = function(v,$this){
	var s = v.split('.');
	var l = s.length;
	if(l>2){
		$this.val(v.substring(0,v.length-1));
	}else if(l==2){
		var r = s[0];
		var r1 = s[1];
		if(r.length >=2){
			if(r.charAt(0) == '0'){
				if(r.charAt(1) != '.'){
					$this.val(0);
				}
			}
		}
		if(isNaN(r1)){
			$this.val(r);
		}
	}else{
		if(v.length >=2){
			if(!isNaN(v)){
				if(v.charAt(0)=='0'){
					$this.val(0);
				}
			}else{
				$this.val('');
			}
		}
	}
	return true;
}