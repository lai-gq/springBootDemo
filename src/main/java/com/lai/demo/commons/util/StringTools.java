package com.lai.demo.commons.util;

import java.util.Random;
import java.util.UUID;

public class StringTools {
	/**
	 * 校验是否为空
	 * @param msg
	 * @return
	 */
	public static boolean checkNull(String msg){
		if("".equals(msg)||null==msg||msg.length()==0){
			return true;
		}
		return false;
	}
	
    /**
     * 随机字符串生成
     * @param length  生成长度
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度      
         String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";         
         Random random = new Random();         
         StringBuffer sb = new StringBuffer();         
         for (int i = 0; i < length; i++) {         
             int number = random.nextInt(base.length());         
             sb.append(base.charAt(number));         
         }         
         return sb.toString();         
    }
    
    /**
     * 生成uuid唯一值
     * @return
     */
    public static String createUUID(){
    	String uuid = UUID.randomUUID().toString(); //转化为String对象  
        uuid = uuid.replace("-", ""); //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可 
        return uuid;
    }
}
