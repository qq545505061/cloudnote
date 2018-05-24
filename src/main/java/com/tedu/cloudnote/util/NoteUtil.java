package com.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密处理
 */
public class NoteUtil {
	
	public static String createId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	/**
	 * 将传入的src加密处理
	 * @param src 明文字符串
	 * @return 加密后的字符串结果
	 * @throws Exception 
	 */
	public static String md5(String src) throws Exception {
		//将字符串信息采用MD5处理
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] output = md.digest(src.getBytes());
		//将MD5处理结果利用Base64转换成字符串
		String str = Base64.encodeBase64String(output);
		return str;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(md5("110110"));
		System.out.println(md5("few343"));
		System.out.println(createId());
	}

}
