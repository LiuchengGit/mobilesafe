/**
 * @functon
 * @author
 * @param
 * @return
 */
package com.sharetronic.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Louris 201
 *
 * 
 */
public class MD5Utils {
	
	
	public static String encodeMD5(String original)
	{
	try {
		MessageDigest instance = MessageDigest.getInstance("MD5");// 获取MD5算法对象
		byte[] digest = instance.digest(original.getBytes());// 对字符串加密,返回字节数组

		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			int i = b & 0xff;// 获取字节的低八位有效值
			String hexString = Integer.toHexString(i);// 将整数转为16进制
			// System.out.println(hexString);

			if (hexString.length() < 2) {
				hexString = "0" + hexString;// 如果是1位的话,补0
			}

			 sb.append(hexString);
		}

			Utils.debug("","md5:" + sb.toString());
		    //System.out.println("md5 length:" + sb.toString().length());//Md5都是32位
              return sb.toString();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		// 没有该算法时,抛出异常, 不会走到这里
    }
	
	return null;
}
	
}


