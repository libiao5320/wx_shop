package cc.royao.commons.utils;

import java.util.Random;

/**
 * @objectname  
 * @createdate 2013-7-18 11:11:47
 * @description 此类为工具类，所有方法均协助处理String字符串，此工具类上锁。
 * @creator yangx
 */
public final class StringUtils {
	
	private StringUtils(){}
	
	/**
	 * hasLength
	 * @param str
	 * @return boolean 
	 * @description: 判断字符串是否为非0
	 * 	StringUtils.hasLength(null)  => false;
	 * 	StringUtils.hasLength("")    => false;
	 * 	StringUtils.hasLength(" ")   =>true;
	 * 	StringUtils.hasLength("hi")  =>true;
	 */
	public static boolean hasLength(final CharSequence str){
		
		return (str!=null && str.length()>0);
		
	}
	
	/**
	 * hasLength
	 * @param str
	 * @return boolean 
	 * @see: hasLength(CharSequence str)
	 */
	public static boolean hasLength(final String str){
		
		return hasLength((CharSequence)str);
		
	}
	
	/**
	 * hasText
	 * @param str
	 * @return boolean 
	 * @description: 判断字符串是否有文字
	 * 	StringUtils.hasText(null)   => false;
	 *  StringUtils.hasText("")     => false;
	 *  StringUtils.hasText("  ")   => false;
	 *  StringUtils.hasText("text") =>true;
	 *  
	 * @see java.lang.Character.isWhitespace method
	 */
	public static boolean hasText(final CharSequence str){
		
		if(!hasLength(str))
			return false;
		
		int strLenght=str.length();
		
		for(int i=0;i<strLenght;i++)
			if(Character.isWhitespace(str.charAt(i)))
				return false;
		
		return true;
		
	}
	
	/**
	 * hasText
	 * @param str
	 * @return boolean 
	 * @see hasText(CharSequence str)
	 */
	public static boolean hasText(final String str){
		
		return hasText((CharSequence)str);
		
	}
	
	/**
	 * quote
	 * @param str
	 * @return String 
	 * @description: 为字符串加上引用符号
	 * StringUtils.quote("quoteStr") => "'quoteStr'"
	 */
	public static String quote(String str,String symbol){
		
		return str==null?null:symbol+str+symbol;
		
	}
	
	/**
	 * quote
	 * @param str
	 * @return String 
	 * @description: 为字符串加上引用符号
	 * StringUtils.quote("quoteStr") => "'quoteStr'"
	 */
	public static String quote(String str){
		
		return quote(str,"'");
		
	}
	
	/**
	 * replaceNull
	 * @param inStr
	 * @param defStr
	 * @return String 
	 * @discription: 验证inStr为null or "" or ""+null  如满足条件，则返回 defStr定义的String
	 */
	public static String replaceNull(final String inStr,final String defStr){
		
		if(defStr==null)
			return null;
		
		if(inStr==null || "".equals(inStr.trim()) || "NULL".equals(inStr.trim().toUpperCase()))
			return defStr;
		
		return inStr;
		
	}
	
	/**
	 * stringFormat
	 * @param str
	 * @param length
	 * @param formatStr
	 * @return String 
	 * @description: 从字符串第一位开始截取指定长度字符串后加上指定符号
	 * StringUtils.stringFormat("",3,"%%")    =>null
	 * StringUtils.stringFormat(null,3,"%%")  =>null
	 * StringUtils.stringFormat("1234567890",3,null)  =>"1234567890"
	 * StringUtils.stringFormat("1234567890",11,"%%")  =>"1234567890%%"
	 * StringUtils.stringFormat("1234567890",-1,"%%")  =>"1234567890%%"
	 * StringUtils.stringFormat("1234567890",3,"%%")  =>"123%%"
	 * 
	 */
	public static String stringFormat(CharSequence str,int length,final CharSequence formatStr){
		
		if(!hasLength(str))
			return null;
		else if(formatStr ==null)
			return str.toString();
		else if(str.length()<length || length<0)
			return str.toString();
		else
			return str.subSequence(0,length)+formatStr.toString();
		
		
		
	}
	
	/**
	 * stringFormat
	 * @param str
	 * @param length
	 * @param formatStr
	 * @return String 
	 * @see stringFormat(CharSequence str,int length,final CharSequence formatStr)
	 */
	public static String stringFormat(String str,int length,final String formatStr){
		
		return stringFormat((CharSequence)str,length,(CharSequence)formatStr);
		
	}
	
	/**
	 * titleFormat
	 * @param str
	 * @param length
	 * @return String 
	 * @description:从字符串第一位开始截取指定长度字符串后加上'...' 一般指用户标题列表的截取
	 * StringUtils.stringFormat("",3)    =>null
	 * StringUtils.stringFormat(null,3)  =>null
	 * StringUtils.stringFormat("1234567890",11)  =>"1234567890..."
	 * StringUtils.stringFormat("1234567890",-1)  =>"1234567890..."
	 * StringUtils.stringFormat("1234567890",3)  =>"123..."
	 * 
	 */
	public static String titleFormat(String str,int length){
		
		return stringFormat((CharSequence)str,length,"...");
		
	}
	
	/**
	 * isString
	 * @param str
	 * @return boolean 
	 * @description: 判断传入的object 是否为String
	 * StringUtils.isString((Object)1234)       =>false
	 * StringUtils.isString((Object)testClass)  =>false
	 * StringUtils.isString((Object)"test")     =>true
	 */
	public static boolean isString(Object str){
		
		return (str instanceof String); 
		
	}
	
	public static String replaceHtmlTag(String content){
		
		if(hasLength(content))
		
			return content.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		
		else
			
			return "";
		
	}
	
	
	public static String randomNum(int n) {
		String strChar= "0,1,2,3,4,5,6,7,8,9";
		String[] vcArray = strChar.split(",");
		Random rand = new Random(System.currentTimeMillis());
		String code = "";
		for (int i = 0; i < n; i++)
		{
		  code += vcArray[rand.nextInt(10)];
		}
		return code;
	}

	
	
	
	public static void main(String[] args){
		
		
		System.out.println("1234567890".substring(0, 0));
		
		String testString=null;
		
		System.out.println(StringUtils.replaceNull(testString, "isnull"));
		
		testString=" ";
		
		System.out.println(StringUtils.replaceNull(testString, "is  ''"));
		
		testString="null";
		
		System.out.println(StringUtils.replaceNull(testString, "is String null "));
		
		testString="rightString";
		
		System.out.println(StringUtils.replaceNull(testString, "is changeError"));
		
		System.out.println(StringUtils.replaceNull(testString, null));
		
		System.out.println(StringUtils.titleFormat("发几嗲飞机哦啊房间哦", 3));
		
	}
}