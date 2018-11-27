/*
 * 贝多前端获取数据函数
 * 作者：周降临
 * 邮箱：zhoutq@outlook.com
 */
var getData=function(){
	this.data=[];                        //发送给服务器的数据
	this.getdata=[];　　　　//接受服务器发来的数据
/*
 * 获取服务器数据
 * data{"url":{url:http://www.baidu.com},"data":{char:1,char:2,char:3}}
 * */ 
	this.getPost=function(data){
		alert("dsd");
		$.ajax({
			  type: 'POST',
			  url: data["url"]['url'],
			  data: data["data"],
			  success:  function(getdata){
				     return this.getdata=getdata;
			   },
			  dataType: "json"
			});
	}
	
	
}