/*
 *上传文件js函数类
 *fun up
 *fun down
 *作者：周降临
 *邮箱：zhoutq@outlook.com 
 */
 
var upFile=function(){
	/*
	 * up为上传函数
	 * @param path  //上传路径
	 * @param fileName //上传文件名  包含后缀
	 * @param parent    //需要添加信息的dom父类
	 * @param inputId    //上传域的id
	 */
	this.up=function(path,fileName,parent,inputId){
		$.ajaxFileUpload({  
	        url : path,   //submit to UploadFileServlet  
	        secureuri : false,  
	        fileElementId : parent,  
	        dataType : 'text', //or json xml whatever you like~  
	        success : function(data, status) {  
	            $(''+parent+'').append(data);  
	        },  
	        error : function(data, status, e) {  
	           // $(''+parent+'').append(data);  
	        }  
	    });  
	}
	
}
